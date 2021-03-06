package com.servletProject.librarySystem.dao.transactionManager.connectionPool;

import com.servletProject.librarySystem.exception.ConnectionPoolIsEmptyException;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.*;

public class ConnectionPool {
    private BlockingQueue<ConnectionHolder> connectionsPool = new LinkedBlockingDeque<>();
    @Getter
    private int pollSize;
    private long connectionTimeoutInThePool;
    private int usedConnections;
    private Properties dbProps;

    public ConnectionPool(int poolSize, long connectionTimeoutInSecond) {
        this.pollSize = poolSize;
        this.connectionTimeoutInThePool = connectionTimeoutInSecond;
        try {
            createConnectionAndFillThePool();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ConnectionUsageMonitoring connectionUsageMonitoring = new ConnectionUsageMonitoring();
        connectionUsageMonitoring.run();
    }

    int getCurrentPoolSize() {
        return connectionsPool.size();
    }

    private void returnConnectionToThePool(Connection connection) {
        connectionsPool.add(new ConnectionHolder(connection, new Date()));
    }

    @SneakyThrows
    public  Connection getConnection() {
        if (pollSize == usedConnections) {
            throw new ConnectionPoolIsEmptyException("Connection pool is empty!");
        } else if (connectionsPool.isEmpty() && usedConnections < pollSize) {
            usedConnections++;
            return getJdbcConnection();
        } else {
            final Connection connection = getPendingConnection();
            return connection.isValid( 0) ? connection : getJdbcConnection();
        }
    }

    private JdbcConnection getJdbcConnection() throws ClassNotFoundException, SQLException {
        return new JdbcConnection(createAndGetConnection(), this);
    }

    private Connection getPendingConnection() throws InterruptedException {
        final ConnectionHolder connectionHolder = connectionsPool.poll(connectionTimeoutInThePool, TimeUnit.SECONDS);
            usedConnections++;
            return new JdbcConnection(connectionHolder.getConnection(), this);

    }

    private void createConnectionAndFillThePool() throws SQLException, ClassNotFoundException {
        for (int i = 0; i < pollSize; i++) {
            final Connection connection = createAndGetConnection();
            Date connectionCreationTime = new Date();
            connectionsPool.add(new ConnectionHolder(connection, connectionCreationTime));
        }
    }

    private Connection createAndGetConnection() throws ClassNotFoundException, SQLException {
        loadPropertyFile();
        String driver = dbProps.getProperty("driver");
        Class.forName(driver);
        String url = dbProps.getProperty("url");
        Properties props = setAndGetDataBaseProperties();
        return getConnectionFromDriverManager(url, props);
    }

    private void loadPropertyFile() {
        try (FileInputStream in = new FileInputStream("/Users/samsonov/IdeaProjects/LibrarySystem/src/main/resources/db.properties")) {
            dbProps  = new Properties();
            dbProps.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException {
        return DriverManager.getConnection(url, props);
    }

    private Properties setAndGetDataBaseProperties() {
        Properties props = new Properties();
        String username = dbProps.getProperty("username");
        String password = dbProps.getProperty("password");
        props.setProperty("user", username);
        props.setProperty("password", password);
        props.setProperty("ssl", "false");
        return props;
    }

    void close(Connection connection) throws SQLException {
        if (connection.isValid(0)) {
            closeValidConnection(connection);
        } else {
            connection.close();
        }
    }

    private void closeValidConnection(Connection connection) throws SQLException {
        if (getCurrentPoolSize() == getPollSize()) {
            closeConnectionIfPoolFull(connection);
        }
        putUserConnectionToThePool(connection);
    }

    private void closeConnectionIfPoolFull(Connection connection) throws SQLException {
        usedConnections--;
        connection.close();
    }

    private void putUserConnectionToThePool(Connection connection) {
        usedConnections--;
        returnConnectionToThePool(connection);
    }

    private class ConnectionUsageMonitoring {

        private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        void run() {
            executorService.schedule(this::cleanUpConnectionPool, 1000, TimeUnit.MILLISECONDS);
        }

        private void cleanUpConnectionPool() {
            doWork();
            executorService.schedule(this::cleanUpConnectionPool, 1000, TimeUnit.MICROSECONDS);
        }

        private void doWork() {
            try {
                startMonitoringConnectionUsage();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        private void startMonitoringConnectionUsage() throws SQLException {
            final Iterator<ConnectionHolder> iterator = connectionsPool.iterator();
            closeUnusedConnection(iterator);
        }

        private void closeUnusedConnection(Iterator<ConnectionHolder> iterator) throws SQLException {
            if (iterator.hasNext()) {
                final ConnectionHolder connectionHolder = iterator.next();
                if (connectionTimeout(connectionHolder.getLastAccessTime())) {
                    iterator.remove();
                    connectionHolder.getConnection().close();
                }
            }
        }

        private boolean connectionTimeout(Date lastAccessTime) {
            final long l = Duration.between(lastAccessTime.toInstant(), Instant.now()).toMillis();
            return l > 2000;
        }
    }
}
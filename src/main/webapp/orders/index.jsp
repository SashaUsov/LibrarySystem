<%@ page import="com.servletProject.librarySystem.domen.UserOrdersTransferObject" %>
<%@ page import="java.util.List" %>
<%@ page import="static com.oracle.jrockit.jfr.ContentType.Address" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book catalog</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Library system</h1>
</div>

<div class="w3-container w3-center w3-green">
    <h2>Reserved books</h2>

    <table class="w3-table-all">
        <tr class="w3-blue">
            <th>Book title</th>
            <th>Book author</th>
            <th>Genre</th>
            <th>Publication</th>
            <th> </th>
            <th> </th>
        </tr>

        <%
            if (request != null) {
                final List<UserOrdersTransferObject> reservedBooks = (List<UserOrdersTransferObject>) request
                        .getSession().getAttribute("list_of_reserved_books");
                if (reservedBooks != null && !reservedBooks.isEmpty()) {
                    for (UserOrdersTransferObject book : reservedBooks) {
                        out.println("<tr>\n" +
                                            "<td>" + book.getBookTitle() + "</td>\n" +
                                            "<td>" + book.getBookAuthor() + "</td>\n" +
                                            "<td>" + book.getGenre() + "</td>\n" +
                                            "<td>" + book.getYearOfPublication() + "</td>\n" +
                                            "<td>" + "<div>\n" +
                                            "    <form id=\"submitform\" action=\"/order-cancel\" method=\"post\">\n" +
                                            "            <input type=\"hidden\" name=\"book_copy_id\" value=" + book.getUniqueId() + "><br/>\n" +
                                            "        <button type=\"submit\"  class=\"w3-btn w3-ripple w3-teal\">Cancel order</button>\n" +
                                            "    </form>\n" +
                                            "</div>" + "</td>\n" +
                                            "<td>" + "<div>\n" +
                                            "    <form id=\"submitform2\" action=\"/complete/order\" method=\"post\">\n" +
                                            "            <input type=\"hidden\" name=\"book_copy_id\" value=" + book.getUniqueId() + "><br/>\n" +
                                            "            <input type=\"hidden\" name=\"reader_id\" value=" + book.getUserId() + "><br/>\n" +
                                            "        <button type=\"submit\"  class=\"w3-btn w3-ripple w3-teal\">Give book</button>\n" +
                                            "    </form>\n" +
                                            "</div>" + "</td>\n" +
                                            "</tr>");
                    }
                }
            }
        %>
    </table>
</div>
<%
    if (request.getSession().getAttribute("user") != null) {
        out.println("<button class=\"w3-btn w3-block w3-red w3-left-align\" onclick=\"location.href='/logout/exit'\">Logout</button>\n");
    }
%>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main</button>
</div>
</body>
</html>

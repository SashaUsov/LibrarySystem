<%@ page import="com.servletProject.librarySystem.domen.CopiesOfBooks" %>
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
    <h2>Unusable books</h2>

    <table class="w3-table-all">
        <tr class="w3-blue">
            <th>Instance number</th>
            <th>Book id</th>
            <th>Available</th>
            <th>Book condition</th>
            <th></th>
        </tr>

        <%
            if (request != null) {
                final List<CopiesOfBooks> unusableCopy = (List<CopiesOfBooks>) request
                        .getSession().getAttribute("unusable_copy");
                if (unusableCopy != null && !unusableCopy.isEmpty()) {
                    for (CopiesOfBooks book : unusableCopy) {
                        out.println("<tr>\n" +
                                            "<td>" + book.getId() + "</td>\n" +
                                            "<td>" + book.getIdBook() + "</td>\n" +
                                            "<td>" + book.getBookCondition() + "</td>\n" +
                                            "<td>" + book.isAvailability() + "</td>\n" +
                                            "<td>" + "<div>\n" +
                                            "    <form id=\"submitform\" action=\"/books/unusable\" method=\"post\">\n" +
                                            "            <input type=\"hidden\" name=\"unusable_id\" value=" + book.getId() + "><br/>\n" +
                                            "        <button type=\"submit\"  class=\"w3-btn w3-ripple w3-teal\">Delete unusable book</button>\n" +
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

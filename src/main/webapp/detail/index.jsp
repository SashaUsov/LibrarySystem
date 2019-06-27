<%@ page import="com.servletProject.librarySystem.domen.CopiesOfBooks" %>
<%@ page import="com.servletProject.librarySystem.service.data.BookCatalogService" %>
<%@ page import="java.util.List" %>
<%@ page import="static com.oracle.jrockit.jfr.ContentType.Address" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book copy</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Library system</h1>
</div>


<table class="w3-table-all">
    <tr>
        <th>Instance number</th>
        <th>Available</th>
        <th>Book condition</th>
        <th> </th>
    </tr>
    <%
        if (request != null) {
            long book_id = Long.valueOf(request.getParameter("book_id"));
            BookCatalogService bookCatalogService = new BookCatalogService(bookRepository, copiesOfBooksRepository);
            List<CopiesOfBooks> bookCopy = bookCatalogService.getAllBookCopy(book_id);
            out.println("<div class=\"w3-container w3-center w3-green\">\n" +
                                "            <h2>Copies of the book</h2>\n" +
                                "        </div>");
            if (bookCopy != null && !bookCopy.isEmpty()) {
                for (CopiesOfBooks book : bookCopy) {
                    final long book_copy_id = book.getId();
                    out.println("<tr>\n" +
                                        "<td>" + book.getId() + "</td>\n" +
                                        "<td>" + book.isAvailability() + "</td>\n" +
                                        "<td>" + book.getBookCondition() + "</td>\n" +
                                        "<td>" + "<div>\n" +
                                        "    <form id=\"submitform\" action=\"/booking\" method=\"post\">\n" +
                                        "            <input type=\"hidden\" name=\"book_copy_id\" value=" + book_copy_id + "><br/>\n" +
                                        "        <button type=\"submit\"  class=\"w3-btn w3-ripple w3-teal\">Booking</button>\n" +
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

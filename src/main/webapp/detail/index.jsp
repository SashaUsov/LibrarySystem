<%@ page import="com.servletProject.librarySystem.domen.CopiesOfBooks" %>
<%@ page import="com.servletProject.librarySystem.service.BooksService" %>
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


<div class="w3-container w3-padding">
    <%
        if (request != null) {
            long book_id = Long.valueOf(request.getParameter("book_id"));
            BooksService booksService = new BooksService();
            List<CopiesOfBooks> bookCopy = booksService.getAllBookCopy(book_id);
            out.println("<div class=\"w3-container w3-center w3-green\">\n" +
                                "            <h2>Copies of the book</h2>\n" +
                                "        </div>");
            if (bookCopy != null && !bookCopy.isEmpty()) {
                for (CopiesOfBooks book : bookCopy) {
                    out.println("<li class=\"w3-hover-sand\">" + "Instance number: " + book.getId()
                                        + "; Available: " + book.isAvailability()
                                        + "; Book condition: " + book.getBookCondition()
                                        + "</li>");
                }
            }
        }
    %>
</div>
<%
    if (request.getSession().getAttribute("user") != null) {
        out.println("<div class=\"w3-container w3-grey w3-opacity w3-right-align w3-padding\">\n" +
                            "<button class=\"w3-btn w3-round-large\" onclick=\"location.href='/logout/exit'\">Logout</button>\n" +
                            "</div>");
    }
%>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main</button>
</div>
</body>
</html>

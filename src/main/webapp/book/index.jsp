<%@ page import="com.servletProject.librarySystem.domen.BookCatalog" %>
<%@ page import="com.servletProject.librarySystem.domen.UserEntity" %>
<%@ page import="com.servletProject.librarySystem.service.LibrarianService" %>
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

<%
    if (request.getSession().getAttribute("user") != null) {
        final UserEntity user = (UserEntity)request.getSession().getAttribute("user");
        final List<String> roles = user.getRole();
        for (String role : roles) {
            if ("LIBRARIAN".equals(role) || "ADMIN".equals(role)) {
                out.println("<div class=\"w3-card-4\" >\n" +
                                    "    <div class=\"w3-container w3-center w3-green\" >\n" +
                                    "        <h2 > Add book</h2 >\n" +
                                    "    </div >\n" +
                                    "\n" +
                                    "    <form id = \"submitform\" action = \"/book/catalog\" method = \"post\" class=\n" +
                                    "                \"w3-selection w3-light-grey w3-padding\" >\n" +
                                    "        <label > Book title:\n" +
                                    "            <input type = \"text\" name = \"book_title\" class=\"w3-input w3-animate-input w3-border w3-round-large\"\n" +
                                    "                style = \"width: 30%\" ><br / >\n" +
                                    "        </label >\n" +
                                    "\n" +
                                    "        <label > Book author:\n" +
                                    "            <input type = \"text\" name = \"book_author\" class=\"w3-input w3-animate-input w3-border w3-round-large\"\n" +
                                    "                style = \"width: 30%\" ><br / >\n" +
                                    "        </label >\n" +
                                    "\n" +
                                    "        <label > Publication:\n" +
                                    "            <input type = \"text\" name = \"publication\" class=\"w3-input w3-animate-input w3-border w3-round-large\"\n" +
                                    "                style = \"width: 30%\" ><br / >\n" +
                                    "        </label >\n" +
                                    "\n" +
                                    "        <label > Genre:\n" +
                                    "            <input type = \"text\" name = \"genre\" class=\"w3-input w3-animate-input w3-border w3-round-large\"\n" +
                                    "                style = \"width: 30%\" ><br / >\n" +
                                    "        </label >\n" +
                                    "\n" +
                                    "        <button type = \"submit\" class=\"w3-btn w3-green w3-round-large w3-margin-bottom\" > Submit </button >\n" +
                                    "    </form >\n" +
                                    "</div >");
            }
        }
    }
%>

<div class="w3-container w3-padding">
    <%
        LibrarianService librarianService = new LibrarianService();
        final List<BookCatalog> allBook = librarianService.getAllBook();
        out.println("<div class=\"w3-container w3-center w3-green\">\n" +
                            "            <h2>All books</h2>\n" +
                            "        </div>");
        if (allBook != null && !allBook.isEmpty()) {
            for (BookCatalog book : allBook) {
                out.println( "<li class=\"w3-hover-sand\">" + "Book title: " + book.getBookTitle()
                                    + "; Book author: " + book.getBookAuthor()
                                    + "; Year of publication: " + book.getYearOfPublication()
                            + "; Genre: " + book.getGenre() + " "
                            + "; Total amount: " + book.getTotalAmount() +"</li>");
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

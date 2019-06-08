<%@ page import="com.servletProject.librarySystem.domen.UserEntity" %>
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
        final UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        final List<String> roles = user.getRole();
        for (String role : roles) {
            if ("LIBRARIAN".equals(role) || "ADMIN".equals(role)) {
                out.println("<details>\n" +
                                    "    <summary><div class=\"w3-container w3-center w3-green\">\n" +
                                    "            <h2>Add book</h2>\n" +
                                    "        </div></summary>" + "<div class=\"w3-card-4\" >\n" +
                                    "\n" +
                                    "    <form id = \"submitform\" action = \"/books/catalog\" method = \"post\" class=\n" +
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
                                    "</div >" +
                                    "</details>");
            }
        }
    }
%>

<details>
    <summary>
        <div class="w3-panel w3-blue">
            <h1>Find a book by title</h1>
        </div>
    </summary>
    <form id="submitform" action="/book/search-by-title" method="post" class="w3-selection w3-light-grey w3-padding">
        <input type="text" name="book_title" class="w3-input w3-animate-input w3-border w3-round-large"
               style="width: 30%"><br/>
        <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Find</button>
    </form>
</details>

<details>
    <summary>
        <div class="w3-panel w3-blue">
            <h1>Find a book by author</h1>
        </div>
    </summary>
    <form id="submitform2" action="/book/search-by-author" method="post" class="w3-selection w3-light-grey w3-padding">
        <input type="text" name="book_author" class="w3-input w3-animate-input w3-border w3-round-large"
               style="width: 30%"><br/>
        <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Find</button>
    </form>
</details>

<details>
    <summary>
        <div class="w3-panel w3-blue">
            <h1>Find a book by genre</h1>
        </div>
    </summary>
    <form id="submitform3" action="/book/search-by-genre" method="post" class="w3-selection w3-light-grey w3-padding">
        <input type="text" name="book_genre" class="w3-input w3-animate-input w3-border w3-round-large"
               style="width: 30%"><br/>
        <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Find</button>
    </form>
</details>

<details>
    <summary>
        <div class="w3-panel w3-blue">
            <h1>Books catalog</h1>
        </div>
    </summary>
    <div class="w3-btn w3-block w3-green w3-left-align">
        <button class="w3-btn w3-round-large" onclick="location.href='/books'">To the books catalog</button>
    </div>
</details>
<br>

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

<%@ page import="com.servletProject.librarySystem.domen.UserEntity" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Library system</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Library system greeting you!</h1>
</div>

<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
        <%
            if (request.getSession().getAttribute("user") == null) {
                out.println("<button class=\"w3-btn w3-hover-green w3-round-large\" onclick=\"location.href='/authorization'\">Authorization</button>\n");
            }
            if (request.getSession().getAttribute("user") != null) {
                out.println("<button class=\"w3-btn w3-hover-green w3-round-large\" onclick=\"location.href='/userpage'\">My account details</button>\n" +
                                    "<button class=\"w3-btn w3-hover-green w3-round-large\" onclick=\"location.href='/workwithbooks'\">Work with books</button>\n");
                UserEntity user = (UserEntity) request.getSession().getAttribute("user");
                List<String> roles = user.getRole();
                for (String role : roles) {
                    if ("ADMIN".equals(role)) out.println("<button class=\"w3-btn w3-hover-green w3-round-large\" " +
                                                                  "onclick=\"location.href='/admin'\">Admin page</button>");
                    if ("LIBRARIAN".equals(role) || "ADMIN".equals(role))
                        out.println("<button class=\"w3-btn w3-hover-green w3-round-large\" " +
                                                                  "onclick=\"location.href='/\"/reserved-books\"'\">Reserved books</button>");
                }
            }
        %>

    </div>
</div>

<div class="w3-container w3-padding">
    <%
        if (request.getSession().getAttribute("user") != null) {
            out.println("<button class=\"w3-btn w3-block w3-red w3-left-align\" onclick=\"location.href='/logout/exit'\">Logout</button>\n");
        }
    %>
</div>

</body>
</html>
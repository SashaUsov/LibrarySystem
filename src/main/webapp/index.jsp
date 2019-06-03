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
        %>
        <button class="w3-btn w3-hover-green w3-round-large" onclick="location.href='/userpage'">My account details</button>

        <%
            if (request.getSession().getAttribute("user") != null) {
                UserEntity user = (UserEntity) request.getSession().getAttribute("user");
                List<String> roles = user.getRole();
                for (String role : roles) {
                    if ("ADMIN".equals(role)) out.println("<button class=\"w3-btn w3-hover-green w3-round-large\" " +
                                                                  "onclick=\"location.href='/admin'\">Admin page</button>");
                }
            }
        %>

    </div>
</div>

<div class="w3-container w3-padding">
<%
    if (request.getSession().getAttribute("user") != null) {
        out.println("<div class=\"w3-container w3-grey w3-opacity w3-right-align w3-padding\">\n" +
                            "<button class=\"w3-btn w3-round-large\" onclick=\"location.href='/logout/exit'\">Logout</button>\n" +
                            "</div>");
    }
%>
</div>

</body>
</html>
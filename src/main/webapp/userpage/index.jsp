<%@ page import="com.servletProject.librarySystem.domen.UserEntity" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Library system</h1>
</div>

<div class="w3-container w3-padding">
    <%
        if (request != null) {
            UserEntity user = (UserEntity) request.getSession().getAttribute("user");
            out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                                "<span onclick=\"this.parentElement.style.display='none'\" " +
                                "class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                                "<h5> User " + user.getLastName() + " " + user.getFirstName() + " " +
                                " account details</h5>\n" +
                                "</div>");

            out.println("</ul>");


            Map<String, String> info = new HashMap<>();
            info.put("First name: ", user.getFirstName());
            info.put("Last name: ", user.getLastName());
            info.put("Nick name: ", user.getNickName());
            info.put("Email: ", user.getMail());
            info.put("Address: ", user.getAddress());


            final Set<String> userInfo = info.keySet();
                out.println("<ul class=\"w3-ul\">");
                for (String param : userInfo) {
                    out.println("<li class=\"w3-hover-sand\">" + param + info.get(param) + "</li>");
                }
                out.println("</ul>");

        } else out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n" +
                             "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                             "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey\">×</span>\n" +
                             "   <h5>Something went wrong</h5>\n" +
                             "</div>");
    %>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main</button>
</div>
</body>
</html>

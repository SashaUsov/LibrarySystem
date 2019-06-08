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
        if (request.getAttribute("nick_name") != null) {
            out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                                "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                                "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                                "   <h5>User '" + request.getAttribute("nick_name") + "' already registered!</h5>\n" +
                                "</div>");
        }
    %>
    <div class="w3-card-4">
        <div class="w3-container w3-center w3-green">
            <h2>Add new user role</h2>
        </div>

        <form id="submitform" action="/admin/action" method="post" class="w3-selection w3-light-grey w3-padding">
            <label>User id:
                <input type="text" name="user_id" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br/>
            </label>

            <label>User role:
                <input type="text" name="role" list="role_list" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br/>
            </label>

            <datalist id="role_list">
                <option>USER</option>
                <option>LIBRARIAN</option>
                <option>ADMIN</option>
            </datalist>

            <button type="submit"  class="w3-btn w3-green w3-round-large w3-margin-bottom">Submit</button>
        </form>
    </div>

    <div class="w3-card-4">
        <div class="w3-container w3-center w3-green">
            <h2>Remove user role</h2>
        </div>

        <form action="/admin/action" method="get" class="w3-selection w3-light-grey w3-padding">
            <label>User id:
                <input type="number" name="user_id" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br/>
            </label>

            <label>User role:
                <input type="text" name="role" list="role_list" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br/>
            </label>
            <button type="submit"  class="w3-btn w3-green w3-round-large w3-margin-bottom">Submit</button>
        </form>
    </div>

</div>

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

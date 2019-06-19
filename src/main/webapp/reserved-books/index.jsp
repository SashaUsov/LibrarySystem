<%@ page import="static com.oracle.jrockit.jfr.ContentType.Address" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>General actions</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Library system</h1>
</div>

<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
        <div>
            <form id="submitform" action="/reserve-data" method="get">
                <input type="hidden"><br/>
                <button type="submit" class="w3-btn w3-ripple w3-teal">All reserved books</button>
            </form>
        </div>
    </div>
    <div class="w3-bar w3-padding-large w3-padding-24">
        <div>
            <form id="submitform2" action="/reserve-data" method="post">
                <label>Reader email:
                    <input type="text" name="user_email"><br/>
                </label>
                <button type="submit" class="w3-btn w3-ripple w3-teal">Get orders by reader email</button>
            </form>
        </div>
    </div>
</div>
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

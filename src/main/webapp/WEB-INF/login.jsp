<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">
        <div class="w3-container w3-light-blue">
            <h2>User profile</h2>
        </div>
        <%
            Map<String, String> info = new HashMap<>();
            info.put("First name: ", user.getFirstName());
            info.put("Last name: ", user.getLastName());
            info.put("Nick name: ", user.getNickName());
            info.put("Email: ", user.getMail());
            info.put("Address: ", user.getAddress());


            final Set<String> userInfo = info.keySet();
            if (userInfo.size() != 0) {
                out.println("<ul class=\"w3-ul\">");
                for (String param : userInfo) {
                    out.println("<li class=\"w3-hover-sand\">" + param + info.get(param) + "</li>");
                }
                out.println("</ul>");

            } else out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n"
                                       +
                                       "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                                       "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey\">×</span>\n" +
                                       "   <h5>Something went wrong</h5>\n" +
                                       "</div>");
        %>
    </div>
</div>



out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
"   <span onclick=\"this.parentElement.style.display='none'\"\n" +
"   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
"   <h5>'" + user.getRole() + user.getFirstName() + " " +
    user.getLastName() + "' registration is successful!</h5>\n" +
"</div>");
out.println("</ul>");
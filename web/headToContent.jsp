<%@page import="model.StringData"%>
</head>

<body>
    <%

        StringData loggedOnCust = (StringData) session.getAttribute("cust"); // must type cast the object before use 
        String onOrOffLink = "";
        String onOrOffText = "";
        String strloggedInAs = "";
        if (loggedOnCust == null) { // means user is not logged in
            try {
                onOrOffLink = "logon.jsp";
                onOrOffText = "log on";
            } catch (Exception e) {
                out.println(" Exception was thrown: " + e.getMessage());
            }
        }

        if (loggedOnCust != null) { // means user is logged in
            try {
                onOrOffLink = "logoff.jsp";
                onOrOffText = "log off";
                strloggedInAs = "logged in as: " + loggedOnCust.username;
            } catch (Exception e) {
                out.println(" Exception was thrown: " + e.getMessage());
            }
        }

    %>
    <br>
    <div id="title">With The Grain Co-op</div>
    <br>
    <nav>
        <a href="./index.jsp">home</a>  |
        <a href="./users.jsp">users</a> |
        <a href="./other.jsp">products</a> |
        <a href="./assoc.jsp">purchases</a> |
        <a href="./search.jsp">search</a>  |
        <a href="./membersOnly.jsp">members</a>  |
        <a href="./contact.jsp">contact</a>  |
        <a href="<%=onOrOffLink%>"><%=onOrOffText%></a>
    </nav>
    <br>

    <div class = "loggedin">
        <%=strloggedInAs%>
    </div>

    <div id="content">

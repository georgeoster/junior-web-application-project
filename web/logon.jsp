<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="model.StringData"%>
<%@page import="model.Logon"%>
<%@page import="dbUtils.DbConn"%>

<jsp:include page="toHead.jsp" />
<jsp:include page="headToContent.jsp" />

<%
    DbConn dbc = new DbConn();

    String strusername = "";
    String strpassword = "";
    String errorMsg = "";
    String nopeMsg = "";

    if (request.getParameter("username") != null) {

        strusername = request.getParameter("username"); // extract user input from the URL 
        strpassword = request.getParameter("password"); // extract user input from the URL

        errorMsg = dbc.getErr();// Check to see if there was any error trying to connect to the database. 

        if (errorMsg.length() == 0) { // no error message, so database connection is OK

            StringData loggedOnCust = Logon.find(dbc, strusername, strpassword);

            if (loggedOnCust != null) {//if customer logged in successfully
                session.setAttribute("cust", loggedOnCust); // 1st parameter is name, 2nd parameter is value to put into session
                response.sendRedirect("index.jsp");
            }

            if (loggedOnCust == null) {
                nopeMsg = "wrong username or password";
            }
        }
    }

    dbc.close();
%>
<form action="logon.jsp" method="get" style = "text-align: center">
    <span style="color:red"><%=nopeMsg%></span><br/>
    username <input type="text" name="username" size="20" style="border:1px solid black" value="<%=strusername%>"/><br/> 
    password <input type="password" name="password" size="20" style="border:1px solid black" value="<%=strpassword%>"/><br/>
    <input type="submit" value="log in"/><br/>

    <%=errorMsg%>
</form>

<jsp:include page="postContent.jsp" />
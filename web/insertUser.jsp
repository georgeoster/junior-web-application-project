<%@page import="model.User.DbMods"%>
<%@page import="dbUtils.MakeSelectTag"%>
<%@page import="dbUtils.FormatUtils"%>
<%@page import="view.PurchaseView"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="dbUtils.DbConn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="toHead.jsp" />
<jsp:include page="headToContent.jsp" />


<%

    DbConn dbc = new DbConn();
    String strUserNameError = "";
    String strPasswordError = "";
    String roleIDError = "";

    String roleIDSelect = ""; //for persistence
    String roleIDsql = "SELECT roleName, roleID FROM siteRole ORDER BY roleID";
    int introleID = 0;

    roleIDSelect = MakeSelectTag.makeSelect(dbc, roleIDsql, "role", introleID, "role");

    // Declare all Strings and objects as they should be if it is first rendering
    model.User.StringData inputData = new model.User.StringData(); // all properties empty string, good for first rendering
    model.User.StringData errorMsgs = new model.User.StringData(); // all properties empty string, good for first rendering

    if (request.getParameter("username") != null) { // this is postback

        introleID = Integer.parseInt(request.getParameter("role"));
        dbc = new DbConn();
        roleIDSelect = MakeSelectTag.makeSelect(dbc, roleIDsql, "role", introleID, "role");

        // package up Customer String data
        inputData.username = request.getParameter("username");
        inputData.password = request.getParameter("password");
        inputData.memberSince = request.getParameter("memberSince");
        inputData.roleID = request.getParameter("role");

        if (inputData.username.length() < 1) {
            strUserNameError = "username is a required field";
        }
        if (inputData.password.length() < 1) {
            strPasswordError = "password is a required field";
        }
        if (Integer.parseInt(request.getParameter("role")) == 0) {
            roleIDError = "role is a required field";
        }

        errorMsgs.errorMsg = dbc.getErr();
        if (errorMsgs.errorMsg.length() == 0) { // DB connection is good
            errorMsgs = DbMods.insert(inputData, dbc); // errorMsgs will hold all validation messags
            if (errorMsgs.errorMsg.length() == 0) { // this is the form level error message
                // replace empty string with successful message
                errorMsgs.errorMsg = "Record successfully inserted !";
            }
        } // if db connection is good

        dbc.close(); // no database connection leaks !
    } // postback
%>
<br>
<form action="insertUser.jsp" method="get" style = "text-align: center">

    username       <input type="text" name="username" size="20" style="border:1px solid black" value="<%=inputData.username%>"/><%=strUserNameError%><br>
    password<input type="text" name="password" size="20" style="border:1px solid black" value="<%=inputData.password%>"/><%=strPasswordError%><br>
    role    <%=roleIDSelect%><%=roleIDError%><br>
    member since  <input type="text" name="memberSince" size="20" style="border:1px solid black"    value="<%=inputData.memberSince%>"/><br>


    <input type="submit" value="insert"><br/>

    <%=errorMsgs.errorMsg%>

</form>

<jsp:include page="postContent.jsp" />
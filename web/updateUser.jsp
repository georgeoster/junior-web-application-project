<%@page import="model.User.FinderOfCustomers"%>
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

    String strcustomerID = request.getParameter("customerID");
    String struserName = "";
    String strpassword = "";
    String strroleID = "";
    String roleIDSelect = "";
    String strmemberSince = "";

    String struserNameError = "";
    String strpasswordError = "";
    String strmemberSinceError = "";

    String successOrFail = "";

    model.User.StringData toUpdate = FinderOfCustomers.find(dbc, strcustomerID);

    struserName = toUpdate.username;
    strpassword = toUpdate.password;
    String roleIDsql = "SELECT roleName, roleID FROM siteRole ORDER BY roleID";
    int introleID = Integer.parseInt(toUpdate.roleID);
    roleIDSelect = MakeSelectTag.makeSelect(dbc, roleIDsql, "role", introleID, "role");
    strmemberSince = toUpdate.memberSince;

    if (request.getParameter("username") != null) { // this is postback

        struserName = request.getParameter("username");
        strpassword = request.getParameter("password");
        strroleID = request.getParameter("role");

        roleIDSelect = MakeSelectTag.makeSelect(dbc, roleIDsql, "role", Integer.parseInt(strroleID), "role");
        strmemberSince = request.getParameter("memberSince");

        model.User.StringData errorChecking = new model.User.StringData();
        model.User.StringData changes = new model.User.StringData();

        changes.customerID = strcustomerID;
        changes.username = struserName;
        changes.password = strpassword;
        changes.roleID = strroleID;
        changes.memberSince = strmemberSince;

        errorChecking = DbMods.validate(changes);

        if (errorChecking.username.length() > 0) {
            struserNameError = "username is a required field";
        }
        if (errorChecking.password.length() > 0) {
            strpasswordError = "password is a required field";
        }
        if (errorChecking.memberSince.length() > 0) {
            strmemberSinceError = "date made must be in yyyy-MM-dd format";
        }
        if (errorChecking.getCharacterCount() == 0) {
            successOrFail = "Record Successfully Updated";
            DbMods.update(changes, dbc);
        }

    }
    dbc.close();
%>
<br>
<form action="updateUser.jsp" method="get" style = "text-align: center">
    customer ID  <input type="text" name="customerID" size="20" style="border:1px solid black" value="<%=strcustomerID%>"/><br>
    username       <input type="text" name="username" size="20" style="border:1px solid black" value="<%=struserName%>"/><%=struserNameError%><br>
    password<input type="text" name="password" size="20" style="border:1px solid black" value="<%=strpassword%>"/><%=strpasswordError%><br>
    role    <%=roleIDSelect%><br>
    member since  <input type="text" name="memberSince" size="20" style="border:1px solid black"    value="<%=strmemberSince%>"/><%=strmemberSinceError%><br>


    <input type="submit" value="insert"><br/>

    <%=successOrFail%>

</form>

<jsp:include page="postContent.jsp" />
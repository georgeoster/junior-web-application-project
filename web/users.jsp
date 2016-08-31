<%@page import="view.WebUserView"%>
<%@page import="dbUtils.DbConn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="toHead.jsp" />
<jsp:include page="headToContent.jsp" />

<center>
    <%
        DbConn dbc = new DbConn();
        String msg = dbc.getErr(); // returns "" if connection is good, else error msg.
        String potentialError = "";
        if (msg.length() == 0) { // got open connection
            String ID = request.getParameter("customerID");
            if (ID != null && ID.length() != 0) {
                int i = model.User.DbMods.delete(dbc, ID);
                if (i == -1) {
                    potentialError = "customer # " + ID + " could not be deleted";
                }
            }
            msg = WebUserView.listAllUsers("table", dbc);
        }

        dbc.close();
    %>

    <a href ="./insertUser.jsp">insert user</a><br><br>

    <%=potentialError%><br>
    <% out.print(msg);%>

</center>

<div style ="visibility:hidden">
    <form name="deleteForm" action="users.jsp" method="get">
        <input type="text" name="customerID">
        <input type="submit"/>
    </form> 
</div>

<script language="Javascript" type="text/javascript">
    function deleteRow(ID) {
        if (confirm("Do you really want to delete customer #" + ID + "?")) {
            document.deleteForm.customerID.value = ID;
            document.deleteForm.submit();
        }
    }
</script>
<jsp:include page="postContent.jsp" />
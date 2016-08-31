<%@page import="view.PurchaseView"%>
<%@page import="view.ProductView"%>
<%@page import="dbUtils.DbConn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="toHead.jsp" />
<jsp:include page="headToContent.jsp" />


    <%
        DbConn dbc = new DbConn();
        String msg = dbc.getErr(); // returns "" if connection is good, else error msg.

        if (msg.length() == 0) { // got open connection
            String ID = request.getParameter("purchasedID");
            if (ID != null && ID.length() != 0) {
                model.Purchased.DbMods.delete(dbc, ID);
            }
            
            msg = PurchaseView.listAllPurchases("table", dbc);
        }
     
        dbc.close();
    %>
<center>
    <a href ="./insertAssoc.jsp">insert purchases</a><br><br>
    <% out.print(msg);%>
</center>

<div style ="visibility:hidden">
    <form name="deleteForm" action="assoc.jsp" method="get">
        <input type="text" name="purchasedID">
        <input type="submit"/>
    </form> 
</div>

<script language="Javascript" type="text/javascript">
    function deleteRow(ID) {
        if (confirm("Do you really want to delete purchase #" + ID + "?")) {
            document.deleteForm.purchasedID.value = ID;
            document.deleteForm.submit();
        }
    }
</script>



<jsp:include page="postContent.jsp" />
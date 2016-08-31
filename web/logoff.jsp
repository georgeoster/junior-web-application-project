<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% session.invalidate();
        try {
            response.sendRedirect("index.jsp");  
        } catch (Exception e) {
            System.out.println("**** Exception was thrown in logoff.jsp: " + e.getMessage());
        }
    %>
</html>

<%-- 
    Document   : menu
    Created on : 23-nov-2016, 12:00:43
    Author     : Ismael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.ServletContext" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%
    String contextPath = request.getContextPath();
    Cookie[] cookies = request.getCookies();
    boolean autorizado = false;
    for (int i = 0; cookies != null && i < cookies.length; i++) {
        Cookie cookie = cookies[i];
        if ("cookie".equals(cookie.getName())) {
            autorizado = true;
            break;
        }
    }

    if (!autorizado) {
        response.sendRedirect(contextPath + "/index.jsp");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%= contextPath%>/css/maqMenu.css">
        <title>Main Menu</title>
        <script type="text/javascript">
            function logout() {
                document.datacookie.action.value = 'logout'
                document.datacookie.submit();
            }
            function matches() {
                document.dataform.action.value = 'matches'
                document.dataform.submit();
            }
            function top() {
                document.dataform.action.value = 'top'
                document.dataform.submit();
            }
            function friends() {
                document.dataform.action.value = 'friends'
                document.dataform.submit();
            }
        </script>
    </head>
    <body>
        <h1>MAIN MENU</h1>
        <form name="dataform" action="<%=contextPath%>/Servlet_Nave" method="post">
            <input type="hidden" name="action" value="">
            
            <nav class="menu">
                <input type="checkbox" id="togglemenu" checked />
                <label for="togglemenu" class="togglemenu"></label>
                <ul>
                    <li><a href="<%= contextPath%>/lander/index.html">PLAY</a></li>
                </ul>
            </nav>	
            
            <section>
                <input type="button" value="PLAYED MATCHES" onclick="matches();">
                <input type="button" value="TOP 10" onclick="top();">
                <input type="button" value="ONLINE FRIENDS" onclick="friends();">
            </section>	
        </form>
        <br>
        <br>
        <form name="datacookie" action="<%=contextPath%>/Servlet_Cookie" method="post">
            <input type="hidden" name="action" value="">
            <input type="button" value="Logout" onclick="logout();">
        </form>
    </body>
</html>

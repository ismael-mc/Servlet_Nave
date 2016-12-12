<%-- 
    Document   : playedMatches
    Created on : 28-nov-2016, 10:46:19
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
        if ("errorcookie".equals(cookie.getName())) {
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
        <link rel="stylesheet" href="<%= contextPath%>/css/maqIndex.css">
        <title>Played Matches</title>
    </head>
    <body>
        <header>
            <h1>WELCOME</h1>
        </header>
        <section>
            <center>
                <table id="tabla">
                    <tr>
                        <td bgcolor="#FFFFFF">START DATE</td>
                        <td bgcolor="#FFFFFF">END DATE</td>
                    </tr>
                    <tr>
                        <td bgcolor="#FFFFFF"></td>
                        <td bgcolor="#FFFFFF"></td>
                    </tr>
                </table>
            </center>
        </section>
    </body>
</html>

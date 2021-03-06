<%-- 
    Document   : playedMatches
    Created on : 28-nov-2016, 10:46:19
    Author     : Ismael
--%>

<%@page import="model.MimcerUsers"%>
<%@page import="services.MimcerUsersJpaController"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.TypedQuery"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="model.MimcerGame"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="javax.servlet.ServletContext" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%
    String contextPath = request.getContextPath();
    session = request.getSession(true);
    String alias = (String) session.getAttribute("alias");
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

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Nave_JpaPU");
    EntityManager em = emf.createEntityManager();

    TypedQuery<MimcerGame> query = em.createNamedQuery("MimcerGame.findAll", MimcerGame.class);
    List<MimcerGame> fecha = query.getResultList();

    MimcerUsersJpaController search = new MimcerUsersJpaController(Persistence.createEntityManagerFactory("Nave_JpaPU"));
    List<MimcerUsers> mimcerusers = search.findMimcerUsersEntities();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%= contextPath%>/css/maqIndex.css">
        <title>Played Matches</title>
        <script type="text/javascript">
            function menu() {
                document.dataform.action.value = 'menu'
                document.dataform.submit();
            }
        </script>
    </head>
    <body>
        <header>
            <h1>WELCOME <%=alias%></h1>
        </header>
    <center>
        <table id="tabla">
            <tr><td bgcolor="#FFFFFF">START DATE</td><td bgcolor="#FFFFFF">END DATE</td></tr>
            <%for (MimcerGame mimcer : fecha) {
                    Date endDate = mimcer.getEnddate();
                    Date startDate = mimcer.getStartdate();%>
            <tr><td bgcolor="#FFFFFF"><%=startDate%></td><td bgcolor="#FFFFFF"><%=endDate%></td></tr>
                    <%}%>
        </table>
    </center>
    <br>
    <section>
        <form name="dataform" action="<%=contextPath%>/Servlet_Nave" method="post">
            <input type="hidden" name="action" value="">
            <input type="button" value="BACK MENU" onclick="menu();">
        </form>
    </section>
</body>
</html>

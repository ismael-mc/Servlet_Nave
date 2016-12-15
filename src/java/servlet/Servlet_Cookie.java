/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MimcerGame;
import model.MimcerUsers;
import services.MimcerGameJpaController;
import services.MimcerUsersJpaController;

/**
 *
 * @author Ismael
 */
public class Servlet_Cookie extends HttpServlet {

    public static final int SECONDS_PER_YEAR = 60 * 60;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        Date starttime = new Date(); //FECHA DE INICIO DE SESION
        Date endtime = new Date(); //FECHA DE FINAL DE SESION
        MimcerGame mimcerGame = new MimcerGame();
        HttpSession session = null;
        int contador = 0;
        try {
            if ("login".equalsIgnoreCase(action)) {
                //OBTENER LA HORA Y LA FECHA EN LA QUE SE HA INICIADO SESIÓN
                session = request.getSession(true);
                session.setAttribute("starttime", starttime);
                //CREAR COOKIES "LOG IN"
                String alias = request.getParameter("alias");
                String pass = request.getParameter("password");
                //RECOJO EL VALOR DEL ALIAS
                session.setAttribute("alias", alias);
                //SELECT BBDD
                MimcerUsersJpaController find = new MimcerUsersJpaController(Persistence.createEntityManagerFactory("Nave_JpaPU"));
                List<MimcerUsers> mimcerusers = find.findMimcerUsersEntities();

                //RECORRO LA BASE DE DATOS PARA ENCONTRAR ALLIAS Y PASS Y CREAR COOKIES
                for (MimcerUsers mimcerUsers : mimcerusers) {
                    if ((alias.equals(mimcerUsers.getAlias())) && (pass.equals(mimcerUsers.getPasswordu()))) {
                        Cookie b = getCookie(request, "cookie");
                        if (b == null) {
                            b = crearCookieB("cookie", 1);
                            //request.setAttribute("alias", alias);
                            contador++;
                        }
                        response.addCookie(b);
                        response.sendRedirect("jsp/menu.jsp");
                        /* RequestDispatcher ap = request.getRequestDispatcher("jsp/menu.jsp");
                        ap.forward(request, response);*/
                    } else {
                        RequestDispatcher z = request.getRequestDispatcher("jsp/error.jsp");
                        z.forward(request, response);
                    }
                }
            } else if ("logout".equalsIgnoreCase(action)) {
                //AÑADE EN LA BBDD FECHA DE INICIO Y FINAL DE PARTIDA CON EL ID DELL USUARIO
                session = request.getSession(true);
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("Nave_JpaPU");
                EntityManager em = emf.createEntityManager();

                TypedQuery<MimcerUsers> query = em.createNamedQuery("MimcerUsers.findByAlias", MimcerUsers.class);
                query.setParameter("alias", (String) session.getAttribute("alias"));
                List<MimcerUsers> ListaUsuarios = query.getResultList();
                mimcerGame.setIdUser(ListaUsuarios.get(0));
                mimcerGame.setStartdate((Date) session.getAttribute("starttime"));
                mimcerGame.setEnddate(endtime);
                mimcerGame.setMatches(contador);

                MimcerGameJpaController insert = new MimcerGameJpaController(Persistence.createEntityManagerFactory("Nave_JpaPU"));
                insert.create(mimcerGame);

                //ELIMINO LAS COOKIES DEL USUARIO
                Cookie b = new Cookie("cookie", "");
                b.setMaxAge(0);
                response.addCookie(b);

                RequestDispatcher z = request.getRequestDispatcher("index.jsp");
                z.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public static Cookie crearCookieB(String nombre, int valor) {
        Cookie b = new Cookie(nombre, new Integer(valor).toString());
        b.setMaxAge(SECONDS_PER_YEAR);
        return b;
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookieName.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookieName.equals(cookie.getName())) {
                return (cookie.getValue());
            }
        }
        return defaultValue;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

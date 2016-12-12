/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MimcerUsers;
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

        try {
            if ("login".equalsIgnoreCase(action)) {

                //OBTENER LA HORA Y LA FECHA EN LA QUE SE HA INICIADO SESIÓN
                /* Date date = new Date(); 
                DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                String dateTime = hourdateFormat.format(date); //Guardo los datos en un String
                Date timeDate = hourdateFormat.parse(dateTime); //Guardo los datos en un Date
                MimcerGame mimcerGame = new MimcerGame(); 
                mimcerGame.setStartdate(timeDate); //Obtengo el valor de timeDate para la columna startDate de la tabla mimcerGame
                MimcerGameJpaController insert = new MimcerGameJpaController(Persistence.createEntityManagerFactory("Nave_JpaPU"));
                insert.create(mimcerGame); //inserto la fecha en la tabla*/
                String alias = request.getParameter("alias");
                String pass = request.getParameter("password");

                MimcerUsersJpaController find = new MimcerUsersJpaController(Persistence.createEntityManagerFactory("Nave_JpaPU"));
                List<MimcerUsers> mimcerusers = find.findMimcerUsersEntities();

                for (MimcerUsers mimcerUsers : mimcerusers) {

                    if ((alias.equals(mimcerUsers.getAlias())) && (pass.equals(mimcerUsers.getPasswordu()))) {

                        Cookie b = getCookie(request, "errorcookie");

                        if (b == null) {
                            b = crearCookieB("errorcookie", 1);
                            
                            //request.setAttribute("alias", alias);
                        }

                        response.addCookie(b);

                        response.sendRedirect("jsp/menu.jsp");
                        /*RequestDispatcher ap = request.getRequestDispatcher("jsp/menu.jsp");
                        ap.forward(request, response);*/

                    } else {
                        RequestDispatcher z = request.getRequestDispatcher("jsp/error.jsp");
                        z.forward(request, response);
                    }
                }

            } else if ("logout".equalsIgnoreCase(action)) {

                //OBTENER LA HORA Y LA FECHA EN LA QUE SE HA FINALIZADO LA SESIÓN
                /*Date date = new Date(); 
                DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                String dateTime = hourdateFormat.format(date); //Guardo los datos en un String
                Date timeDate = hourdateFormat.parse(dateTime); //Guardo los datos en un Date
                MimcerGame mimcerGame = new MimcerGame(); 
                mimcerGame.setEnddate(timeDate); //Obtengo el valor de timeDate para la columna startDate de la tabla mimcerGame
                MimcerGameJpaController insert = new MimcerGameJpaController(Persistence.createEntityManagerFactory("Nave_JpaPU"));
                insert.create(mimcerGame); //inserto la fecha en la tabla*/
                Cookie b = new Cookie("errorcookie", "");

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

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
import java.util.Date;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MimcerGame;
import model.MimcerUsers;
import services.MimcerGameJpaController;
import services.MimcerUsersJpaController;

/**
 *
 * @author Ismael
 */
public class Servlet_Nave extends HttpServlet {

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
            if ("signup".equalsIgnoreCase(action)) {
                RequestDispatcher a = request.getRequestDispatcher("/jsp/Register.jsp");
                a.forward(request, response);
            } else if ("register".equalsIgnoreCase(action)) {
                String name = request.getParameter("name");
                String alias = request.getParameter("alias");
                String password = request.getParameter("password");

                MimcerUsers mimcerUsers = new MimcerUsers();
                mimcerUsers.setNameu(name);
                mimcerUsers.setAlias(alias);
                mimcerUsers.setPasswordu(password);

                MimcerUsersJpaController insert = new MimcerUsersJpaController(Persistence.createEntityManagerFactory("Nave_JpaPU"));
                insert.create(mimcerUsers);

                RequestDispatcher a = request.getRequestDispatcher("index.jsp");
                a.forward(request, response);
            } else if ("retry".equalsIgnoreCase(action)) {
                RequestDispatcher a = request.getRequestDispatcher("index.jsp");
                a.forward(request, response);
            } else if ("play".equalsIgnoreCase(action)) {
                RequestDispatcher a = request.getRequestDispatcher("lander/index.html");
                a.forward(request, response);
            } else if ("matches".equalsIgnoreCase(action)) {
                RequestDispatcher a = request.getRequestDispatcher("jsp/playedMatches.jsp");
                a.forward(request, response);
            } else if ("top".equalsIgnoreCase(action)) {
                RequestDispatcher a = request.getRequestDispatcher("jsp/topTen.jsp");
                a.forward(request, response);
            } else if ("friends".equalsIgnoreCase(action)) {
                RequestDispatcher a = request.getRequestDispatcher("jsp/friends.jsp");
                a.forward(request, response);
            } else if ("login".equalsIgnoreCase(action)) {
                //OBTENER LA HORA Y LA FECHA EN LA QUE SE HA INICIADO SESIÓN
                /* Date date = new Date(); 
                DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                String dateTime = hourdateFormat.format(date); //Guardo los datos en un String
                Date timeDate = hourdateFormat.parse(dateTime); //Guardo los datos en un Date
                MimcerGame mimcerGame = new MimcerGame(); 
                mimcerGame.setStartdate(timeDate); //Obtengo el valor de timeDate para la columna startDate de la tabla mimcerGame
                MimcerGameJpaController insert = new MimcerGameJpaController(Persistence.createEntityManagerFactory("Nave_JpaPU"));
                insert.create(mimcerGame); //inserto la fecha en la tabla*/

                //SI EL LOGIN ES CORRECTO LLAMO AL MENU
                RequestDispatcher a = request.getRequestDispatcher("jsp/menu.jsp");
                a.forward(request, response);
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

                //REDIRECCIONO EL MENU AL LOGIN
                RequestDispatcher a = request.getRequestDispatcher("index.jsp");
                a.forward(request, response);
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

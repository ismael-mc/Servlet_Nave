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
        //SEGUN EL VALOR QUE RECOJO DEL HIDDEN REALIZO O EL REGISTRI DE USUARIO O LAS OPCIONES DEL MENU
        String action = request.getParameter("action");

        try {
            if ("signup".equalsIgnoreCase(action)) {
                RequestDispatcher a = request.getRequestDispatcher("/jsp/Register.jsp");
                a.forward(request, response);
            } else if ("register".equalsIgnoreCase(action)) {
                //RECOJO LOS VALORES DE LOS CAMPOS
                String name = request.getParameter("name");
                String alias = request.getParameter("alias");
                String password = request.getParameter("password");
                //ESTABLEZCO LOS VALORES CON LAS COLUMNAS
                MimcerUsers mimcerUsers = new MimcerUsers();
                mimcerUsers.setNameu(name);
                mimcerUsers.setAlias(alias);
                mimcerUsers.setPasswordu(password);
                //LOS AÑADO A LA BBDD
                MimcerUsersJpaController insert = new MimcerUsersJpaController(Persistence.createEntityManagerFactory("Nave_JpaPU"));
                insert.create(mimcerUsers);

                RequestDispatcher a = request.getRequestDispatcher("index.jsp");
                a.forward(request, response);
            } else if ("retry".equalsIgnoreCase(action)) {
                RequestDispatcher a = request.getRequestDispatcher("index.jsp");
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
            }else if ("menu".equalsIgnoreCase(action)){
                RequestDispatcher a = request.getRequestDispatcher("jsp/menu.jsp");
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

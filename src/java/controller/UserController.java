/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author HP
 */
public class UserController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();
        List<User> users = (List<User>) session.getAttribute("userList");
        if (users == null) {
            users = userDAO.listAll();
        }
        
        session.setAttribute("userList", users);
        request.getRequestDispatcher("userPage.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        List<User> users = null;
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "search":
                users = searchUser(request, response);
                break;
            case "insert":
                users = insertUser(request, response);
                break;
            case "update":
                users = updateUser(request, response);
                break;
            case "remove":
                users = deleteUser(request, response);
                break;
            default:
                throw new AssertionError();
        }
        HttpSession session = request.getSession();
        session.setAttribute("userList", users);
        response.sendRedirect("UserController");
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

    private List<User> searchUser(HttpServletRequest request, HttpServletResponse response) {
        //đã tìm thấy search box và lấy tên ra
        
        String name = request.getParameter("searchBox");
        //debug cho bọn bay xem có lấy được tên để search hay không
        if (name != null) {
            //nếu tìm thấy thì trẻ về tên của những user
            System.out.println("found search box with name" + name);
        }
        else {
            //còn không thì trả về tên rỗng
            name = "";
        }
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.searchByName(name);
        if (userList != null) {
            //debug xem có tìm thấy user nào hay không
            //có thì trẻ về userList
            return userList;
        }
        //không thì trả về null
        return null;
    }

    private List<User> insertUser(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private List<User> updateUser(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private List<User> deleteUser(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

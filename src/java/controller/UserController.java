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
        try {
            UserDAO userDAO = new UserDAO();
            HttpSession session = request.getSession();
            List<User> users = (List<User>) session.getAttribute("userList");
            if (users == null) {
                users = userDAO.listAll();
            }
            session.setAttribute("userList", users);
            request.getRequestDispatcher("userPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        UserDAO userDAO = new UserDAO();
        String action = request.getParameter("action");
        List<User> users;
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
                users = userDAO.listAll();
                break;
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
        } else {
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
        return new ArrayList<>();
    }

    private List<User> insertUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
//khai báo lớp UserDAO
        UserDAO userDAO = new UserDAO();
        //lấy userID từ form
        String userID = request.getParameter("userIDInsert");
        //check userID có bị trùng trong danh sách hay không
        if (isDuplicateUserID(userID)) {
            //nếu trùng thì gửi lỗi
            request.getSession().setAttribute("msgError", "Duplicated User ID");
            //trả về danh sách ban đầu
            return userDAO.listAll();
        }
        //lấy full name từ form
        String fullName = request.getParameter("fullNameInsert");
        //lấy role ID từ form
        String roleID = request.getParameter("roleIDInsert");
        //check xem role id có đúng là ad hay nv
        if (isWrongRole(roleID)) {
            //sai thì gửi lỗi đến trang userPage.jsp
            request.getSession().setAttribute("msgError", "Role ID must be AD or NV");
            //trả về danh sách ban đầu
            return userDAO.listAll();
        }
        //lấy password từ form
        String password = request.getParameter("passwordInsert");
        //tạo 1 đối tượng user
        User user = new User(userID, fullName, roleID, password);
        //gọi hàm insert và truyền đối tượng mới tại vô
        userDAO.insertUser(user);
        //xóa sesstion thông báo lỗi khi hoàn thành
        session.removeAttribute("msgError");
        //refresh lại list
        return userDAO.listAll();
    }

    private List<User> updateUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        String userID = request.getParameter("userID");
        String fullName = request.getParameter("fullName");
        String roleID = request.getParameter("roleID");
        if (isWrongRole(roleID)) {
            request.getSession().setAttribute("msgError", "Role ID must be AD or NV");
            return userDAO.listAll();
        }
        String password = request.getParameter("password");

        if (userID == null || userID.isEmpty()) {
            session.setAttribute("msgError", "User ID is missing");
            return userDAO.listAll();
        }
        User user = new User();
        user.setFullName(fullName);
        user.setPassword(password);
        user.setRoleID(roleID);
        user.setUserID(userID);
        userDAO.updateUser(user);
        //xóa sesstion thông báo lỗi khi hoàn thành
        session.removeAttribute("msgError");
        return userDAO.listAll();
    }

    private List<User> deleteUser(HttpServletRequest request, HttpServletResponse response) {
        String userID = request.getParameter("userID");
        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser(userID);
        return userDAO.listAll();
    }

    private boolean isDuplicateUserID(String userID) {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.listAll();
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                return true;
            }
        }
        return false;
    }

    private boolean isWrongRole(String roleID) {
        if (roleID.equalsIgnoreCase("ad") || roleID.equalsIgnoreCase("nv")) {
            return false;
        }
        return true;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.UserDAO;
import java.io.IOException;
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
            case "searchUser":
                users = searchUser(request, response);
                break;
            case "insertUser":
                users = insertUser(request, response);
                break;
            case "updateUser":
                users = updateUser(request, response);
                break;
            case "removeUser":
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private List<User> searchUser(HttpServletRequest request, HttpServletResponse response) {
        //đã tìm thấy search box và lấy tên ra

        String name = request.getParameter("searchBox");
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.searchByName(name);
        if (userList != null) {
            return userList;
        }
        return null;
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

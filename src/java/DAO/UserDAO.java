/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBUtils.utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author HP
 */
public class UserDAO extends utils {

    public User login(String userID, String password)
            throws ClassNotFoundException, SQLException {
        System.out.println("UserDAO.login(): userID=" + userID + ", password=" + password);

        String sql = "SELECT fullName, roleID FROM tblUsers WHERE userID = ? AND password = ?";
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, password);
            try ( ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    System.out.println("Login successful for " + userID + ", fullName=" + fullName + ", roleID=" + roleID);
                    return new User(userID, fullName, roleID, "***");
                } else {
                    System.out.println("Login failed: no matching record");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
        return null;
    }

    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT [userID]\n"
                + "      ,[fullName]\n"
                + "      ,[roleID]\n"
                + "      ,[password]\n"
                + "  FROM [dbo].[tblUsers]";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                String fullName = resultSet.getString("fullName");
                String roleID = resultSet.getString("roleID");
                String password = resultSet.getString("password");
                users.add(new User(userID, fullName, roleID, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
        return users;
    }

    public void insertUser(User user) {
        String query = "INSERT INTO [dbo].[tblUsers]\n"
                + "           ([userID]\n"
                + "           ,[fullName]\n"
                + "           ,[roleID]\n"
                + "           ,[password])\n"
                + "     VALUES (?,?,?,?)";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, user.getUserID());
            preparedStatement.setObject(2, user.getFullName());
            preparedStatement.setObject(3, user.getRoleID());
            preparedStatement.setObject(4, user.getPassword());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public List<User> searchByName(String name) {
        List<User> users = new ArrayList<>();
        String query = "SELECT [userID]\n"
                + "      ,[roleID]\n"
                + "      ,[password]\n"
                + "  FROM [dbo].[tblUsers]\n"
                + "  WHERE [fullName] LIKE ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, "%" + name + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                String roleID = resultSet.getString("roleID");
                String password = resultSet.getString("password");
                users.add(new User(userID, name, roleID, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
        return users;
    }

    public void deleteUser(User user) {
        String sqlStatement = "DELETE FROM [dbo].[tblUsers]\n"
                + "      WHERE [userID] = ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setObject(1, user.getUserID());
            preparedStatement.executeUpdate();
            System.out.println("Delete user" + user.getFullName() + "sucessfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void updateUser(User user) {
        String sqlStatement = "UPDATE [dbo].[tblUsers]\n"
                + "   SET [fullName] = ?\n"
                + "      ,[roleID] = ?\n"
                + "      ,[password] = ?\n"
                + " WHERE [userID] = ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setObject(1, user.getFullName());
            preparedStatement.setObject(2, user.getRoleID());
            preparedStatement.setObject(3, user.getPassword());
            preparedStatement.setObject(4, user.getUserID());
            preparedStatement.executeLargeUpdate();
        } catch (Exception e) {
        }
        closeConnection();
    }
}

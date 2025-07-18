<%-- 
    Document   : userPage
    Created on : Jun 3, 2025, 11:05:55 AM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- CHỗ để search user -->
        <div class="searchZone">
            <h2>Search User</h2>
            <!-- form để search -->

            <form action="UserController?action=searchUser" method="POST">
                <!-- chỗ nhập tên để search -->
                <input type="text" name="searchBox" placeholder="Enter user name">
                <!-- nút submit -->
                <button type="submit" name="searchButton" value="SearchUser">Search User</button>
            </form>
        </div>
        <button onclick="addUser()" class="addButton">Add</button>
        <!--form để insert user-->
        <form action="UserController?action=insertUser" id="form-insert" style="display: none" class="form-container" method="post">
            <h2>Insert new user</h2>
            <table>
                <tr>
                    <td>User ID</td>
                    <td>
                        <input type="text" name="userIDInsert" required>
                    </td>
                </tr>
                <tr>
                    <td>Full Name</td>
                    <td>
                        <input type="text" name="fullNameInsert" required>
                    </td>
                </tr>
                <tr>
                    <td>Role ID</td>
                    <td>
                        <input type="text" name="roleIDInsert" required>
                    </td>
                </tr>
                <tr>
                    <td>Pasword</td>
                    <td><input type="password" name="passwordInsert" required></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <button type="submit" name="submitForm">Add User</button>
                    </td>
                </tr>
            </table>
        </form>
        <!-- List user -->
        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>User ID</th>
                    <th>Full Name</th>
                    <th>Role ID</th>
                    <th>Password</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${userList}" var="user" varStatus="record">
                    <tr>
                <form action="UserController" method="POST">
                    <!-- No: dùng để đến số dùng -->
                    <td>
                        <input type="text" name="No" value="${record.count}" disabled>
                    </td>
                    <%--userID này để delete hoặc update--%>
                    <td>
                        <input type="hidden" name="userID" value="${user.userID}" />
                        ${user.userID}
                    </td>
                    <%--Hiển thị userID--%>
                    <td>
                        <input type="text" value="${user.userID}" disabled/>
                    </td>
                    <%--Hiển thị full name--%>
                    <td>
                        <input type="text"  name="fullName"   value="${user.fullName}" required/>
                    </td>
                    <%--Hiển thị role--%>
                    <td>
                        <input type="text"  name="roleID" value="${user.roleID}" required/>
                    </td>
                    <%--hiển thị password--%>
                    <td>
                        <input type="text"  name="password"  value="${user.password}" required/>
                    </td>
                    <%--Hành độngc ảu người dùng--%>
                    <td class="actions">
                        <button type="submit" name="action" value="updateUser">Update</button>
                        <button type="submit" name="action" value="removeUser">Delete</button>
                    </td>
                </form>
            </tr>
        </c:forEach>



    </tbody>
</table>
<script>
    //hàm này để dùng hieenrthij form
    function displayForm() {
        //tìm cái form có id là form-insert
        let form = document.querySelector("#form-insert");
        //nếu cái form đó ko display thì đẻ nó display
        if (form.style.display === 'none') {
            form.style.display = 'block';
        } else {
            //không thì ngược lại
            form.style.display = 'none';
        }
    }
    //khi bấm nút này để hiển thị ra form
    function addUser() {
        displayForm();
    }
</script>
</body>

</html>

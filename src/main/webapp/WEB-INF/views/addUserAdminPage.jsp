<%@ page import="com.trots.periodacals.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.LinkedHashSet" %>
<%@ page import="com.trots.periodacals.dao.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AddUser</title>
</head>
<body>
<div align="center">
    <h1>AddUser</h1>
    <form action="<%= request.getContextPath() %>/addUser" method="post">
        <table style="with: 80%">
            <tr>
                <td>Username</td>
                <td><input type="text" name="username"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="email" name="email"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password"/></td>
            </tr>
            <tr>
                <td>Surname</td>
                <td><input type="text" name="surname"/></td>
            </tr>
            <tr>
                <td>Name</td>
                <td><input type="text" name="name"/></td>
            </tr>
            <tr>
                <td>Telephone</td>
                <td><input type="text" name="telephone"/></td>
            </tr>
            <tr>
                <td>Role</td>
                <td><select id="inputState" class="form-control" name="nameOfRole" required>
                    <option selected disabled>Choose Category</option>
                    <c:forEach var="roleList" items="${ROLE_LIST}">
                        <option value="${roleList.toString()}">${roleList.toString()}</option>
                    </c:forEach>
                </select>
                </td>
            </tr>
        </table>
    </form>
</div>

<input type="submit" value="Submit"/>
</form>
</div>
<h3>
    Book Information From Database</h3>
<table class="table">
    <thead class="bg-light">
    <tr>
        <th scope="col">Username</th>
        <th scope="col">Email</th>
        <th scope="col">Name</th>
        <th scope="col">Surname</th>
        <th scope="col">Role</th>
        <th scope="col">Telephone</th>
        <th scope="col">Balance</th>
        <th scope="col">BanStatus</th>
        <th scope="col">Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="tempBook" items="${USERS_LIST}">
        <tr>
            <td>${tempBook.username }</td>
            <td>${tempBook.email }</td>
            <td>${tempBook.name }</td>
            <td>${tempBook.surname}</td>
            <td>${tempBook.role}</td>
            <td>${tempBook.telephone}</td>
            <td>${tempBook.balance}</td>
            <td>${tempBook.banStatus}</td>
            <td><a href="/editUser?id=${tempBook.id }">Edit</a>
                <a href="DeleteBookServlet?id=${tempBook.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>

</table>
</body>
</html>


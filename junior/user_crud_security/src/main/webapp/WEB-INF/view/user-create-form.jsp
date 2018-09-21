<%@ page import="ru.job4j.servlets.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of users</title>
</head>
<body>
<form action="/" method="post">
    ID: <input type="text" name="id"    value = ${id}><br>
    Name: <input type="text" name="name"  value = ${name}><br>
    Email: <input type="text" name="email" value = ${email}><br>
    Login: <input type="text" name="login" value = ${login} readonly><br>
    Password: <input type="password" name="password" value = ${password}><br>
    Role: <input type="role" name="role" value = ${role} readonly><br>
    <c:if test="${role == 'admin'}">
        <c:forEach var="item" items="${roles}">
            <option>${item}</option>
        </c:forEach>
    </c:if>
    <button type="submit">Save</button>
</form>
</body>
</html>


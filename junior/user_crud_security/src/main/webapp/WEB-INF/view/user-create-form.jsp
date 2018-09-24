<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of users</title>
</head>
<body>
<form action="/" method="post">
    ID: <input type="text" name="id"    value = "${id}"><br>
    Name: <input type="text" name="name"  value = "${name}"><br>
    Email: <input type="text" name="email" value = "${email}"><br>
    Login: <input type="text" name="login" value = "${login}"><br>
    Password: <input type="password" name="password" value = "${password}"><br>
    <c:set var="r" value="${role}"/>
    <c:if test="${r == 'admin'}">
        <select name='role'>
            <option value="${selected}" selected>${selected}</option>
            <c:forEach items="${roles}" var="role">
                <c:if test="${role != selected}">
                    <option value="${role}">${role}</option>
                </c:if>
            </c:forEach>
        </select>
    </c:if>
    <button type="submit">Save</button>
</form>
</body>
</html>


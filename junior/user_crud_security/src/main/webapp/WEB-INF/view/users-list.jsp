<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of users</title>
</head>
<body>

<table border="1">
    <c:forEach var="u" items="${users}">
        <tr style="border-bottom: 1px solid #000;">
            <td>
                User[<c:out value="${u.getId()}"/>]:<br>
                Name: <c:out value="${u.getName()}"/><br>
                Login: <c:out value="${u.getLogin()}"/><br>
                Email: <c:out value="${u.getEmail()}"/><br>
                Password: <c:out value="${u.getPassword()}"/><br>
                Role: <c:out value="${u.getRole().getName()}"/><br>
                Creation Date: <c:out value="${u.getFormattedCreationDate()}"/><br>
            </td>
            <td>
                <form action="/edit" method="get">
                    <input type="hidden" name="id" value="<c:out value="${u.getId()}"/>">
                    <button type="submit">Edit</button>
                </form>
            </td>
            <td>
                <form action="/" method="post">
                    <input type="hidden" name="id" value="<c:out value="${u.getId()}"/>">
                    <input type="hidden" name="operation" value="delete"/>
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="/create" method="get">
    <button type="submit">Create user</button>
</form>
</body>
</html>


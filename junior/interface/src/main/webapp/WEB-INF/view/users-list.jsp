<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of users</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<table border="1" class="table table-hover">
    <c:forEach items = "${users}" var = "user">
        <tr style="border-bottom: 1px solid #000;">
            <td>
                User[<c:out value="${user.getId()}"/>]:<br>
                Name: <c:out value="${user.getName()}"/><br>
                Login: <c:out value="${user.getLogin()}"/><br>
                Email: <c:out value="${user.getEmail()}"/><br>
                Creation Date: <c:out value="${user.getFormattedCreationDate()}"/><br>
            </td>
            <td>
                <form action="/edit" method="get">
                    <input type="hidden" name="id" value="<c:out value="${user.getId()}"/>"/>
                    <button type="submit">Edit</button>
                </form>
            </td>
            <td>
                <form action="/" method="post">
                    <input type="hidden" name="id" value="<c:out value="${user.getId()}"/>"/>
                    <input type="hidden" name="operation" value="delete"/>
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>


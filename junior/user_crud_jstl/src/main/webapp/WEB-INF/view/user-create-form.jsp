<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of users</title>
</head>
<body>
<form action="/" method="post">
    ID: <input type="text" name="id" value = <c:out value = "${id}"/>><br>
    Name: <input type="text" name="name" value = <c:out value = "${name}"/>><br>
    Email: <input type="text" name="email" value = <c:out value = "${email}"/>><br>
    Login: <input type="text" name="login" value = <c:out value = "${login}"/>><br>
    <button type="submit">Save</button>
</form>
</body>
</html>



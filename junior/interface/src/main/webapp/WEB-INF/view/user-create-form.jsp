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

<script type="text/javascript">
    function onChange() {
        window.location.reload(true);
    }
</script>

<form action="/" method="post">
    ID: <input type="text" name="id" value = <c:out value = "${id}"/>><br>
    Name: <input type="text" name="name" value = <c:out value = "${name}"/>><br>
    Email: <input type="text" name="email" value = <c:out value = "${email}"/>><br>
    Login: <input type="text" name="login" value = <c:out value = "${login}"/>><br>
    Country:
    <select id="sel" onchange="onChange()">
        <c:forEach var="country" items="${countries}">
            <option value=${country}>${country}</option>
        </c:forEach>
    </select>
    <br>
    City: <input type="text" name="city" value = <c:out value = "${city}"/>><br>
    <button type="submit">Save</button>
</form>
</body>
</html>



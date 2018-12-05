<%@ page import="ru.job4j.servlets.persistent.MemoryStore" %>
<%@ page import="ru.job4j.servlets.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of users</title>
</head>
<body>
<table border="1">
<% for (User u: MemoryStore.getInstance().findAll()) { %>
<tr style="border-bottom: 1px solid #000;">
    <td>
        User[<%=u.getId()%>]:<br>
        Name: <%=u.getName() %><br>
        Login: <%=u.getLogin() %><br>
        Email: <%=u.getEmail() %><br>
        Creation Date: <%=u.getFormattedCreationDate() %><br>
    </td>
    <td>
        <form action="/edit" method="get">
            <input type="hidden" name="id" value="<%=u.getId()%>"/>
            <button type="submit">Edit</button>
        </form>
    </td>
    <td>
        <form action="/" method="post">
            <input type="hidden" name="id" value="<%=u.getId()%>"/>
            <input type="hidden" name="operation" value="delete"/>
            <button type="submit">Delete</button>
        </form>
    </td>
</tr>
<% } %>
</table>
</body>
</html>


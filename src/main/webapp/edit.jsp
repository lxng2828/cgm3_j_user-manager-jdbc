<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<a href="<%= request.getContextPath() %>/user-manager">User Manager</a>
<form method="post" action="<%= request.getContextPath() %>/user-manager">
    <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
    <input type="text" name="name" placeholder="Name"/>
    <input type="text" name="email" placeholder="Email"/>
    <input type="text" name="country" placeholder="Country"/>
    <button type="submit" name="action" value="edit">Edit</button>
</form>
</body>
</html>

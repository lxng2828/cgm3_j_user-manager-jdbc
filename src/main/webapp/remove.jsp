<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Remove</title>
</head>
<body>
<a href="<%= request.getContextPath() %>/user-manager">User Manager</a>
<form method="post" action="<%= request.getContextPath() %>/user-manager">
    <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
    <h1>Are you sure?</h1>
    <button type="submit" name="action" value="remove">Delete</button>
</form>
</body>
</html>

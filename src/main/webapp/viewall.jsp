<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.codegym.app.usermanagerjdbc.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Manager JDBC</title>
</head>
<body>
<h1>User Manager JDBC</h1>
<h3>User List</h3>

<div>
    <form action="<%=request.getContextPath()%>/user-manager" method="get">
        <input type="text" name="searchCountry" placeholder="Enter country name"
               value="<%=request.getParameter("searchCountry") != null ? request.getParameter("searchCountry") : ""%>">
        <input type="hidden" name="action" value="search">
        <button type="submit">Search</button>
    </form>

    <form action="<%=request.getContextPath()%>/user-manager" method="get">
        <input type="hidden" name="action" value="sort">
        <input type="hidden" name="sortBy" value="name">
        <button type="submit">Sort by name</button>
    </form>

    <a href="<%=request.getContextPath()%>/user-manager">ViewAll</a>
</div>

<h5><a href="<%=request.getContextPath()%>/user-manager?action=add">Add</a></h5>

<%
    List<User> userList = (List<User>) request.getAttribute("userList");
    if (userList != null && !userList.isEmpty()) {
%>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Country</th>
        <th></th>
        <th></th>
    </tr>
    <%
        for (User user : userList) {
    %>
    <tr>
        <td><%=user.getId()%>
        </td>
        <td><%=user.getName()%>
        </td>
        <td><%=user.getEmail()%>
        </td>
        <td><%=user.getCountry()%>
        </td>
        <td>
            <h5>
                <a href="<%=request.getContextPath()%>/user-manager?action=edit&id=<%=user.getId()%>">Edit</a>
            </h5>
        </td>
        <td>
            <h5>
                <a href="<%=request.getContextPath()%>/user-manager?action=remove&id=<%=user.getId()%>">Remove</a>
            </h5>
        </td>
    </tr>
    <%
        }
    %>
</table>
<%
} else if (userList == null) {
%>
<p>Failed to load user list. There may be an error on the server.</p>
<%
} else {
%>
<p>No users in the list.</p>
<%
    }
%>
</body>
</html>
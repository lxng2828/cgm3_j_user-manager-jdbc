<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm người dùng mới</title>
</head>
<body>
<a href="<%= request.getContextPath() %>/user-manager" class="back-link">← Quay lại danh sách</a>
<h2>Thêm người dùng mới</h2>

<%-- Hiển thị thông báo lỗi nếu có --%>
<% if (request.getSession().getAttribute("error") != null) { %>
    <div style="color: red; margin-bottom: 15px;">
        <%= request.getSession().getAttribute("error") %>
    </div>
    <% request.getSession().removeAttribute("error"); %>
<% } %>

<form action="<%= request.getContextPath() %>/user-manager" method="post">
    <input type="hidden" name="action" value="add">
    
    <div class="form-group">
        <label for="name">Tên:</label>
        <input type="text" id="name" name="name" placeholder="Nhập tên" required>
    </div>
    
    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" placeholder="Nhập email" required>
    </div>
    
    <div class="form-group">
        <label for="country">Quốc gia:</label>
        <input type="text" id="country" name="country" placeholder="Nhập quốc gia" required>
    </div>
    
    <div class="permissions">
        <h4>Quyền hạn:</h4>
        <div class="permission-item">
            <input type="checkbox" id="perm1" name="permissions" value="1">
            <label for="perm1" style="width: auto;">Xem danh sách người dùng</label>
        </div>
        <div class="permission-item">
            <input type="checkbox" id="perm2" name="permissions" value="2">
            <label for="perm2" style="width: auto;">Thêm người dùng</label>
        </div>
        <div class="permission-item">
            <input type="checkbox" id="perm3" name="permissions" value="3">
            <label for="perm3" style="width: auto;">Sửa thông tin</label>
        </div>
        <div class="permission-item">
            <input type="checkbox" id="perm4" name="permissions" value="4">
            <label for="perm4" style="width: auto;">Xóa người dùng</label>
        </div>
        <div class="permission-item">
            <input type="checkbox" id="perm5" name="permissions" value="5">
            <label for="perm5" style="width: auto;">Quản lý quyền hạn</label>
        </div>
    </div>
    
    <div class="form-group">
        <input type="submit" value="Thêm mới" class="btn">
    </div>
</form>
</body>
</html>

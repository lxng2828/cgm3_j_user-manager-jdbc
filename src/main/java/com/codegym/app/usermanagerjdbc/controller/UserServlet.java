package com.codegym.app.usermanagerjdbc.controller;

import com.codegym.app.usermanagerjdbc.dao.IUserDAO;
import com.codegym.app.usermanagerjdbc.dao.UserDAO;
import com.codegym.app.usermanagerjdbc.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user-manager")
public class UserServlet extends HttpServlet {

    static final IUserDAO UserDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                addUserForm(req, resp);
                break;
            case "edit":
                editUserForm(req, resp);
                break;
            case "remove":
                removeUserForm(req, resp);
                break;
            case "search":
                searchUsersByCountry(req, resp);
                break;
            case "sort":
                sortUsersByName(req, resp);
                break;
            default:
                viewAll(req, resp);
                break;
        }
    }

    private void viewAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> userList = UserDAO.getAllUsers();
            req.setAttribute("userList", userList);
            req.getRequestDispatcher("viewall.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
    }


    private void searchUsersByCountry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String country = req.getParameter("searchCountry");
        if (country == null || country.trim().isEmpty()) {
            viewAll(req, resp);
            return;
        }

        try {
            List<User> userList = UserDAO.searchUsersByCountry(country);
            req.setAttribute("userList", userList);
            req.setAttribute("searchCountry", country);
            req.getRequestDispatcher("viewall.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
    }


    private void sortUsersByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> userList = UserDAO.sortUsersByName();
            req.setAttribute("userList", userList);
            req.getRequestDispatcher("viewall.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
    }

    private void addUserForm(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("add.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void editUserForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            User user = UserDAO.getUserById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher("edit.jsp").forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeUserForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            User user = UserDAO.getUserById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher("remove.jsp").forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                addUser(req, resp);
                break;
            case "edit":
                editUser(req, resp);
                break;
            case "remove":
                removeUser(req, resp);
                break;
            default:
                break;
        }
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        String[] permissions = req.getParameterValues("permissions");

        if (name != null && !name.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                country != null && !country.trim().isEmpty()) {
            
            // Tạo đối tượng User mới
            User user = new User(name, email, country);
            
            // Chuyển đổi mảng String permissions thành List<Integer>
            List<Integer> permissionList = new ArrayList<>();
            if (permissions != null && permissions.length > 0) {
                for (String permId : permissions) {
                    try {
                        permissionList.add(Integer.parseInt(permId));
                    } catch (NumberFormatException e) {
                        // Bỏ qua nếu có lỗi chuyển đổi
                        e.printStackTrace();
                    }
                }
            }
            
            // Thêm người dùng với các quyền đã chọn
            UserDAO.addUserTransaction(user, permissionList);
            
            // Thông báo thành công
            req.getSession().setAttribute("message", "Thêm người dùng thành công!");
        } else {
            req.getSession().setAttribute("error", "Vui lòng điền đầy đủ thông tin!");
        }
        
        resp.sendRedirect(req.getContextPath() + "/user-manager");
    }

    private void editUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");

        try {
            if (name != null && !name.trim().isEmpty() &&
                    email != null && !email.trim().isEmpty() &&
                    country != null && !country.trim().isEmpty()) {
                User user = new User(name, email, country);
                UserDAO.editUser(id, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/user-manager");
    }

    private void removeUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            UserDAO.removeUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/user-manager");
    }

}

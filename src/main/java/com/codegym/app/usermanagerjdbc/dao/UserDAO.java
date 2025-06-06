package com.codegym.app.usermanagerjdbc.dao;

import com.codegym.app.usermanagerjdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "haha123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Can not find MySQL JDBC Driver! Please check your classpath.", e);
        }

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    @Override
    public boolean addUser(User user) throws SQLException {
        String sql = "INSERT INTO users(name, email, country) VALUES(?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getCountry());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    @Override
    public boolean editUser(int id, User u) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, country = ? WHERE id = ?";
        try (Connection conn = getConnection();) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getCountry());
            stmt.setInt(4, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    @Override
    public boolean removeUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    @Override
    public User getUser(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection();) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            try {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String country = rs.getString("country");
                    User user = new User(id, name, email, country);
                    users.add(user);
                }
                return users;
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public List<User> searchUsersByCountry(String country) throws SQLException {
        String sql = "SELECT * FROM users WHERE LOWER(country) LIKE LOWER(?)";
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + country + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String countryName = rs.getString("country");
                User user = new User(id, name, email, countryName);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return users;
    }


    @Override
    public List<User> sortUsersByName() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY name ASC";
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                User user = new User(id, name, email, country);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return users;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        User user = null;
        String query = "{CALL get_user_by_id(?)}";

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {
            callableStatement.setInt(1, id);
            ResultSet rs = callableStatement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return user;
    }


    @Override
    public void insertUserStore(User user) throws SQLException {
        String query = "{CALL insert_user(?,?,?)}";

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {
            callableStatement.setString(1, user.getName());
            callableStatement.setString(2, user.getEmail());
            callableStatement.setString(3, user.getCountry());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void addUserTransaction(User user, List<Integer> permissions) {
        Connection conn = null;

        PreparedStatement pstmt = null;

        PreparedStatement pstmtAssignment = null;

        ResultSet rs = null;

        try {
            conn = getConnection();

            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement("INSERT INTO users(name, email, country) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getCountry());
            int rowAffected = pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt(1);
            }

            if (rowAffected == 1) {
                String sqlPivot = "INSERT INTO user_permision(user_id,permision_id) VALUES(?,?)";
                pstmtAssignment = conn.prepareStatement(sqlPivot);

                for (int permisionId : permissions) {
                    pstmtAssignment.setInt(1, userId);
                    pstmtAssignment.setInt(2, permisionId);
                    pstmtAssignment.executeUpdate();
                }

                conn.commit();
            } else {
                conn.rollback();
            }

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(ex.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (pstmtAssignment != null) pstmtAssignment.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void addUserWithoutTransaction(User user, List<Integer> permissions) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtAssignment = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            // Auto-commit mặc định là true, không cần setAutoCommit(false)

            // Thêm user mới
            pstmt = conn.prepareStatement("INSERT INTO users(name, email, country) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getCountry());

            int rowAffected = pstmt.executeUpdate();
            int userId = 0;

            if (rowAffected == 1) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    userId = rs.getInt(1);
                }

                // Thêm permissions cho user
                String sqlPivot = "INSERT INTO user_permision(user_id, permision_id) VALUES(?, ?)";
                pstmtAssignment = conn.prepareStatement(sqlPivot);

                for (int permisionId : permissions) {
                    try {
                        pstmtAssignment.setInt(1, userId);
                        pstmtAssignment.setInt(2, permisionId);
                        pstmtAssignment.executeUpdate();
                    } catch (SQLException e) {
                        // Nếu một permission thất bại, ghi log nhưng tiếp tục
                        System.out.println("Không thể gán permission " + permisionId + ": " + e.getMessage());
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println("Lỗi khi thêm user: " + ex.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (pstmtAssignment != null) pstmtAssignment.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
    }
}

package com.codegym.app.usermanagerjdbc.dao;

import com.codegym.app.usermanagerjdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    boolean addUser(User user) throws SQLException;

    boolean editUser(int id, User user) throws SQLException;

    boolean removeUser(int id) throws SQLException;

    User getUser(int id) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    List<User> searchUsersByCountry(String country) throws SQLException;

    List<User> sortUsersByName() throws SQLException;

    User getUserById(int id) throws SQLException;

    void insertUserStore(User user) throws SQLException;

    void addUserTransaction(User user, List<Integer> permission);

    void addUserWithoutTransaction(User user, List<Integer> permissions);
}

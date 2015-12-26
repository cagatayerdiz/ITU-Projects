package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Distribution;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.IUserCollection;


public class UserCollectionJDBC implements IUserCollection {

    public UserCollectionJDBC() {
    }

    @Override
    public List<User> getUser() {
        Connection connection = DBConnectionManager.getConnection();
        List<User> users = new LinkedList<User>();
        Statement statement = null;
        ResultSet results = null;
        try {
            String query = "SELECT * FROM USER ORDER BY USER_NAME";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {

                Integer id = results.getInt("ID");
                String userName = results.getString("USER_NAME");
                String password = results.getString("PASSWORD");
                String email = results.getString("EMAIL");
                String name = results.getString("NAME");
                String surname = results.getString("SURNAME");
                Date lastLogin = results.getDate("LAST_LOGIN");
                Boolean isActive = results.getBoolean("IS_ACTIVE");
                String confirmationCode = results
                        .getString("CONFIRMATION_CODE");
                Boolean isAdmin = results.getBoolean("IS_ADMIN");
                User user = new User(id, userName, password, email, name,
                        surname, lastLogin, isActive, confirmationCode, isAdmin);

                users.add(user);
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        } finally {
            try {
                DBConnectionManager.closeConnection(connection);
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void addUser(User user) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        user.setConfirmationCode(UUID.randomUUID().toString().substring(0, 10));
        try {
            Distribution.sendMail(user);
            String query = "INSERT INTO USER (USER_NAME, PASSWORD, EMAIL, NAME, SURNAME, LAST_LOGIN, IS_ACTIVE, CONFIRMATION_CODE, IS_ADMIN) VALUES (?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSurName());
            statement.setDate(6, new java.sql.Date(System.currentTimeMillis()));
            statement.setBoolean(7, false);
            statement.setString(8, user.getConfirmationCode());
            statement.setBoolean(9, false);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.getMessage());
        } finally {
            try {
                DBConnectionManager.closeConnection(connection);
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM USER WHERE (ID = ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        } finally {
            try {
                DBConnectionManager.closeConnection(connection);
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateUser(User user) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE USER SET USER_NAME =?, PASSWORD= ?, EMAIL=?, NAME=?,"
                    + "SURNAME=?, LAST_LOGIN=?, IS_ACTIVE = ?, CONFIRMATION_CODE=?, IS_ADMIN=? WHERE (ID = ?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSurName());
            statement.setDate(6, new java.sql.Date(System.currentTimeMillis()));
            statement.setBoolean(7, user.getIsActive());
            statement.setString(8, user.getConfirmationCode());
            statement.setBoolean(9, user.getIsAdmin());
            statement.setInt(10, user.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        } finally {
            try {
                DBConnectionManager.closeConnection(connection);
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void activated(User user) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        user.setLastLogin(new java.sql.Date(System.currentTimeMillis()));
        try {
            String query = "UPDATE USER SET IS_ACTIVE = ?, LAST_LOGIN =? WHERE (EMAIL = ?)";
            statement = connection.prepareStatement(query);
            statement.setBoolean(1, true);
            statement.setDate(2, user.getLastLogin());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        } finally {
            try {
                DBConnectionManager.closeConnection(connection);
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getVerification(User user) {
        Connection connection = DBConnectionManager.getConnection();
        String confirmationCode = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            String query = "SELECT CONFIRMATION_CODE FROM USER WHERE (EMAIL = ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            results = statement.executeQuery();

            while (results.next()) {
                confirmationCode = results.getString("CONFIRMATION_CODE");
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        } finally {
            try {
                DBConnectionManager.closeConnection(connection);
                if (statement != null) {
                    statement.close();
                }
                if (results != null) {
                    results.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return confirmationCode;
    }

    public User login(String username) {
        Connection connection = DBConnectionManager.getConnection();
        User user = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            String query = "SELECT * FROM USER WHERE (USER_NAME = ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            results = statement.executeQuery();
            if (results.next()) {
                Integer id = results.getInt("ID");
                String userName = results.getString("USER_NAME");
                String password = results.getString("PASSWORD");
                String email = results.getString("EMAIL");
                String name = results.getString("NAME");
                String surname = results.getString("SURNAME");
                Date lastLogin = results.getDate("LAST_LOGIN");
                Boolean isActive = results.getBoolean("IS_ACTIVE");
                String confirmationCode = results
                        .getString("CONFIRMATION_CODE");
                Boolean isAdmin = results.getBoolean("IS_ADMIN");
                user = new User(id, userName, password, email, name, surname,
                        lastLogin, isActive, confirmationCode, isAdmin);
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        } finally {
            try {
                DBConnectionManager.closeConnection(connection);
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}

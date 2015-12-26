package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.UserTheaterVote;
import tr.edu.itu.cs.db.Interface.IUserTheaterVoteCollection;


public class UserTheaterVoteCollectionJDBC implements
        IUserTheaterVoteCollection {

    public UserTheaterVoteCollectionJDBC() {
    }

    @Override
    public List<UserTheaterVote> getUserTheaterVote() {
        Connection connection = DBConnectionManager.getConnection();
        List<UserTheaterVote> userTheaterVotes = new LinkedList<UserTheaterVote>();
        Statement statement = null;
        ResultSet results = null;
        try {
            String query = "SELECT * FROM USER_THEATER_VOTE ORDER BY THEATER_ID";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                Integer userId = results.getInt("USER_ID");
                Integer theaterId = results.getInt("THEATER_ID");
                Integer vote = results.getInt("VOTE");
                UserTheaterVote userTheaterVote = new UserTheaterVote(id,
                        userId, theaterId, vote);
                userTheaterVotes.add(userTheaterVote);
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
        return userTheaterVotes;
    }

    @Override
    public void addUserTheaterVote(UserTheaterVote userTheaterVote) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO USER_THEATER_VOTE (USER_ID, THEATER_ID, VOTE) VALUES (?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userTheaterVote.getUserId());
            statement.setInt(2, userTheaterVote.getTheaterId());
            statement.setInt(3, userTheaterVote.getVote());
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
    public void deleteUserTheaterVote(UserTheaterVote userTheaterVote) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM USER_THEATER_VOTE WHERE (ID = ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userTheaterVote.getId());
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
    public void updateUserTheaterVote(UserTheaterVote userTheaterVote) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE USER_THEATER_VOTE SET USER_ID = ?, THEATER_ID= ?, VOTE=? WHERE (ID = ?) ";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userTheaterVote.getUserId());
            statement.setInt(2, userTheaterVote.getTheaterId());
            statement.setInt(3, userTheaterVote.getVote());
            statement.setInt(4, userTheaterVote.getId());
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
}

package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.UserActorVote;
import tr.edu.itu.cs.db.Interface.IUserActorVoteCollection;


public class UserActorVoteCollectionJDBC implements IUserActorVoteCollection {

    public UserActorVoteCollectionJDBC() {
    }

    @Override
    public List<UserActorVote> getUserActorVote() {
        Connection connection = DBConnectionManager.getConnection();
        List<UserActorVote> userActorVotes = new LinkedList<UserActorVote>();
        Statement statement = null;
        ResultSet results = null;
        try {
            String query = "SELECT * FROM USER_ACTOR_VOTE ORDER BY ACTOR_ID";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                Integer userId = results.getInt("USER_ID");
                Integer actorId = results.getInt("ACTOR_ID");
                Integer vote = results.getInt("VOTE");
                UserActorVote userActorVote = new UserActorVote(id, userId,
                        actorId, vote);
                userActorVotes.add(userActorVote);
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
        return userActorVotes;
    }

    @Override
    public void addUserActorVote(UserActorVote userActorVote) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO USER_ACTOR_VOTE (USER_ID, ACTOR_ID, VOTE) VALUES (?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userActorVote.getUserId());
            statement.setInt(2, userActorVote.getActorId());
            statement.setInt(3, userActorVote.getVote());
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
    public void deleteUserActorVote(UserActorVote userActorVote) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM USER_ACTOR_VOTE WHERE (ID = ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userActorVote.getId());
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
    public void updateUserActorVote(UserActorVote userActorVote) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE USER_ACTOR_VOTE SET USER_ID = ?, ACTOR_ID= ?, VOTE=? WHERE (ID = ?) ";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userActorVote.getUserId());
            statement.setInt(2, userActorVote.getActorId());
            statement.setInt(3, userActorVote.getVote());
            statement.setInt(4, userActorVote.getId());
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

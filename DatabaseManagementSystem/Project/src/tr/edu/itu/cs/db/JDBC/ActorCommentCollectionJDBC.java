package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.ActorComment;
import tr.edu.itu.cs.db.Interface.IActorCommentCollection;


public class ActorCommentCollectionJDBC implements IActorCommentCollection {

    public ActorCommentCollectionJDBC() {
    }

    @Override
    public List<ActorComment> getActorComment() {
        Connection connection = DBConnectionManager.getConnection();
        List<ActorComment> actorsComment = new LinkedList<ActorComment>();
        Statement statement = null;
        ResultSet results = null;
        try {
            String query = "SELECT * FROM ACTOR_COMMENT ORDER BY DATE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                Integer userId = results.getInt("USER_ID");
                Integer actorId = results.getInt("ACTOR_ID");
                String comment = results.getString("COMMENT");
                java.sql.Date date = results.getDate("DATE");
                ActorComment actorComment = new ActorComment(id, userId,
                        actorId, comment, date);
                actorsComment.add(actorComment);
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
        return actorsComment;
    }

    public void addActorComment(ActorComment actorComment) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO ACTOR_COMMENT (USER_ID, ACTOR_ID, COMMENT, DATE) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, actorComment.getUserId());
            statement.setInt(2, actorComment.getActorId());
            statement.setString(3, actorComment.getComment());
            statement.setDate(4, actorComment.getDate());
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

    public void deleteActorComment(ActorComment actorComment) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM ACTOR_COMMENT WHERE (ID=?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, actorComment.getId());
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
    public void updateActorComment(ActorComment actorComment) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE ACTOR_COMMENT SET COMMENT = ?, USER_ID = ?, ACTOR_ID = ?, DATE=? WHERE(ID = ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, actorComment.getComment());
            statement.setInt(2, actorComment.getUserId());
            statement.setInt(3, actorComment.getActorId());
            statement.setDate(4, actorComment.getDate());
            statement.setInt(5, actorComment.getId());
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

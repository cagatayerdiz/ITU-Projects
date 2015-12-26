package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.Actor;
import tr.edu.itu.cs.db.Interface.IActorCollection;


public class ActorCollectionJDBC implements IActorCollection {

    public ActorCollectionJDBC() {
    }

    @Override
    public List<Actor> getActor() {
        Connection connection = DBConnectionManager.getConnection();
        List<Actor> actors = new LinkedList<Actor>();
        Statement statement = null;
        ResultSet results = null;
        try {
            String query = "SELECT ID, NAME, SURNAME, VOTE_COUNT, TOTAL_VOTE FROM ACTOR ORDER BY NAME";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                String name = results.getString("NAME");
                String surname = results.getString("SURNAME");
                Integer voteCount = results.getInt("VOTE_COUNT");
                Integer totalVote = results.getInt("TOTAL_VOTE");

                Actor actor = new Actor(id, name, surname, voteCount, totalVote);
                actors.add(actor);
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
        return actors;
    }

    public void addActor(Actor actor) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO ACTOR (NAME, SURNAME,VOTE_COUNT,TOTAL_VOTE) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, actor.getName());
            statement.setString(2, actor.getSurname());
            statement.setInt(3, actor.getVoteCount());
            statement.setInt(4, actor.getTotalVote());

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

    public void deleteActor(Actor actor) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM ACTOR WHERE (ID=?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, actor.getId());
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
    public void updateActor(Actor actor) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE ACTOR SET NAME = ?, SURNAME= ?, VOTE_COUNT=?, TOTAL_VOTE=?"
                    + " WHERE ID = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, actor.getName());
            statement.setString(2, actor.getSurname());
            statement.setInt(3, actor.getVoteCount());
            statement.setInt(4, actor.getTotalVote());
            statement.setInt(5, actor.getId());

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

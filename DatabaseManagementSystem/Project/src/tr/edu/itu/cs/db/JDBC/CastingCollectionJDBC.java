package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.Casting;
import tr.edu.itu.cs.db.Interface.ICastingCollection;


public class CastingCollectionJDBC implements ICastingCollection {
    public CastingCollectionJDBC() {
    }

    @Override
    public List<Casting> getCasting() {
        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        List<Casting> castings = new LinkedList<Casting>();
        try {
            String query = "SELECT * FROM CASTING ORDER BY THEATER_ID";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                Integer theaterId = results.getInt("THEATER_ID");
                Integer actorId = results.getInt("ACTOR_ID");
                Casting casting = new Casting(id, theaterId, actorId);
                castings.add(casting);
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
        return castings;
    }

    @Override
    public void addCasting(Casting casting) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO CASTING (THEATER_ID, ACTOR_ID) VALUES (?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, casting.getTheaterId());
            statement.setInt(2, casting.getActorId());
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
    public void deleteCasting(Casting casting) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM CASTING WHERE (ID=?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, casting.getId());
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
    public void updateCasting(Casting casting) {
    }

}

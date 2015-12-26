package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.TheaterType;
import tr.edu.itu.cs.db.Interface.ITheaterTypeCollection;


public class TheaterTypeCollectionJDBC implements ITheaterTypeCollection {

    public TheaterTypeCollectionJDBC() {

    }

    @Override
    public List<TheaterType> getTheaterType() {
        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        List<TheaterType> theaterTypes = new LinkedList<TheaterType>();
        try {
            String query = "SELECT ID, TYPE FROM THEATER_TYPE ORDER BY TYPE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                String type = results.getString("TYPE");

                TheaterType theaterType = new TheaterType(id, type);
                theaterTypes.add(theaterType);
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
        return theaterTypes;
    }

    @Override
    public void addTheaterType(TheaterType theaterType) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO THEATER_TYPE (TYPE) VALUES (?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, theaterType.getType());
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
    public void deleteTheaterType(TheaterType theaterType) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM THEATER_TYPE WHERE (ID=?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, theaterType.getId());
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
    public void updateTheaterType(TheaterType theaterType) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE THEATER_TYPE SET TYPE = ? WHERE (ID = ?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, theaterType.getType());
            statement.setInt(2, theaterType.getId());
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

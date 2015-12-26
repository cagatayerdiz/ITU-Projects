package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.Theater;
import tr.edu.itu.cs.db.Interface.ITheaterCollection;


public class TheaterCollectionJDBC implements ITheaterCollection {

    public TheaterCollectionJDBC() {
    }

    @Override
    public List<Theater> getTheater() {
        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        List<Theater> theaters = new LinkedList<Theater>();
        try {
            String query = "SELECT ID, TITLE, TYPE_ID, LANGUAGE_ID, VOTE_COUNT, TOTAL_VOTE FROM THEATER ORDER BY TITLE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                String title = results.getString("TITLE");
                Integer typeId = results.getInt("TYPE_ID");
                Integer languageId = results.getInt("LANGUAGE_ID");
                Integer voteCount = results.getInt("VOTE_COUNT");
                Integer totalVote = results.getInt("TOTAL_VOTE");

                Theater theater = new Theater(id, title, typeId, languageId,
                        voteCount, totalVote);
                theaters.add(theater);
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
        return theaters;
    }

    public void addTheater(Theater theater) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO THEATER (TITLE, TYPE_ID, LANGUAGE_ID, VOTE_COUNT, TOTAL_VOTE) VALUES (?,?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, theater.getTitle());
            statement.setInt(2, theater.getTypeId());
            statement.setInt(3, theater.getLanguageId());
            statement.setInt(4, theater.getVoteCount());
            statement.setInt(5, theater.getTotalVote());
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

    public void deleteTheater(Theater theater) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM THEATER WHERE (ID=?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, theater.getId());
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
    public void updateTheater(Theater theater) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE THEATER SET TITLE = ?, TYPE_ID= ?, LANGUAGE_ID= ?, VOTE_COUNT=?, TOTAL_VOTE=? WHERE (ID = ?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, theater.getTitle());
            statement.setInt(2, theater.getTypeId());
            statement.setInt(3, theater.getLanguageId());
            statement.setInt(4, theater.getVoteCount());
            statement.setInt(5, theater.getTotalVote());
            statement.setInt(6, theater.getId());
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

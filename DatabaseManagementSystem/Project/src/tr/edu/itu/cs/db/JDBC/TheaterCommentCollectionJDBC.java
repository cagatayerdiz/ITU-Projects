package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.TheaterComment;
import tr.edu.itu.cs.db.Interface.ITheaterCommentCollection;


public class TheaterCommentCollectionJDBC implements ITheaterCommentCollection {

    public TheaterCommentCollectionJDBC() {
    }

    @Override
    public List<TheaterComment> getTheaterComment() {
        Connection connection = DBConnectionManager.getConnection();
        List<TheaterComment> theatersComment = new LinkedList<TheaterComment>();
        Statement statement = null;
        ResultSet results = null;
        try {
            String query = "SELECT * FROM THEATER_COMMENT ORDER BY DATE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                Integer userId = results.getInt("USER_ID");
                Integer theaterId = results.getInt("THEATER_ID");
                String comment = results.getString("COMMENT");
                java.sql.Date date = results.getDate("DATE");
                TheaterComment theaterComment = new TheaterComment(id, userId,
                        theaterId, comment, date);
                theatersComment.add(theaterComment);
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
        return theatersComment;
    }

    public void addTheaterComment(TheaterComment theaterComment) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO THEATER_COMMENT (USER_ID, THEATER_ID, COMMENT, DATE) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, theaterComment.getUserId());
            statement.setInt(2, theaterComment.getTheaterId());
            statement.setString(3, theaterComment.getComment());
            statement.setDate(4, theaterComment.getDate());
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

    public void deleteTheaterComment(TheaterComment theaterComment) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM THEATER_COMMENT WHERE (ID=?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, theaterComment.getId());
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
    public void updateTheaterComment(TheaterComment theaterComment) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE THEATER_COMMENT SET COMMENT = ?, USER_ID = ?, THEATER_ID = ?, DATE=? WHERE(ID = ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, theaterComment.getComment());
            statement.setInt(2, theaterComment.getUserId());
            statement.setInt(3, theaterComment.getTheaterId());
            statement.setDate(4, theaterComment.getDate());
            statement.setInt(5, theaterComment.getId());
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

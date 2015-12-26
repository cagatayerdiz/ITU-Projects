package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.Language;
import tr.edu.itu.cs.db.Interface.ILanguageCollection;


public class LanguageCollectionJDBC implements ILanguageCollection {
    public LanguageCollectionJDBC() {
    }

    @Override
    public List<Language> getLanguage() {
        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        List<Language> languages = new LinkedList<Language>();
        try {
            String query = "SELECT ID, LANGUAGE FROM LANGUAGE ORDER BY LANGUAGE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                String lang = results.getString("LANGUAGE");

                Language language = new Language(id, lang);
                languages.add(language);
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
        return languages;
    }

    @Override
    public void addLanguage(Language language) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO LANGUAGE (LANGUAGE) VALUES (?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, language.getLanguage());
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
    public void deleteLanguage(Language language) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM LANGUAGE WHERE (ID=?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, language.getId());
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
    public void updateLanguage(Language language) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE LANGUAGE SET LANGUAGE = ? WHERE (ID = ?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, language.getLanguage());
            statement.setInt(2, language.getId());
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

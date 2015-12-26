package tr.edu.itu.cs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;


public class DBConnectionManager {
    private static String jdbcURLBluemix = "jdbc:sqlite:"
            + System.getProperty("user.home")
            + "/app/tr/edu/itu/cs/db/Theautomata.sqlite";

    private static String jdbcURLLocal = "jdbc:sqlite:"
            + System.getProperty("user.home")
            + "\\git\\theautomata\\src\\tr\\edu\\itu\\cs\\db\\Theautomata.sqlite";

    public static Connection getConnection() {

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        String OS = System.getProperty("os.name");
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            if (OS.startsWith("Windows")) {
                connection = DriverManager.getConnection(jdbcURLLocal,
                        config.toProperties());
            } else {
                connection = DriverManager.getConnection(jdbcURLBluemix,
                        config.toProperties());
            }
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException(e.getMessage());
        } catch (SQLException ex) {
            throw new UnsupportedOperationException(ex.getMessage());
        }
        return connection;
    }

    public static void closeConnection(Connection connection)
            throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

}

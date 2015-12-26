package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.Reservation;
import tr.edu.itu.cs.db.Interface.IReservationCollection;


public class ReservationCollectionJDBC implements IReservationCollection {

    public ReservationCollectionJDBC() {
    }

    @Override
    public List<Reservation> getReservation() {

        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        List<Reservation> reservations = new LinkedList<Reservation>();
        try {
            String query = "SELECT RESERVATION_CODE, EVENT_NAME, USER_ID, NUM_TICKET FROM RESERVATION ORDER BY EVENT_NAME";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer reservationCode = results.getInt("RESERVATION_CODE");
                String eventName = results.getString("EVENT_NAME");
                Integer userId = results.getInt("USER_ID");
                Integer numTicket = results.getInt("NUM_TICKET");

                Reservation reservation = new Reservation(reservationCode,
                        eventName, userId, numTicket);
                reservations.add(reservation);
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
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO RESERVATION (EVENT_NAME, USER_ID, NUM_TICKET) VALUES (?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, reservation.getEventName());
            statement.setInt(2, reservation.getUserId());
            statement.setInt(3, reservation.getNumTicket());
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

    public void deleteReservation(Reservation reservation) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM RESERVATION WHERE (RESERVATION_CODE=?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, reservation.getReservationCode());
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
    public void updateReservation(Reservation reservation) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE RESERVATION SET EVENT_NAME = ?, USER_ID= ?, NUM_TICKET= ? WHERE (RESERVATION_CODE = ?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, reservation.getEventName());
            statement.setInt(2, reservation.getUserId());
            statement.setInt(3, reservation.getNumTicket());
            statement.setInt(4, reservation.getReservationCode());
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

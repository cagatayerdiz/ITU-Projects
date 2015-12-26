package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.Event;
import tr.edu.itu.cs.db.Interface.IEventCollection;


public class EventCollectionJDBC implements IEventCollection {

    public EventCollectionJDBC() {

    }

    @Override
    public List<Event> getEvent() {
        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        List<Event> events = new LinkedList<Event>();
        try {
            String query = "SELECT EVENT_NAME, THEATER_ID, CITY_ID, LOCATION, DATE, SOLD_SEAT, CAPACITY FROM EVENT ORDER BY DATE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                String eventName = results.getString("EVENT_NAME");
                Integer theaterId = results.getInt("THEATER_ID");
                Integer cityId = results.getInt("CITY_ID");
                String location = results.getString("LOCATION");
                java.sql.Date date = results.getDate("DATE");
                Integer soldDate = results.getInt("SOLD_SEAT");
                Integer capacity = results.getInt("CAPACITY");

                Event event = new Event(eventName, theaterId, cityId, location,
                        date, soldDate, capacity);
                events.add(event);
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
        return events;
    }

    @Override
    public void addEvent(Event event) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO EVENT (EVENT_NAME, THEATER_ID, CITY_ID, LOCATION, DATE, SOLD_SEAT, CAPACITY) VALUES (?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, event.getEventName());
            statement.setInt(2, event.getTheaterId());
            statement.setInt(3, event.getCityId());
            statement.setString(4, event.getLocation());
            statement.setDate(5, event.getDate());
            statement.setInt(6, event.getSoldSeat());
            statement.setInt(7, event.getCapacity());
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
    public void deleteEvent(Event event) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM EVENT WHERE (EVENT_NAME=?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, event.getEventName());
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
    public void updateEvent(Event event) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE EVENT SET EVENT_NAME = ?, THEATER_ID = ?, CITY_ID = ?, LOCATION = ?, DATE = ?, SOLD_SEAT = ?, CAPACITY = ? WHERE (EVENT_NAME = ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, event.getEventName());
            statement.setInt(2, event.getTheaterId());
            statement.setInt(3, event.getCityId());
            statement.setString(4, event.getLocation());
            statement.setDate(5, event.getDate());
            statement.setInt(6, event.getSoldSeat());
            statement.setInt(7, event.getCapacity());
            statement.setString(8, event.getEventName());
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

package tr.edu.itu.cs.db.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Class.City;
import tr.edu.itu.cs.db.Interface.ICityCollection;


public class CityCollectionJDBC implements ICityCollection {
    public CityCollectionJDBC() {
    }

    @Override
    public List<City> getCity() {
        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        List<City> citys = new LinkedList<City>();
        try {
            String query = "SELECT ID, NAME FROM CITY ORDER BY NAME";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                String name = results.getString("NAME");

                City city = new City(id, name);
                citys.add(city);
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
        return citys;
    }

    @Override
    public void addCity(City city) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO CITY (NAME) VALUES (?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, city.getName());
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
    public void deleteCity(City city) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM CITY WHERE (ID=?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, city.getId());
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
    public void updateCity(City city) {
        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query = "UPDATE CITY SET NAME = ? WHERE (ID = ?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, city.getName());
            statement.setInt(2, city.getId());
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

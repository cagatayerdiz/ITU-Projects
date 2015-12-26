package tr.edu.itu.cs.db.Page;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.DBConnectionManager;


public class EventInformationPage extends BasePage {
    final List<String> titles = new LinkedList<String>();
    final List<String> citys = new LinkedList<String>();
    final List<String> locations = new LinkedList<String>();
    final List<Date> dates = new LinkedList<Date>();

    public EventInformationPage(String eventName) {

        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            String query2 = "SELECT TITLE, CITY.NAME, LOCATION, DATE FROM EVENT JOIN THEATER ON THEATER.ID = EVENT.THEATER_ID JOIN CITY ON CITY.ID = EVENT.CITY_ID WHERE (EVENT_NAME = ?)";
            ResultSet results = null;
            statement = connection.prepareStatement(query2);
            statement.setString(1, eventName);
            results = statement.executeQuery();
            while (results.next()) {
                titles.add(results.getString("TITLE"));
                citys.add(results.getString("NAME"));
                locations.add(results.getString("LOCATION"));
                dates.add(results.getDate("Date"));
            }

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

        PropertyListView eventListView = new PropertyListView("event_list",
                titles) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("title", titles.get(count)));
                item.add(new Label("city", citys.get(count)));
                item.add(new Label("location", locations.get(count)));
                item.add(new Label("date", dates.get(count)));
                if (count < titles.size() - 1)
                    count = count + 1;
            }
        };
        this.add(eventListView);
        this.add(new Label("eventName", eventName));

    }

}

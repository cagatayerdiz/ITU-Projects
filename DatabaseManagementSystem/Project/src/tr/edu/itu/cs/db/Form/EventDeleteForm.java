package tr.edu.itu.cs.db.Form;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Event;
import tr.edu.itu.cs.db.Interface.IEventCollection;
import tr.edu.itu.cs.db.Page.EventEditPage;


public class EventDeleteForm extends Form {
    private List<String> selectedEventNames;

    public EventDeleteForm(String id) {
        super(id);
        this.selectedEventNames = new LinkedList<String>();

        CheckGroup eventCheckGroup = new CheckGroup("selected_events",
                this.selectedEventNames);
        this.add(eventCheckGroup);

        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        final List<String> eventNames = new LinkedList<String>();
        final List<String> theaterTitles = new LinkedList<String>();
        final List<String> citys = new LinkedList<String>();
        final List<String> locations = new LinkedList<String>();
        final List<Date> dates = new LinkedList<Date>();
        final List<Integer> soldSeats = new LinkedList<Integer>();
        final List<Integer> capacitys = new LinkedList<Integer>();
        try {
            String query = "SELECT EVENT_NAME, TITLE, NAME, LOCATION, DATE, SOLD_SEAT, CAPACITY FROM EVENT JOIN THEATER ON THEATER.ID = EVENT.THEATER_ID JOIN CITY ON CITY.ID = EVENT.CITY_ID ORDER BY DATE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                eventNames.add(results.getString("EVENT_NAME"));
                theaterTitles.add(results.getString("TITLE"));
                citys.add(results.getString("NAME"));
                locations.add(results.getString("LOCATION"));
                dates.add(results.getDate("DATE"));
                soldSeats.add(results.getInt("SOLD_SEAT"));
                capacitys.add(results.getInt("CAPACITY"));
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

        PropertyListView eventListView = new PropertyListView("event_list",
                eventNames) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("eventName", eventNames.get(count)));
                item.add(new Label("title", theaterTitles.get(count)));
                item.add(new Label("name", citys.get(count)));
                item.add(new Label("location", locations.get(count)));
                item.add(new Label("date", dates.get(count)));
                item.add(new Label("soldSeat", soldSeats.get(count)));
                item.add(new Label("capacity", capacitys.get(count)));
                item.add(new Check("selected", item.getModel()));
                if (count < eventNames.size() - 1)
                    count = count + 1;
            }
        };
        eventCheckGroup.add(eventListView);
    }

    @Override
    public void onSubmit() {
        WicketApplication app = (WicketApplication) this.getApplication();
        IEventCollection collection = app.getEventCollection();
        for (String x : this.selectedEventNames) {
            Event aEvent = new Event();
            aEvent.setEventName(x);
            collection.deleteEvent(aEvent);
        }
        this.setResponsePage(new EventEditPage());
    }
}

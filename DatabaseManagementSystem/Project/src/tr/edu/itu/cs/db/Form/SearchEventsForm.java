package tr.edu.itu.cs.db.Form;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.City;
import tr.edu.itu.cs.db.Class.Event;
import tr.edu.itu.cs.db.Interface.ICityCollection;
import tr.edu.itu.cs.db.Page.SearchEventsPage;


public class SearchEventsForm extends Form {

    private City selectedCity = new City();
    DropDownChoice selectbox1;
    final TextField<String> titleText = new TextField<String>("location");
    final DateTextField dateTextField = new DateTextField("date");

    WicketApplication app = (WicketApplication) this.getApplication();

    final List<String> eventNames = new LinkedList<String>();
    final List<String> theaterTitles = new LinkedList<String>();
    final List<String> cityNames = new LinkedList<String>();
    final List<String> locations = new LinkedList<String>();
    final List<Date> dates = new LinkedList<Date>();
    final List<Integer> soldSeats = new LinkedList<Integer>();
    final List<Integer> capacitys = new LinkedList<Integer>();

    public SearchEventsForm(String id) {
        super(id);
        CompoundPropertyModel model = new CompoundPropertyModel(new Event());
        this.setModel(model);
        this.add(titleText);

        DatePicker datePicker = new DatePicker();
        datePicker.setShowOnFieldClick(true);
        datePicker.setAutoHide(true);
        dateTextField.add(datePicker);
        this.add(dateTextField);

        ICityCollection collection = app.getCityCollection();
        List<City> citys = collection.getCity();

        ChoiceRenderer cityRenderer = new ChoiceRenderer("name");
        selectbox1 = new DropDownChoice<City>("city_id",
                new PropertyModel<City>(this, "selectedCity"), citys,
                cityRenderer);
        selectbox1.setNullValid(true);

        this.add(selectbox1);

        Button search = new Button("Search") {

            public void onSubmit() {
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement statement = null;
                ResultSet results = null;

                try {
                    String query = "SELECT EVENT_NAME, TITLE, CITY.NAME, LOCATION, DATE, SOLD_SEAT, CAPACITY FROM EVENT JOIN THEATER ON THEATER.ID = EVENT.THEATER_ID JOIN CITY ON CITY.ID = EVENT.CITY_ID WHERE (TITLE LIKE ? AND (? IS NULL OR CITY_ID=?) AND (? IS NULL OR DATE=?)) ORDER BY DATE";
                    statement = connection.prepareStatement(query);
                    if (titleText.getModelObject() == null)
                        statement.setString(1, "%%");
                    else
                        statement.setString(1, "%" + titleText.getModelObject()
                                + "%");

                    if (selectedCity != null) {
                        statement.setInt(2, selectedCity.getId());
                        statement.setInt(3, selectedCity.getId());
                    }

                    if (dateTextField.getModelObject() != null) {
                        statement.setDate(4,
                                (Date) dateTextField.getModelObject());
                        statement.setDate(5,
                                (Date) dateTextField.getModelObject());
                    }

                    results = statement.executeQuery();

                    while (results.next()) {
                        eventNames.add(results.getString("EVENT_NAME"));
                        theaterTitles.add(results.getString("TITLE"));
                        cityNames.add(results.getString("NAME"));
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

                this.setResponsePage(new SearchEventsPage(eventNames,
                        theaterTitles, cityNames, locations, dates, soldSeats,
                        capacitys));

            }
        };
        this.add(search);

    }
}

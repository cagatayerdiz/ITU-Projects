package tr.edu.itu.cs.db.Form;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;

import tr.edu.itu.cs.db.MySession;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Event;
import tr.edu.itu.cs.db.Class.Reservation;
import tr.edu.itu.cs.db.Interface.IEventCollection;
import tr.edu.itu.cs.db.Interface.IReservationCollection;
import tr.edu.itu.cs.db.Page.LoginPage;
import tr.edu.itu.cs.db.Page.SearchEventsPage;
import tr.edu.itu.cs.db.Page.SearchReservationPage;


public class BuyTicketForm extends Form {
    private String selectedEventName;
    final TextField<Integer> titleText = new TextField<Integer>("numTicket");
    RadioGroup group;
    List<String> eventNames = new LinkedList<String>();
    List<String> theaterTitles = new LinkedList<String>();
    List<String> citys = new LinkedList<String>();
    List<String> locations = new LinkedList<String>();
    List<Date> dates = new LinkedList<Date>();
    List<Integer> capacitys = new LinkedList<Integer>();
    List<Integer> soldSeats = new LinkedList<Integer>();

    public BuyTicketForm(String id, final List<String> eventNames,
            final List<String> theaterTitles, final List<String> citys,
            final List<String> locations, final List<Date> dates,
            final List<Integer> soldSeats, final List<Integer> capacitys) {
        super(id);

        CompoundPropertyModel model = new CompoundPropertyModel(new Event());
        this.setModel(model);

        this.theaterTitles = theaterTitles;
        this.citys = citys;
        this.dates = dates;
        this.locations = locations;
        this.soldSeats = soldSeats;
        this.capacitys = capacitys;
        this.eventNames = eventNames;
        this.add(titleText);
        group = new RadioGroup("location");
        this.add(group);

        PropertyListView eventListView = new PropertyListView("event_list",
                eventNames) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Radio("radioo", item.getModel()));
                item.add(new Label("eventName", eventNames.get(count)));
                item.add(new Label("title", theaterTitles.get(count)));
                item.add(new Label("city", citys.get(count)));
                item.add(new Label("scene", locations.get(count)));
                item.add(new Label("date", dates.get(count)));
                item.add(new Label("occupancy", soldSeats.get(count) + "/"
                        + capacitys.get(count)));
                item.add(new Label("emptySeat", (int) capacitys.get(count)
                        - (int) soldSeats.get(count)));
                if (count < eventNames.size() - 1)
                    count = count + 1;
            }
        };
        group.add(eventListView);
        Button reserve = new Button("reserve");
        this.add(reserve);
        Label t1 = new Label("t1", "Select");
        Label t2 = new Label("t2", "Event Name");
        Label t3 = new Label("t3", "Theater Title");
        Label t4 = new Label("t4", "City");
        Label t5 = new Label("t5", "Scene");
        Label t6 = new Label("t6", "Date");
        Label t7 = new Label("t7", "Occupancy");
        Label t8 = new Label("t8", "#Empty Seat");
        Label t9 = new Label("t9", "Ticket Number:");
        this.add(t1);
        this.add(t2);
        this.add(t3);
        this.add(t4);
        this.add(t5);
        this.add(t6);
        this.add(t7);
        this.add(t8);
        this.add(t9);
        if (eventNames == null && theaterTitles == null && citys == null
                && locations == null && dates == null && capacitys == null
                && soldSeats == null) {
            t1.setVisibilityAllowed(false);
            t2.setVisibilityAllowed(false);
            t3.setVisibilityAllowed(false);
            t4.setVisibilityAllowed(false);
            t5.setVisibilityAllowed(false);
            t6.setVisibilityAllowed(false);
            t7.setVisibilityAllowed(false);
            t8.setVisibilityAllowed(false);
            t9.setVisibilityAllowed(false);
            titleText.setVisibilityAllowed(false);
            group.setVisibilityAllowed(false);
            reserve.setVisibilityAllowed(false);
        }

    }

    public void onSubmit() {
        if (((MySession) Session.get()).getUser() == null) {
            this.setResponsePage(new LoginPage());
        } else {

            if (titleText.getModelObject() != null
                    && titleText.getModelObject() != 0) {
                WicketApplication app = (WicketApplication) this
                        .getApplication();
                IReservationCollection collection = app
                        .getReservationCollection();
                Reservation reservation = new Reservation();

                IEventCollection collection2 = app.getEventCollection();
                reservation.setEventName(group.getDefaultModelObjectAsString());
                if (titleText.getModelObject() <= (capacitys.get(eventNames
                        .indexOf(group.getDefaultModelObjectAsString())) - soldSeats
                        .get(eventNames.indexOf(group
                                .getDefaultModelObjectAsString())))) {
                    reservation.setNumTicket(titleText.getModelObject());
                    reservation.setUserId(((MySession) Session.get()).getUser()
                            .getId());
                    collection.addReservation(reservation);

                    this.setResponsePage(new SearchReservationPage());
                } else {
                    this.setResponsePage(new SearchEventsPage(eventNames,
                            theaterTitles, citys, locations, dates, soldSeats,
                            capacitys));
                }
            } else {
                this.setResponsePage(new SearchEventsPage(eventNames,
                        theaterTitles, citys, locations, dates, soldSeats,
                        capacitys));
            }
        }
    }
}

package tr.edu.itu.cs.db.Form;

import java.sql.Date;
import java.util.List;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.City;
import tr.edu.itu.cs.db.Class.Event;
import tr.edu.itu.cs.db.Class.Theater;
import tr.edu.itu.cs.db.Interface.ICityCollection;
import tr.edu.itu.cs.db.Interface.IEventCollection;
import tr.edu.itu.cs.db.Interface.ITheaterCollection;
import tr.edu.itu.cs.db.Page.EventEditPage;


public class EventUpdateForm extends Form {
    WicketApplication app = (WicketApplication) this.getApplication();
    private Event selectedEvent = new Event();
    private Theater selectedTheater = new Theater();
    private City selectedCity = new City();
    DropDownChoice selectbox1, selectbox2, selectbox3;
    final TextField<String> eventNameText = new TextField<String>("eventName");
    final TextField<String> locationText = new TextField<String>("location");
    final DateTextField dateTextField = new DateTextField("date");
    final TextField<Integer> soldSeatText = new TextField<Integer>("soldSeat");
    final TextField<Integer> capacityText = new TextField<Integer>("capacity");

    public EventUpdateForm(String id) {
        super(id);

        CompoundPropertyModel model = new CompoundPropertyModel(new Event());
        this.setModel(model);

        IEventCollection collection3 = app.getEventCollection();
        List<Event> events = collection3.getEvent();

        ChoiceRenderer eventRenderer = new ChoiceRenderer("eventName");
        selectbox3 = new DropDownChoice<Event>("event_name",
                new PropertyModel<Event>(this, "selectedEvent"), events,
                eventRenderer);
        this.add(selectbox3);

        ITheaterCollection collection = app.getTheaterCollection();
        List<Theater> theaters = collection.getTheater();

        ChoiceRenderer theaterRenderer = new ChoiceRenderer("title");
        selectbox1 = new DropDownChoice<Theater>("theater_id",
                new PropertyModel<Theater>(this, "selectedTheater"), theaters,
                theaterRenderer);
        this.add(selectbox1);

        ICityCollection collection2 = app.getCityCollection();
        List<City> citys = collection2.getCity();

        ChoiceRenderer cityRenderer = new ChoiceRenderer("name");
        selectbox2 = new DropDownChoice<City>("city_id",
                new PropertyModel<City>(this, "selectedCity"), citys,
                cityRenderer);
        this.add(selectbox2);

        eventNameText.setRequired(true);
        this.add(eventNameText);
        locationText.setRequired(true);
        this.add(locationText);
        DatePicker datePicker = new DatePicker();
        datePicker.setShowOnFieldClick(true);
        datePicker.setAutoHide(true);
        dateTextField.add(datePicker);
        dateTextField.setRequired(true);
        this.add(dateTextField);
        soldSeatText.setRequired(true);
        this.add(soldSeatText);
        capacityText.setRequired(true);
        this.add(capacityText);
    }

    @Override
    public void onSubmit() {
        Event event = new Event();
        event.setEventName(selectedEvent.getEventName());
        event.setTheaterId(selectedTheater.getId());
        event.setCityId(selectedCity.getId());
        event.setLocation(locationText.getModelObject());
        event.setDate((Date) dateTextField.getModelObject());
        event.setSoldSeat(soldSeatText.getModelObject());
        event.setCapacity(capacityText.getModelObject());

        IEventCollection collection = app.getEventCollection();
        collection.updateEvent(event);
        this.setResponsePage(new EventEditPage());
    }
}

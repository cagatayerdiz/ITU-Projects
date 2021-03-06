package tr.edu.itu.cs.db.Form;

import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Event;
import tr.edu.itu.cs.db.Class.Reservation;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.IEventCollection;
import tr.edu.itu.cs.db.Interface.IReservationCollection;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Page.ReservationEditPage;


public class ReservationAddForm extends Form {

    private WicketApplication app = (WicketApplication) this.getApplication();
    private Event selectedEvent = new Event();
    private User selectedUser = new User();
    DropDownChoice selectbox1, selectbox2;
    final TextField<Integer> numTicketText = new TextField<Integer>("numTicket");

    public ReservationAddForm(String id) {
        super(id);
        CompoundPropertyModel model = new CompoundPropertyModel(
                new Reservation());
        this.setModel(model);

        IEventCollection collection = app.getEventCollection();
        List<Event> events = collection.getEvent();

        ChoiceRenderer eventRenderer = new ChoiceRenderer("eventName");
        selectbox1 = new DropDownChoice<Event>("event_name",
                new PropertyModel<Event>(this, "selectedEvent"), events,
                eventRenderer);
        this.add(selectbox1);

        IUserCollection collection2 = app.getUserCollection();
        List<User> users = collection2.getUser();

        ChoiceRenderer userRenderer = new ChoiceRenderer("userName");
        selectbox2 = new DropDownChoice<User>("user_name",
                new PropertyModel<User>(this, "selectedUser"), users,
                userRenderer);
        this.add(selectbox2);

        numTicketText.setRequired(true);
        this.add(numTicketText);
    }

    @Override
    public void onSubmit() {
        Reservation reservation = new Reservation();
        reservation.setEventName(selectedEvent.getEventName());
        reservation.setUserId(selectedUser.getId());
        reservation.setNumTicket(numTicketText.getModelObject());

        IReservationCollection collection = app.getReservationCollection();
        collection.addReservation(reservation);
        this.setResponsePage(new ReservationEditPage());
    }
}

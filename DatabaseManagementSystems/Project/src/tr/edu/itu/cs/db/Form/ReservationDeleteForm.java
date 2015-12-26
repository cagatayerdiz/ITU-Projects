package tr.edu.itu.cs.db.Form;

import java.sql.Connection;
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
import tr.edu.itu.cs.db.Class.Reservation;
import tr.edu.itu.cs.db.Interface.IReservationCollection;
import tr.edu.itu.cs.db.Page.ReservationEditPage;


public class ReservationDeleteForm extends Form {
    private List<Integer> selectedReservationCodes;

    public ReservationDeleteForm(String id) {
        super(id);
        this.selectedReservationCodes = new LinkedList<Integer>();

        CheckGroup reservationCheckGroup = new CheckGroup(
                "selected_reservations", this.selectedReservationCodes);
        this.add(reservationCheckGroup);

        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        final List<Integer> reservationCodes = new LinkedList<Integer>();
        final List<String> eventNames = new LinkedList<String>();
        final List<String> userNames = new LinkedList<String>();
        final List<Integer> numTickets = new LinkedList<Integer>();
        try {
            String query = "SELECT RESERVATION_CODE, RESERVATION.EVENT_NAME, USER_NAME, NUM_TICKET FROM RESERVATION JOIN EVENT ON EVENT.EVENT_NAME = RESERVATION.EVENT_NAME JOIN USER ON USER.ID = RESERVATION.USER_ID ORDER BY RESERVATION_CODE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                reservationCodes.add(results.getInt("RESERVATION_CODE"));
                eventNames.add(results.getString("EVENT_NAME"));
                userNames.add(results.getString("USER_NAME"));
                numTickets.add(results.getInt("NUM_TICKET"));
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

        PropertyListView reservationListView = new PropertyListView(
                "reservation_list", reservationCodes) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("reservationCode", reservationCodes
                        .get(count)));
                item.add(new Label("eventName", eventNames.get(count)));
                item.add(new Label("userName", userNames.get(count)));
                item.add(new Label("numTicket", numTickets.get(count)));
                item.add(new Check("selected", item.getModel()));
                if (count < reservationCodes.size() - 1)
                    count++;
            }
        };
        reservationCheckGroup.add(reservationListView);
    }

    @Override
    public void onSubmit() {
        WicketApplication app = (WicketApplication) this.getApplication();
        IReservationCollection collection = app.getReservationCollection();
        for (Integer x : this.selectedReservationCodes) {
            Reservation reservation = new Reservation();
            reservation.setReservationCode(x);
            collection.deleteReservation(reservation);
        }
        this.setResponsePage(new ReservationEditPage());
    }
}

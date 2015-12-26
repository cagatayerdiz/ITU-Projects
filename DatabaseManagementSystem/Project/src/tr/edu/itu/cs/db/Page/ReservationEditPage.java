package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.ReservationAddForm;
import tr.edu.itu.cs.db.Form.ReservationDeleteForm;
import tr.edu.itu.cs.db.Form.ReservationUpdateForm;


public class ReservationEditPage extends BasePage {
    public ReservationEditPage() {

        this.add(new ReservationAddForm("add_reservation"));
        this.add(new ReservationDeleteForm("del_reservation"));
        this.add(new ReservationUpdateForm("update_reservation"));
    }
}

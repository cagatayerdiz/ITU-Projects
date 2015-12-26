package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.EventAddForm;
import tr.edu.itu.cs.db.Form.EventDeleteForm;
import tr.edu.itu.cs.db.Form.EventUpdateForm;


public class EventEditPage extends BasePage {
    public EventEditPage() {
        this.add(new EventAddForm("add_event"));
        this.add(new EventDeleteForm("del_event"));
        this.add(new EventUpdateForm("update_event"));
    }
}

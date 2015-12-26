package tr.edu.itu.cs.db.Page;

import org.apache.wicket.markup.html.form.DropDownChoice;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.ActorAddForm;
import tr.edu.itu.cs.db.Form.ActorDeleteForm;
import tr.edu.itu.cs.db.Form.ActorUpdateForm;


public class ActorEditPage extends BasePage {
    public ActorEditPage() {
        DropDownChoice selectbox;
        this.add(new ActorAddForm("add_actor"));
        this.add(new ActorDeleteForm("del_actor"));
        this.add(new ActorUpdateForm("update_actor"));
    }
}

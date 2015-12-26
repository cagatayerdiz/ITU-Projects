package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.CastingAddForm;
import tr.edu.itu.cs.db.Form.CastingDeleteForm;


public class CastingEditPage extends BasePage {
    public CastingEditPage() {
        this.add(new CastingAddForm("add_actor_theater"));
        this.add(new CastingDeleteForm("del_actor_theater"));
    }

}

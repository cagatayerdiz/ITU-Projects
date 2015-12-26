package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.TheaterTypeAddForm;
import tr.edu.itu.cs.db.Form.TheaterTypeDeleteForm;
import tr.edu.itu.cs.db.Form.TheaterTypeUpdateForm;


public class TheaterTypeEditPage extends BasePage {
    public TheaterTypeEditPage() {
        this.add(new TheaterTypeAddForm("add_theater_type"));
        this.add(new TheaterTypeDeleteForm("del_theater_type"));
        this.add(new TheaterTypeUpdateForm("update_theater_type"));
    }
}

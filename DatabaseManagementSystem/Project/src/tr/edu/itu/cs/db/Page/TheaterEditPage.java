package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.TheaterAddForm;
import tr.edu.itu.cs.db.Form.TheaterDeleteForm;
import tr.edu.itu.cs.db.Form.TheaterUpdateForm;


public class TheaterEditPage extends BasePage {
    public TheaterEditPage() {
        this.add(new TheaterAddForm("add_theater"));
        this.add(new TheaterDeleteForm("del_theater"));
        this.add(new TheaterUpdateForm("update_theater"));
    }
}

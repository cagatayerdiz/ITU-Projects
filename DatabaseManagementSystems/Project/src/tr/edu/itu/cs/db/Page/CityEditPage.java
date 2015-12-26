package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.CityAddForm;
import tr.edu.itu.cs.db.Form.CityDeleteForm;
import tr.edu.itu.cs.db.Form.CityUpdateForm;


public class CityEditPage extends BasePage {
    public CityEditPage() {
        this.add(new CityAddForm("add_city"));
        this.add(new CityDeleteForm("del_city"));
        this.add(new CityUpdateForm("update_city"));
    }
}

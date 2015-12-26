package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.LanguageAddForm;
import tr.edu.itu.cs.db.Form.LanguageDeleteForm;
import tr.edu.itu.cs.db.Form.LanguageUpdateForm;


public class LanguageEditPage extends BasePage {
    public LanguageEditPage() {
        this.add(new LanguageAddForm("add_language"));
        this.add(new LanguageDeleteForm("del_language"));
        this.add(new LanguageUpdateForm("update_language"));
    }
}

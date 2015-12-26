package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.UserAddForm;
import tr.edu.itu.cs.db.Form.UserDeleteForm;
import tr.edu.itu.cs.db.Form.UserUpdateForm;


public class UserEditPage extends BasePage {
    public UserEditPage() {
        this.add(new UserAddForm("add_user"));
        this.add(new UserDeleteForm("del_user"));
        this.add(new UserUpdateForm("update_user"));
    }
}

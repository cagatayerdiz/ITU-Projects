package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Form.EmailConfirmationForm;


public class EmailConfirmationPage extends BasePage {
    public EmailConfirmationPage(User user) {
        add(new EmailConfirmationForm("EmailConfirmationForm", user));
    }
}

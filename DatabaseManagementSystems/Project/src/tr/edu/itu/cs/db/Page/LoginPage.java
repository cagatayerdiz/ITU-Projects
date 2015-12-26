package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.LoginForm;


public class LoginPage extends BasePage {

    public LoginPage() {
        add(new LoginForm("loginForm"));
    }
}

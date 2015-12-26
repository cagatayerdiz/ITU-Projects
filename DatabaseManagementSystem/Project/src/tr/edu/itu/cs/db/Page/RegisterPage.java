package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.RegisterForm;


public class RegisterPage extends BasePage {
    public RegisterPage() {
        add(new RegisterForm("registerForm"));
    }
}

package tr.edu.itu.cs.db.Form;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import tr.edu.itu.cs.db.MySession;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Page.EmailConfirmationPage;
import tr.edu.itu.cs.db.Page.HomePage;
import tr.edu.itu.cs.db.Page.RegisterPage;


public class LoginForm extends Form {

    private TextField<String> usernameField;
    private PasswordTextField passwordField;
    private Label loginStatus;
    CaptchaForm<Void> cForm;
    User user = new User();
    final FeedbackPanel feedback;

    public LoginForm(String id) {
        super(id);
        usernameField = new TextField<String>("username", Model.of(""));
        passwordField = new PasswordTextField("password", Model.of(""));
        loginStatus = new Label("loginStatus", Model.of(""));
        add(usernameField);
        add(passwordField);
        add(loginStatus);

        Link registerPageLink = new Link("register") {
            @Override
            public void onClick() {
                this.setResponsePage(new RegisterPage());
            }
        };
        this.add(registerPageLink);

        feedback = new FeedbackPanel("feedback");
        add(feedback);
        cForm = new CaptchaForm<Void>("captchaForm");
        add(cForm);
    }

    public final void onSubmit() {
        loginStatus.setDefaultModelObject("");
        if (cForm.work()) {
            try {
                String username = (String) usernameField
                        .getDefaultModelObject();
                String password = (String) passwordField
                        .getDefaultModelObject();

                User user = (User) this.getModelObject();
                WicketApplication app = (WicketApplication) this
                        .getApplication();
                IUserCollection collection = app.getUserCollection();

                user = collection.login(username);
                if (user != null) {
                    if (user.getUserName().equals(username)) {
                        if (user.getPassword().equals(password)) {
                            app.setUser(user);
                            if (user.getIsActive()) {

                                user.setLastLogin(new java.sql.Date(System
                                        .currentTimeMillis()));
                                collection.updateUser(user);

                                ((MySession) Session.get()).setUser(user);
                                MySession.get().setUser(user);

                                this.setResponsePage(new HomePage());
                            } else {
                                this.setResponsePage(new EmailConfirmationPage(
                                        user));
                            }
                        } else {
                            loginStatus.setDefaultModelObject("Wrong Password");
                        }
                    }
                } else {
                    loginStatus.setDefaultModelObject("Wrong Username");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            feedback.error("Wrong Captcha Password");
        }

        cForm.captchaImageResource.invalidate();
        remove(cForm);
        cForm = new CaptchaForm<Void>("captchaForm");
        add(cForm);
    }
}

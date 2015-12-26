package tr.edu.itu.cs.db.Form;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Page.EmailConfirmationPage;
import tr.edu.itu.cs.db.Page.LoginPage;


public class RegisterForm extends Form {
    private Label registerStatus;
    CaptchaForm<Void> cForm;
    final FeedbackPanel feedback;

    public RegisterForm(String id) {
        super(id);
        User user = new User();
        CompoundPropertyModel model = new CompoundPropertyModel(user);
        registerStatus = new Label("registerStatus", Model.of(""));
        this.setModel(model);

        this.add(new TextField("name"));
        this.add(new TextField("surName"));
        this.add(new TextField("userName"));
        this.add(new PasswordTextField("password"));
        this.add(new TextField("email"));
        this.add(registerStatus);

        Link loginPageLink = new Link("login") {
            @Override
            public void onClick() {
                this.setResponsePage(new LoginPage());
            }
        };
        this.add(loginPageLink);

        feedback = new FeedbackPanel("feedback");
        add(feedback);
        cForm = new CaptchaForm<Void>("captchaForm");
        add(cForm);

    }

    @Override
    public void onSubmit() {
        if (cForm.work()) {
            User user = (User) this.getModelObject();
            WicketApplication app = (WicketApplication) this.getApplication();
            IUserCollection collection = app.getUserCollection();
            try {
                collection.addUser(user);
                this.setResponsePage(new EmailConfirmationPage(user));
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

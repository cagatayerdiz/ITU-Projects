package tr.edu.itu.cs.db.Form;

import java.util.UUID;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import tr.edu.itu.cs.db.Distribution;
import tr.edu.itu.cs.db.MySession;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Page.HomePage;


public class EmailConfirmationForm extends Form {

    private TextField<String> verificationField;
    User new_user = new User();
    WicketApplication app = (WicketApplication) this.getApplication();
    IUserCollection collection = app.getUserCollection();

    public EmailConfirmationForm(String id, User user) {
        super(id);
        verificationField = new TextField<String>("verificationCode",
                Model.of(""));
        add(verificationField);
        new_user = user;

        Button okButton = new Button("ok") {
            public void onSubmit() {
                String verification = (String) verificationField
                        .getDefaultModelObject();

                if (verification != null
                        && verification.equals(collection
                                .getVerification(new_user))) {

                    collection.activated(new_user);
                    ((MySession) Session.get()).setUser(new_user);
                    MySession.get().setUser(new_user);

                    this.setResponsePage(new HomePage());
                }
            }
        };
        this.add(okButton);

        Button resendButton = new Button("resend") {
            public void onSubmit() {
                new_user.setConfirmationCode(UUID.randomUUID().toString()
                        .substring(0, 10));
                collection.updateUser(new_user);
                try {
                    Distribution.sendMail(new_user);
                    collection.updateUser(new_user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.add(resendButton);

    }
}

package tr.edu.itu.cs.db.Form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Page.UserEditPage;


public class UserAddForm extends Form {

    public UserAddForm(String id) {
        super(id);

        User aUser = new User();
        CompoundPropertyModel model = new CompoundPropertyModel(aUser);
        this.setModel(model);
        TextField textUserName = new TextField("userName");
        TextField textPassword = new TextField("password");
        TextField textEmail = new TextField("email");
        TextField textName = new TextField("name");
        TextField textSurname = new TextField("surName");
        textUserName.setRequired(true);
        textPassword.setRequired(true);
        textEmail.setRequired(true);
        textName.setRequired(true);
        textSurname.setRequired(true);
        this.add(textUserName);
        this.add(textPassword);
        this.add(textEmail);
        this.add(textName);
        this.add(textSurname);

    }

    @Override
    public void onSubmit() {

        User user = (User) this.getModelObject();
        WicketApplication app = (WicketApplication) this.getApplication();
        IUserCollection collection = app.getUserCollection();
        collection.addUser(user);
        this.setResponsePage(new UserEditPage());
    }
}

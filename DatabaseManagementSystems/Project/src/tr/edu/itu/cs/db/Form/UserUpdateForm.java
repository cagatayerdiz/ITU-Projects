package tr.edu.itu.cs.db.Form;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Page.UserEditPage;


public class UserUpdateForm extends Form {

    private User selectedUser = new User();
    DropDownChoice selectbox1;
    private Boolean adminSelected, activeSelected;
    final TextField<String> userNameText = new TextField<String>("userName");
    final TextField<String> passText = new TextField<String>("password");
    final TextField<String> mailText = new TextField<String>("email");
    final TextField<String> nameText = new TextField<String>("name");
    final TextField<String> surNameText = new TextField<String>("surName");
    final TextField<String> confirmationCodeText = new TextField<String>(
            "confirmationCode");
    DropDownChoice isActiveSelect, isAdminSelect;
    final List<Boolean> trueOrFalse = Arrays.asList(false, true);

    WicketApplication app = (WicketApplication) this.getApplication();
    IUserCollection collection = app.getUserCollection();

    public UserUpdateForm(String id) {
        super(id);

        CompoundPropertyModel model = new CompoundPropertyModel(new User());
        this.setModel(model);
        List<User> users = collection.getUser();

        ChoiceRenderer userRenderer = new ChoiceRenderer("userName");
        selectbox1 = new DropDownChoice<User>("user_list",
                new PropertyModel<User>(this, "selectedUser"), users,
                userRenderer);
        this.add(selectbox1);
        userNameText.setRequired(true);
        passText.setRequired(true);
        mailText.setRequired(true);
        nameText.setRequired(true);
        surNameText.setRequired(true);
        confirmationCodeText.setRequired(true);
        this.add(userNameText);
        this.add(passText);
        this.add(mailText);
        this.add(nameText);
        this.add(surNameText);
        this.add(confirmationCodeText);
        isActiveSelect = new DropDownChoice<Boolean>("isActive",
                new PropertyModel<Boolean>(this, "activeSelected"), trueOrFalse);
        isActiveSelect.setModelObject(isActiveSelect.getChoices().get(0));
        this.add(isActiveSelect);

        isAdminSelect = new DropDownChoice<Boolean>("isAdmin",
                new PropertyModel<Boolean>(this, "adminSelected"), trueOrFalse);
        isAdminSelect.setModelObject(isAdminSelect.getChoices().get(0));
        this.add(isAdminSelect);

    }

    @Override
    public void onSubmit() {
        User user = new User();
        user.setId(selectedUser.getId());
        user.setUserName(userNameText.getModelObject());
        user.setPassword(passText.getModelObject());
        user.setEmail(mailText.getModelObject());
        user.setName(nameText.getModelObject());
        user.setSurName(surNameText.getModelObject());
        user.setConfirmationCode(confirmationCodeText.getModelObject());
        user.setIsActive(activeSelected);
        user.setIsAdmin(adminSelected);

        collection.updateUser(user);
        this.setResponsePage(new UserEditPage());
    }
}

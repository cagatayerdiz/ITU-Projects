package tr.edu.itu.cs.db.Form;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Page.UserEditPage;


public class UserDeleteForm extends Form {
    private List<User> selectedUsers;
    WicketApplication app = (WicketApplication) this.getApplication();
    IUserCollection collection = app.getUserCollection();

    public UserDeleteForm(String id) {
        super(id);
        this.selectedUsers = new LinkedList<User>();

        CheckGroup userCheckGroup = new CheckGroup("selected_users",
                this.selectedUsers);
        this.add(userCheckGroup);

        List<User> users = collection.getUser();

        PropertyListView userListView = new PropertyListView("user_list", users) {
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Check("selected", item.getModel()));
                item.add(new Label("userName"));
                item.add(new Label("password"));
                item.add(new Label("email"));
                item.add(new Label("name"));
                item.add(new Label("surName"));
                item.add(new Label("lastLogin"));
                item.add(new Label("isActive"));
                item.add(new Label("confirmationCode"));
                item.add(new Label("isAdmin"));
            }
        };
        userCheckGroup.add(userListView);
    }

    @Override
    public void onSubmit() {
        for (User user : this.selectedUsers)
            collection.deleteUser(user);
        this.setResponsePage(new UserEditPage());
    }
}

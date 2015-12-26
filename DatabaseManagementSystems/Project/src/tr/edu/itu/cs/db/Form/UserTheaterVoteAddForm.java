package tr.edu.itu.cs.db.Form;

import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Theater;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Class.UserTheaterVote;
import tr.edu.itu.cs.db.Interface.ITheaterCollection;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Interface.IUserTheaterVoteCollection;
import tr.edu.itu.cs.db.Page.UserTheaterVoteEditPage;


public class UserTheaterVoteAddForm extends Form {
    WicketApplication app = (WicketApplication) this.getApplication();
    private User selectedUser = new User();
    private Theater selectedTheater = new Theater();
    DropDownChoice selectbox1, selectbox2;
    final TextField<Integer> voteText = new TextField<Integer>("vote");

    public UserTheaterVoteAddForm(String id) {
        super(id);

        UserTheaterVote aUserTheaterVote = new UserTheaterVote();
        CompoundPropertyModel model = new CompoundPropertyModel(
                aUserTheaterVote);
        this.setModel(model);

        IUserCollection collection1 = app.getUserCollection();
        List<User> users = collection1.getUser();

        ChoiceRenderer userRenderer = new ChoiceRenderer("userName");
        selectbox1 = new DropDownChoice<User>("user_id",
                new PropertyModel<User>(this, "selectedUser"), users,
                userRenderer);
        this.add(selectbox1);

        ITheaterCollection collection2 = app.getTheaterCollection();
        List<Theater> theaters = collection2.getTheater();

        ChoiceRenderer theaterRenderer = new ChoiceRenderer("title");
        selectbox2 = new DropDownChoice<Theater>("theater_id",
                new PropertyModel<Theater>(this, "selectedTheater"), theaters,
                theaterRenderer);
        this.add(selectbox2);
        voteText.setRequired(true);
        this.add(voteText);
    }

    @Override
    public void onSubmit() {
        UserTheaterVote userTheaterVote = (UserTheaterVote) this
                .getModelObject();

        userTheaterVote.setUserId(selectedUser.getId());
        userTheaterVote.setTheaterId(selectedTheater.getId());
        if (voteText.isValid() && voteText.getModelObject() <= 5
                && voteText.getModelObject() >= 0) {
            userTheaterVote.setVote(voteText.getModelObject());
            WicketApplication app = (WicketApplication) this.getApplication();
            IUserTheaterVoteCollection collection = app
                    .getUserTheaterVoteCollection();
            collection.addUserTheaterVote(userTheaterVote);
            this.setResponsePage(new UserTheaterVoteEditPage());
        }
    }
}

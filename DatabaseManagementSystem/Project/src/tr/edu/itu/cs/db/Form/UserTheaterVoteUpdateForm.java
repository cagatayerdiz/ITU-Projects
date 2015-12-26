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


public class UserTheaterVoteUpdateForm extends Form {
    WicketApplication app = (WicketApplication) this.getApplication();
    private UserTheaterVote selectedUserTheaterVote = new UserTheaterVote();
    private User selectedUser = new User();
    private Theater selectedTheater = new Theater();
    DropDownChoice selectbox1, selectbox2, selectbox3;
    final TextField<Integer> voteText = new TextField<Integer>("vote");

    public UserTheaterVoteUpdateForm(String id) {
        super(id);

        UserTheaterVote aUserTheaterVote = new UserTheaterVote();
        CompoundPropertyModel model = new CompoundPropertyModel(
                aUserTheaterVote);
        this.setModel(model);

        IUserTheaterVoteCollection collection3 = app
                .getUserTheaterVoteCollection();
        List<UserTheaterVote> userTheaterVote = collection3
                .getUserTheaterVote();

        ChoiceRenderer userTheaterVoteRenderer = new ChoiceRenderer("id");
        selectbox3 = new DropDownChoice<UserTheaterVote>(
                "user_theater_vote_list", new PropertyModel<UserTheaterVote>(
                        this, "selectedUserTheaterVote"), userTheaterVote,
                userTheaterVoteRenderer);
        this.add(selectbox3);

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
        userTheaterVote.setId(selectedUserTheaterVote.getId());
        userTheaterVote.setUserId(selectedUser.getId());
        userTheaterVote.setTheaterId(selectedTheater.getId());
        if (voteText.isValid() && voteText.getModelObject() <= 5
                && voteText.getModelObject() >= 0) {
            userTheaterVote.setVote(voteText.getModelObject());
            WicketApplication app = (WicketApplication) this.getApplication();
            IUserTheaterVoteCollection collection = app
                    .getUserTheaterVoteCollection();
            collection.updateUserTheaterVote(userTheaterVote);
            this.setResponsePage(new UserTheaterVoteEditPage());
        }
    }
}

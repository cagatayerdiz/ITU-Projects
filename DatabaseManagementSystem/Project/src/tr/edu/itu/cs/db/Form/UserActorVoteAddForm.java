package tr.edu.itu.cs.db.Form;

import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Actor;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Class.UserActorVote;
import tr.edu.itu.cs.db.Interface.IActorCollection;
import tr.edu.itu.cs.db.Interface.IUserActorVoteCollection;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Page.UserActorVoteEditPage;


public class UserActorVoteAddForm extends Form {
    WicketApplication app = (WicketApplication) this.getApplication();
    private User selectedUser = new User();
    private Actor selectedActor = new Actor();
    DropDownChoice selectbox1, selectbox2;
    final TextField<Integer> voteText = new TextField<Integer>("vote");

    public UserActorVoteAddForm(String id) {
        super(id);

        UserActorVote aUserActorVote = new UserActorVote();
        CompoundPropertyModel model = new CompoundPropertyModel(aUserActorVote);
        this.setModel(model);

        IUserCollection collection1 = app.getUserCollection();
        List<User> users = collection1.getUser();

        ChoiceRenderer userRenderer = new ChoiceRenderer("userName");
        selectbox1 = new DropDownChoice<User>("user_id",
                new PropertyModel<User>(this, "selectedUser"), users,
                userRenderer);
        this.add(selectbox1);

        IActorCollection collection2 = app.getActorCollection();
        List<Actor> actors = collection2.getActor();

        ChoiceRenderer actorRenderer = new ChoiceRenderer("name");
        selectbox2 = new DropDownChoice<Actor>("actor_id",
                new PropertyModel<Actor>(this, "selectedActor"), actors,
                actorRenderer);
        this.add(selectbox2);
        voteText.setRequired(true);
        this.add(voteText);
    }

    @Override
    public void onSubmit() {
        UserActorVote userActorVote = (UserActorVote) this.getModelObject();

        userActorVote.setUserId(selectedUser.getId());
        userActorVote.setActorId(selectedActor.getId());
        if (voteText.isValid() && voteText.getModelObject() <= 5
                && voteText.getModelObject() >= 0) {
            userActorVote.setVote(voteText.getModelObject());
            WicketApplication app = (WicketApplication) this.getApplication();
            IUserActorVoteCollection collection = app
                    .getUserActorVoteCollection();
            collection.addUserActorVote(userActorVote);
            this.setResponsePage(new UserActorVoteEditPage());
        }
    }
}

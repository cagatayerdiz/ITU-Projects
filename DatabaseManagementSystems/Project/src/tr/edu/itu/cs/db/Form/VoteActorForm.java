package tr.edu.itu.cs.db.Form;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.MySession;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.UserActorVote;
import tr.edu.itu.cs.db.Interface.IUserActorVoteCollection;
import tr.edu.itu.cs.db.Page.ActorInformationPage;
import tr.edu.itu.cs.db.Page.LoginPage;


public class VoteActorForm extends Form {
    DropDownChoice selectbox1;
    Integer actorId;
    private Integer selectedVote;
    List<Integer> votes = Arrays.asList(0, 1, 2, 3, 4, 5);

    public VoteActorForm(String id, Integer actorId, String name, String surname) {
        super(id);
        this.actorId = actorId;
        CompoundPropertyModel model = new CompoundPropertyModel(
                new UserActorVote());
        this.setModel(model);
        selectbox1 = new DropDownChoice<Integer>("vote",
                new PropertyModel<Integer>(this, "selectedVote"), votes);
        selectbox1.setModelObject(selectbox1.getChoices().get(0));
        this.add(selectbox1);
        this.add(new Label("name", name));
        this.add(new Label("surname", surname));
    }

    @Override
    public void onSubmit() {
        if (((MySession) Session.get()).getUser() == null) {
            this.setResponsePage(new LoginPage());
        } else {
            UserActorVote userActorVote = new UserActorVote();
            userActorVote.setActorId(this.actorId);
            userActorVote.setUserId(((MySession) Session.get()).getUser()
                    .getId());
            userActorVote.setVote(selectedVote);
            WicketApplication app = (WicketApplication) this.getApplication();
            IUserActorVoteCollection collection = app
                    .getUserActorVoteCollection();
            collection.addUserActorVote(userActorVote);
            this.setResponsePage(new ActorInformationPage(this.actorId));
        }
    }

}

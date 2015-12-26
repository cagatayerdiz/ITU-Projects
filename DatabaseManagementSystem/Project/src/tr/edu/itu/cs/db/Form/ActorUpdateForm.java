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
import tr.edu.itu.cs.db.Interface.IActorCollection;
import tr.edu.itu.cs.db.Page.ActorEditPage;


public class ActorUpdateForm extends Form {

    WicketApplication app = (WicketApplication) this.getApplication();
    IActorCollection collection = app.getActorCollection();
    private Actor actor = new Actor();
    private Actor selectedActor = new Actor();
    DropDownChoice selectbox;
    final TextField<String> nameText = new TextField<String>("name");
    final TextField<String> surnameText = new TextField<String>("surname");
    final TextField<Integer> totalVote = new TextField<Integer>("totalVote");
    final TextField<Integer> voteCount = new TextField<Integer>("voteCount");

    public ActorUpdateForm(String id) {
        super(id);

        CompoundPropertyModel model = new CompoundPropertyModel(new Actor());
        this.setModel(model);

        List<Actor> actors = collection.getActor();

        ChoiceRenderer actorRenderer = new ChoiceRenderer("id");
        selectbox = new DropDownChoice<Actor>("actor_list",
                new PropertyModel<Actor>(this, "selectedActor"), actors,
                actorRenderer);
        this.add(selectbox);

        nameText.setRequired(true);
        surnameText.setRequired(true);
        totalVote.setRequired(true);
        voteCount.setRequired(true);
        this.add(totalVote);
        this.add(voteCount);
        this.add(nameText);
        this.add(surnameText);
    }

    @Override
    public void onSubmit() {
        Actor actor = new Actor();
        actor.setId(selectedActor.getId());
        actor.setName(nameText.getModelObject());
        actor.setSurname(surnameText.getModelObject());
        actor.setTotalVote(totalVote.getModelObject());
        actor.setVoteCount(voteCount.getModelObject());
        collection.updateActor(actor);
        this.setResponsePage(new ActorEditPage());
    }
}

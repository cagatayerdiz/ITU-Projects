package tr.edu.itu.cs.db.Form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Actor;
import tr.edu.itu.cs.db.Interface.IActorCollection;
import tr.edu.itu.cs.db.Page.ActorEditPage;


public class ActorAddForm extends Form {

    private WicketApplication app = (WicketApplication) this.getApplication();
    final TextField<String> nameText = new TextField<String>("name");
    final TextField<String> surnameText = new TextField<String>("surname");
    final TextField<Integer> totalVote = new TextField<Integer>("totalVote");
    final TextField<Integer> voteCount = new TextField<Integer>("voteCount");

    public ActorAddForm(String id) {
        super(id);
        CompoundPropertyModel model = new CompoundPropertyModel(new Actor());
        this.setModel(model);

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

        Actor actor = (Actor) this.getModelObject();
        actor.setName(nameText.getModelObject());
        actor.setSurname(surnameText.getModelObject());
        actor.setVoteCount(voteCount.getModelObject());
        actor.setTotalVote(totalVote.getModelObject());
        IActorCollection collection = app.getActorCollection();
        collection.addActor(actor);
        this.setResponsePage(new ActorEditPage());
    }
}

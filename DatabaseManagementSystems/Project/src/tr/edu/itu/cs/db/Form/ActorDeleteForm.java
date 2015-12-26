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
import tr.edu.itu.cs.db.Class.Actor;
import tr.edu.itu.cs.db.Interface.IActorCollection;
import tr.edu.itu.cs.db.Page.ActorEditPage;


public class ActorDeleteForm extends Form {

    WicketApplication app = (WicketApplication) this.getApplication();
    private List<Actor> selectedActors;
    IActorCollection collection = app.getActorCollection();

    public ActorDeleteForm(String id) {
        super(id);
        this.selectedActors = new LinkedList<Actor>();

        CheckGroup actorCheckGroup = new CheckGroup("selected_actors",
                this.selectedActors);
        this.add(actorCheckGroup);

        List<Actor> actors = collection.getActor();

        PropertyListView actorListView = new PropertyListView("actor_list",
                actors) {
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("id"));
                item.add(new Label("name"));
                item.add(new Label("surname"));
                item.add(new Check("selected", item.getModel()));
                item.add(new Label("voteCount"));
                item.add(new Label("totalVote"));

            }
        };
        actorCheckGroup.add(actorListView);
    }

    @Override
    public void onSubmit() {

        for (Actor actor : this.selectedActors)
            collection.deleteActor(actor);
        this.setResponsePage(new ActorEditPage());
    }
}

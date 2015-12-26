package tr.edu.itu.cs.db.Form;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Actor;
import tr.edu.itu.cs.db.Class.Casting;
import tr.edu.itu.cs.db.Class.Theater;
import tr.edu.itu.cs.db.Interface.IActorCollection;
import tr.edu.itu.cs.db.Interface.ICastingCollection;
import tr.edu.itu.cs.db.Interface.ITheaterCollection;
import tr.edu.itu.cs.db.Page.CastingEditPage;


public class CastingAddForm extends Form {
    private Theater selectedTheater = new Theater();
    private List<Actor> selectedActors;
    DropDownChoice selectbox;

    public CastingAddForm(String id) {
        super(id);

        WicketApplication app = (WicketApplication) this.getApplication();
        ITheaterCollection collection = app.getTheaterCollection();
        List<Theater> theaters = collection.getTheater();

        ChoiceRenderer theaterRenderer = new ChoiceRenderer("title");
        selectbox = new DropDownChoice<Theater>("theater_list",
                new PropertyModel<Theater>(this, "selectedTheater"), theaters,
                theaterRenderer);
        this.add(selectbox);

        this.selectedActors = new LinkedList<Actor>();

        CheckGroup actorCheckGroup = new CheckGroup("selected_actors",
                this.selectedActors);
        this.add(actorCheckGroup);

        IActorCollection collection2 = app.getActorCollection();
        List<Actor> actors = collection2.getActor();

        PropertyListView actorListView = new PropertyListView("actor_list",
                actors) {
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("name"));
                item.add(new Label("surname"));
                item.add(new Check("selected", item.getModel()));
            }
        };
        actorCheckGroup.add(actorListView);

    }

    protected void onSubmit() {
        Casting casting = new Casting();

        for (Actor actor : this.selectedActors) {
            casting.setActorId(actor.getId());
            casting.setTheaterId(selectedTheater.getId());

            WicketApplication app = (WicketApplication) this.getApplication();
            ICastingCollection collection = app.getCastingCollection();
            collection.addCasting(casting);
            this.setResponsePage(new CastingEditPage());
        }
    }
}

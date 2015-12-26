package tr.edu.itu.cs.db.Form;

import java.sql.Date;
import java.util.List;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Actor;
import tr.edu.itu.cs.db.Class.ActorComment;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.IActorCollection;
import tr.edu.itu.cs.db.Interface.IActorCommentCollection;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Page.ActorCommentEditPage;


public class ActorCommentUpdateForm extends Form {
    private ActorComment selectedActorComment = new ActorComment();
    private User selectedUser = new User();
    private Actor selectedActor = new Actor();
    DropDownChoice selectbox, selectbox2, selectbox3;
    WicketApplication app = (WicketApplication) this.getApplication();

    final TextField<String> commentText = new TextField<String>("comment");
    final DateTextField dateTextField = new DateTextField("date");

    public ActorCommentUpdateForm(String id) {
        super(id);

        CompoundPropertyModel model = new CompoundPropertyModel(
                new ActorComment());
        this.setModel(model);

        IActorCommentCollection collection3 = app.getActorCommentCollection();
        List<ActorComment> actorComments = collection3.getActorComment();

        ChoiceRenderer actorCommentRenderer = new ChoiceRenderer("id");
        selectbox3 = new DropDownChoice<ActorComment>("actor_comment_list",
                new PropertyModel<ActorComment>(this, "selectedActorComment"),
                actorComments, actorCommentRenderer);
        this.add(selectbox3);

        IUserCollection collection = app.getUserCollection();
        List<User> users = collection.getUser();
        ChoiceRenderer userRenderer = new ChoiceRenderer("userName");
        selectbox = new DropDownChoice<User>("user_list",
                new PropertyModel<User>(this, "selectedUser"), users,
                userRenderer);
        this.add(selectbox);

        IActorCollection collection2 = app.getActorCollection();
        List<Actor> actors = collection2.getActor();
        ChoiceRenderer actorRenderer = new ChoiceRenderer("name");
        selectbox2 = new DropDownChoice<Actor>("actor_list",
                new PropertyModel<Actor>(this, "selectedActor"), actors,
                actorRenderer);
        this.add(selectbox2);
        commentText.setRequired(true);
        this.add(commentText);
        DatePicker datePicker = new DatePicker();
        datePicker.setShowOnFieldClick(true);
        datePicker.setAutoHide(true);
        dateTextField.add(datePicker);
        dateTextField.setRequired(true);
        this.add(dateTextField);
    }

    @Override
    public void onSubmit() {
        ActorComment actorComment = (ActorComment) this.getModelObject();
        IActorCommentCollection collection = app.getActorCommentCollection();
        actorComment.setId(selectedActorComment.getId());
        actorComment.setActorId(selectedActor.getId());
        actorComment.setUserId(selectedUser.getId());
        actorComment.setDate((Date) dateTextField.getModelObject());
        actorComment.setComment(commentText.getModelObject());
        collection.updateActorComment(actorComment);
        this.setResponsePage(new ActorCommentEditPage());
    }
}

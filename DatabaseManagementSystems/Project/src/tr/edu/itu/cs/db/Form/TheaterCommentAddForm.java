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
import tr.edu.itu.cs.db.Class.Theater;
import tr.edu.itu.cs.db.Class.TheaterComment;
import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.ITheaterCollection;
import tr.edu.itu.cs.db.Interface.ITheaterCommentCollection;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Page.TheaterCommentEditPage;


public class TheaterCommentAddForm extends Form {
    private User selectedUser = new User();
    private Theater selectedTheater = new Theater();
    DropDownChoice selectbox, selectbox2;
    WicketApplication app = (WicketApplication) this.getApplication();

    final TextField<String> commentText = new TextField<String>("comment");
    final DateTextField dateTextField = new DateTextField("date");

    public TheaterCommentAddForm(String id) {
        super(id);

        TheaterComment aTheaterComment = new TheaterComment();
        CompoundPropertyModel model = new CompoundPropertyModel(aTheaterComment);
        this.setModel(model);

        IUserCollection collection = app.getUserCollection();
        List<User> users = collection.getUser();
        ChoiceRenderer userRenderer = new ChoiceRenderer("userName");
        selectbox = new DropDownChoice<User>("user_list",
                new PropertyModel<User>(this, "selectedUser"), users,
                userRenderer);
        this.add(selectbox);

        ITheaterCollection collection2 = app.getTheaterCollection();
        List<Theater> theaters = collection2.getTheater();
        ChoiceRenderer theaterRenderer = new ChoiceRenderer("title");
        selectbox2 = new DropDownChoice<Theater>("theater_list",
                new PropertyModel<Theater>(this, "selectedTheater"), theaters,
                theaterRenderer);
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
        TheaterComment theaterComment = (TheaterComment) this.getModelObject();
        ITheaterCommentCollection collection = app
                .getTheaterCommentCollection();
        theaterComment.setTheaterId(selectedTheater.getId());
        theaterComment.setUserId(selectedUser.getId());
        theaterComment.setDate((Date) dateTextField.getModelObject());
        theaterComment.setComment(commentText.getModelObject());
        collection.addTheaterComment(theaterComment);
        this.setResponsePage(new TheaterCommentEditPage());
    }
}

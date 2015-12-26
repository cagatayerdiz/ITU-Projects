package tr.edu.itu.cs.db.Page;

import org.apache.wicket.markup.html.form.DropDownChoice;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.UserTheaterVoteAddForm;
import tr.edu.itu.cs.db.Form.UserTheaterVoteDeleteForm;
import tr.edu.itu.cs.db.Form.UserTheaterVoteUpdateForm;


public class UserTheaterVoteEditPage extends BasePage {
    public UserTheaterVoteEditPage() {
        DropDownChoice selectbox;
        this.add(new UserTheaterVoteAddForm("add_user_theater_vote"));
        this.add(new UserTheaterVoteDeleteForm("del_user_theater_vote"));
        this.add(new UserTheaterVoteUpdateForm("update_user_theater_vote"));
    }
}

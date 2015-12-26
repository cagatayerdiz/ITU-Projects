package tr.edu.itu.cs.db.Page;

import org.apache.wicket.markup.html.form.DropDownChoice;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.UserActorVoteAddForm;
import tr.edu.itu.cs.db.Form.UserActorVoteDeleteForm;
import tr.edu.itu.cs.db.Form.UserActorVoteUpdateForm;


public class UserActorVoteEditPage extends BasePage {
    public UserActorVoteEditPage() {
        DropDownChoice selectbox;
        this.add(new UserActorVoteAddForm("add_user_actor_vote"));
        this.add(new UserActorVoteDeleteForm("del_user_actor_vote"));
        this.add(new UserActorVoteUpdateForm("update_user_actor_vote"));
    }
}

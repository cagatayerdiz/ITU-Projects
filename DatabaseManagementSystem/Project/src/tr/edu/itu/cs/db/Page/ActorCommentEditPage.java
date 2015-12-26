package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.ActorCommentAddForm;
import tr.edu.itu.cs.db.Form.ActorCommentDeleteForm;
import tr.edu.itu.cs.db.Form.ActorCommentUpdateForm;


public class ActorCommentEditPage extends BasePage {
    public ActorCommentEditPage() {
        this.add(new ActorCommentAddForm("add_actor_comment"));
        this.add(new ActorCommentDeleteForm("del_actor_comment"));
        this.add(new ActorCommentUpdateForm("update_actor_comment"));
    }
}

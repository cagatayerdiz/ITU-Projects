package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.TheaterCommentAddForm;
import tr.edu.itu.cs.db.Form.TheaterCommentDeleteForm;
import tr.edu.itu.cs.db.Form.TheaterCommentUpdateForm;


public class TheaterCommentEditPage extends BasePage {
    public TheaterCommentEditPage() {
        this.add(new TheaterCommentAddForm("add_theater_comment"));
        this.add(new TheaterCommentDeleteForm("del_theater_comment"));
        this.add(new TheaterCommentUpdateForm("update_theater_comment"));
    }
}

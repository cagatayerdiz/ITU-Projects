package tr.edu.itu.cs.db.Form;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.MySession;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.ActorComment;
import tr.edu.itu.cs.db.Interface.IActorCommentCollection;
import tr.edu.itu.cs.db.Page.ActorInformationPage;
import tr.edu.itu.cs.db.Page.LoginPage;


public class CommentActorForm extends Form {
    private Integer actorId;
    private TextField<String> commentText;

    private List<String> usernames = new LinkedList<String>();
    private List<String> comments = new LinkedList<String>();;
    private List<Date> dates = new LinkedList<Date>();;

    public CommentActorForm(String id, Integer actorId, String name,
            String surname) {
        super(id);
        this.actorId = actorId;
        CompoundPropertyModel model = new CompoundPropertyModel(
                new ActorComment());
        this.setModel(model);
        commentText = new TextField("comment");
        commentText.setRequired(true);
        this.add(commentText);
        this.add(new Label("name", name));
        this.add(new Label("surname", surname));

        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            String query = "SELECT USER_NAME, COMMENT, DATE FROM ACTOR_COMMENT JOIN USER ON USER.ID = ACTOR_COMMENT.USER_ID WHERE ACTOR_ID = ? ORDER BY DATE";

            statement = connection.prepareStatement(query);
            statement.setInt(1, actorId);
            results = statement.executeQuery();
            while (results.next()) {
                usernames.add(results.getString("USER_NAME"));
                comments.add(results.getString("COMMENT"));
                dates.add(results.getDate("DATE"));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        } finally {
            try {
                DBConnectionManager.closeConnection(connection);
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        PropertyListView actorCommentListView = new PropertyListView(
                "actor_comment_list", usernames) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("userName", usernames.get(count)));
                item.add(new Label("comment", comments.get(count)));
                item.add(new Label("date", dates.get(count)));
                if (count < usernames.size() - 1)
                    count = count + 1;
            }
        };
        this.add(actorCommentListView);
    }

    public void onSubmit() {
        if (((MySession) Session.get()).getUser() == null) {
            this.setResponsePage(new LoginPage());
        } else {
            ActorComment actorComment = new ActorComment();
            actorComment.setActorId(this.actorId);
            actorComment.setUserId(((MySession) Session.get()).getUser()
                    .getId());
            actorComment.setComment(commentText.getModelObject());
            actorComment.setDate(new Date(System.currentTimeMillis()));
            WicketApplication app = (WicketApplication) this.getApplication();
            IActorCommentCollection collection = app
                    .getActorCommentCollection();
            collection.addActorComment(actorComment);
            this.setResponsePage(new ActorInformationPage(this.actorId));
        }
    }
}

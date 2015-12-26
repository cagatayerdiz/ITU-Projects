package tr.edu.itu.cs.db.Form;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.ActorComment;
import tr.edu.itu.cs.db.Interface.IActorCommentCollection;
import tr.edu.itu.cs.db.Page.ActorCommentEditPage;


public class ActorCommentDeleteForm extends Form {
    private List<Integer> selectedIDs;

    public ActorCommentDeleteForm(String id) {
        super(id);

        this.selectedIDs = new LinkedList<Integer>();

        CheckGroup actorCheckGroup = new CheckGroup("selected_actor_comments",
                this.selectedIDs);
        this.add(actorCheckGroup);

        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        final List<Integer> IDs = new LinkedList<Integer>();
        final List<String> userNames = new LinkedList<String>();
        final List<String> names = new LinkedList<String>();
        final List<String> comments = new LinkedList<String>();
        final List<Date> dates = new LinkedList<Date>();
        try {
            String query = "SELECT ACTOR_COMMENT.ID, USER_NAME, ACTOR.NAME, COMMENT, DATE FROM ACTOR_COMMENT JOIN USER ON USER.ID = ACTOR_COMMENT.USER_ID JOIN ACTOR ON ACTOR.ID = ACTOR_COMMENT.ACTOR_ID ORDER BY DATE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                IDs.add(results.getInt("ID"));
                userNames.add(results.getString("USER_NAME"));
                names.add(results.getString("NAME"));
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

        PropertyListView actorListView = new PropertyListView(
                "actor_comment_list", IDs) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("id", IDs.get(count)));
                item.add(new Label("userName", userNames.get(count)));
                item.add(new Label("name", names.get(count)));
                item.add(new Label("comment", comments.get(count)));
                item.add(new Label("date", dates.get(count)));
                item.add(new Check("selected", item.getModel()));
                if (count < IDs.size() - 1)
                    count++;
            }
        };
        actorCheckGroup.add(actorListView);

    }

    protected void onSubmit() {
        ActorComment casting = new ActorComment();

        for (Integer x : this.selectedIDs) {
            casting.setId(x);
            WicketApplication app = (WicketApplication) this.getApplication();
            IActorCommentCollection collection = app
                    .getActorCommentCollection();
            collection.deleteActorComment(casting);
            this.setResponsePage(new ActorCommentEditPage());
        }
    }
}

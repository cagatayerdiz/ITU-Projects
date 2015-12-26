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
import tr.edu.itu.cs.db.Class.TheaterComment;
import tr.edu.itu.cs.db.Interface.ITheaterCommentCollection;
import tr.edu.itu.cs.db.Page.TheaterCommentEditPage;


public class TheaterCommentDeleteForm extends Form {
    private List<Integer> selectedIDs;

    public TheaterCommentDeleteForm(String id) {
        super(id);

        this.selectedIDs = new LinkedList<Integer>();

        CheckGroup actorCheckGroup = new CheckGroup(
                "selected_theater_comments", this.selectedIDs);
        this.add(actorCheckGroup);

        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        final List<Integer> IDs = new LinkedList<Integer>();
        final List<String> userNames = new LinkedList<String>();
        final List<String> titles = new LinkedList<String>();
        final List<String> comments = new LinkedList<String>();
        final List<Date> dates = new LinkedList<Date>();
        try {
            String query = "SELECT THEATER_COMMENT.ID, USER_NAME, TITLE, COMMENT, DATE FROM THEATER_COMMENT JOIN USER ON USER.ID = THEATER_COMMENT.USER_ID JOIN THEATER ON THEATER.ID = THEATER_COMMENT.THEATER_ID ORDER BY DATE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                IDs.add(results.getInt("ID"));
                userNames.add(results.getString("USER_NAME"));
                titles.add(results.getString("title"));
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
                "theater_comment_list", IDs) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("id", IDs.get(count)));
                item.add(new Label("userName", userNames.get(count)));
                item.add(new Label("title", titles.get(count)));
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
        TheaterComment casting = new TheaterComment();

        for (Integer x : this.selectedIDs) {
            casting.setId(x);
            WicketApplication app = (WicketApplication) this.getApplication();
            ITheaterCommentCollection collection = app
                    .getTheaterCommentCollection();
            collection.deleteTheaterComment(casting);
            this.setResponsePage(new TheaterCommentEditPage());
        }
    }
}

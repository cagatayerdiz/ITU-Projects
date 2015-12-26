package tr.edu.itu.cs.db.Form;

import java.sql.Connection;
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
import tr.edu.itu.cs.db.Class.Theater;
import tr.edu.itu.cs.db.Interface.ITheaterCollection;
import tr.edu.itu.cs.db.Page.TheaterEditPage;


public class TheaterDeleteForm extends Form {
    private List<Integer> selectedIDs;

    public TheaterDeleteForm(String id) {
        super(id);
        this.selectedIDs = new LinkedList<Integer>();

        CheckGroup theaterCheckGroup = new CheckGroup("selected_theaters",
                this.selectedIDs);
        this.add(theaterCheckGroup);

        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        final List<Integer> th_id = new LinkedList<Integer>();
        final List<String> th_title = new LinkedList<String>();
        final List<String> type = new LinkedList<String>();
        final List<String> language = new LinkedList<String>();
        final List<Integer> th_voteCount = new LinkedList<Integer>();
        final List<Integer> th_totalVote = new LinkedList<Integer>();
        try {
            String query = "SELECT THEATER.ID, TITLE, TYPE, LANGUAGE, VOTE_COUNT, TOTAL_VOTE FROM THEATER JOIN LANGUAGE ON LANGUAGE.ID = THEATER.LANGUAGE_ID JOIN THEATER_TYPE ON THEATER_TYPE.ID = THEATER.TYPE_ID ORDER BY TITLE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                th_id.add(results.getInt("ID"));
                th_title.add(results.getString("TITLE"));
                type.add(results.getString("TYPE"));
                language.add(results.getString("LANGUAGE"));
                th_voteCount.add(results.getInt("VOTE_COUNT"));
                th_totalVote.add(results.getInt("TOTAL_VOTE"));
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

        PropertyListView theaterListView = new PropertyListView("theater_list",
                th_id) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("id", th_id.get(count)));
                item.add(new Label("title", th_title.get(count)));
                item.add(new Label("type", type.get(count)));
                item.add(new Label("language", language.get(count)));
                item.add(new Label("voteCount", th_voteCount.get(count)));
                item.add(new Label("totalVote", th_totalVote.get(count)));
                item.add(new Check("selected", item.getModel()));
                if (count < th_id.size() - 1)
                    count = count + 1;
            }
        };
        theaterCheckGroup.add(theaterListView);
    }

    @Override
    public void onSubmit() {
        WicketApplication app = (WicketApplication) this.getApplication();
        ITheaterCollection collection = app.getTheaterCollection();
        for (Integer x : this.selectedIDs) {
            Theater aTheater = new Theater();
            aTheater.setId(x);
            collection.deleteTheater(aTheater);
        }
        this.setResponsePage(new TheaterEditPage());
    }
}

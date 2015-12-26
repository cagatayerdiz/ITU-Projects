package tr.edu.itu.cs.db.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.Form.CommentTheaterForm;
import tr.edu.itu.cs.db.Form.VoteTheaterForm;


public class TheaterInformationPage extends BasePage {
    String title;
    final List<String> actorNames = new LinkedList<String>();
    final List<String> actorSurnames = new LinkedList<String>();
    final List<Float> ratings = new LinkedList<Float>();

    public TheaterInformationPage(Integer theaterId) {

        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            String query = "SELECT TITLE FROM THEATER WHERE (ID=?)";
            String query2 = "SELECT NAME, SURNAME, CAST(TOTAL_VOTE AS FLOAT)/VOTE_COUNT FROM CASTING JOIN ACTOR ON ACTOR.ID = CASTING.ACTOR_ID WHERE (THEATER_ID = ?) ORDER BY NAME";
            ResultSet results = null;
            ResultSet results2 = null;
            statement = connection.prepareStatement(query);
            statement2 = connection.prepareStatement(query2);
            statement.setInt(1, theaterId);
            statement2.setInt(1, theaterId);

            results = statement.executeQuery();
            results2 = statement2.executeQuery();

            title = results.getString("TITLE");
            while (results2.next()) {
                actorNames.add(results2.getString("NAME"));
                actorSurnames.add(results2.getString("SURNAME"));
                ratings.add(results2
                        .getFloat("CAST(TOTAL_VOTE AS FLOAT)/VOTE_COUNT"));
            }

        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        } finally {
            try {
                DBConnectionManager.closeConnection(connection);
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        PropertyListView actorListView = new PropertyListView("actor_list",
                actorNames) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("name", actorNames.get(count)));
                item.add(new Label("surname", actorSurnames.get(count)));
                item.add(new Label("rating", ratings.get(count)));
                if (count < actorNames.size() - 1)
                    count = count + 1;
            }
        };
        this.add(actorListView);
        this.add(new Label("title", this.title));

        this.add(new VoteTheaterForm("add_user_theater_vote", theaterId,
                this.title));
        this.add(new CommentTheaterForm("add_theater_comment", theaterId,
                this.title));

    }

}

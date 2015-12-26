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
import tr.edu.itu.cs.db.Form.CommentActorForm;
import tr.edu.itu.cs.db.Form.VoteActorForm;


public class ActorInformationPage extends BasePage {
    String name, surname;
    final List<String> titles = new LinkedList<String>();
    final List<String> types = new LinkedList<String>();
    final List<String> languages = new LinkedList<String>();
    final List<Float> ratings = new LinkedList<Float>();

    public ActorInformationPage(Integer actorId) {

        Connection connection = DBConnectionManager.getConnection();
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            String query = "SELECT NAME, SURNAME FROM ACTOR WHERE (ID=?)";
            String query2 = "SELECT TITLE, TYPE, LANGUAGE, CAST(TOTAL_VOTE AS FLOAT)/VOTE_COUNT FROM CASTING JOIN THEATER ON THEATER.ID = CASTING.THEATER_ID JOIN THEATER_TYPE ON THEATER_TYPE.ID = THEATER.TYPE_ID JOIN LANGUAGE ON LANGUAGE.ID = THEATER.LANGUAGE_ID WHERE (ACTOR_ID = ?) ORDER BY TITLE";
            ResultSet results = null;
            ResultSet results2 = null;
            statement = connection.prepareStatement(query);
            statement2 = connection.prepareStatement(query2);
            statement.setInt(1, actorId);
            statement2.setInt(1, actorId);

            results = statement.executeQuery();
            results2 = statement2.executeQuery();

            name = results.getString("NAME");
            surname = results.getString("SURNAME");
            while (results2.next()) {
                titles.add(results2.getString("TITLE"));
                types.add(results2.getString("TYPE"));
                languages.add(results2.getString("LANGUAGE"));
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

        PropertyListView actorListView = new PropertyListView("theater_list",
                titles) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("title", titles.get(count)));
                item.add(new Label("type", types.get(count)));
                item.add(new Label("language", languages.get(count)));
                item.add(new Label("rating", ratings.get(count)));
                if (count < titles.size() - 1)
                    count = count + 1;
            }
        };
        this.add(actorListView);
        this.add(new Label("name", this.name));
        this.add(new Label("surname", this.surname));

        this.add(new VoteActorForm("add_user_actor_vote", actorId, this.name,
                this.surname));
        this.add(new CommentActorForm("add_actor_comment", actorId, this.name,
                this.surname));

    }

}

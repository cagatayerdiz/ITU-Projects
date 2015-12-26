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
import tr.edu.itu.cs.db.Class.UserActorVote;
import tr.edu.itu.cs.db.Interface.IUserActorVoteCollection;
import tr.edu.itu.cs.db.Page.UserActorVoteEditPage;


public class UserActorVoteDeleteForm extends Form {
    private List<Integer> selectedIDs;

    public UserActorVoteDeleteForm(String id) {
        super(id);

        this.selectedIDs = new LinkedList<Integer>();

        CheckGroup userActorVoteCheckGroup = new CheckGroup(
                "selected_user_actor_votes", this.selectedIDs);
        this.add(userActorVoteCheckGroup);

        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        final List<Integer> IDs = new LinkedList<Integer>();
        final List<String> userNames = new LinkedList<String>();
        final List<String> names = new LinkedList<String>();
        final List<Integer> votes = new LinkedList<Integer>();
        try {
            String query = "SELECT USER_ACTOR_VOTE.ID, USER_NAME, ACTOR.NAME, USER_ACTOR_VOTE.VOTE FROM USER_ACTOR_VOTE JOIN USER ON USER.ID = USER_ACTOR_VOTE.USER_ID JOIN ACTOR ON ACTOR.ID = USER_ACTOR_VOTE.ACTOR_ID ORDER BY ACTOR.NAME";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                IDs.add(results.getInt("ID"));
                userNames.add(results.getString("USER_NAME"));
                names.add(results.getString("NAME"));
                votes.add(results.getInt("VOTE"));
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

        PropertyListView userActorVoteListView = new PropertyListView(
                "user_actor_vote_list", IDs) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("id", IDs.get(count)));
                item.add(new Label("userName", userNames.get(count)));
                item.add(new Label("name", names.get(count)));
                item.add(new Label("vote", votes.get(count)));
                item.add(new Check("selected", item.getModel()));
                if (count < IDs.size() - 1)
                    count++;
            }
        };
        userActorVoteCheckGroup.add(userActorVoteListView);

    }

    protected void onSubmit() {
        UserActorVote userActorVote = new UserActorVote();

        for (Integer x : this.selectedIDs) {
            userActorVote.setId(x);
            WicketApplication app = (WicketApplication) this.getApplication();
            IUserActorVoteCollection collection = app
                    .getUserActorVoteCollection();
            collection.deleteUserActorVote(userActorVote);
            this.setResponsePage(new UserActorVoteEditPage());
        }
    }
}

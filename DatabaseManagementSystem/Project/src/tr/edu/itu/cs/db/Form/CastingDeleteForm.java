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
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Casting;
import tr.edu.itu.cs.db.Class.Theater;
import tr.edu.itu.cs.db.Interface.ICastingCollection;
import tr.edu.itu.cs.db.Page.CastingEditPage;


public class CastingDeleteForm extends Form {
    private Theater selectedTheater = new Theater();
    private List<Integer> selectedIDs;
    DropDownChoice selectbox;
    WicketApplication app = (WicketApplication) this.getApplication();

    public CastingDeleteForm(String id) {
        super(id);

        this.selectedIDs = new LinkedList<Integer>();

        CheckGroup actorCheckGroup = new CheckGroup("selected_actors",
                this.selectedIDs);
        this.add(actorCheckGroup);

        Connection connection = DBConnectionManager.getConnection();
        Statement statement = null;
        ResultSet results = null;
        final List<Integer> act_id = new LinkedList<Integer>();
        final List<String> th_name = new LinkedList<String>();
        final List<String> act_name = new LinkedList<String>();
        final List<String> act_surname = new LinkedList<String>();
        try {
            String query = "SELECT CASTING.ID, TITLE, NAME, SURNAME FROM CASTING JOIN ACTOR ON ACTOR.ID = CASTING.ACTOR_ID JOIN THEATER ON THEATER.ID = CASTING.THEATER_ID ORDER BY THEATER.TITLE";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                act_id.add(results.getInt("ID"));
                th_name.add(results.getString("TITLE"));
                act_name.add(results.getString("NAME"));
                act_surname.add(results.getString("SURNAME"));
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

        PropertyListView actorListView = new PropertyListView("actor_list",
                act_id) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("title", th_name.get(count)));
                item.add(new Label("name", act_name.get(count)));
                item.add(new Label("surname", act_surname.get(count)));
                item.add(new Check("selected", item.getModel()));
                if (count < act_id.size() - 1)
                    count++;
            }
        };
        actorCheckGroup.add(actorListView);

    }

    protected void onSubmit() {
        Casting casting = new Casting();

        for (Integer ID : this.selectedIDs) {
            casting.setId(ID);

            ICastingCollection collection = app.getCastingCollection();
            collection.deleteCasting(casting);
        }
        this.setResponsePage(new CastingEditPage());
    }
}

package tr.edu.itu.cs.db.Form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Actor;
import tr.edu.itu.cs.db.Page.ActorInformationPage;
import tr.edu.itu.cs.db.Page.SearchActorsPage;


public class SearchActorsForm extends Form {

    DropDownChoice selectbox1, selectbox2;

    final TextField<String> nameText = new TextField<String>("name");
    final TextField<String> surnameText = new TextField<String>("surname");

    List<Integer> ratings = Arrays.asList(0, 1, 2, 3, 4, 5);
    private Integer selectedMinRating;
    private Integer selectedMaxRating;

    WicketApplication app = (WicketApplication) this.getApplication();

    final List<Integer> ac_id = new LinkedList<Integer>();
    final List<String> ac_name = new LinkedList<String>();
    final List<String> ac_surname = new LinkedList<String>();
    final List<Integer> ac_voteCount = new LinkedList<Integer>();
    final List<Integer> ac_totalVote = new LinkedList<Integer>();
    CheckGroup actorCheckGroup = new CheckGroup("selected_actors",
            new LinkedList<Integer>());
    PropertyListView actorListView;

    public SearchActorsForm(String id) {
        super(id);
        CompoundPropertyModel model = new CompoundPropertyModel(new Actor());
        this.setModel(model);
        this.add(nameText);
        this.add(surnameText);

        selectbox1 = new DropDownChoice<Integer>("minRate",
                new PropertyModel<Integer>(this, "selectedMinRating"), ratings);
        selectbox1.setNullValid(true);
        this.add(selectbox1);

        selectbox2 = new DropDownChoice<Integer>("maxRate",
                new PropertyModel<Integer>(this, "selectedMaxRating"), ratings);
        selectbox2.setNullValid(true);
        this.add(selectbox2);

        this.add(actorCheckGroup);

        actorListView = new PropertyListView("actor_list", ac_id) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                final Integer x = ac_id.get(count);
                Link actorDisplay = new Link("actorInfo") {
                    public void onClick() {
                        setResponsePage(new ActorInformationPage(x));
                    }
                };
                actorDisplay.add(new Label("name", ac_name.get(count)));
                actorDisplay.add(new Label("surname", ac_surname.get(count)));
                item.add(actorDisplay);
                item.add(new Label("voteCount", ac_voteCount.get(count)));
                item.add(new Label("totalVote", ac_totalVote.get(count)));
                if (ac_voteCount.get(count) == 0)
                    item.add(new Label("rating", "unrated"));
                else
                    item.add(new Label("rating", (float) ac_totalVote
                            .get(count) / ac_voteCount.get(count)));
                if (count < ac_id.size() - 1)
                    count = count + 1;
            }
        };

        actorCheckGroup.add(actorListView);

        Button search = new Button("Search") {
            public void onSubmit() {
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement statement = null;
                ResultSet results = null;

                try {
                    String query = "SELECT ACTOR.ID, NAME, SURNAME, VOTE_COUNT, TOTAL_VOTE FROM ACTOR WHERE (NAME LIKE ? AND SURNAME LIKE ? AND (? IS NULL OR CAST(TOTAL_VOTE AS FLOAT)/VOTE_COUNT >= ?) AND (? IS NULL OR CAST(TOTAL_VOTE AS FLOAT)/VOTE_COUNT <=?)) ORDER BY NAME";
                    statement = connection.prepareStatement(query);
                    if (nameText.getModelObject() == null) {
                        statement.setString(1, "%%");
                    } else {
                        statement.setString(1, "%" + nameText.getModelObject()
                                + "%");
                    }
                    if (surnameText.getModelObject() == null) {
                        statement.setString(2, "%%");
                    } else {
                        statement.setString(2,
                                "%" + surnameText.getModelObject() + "%");
                    }

                    if (selectedMinRating != null) {
                        statement.setInt(3, selectedMinRating);
                        statement.setInt(4, selectedMinRating);
                    }
                    if (selectedMaxRating != null) {
                        statement.setInt(5, selectedMaxRating);
                        statement.setInt(6, selectedMaxRating);
                    }

                    results = statement.executeQuery();

                    while (results.next()) {
                        ac_id.add(results.getInt("ID"));
                        ac_name.add(results.getString("NAME"));
                        ac_surname.add(results.getString("SURNAME"));
                        ac_voteCount.add(results.getInt("VOTE_COUNT"));
                        ac_totalVote.add(results.getInt("TOTAL_VOTE"));
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

                // To do : clean old results from the list
                actorListView.remove();
                actorListView = new PropertyListView("actor_list", ac_id) {
                    Integer count = 0;

                    @Override
                    protected void populateItem(ListItem item) {
                        final Integer x = ac_id.get(count);
                        Link actorDisplay = new Link("actorInfo") {
                            public void onClick() {
                                setResponsePage(new ActorInformationPage(x));
                            }
                        };
                        actorDisplay.add(new Label("name", ac_name.get(count)));
                        actorDisplay.add(new Label("surname", ac_surname
                                .get(count)));
                        item.add(actorDisplay);
                        item.add(new Label("voteCount", ac_voteCount.get(count)));
                        item.add(new Label("totalVote", ac_totalVote.get(count)));
                        if (ac_voteCount.get(count) == 0)
                            item.add(new Label("rating", "unrated"));
                        else
                            item.add(new Label("rating", (float) ac_totalVote
                                    .get(count) / ac_voteCount.get(count)));
                        count = count + 1;
                    }
                };

                actorCheckGroup.add(actorListView);

            }
        };
        this.add(search);

        Button clean = new Button("Clean") {
            public void onSubmit() {
                this.setResponsePage(new SearchActorsPage());
            }
        };
        this.add(clean);

    }
}

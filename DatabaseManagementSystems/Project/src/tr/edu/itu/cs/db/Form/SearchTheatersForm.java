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
import org.apache.wicket.markup.html.form.ChoiceRenderer;
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
import tr.edu.itu.cs.db.Class.Language;
import tr.edu.itu.cs.db.Class.Theater;
import tr.edu.itu.cs.db.Class.TheaterType;
import tr.edu.itu.cs.db.Interface.ILanguageCollection;
import tr.edu.itu.cs.db.Interface.ITheaterTypeCollection;
import tr.edu.itu.cs.db.Page.SearchTheatersPage;
import tr.edu.itu.cs.db.Page.TheaterInformationPage;


public class SearchTheatersForm extends Form {

    private TheaterType selectedTheaterType = new TheaterType();;
    private Language selectedLanguage = new Language();
    DropDownChoice selectbox1, selectbox2, selectbox3, selectbox4;

    final TextField<String> titleText = new TextField<String>("title");

    List<Integer> ratings = Arrays.asList(0, 1, 2, 3, 4, 5);
    private Integer selectedMinRating;
    private Integer selectedMaxRating;

    WicketApplication app = (WicketApplication) this.getApplication();

    final List<Integer> th_id = new LinkedList<Integer>();
    final List<String> th_title = new LinkedList<String>();
    final List<String> type = new LinkedList<String>();
    final List<String> language = new LinkedList<String>();
    final List<Integer> th_voteCount = new LinkedList<Integer>();
    final List<Integer> th_totalVote = new LinkedList<Integer>();

    CheckGroup theaterCheckGroup = new CheckGroup("selected_theaters",
            new LinkedList<Integer>());
    PropertyListView theaterListView;

    public SearchTheatersForm(String id) {
        super(id);
        CompoundPropertyModel model = new CompoundPropertyModel(new Theater());
        this.setModel(model);
        this.add(titleText);

        ITheaterTypeCollection collection = app.getTheaterTypeCollection();
        List<TheaterType> theaterTypes = collection.getTheaterType();

        ChoiceRenderer theaterTypeRenderer = new ChoiceRenderer("type");
        selectbox1 = new DropDownChoice<TheaterType>("type_id",
                new PropertyModel<TheaterType>(this, "selectedTheaterType"),
                theaterTypes, theaterTypeRenderer);
        selectbox1.setNullValid(true);

        this.add(selectbox1);

        ILanguageCollection collection2 = app.getLanguageCollection();
        List<Language> languages = collection2.getLanguage();

        ChoiceRenderer languageRenderer = new ChoiceRenderer("language");
        selectbox2 = new DropDownChoice<Language>("language_id",
                new PropertyModel<Language>(this, "selectedLanguage"),
                languages, languageRenderer);
        selectbox2.setNullValid(true);
        this.add(selectbox2);

        selectbox3 = new DropDownChoice<Integer>("minRate",
                new PropertyModel<Integer>(this, "selectedMinRating"), ratings);
        selectbox3.setNullValid(true);
        this.add(selectbox3);
        selectbox4 = new DropDownChoice<Integer>("maxRate",
                new PropertyModel<Integer>(this, "selectedMaxRating"), ratings);
        selectbox4.setNullValid(true);
        this.add(selectbox4);

        this.add(theaterCheckGroup);

        theaterListView = new PropertyListView("theater_list", th_id) {
            Integer count = 0;

            @Override
            protected void populateItem(ListItem item) {
                final Integer x = th_id.get(count);
                Link theaterDisplay = new Link("theaterInfo") {
                    public void onClick() {
                        setResponsePage(new TheaterInformationPage(x));
                    }
                };
                theaterDisplay.add(new Label("title", th_title.get(count)));
                item.add(theaterDisplay);
                item.add(new Label("type", type.get(count)));
                item.add(new Label("language", language.get(count)));
                item.add(new Label("voteCount", th_voteCount.get(count)));
                item.add(new Label("totalVote", th_totalVote.get(count)));
                if (th_voteCount.get(count) == 0)
                    item.add(new Label("rating", "unrated"));
                else
                    item.add(new Label("rating", (float) th_totalVote
                            .get(count) / th_voteCount.get(count)));
                if (count < th_id.size() - 1)
                    count = count + 1;
            }
        };

        theaterCheckGroup.add(theaterListView);

        Button search = new Button("Search") {
            public void onSubmit() {
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement statement = null;
                ResultSet results = null;

                try {
                    String query = "SELECT THEATER.ID, TITLE, TYPE, LANGUAGE, VOTE_COUNT, TOTAL_VOTE FROM THEATER JOIN LANGUAGE ON LANGUAGE.ID = THEATER.LANGUAGE_ID JOIN THEATER_TYPE ON THEATER_TYPE.ID = THEATER.TYPE_ID WHERE (TITLE LIKE ? AND (? IS NULL OR TYPE_ID=?) AND (? IS NULL OR LANGUAGE_ID=?) AND (? IS NULL OR CAST(TOTAL_VOTE AS FLOAT)/VOTE_COUNT >= ?) AND (? IS NULL OR CAST(TOTAL_VOTE AS FLOAT)/VOTE_COUNT <=?)) ORDER BY TITLE";
                    statement = connection.prepareStatement(query);
                    if (titleText.getModelObject() == null)
                        statement.setString(1, "%%");
                    else
                        statement.setString(1, "%" + titleText.getModelObject()
                                + "%");

                    if (selectedTheaterType != null) {
                        statement.setInt(2, selectedTheaterType.getId());
                        statement.setInt(3, selectedTheaterType.getId());
                    }
                    if (selectedLanguage != null) {
                        statement.setInt(4, selectedLanguage.getId());
                        statement.setInt(5, selectedLanguage.getId());
                    }
                    if (selectedMinRating != null) {
                        statement.setInt(6, selectedMinRating);
                        statement.setInt(7, selectedMinRating);
                    }
                    if (selectedMaxRating != null) {
                        statement.setInt(8, selectedMaxRating);
                        statement.setInt(9, selectedMaxRating);
                    }

                    results = statement.executeQuery();

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

                // To do : clean old results from the list
                theaterListView.remove();
                theaterListView = new PropertyListView("theater_list", th_id) {
                    Integer count = 0;

                    @Override
                    protected void populateItem(ListItem item) {
                        final Integer x = th_id.get(count);
                        Link theaterDisplay = new Link("theaterInfo") {
                            public void onClick() {
                                setResponsePage(new TheaterInformationPage(x));
                            }
                        };
                        theaterDisplay.add(new Label("title", th_title
                                .get(count)));
                        item.add(theaterDisplay);
                        item.add(new Label("type", type.get(count)));
                        item.add(new Label("language", language.get(count)));
                        item.add(new Label("voteCount", th_voteCount.get(count)));
                        item.add(new Label("totalVote", th_totalVote.get(count)));
                        if (th_voteCount.get(count) == 0)
                            item.add(new Label("rating", "unrated"));
                        else
                            item.add(new Label("rating", (float) th_totalVote
                                    .get(count) / th_voteCount.get(count)));
                        count = count + 1;
                    }
                };

                theaterCheckGroup.add(theaterListView);

            }
        };
        this.add(search);

        Button clean = new Button("Clean") {
            public void onSubmit() {
                this.setResponsePage(new SearchTheatersPage());
            }
        };
        this.add(clean);

    }

}

package tr.edu.itu.cs.db.Form;

import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Language;
import tr.edu.itu.cs.db.Class.Theater;
import tr.edu.itu.cs.db.Class.TheaterType;
import tr.edu.itu.cs.db.Interface.ILanguageCollection;
import tr.edu.itu.cs.db.Interface.ITheaterCollection;
import tr.edu.itu.cs.db.Interface.ITheaterTypeCollection;
import tr.edu.itu.cs.db.Page.TheaterEditPage;


public class TheaterUpdateForm extends Form {
    private Theater selectedTheater = new Theater();
    private TheaterType selectedTheaterType = new TheaterType();
    private Language selectedLanguage = new Language();
    DropDownChoice selectbox1, selectbox2, selectbox3;
    final TextField<String> titleText = new TextField<String>("title");
    final TextField<Integer> voteCount = new TextField<Integer>("voteCount");
    final TextField<Integer> totalVote = new TextField<Integer>("totalVote");
    WicketApplication app = (WicketApplication) this.getApplication();

    public TheaterUpdateForm(String id) {
        super(id);

        CompoundPropertyModel model = new CompoundPropertyModel(new Theater());
        this.setModel(model);

        ITheaterCollection collection = app.getTheaterCollection();
        List<Theater> theaters = collection.getTheater();

        ChoiceRenderer theaterRenderer = new ChoiceRenderer("id");
        selectbox1 = new DropDownChoice<Theater>("theater_list",
                new PropertyModel<Theater>(this, "selectedTheater"), theaters,
                theaterRenderer);
        this.add(selectbox1);

        titleText.setRequired(true);
        this.add(titleText);

        ITheaterTypeCollection collection2 = app.getTheaterTypeCollection();
        List<TheaterType> theaterTypes = collection2.getTheaterType();

        ChoiceRenderer theaterTypeRenderer = new ChoiceRenderer("type");
        selectbox2 = new DropDownChoice<TheaterType>("type_id",
                new PropertyModel<TheaterType>(this, "selectedTheaterType"),
                theaterTypes, theaterTypeRenderer);
        this.add(selectbox2);

        ILanguageCollection collection3 = app.getLanguageCollection();
        List<Language> languages = collection3.getLanguage();

        ChoiceRenderer languageRenderer = new ChoiceRenderer("language");
        selectbox3 = new DropDownChoice<Language>("language_id",
                new PropertyModel<Language>(this, "selectedLanguage"),
                languages, languageRenderer);
        this.add(selectbox3);

        voteCount.setRequired(true);
        this.add(voteCount);
        totalVote.setRequired(true);
        this.add(totalVote);
    }

    @Override
    public void onSubmit() {
        Theater theater = new Theater();
        theater.setId(selectedTheater.getId());
        theater.setTitle(titleText.getModelObject());
        theater.setTypeId(selectedTheaterType.getId());
        theater.setLanguageId(selectedLanguage.getId());
        theater.setTotalVote(voteCount.getModelObject());
        theater.setVoteCount(totalVote.getModelObject());
        WicketApplication app = (WicketApplication) this.getApplication();
        ITheaterCollection collection = app.getTheaterCollection();
        collection.updateTheater(theater);
        this.setResponsePage(new TheaterEditPage());
    }
}

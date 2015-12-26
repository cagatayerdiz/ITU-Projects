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


public class TheaterAddForm extends Form {
    private TheaterType selectedTheaterType = new TheaterType();
    private Language selectedLanguage = new Language();
    DropDownChoice selectbox1, selectbox2;
    final TextField<String> titleText = new TextField<String>("title");
    final TextField<Integer> voteCount = new TextField<Integer>("voteCount");
    final TextField<Integer> totalVote = new TextField<Integer>("totalVote");
    WicketApplication app = (WicketApplication) this.getApplication();

    public TheaterAddForm(String id) {
        super(id);

        CompoundPropertyModel model = new CompoundPropertyModel(new Theater());
        this.setModel(model);
        titleText.setRequired(true);
        this.add(titleText);

        ITheaterTypeCollection collection = app.getTheaterTypeCollection();
        List<TheaterType> theaterTypes = collection.getTheaterType();

        ChoiceRenderer theaterTypeRenderer = new ChoiceRenderer("type");
        selectbox1 = new DropDownChoice<TheaterType>("type_id",
                new PropertyModel<TheaterType>(this, "selectedTheaterType"),
                theaterTypes, theaterTypeRenderer);
        this.add(selectbox1);

        ILanguageCollection collection2 = app.getLanguageCollection();
        List<Language> languages = collection2.getLanguage();

        ChoiceRenderer languageRenderer = new ChoiceRenderer("language");
        selectbox2 = new DropDownChoice<Language>("language_id",
                new PropertyModel<Language>(this, "selectedLanguage"),
                languages, languageRenderer);
        this.add(selectbox2);

        voteCount.setRequired(true);
        this.add(voteCount);
        totalVote.setRequired(true);
        this.add(totalVote);
    }

    @Override
    public void onSubmit() {
        Theater theater = new Theater();
        theater.setTitle(titleText.getModelObject());
        theater.setTypeId(selectedTheaterType.getId());
        theater.setLanguageId(selectedLanguage.getId());
        theater.setTotalVote(totalVote.getModelObject());
        theater.setVoteCount(voteCount.getModelObject());
        ITheaterCollection collection = app.getTheaterCollection();
        collection.addTheater(theater);
        this.setResponsePage(new TheaterEditPage());
    }
}

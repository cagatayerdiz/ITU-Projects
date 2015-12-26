package tr.edu.itu.cs.db.Form;

import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Language;
import tr.edu.itu.cs.db.Interface.ILanguageCollection;
import tr.edu.itu.cs.db.Page.LanguageEditPage;


public class LanguageUpdateForm extends Form {
    private Language selectedLanguage = new Language();
    DropDownChoice selectbox1;
    final TextField<String> titleText = new TextField<String>("language",
            Model.of(""));;

    public LanguageUpdateForm(String id) {
        super(id);

        WicketApplication app = (WicketApplication) this.getApplication();
        ILanguageCollection collection = app.getLanguageCollection();
        List<Language> languages = collection.getLanguage();

        ChoiceRenderer languageRenderer = new ChoiceRenderer("language");
        selectbox1 = new DropDownChoice<Language>("language_list",
                new PropertyModel<Language>(this, "selectedLanguage"),
                languages, languageRenderer);
        this.add(selectbox1);

        titleText.setRequired(true);
        this.add(titleText);
    }

    @Override
    public void onSubmit() {
        Language language = new Language();
        language.setId(selectedLanguage.getId());
        language.setLanguage(titleText.getModelObject());
        WicketApplication app = (WicketApplication) this.getApplication();
        ILanguageCollection collection = app.getLanguageCollection();
        collection.updateLanguage(language);
        this.setResponsePage(new LanguageEditPage());
    }
}

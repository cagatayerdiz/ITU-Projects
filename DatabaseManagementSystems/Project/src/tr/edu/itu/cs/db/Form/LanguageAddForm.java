package tr.edu.itu.cs.db.Form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Language;
import tr.edu.itu.cs.db.Interface.ILanguageCollection;
import tr.edu.itu.cs.db.Page.LanguageEditPage;


public class LanguageAddForm extends Form {
    public LanguageAddForm(String id) {
        super(id);
        Language aLanguage = new Language();
        CompoundPropertyModel model = new CompoundPropertyModel(aLanguage);
        this.setModel(model);
        TextField titleText = new TextField("language");
        titleText.setRequired(true);
        this.add(titleText);
    }

    @Override
    public void onSubmit() {
        Language language = (Language) this.getModelObject();
        WicketApplication app = (WicketApplication) this.getApplication();
        ILanguageCollection collection = app.getLanguageCollection();
        collection.addLanguage(language);
        this.setResponsePage(new LanguageEditPage());
    }
}

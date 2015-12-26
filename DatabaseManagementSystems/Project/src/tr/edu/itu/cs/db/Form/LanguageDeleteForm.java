package tr.edu.itu.cs.db.Form;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.Language;
import tr.edu.itu.cs.db.Interface.ILanguageCollection;
import tr.edu.itu.cs.db.Page.LanguageEditPage;


public class LanguageDeleteForm extends Form {
    private List<Language> selectedLanguages;

    public LanguageDeleteForm(String id) {
        super(id);
        this.selectedLanguages = new LinkedList<Language>();

        CheckGroup languageCheckGroup = new CheckGroup("selected_languages",
                this.selectedLanguages);
        this.add(languageCheckGroup);

        WicketApplication app = (WicketApplication) this.getApplication();
        ILanguageCollection collection = app.getLanguageCollection();
        List<Language> languages = collection.getLanguage();

        PropertyListView languageListView = new PropertyListView(
                "language_list", languages) {
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("language"));
                item.add(new Check("selected", item.getModel()));
            }
        };
        languageCheckGroup.add(languageListView);
    }

    @Override
    public void onSubmit() {
        WicketApplication app = (WicketApplication) this.getApplication();
        ILanguageCollection collection = app.getLanguageCollection();
        for (Language language : this.selectedLanguages)
            collection.deleteLanguage(language);
        this.setResponsePage(new LanguageEditPage());
    }
}

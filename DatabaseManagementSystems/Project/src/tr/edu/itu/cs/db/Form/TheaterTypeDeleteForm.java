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
import tr.edu.itu.cs.db.Class.TheaterType;
import tr.edu.itu.cs.db.Interface.ITheaterTypeCollection;
import tr.edu.itu.cs.db.Page.TheaterTypeEditPage;


public class TheaterTypeDeleteForm extends Form {
    private List<TheaterType> selectedTheaterTypes;

    public TheaterTypeDeleteForm(String id) {
        super(id);
        this.selectedTheaterTypes = new LinkedList<TheaterType>();

        CheckGroup languageCheckGroup = new CheckGroup("selected_types",
                this.selectedTheaterTypes);
        this.add(languageCheckGroup);

        WicketApplication app = (WicketApplication) this.getApplication();
        ITheaterTypeCollection collection = app.getTheaterTypeCollection();
        List<TheaterType> languages = collection.getTheaterType();

        PropertyListView languageListView = new PropertyListView(
                "theater_type_list", languages) {
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("type"));
                item.add(new Check("selected", item.getModel()));
            }
        };
        languageCheckGroup.add(languageListView);
    }

    @Override
    public void onSubmit() {
        WicketApplication app = (WicketApplication) this.getApplication();
        ITheaterTypeCollection collection = app.getTheaterTypeCollection();
        for (TheaterType theaterType : this.selectedTheaterTypes)
            collection.deleteTheaterType(theaterType);
        this.setResponsePage(new TheaterTypeEditPage());
    }
}

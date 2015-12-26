package tr.edu.itu.cs.db.Form;

import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.TheaterType;
import tr.edu.itu.cs.db.Interface.ITheaterTypeCollection;
import tr.edu.itu.cs.db.Page.TheaterTypeEditPage;


public class TheaterTypeUpdateForm extends Form {
    private TheaterType theaterType = new TheaterType();
    private TheaterType selectedTheaterType = new TheaterType();
    DropDownChoice selectbox;
    final TextField<String> typeText = new TextField<String>("type",
            Model.of(""));;

    public TheaterTypeUpdateForm(String id) {
        super(id);

        WicketApplication app = (WicketApplication) this.getApplication();
        typeText.setRequired(true);
        this.add(typeText);

        ITheaterTypeCollection collection = app.getTheaterTypeCollection();
        List<TheaterType> theaterTypes = collection.getTheaterType();

        ChoiceRenderer theaterTypeRenderer = new ChoiceRenderer("type");
        selectbox = new DropDownChoice<TheaterType>("type_list",
                new PropertyModel<TheaterType>(this, "selectedTheaterType"),
                theaterTypes, theaterTypeRenderer);
        this.add(selectbox);
    }

    @Override
    public void onSubmit() {
        TheaterType theaterType = new TheaterType();
        theaterType.setId(selectedTheaterType.getId());
        theaterType.setType(typeText.getModelObject());
        WicketApplication app = (WicketApplication) this.getApplication();
        ITheaterTypeCollection collection = app.getTheaterTypeCollection();
        collection.updateTheaterType(theaterType);
        this.setResponsePage(new TheaterTypeEditPage());
    }
}

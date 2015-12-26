package tr.edu.itu.cs.db.Form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.TheaterType;
import tr.edu.itu.cs.db.Interface.ITheaterTypeCollection;
import tr.edu.itu.cs.db.Page.TheaterTypeEditPage;


public class TheaterTypeAddForm extends Form {
    public TheaterTypeAddForm(String id) {
        super(id);
        TheaterType aTheaterType = new TheaterType();
        CompoundPropertyModel model = new CompoundPropertyModel(aTheaterType);
        this.setModel(model);
        TextField titleText = new TextField("type");
        titleText.setRequired(true);
        this.add(titleText);
    }

    @Override
    public void onSubmit() {
        TheaterType theaterType = (TheaterType) this.getModelObject();
        WicketApplication app = (WicketApplication) this.getApplication();
        ITheaterTypeCollection collection = app.getTheaterTypeCollection();
        collection.addTheaterType(theaterType);
        this.setResponsePage(new TheaterTypeEditPage());
    }
}

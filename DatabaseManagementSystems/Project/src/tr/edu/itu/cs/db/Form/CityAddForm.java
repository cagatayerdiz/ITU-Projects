package tr.edu.itu.cs.db.Form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.City;
import tr.edu.itu.cs.db.Interface.ICityCollection;
import tr.edu.itu.cs.db.Page.CityEditPage;


public class CityAddForm extends Form {
    public CityAddForm(String id) {
        super(id);
        City aCity = new City();
        CompoundPropertyModel model = new CompoundPropertyModel(aCity);
        this.setModel(model);
        TextField titleText = new TextField("name");
        titleText.setRequired(true);
        this.add(titleText);
    }

    @Override
    public void onSubmit() {
        City city = (City) this.getModelObject();
        WicketApplication app = (WicketApplication) this.getApplication();
        ICityCollection collection = app.getCityCollection();
        collection.addCity(city);
        this.setResponsePage(new CityEditPage());
    }
}

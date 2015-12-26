package tr.edu.itu.cs.db.Form;

import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.City;
import tr.edu.itu.cs.db.Interface.ICityCollection;
import tr.edu.itu.cs.db.Page.CityEditPage;


public class CityUpdateForm extends Form {
    private City selectedCity = new City();
    DropDownChoice selectbox1;
    final TextField<String> titleText = new TextField<String>("name",
            Model.of(""));;

    public CityUpdateForm(String id) {
        super(id);

        WicketApplication app = (WicketApplication) this.getApplication();
        ICityCollection collection = app.getCityCollection();
        List<City> citys = collection.getCity();

        ChoiceRenderer cityRenderer = new ChoiceRenderer("name");
        selectbox1 = new DropDownChoice<City>("city_list",
                new PropertyModel<City>(this, "selectedCity"), citys,
                cityRenderer);
        this.add(selectbox1);

        titleText.setRequired(true);
        this.add(titleText);
    }

    @Override
    public void onSubmit() {
        City city = new City();
        city.setId(selectedCity.getId());
        city.setName(titleText.getModelObject());
        WicketApplication app = (WicketApplication) this.getApplication();
        ICityCollection collection = app.getCityCollection();
        collection.updateCity(city);
        this.setResponsePage(new CityEditPage());
    }
}

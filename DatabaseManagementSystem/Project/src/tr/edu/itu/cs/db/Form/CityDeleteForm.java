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
import tr.edu.itu.cs.db.Class.City;
import tr.edu.itu.cs.db.Interface.ICityCollection;
import tr.edu.itu.cs.db.Page.CityEditPage;


public class CityDeleteForm extends Form {
    private List<City> selectedCities;

    public CityDeleteForm(String id) {
        super(id);
        this.selectedCities = new LinkedList<City>();

        CheckGroup cityCheckGroup = new CheckGroup("selected_cities",
                this.selectedCities);
        this.add(cityCheckGroup);

        WicketApplication app = (WicketApplication) this.getApplication();
        ICityCollection collection = app.getCityCollection();
        List<City> cities = collection.getCity();

        PropertyListView cityListView = new PropertyListView("city_list",
                cities) {
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("name"));
                item.add(new Check("selected", item.getModel()));
            }
        };
        cityCheckGroup.add(cityListView);
    }

    @Override
    public void onSubmit() {
        WicketApplication app = (WicketApplication) this.getApplication();
        ICityCollection collection = app.getCityCollection();
        for (City city : this.selectedCities)
            collection.deleteCity(city);
        this.setResponsePage(new CityEditPage());
    }
}

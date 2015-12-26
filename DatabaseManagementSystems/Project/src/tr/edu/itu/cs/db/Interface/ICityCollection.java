package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.City;


public interface ICityCollection {
    public List<City> getCity();

    public void addCity(City city);

    public void deleteCity(City city);

    public void updateCity(City city);
}

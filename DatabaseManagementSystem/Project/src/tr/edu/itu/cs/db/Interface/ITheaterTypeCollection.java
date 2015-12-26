package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.TheaterType;


public interface ITheaterTypeCollection {
    public List<TheaterType> getTheaterType();

    public void addTheaterType(TheaterType theaterType);

    public void deleteTheaterType(TheaterType theaterType);

    public void updateTheaterType(TheaterType theaterType);
}

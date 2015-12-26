package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.Theater;


public interface ITheaterCollection {
    public List<Theater> getTheater();

    public void addTheater(Theater theater);

    public void deleteTheater(Theater theater);

    public void updateTheater(Theater theater);
}

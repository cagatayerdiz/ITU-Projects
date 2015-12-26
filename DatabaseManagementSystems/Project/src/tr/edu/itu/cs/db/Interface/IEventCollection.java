package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.Event;


public interface IEventCollection {
    public List<Event> getEvent();

    public void addEvent(Event event);

    public void deleteEvent(Event event);

    public void updateEvent(Event event);
}

package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.Actor;


public interface IActorCollection {
    public List<Actor> getActor();

    public void addActor(Actor actor);

    public void deleteActor(Actor actor);

    public void updateActor(Actor actor);
}

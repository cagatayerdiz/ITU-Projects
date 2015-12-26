package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.ActorComment;


public interface IActorCommentCollection {
    public List<ActorComment> getActorComment();

    public void addActorComment(ActorComment actorComment);

    public void deleteActorComment(ActorComment actorComment);

    public void updateActorComment(ActorComment actorComment);
}

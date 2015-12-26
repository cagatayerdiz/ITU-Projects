package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.UserActorVote;


public interface IUserActorVoteCollection {
    public List<UserActorVote> getUserActorVote();

    public void addUserActorVote(UserActorVote userActorVote);

    public void deleteUserActorVote(UserActorVote userActorVote);

    public void updateUserActorVote(UserActorVote userActorVote);

}

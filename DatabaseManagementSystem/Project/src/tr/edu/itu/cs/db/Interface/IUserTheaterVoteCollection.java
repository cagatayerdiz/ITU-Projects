package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.UserTheaterVote;


public interface IUserTheaterVoteCollection {
    public List<UserTheaterVote> getUserTheaterVote();

    public void addUserTheaterVote(UserTheaterVote userTheaterVote);

    public void deleteUserTheaterVote(UserTheaterVote userTheaterVote);

    public void updateUserTheaterVote(UserTheaterVote userTheaterVote);
}

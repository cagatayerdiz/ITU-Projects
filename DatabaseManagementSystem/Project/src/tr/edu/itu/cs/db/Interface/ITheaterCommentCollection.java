package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.TheaterComment;


public interface ITheaterCommentCollection {
    public List<TheaterComment> getTheaterComment();

    public void addTheaterComment(TheaterComment theaterComment);

    public void deleteTheaterComment(TheaterComment theaterComment);

    public void updateTheaterComment(TheaterComment theaterComment);
}

package tr.edu.itu.cs.db.Class;

import java.sql.Date;


public class TheaterComment {
    private Integer id;
    private Integer userId;
    private Integer theaterId;
    private String comment;
    private java.sql.Date date;

    public TheaterComment() {
        super();

    }

    public TheaterComment(Integer id, Integer userId, Integer theaterId,
            String comment, Date date) {
        super();
        this.id = id;
        this.userId = userId;
        this.theaterId = theaterId;
        this.comment = comment;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Integer theaterId) {
        this.theaterId = theaterId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

}

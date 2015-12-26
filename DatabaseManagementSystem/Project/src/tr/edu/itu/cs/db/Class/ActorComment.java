package tr.edu.itu.cs.db.Class;

import java.sql.Date;


public class ActorComment {
    private Integer id;
    private Integer userId;
    private Integer actorId;
    private String comment;
    private java.sql.Date date;

    public ActorComment() {
        super();

    }

    public ActorComment(Integer id, Integer userId, Integer actorId,
            String comment, Date date) {
        super();
        this.id = id;
        this.userId = userId;
        this.actorId = actorId;
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

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
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

package tr.edu.itu.cs.db.Class;

public class UserActorVote {
    private Integer id;
    private Integer userId;
    private Integer actorId;
    private Integer vote;

    public UserActorVote() {
        super();
    }

    public UserActorVote(Integer id, Integer userId, Integer actorId,
            Integer vote) {
        super();
        this.id = id;
        this.userId = userId;
        this.actorId = actorId;
        this.vote = vote;

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

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

}

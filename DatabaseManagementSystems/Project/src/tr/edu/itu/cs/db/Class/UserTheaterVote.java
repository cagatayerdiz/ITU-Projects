package tr.edu.itu.cs.db.Class;

public class UserTheaterVote {
    private Integer id;
    private Integer userId;
    private Integer theaterId;
    private Integer vote;

    public UserTheaterVote() {
        super();
    }

    public UserTheaterVote(Integer id, Integer userId, Integer theaterId,
            Integer vote) {
        super();
        this.id = id;
        this.userId = userId;
        this.theaterId = theaterId;
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

    public Integer getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Integer theaterId) {
        this.theaterId = theaterId;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

}

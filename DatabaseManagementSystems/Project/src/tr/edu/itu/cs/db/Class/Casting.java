package tr.edu.itu.cs.db.Class;

public class Casting {
    private Integer id;
    private Integer theaterId;
    private Integer actorId;

    public Casting() {
        super();
    }

    public Casting(Integer id, Integer theaterId, Integer actorId) {
        super();
        this.id = id;
        this.theaterId = theaterId;
        this.actorId = actorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Integer theaterId) {
        this.theaterId = theaterId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

}

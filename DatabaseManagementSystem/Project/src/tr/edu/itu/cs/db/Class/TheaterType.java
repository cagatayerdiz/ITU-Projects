package tr.edu.itu.cs.db.Class;

public class TheaterType {
    private Integer id;
    private String type;

    public TheaterType() {
        super();
    }

    public TheaterType(Integer id, String type) {
        super();
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

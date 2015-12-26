package tr.edu.itu.cs.db.Class;

public class Actor {

    private Integer id;
    private String name;
    private String surname;
    private Integer voteCount;
    private Integer totalVote;

    public Actor() {
        super();
    }

    public Actor(Integer id, String name, String surname, Integer voteCount,
            Integer totalVote) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.voteCount = voteCount;
        this.totalVote = totalVote;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(Integer totalVote) {
        this.totalVote = totalVote;
    }

}

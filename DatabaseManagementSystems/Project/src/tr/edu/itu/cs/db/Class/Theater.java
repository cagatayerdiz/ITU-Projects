package tr.edu.itu.cs.db.Class;

public class Theater {

    private Integer id;
    private String title;
    private Integer typeId;
    private Integer languageId;
    private Integer voteCount;
    private Integer totalVote;

    public Theater() {
        super();
    }

    public Theater(Integer id, String title, Integer typeId,
            Integer languageId, Integer voteCount, Integer totalVote) {
        super();
        this.id = id;
        this.title = title;
        this.typeId = typeId;
        this.languageId = languageId;
        this.voteCount = voteCount;
        this.totalVote = totalVote;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
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

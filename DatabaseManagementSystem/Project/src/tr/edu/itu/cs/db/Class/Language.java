package tr.edu.itu.cs.db.Class;

public class Language {
    private Integer id;
    private String language;

    public Language() {
        super();
    }

    public Language(Integer id, String language) {
        super();
        this.id = id;
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}

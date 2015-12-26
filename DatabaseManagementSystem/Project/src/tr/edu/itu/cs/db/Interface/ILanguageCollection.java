package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.Language;


public interface ILanguageCollection {
    public List<Language> getLanguage();

    public void addLanguage(Language language);

    public void deleteLanguage(Language language);

    public void updateLanguage(Language language);
}

package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.Casting;


public interface ICastingCollection {
    public List<Casting> getCasting();

    public void addCasting(Casting casting);

    public void deleteCasting(Casting casting);

    public void updateCasting(Casting casting);
}

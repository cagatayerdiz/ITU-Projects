package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.SearchActorsForm;


public class SearchActorsPage extends BasePage {
    public SearchActorsPage() {
        this.add(new SearchActorsForm("search_actor"));
    }

}

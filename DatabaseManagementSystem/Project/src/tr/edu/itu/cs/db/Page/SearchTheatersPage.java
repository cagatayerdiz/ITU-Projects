package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.SearchTheatersForm;


public class SearchTheatersPage extends BasePage {
    public SearchTheatersPage() {
        this.add(new SearchTheatersForm("search_theater"));
    }

}

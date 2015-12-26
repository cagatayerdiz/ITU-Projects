package tr.edu.itu.cs.db.Page;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.SearchReservationForm;


public class SearchReservationPage extends BasePage {

    public SearchReservationPage() {
        this.add(new SearchReservationForm("search_reservation"));
    }

}

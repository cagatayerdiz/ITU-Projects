package tr.edu.itu.cs.db.Page;

import java.sql.Date;
import java.util.List;

import tr.edu.itu.cs.db.BasePage;
import tr.edu.itu.cs.db.Form.BuyTicketForm;
import tr.edu.itu.cs.db.Form.SearchEventsForm;


public class SearchEventsPage extends BasePage {
    public SearchEventsPage(final List<String> eventNames,
            final List<String> theaterTitles, final List<String> citys,
            final List<String> locations, final List<Date> dates,
            final List<Integer> soldSeats, final List<Integer> capacitys) {
        this.add(new SearchEventsForm("search_event"));
        this.add(new BuyTicketForm("results", eventNames, theaterTitles, citys,
                locations, dates, soldSeats, capacitys));
    }

}

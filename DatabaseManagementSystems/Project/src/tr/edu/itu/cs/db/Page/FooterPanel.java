package tr.edu.itu.cs.db.Page;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;


public class FooterPanel extends Panel {

    public FooterPanel(String id) {
        super(id);
        // TODO Auto-generated constructor stub
        Date now = new Date();
        this.add(new Label("datetime", now.toString())); // adding time
    }
}

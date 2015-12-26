package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

import tr.edu.itu.cs.db.Page.FooterPanel;
import tr.edu.itu.cs.db.Page.NavigationPanel;


public class BasePage extends WebPage {

    public BasePage() {
        this(null);
    }

    public BasePage(IModel model) {
        super(model);
        this.add(new NavigationPanel("mainNavigation")); // navigation panel
        this.add(new FooterPanel("footerNavigation")); // footer panel
    }
}

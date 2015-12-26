package tr.edu.itu.cs.db.Form;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import tr.edu.itu.cs.db.MySession;
import tr.edu.itu.cs.db.WicketApplication;
import tr.edu.itu.cs.db.Class.UserTheaterVote;
import tr.edu.itu.cs.db.Interface.IUserTheaterVoteCollection;
import tr.edu.itu.cs.db.Page.LoginPage;
import tr.edu.itu.cs.db.Page.TheaterInformationPage;


public class VoteTheaterForm extends Form {
    DropDownChoice selectbox1;
    Integer theaterId;
    private Integer selectedVote;
    List<Integer> votes = Arrays.asList(0, 1, 2, 3, 4, 5);

    public VoteTheaterForm(String id, Integer theaterId, String title) {
        super(id);
        this.theaterId = theaterId;
        CompoundPropertyModel model = new CompoundPropertyModel(
                new UserTheaterVote());
        this.setModel(model);
        selectbox1 = new DropDownChoice<Integer>("vote",
                new PropertyModel<Integer>(this, "selectedVote"), votes);
        selectbox1.setModelObject(selectbox1.getChoices().get(0));
        this.add(selectbox1);
        this.add(new Label("title", title));
    }

    @Override
    public void onSubmit() {
        if (((MySession) Session.get()).getUser() == null) {
            this.setResponsePage(new LoginPage());
        } else {
            UserTheaterVote userTheaterVote = new UserTheaterVote();
            userTheaterVote.setTheaterId(this.theaterId);
            userTheaterVote.setUserId(((MySession) Session.get()).getUser()
                    .getId());
            userTheaterVote.setVote(selectedVote);
            WicketApplication app = (WicketApplication) this.getApplication();
            IUserTheaterVoteCollection collection = app
                    .getUserTheaterVoteCollection();
            collection.addUserTheaterVote(userTheaterVote);
            this.setResponsePage(new TheaterInformationPage(this.theaterId));
        }
    }

}

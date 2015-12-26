package tr.edu.itu.cs.db;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import tr.edu.itu.cs.db.Class.User;
import tr.edu.itu.cs.db.Interface.IActorCollection;
import tr.edu.itu.cs.db.Interface.IActorCommentCollection;
import tr.edu.itu.cs.db.Interface.ICastingCollection;
import tr.edu.itu.cs.db.Interface.ICityCollection;
import tr.edu.itu.cs.db.Interface.IEventCollection;
import tr.edu.itu.cs.db.Interface.ILanguageCollection;
import tr.edu.itu.cs.db.Interface.IReservationCollection;
import tr.edu.itu.cs.db.Interface.ITheaterCollection;
import tr.edu.itu.cs.db.Interface.ITheaterCommentCollection;
import tr.edu.itu.cs.db.Interface.ITheaterTypeCollection;
import tr.edu.itu.cs.db.Interface.IUserActorVoteCollection;
import tr.edu.itu.cs.db.Interface.IUserCollection;
import tr.edu.itu.cs.db.Interface.IUserTheaterVoteCollection;
import tr.edu.itu.cs.db.JDBC.ActorCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.ActorCommentCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.CastingCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.CityCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.EventCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.LanguageCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.ReservationCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.TheaterCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.TheaterCommentCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.TheaterTypeCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.UserActorVoteCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.UserCollectionJDBC;
import tr.edu.itu.cs.db.JDBC.UserTheaterVoteCollectionJDBC;
import tr.edu.itu.cs.db.Page.HomePage;


public class WicketApplication extends WebApplication {
    private User user = new User();
    private IActorCollection ActorCollection;
    private IActorCommentCollection ActorCommentCollection;
    private ICastingCollection CastingCollection;
    private ICityCollection CityCollection;
    private IEventCollection EventCollection;
    private ILanguageCollection LanguageCollection;
    private IReservationCollection ReservationCollection;
    private ITheaterCollection TheaterCollection;
    private ITheaterCommentCollection TheaterCommentCollection;
    private ITheaterTypeCollection TheaterTypeCollection;
    private IUserActorVoteCollection UserActorVoteCollection;
    private IUserCollection UserCollection;
    private IUserTheaterVoteCollection UserTheaterVoteCollection;

    @Override
    public void init() {
        super.init();
        this.ActorCollection = new ActorCollectionJDBC();
        this.ActorCommentCollection = new ActorCommentCollectionJDBC();
        this.CastingCollection = new CastingCollectionJDBC();
        this.CityCollection = new CityCollectionJDBC();
        this.EventCollection = new EventCollectionJDBC();
        this.LanguageCollection = new LanguageCollectionJDBC();
        this.ReservationCollection = new ReservationCollectionJDBC();
        this.TheaterCollection = new TheaterCollectionJDBC();
        this.TheaterCommentCollection = new TheaterCommentCollectionJDBC();
        this.TheaterTypeCollection = new TheaterTypeCollectionJDBC();
        this.UserActorVoteCollection = new UserActorVoteCollectionJDBC();
        this.UserCollection = new UserCollectionJDBC();
        this.UserTheaterVoteCollection = new UserTheaterVoteCollectionJDBC();
        this.getMarkupSettings().setStripWicketTags(true);
    }

    @Override
    public final Session newSession(Request request, Response response) {
        return new MySession(request);
    }

    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    public IActorCollection getActorCollection() {
        return ActorCollection;
    }

    public IActorCommentCollection getActorCommentCollection() {
        return ActorCommentCollection;
    }

    public ICastingCollection getCastingCollection() {
        return CastingCollection;
    }

    public ICityCollection getCityCollection() {
        return CityCollection;
    }

    public IEventCollection getEventCollection() {
        return EventCollection;
    }

    public ILanguageCollection getLanguageCollection() {
        return LanguageCollection;
    }

    public IReservationCollection getReservationCollection() {
        return ReservationCollection;
    }

    public ITheaterCollection getTheaterCollection() {
        return TheaterCollection;
    }

    public ITheaterCommentCollection getTheaterCommentCollection() {
        return TheaterCommentCollection;
    }

    public ITheaterTypeCollection getTheaterTypeCollection() {
        return TheaterTypeCollection;
    }

    public IUserActorVoteCollection getUserActorVoteCollection() {
        return UserActorVoteCollection;
    }

    public IUserCollection getUserCollection() {
        return UserCollection;
    }

    public IUserTheaterVoteCollection getUserTheaterVoteCollection() {
        return UserTheaterVoteCollection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

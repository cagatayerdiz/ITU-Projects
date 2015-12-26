package tr.edu.itu.cs.db;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import tr.edu.itu.cs.db.Class.User;


public final class MySession extends WebSession {

    private User user = null;

    public MySession(Request request) {
        super(request);
    }

    public final User getUser() {
        return user;
    }

    public final void setUser(User user) {
        this.user = user;
    }

    public static MySession get() {
        return (MySession) Session.get();
    }
}

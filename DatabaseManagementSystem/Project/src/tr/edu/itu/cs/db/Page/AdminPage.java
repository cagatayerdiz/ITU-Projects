package tr.edu.itu.cs.db.Page;

import org.apache.wicket.markup.html.link.Link;

import tr.edu.itu.cs.db.BasePage;


public final class AdminPage extends BasePage {
    public AdminPage() {

        Link cityEditPageLink = new Link("cityEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new CityEditPage());
            }
        };
        this.add(cityEditPageLink);

        Link languageEditPageLink = new Link("languageEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new LanguageEditPage());
            }
        };
        this.add(languageEditPageLink);

        Link theaterTypeEditPageLink = new Link("theaterTypeEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new TheaterTypeEditPage());
            }
        };
        this.add(theaterTypeEditPageLink);

        Link actorEditPageLink = new Link("actorEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new ActorEditPage());
            }
        };
        this.add(actorEditPageLink);

        Link theaterEditPageLink = new Link("theaterEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new TheaterEditPage());
            }
        };
        this.add(theaterEditPageLink);

        Link eventEditPageLink = new Link("eventEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new EventEditPage());
            }
        };
        this.add(eventEditPageLink);

        Link castingEditPageLink = new Link("castingEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new CastingEditPage());
            }
        };
        this.add(castingEditPageLink);

        Link reservationEditPageLink = new Link("reservationEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new ReservationEditPage());
            }
        };
        this.add(reservationEditPageLink);

        Link commentEditPageLink = new Link("theaterCommentEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new TheaterCommentEditPage());
            }
        };
        this.add(commentEditPageLink);

        Link actorCommentEditPageLink = new Link("actorCommentEditPage") {

            @Override
            public void onClick() {
                this.setResponsePage(new ActorCommentEditPage());
            }
        };
        this.add(actorCommentEditPageLink);

        Link userEditPageLink = new Link("userEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new UserEditPage());
            }
        };
        this.add(userEditPageLink);

        Link userActorVoteEditPageLink = new Link("userActorVoteEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new UserActorVoteEditPage());
            }
        };
        this.add(userActorVoteEditPageLink);

        Link userTheaterVoteEditPageLink = new Link("userTheaterVoteEditPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new UserTheaterVoteEditPage());
            }
        };
        this.add(userTheaterVoteEditPageLink);
    }
}

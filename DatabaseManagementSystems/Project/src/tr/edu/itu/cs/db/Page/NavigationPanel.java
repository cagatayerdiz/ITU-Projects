package tr.edu.itu.cs.db.Page;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import tr.edu.itu.cs.db.DBConnectionManager;
import tr.edu.itu.cs.db.MySession;


public class NavigationPanel extends Panel {

    private static String linuxPath = System.getProperty("user.home")
            + "/app/WEB-INF/";

    private static String winPath = System.getProperty("user.home")
            + "\\git\\theautomata\\WebContent\\WEB-INF\\";

    private static String path;

    public NavigationPanel(String id) {
        super(id);
        // Home linking for theautomata in Navbar
        Link homePageLink1 = new Link("home1") {
            @Override
            public void onClick() {
                this.setResponsePage(new HomePage());
            }
        };
        this.add(homePageLink1);

        // About linking for Home in Navbar
        Link aboutPageLink = new Link("aboutPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new AboutUsPage());
            }
        };
        this.add(aboutPageLink);

        Link adminLink = new Link("adminPage") {
            @Override
            public void onClick() {
                this.setResponsePage(new AdminPage());
            }
        };
        this.add(adminLink);
        adminLink.setVisible(false);

        if (((MySession) Session.get()).getUser() == null) {
            // Login linking
            Link loginPageLink = new Link("loginOrLogout") {
                @Override
                public void onClick() {
                    this.setResponsePage(new LoginPage());
                }
            };

            loginPageLink.add(new Label("logStatus", "LOGIN / SIGNUP"));
            this.add(loginPageLink);
            Label infoUserName = new Label("infoUserName", "");
            infoUserName.setVisible(false);
            this.add(infoUserName);
        } else {

            Link logOutPageLink = new Link("loginOrLogout") {
                @Override
                public void onClick() {
                    ((MySession) Session.get()).setUser(null);
                    this.setResponsePage(new HomePage());
                }
            };

            logOutPageLink.add(new Label("logStatus", "LOGOUT"));
            this.add(logOutPageLink);
            this.add(new Label("infoUserName", "Welcome "
                    + ((MySession) Session.get()).getUser().getUserName()));

            if (((MySession) Session.get()).getUser().getIsAdmin()) {
                adminLink.setVisible(true);
            }
        }

        String OS = System.getProperty("os.name");
        if (OS.startsWith("Windows")) {
            path = winPath;
        } else {
            path = linuxPath;
        }

        final Label executeStatus = new Label("executeStatus", Model.of(""));
        this.add(executeStatus);

        Link sqlExecute = new Link("sqlExecute") {
            @Override
            public void onClick() {
                String line;
                Statement statement = null;
                Connection connection = DBConnectionManager.getConnection();
                try {
                    InputStream in = null;
                    in = new FileInputStream(new File(path + "sql.txt"));
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in));
                    while ((line = reader.readLine()) != null) {
                        statement = connection.createStatement();
                        statement.executeUpdate(line);
                    }
                    executeStatus.setDefaultModelObject("Execution Success");
                } catch (IOException e) {
                    throw new UnsupportedOperationException(e.getMessage());
                } catch (SQLException e) {
                    throw new UnsupportedOperationException(e.getMessage());
                } finally {
                    try {
                        DBConnectionManager.closeConnection(connection);
                        if (statement != null) {
                            statement.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                PrintWriter writer;
                try {
                    writer = new PrintWriter(path + "sql.txt");
                    writer.print("");
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        };
        this.add(sqlExecute);

        Link databaseInitializer = new Link("databaseInitializer") {
            @Override
            public void onClick() {
                String line;
                Statement statement = null;
                Connection connection = DBConnectionManager.getConnection();
                try {
                    InputStream in = null;
                    in = new FileInputStream(new File(path + "database.txt"));
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in));
                    while ((line = reader.readLine()) != null) {
                        statement = connection.createStatement();
                        statement.executeUpdate(line);
                    }
                    executeStatus.setDefaultModelObject("Execution Success");
                } catch (SQLException e) {
                    throw new UnsupportedOperationException(e.getMessage());
                } catch (IOException e) {
                    throw new UnsupportedOperationException(e.getMessage());
                } finally {
                    try {
                        DBConnectionManager.closeConnection(connection);
                        if (statement != null) {
                            statement.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.add(databaseInitializer);

        // Events linking
        Link eventsPageLink = new Link("events") {
            @Override
            public void onClick() {
                this.setResponsePage(new SearchEventsPage(null, null, null,
                        null, null, null, null));
            }
        };
        this.add(eventsPageLink);

        if (((MySession) Session.get()).getUser() == null) {
            // MyReservations linking
            Link myReservationsPageLink = new Link("myReservations") {
                @Override
                public void onClick() {
                    this.setResponsePage(new LoginPage());
                }
            };
            this.add(myReservationsPageLink);
        } else {
            Link myReservationsPageLink = new Link("myReservations") {
                @Override
                public void onClick() {
                    this.setResponsePage(new SearchReservationPage());
                }
            };
            this.add(myReservationsPageLink);
        }

        // ShowTheaters linking
        Link showTheatersPageLink = new Link("searchTheaters") {
            @Override
            public void onClick() {
                this.setResponsePage(new SearchTheatersPage());
            }
        };
        this.add(showTheatersPageLink);

        // ShowActors linking
        Link showActorsPageLink = new Link("searchActors") {
            @Override
            public void onClick() {
                this.setResponsePage(new SearchActorsPage());
            }
        };
        this.add(showActorsPageLink);

    }
}

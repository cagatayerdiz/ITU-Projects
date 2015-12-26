package tr.edu.itu.cs.db.Class;

public class Reservation {
    private Integer reservationCode;
    private String eventName;
    private Integer userId;
    private Integer numTicket;

    public Reservation() {
        super();
    }

    public Reservation(Integer reservationCode, String eventName,
            Integer userId, Integer numTicket) {
        super();
        this.reservationCode = reservationCode;
        this.eventName = eventName;
        this.userId = userId;
        this.numTicket = numTicket;
    }

    public Integer getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(Integer reservationCode) {
        this.reservationCode = reservationCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(Integer numTicket) {
        this.numTicket = numTicket;
    }

}

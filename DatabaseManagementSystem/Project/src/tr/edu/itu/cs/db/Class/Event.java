package tr.edu.itu.cs.db.Class;

import java.sql.Date;


public class Event {
    private String eventName;
    private Integer theaterId;
    private Integer cityId;
    private String location;
    private java.sql.Date date;
    private Integer soldSeat;
    private Integer capacity;
    private Integer numTicket;

    public Event() {
        super();
    }

    public Event(String eventName, Integer theaterId, Integer cityId,
            String location, Date date, Integer soldSeat, Integer capacity) {
        super();
        this.eventName = eventName;
        this.theaterId = theaterId;
        this.cityId = cityId;
        this.location = location;
        this.date = date;
        this.soldSeat = soldSeat;
        this.capacity = capacity;
        this.numTicket = capacity - soldSeat;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Integer theaterId) {
        this.theaterId = theaterId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public Integer getSoldSeat() {
        return soldSeat;
    }

    public void setSoldSeat(Integer soldSeat) {
        this.soldSeat = soldSeat;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(Integer numTicket) {
        this.numTicket = numTicket;
    }

}

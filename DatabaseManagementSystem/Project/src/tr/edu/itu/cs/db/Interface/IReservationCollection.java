package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.Reservation;


public interface IReservationCollection {
    public List<Reservation> getReservation();

    public void addReservation(Reservation reservation);

    public void deleteReservation(Reservation reservation);

    public void updateReservation(Reservation reservation);
}

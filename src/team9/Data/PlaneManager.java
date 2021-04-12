package team9.Data;

import java.util.HashMap;

import team9.Data.PClass.SeatType;

public class PlaneManager {

	public HashMap<String, Plane> plane = new HashMap<String, Plane>();
	
	private HashMap<String, User> reservation = new HashMap<String, User>(); 
	
	public User getReservation(String reservationId) {
		return reservation.get(reservationId);
	}
	
	public void reserve(User u, Plane p, SeatPosition pos, SeatType type) {
		String reservationID = ReservationID.generateID(p, pos, type);
		
		if(!u.reservationID.contains(reservationID)) { 
			u.reservationID.add(reservationID);
		}
		
		p.getClass(pos.index).setSeatType(pos.row, pos.col, type); 
	} 
	
	public void reserve(User u, String reservationId) {
		Plane p = plane.get(ReservationID.getPlaneID(reservationId));
		
		reserve(u, p, p.getSeatPosition(ReservationID.getSeatID(reservationId)), ReservationID.getSeatType(reservationId));
	} 
	
	public void cancel(String reservationId) {   
		User u = reservation.get(reservationId);
		
		Plane p = plane.get(ReservationID.getPlaneID(reservationId));
		
		SeatPosition pos = p.getSeatPosition(ReservationID.getSeatID(reservationId));
		
		u.reservationID.remove(reservationId);
		  
		p.getClass(pos.index).setSeatType(pos.row, pos.col, SeatType.NONE);
		
		reservation.remove(reservationId); 
	}  
}

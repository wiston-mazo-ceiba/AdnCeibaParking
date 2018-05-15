package co.ceiba.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class ParkingLot {

	private Collection<ParkingTicket> parking;

	public ParkingLot() {
		super();
		parking = new ArrayList<ParkingTicket>();
	}

	public boolean checkIn(ParkingTicket parkingTicket) {
		try {
			
			parking.add(parkingTicket);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean checkOut(ParkingTicket parkingTicket) {
		try {
			parking.remove(parkingTicket);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public int getCount() {
		return parking.size();
	}

	public Collection<ParkingTicket> getParking() {
		return parking;
	}

	public BigDecimal Cobrar(ParkingTicket parkingTicket) {
		// TODO Auto-generated method stub
		return null;
	}
}

package co.ceiba.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.type.LocalDateTimeType;

import co.ceiba.domain.ParkingLot;
import co.ceiba.domain.ParkingTicket;
import co.ceiba.domain.VehicleType;
import co.ceiba.exceptions.ParkingException;

public class ParkingService {

	private ParkingLot parkingLot;

	public ParkingService() {
		parkingLot = new ParkingLot();
	}

	public ParkingTicket checkIn(ParkingTicket parkingTicket) {
		isValidParkingTicket(parkingTicket);
		parkingTicket.setCheckInDate(LocalDateTime.now());
		parkingLot.checkIn(parkingTicket);
		return parkingTicket;
	}

	public ParkingTicket checkOut(ParkingTicket parkingTicket) {
		isValidParkingTicket(parkingTicket);
		parkingTicket.setCheckOutDate(LocalDateTime.now());
		parkingTicket.setServiceCost(parkingLot.Cobrar(parkingTicket));
		parkingLot.checkOut(parkingTicket);
		return parkingTicket;
	}

	public void isValidParkingTicket(ParkingTicket parkingTicket) throws ParkingException {
		// no puede estar vac�o ni el tiquete ni el veh�culo
		if (parkingTicket == null)
			throw new ParkingException("El par�metro proporcionado parkingTicket no puede estar vac�o");

		if (parkingTicket.getParkedVehicle() == null)
			throw new ParkingException("El par�metro proporcionado parkingTicket no contiene un veh�culo");

		VehicleType vt = parkingTicket.getParkedVehicle().getType();

		// Solo est�n permitidos los carros y las motos
		if (vt != VehicleType.CAR && vt != VehicleType.MOTORCYCLE)
			throw new ParkingException("El  tipo de veh�culo ingresado no es� permitido");
	}

	public int getCount() {
		return parkingLot.getCount();
	}

	public Collection<ParkingTicket> getParking() {
		return parkingLot.getParking() ;
	}
}

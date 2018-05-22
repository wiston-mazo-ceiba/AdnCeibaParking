package co.ceiba.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import co.ceiba.domain.ParkingTicket;
import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;

public interface IParkingService {

	ParkingTicket checkIn(String licencePlate, VehicleType vehicleType, int cylinderCapacity);

	ParkingTicket checkIn(ParkingTicket parkingTicket);

	ParkingTicket checkIn(ParkingTicket parkingTicket, LocalDateTime dateNow);

	ParkingTicket checkOut(ParkingTicket parkingTicket);

	ParkingTicket checkOut(ParkingTicket parkingTicket, LocalDateTime dateNow);

	void isValidParkingTicket(ParkingTicket parkingTicket);

	void isValidParkingTicketForCheckOut(String ticketNumber);

	void isAllowedParkingTicket(ParkingTicket parkingTicket, LocalDateTime dateNow);

	int getCount(VehicleType vehicleType);

	BigDecimal cobrarTarifa(ParkingTicket parkingTicket);

	BigDecimal cobrarImpuestoCilindraje(ParkingTicket parkingTicket);

	BigDecimal cobrarEstadiaNormal(ParkingTicket parkingTicket);

	BigDecimal getValorHora(VehicleType vehicleType);

	BigDecimal getValorDia(VehicleType vehicleType);

	List<ParkingTicket> findAllParkingTickets();

	List<Vehicle> findAllVehicles();

	ParkingTicket findByLicencePlateAndCheckOutDateIsNull(String licencePlate);

	ParkingTicket findByTicketNumber(String ticketNumber);

	List<Vehicle> findVehicleByLicencePlateContains(String licencePlate);

	Vehicle findVehicleByLicencePlate(String licencePlate);

	List<ParkingTicket> findActiveParkingTickets();

	List<ParkingTicket> findActiveByVehicleType(VehicleType vehicleType);

}
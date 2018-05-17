package co.ceiba.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ceiba.adapter.VehicleAdapter;
import co.ceiba.domain.ParkingTicket;
import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;
import co.ceiba.exceptions.ParkingException;
import co.ceiba.util.SystemMessages;

//Clase que se encarga del control del parqueadero
@Service("parkingService")
public class ParkingService {

	private final VehicleAdapter vehicleAdapter;

	public ParkingService() {
		this.vehicleAdapter = new VehicleAdapter();
	}

	public ParkingService(VehicleAdapter vehicleAdapter) {
		this.vehicleAdapter = vehicleAdapter;
	}

	public ParkingTicket checkIn(String licencePlate, VehicleType vehicleType, int cylinderCapacity) {
		Vehicle parkedVehicle = new Vehicle(licencePlate, cylinderCapacity, vehicleType);
		ParkingTicket parkingTicket = new ParkingTicket();
		parkingTicket.setParkedVehicle(parkedVehicle);

		return checkIn(parkingTicket);
	}

	public ParkingTicket checkIn(ParkingTicket parkingTicket) {
		return checkIn(parkingTicket, LocalDateTime.now());
	}

	public ParkingTicket checkIn(ParkingTicket parkingTicket, LocalDateTime dateNow) {
		isValidParkingTicket(parkingTicket);
		isAllowedParkingTicket(parkingTicket, dateNow);
		parkingTicket.setCheckInDate(dateNow);
		parkingTicket.setTicketNumber(createTicket(parkingTicket.getParkedVehicle(), dateNow));
		if (vehicleAdapter.saveTicket(parkingTicket) == SystemMessages.SAVED_TICKET.getText())
			return parkingTicket;
		return null;
	}

	private String createTicket(Vehicle parkedVehicle, LocalDateTime dateNow) {
		return String.format("%s-%d%d%d%d%d%d", parkedVehicle.getType().getVehicleType().substring(0, 1),
				getCount(parkedVehicle.getType()), dateNow.getHour(), dateNow.getMinute(), dateNow.getSecond(),
				dateNow.getDayOfYear(), dateNow.getYear());
	}

	public ParkingTicket checkOut(ParkingTicket parkingTicket) {
		return checkOut(parkingTicket, LocalDateTime.now());
	}

	public ParkingTicket checkOut(ParkingTicket parkingTicket, LocalDateTime dateNow) {
		isValidParkingTicket(parkingTicket);
		parkingTicket.setCheckOutDate(dateNow);
		parkingTicket.setServiceCost(cobrarTarifa(parkingTicket));
		vehicleAdapter.saveTicket(parkingTicket);
		return parkingTicket;
	}

	public void isValidParkingTicket(ParkingTicket parkingTicket) {
		// no puede estar vacío ni el tiquete ni el vehículo
		if (parkingTicket == null)
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_EMPTY_PARKING_TICKET.getText());

		if (parkingTicket.getParkedVehicle() == null)
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_EMPTY_VEHICLE_IN_PARKING_TICKET.getText());

		VehicleType vt = parkingTicket.getParkedVehicle().getType();

		// Solo están permitidos los carros y las motos
		if (vt == null)
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_EMPTY_VEHICLE_TYPE.getText());
		if (vt != VehicleType.CAR && vt != VehicleType.MOTORCYCLE)
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_NOT_ALLOWED_VEHICLE_TYPE.getText());
	}

	public void isAllowedParkingTicket(ParkingTicket parkingTicket, LocalDateTime dateNow) {

		if (consultarPlacaProhibida(parkingTicket.getParkedVehicle().getLicencePlate(), dateNow))
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_UNAUTHORIZED_VEHICLE.getText());

		if ((parkingTicket.getParkedVehicle().getType() == VehicleType.MOTORCYCLE)
				&& (getCount(VehicleType.MOTORCYCLE) >= 10))
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_FULL_MOTORCYCLE_PARKING_LOTS.getText());

		if ((parkingTicket.getParkedVehicle().getType() == VehicleType.CAR) && (getCount(VehicleType.CAR) >= 20))
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_FULL_CAR_PARKING_LOTS.getText());
	}

	private boolean consultarPlacaProhibida(String licencePlate, LocalDateTime dateNow) {
		return licencePlate.substring(0, 1).equals("A")
				&& (dateNow.getDayOfWeek() != DayOfWeek.SUNDAY && dateNow.getDayOfWeek() != DayOfWeek.MONDAY);
	}

	public int getCount(VehicleType vehicleType) {
		return vehicleAdapter.findActiveByVehicleType(vehicleType).size();
	}

	// ================================================================================================================================

	public BigDecimal cobrarTarifa(ParkingTicket parkingTicket) {
		BigDecimal valorACobrar = BigDecimal.ZERO;

		valorACobrar = valorACobrar.add(cobrarEstadiaNormal(parkingTicket));
		valorACobrar = valorACobrar.add(cobrarImpuestoCilindraje(parkingTicket));

		return valorACobrar;
	}

	public BigDecimal cobrarImpuestoCilindraje(ParkingTicket parkingTicket) {
		if (parkingTicket.getParkedVehicle().getType() == VehicleType.MOTORCYCLE
				&& (parkingTicket.getParkedVehicle().getCylinderCapacity() > 500))
			return new BigDecimal(2000);
		return BigDecimal.ZERO;
	}

	public BigDecimal cobrarEstadiaNormal(ParkingTicket parkingTicket) {
		double horasTotales = (double) ChronoUnit.MINUTES.between(parkingTicket.getCheckInDate(),
				parkingTicket.getCheckOutDate()) / 60.0;
		double dias = Math.floor((horasTotales / 24.0));
		double horasAdicionales = Math.round((horasTotales % 24.0));
		if ((horasTotales % 24.0) >= 9.00) {
			dias += 1.0;
			horasAdicionales = 0.0;
		}
		BigDecimal valorTotal = BigDecimal.valueOf(dias)
				.multiply(getValorDia(parkingTicket.getParkedVehicle().getType()));
		valorTotal = valorTotal.add(BigDecimal.valueOf(horasAdicionales)
				.multiply(getValorHora(parkingTicket.getParkedVehicle().getType())));

		return valorTotal;
	}

	public BigDecimal getValorHora(VehicleType vehicleType) {
		if (vehicleType == VehicleType.CAR) {
			return BigDecimal.valueOf(1000);
		}
		if (vehicleType == VehicleType.MOTORCYCLE) {
			return BigDecimal.valueOf(500);
		} else {
			return BigDecimal.valueOf(0);
		}

	}

	public BigDecimal getValorDia(VehicleType vehicleType) {
		if (vehicleType == VehicleType.CAR) {
			return BigDecimal.valueOf(8000);
		}
		if (vehicleType == VehicleType.MOTORCYCLE) {
			return BigDecimal.valueOf(4000);
		} else {
			return BigDecimal.valueOf(0);
		}
	}

	public List<ParkingTicket> findAllParkingTickets() {
		return vehicleAdapter.findAllParkingTickets();
	}

	public List<Vehicle> findAllVehicles() {
		return vehicleAdapter.findAllVehicles();
	}
	public ParkingTicket findByLicencePlateAndCheckOutDateIsNull(String licencePlate) {
		return vehicleAdapter.findByLicencePlateAndCheckOutDateIsNull(licencePlate);
	}
	public List<ParkingTicket> findByLicencePlateAndDay(String licencePlate,LocalDateTime dateOfCheckIn) {
		return vehicleAdapter.findByLicencePlateAndDay(licencePlate, dateOfCheckIn);
	}
}

package co.ceiba.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ceiba.adapter.IParkingAdapter;
import co.ceiba.adapter.ParkingAdapter;
import co.ceiba.domain.ParkingTicket;
import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;
import co.ceiba.exceptions.ParkingException;
import co.ceiba.util.SystemMessages;

//Clase que se encarga del control del parqueadero
@Service("parkingService")
public class ParkingService implements IParkingService {
	private static final double HORAS_DE_UN_DIA = 24.0;
	private static final double LIMITE_HORAS_X_DIA = 9.00;
	@Autowired
	private IParkingAdapter vehicleAdapter;

	public ParkingService() {
		if(vehicleAdapter == null) 
			this.vehicleAdapter = new ParkingAdapter();
	}

	public ParkingService(IParkingAdapter vehicleAdapter) {
		this.vehicleAdapter = vehicleAdapter;
	}

	@Override
	public ParkingTicket checkIn(String licencePlate, VehicleType vehicleType, int cylinderCapacity) {
		Vehicle parkedVehicle = new Vehicle(licencePlate, cylinderCapacity, vehicleType);
		ParkingTicket parkingTicket = new ParkingTicket();
		parkingTicket.setParkedVehicle(parkedVehicle);

		return checkIn(parkingTicket);
	}

	@Override
	public ParkingTicket checkIn(ParkingTicket parkingTicket) {
		return checkIn(parkingTicket, LocalDateTime.now());
	}

	@Override
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

	@Override
	public ParkingTicket checkOut(ParkingTicket parkingTicket) {
		return checkOut(parkingTicket, LocalDateTime.now());
	}

	@Override
	public ParkingTicket checkOut(ParkingTicket parkingTicket, LocalDateTime dateNow) {
		isValidParkingTicket(parkingTicket);
		isValidParkingTicketForCheckOut(parkingTicket.getTicketNumber());
		parkingTicket.setCheckOutDate(dateNow);
		parkingTicket.setServiceCost(cobrarTarifa(parkingTicket));
		vehicleAdapter.saveTicket(parkingTicket);
		return parkingTicket;
	}

	@Override
	public void isValidParkingTicket(ParkingTicket parkingTicket) {
		
		// no puede estar vacío ni el tiquete ni el vehículo
		validarObjetoNulo(parkingTicket, SystemMessages.PARKING_EXCEPTION_EMPTY_PARKING_TICKET);
		
		validarObjetoNulo(parkingTicket.getParkedVehicle(), SystemMessages.PARKING_EXCEPTION_EMPTY_VEHICLE_IN_PARKING_TICKET);
		
		VehicleType vt = parkingTicket.getParkedVehicle().getType();
		
		validarObjetoNulo(vt, SystemMessages.PARKING_EXCEPTION_EMPTY_VEHICLE_TYPE);
		
		if (vt != VehicleType.CAR && vt != VehicleType.MOTORCYCLE)
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_NOT_ALLOWED_VEHICLE_TYPE.getText());
	}

	private void validarObjetoNulo(Object parkingTicket, SystemMessages mensaje) {
		if (parkingTicket == null)
			throw new ParkingException(mensaje.getText());
	}
	@Override
	public void isValidParkingTicketForCheckOut(String ticketNumber) {
		ParkingTicket parkingTicket = this.findByTicketNumber(ticketNumber);
		
		if (parkingTicket == null || ticketNumber == null)
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_NO_VALID_PARKING_TICKET_NUMBER.getText());
		
		if (parkingTicket.getCheckOutDate() != null)
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_ALREADY_USED_PARKING_TICKET_NUMBER.getText());
	}

	@Override
	public void isAllowedParkingTicket(ParkingTicket parkingTicket, LocalDateTime dateNow) {

		if (consultarPlacaProhibida(parkingTicket.getParkedVehicle().getLicencePlate(), dateNow))
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_UNAUTHORIZED_VEHICLE.getText());

		if ((parkingTicket.getParkedVehicle().getType() == VehicleType.MOTORCYCLE)
				&& (getCount(VehicleType.MOTORCYCLE) >= 10))
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_FULL_MOTORCYCLE_PARKING_LOTS.getText());

		if ((parkingTicket.getParkedVehicle().getType() == VehicleType.CAR) && (getCount(VehicleType.CAR) >= 20))
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_FULL_CAR_PARKING_LOTS.getText());
		
		parkingTicket = this.findByLicencePlateAndCheckOutDateIsNull(parkingTicket.getParkedVehicle().getLicencePlate());
		if (parkingTicket != null) {
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_ALREADY_REGISTERED_VEHICLE.getText());
		} 
	}

	private boolean consultarPlacaProhibida(String licencePlate, LocalDateTime dateNow) {
		return licencePlate.substring(0, 1).equals("A")
				&& (dateNow.getDayOfWeek() != DayOfWeek.SUNDAY && dateNow.getDayOfWeek() != DayOfWeek.MONDAY);
	}

	@Override
	public int getCount(VehicleType vehicleType) {
		return vehicleAdapter.findActiveByVehicleType(vehicleType).size();
	}

	// ================================================================================================================================

	@Override
	public BigDecimal cobrarTarifa(ParkingTicket parkingTicket) {
		BigDecimal valorACobrar = BigDecimal.ZERO;

		valorACobrar = valorACobrar.add(cobrarEstadiaNormal(parkingTicket));
		valorACobrar = valorACobrar.add(cobrarImpuestoCilindraje(parkingTicket));

		return valorACobrar;
	}

	@Override
	public BigDecimal cobrarImpuestoCilindraje(ParkingTicket parkingTicket) {
		if (parkingTicket.getParkedVehicle().getType() == VehicleType.MOTORCYCLE
				&& (parkingTicket.getParkedVehicle().getCylinderCapacity() > 500))
			return new BigDecimal(2000);
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal cobrarEstadiaNormal(ParkingTicket parkingTicket) {
		double horasTotales = (double) ChronoUnit.MINUTES.between(parkingTicket.getCheckInDate(),
				parkingTicket.getCheckOutDate()) / 60.0;
		double dias = Math.floor((horasTotales / 24.0));
		double horasAdicionales = Math.round((horasTotales % HORAS_DE_UN_DIA));
		if ((horasTotales % 24.0) >= LIMITE_HORAS_X_DIA) {
			dias += 1.0;
			horasAdicionales = 0.0;
		}
		BigDecimal valorTotal = BigDecimal.valueOf(dias)
				.multiply(getValorDia(parkingTicket.getParkedVehicle().getType()));
		valorTotal = valorTotal.add(BigDecimal.valueOf(horasAdicionales)
				.multiply(getValorHora(parkingTicket.getParkedVehicle().getType())));

		return valorTotal;
	}

	@Override
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

	@Override
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

	@Override
	public List<ParkingTicket> findAllParkingTickets() {
		return vehicleAdapter.findAllParkingTickets();
	}

	@Override
	public List<Vehicle> findAllVehicles() {
		return vehicleAdapter.findAllVehicles();
	}
	@Override
	public ParkingTicket findByLicencePlateAndCheckOutDateIsNull(String licencePlate) {
		return vehicleAdapter.findByLicencePlateAndCheckOutDateIsNull(licencePlate);
	}
	@Override
	public ParkingTicket findByTicketNumber(String ticketNumber) {
		return vehicleAdapter.findByTicketNumber(ticketNumber);
	}
	@Override
	public Vehicle findVehicleByLicencePlate(String licencePlate) {
		return vehicleAdapter.findVehicleByLicencePlate(licencePlate);
	}
	@Override
	public List<Vehicle> findVehicleByLicencePlateContains(String licencePlate) {
		return vehicleAdapter.findVehicleByLicencePlateContains(licencePlate);
	}
	@Override
	public List<ParkingTicket> findActiveParkingTickets() {
		return vehicleAdapter.findActiveParkingTickets();
	}
	@Override
	public List<ParkingTicket> findActiveByVehicleType(VehicleType  vehicleType){
		return vehicleAdapter.findActiveByVehicleType(vehicleType);
	}
}

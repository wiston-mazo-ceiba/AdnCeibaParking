package co.ceiba.adapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ceiba.domain.ParkingTicket;
import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;
import co.ceiba.entity.ParkingTicketEntity;
import co.ceiba.entity.VehicleEntity;
import co.ceiba.repository.ParkingTicketRepository;
import co.ceiba.repository.VehicleRepository;
import co.ceiba.util.SystemMessages;

@Service("vehicleAdapter")
public class VehicleAdapter {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private ParkingTicketRepository parkingTicketRepository;
	private List<Vehicle> vehiclesCollection = new ArrayList<>();

	public VehicleAdapter() {
		super();
	}

	public VehicleEntity toEntity(Vehicle toConvert) {
		VehicleEntity vehicleEntity = vehicleRepository.findByLicencePlate(toConvert.getLicencePlate());
		vehicleEntity.setCylinderCapacity(toConvert.getCylinderCapacity());
		vehicleEntity.setLicencePlate(toConvert.getLicencePlate());
		vehicleEntity.setType(toConvert.getType());
		return vehicleEntity;
	}
	public Vehicle toDomain(VehicleEntity toConvert) {
		return new Vehicle(toConvert.getLicencePlate(), toConvert.getCylinderCapacity(), toConvert.getType());
	}

	public ParkingTicketEntity toEntity(ParkingTicket toConvert) {
		ParkingTicketEntity parkingTicketEntity = parkingTicketRepository
				.findByTicketNumber(toConvert.getTicketNumber());
		parkingTicketEntity.setCheckInDate(toConvert.getCheckInDate());
		parkingTicketEntity.setCheckOutDate(toConvert.getCheckOutDate());
		parkingTicketEntity.setParkedVehicle(toEntity(toConvert.getParkedVehicle()));
		parkingTicketEntity.setServiceCost(toConvert.getServiceCost());
		return parkingTicketEntity;
	}

	public ParkingTicket toDomain(ParkingTicketEntity toConvert) {
		ParkingTicket parkingTicket = new ParkingTicket();

		parkingTicket.setCheckInDate(toConvert.getCheckInDate());
		parkingTicket.setCheckOutDate(toConvert.getCheckOutDate());
		parkingTicket.setParkedVehicle(toDomain(toConvert.getParkedVehicle()));
		parkingTicket.setServiceCost(toConvert.getServiceCost());
		parkingTicket.setTicketNumber(toConvert.getTicketNumber());

		return parkingTicket;
	}
	public List<ParkingTicket> toDomainList(List<ParkingTicketEntity> toConvert) {
		List<ParkingTicket> parkingTickets = new ArrayList<>();
		for (ParkingTicketEntity parkingTicketEntity : toConvert) {
			parkingTickets.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTickets;
	}
	public List<ParkingTicket> findAllParkingTickets(){
		List<ParkingTicket>  parkingTicketsCollection = new ArrayList<>();
		Iterable<ParkingTicketEntity> findAllResult = parkingTicketRepository.findAll();
		for (ParkingTicketEntity parkingTicketEntity : findAllResult) {
			parkingTicketsCollection.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTicketsCollection;
	}
	public List<Vehicle> findAllVehicles(){
		Iterable<VehicleEntity> findAllResult = vehicleRepository.findAll();
		for (VehicleEntity vehicleEntity : findAllResult) {
			vehiclesCollection.add(this.toDomain(vehicleEntity));
		}
		return vehiclesCollection;
	}
	
	public String saveVehicle(Vehicle vehicleToSave) {
		try {
			vehicleRepository.save(this.toEntity(vehicleToSave));
			return SystemMessages.SAVED_VEHICLE.getText();
		} catch (Exception e) {
			return SystemMessages.NOT_SAVED_VECHICLE.getText() +": {" + e.getMessage() + "}";
		}
	}
	
	public String saveTicket(ParkingTicket parkingTicketToSave) {
		try {
			parkingTicketRepository.save(this.toEntity((parkingTicketToSave)));
			return SystemMessages.SAVED_TICKET.getText();
		} catch (Exception e) {
			return SystemMessages.NOT_SAVED_TICKET.getText() +": {" + e.getMessage() + "}";
		}
	}
	public String saveTickets(List<ParkingTicket> parkingTicketToSave) {
		try {
			List<ParkingTicketEntity> tickets = new ArrayList<>();
			
			for (ParkingTicket parkingTicket : parkingTicketToSave) {
				tickets.add(toEntity(parkingTicket));
			}
			parkingTicketRepository.saveAll(tickets);
			return SystemMessages.SAVED_TICKETS.getText();
		} catch (Exception e) {
			return SystemMessages.NOT_SAVED_TICKET.getText() +": {" + e.getMessage() + "}";
		}
	}

	public List<ParkingTicket> findActiveParkingTickets(){
		List<ParkingTicket> parkingTicketsCollection = new ArrayList<>();
		Iterable<ParkingTicketEntity> findAllResult = parkingTicketRepository.findByCheckOutDateIsNull();
		for (ParkingTicketEntity parkingTicketEntity : findAllResult) {
			parkingTicketsCollection.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTicketsCollection;
	}
	public List<ParkingTicket> findActiveByVehicleType(VehicleType vehicleType){
		List<ParkingTicket> parkingTicketsCollection = new ArrayList<>();
		Iterable<ParkingTicketEntity> findAllResult = parkingTicketRepository.findActiveByVehicleType(vehicleType);
		for (ParkingTicketEntity parkingTicketEntity : findAllResult) {
			parkingTicketsCollection.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTicketsCollection;
	}
	
	public ParkingTicket findByTicketNumber(String ticketNumber) {
		ParkingTicketEntity parkingTicketEntity =  parkingTicketRepository.findByTicketNumber(ticketNumber);
		if(parkingTicketEntity == null)
			return null;
		return this.toDomain(parkingTicketEntity);
	}
	
	public ParkingTicket findByLicencePlateAndCheckOutDateIsNull(String licencePlate) {
		List<ParkingTicketEntity> parkingTicketEntity =  parkingTicketRepository.findByLicencePlateAndCheckOutDateIsNull(licencePlate);
		if(parkingTicketEntity == null || parkingTicketEntity.isEmpty())
			return null;
		return this.toDomain(parkingTicketEntity.get(0));
	}
	
	public List<ParkingTicket> findByLicencePlateAndDay(String licencePlate,LocalDateTime dateOfCheckIn) {
		List<ParkingTicket> parkingTicketsCollection = new ArrayList<>();
		Iterable<ParkingTicketEntity> findAllResult =   parkingTicketRepository.findByLicencePlateAndDay(licencePlate,dateOfCheckIn);
		for (ParkingTicketEntity parkingTicketEntity : findAllResult) {
			parkingTicketsCollection.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTicketsCollection;
	}	
	

	public List<ParkingTicket> findByLicencePlate(String licencePlate) {
		List<ParkingTicket> parkingTicketsCollection = new ArrayList<>();
		Iterable<ParkingTicketEntity> findAllResult =   parkingTicketRepository.findByLicencePlate(licencePlate);
		for (ParkingTicketEntity parkingTicketEntity : findAllResult) {
			parkingTicketsCollection.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTicketsCollection;
	}
}

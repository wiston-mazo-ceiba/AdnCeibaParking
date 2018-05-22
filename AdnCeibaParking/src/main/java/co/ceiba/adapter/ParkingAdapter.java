package co.ceiba.adapter;

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

@Service("parkingAdapter")
public class ParkingAdapter implements IParkingAdapter {

	@Autowired
	public VehicleRepository vehicleRepository;

	@Autowired
	public ParkingTicketRepository parkingTicketRepository;
	
	public ParkingAdapter() {
		super();
	}
	/*====================================================================================================================
	 * ================================== conversores de dominio a entidad =================================================
	 *==================================================================================================================== 
	 * */
	@Override
	public VehicleEntity toEntity(Vehicle toConvert) {
		VehicleEntity vehicleEntity = vehicleRepository.findByLicencePlate(toConvert.getLicencePlate());
		if(vehicleEntity == null)
			vehicleEntity = new VehicleEntity();
		vehicleEntity.setCylinderCapacity(toConvert.getCylinderCapacity());
		vehicleEntity.setLicencePlate(toConvert.getLicencePlate());
		vehicleEntity.setType(toConvert.getType());
		return vehicleEntity;
	}
	@Override
	public ParkingTicketEntity toEntity(ParkingTicket toConvert) {
		ParkingTicketEntity parkingTicketEntity = parkingTicketRepository
				.findByTicketNumber(toConvert.getTicketNumber());
		if (parkingTicketEntity == null)
			parkingTicketEntity = new ParkingTicketEntity();
		parkingTicketEntity.setCheckInDate(toConvert.getCheckInDate());
		parkingTicketEntity.setCheckOutDate(toConvert.getCheckOutDate());
		parkingTicketEntity.setParkedVehicle(toEntity(toConvert.getParkedVehicle()));
		parkingTicketEntity.setServiceCost(toConvert.getServiceCost());
		return parkingTicketEntity;
	}
	
	/*====================================================================================================================
	 * ================================== conversores de entidad a dominio =================================================
	 *==================================================================================================================== 
	 * */
	@Override
	public Vehicle toDomain(VehicleEntity toConvert) {
		return new Vehicle(toConvert.getLicencePlate(), toConvert.getCylinderCapacity(), toConvert.getType());
	}


	@Override
	public ParkingTicket toDomain(ParkingTicketEntity toConvert) {
		ParkingTicket parkingTicket = new ParkingTicket();

		parkingTicket.setCheckInDate(toConvert.getCheckInDate());
		parkingTicket.setCheckOutDate(toConvert.getCheckOutDate());
		parkingTicket.setParkedVehicle(toDomain(toConvert.getParkedVehicle()));
		parkingTicket.setServiceCost(toConvert.getServiceCost());
		parkingTicket.setTicketNumber(toConvert.getTicketNumber());

		return parkingTicket;
	}
	@Override
	public List<ParkingTicket> toDomainList(List<ParkingTicketEntity> toConvert) {
		List<ParkingTicket> parkingTickets = new ArrayList<>();
		for (ParkingTicketEntity parkingTicketEntity : toConvert) {
			parkingTickets.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTickets;
	}

	/*====================================================================================================================
	 * ================================== Guardados en Repositorio =================================================
	 *==================================================================================================================== 
	 * */	
	@Override
	public String saveVehicle(Vehicle vehicleToSave) {
		try {
			vehicleRepository.save(this.toEntity(vehicleToSave));
			return SystemMessages.SAVED_VEHICLE.getText();
		} catch (Exception e) {
			return SystemMessages.NOT_SAVED_VECHICLE.getText() +": {" + e.getMessage() + "}";
		}
	}
	
	@Override
	public String saveTicket(ParkingTicket parkingTicketToSave) {
		try {
			vehicleRepository.save(this.toEntity(parkingTicketToSave.getParkedVehicle()));
			parkingTicketRepository.save(this.toEntity((parkingTicketToSave)));
			return SystemMessages.SAVED_TICKET.getText();
		} catch (Exception e) {
			return SystemMessages.NOT_SAVED_TICKET.getText() +": {" + e.getMessage() + "}";
		}
	}
	@Override
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
	
	/*====================================================================================================================
	 * ================================== Consultas al Repositorio =================================================
	 *==================================================================================================================== 
	 * */
	@Override
	public List<ParkingTicket> findAllParkingTickets(){
		List<ParkingTicket>  parkingTicketsCollection = new ArrayList<>();
		Iterable<ParkingTicketEntity> findAllResult = parkingTicketRepository.findAll();
		for (ParkingTicketEntity parkingTicketEntity : findAllResult) {
			parkingTicketsCollection.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTicketsCollection;
	}
	@Override
	public List<Vehicle> findAllVehicles(){
		Iterable<VehicleEntity> findAllResult = vehicleRepository.findAll();
		List<Vehicle> vehiclesCollection = new ArrayList<>();
		for (VehicleEntity vehicleEntity : findAllResult) {
			vehiclesCollection.add(this.toDomain(vehicleEntity));
		}
		return vehiclesCollection;
	}
	
	@Override
	public List<ParkingTicket> findActiveParkingTickets(){
		List<ParkingTicket> parkingTicketsCollection = new ArrayList<>();
		Iterable<ParkingTicketEntity> findAllResult = parkingTicketRepository.findByCheckOutDateIsNull();
		for (ParkingTicketEntity parkingTicketEntity : findAllResult) {
			parkingTicketsCollection.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTicketsCollection;
	}
	@Override
	public List<ParkingTicket> findActiveByVehicleType(VehicleType vehicleType){
		List<ParkingTicket> parkingTicketsCollection = new ArrayList<>();
		Iterable<ParkingTicketEntity> findAllResult = parkingTicketRepository.findByVehicleTypeAndCheckOutDateIsNull(vehicleType);
		for (ParkingTicketEntity parkingTicketEntity : findAllResult) {
			parkingTicketsCollection.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTicketsCollection;
	}
	
	@Override
	public ParkingTicket findByTicketNumber(String ticketNumber) {
		ParkingTicketEntity parkingTicketEntity =  parkingTicketRepository.findByTicketNumber(ticketNumber);
		if(parkingTicketEntity == null)
			return null;
		return this.toDomain(parkingTicketEntity);
	}
	
	@Override
	public ParkingTicket findByLicencePlateAndCheckOutDateIsNull(String licencePlate) {
		List<ParkingTicketEntity> parkingTicketEntity =  parkingTicketRepository.findByLicencePlateAndCheckOutDateIsNull(licencePlate);
		if(parkingTicketEntity == null || parkingTicketEntity.isEmpty())
			return null;
		return this.toDomain(parkingTicketEntity.get(0));
	}
	@Override
	public List<ParkingTicket> findByLicencePlate(String licencePlate) {
		List<ParkingTicket> parkingTicketsCollection = new ArrayList<>();
		Iterable<ParkingTicketEntity> findAllResult =   parkingTicketRepository.findTicketsByLicencePlate(licencePlate);
		for (ParkingTicketEntity parkingTicketEntity : findAllResult) {
			parkingTicketsCollection.add(this.toDomain(parkingTicketEntity));
		}
		return parkingTicketsCollection;
	}
	@Override
	public List<Vehicle>findVehicleByLicencePlateContains(String licencePlate) {
		List<Vehicle> vehiclesCollection = new ArrayList<>();
		Iterable<VehicleEntity> findAllResult =   vehicleRepository.findByLicencePlateContains(licencePlate);
		for (VehicleEntity vehicleEntity : findAllResult) {
			vehiclesCollection.add(this.toDomain(vehicleEntity));
		}
		return vehiclesCollection;
	}
	@Override
	public Vehicle findVehicleByLicencePlate(String licencePlate) {
		vehicleRepository.findByLicencePlate(licencePlate);
		return null;
	}
}

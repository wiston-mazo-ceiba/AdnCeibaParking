package co.ceiba.adapter;

import java.util.List;

import co.ceiba.domain.ParkingTicket;
import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;
import co.ceiba.entity.ParkingTicketEntity;
import co.ceiba.entity.VehicleEntity;

public interface IParkingAdapter {

	VehicleEntity toEntity(Vehicle toConvert);

	Vehicle toDomain(VehicleEntity toConvert);

	ParkingTicketEntity toEntity(ParkingTicket toConvert);

	ParkingTicket toDomain(ParkingTicketEntity toConvert);

	List<ParkingTicket> toDomainList(List<ParkingTicketEntity> toConvert);

	List<ParkingTicket> findAllParkingTickets();

	List<Vehicle> findAllVehicles();

	String saveVehicle(Vehicle vehicleToSave);

	String saveTicket(ParkingTicket parkingTicketToSave);

	String saveTickets(List<ParkingTicket> parkingTicketToSave);

	List<ParkingTicket> findActiveParkingTickets();

	List<ParkingTicket> findActiveByVehicleType(VehicleType vehicleType);

	ParkingTicket findByTicketNumber(String ticketNumber);

	ParkingTicket findByLicencePlateAndCheckOutDateIsNull(String licencePlate);
	/*	
		public List<ParkingTicket> findByLicencePlateAndDay(String licencePlate,LocalDateTime dateOfCheckIn) {
			List<ParkingTicket> parkingTicketsCollection = new ArrayList<>();
			Iterable<ParkingTicketEntity> findAllResult =   parkingTicketRepository.findByLicencePlateAndDay(licencePlate,dateOfCheckIn);
			for (ParkingTicketEntity parkingTicketEntity : findAllResult) {
				parkingTicketsCollection.add(this.toDomain(parkingTicketEntity));
			}
			return parkingTicketsCollection;
		}	
		*/

	List<ParkingTicket> findByLicencePlate(String licencePlate);

	Vehicle findVehicleByLicencePlate(String licencePlate);

	List<Vehicle> findVehicleByLicencePlateContains(String licencePlate);

}
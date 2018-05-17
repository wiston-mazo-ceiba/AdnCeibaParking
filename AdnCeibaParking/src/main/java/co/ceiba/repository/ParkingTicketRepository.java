package co.ceiba.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

//import java.time.LocalDate;
//import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.ceiba.domain.VehicleType;
import co.ceiba.entity.ParkingTicketEntity;

@Repository("parkingTicketRepository")
public interface ParkingTicketRepository extends CrudRepository<ParkingTicketEntity, Long> {
	public ParkingTicketEntity findByTicketNumber(String ticketNumber);
	
	public List<ParkingTicketEntity> findByLicencePlateAndCheckOutDateIsNull(String licencePlate);
	
	public List<ParkingTicketEntity> findByCheckOutDateIsNull();
	
	@Query("select p from parking_ticket p where p.licence_plate = ?1 and date(p.check_in_date) = date(?2)")
	public List<ParkingTicketEntity> findByLicencePlateAndDay(String licencePlate,LocalDateTime dateOfCheckIn);
	@Query("select p from parking_ticket p inner join vehicle v on p.parked_vehicle_vehicle_id ) v.vehicle_id where v.vehicle_type = ?1 and p.check_out_date is null")
	public List<ParkingTicketEntity> findActiveByVehicleType(VehicleType vehicleType);
	
	public List<ParkingTicketEntity> findByLicencePlate(String licencePlate);
}
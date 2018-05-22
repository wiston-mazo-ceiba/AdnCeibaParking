package co.ceiba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

//import java.time.LocalDate;
//import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.ceiba.domain.VehicleType;
import co.ceiba.entity.ParkingTicketEntity;

@Repository("parkingTicketRepository")
public interface ParkingTicketRepository extends CrudRepository<ParkingTicketEntity, Long> {

	public ParkingTicketEntity findByTicketNumber(String ticketNumber);

	@Query(value = "select p.* from parking_ticket p inner join vehicle v on p.parked_vehicle_vehicle_id = v.vehicle_id where v.licence_plate = :licencePlate and p.check_out_date IS NULL", nativeQuery = true)
	public List<ParkingTicketEntity> findByLicencePlateAndCheckOutDateIsNull(
			@Param("licencePlate") String licencePlate);

	public List<ParkingTicketEntity> findByCheckOutDateIsNull();

	@Query(value = "select p.* from parking_ticket p inner join vehicle v on p.parked_vehicle_vehicle_id = v.vehicle_id where v.vehicle_type = :vehicleType and p.check_out_date IS NULL", nativeQuery = true)
	public List<ParkingTicketEntity> findByVehicleTypeAndCheckOutDateIsNull(
			@Param("vehicleType") VehicleType vehicleType);

	@Query(value = "select p.* from parking_ticket p inner join vehicle v on p.parked_vehicle_vehicle_id = v.vehicle_id where v.licence_plate = ?1;", nativeQuery = true)
	public List<ParkingTicketEntity> findTicketsByLicencePlate(String licencePlate);

}
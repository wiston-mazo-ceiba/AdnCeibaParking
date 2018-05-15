package co.ceiba.repository;

//import java.time.LocalDate;
//import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.ceiba.entity.ParkingTicketEntity;

@Repository("parkingTicketRepository")
public interface ParkingTicketRepository extends CrudRepository<ParkingTicketEntity, Long> {
	//public ParkingTicketEntity findByTicketNumber(String ticketNumber);
	//public List<ParkingTicketEntity> findByLicencePlate(String licencePlate,LocalDate dateOfCheckIn);
}
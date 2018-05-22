package co.ceiba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.ceiba.domain.ParkingTicket;
import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;
import co.ceiba.service.IParkingService;

@RestController
@RequestMapping(path = "/api/ticket")
public class TicketController {

	@Autowired
	IParkingService parkingService;

	@GetMapping("/check/{placa}")
	public ParkingTicket checkInAllowed(@PathVariable("placa") String licencePlate) {
		ParkingTicket parkingTicket = parkingService.findByLicencePlateAndCheckOutDateIsNull(licencePlate);
		if (parkingTicket == null)
			parkingTicket = new ParkingTicket();
		return parkingTicket;
	}

	@PostMapping("/check")
	public ParkingTicket checkInAllowed2(@RequestBody Vehicle vehicle) {
		return parkingService.findByLicencePlateAndCheckOutDateIsNull(vehicle.getLicencePlate());
	}

	@GetMapping("/checkInRest/{licencePlate}/{vehicleType}/{cylinderCapacity}")
	public ParkingTicket doCheckIn(@PathVariable("licencePlate") String licencePlate,
			@PathVariable("vehicleType") VehicleType vehicleType,
			@PathVariable(required = false, value = "cylinderCapacity") int cylinderCapacity) {
		return parkingService.checkIn(licencePlate, vehicleType, cylinderCapacity);
	}

	@PostMapping("/checkIn")
	public ParkingTicket doCheckIn(@RequestBody Vehicle vehicle) {
		return  parkingService.checkIn(vehicle.getLicencePlate(), vehicle.getType(),
					vehicle.getCylinderCapacity());
	}

	@GetMapping("/checkOutRest/{ticketNumber}")
	public ParkingTicket doCheckOut(@PathVariable("ticketNumber") String ticketNumber) {
		ParkingTicket parkingTicket = parkingService.findByTicketNumber(ticketNumber);
		return parkingService.checkOut(parkingTicket);
	}
	@PostMapping("/checkOutRest")
	public ParkingTicket doCheckOut2(@RequestBody ParkingTicket parkingTicket) {
		return parkingService.checkOut(parkingTicket);
	}

	@GetMapping("/all")
	public @ResponseBody Iterable<ParkingTicket> getAllTickets() {
		return parkingService.findAllParkingTickets();
	}
}

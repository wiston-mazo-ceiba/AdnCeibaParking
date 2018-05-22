package co.ceiba.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.ceiba.domain.Vehicle;
import co.ceiba.exceptions.ParkingException;
import co.ceiba.service.IParkingService;
import co.ceiba.util.SystemMessages;

@RestController
@RequestMapping(path="/api/vehicle")
public class VehicleController {
	@Autowired
	public IParkingService parkingService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public boolean add(@RequestBody Vehicle vehicle) {
		if (parkingService.findByLicencePlateAndCheckOutDateIsNull(vehicle.getLicencePlate()) != null)
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_ALREADY_REGISTERED_VEHICLE.getText());
		parkingService.checkIn(vehicle.getLicencePlate(), vehicle.getType(), vehicle.getCylinderCapacity());
		return true;
	}

	@GetMapping("/search/{placa}")
	public List<Vehicle> findVehiclesByLicence(@PathVariable("placa") String licencePlate) {
		List<Vehicle> vehicles = parkingService.findVehicleByLicencePlateContains(licencePlate);
		if (vehicles == null)
			vehicles = new ArrayList<>();
		return vehicles;
	}
	@GetMapping("/find/{placa}")
	public Vehicle searchVehicle2(@PathVariable("placa") String licencePlate) {
		Vehicle vehicle = parkingService.findVehicleByLicencePlate(licencePlate);
		if (vehicle == null)
			vehicle = new Vehicle();
		return vehicle;
	}
		
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody Iterable<Vehicle> getAllVehicles() {
		return parkingService.findAllVehicles();
	}
}

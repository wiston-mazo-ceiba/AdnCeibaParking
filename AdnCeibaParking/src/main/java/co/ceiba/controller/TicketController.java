package co.ceiba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;
import co.ceiba.service.ParkingService;

@RestController
@RequestMapping(path="/api/vehicle")
public class TicketController {
	ParkingService parkingService = new ParkingService();
	@GetMapping(path="/find")
	public String addVehicle (@RequestParam String licencePlate, @RequestParam String vehicleType, @RequestParam(defaultValue="0") int cylinderCapacity) {
		try {
			parkingService.checkIn(licencePlate, VehicleType.valueOf(vehicleType), cylinderCapacity);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "Saved";
	}
	/*@PostMapping (path="/add")
	public String addNewUser (@RequestParam String licencePlate, @RequestParam int cylinderCapacity
			, @RequestParam String vehicleType) {
		try {

			VehicleEntity v = new VehicleEntity();
			v.setCylinderCapacity(cylinderCapacity);
			v.setLicencePlate(licencePlate);
			v.setType(VehicleType.valueOf(VehicleType.class, vehicleType));
			vehicleRepository.save(v);
		} catch (Exception e) {
			return "Not Saved";
		}
		return "Saved";
	}*/
	
/*
 * @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
 */
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Vehicle> getAllVehicles() {
		return parkingService.findAllVehicles();
	}
}

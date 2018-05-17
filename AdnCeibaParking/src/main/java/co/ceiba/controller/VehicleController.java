package co.ceiba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;
import co.ceiba.exceptions.ParkingException;
import co.ceiba.service.ParkingService;
import co.ceiba.util.SystemMessages;

@RestController
@RequestMapping(path="/api/vehicle")
public class VehicleController {
	ParkingService parkingService = new ParkingService();
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public boolean add(@RequestBody Vehicle vehicle) {
		if (parkingService.findByLicencePlateAndCheckOutDateIsNull(vehicle.getLicencePlate()) != null)
			throw new ParkingException(SystemMessages.PARKING_EXCEPTION_ALREADY_REGISTERED_VEHICLE.getText());
		parkingService.checkIn(vehicle.getLicencePlate(), vehicle.getType(), vehicle.getCylinderCapacity());
		return true;
		/*
		if(!watchman.vehicleTypeAllowed(vehicle.getType())) {
			throw new ParkingLotException(TYPE_NOT_ALLOWED);
		}
		if(!watchman.vehicleDisponibility(vehicle.getType())) {
			throw new ParkingLotException(NO_SPACE);
		}
		if(!watchman.plateValidToday(vehicle.getPlate())) {
			throw new ParkingLotException(NOT_BUSINESS_DAY);
		}
		watchman.addVehicle(vehicle);
		return true;
		*/
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

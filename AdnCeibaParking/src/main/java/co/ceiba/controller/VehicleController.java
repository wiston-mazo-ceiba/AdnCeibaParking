package co.ceiba.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.ceiba.domain.VehicleType;
import co.ceiba.entity.VehicleEntity;
import co.ceiba.repository.VehicleRepository;

@RestController
@RequestMapping(path="/api/vehicle")
public class VehicleController {
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@GetMapping(path="/add")
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
	}
	
/*
 * @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
 */
	@GetMapping(path="/all")
	public @ResponseBody Iterable<VehicleEntity> getAllVehicles() {
		return vehicleRepository.findAll();
	}
}

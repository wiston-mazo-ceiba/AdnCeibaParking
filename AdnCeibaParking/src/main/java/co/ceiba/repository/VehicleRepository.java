package co.ceiba.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.ceiba.entity.VehicleEntity;
@Repository("VehicleRepository")
public interface VehicleRepository extends CrudRepository<VehicleEntity, Long> {
	//public VehicleEntity findByLicencePlate(String licencePlate);
	//public void saveVehicle(VehicleEntity v);
}

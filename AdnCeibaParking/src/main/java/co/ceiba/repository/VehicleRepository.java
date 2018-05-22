package co.ceiba.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.ceiba.entity.VehicleEntity;
@Repository("VehicleRepository")
public interface VehicleRepository extends CrudRepository<VehicleEntity, Long> {
	public List<VehicleEntity> findByLicencePlateContains(String licencePlate);
	public VehicleEntity findByLicencePlate(String licencePlate);
}

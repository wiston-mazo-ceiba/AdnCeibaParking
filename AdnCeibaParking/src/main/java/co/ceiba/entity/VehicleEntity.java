package co.ceiba.entity;
import javax.persistence.*;

import co.ceiba.domain.VehicleType;

@Entity 
@Table(name = "Vehicle")
public class VehicleEntity {
	 	@Id
	 	@Column(name="vehicle_id")
	    @GeneratedValue(strategy=GenerationType.AUTO)
	    private Integer id;
	 	@Column(name="licencePlate")
		private String licencePlate;
	 	@Column(name="cylinderCapacity")
		private int cylinderCapacity;
	 	@Column(name="vehicle_type")
		private VehicleType type;
	 	
		public Integer getId() {
			return id;
		}
		public String getLicencePlate() {
			return licencePlate;
		}
		public int getCylinderCapacity() {
			return cylinderCapacity;
		}
		public VehicleType getType() {
			return type;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public void setLicencePlate(String licencePlate) {
			this.licencePlate = licencePlate;
		}
		public void setCylinderCapacity(int cylinderCapacity) {
			this.cylinderCapacity = cylinderCapacity;
		}
		public void setType(VehicleType type) {
			this.type = type;
		}
		
}

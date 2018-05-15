package co.ceiba.testdatabuilder;

import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;

public class VehicleTestDataBuilder {
	private String licencePlate;
	private int cylinderCapacity;
	private VehicleType type;
	public VehicleTestDataBuilder() {
		licencePlate = "JWW40E";
		cylinderCapacity = 250;
		type = VehicleType.MOTORCYCLE;
	}
	public VehicleTestDataBuilder withLicencePlate( String LicencePlate) {
		this.licencePlate = LicencePlate;
		return this;
	}
	public VehicleTestDataBuilder withCylinderCapacity( int CylinderCapacity) {
		this.cylinderCapacity = CylinderCapacity;
		return this;
	}
	public VehicleTestDataBuilder withType( VehicleType Type) {
		this.type = Type;
		return this;
	}
	
	public Vehicle build() {
			Vehicle vehicle = new Vehicle(licencePlate,cylinderCapacity,type);
			return vehicle ;
	}
}

package co.ceiba.domain;

public class Vehicle {
	private String licencePlate;
	private int cylinderCapacity;
	private VehicleType type;

	public Vehicle() {
		super();
	}

	public Vehicle(String licencePlate, int cylinderCapacity, VehicleType type) {
		super();
		this.licencePlate = licencePlate.toUpperCase();
		this.cylinderCapacity = cylinderCapacity;
		this.type = type;
	}

	public String getLicencePlate() {
		return licencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}

	public int getCylinderCapacity() {
		return cylinderCapacity;
	}

	public void setCylinderCapacity(int cylinderCapacity) {
		this.cylinderCapacity = cylinderCapacity;
	}

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}
}

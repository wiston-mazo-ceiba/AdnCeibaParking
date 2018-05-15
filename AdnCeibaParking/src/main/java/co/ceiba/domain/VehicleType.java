package co.ceiba.domain;

public enum VehicleType {
	MOTORCYCLE("Motorcycle"),
	CAR("Car"),
	PLANE("Plane"),
	TRAIN("Train"),
	BOAT("Boat"),
	BICYCLE("Bicycle");
	private String type;
	
	VehicleType(String type){
		this.type = type;
		
	}
	public String getVehicleType() {
		return this.type;		
	}
}

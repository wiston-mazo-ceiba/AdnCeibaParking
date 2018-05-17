package co.ceiba.util;

public enum SystemMessages {
	SAVED("Elemento guardado correctamente"),
	SAVED_VEHICLE("Vehículo guardado correctamente"),
	SAVED_TICKET("Ticket guardado correctamente"),
	SAVED_TICKETS("Todos los tickets guardados correctamente"),
	NOT_SAVED("No ha sido posible guardar el elemento"),
	NOT_SAVED_VECHICLE("No se pudo guardar el vehículo"),
	NOT_SAVED_TICKET("No se pudo guardar el ticket"),
	PARKING_EXCEPTION_EMPTY_PARKING_TICKET("El parámetro proporcionado parkingTicket no puede estar vacío"),
	PARKING_EXCEPTION_EMPTY_VEHICLE_IN_PARKING_TICKET("El parámetro proporcionado parkingTicket no contiene un vehículo"),
	PARKING_EXCEPTION_NOT_ALLOWED_VEHICLE_TYPE("El  tipo de vehículo ingresado no esá permitido"),
	PARKING_EXCEPTION_EMPTY_VEHICLE_TYPE("El  tipo de vehículo ingresado está vacío"),
	PARKING_EXCEPTION_UNAUTHORIZED_VEHICLE("El ingreso de este vehículo no está autorizado"),
	PARKING_EXCEPTION_FULL_MOTORCYCLE_PARKING_LOTS("No hay cupos para motos disponibles"),
	PARKING_EXCEPTION_FULL_CAR_PARKING_LOTS("No hay cupos para motos disponibles"),
	PARKING_EXCEPTION_ALREADY_REGISTERED_VEHICLE("El vehículo ya se encuentra registrado en el sistema");
	
	//SystemMessages.TYPE.getText()
	private String message;
	
	SystemMessages(String message){
		this.message = message;
		
	}
	public String getText() {
		return this.message;		
	}
}

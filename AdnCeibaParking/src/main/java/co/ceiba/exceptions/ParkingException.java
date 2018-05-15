package co.ceiba.exceptions;

public class ParkingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParkingException(String message) {
		super(message);
	}

}

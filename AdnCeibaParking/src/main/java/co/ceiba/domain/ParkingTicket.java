package co.ceiba.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingTicket {
	private String ticketNumber;
	private Vehicle parkedVehicle;
	private LocalDateTime checkInDate;
	
	private LocalDateTime checkOutDate;
	private BigDecimal serviceCost;

	public String getTicketNumber() {
		return ticketNumber;
	}

	public Vehicle getParkedVehicle() {
		return parkedVehicle;
	}

	public LocalDateTime getCheckInDate() {
		return checkInDate;
	}

	public LocalDateTime getCheckOutDate() {
		return checkOutDate;
	}

	public BigDecimal getServiceCost() {
		return serviceCost;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public void setParkedVehicle(Vehicle parkedVehicle) {
		this.parkedVehicle = parkedVehicle;
	}

	public void setCheckInDate(LocalDateTime checkInDate) {
		this.checkInDate = checkInDate;
	}

	public void setCheckOutDate(LocalDateTime checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public void setServiceCost(BigDecimal serviceCost) {
		this.serviceCost = serviceCost;
	}

}

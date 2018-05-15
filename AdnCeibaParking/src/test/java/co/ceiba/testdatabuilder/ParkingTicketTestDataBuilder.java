package co.ceiba.testdatabuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import co.ceiba.domain.ParkingTicket;
import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;

public class ParkingTicketTestDataBuilder {
	private String ticketNumber;
	private Vehicle parkedVehicle;
	private LocalDateTime checkInDate;
	private LocalDateTime checkOutDate;
	private BigDecimal serviceCost;
	public ParkingTicketTestDataBuilder (){
		ticketNumber = "C1-123456789";
		parkedVehicle = new Vehicle("AWS666", 100, VehicleType.CAR);
		checkInDate = ( LocalDateTime.of(2018, 05, 10,7,0));	
		checkOutDate = ( LocalDateTime.of(2018, 05, 10,8,0));	
		serviceCost = BigDecimal.valueOf(1000.0);
	}
	public ParkingTicketTestDataBuilder withServiceCost( BigDecimal serviceCost)
	{
		this.serviceCost = serviceCost;
		return this;
	}
	public ParkingTicketTestDataBuilder withTicketNumber(String ticketNumber)
	{
		this.ticketNumber = ticketNumber;
		return this;
	}
	public ParkingTicketTestDataBuilder withParkedVehicle( Vehicle parkedVehicle)
	{
		this.parkedVehicle = parkedVehicle;
		return this;
	}
	public ParkingTicketTestDataBuilder withCheckInDate( LocalDateTime checkInDate)
	{
		this.checkInDate = checkInDate;
		return this;
	}

	public ParkingTicketTestDataBuilder withCheckOutDate(LocalDateTime checkOutDate)
	{
		this.checkOutDate = checkOutDate;
		return this;
	}
	
	
	public ParkingTicket build() {
	ParkingTicket parkingTicket = new ParkingTicket();
	parkingTicket.setTicketNumber(ticketNumber);
	parkingTicket.setCheckInDate(checkInDate);
	parkingTicket.setParkedVehicle(parkedVehicle);
	parkingTicket.setCheckOutDate(checkOutDate);
	parkingTicket.setServiceCost(serviceCost);
	return parkingTicket;
	}

}

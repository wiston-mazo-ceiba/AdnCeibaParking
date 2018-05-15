package co.ceiba.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import co.ceiba.entity.VehicleEntity;

@Entity
@Table(name = "ParkingTicket")
public class ParkingTicketEntity {
	@Id
	@Column(name = "ticket_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "ticketNumber")
	private String ticketNumber;

	@ManyToOne(targetEntity = VehicleEntity.class)
	private VehicleEntity parkedVehicle;

	@Column(name = "checkInDate")
	private LocalDateTime checkInDate;

	@Column(name = "checkOutDate")
	private LocalDateTime checkOutDate;

	@Column(name = "serviceCost")
	private BigDecimal serviceCost;

	public Integer getId() {
		return id;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public VehicleEntity getParkedVehicle() {
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public void setParkedVehicle(VehicleEntity parkedVehicle) {
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

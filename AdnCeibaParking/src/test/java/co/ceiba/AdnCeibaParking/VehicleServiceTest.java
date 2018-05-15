package co.ceiba.AdnCeibaParking;


import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceiba.domain.ParkingLot;
import co.ceiba.domain.ParkingTicket;
import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;
import co.ceiba.exceptions.ParkingException;
import co.ceiba.service.ParkingService;
import co.ceiba.testdatabuilder.ParkingTicketTestDataBuilder;
import co.ceiba.testdatabuilder.VehicleTestDataBuilder;


@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleServiceTest {
	private ParkingService parkingLot ;
	@Before
	public void Preparacion() {
		this.parkingLot = Mockito.mock(ParkingService.class);;
		Mockito.when(parkingLot.getCount()).thenReturn(1);
	}
	@Test
	public void IngresarCarro() {
		//El parqueadero recibe carros y motos - Prueba 1
		//arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		LocalDateTime dateInsert = parkingTicket.getCheckInDate();
		try {
			ParkingTicket correctInsert	 = parkingLot.checkIn(parkingTicket); 			
			assertNotEquals(dateInsert,correctInsert.getCheckInDate());
			
		} catch (ParkingException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void IngresarCarro2() {
		//El parqueadero recibe carros y motos - Prueba 1
		
		//arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		
		try {
			//act
			ParkingTicket correctInsert	 = parkingLot.checkIn(parkingTicket); 			

			//assert
			assertTrue(VehicleType.CAR == correctInsert.getParkedVehicle().getType());
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void IngresarMoto() {
		//El parqueadero recibe carros y motos - Prueba 1
		
		//arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		LocalDateTime dateInsert = parkingTicket.getCheckInDate();
		
		try {
			//act
			ParkingTicket correctInsert	 = parkingLot.checkIn(parkingTicket); 			
			
			//assert
			assertNotEquals(dateInsert,correctInsert.getCheckInDate());
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void IngresarMoto2() {
		//El parqueadero recibe carros y motos - Prueba 1
		
		//arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		
		try {
			//act
			ParkingTicket correctInsert	 = parkingLot.checkIn(parkingTicket); 			
			//assert
			assertTrue(VehicleType.MOTORCYCLE == correctInsert.getParkedVehicle().getType());
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void IngresarVehiculoTipoNull() {
		//El parqueadero recibe carros y motos - Prueba 1
		
		//arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(null).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		LocalDateTime dateInsert = parkingTicket.getCheckInDate();
		
		try {
			//act
			ParkingTicket correctInsert	 = parkingLot.checkIn(parkingTicket); 	
			fail("La prueba no se completó correctamente al enviar un parámetro tipo NULL");
		} catch (ParkingException e) {		
			//assert
			assertTrue(e.getMessage().equals("El parámetro proporcionado parkingTicket no puede estar vacío"));
		}
	}
	@Test
	public void IngresarUnTipoDiferenteDeVehiculo() {
		//El parqueadero recibe carros y motos - Prueba 1
		//arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.BICYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();		

		try {
			//act
			ParkingTicket correctInsert	 = parkingLot.checkIn(parkingTicket); 	
			fail("La prueba no se completó correctamente al enviar un parámetro tipo diferente a carro o moto");
		} catch (ParkingException e) {		
			//assert
			assertTrue(e.getMessage().equals("El  tipo de vehículo ingresado no esá permitido"));
		}
	}
}

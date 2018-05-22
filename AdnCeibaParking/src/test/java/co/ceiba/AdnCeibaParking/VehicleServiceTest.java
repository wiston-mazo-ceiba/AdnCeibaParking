package co.ceiba.AdnCeibaParking;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceiba.adapter.IParkingAdapter;
import co.ceiba.adapter.ParkingAdapter;
import co.ceiba.domain.ParkingTicket;
import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;
import co.ceiba.exceptions.ParkingException;
import co.ceiba.service.IParkingService;
import co.ceiba.service.ParkingService;
import co.ceiba.testdatabuilder.ParkingTicketTestDataBuilder;
import co.ceiba.testdatabuilder.TestingListBuilder;
import co.ceiba.testdatabuilder.VehicleTestDataBuilder;
import co.ceiba.util.SystemMessages;

@RunWith(SpringRunner.class)
// @SpringBootTest
public class VehicleServiceTest {
	
	private IParkingService parkingService;
	private IParkingAdapter simuladorAdapter;
	private TestingListBuilder testingListBuilder;

	@Before
	public void Preparacion() {
		this.simuladorAdapter = Mockito.mock(ParkingAdapter.class);
		this.parkingService = new ParkingService(simuladorAdapter);
		// this.parkingLot = Mockito.mock(ParkingService.class);;
		// Mockito.when(parkingLot.getCount()).thenReturn(1);
	}

	public VehicleServiceTest() {
		testingListBuilder = new TestingListBuilder();
	}

	/*====================================================================================================================
	 * ==================================Verificaciones de ingreso Carros =================================================
	 *==================================================================================================================== 
	 * */
	@Test
	public void IngresarCarro() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		LocalDateTime dateInsert = parkingTicket.getCheckInDate();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());;
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		ParkingTicket correctInsert = parkingService.checkIn(parkingTicket);
		// assert
		assertNotEquals(dateInsert, correctInsert.getCheckInDate());
	}

	@Test
	public void IngresarCarro2() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		ParkingTicket correctInsert = parkingService.checkIn(parkingTicket);
		// assert
		assertTrue(VehicleType.CAR == correctInsert.getParkedVehicle().getType());
	}

	/*====================================================================================================================
	 * ==================================Verificaciones de ingreso Motos =================================================
	 *==================================================================================================================== 
	 * */
	@Test
	public void IngresarMoto() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		LocalDateTime dateInsert = parkingTicket.getCheckInDate();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		ParkingTicket correctInsert = parkingService.checkIn(parkingTicket);
		// assert
		assertNotEquals(dateInsert, correctInsert.getCheckInDate());
	}

	@Test
	public void IngresarMoto2() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		ParkingTicket correctInsert = parkingService.checkIn(parkingTicket);
		// assert
		assertTrue(VehicleType.MOTORCYCLE == correctInsert.getParkedVehicle().getType());
	}

	/*====================================================================================================================
	 * ==================================Verificaciones de ingreso Casos Especiales ======================================
	 *==================================================================================================================== 
	 * */
	@Test
	public void IngresarVehiculoTipoNull() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(null).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());

		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		try {
			// act
			parkingService.checkIn(parkingTicket);
			fail("La prueba no se completó correctamente al enviar un parámetro tipo NULL");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_EMPTY_VEHICLE_TYPE.getText()));
		}
	}

	@Test
	public void IngresarUnTipoDiferenteDeVehiculo() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.BICYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		try {
			// act
			parkingService.checkIn(parkingTicket);
			fail("La prueba no se completó correctamente al enviar un parámetro tipo diferente a carro o moto");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_NOT_ALLOWED_VEHICLE_TYPE.getText()));
		}
	}

	@Test
	public void CrearTicketSinVehiculo() {
		// arrange
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(null).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		try {
			// act
			parkingService.checkIn(parkingTicket);
			fail("La prueba no se completó correctamente al enviar un vehículo vacío");
		} catch (ParkingException e) {
			// assert
			assertTrue(
					e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_EMPTY_VEHICLE_IN_PARKING_TICKET.getText()));
		}
	}

	@Test
	public void HacerUnCheckInSinTicket() {
		// arrange
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		try {
			// act
			parkingService.checkIn(null);
			fail("La prueba no se completó correctamente al enviar un vehículo vacío");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_EMPTY_PARKING_TICKET.getText()));
		}
	}

	/*====================================================================================================================
	 * ==================================Cupo en parqueaderos Carros =====================================================
	 *==================================================================================================================== 
	 * */
	@Test
	public void LimiteSuperadoCarros() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(25));
		try {
			// act
			parkingService.checkIn(parkingTicket);
			fail("La prueba no se completó correctamente al sobrepasar el límite de vehículos");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_FULL_CAR_PARKING_LOTS.getText()));
		}
	}

	@Test
	public void LimiteSuperadoCarros2() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(20));
		try {
			// act
			parkingService.checkIn(parkingTicket);
			fail("La prueba no se completó correctamente al sobrepasar el límite de vehículos");
		} catch (ParkingException e) {
			// assert

			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_FULL_CAR_PARKING_LOTS.getText()));
		}
	}

	@Test
	public void LimiteNoSuperadoCarros() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		LocalDateTime dateInsert = parkingTicket.getCheckInDate();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(19));
		// act
		ParkingTicket correctInsert = parkingService.checkIn(parkingTicket);
		// assert
		assertNotEquals(dateInsert, correctInsert.getCheckInDate());
	}

	/*====================================================================================================================
	 * ==================================Cupo en parqueaderos Motos ======================================================
	 *==================================================================================================================== 
	 * */
	@Test
	public void LimiteSuperadoMotos() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(13));
		try {
			// act
			parkingService.checkIn(parkingTicket);
			fail("La prueba no se completó correctamente al sobrepasar el límite de vehículos");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_FULL_MOTORCYCLE_PARKING_LOTS.getText()));
		}
	}

	@Test
	public void LimiteSuperadoMotos2() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(10));
		try {
			// act
			parkingService.checkIn(parkingTicket);
			fail("La prueba no se completó correctamente al sobrepasar el límite de vehículos");
		} catch (ParkingException e) {
			// assert

			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_FULL_MOTORCYCLE_PARKING_LOTS.getText()));
		}
	}

	@Test
	public void LimiteNoSuperadoMotos2() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		LocalDateTime dateInsert = parkingTicket.getCheckInDate();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(9));
		// act
		ParkingTicket correctInsert = parkingService.checkIn(parkingTicket);
		// assert
		assertNotEquals(dateInsert, correctInsert.getCheckInDate());
	}

	/*====================================================================================================================
	 * ==================================Ingreso Vehiculos Placas con A ==================================================
	 *==================================================================================================================== 
	 * */
	@Test
	public void PlacaLetraADomingo() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withLicencePlate("ASD987").build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		LocalDateTime dateInsert = parkingTicket.getCheckInDate();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(9));
		// act
		ParkingTicket correctInsert = parkingService.checkIn(parkingTicket, LocalDateTime.of(2018, 05, 13, 10, 20));
		// assert
		assertNotEquals(dateInsert, correctInsert.getCheckInDate());
	}

	@Test
	public void PlacaLetraALunes() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withLicencePlate("ASD987").build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		LocalDateTime dateInsert = parkingTicket.getCheckInDate();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(9));
		// act
		ParkingTicket correctInsert = parkingService.checkIn(parkingTicket, LocalDateTime.of(2018, 04, 16, 8, 15));
		// assert
		assertNotEquals(dateInsert, correctInsert.getCheckInDate());
	}

	@Test
	public void PlacaLetraAMartes() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withLicencePlate("ASD987").build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(9));
		try {
			// act
			parkingService.checkIn(parkingTicket, LocalDateTime.of(2018, 04, 24, 21, 45));
			fail("La prueba no se completó correctamente. No se realizó la validación de la letra A en una placa el día Martes");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_UNAUTHORIZED_VEHICLE.getText()));
		}
	}

	@Test
	public void PlacaLetraAMiercoles() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withLicencePlate("ASD987").build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(9));
		try {
			// act
			parkingService.checkIn(parkingTicket, LocalDateTime.of(2018, 5, 2, 14, 58));
			fail("La prueba no se completó correctamente. No se realizó la validación de la letra A en una placa el día Miércoles");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_UNAUTHORIZED_VEHICLE.getText()));
		}
	}

	@Test
	public void PlacaLetraAJueves() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withLicencePlate("ASD987").build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(9));
		try {
			// act
			parkingService.checkIn(parkingTicket, LocalDateTime.of(2018, 5, 17, 1, 34));
			fail("La prueba no se completó correctamente. No se realizó la validación de la letra A en una placa el día Jueves");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_UNAUTHORIZED_VEHICLE.getText()));
		}
	}

	@Test
	public void PlacaLetraAViernes() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withLicencePlate("ASD987").build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(9));
		try {
			// act
			parkingService.checkIn(parkingTicket, LocalDateTime.of(2018, 5, 4, 9, 45));
			fail("La prueba no se completó correctamente. No se realizó la validación de la letra A en una placa el día Viernes");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_UNAUTHORIZED_VEHICLE.getText()));
		}
	}

	@Test
	public void PlacaLetraASabado() {
		// arrange
		Vehicle vehiculo = new VehicleTestDataBuilder().withLicencePlate("ASD987").build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		Mockito.when(simuladorAdapter.findByLicencePlateAndCheckOutDateIsNull(Mockito.anyString())).thenReturn(null);
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsListWithElements(9));
		try {
			// act
			parkingService.checkIn(parkingTicket, LocalDateTime.of(2018, 4, 28, 18, 5));
			fail("La prueba no se completó correctamente. No se realizó la validación de la letra A en una placa el día Sábado");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_UNAUTHORIZED_VEHICLE.getText()));
		}
	}

	/*====================================================================================================================
	 * ==================================Valores Tarifas Carros===========================================================
	 *==================================================================================================================== 
	 * */
	@Test
	public void valorHoraCarro() {
		// arrange
		BigDecimal valorHoraEsperado = BigDecimal.valueOf(1000);
		BigDecimal valorHora;
		// act
		valorHora = parkingService.getValorHora(VehicleType.CAR);
		// assert
		assertTrue(valorHora.compareTo(valorHoraEsperado) == 0);
	}

	@Test
	public void valorDiaCarro() {
		// arrange
		BigDecimal valorDiaEsperado = BigDecimal.valueOf(8000);
		BigDecimal valorDia;
		// act
		valorDia = parkingService.getValorDia(VehicleType.CAR);
		// assert
		assertTrue(valorDia.compareTo(valorDiaEsperado) == 0);
	}

	/*====================================================================================================================
	 * ==================================Valores Tarifas Motos============================================================
	 *==================================================================================================================== 
	 * */
	
	@Test
	public void valorHoraMoto() {
		// arrange
		BigDecimal valorHoraEsperado = BigDecimal.valueOf(500);
		BigDecimal valorHora;
		// act
		valorHora = parkingService.getValorHora(VehicleType.MOTORCYCLE);
		// assert
		assertTrue(valorHora.compareTo(valorHoraEsperado) == 0);
	}


	@Test
	public void valorDiaMoto() {
		// arrange
		BigDecimal valorDiaEsperado = BigDecimal.valueOf(4000);
		BigDecimal valorDia;
		// act
		valorDia = parkingService.getValorDia(VehicleType.MOTORCYCLE);
		// assert
		assertTrue(valorDia.compareTo(valorDiaEsperado) == 0);
	}
	
	/*====================================================================================================================
	 * ==================================Metodos Cobro Tarifas============================================================
	 *==================================================================================================================== 
	 * */
	@Test
	public void cobrarEstadiaNormalUnaHoraMotoTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(500);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 9, 0))
				.withCheckOutDate(LocalDateTime.of(2018, 5, 4, 10, 00))
				.build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarEstadiaNormal(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarEstadiaNormalNueveHorasMotoTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(4000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0))
				.withCheckOutDate(LocalDateTime.of(2018, 5, 4, 19, 00)).build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarEstadiaNormal(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarEstadiaNormalNueveHorasMoto550CCTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(4000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).withCylinderCapacity(550).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0))
				.withCheckOutDate(LocalDateTime.of(2018, 5, 4, 19, 00)).build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarEstadiaNormal(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarEstadiaNormalCasiNueveHorasMotoTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(4500);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0))
				.withCheckOutDate(LocalDateTime.of(2018, 5, 4, 18, 30)).build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarEstadiaNormal(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarEstadiaNormalCasiNueveHorasMoto550CCTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(4500);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).withCylinderCapacity(550).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0))
				.withCheckOutDate(LocalDateTime.of(2018, 5, 4, 18, 30)).build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarEstadiaNormal(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
						/*=======================Carro=============================*/
	@Test
	public void cobrarEstadiaNormalUnaHoraCarroTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(1000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 9, 0))
				.withCheckOutDate(LocalDateTime.of(2018, 5, 4, 10, 00))
				.build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarEstadiaNormal(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarEstadiaNormalNueveHorasCarroTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(8000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0))
				.withCheckOutDate(LocalDateTime.of(2018, 5, 4, 19, 00)).build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarEstadiaNormal(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarEstadiaNormalCasiNueveHorasCarroTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(9000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0))
				.withCheckOutDate(LocalDateTime.of(2018, 5, 4, 18, 30)).build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarEstadiaNormal(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	/*====================================================================================================================
	 * ==================================Cobro impuestos por cilindraje ==================================================
	 *==================================================================================================================== 
	 * */

	@Test
	public void cobrarImpuestoCilindrajeMotoTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(0);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).withCylinderCapacity(180).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarImpuestoCilindraje(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarImpuestoCilindrajeMoto550CCTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(2000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.MOTORCYCLE).withCylinderCapacity(550).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarImpuestoCilindraje(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	
	@Test
	public void cobrarImpuestoCilindrajeCarroTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(0);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).withCylinderCapacity(0).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarImpuestoCilindraje(parkingTicket);
		// assert
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarImpuestoCilindrajeCarroConCilindrajeTest() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(0);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).withCylinderCapacity(1796).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo).build();
		// act
		BigDecimal valorDevuelto = parkingService.cobrarImpuestoCilindraje(parkingTicket);
		// assert
		System.out.println("cobrarImpuestoCilindrajeCarroConCilindrajeTest");
		System.out.println("valorDevuelto");
		System.out.println(valorDevuelto);
		System.out.println("valorEsperado");
		System.out.println(valorEsperado);
		assertTrue(valorDevuelto.compareTo(valorEsperado) == 0);
	}
	
		

	/*====================================================================================================================
	 * ==================================Cobro Tarifas Motos=============================================================
	 *==================================================================================================================== 
	 * */

	@Test
	public void cobrarUnaHoraMoto200CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(500);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(200).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 9, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
			.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 10, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarNueveHorasMoto200CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(4000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(200).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 19, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	
	@Test
	public void cobrarCasiNueveHorasMoto200CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(4500);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(200).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 18, 59));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarDoceHorasMoto200CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(4000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(200).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 20, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarUnDiaMoto200CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(4000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(200).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 5, 10, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarUnDiaMasSeisHorasMoto200CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(7000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(200).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 5, 16, 00));
		System.out.println("cobrarUnDiaMasSeisHorasMoto200CC");
		System.out.println(parkingTicket.getServiceCost());
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarUnaHoraMoto550CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(2500);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(550).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 9, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 10, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	
	
	
	@Test
	public void cobrarNueveHorasMoto550CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(6000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(550).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 19, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	
	@Test
	public void cobrarCasiNueveHorasMoto550CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(6500);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(550).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 18, 59));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarDoceHorasMoto550CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(6000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(550).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 20, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarUnDiaMoto550CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(6000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(550).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 5, 10, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarUnDiaMasSeisHorasMoto550CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(9000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(550).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 5, 16, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarUnDiezHorasMoto650CC() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(6000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withCylinderCapacity(650).withType(VehicleType.MOTORCYCLE).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 20, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	
	
	/*====================================================================================================================
	 * ==================================Cobro Tarifas Carros=============================================================
	 *==================================================================================================================== 
	 * */
	@Test
	public void cobrarUnaHoraCarro() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(1000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 9, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 10, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	
	
	
	@Test
	public void cobrarNueveHorasCarro() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(8000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 19, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	
	@Test
	public void cobrarCasiNueveHorasCarro() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(9000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 18, 59));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarDoceHorasCarro() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(8000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 4, 20, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarUnDiaCarro() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(8000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 5, 10, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarUnDiaMasSeisHorasCarro() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(14000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 5, 16, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	@Test
	public void cobrarUnDiaMasTresHorasCarro() {
		// arrange
		BigDecimal valorEsperado = BigDecimal.valueOf(11000);
		Vehicle vehiculo = new VehicleTestDataBuilder().withType(VehicleType.CAR).build();
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().withParkedVehicle(vehiculo)
				.withCheckInDate(LocalDateTime.of(2018, 5, 4, 10, 0)).build();
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(null).withTicketNumber("ticket1").build() );
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		// act
		parkingTicket = parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 5, 13, 00));
		// assert
		assertTrue(parkingTicket.getServiceCost().compareTo(valorEsperado) == 0);
	}
	/*====================================================================================================================
	 * ================================== funcionalidad de tickets=============================================================
	 *==================================================================================================================== 
	 * */

	@Test
	public void salirDosVecesConElMismoTicket() {
		// arrange
		ParkingTicket parkingTicket = new ParkingTicketTestDataBuilder().build();
		Mockito.when(simuladorAdapter.saveTicket(Mockito.any())).thenReturn(SystemMessages.SAVED_TICKET.getText());
		
		Mockito.when(simuladorAdapter.findByTicketNumber((Mockito.anyString())))
		.thenReturn(new ParkingTicketTestDataBuilder().withCheckOutDate(LocalDateTime.of(2018, 5, 5, 13, 00)).withTicketNumber("ticket1").build() );
		
		Mockito.when(simuladorAdapter.findActiveByVehicleType(Mockito.any()))
				.thenReturn(testingListBuilder.parkingTicketsEmptyList());
		try {
			// act
			parkingService.checkOut(parkingTicket, LocalDateTime.of(2018, 5, 5, 13, 00));
			fail("La prueba no se completó correctamente. No se realizó la verificación de la fecha de salida");
		} catch (ParkingException e) {
			// assert
			assertTrue(e.getMessage().equals(SystemMessages.PARKING_EXCEPTION_ALREADY_USED_PARKING_TICKET_NUMBER.getText()));
		}
		
	}

}

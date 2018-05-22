package co.ceiba.AdnCeibaParking;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceiba.domain.Vehicle;
import co.ceiba.domain.VehicleType;
import co.ceiba.testdatabuilder.VehicleTestDataBuilder;


@RunWith(SpringRunner.class)
//@SpringBootTest
public class VehicleTest {
	
	@Before
	public void setup() {
		
	}
	@Test
	public void vehicleCylinderTest() {
		//Arrange
		VehicleTestDataBuilder vehicleTestDataBuilder = new VehicleTestDataBuilder();
		Vehicle vehicle = vehicleTestDataBuilder.build(); 
		//Act
		int cylinderCapacity = vehicle.getCylinderCapacity();
		//Assert
		Assert.assertEquals(250, cylinderCapacity);
	}
	@Test
	public void vehicleCylinderTest2() {
		//Arrange
		VehicleTestDataBuilder vehicleTestDataBuilder = new VehicleTestDataBuilder().withCylinderCapacity(200);
		Vehicle vehicle = vehicleTestDataBuilder.build(); 
		//Act
		int cylinderCapacity = vehicle.getCylinderCapacity();
		//Assert
		Assert.assertEquals(200, cylinderCapacity);
	}	
	@Test
	public void vehicleLicencePlateTest() {
		//Arrange
		VehicleTestDataBuilder vehicleTestDataBuilder = new VehicleTestDataBuilder();
		Vehicle vehicle = vehicleTestDataBuilder.build(); 
		//Act
		String LicencePlate = vehicle.getLicencePlate();
		//Assert
		Assert.assertEquals("JWW40E", LicencePlate);
	}
	@Test
	public void vehicleLicencePlateTest3() {
		//Arrange
		VehicleTestDataBuilder vehicleTestDataBuilder = new VehicleTestDataBuilder().withLicencePlate("ABCD123");
		Vehicle vehicle = vehicleTestDataBuilder.build(); 
		//Act
		String LicencePlate = vehicle.getLicencePlate();
		//Assert
		Assert.assertEquals("ABCD123", LicencePlate);
	}	

	
	
	//**Prueba del enumerador
	@Test
	public void getVehicleTypeTest() {
		//Arrange
		VehicleType vehicleType = VehicleType.CAR; 
		//Act
		String enumValue = vehicleType.getVehicleType();
		//Assert
		Assert.assertEquals("Car", enumValue);
	}
	@Test
	public void getVehicleTypeTest2() {
		//Arrange
		VehicleType vehicleType = VehicleType.MOTORCYCLE; 
		//Act
		String enumValue = vehicleType.getVehicleType();
		//Assert
		Assert.assertEquals("Motorcycle", enumValue);
	}
	//Mockito.when(emailService.sendMail(Mockito.anyString())).thenReturn("este es un mensaje simulado"); 
}

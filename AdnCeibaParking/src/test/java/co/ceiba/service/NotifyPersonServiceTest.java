package co.ceiba.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceiba.domain.Person;
import co.ceiba.testdatabuilder.PersonTestDataBuilder;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class NotifyPersonServiceTest {
	private NotifyPersonService notifyPersonService;
	private EmailService emailService;
	
	@Before
	public void setup() {
		this.emailService = Mockito.mock(EmailService.class);
		this.notifyPersonService = new NotifyPersonService(emailService);
	}
	@Test
	public void notifyTest() {
		//Arrange
		Person person = new PersonTestDataBuilder().build();
		
		Mockito.when(emailService.sendMail(Mockito.anyString())).thenReturn("este es un mensaje simulado"); 
		//Act
		String message = notifyPersonService.notify(person);
		//Assert
		Assert.assertNotNull(message);
		//System.out.println(message);
	}
}

package co.ceiba.AdnCeibaParking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("co.ceiba.controller")
//@ComponentScan("co.ceiba.domain")
@EntityScan("co.ceiba.entity")
@EnableJpaRepositories("co.ceiba.repository")
public class AdnCeibaParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdnCeibaParkingApplication.class, args);
	}
}

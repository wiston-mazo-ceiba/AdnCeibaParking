package co.ceiba.AdnCeibaParking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = { "boot.registration" }, exclude = JpaRepositoriesAutoConfiguration.class)
@ComponentScan("co.ceiba.controller")
@EntityScan("co.ceiba.entity")
@EnableJpaRepositories("co.ceiba.repository")
@ComponentScan("co.ceiba.service")
@ComponentScan("co.ceiba.adapter")
public class AdnCeibaParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdnCeibaParkingApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

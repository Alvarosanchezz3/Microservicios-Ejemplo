package com.alvaro.servicioitems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@EntityScan({"com.alvaro.serviciocommons.entity"}) // Pilla el entity del servicio commons
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // Permite no tener config de BBDD
@SpringBootApplication
public class ServicioItemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioItemsApplication.class, args);
	}

}

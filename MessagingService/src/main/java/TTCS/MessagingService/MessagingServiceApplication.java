package TTCS.MessagingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
@EnableDiscoveryClient
public class MessagingServiceApplication{

	public static void main(String[] args) {
		SpringApplication.run(MessagingServiceApplication.class, args);
	}


}

package TTCS.ProfileService;

import TTCS.ProfileService.config.AxonConfigProfile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({ AxonConfigProfile.class })

@SpringBootApplication
public class ProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
	}

}

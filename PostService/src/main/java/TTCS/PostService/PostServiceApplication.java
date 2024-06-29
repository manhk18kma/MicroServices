package TTCS.PostService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@SpringBootApplication
public class PostServiceApplication {
	@Bean
	JsonMessageConverter jsonMessageConverter(){
		return new JsonMessageConverter();
	}
	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

}

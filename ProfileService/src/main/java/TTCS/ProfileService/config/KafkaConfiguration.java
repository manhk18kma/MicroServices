package TTCS.ProfileService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic followTopic() {
        return new NewTopic("create_follow_topic", 2, (short) 1);
    }


    @Bean
    public NewTopic friendTopic() {
        return new NewTopic("create_friend_topic", 2, (short) 1);
    }
}

package TTCS.PostService.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic commentTopic() {
        return new NewTopic("create_comment_topic", 2, (short) 1);
    }


    @Bean
    public NewTopic likeTopic() {
        return new NewTopic("create_like_topic", 2, (short) 1);
    }

    @Bean
    public NewTopic postTopic() {
        return new NewTopic("create_post_topic", 2, (short) 1);
    }

    @Bean
    public NewTopic removeNotification() {
        return new NewTopic("remove_notification_topic", 2, (short) 1);
    }
}

package TTCS.NotificationService.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.DoubleStream;

@Configuration
public class AxonConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public TokenStore tokenStore(Serializer serializer, MongoClient mongoClient) {
        return MongoTokenStore.builder()
                .mongoTemplate(DefaultMongoTemplate.builder()
                        .mongoDatabase(mongoClient.getDatabase("NotificationService"))
                        .build())
                .serializer(serializer)
                .build();
    }
}

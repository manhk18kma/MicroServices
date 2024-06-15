package TTCS.ProfileService.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenStoreConfig {
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public TokenStore tokenStore(Serializer serializer, MongoClient mongoClient) {
        return MongoTokenStore.builder()
                .mongoTemplate(DefaultMongoTemplate.builder()
                        .mongoDatabase(mongoClient.getDatabase("ProfileService"))
                        .build())
                .serializer(serializer)
                .build();
    }
}


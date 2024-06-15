package TTCS.IdentityService.config;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.commandhandling.gateway.IntervalRetryScheduler;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class RetryConfig {

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(1);
    }

    @Bean
    public IntervalRetryScheduler retryScheduler(ScheduledExecutorService scheduledExecutorService) {
        return IntervalRetryScheduler.builder()
                .retryExecutor(scheduledExecutorService)
                .maxRetryCount(20)
                .retryInterval(2000) // 2000 ms = 2 seconds
                .build();
    }


    @Bean
    public CommandGateway commandGateway(IntervalRetryScheduler retryScheduler) {
        Configurer configurer = DefaultConfigurer.defaultConfiguration();
        return DefaultCommandGateway.builder()
                .commandBus(configurer.buildConfiguration().commandBus())
                .retryScheduler(retryScheduler)
                .build();
    }
}

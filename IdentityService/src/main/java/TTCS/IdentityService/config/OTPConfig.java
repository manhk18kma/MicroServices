package TTCS.IdentityService.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OTPConfig {
    @Bean
    public GoogleAuthenticator googleAuthenticator(GoogleAuthenticatorConfig googleAuthenticatorConfig) {
        return new GoogleAuthenticator(googleAuthenticatorConfig);
    }

    @Bean
    public GoogleAuthenticatorConfig googleAuthenticatorConfig() {
        return new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
                .setCodeDigits(6)
                .setTimeStepSizeInMillis(30000*2)
                .build();
    }



}



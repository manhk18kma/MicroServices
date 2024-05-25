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
                .setTimeStepSizeInMillis(3000000) // Đặt kích thước bước thời gian là 30 giây
                .setWindowSize(1)
                .setCodeDigits(6)
                .build();
    }


}



package TTCS.IdentityService.infrastructure.persistence.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OTPService {
    final GoogleAuthenticator googleAuthenticator;

    public String generateSecretKey() {
        return googleAuthenticator.createCredentials().getKey();
    }

    public boolean verifyOTP(String secretKey, int otp) {
        return googleAuthenticator.authorize(secretKey, otp);

    }

    public int generateOTP(String secretKey) {
        return googleAuthenticator.getTotpPassword(secretKey);
    }
}
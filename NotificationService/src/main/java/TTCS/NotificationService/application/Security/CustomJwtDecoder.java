package TTCS.NotificationService.application.Security;
import TTCS.NotificationService.application.Exception.AppException.AppErrorCode;
import TTCS.NotificationService.application.Exception.AppException.AppException;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class CustomJwtDecoder implements JwtDecoder {
//    @Value("${jwt.signerKey}")
//    private String signerKey;
//
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    private NimbusJwtDecoder nimbusJwtDecoder = null;
//
//    @Override
//    public Jwt decode(String token) throws JwtException {
//
//        try {
//            var response = authenticationService.introspect(
//                    IntrospectRequest.builder().token(token).build());
//
//            if (!response.isValid()) {
//                throw new JwtException("Token invalid , Try again ");
//            }
//        } catch (JOSEException | ParseException e) {
//            throw new JwtException(e.getMessage());
//        }
//
//        if (Objects.isNull(nimbusJwtDecoder)) {
//            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
//            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
//                    .macAlgorithm(MacAlgorithm.HS512)
//                    .build();
//        }
//
//        return nimbusJwtDecoder.decode(token);
//    }
@Override
public Jwt decode(String token) throws JwtException {
    try {
        SignedJWT signedJWT = SignedJWT.parse(token);

        return new Jwt(token,
                signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                signedJWT.getHeader().toJSONObject(),
                signedJWT.getJWTClaimsSet().getClaims()
        );

    } catch (ParseException e) {
       throw  new AppException(AppErrorCode.UNAUTHENTICATED);
    }
}
}

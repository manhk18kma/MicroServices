package TTCS.IdentityService.infrastructure.persistence.service;

import TTCS.IdentityService.application.Exception.AppException.AppErrorCode;
import TTCS.IdentityService.application.Exception.AppException.AppException;
import TTCS.IdentityService.domain.enumType.UserStatus;
import TTCS.IdentityService.domain.model.Account;
import TTCS.IdentityService.domain.model.InvalidatedToken;
import TTCS.IdentityService.infrastructure.persistence.repository.AccountRepository;
import TTCS.IdentityService.infrastructure.persistence.repository.InvalidatedTokenRepository;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Request.AuthenticationRequest;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Request.IntrospectRequest;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Request.LogoutRequest;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Request.RefreshRequest;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Response.AuthenticationResponse;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Response.IntrospectResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

     AccountRepository accountRepository;
     PasswordEncoder passwordEncoder;
     InvalidatedTokenRepository invalidatedTokenRepository;



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername());
        if (account==null){
            throw new  AppException(AppErrorCode.ACCOUNT_NOT_EXISTED);
        }

        boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());
        boolean activated = account.getStatus().equals(UserStatus.ACTIVE);

        if (!authenticated) throw new AppException(AppErrorCode.PASSWORD_INVALID);
        if (!activated) throw new AppException(AppErrorCode.ACCOUNT_INACTIVE);

        var token = generateToken(account);
        return AuthenticationResponse.builder()
                .idAccount(account.getIdAccount())
                .token(token)
                .idProfile(account.getIdProfile())
                .idChatProfile(account.getIdChatProfile())
                .authenticated(true)
                .build();
    }
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);
//        log.info(signedJWT.getJWTClaimsSet().getIssueTime().toString());
//        log.info(String.valueOf(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).isAfter(Instant.now())));

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(AppErrorCode.UNAUTHENTICATED);
        }
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(AppErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
    private String generateToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getIdProfile())
                .issuer("KMA-ACTVN")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(account))
                .claim("idAccount" , account.getIdAccount())
                .claim("idChatProfile" , account.getIdChatProfile())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
    private String buildScope(Account account) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(account.getRoles()))
            account.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
//                if (!CollectionUtils.isEmpty(role.getPermissions()))
//                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException exception){
            log.info("Token already expired");
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        Account account = accountRepository.findByUsername(username);
        if (account==null){
            throw new  AppException(AppErrorCode.ACCOUNT_NOT_EXISTED);
        }
        var token = generateToken(account);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    public  String extractSubject(String token) throws ParseException {
        // Parse JWT token
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Get JWT claims
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        // Extract subject (sub)
        return claimsSet.getSubject();
    }


}

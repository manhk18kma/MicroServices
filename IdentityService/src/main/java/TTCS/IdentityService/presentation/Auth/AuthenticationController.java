package TTCS.IdentityService.presentation.Auth;

import TTCS.IdentityService.application.Command.CommandService.AccountCommandService;
import TTCS.IdentityService.application.Command.CommandService.DTO.OTPResponse;
import TTCS.IdentityService.infrastructure.persistence.service.AuthenticationService;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Request.AuthenticationRequest;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Request.IntrospectRequest;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Request.LogoutRequest;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Request.RefreshRequest;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Response.AuthenticationResponse;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Response.IntrospectResponse;
import TTCS.IdentityService.presentation.command.dto.response.ResponseData;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/identity")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    AccountCommandService accountCommandService;

    @PostMapping("/token")
    public ResponseData<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws ExecutionException, InterruptedException {
        AuthenticationResponse result = authenticationService.authenticate(request);
        accountCommandService.connectChatProfile(result.getIdAccount());
        return ResponseData.<AuthenticationResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Login successful.")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @PostMapping("/token/introspect")
    public ResponseData<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ResponseData.<IntrospectResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Token introspection successful.")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @PostMapping("/token/refresh")
    public ResponseData<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ResponseData.<AuthenticationResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Token refreshed successfully.")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @PostMapping("/logout")
    public ResponseData<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        String sub = authenticationService.extractSubject(request.getToken());
        System.out.println(sub);
        accountCommandService.disConnect(sub);
                   return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Logout successful.")
                .timestamp(new Date())
                .build();
    }
}

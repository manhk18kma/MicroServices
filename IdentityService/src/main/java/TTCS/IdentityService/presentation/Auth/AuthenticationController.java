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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@Tag(name = "Authentication controller", description = "APIs for handling authentication operations")
public class AuthenticationController {
    final AuthenticationService authenticationService;
    final AccountCommandService accountCommandService;

    @Operation(
            summary = "Authenticate user",
            description = "Authenticate user using username and password"
    )
    @PostMapping("/login")
    public ResponseData<AuthenticationResponse> authenticate(
            @Parameter(description = "Authentication request payload", required = true)
            @RequestBody @Valid AuthenticationRequest request
    ) throws ExecutionException, InterruptedException {
        AuthenticationResponse result = authenticationService.authenticate(request);
        accountCommandService.connectChatProfile(result.getIdAccount());
        return ResponseData.<AuthenticationResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Login successful.")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Introspect token",
            description = "Introspect the provided token for its validity and information"
    )
    @PostMapping("/token/introspect")
    public ResponseData<IntrospectResponse> introspect(
            @Parameter(description = "Introspect request payload", required = true)
            @RequestBody IntrospectRequest request
    ) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ResponseData.<IntrospectResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Token introspection successful.")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Refresh token",
            description = "Refresh the provided token and get a new access token"
    )
    @PostMapping("/token/refresh")
    public ResponseData<AuthenticationResponse> refreshToken(
            @Parameter(description = "Refresh token request payload", required = true)
            @RequestBody RefreshRequest request
    ) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ResponseData.<AuthenticationResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Token refreshed successfully.")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Logout user",
            description = "Logout the user and invalidate the provided token"
    )
    @PostMapping("/logout")
    public ResponseData<Void> logout(
            @Parameter(description = "Logout request payload", required = true)
            @RequestBody LogoutRequest request
    ) throws ParseException, JOSEException {
        authenticationService.logout(request);
        String sub = authenticationService.extractSubject(request.getToken());
        accountCommandService.disConnect(sub);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Logout successful.")
                .timestamp(new Date())
                .build();
    }
}

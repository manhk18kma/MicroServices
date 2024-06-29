package TTCS.IdentityService.presentation.command;

import TTCS.IdentityService.application.Command.CommandService.AccountCommandService;
import TTCS.IdentityService.application.Command.CommandService.DTO.AccountCommandResponse;
import TTCS.IdentityService.application.Command.CommandService.DTO.OTPResponse;
import TTCS.IdentityService.presentation.command.dto.request.AccountActiveRequest;
import TTCS.IdentityService.presentation.command.dto.request.AccountChangePasswordRequest;
import TTCS.IdentityService.presentation.command.dto.request.AccountCreateRequest;
import TTCS.IdentityService.presentation.command.dto.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@Tag(name = "Command controller", description = "APIs for handling account commands")
public class CommandController {
    final AccountCommandService accountCommandService;

    @Operation(summary = "Add new account", description = "Send a request via this API to create a new account")
    @PostMapping
    public ResponseData<OTPResponse> addAccount(
            @Parameter(description = "Account creation request payload", required = true)
            @RequestBody @Valid AccountCreateRequest accountCreateRequestDTO
    ) throws ExecutionException, InterruptedException {
        CompletableFuture<OTPResponse> responseFuture = accountCommandService.createAccount(accountCreateRequestDTO);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Account created successfully, check OTP in your email", new Date(), responseFuture.get());
    }

    @Operation(summary = "Activate account", description = "Send a request via this API to activate an account using the provided email")
    @PostMapping("/{email}/activation")
    public ResponseData<AccountCommandResponse> activateAccount(
            @Parameter(description = "Email address to activate the account", required = true)
            @PathVariable("email") @NotNull @NotBlank @Email(message = "Email should be valid") String email,
            @RequestBody @Valid AccountActiveRequest accountActiveRequest
    ) throws ExecutionException, InterruptedException {
        CompletableFuture<AccountCommandResponse> response = accountCommandService.activeAccount(accountActiveRequest, email);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Account activated successfully", new Date(), response.get());
    }

    @Operation(summary = "Change password", description = "Send a request via this API to change the account password using the provided ID")
    @PutMapping("/{id}/password")
    public ResponseData<AccountCommandResponse> changePassword(
            @Parameter(description = "Account ID to change password", required = true)
            @PathVariable("id") @NotNull @NotBlank(message = "ID must not be blank") String id,
            @RequestBody @Valid AccountChangePasswordRequest accountChangePasswordRequest
    ) throws ExecutionException, InterruptedException {
        accountChangePasswordRequest.setIdAccount(id);
        CompletableFuture<AccountCommandResponse> response = accountCommandService.changePassword(accountChangePasswordRequest);
        return new ResponseData<>(HttpStatus.OK.value(), "Password changed successfully", new Date(), response.get());
    }

    @Operation(summary = "Generate OTP", description = "Send a request via this API to generate an OTP using the provided account ID")
    @GetMapping("/{id}/otp")
    public ResponseData<OTPResponse> generateOTP(
            @Parameter(description = "Account ID to generate OTP", required = true)
            @PathVariable("id") @NotNull @NotBlank(message = "ID must not be blank") String id
    ) throws ExecutionException, InterruptedException {
        CompletableFuture<OTPResponse> response = accountCommandService.generateOTP(id);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Check your email", new Date(), response.get());
    }
}

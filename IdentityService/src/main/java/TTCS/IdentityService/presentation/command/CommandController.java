package TTCS.IdentityService.presentation.command;

import TTCS.IdentityService.application.Command.CommandEvent.Command.AccountCreateCommand;
import TTCS.IdentityService.application.Command.CommandService.AccountCommandService;
import TTCS.IdentityService.application.Command.CommandService.DTO.AccountCommandResponse;
import TTCS.IdentityService.application.Command.CommandService.DTO.OTPResponse;
import TTCS.IdentityService.application.Command.Saga.AccountSaga;
import TTCS.IdentityService.application.Exception.ErrorResponse;
import TTCS.IdentityService.domain.enumType.UserStatus;
import TTCS.IdentityService.domain.model.Account;
import TTCS.IdentityService.presentation.command.dto.request.*;
import TTCS.IdentityService.presentation.command.dto.response.ResponseData;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@RequestMapping("/api/v1/identity")
public class CommandController {
    final AccountCommandService accountCommandService;
    final CommandGateway commandGateway;

    @PostMapping
    public ResponseData<OTPResponse> addAccount(@RequestBody @Valid AccountCreateRequest accountCreateRequestDTO) throws ExecutionException, InterruptedException {
        CompletableFuture<OTPResponse> responseFuture = accountCommandService.createAccount(accountCreateRequestDTO);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Account created successfully, check OTP in your email", new Date(), responseFuture.get());
    }

    @PostMapping("/{id}/activate")
    public ResponseData<AccountCommandResponse> activateAccount(@PathVariable String id, @RequestBody AccountActiveRequest accountActiveRequest) throws ExecutionException, InterruptedException {
        accountActiveRequest.setIdAccount(id);
        CompletableFuture<AccountCommandResponse> response = accountCommandService.activeAccount(accountActiveRequest);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Account activated successfully", new Date(), response.get());
    }

    @PutMapping("/{id}/password")
    public ResponseData<AccountCommandResponse> changePassword(@PathVariable String id, @RequestBody AccountChangePasswordRequest accountChangePasswordRequest) throws ExecutionException, InterruptedException {
        accountChangePasswordRequest.setIdAccount(id);
        CompletableFuture<AccountCommandResponse> response = accountCommandService.changePassword(accountChangePasswordRequest);
        return new ResponseData<>(HttpStatus.OK.value(), "Password changed successfully", new Date(), response.get());
    }

    @GetMapping("/{id}/otp")
    public ResponseData<OTPResponse> generateOTP(@PathVariable String id) throws ExecutionException, InterruptedException {
        CompletableFuture<OTPResponse> response = accountCommandService.generateOTP(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Check your email", new Date(), response.get());
    }
}

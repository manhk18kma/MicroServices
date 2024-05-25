package TTCS.IdentityService.presentation.command;


import TTCS.IdentityService.application.Command.CommandService.AccountCommandService;
import TTCS.IdentityService.application.Command.CommandService.DTO.AccountCommandResponse;
import TTCS.IdentityService.application.Command.CommandService.DTO.OTPResponse;
import TTCS.IdentityService.presentation.command.dto.request.AccountActiveRequest;
import TTCS.IdentityService.presentation.command.dto.request.AccountChangePasswordRequest;
import TTCS.IdentityService.presentation.command.dto.request.AccountCreateRequest;
import TTCS.IdentityService.presentation.command.dto.response.ResponseData;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@RequestMapping("/identityCommand")
public class CommandController {
  final AccountCommandService accountCommandService;



    @PostMapping("/createAccount")
    public ResponseData<AccountCommandResponse> addAccount(@RequestBody @Valid AccountCreateRequest accountCreateRequestDTO) throws ExecutionException, InterruptedException {
        AccountCommandResponse response = accountCommandService.createAccount(accountCreateRequestDTO);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Account saved successfully" , response);
    }

    @PostMapping("/activeAccount")
    public ResponseData<AccountCommandResponse> activeAccount(@RequestBody AccountActiveRequest accountActiveRequest) throws ExecutionException, InterruptedException {
            CompletableFuture<AccountCommandResponse> response = accountCommandService.activeAccount(accountActiveRequest);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Account active successfully" , response.get());
    }
    @PutMapping("/changePassword")
    public ResponseData<AccountCommandResponse> changePassword(@RequestBody AccountChangePasswordRequest accountChangePasswordRequest) throws ExecutionException, InterruptedException {
        CompletableFuture<AccountCommandResponse> response = accountCommandService.changePassword(accountChangePasswordRequest);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Password changed successfully" , response.get());
    }

    @GetMapping("/generateOTP")
    public ResponseData<OTPResponse> generateOTP(@RequestParam String id) throws ExecutionException, InterruptedException {
        OTPResponse response = accountCommandService.generateOTP(id);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Check your email" , response);
    }


}

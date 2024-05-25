package TTCS.IdentityService.application.Command.CommandService;

import TTCS.IdentityService.application.Command.CommandEvent.Command.AccountActiveCommand;
import TTCS.IdentityService.application.Command.CommandEvent.Command.AccountChangePasswordCommand;
import TTCS.IdentityService.application.Command.CommandEvent.Command.AccountCreateCommand;
import TTCS.IdentityService.application.Command.CommandService.DTO.AccountCommandResponse;
import TTCS.IdentityService.application.Command.CommandService.DTO.OTPResponse;
import TTCS.IdentityService.application.Exception.AppException.AppException;
import TTCS.IdentityService.application.Exception.AppException.AppErrorCode;
import TTCS.IdentityService.application.Exception.AxonException.AxonErrorCode;
import TTCS.IdentityService.application.Exception.AxonException.AxonException;
import TTCS.IdentityService.domain.enumType.UserStatus;
import TTCS.IdentityService.domain.model.Account;
import TTCS.IdentityService.infrastructure.persistence.repository.AccountRepository;
import TTCS.IdentityService.infrastructure.persistence.service.AccountService;
import TTCS.IdentityService.infrastructure.persistence.service.EmailService;
import TTCS.IdentityService.infrastructure.persistence.service.OTPService;
import TTCS.IdentityService.presentation.command.dto.request.AccountActiveRequest;
import TTCS.IdentityService.presentation.command.dto.request.AccountChangePasswordRequest;
import TTCS.IdentityService.presentation.command.dto.request.AccountCreateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCommandService {
    final CommandGateway commandGateway;
    final AccountRepository accountRepository;
    final OTPService otpService;
    final PasswordEncoder passwordEncoder;
    final EmailService emailService;

    public AccountCommandResponse createAccount(AccountCreateRequest accountCreateRequest) {
//        if (accountRepository.existsAccountByEmail(accountCreateRequest.getEmail())) {
//            throw new AppException(AppErrorCode.EMAIL_EXISTED);
//        }
//        if (accountRepository.existsAccountByUsername(accountCreateRequest.getUsername())) {
//            throw new AppException(AppErrorCode.USERNAME_EXISTED);
//        }
        AccountCreateCommand accountCreateCommand = new AccountCreateCommand();
        BeanUtils.copyProperties(accountCreateRequest, accountCreateCommand);
        String secretKey = otpService.generateSecretKey();
        int otp = otpService.generateOTP(secretKey);
        Account account = Account.builder()
                .idAccount(UUID.randomUUID().toString())
                .username(accountCreateRequest.getUsername())
                .password(passwordEncoder.encode(accountCreateRequest.getPassword()))
                .email(accountCreateRequest.getEmail())
                .createAt(new Date())
                .updateAt(new Date())
                .status(UserStatus.INACTIVE)
                .secretKey(secretKey)
                .idProfile(UUID.randomUUID().toString())
                .build();
        Account account1 = accountRepository.save(account);
        emailService.sendOtpEmail(accountCreateRequest.getEmail(), otp);
        accountCreateCommand.setPassword(account1.getPassword());
        accountCreateCommand.setIdAccount(account.getIdAccount());
        accountCreateCommand.setSecretKey(secretKey);
        accountCreateCommand.setIdProfile(account1.getIdProfile());
        accountCreateCommand.setCreateAt(account1.getCreateAt());
        accountCreateCommand.setExecuteAt(new Date());
        accountCreateCommand.setStatus(account1.getStatus());
        commandGateway.send(accountCreateCommand);
        return AccountCommandResponse.builder().idAccount(account1.getIdAccount()).build();
    }
    public CompletableFuture<AccountCommandResponse> activeAccount(AccountActiveRequest accountActiveRequest) {
        AccountActiveCommand accountActiveCommand = AccountActiveCommand.builder()
                .idAccount(accountActiveRequest.getIdAccount())
                .otp(accountActiveRequest.getOtp())
                .status(UserStatus.ACTIVE)
                .executeAt(new Date())
                .build();
        CompletableFuture future = commandGateway.send(accountActiveCommand);
        return future
                .thenApply(result -> {
                    return new AccountCommandResponse().builder()
                            .idAccount(accountActiveCommand.getIdAccount())
                            .build();
                })
//                .exceptionally(exception -> {
//                    throw new AxonException(AxonErrorCode.UNCATEGORIZED_EXCEPTION);
//                })
                ;
    }


    public CompletableFuture<AccountCommandResponse> changePassword(AccountChangePasswordRequest accountChangePasswordRequest) {

        AccountChangePasswordCommand accountChangePasswordCommand = new AccountChangePasswordCommand();
        BeanUtils.copyProperties(accountChangePasswordRequest , accountChangePasswordCommand);
        accountChangePasswordCommand.setExecuteAt(new Date());
        System.out.println(accountChangePasswordCommand.getIdAccount());
        CompletableFuture future = commandGateway.send(accountChangePasswordCommand);
        return future
                .thenApply(result -> {
                    return new AccountCommandResponse().builder()
                            .idAccount(accountChangePasswordCommand.getIdAccount())
                            .build();
                })
//                .exceptionally(exception -> {
//                    throw new AxonException(AxonErrorCode.UNCATEGORIZED_EXCEPTION);
//                })
                ;
    }

    public OTPResponse generateOTP(String id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account == null) {
            throw new AppException(AppErrorCode.ACCOUNT_NOT_EXISTED);
        }
        int otp = otpService.generateOTP(account.get().getSecretKey());
        long expirationTimeMillis = System.currentTimeMillis() + (60 * 1000);
        Date expirationTime = new Date(expirationTimeMillis);
        OTPResponse otpResponse = OTPResponse.builder()
                .email(account.get().getEmail())
                .createAt(new Date())
                .expiredAt(expirationTime)
                .build();
        emailService.sendOtpEmail(account.get().getEmail() , otp);
        return otpResponse;
    }

}

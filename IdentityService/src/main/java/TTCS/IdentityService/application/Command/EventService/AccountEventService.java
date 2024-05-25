package TTCS.IdentityService.application.Command.EventService;

import TTCS.IdentityService.application.Command.CommandEvent.Event.AccountActiveEvent;
import TTCS.IdentityService.application.Command.CommandEvent.Event.AccountChangePasswordEvent;
import TTCS.IdentityService.application.Command.CommandEvent.Event.AccountCreateEvent;
import TTCS.IdentityService.application.Exception.AppException.AppErrorCode;
import TTCS.IdentityService.application.Exception.AppException.AppException;
import TTCS.IdentityService.application.Exception.AxonException.AxonErrorCode;
import TTCS.IdentityService.application.Exception.AxonException.AxonException;
import TTCS.IdentityService.domain.enumType.UserStatus;
import TTCS.IdentityService.domain.model.Account;
import TTCS.IdentityService.infrastructure.persistence.repository.AccountRepository;
import TTCS.IdentityService.infrastructure.persistence.service.EmailService;
import TTCS.IdentityService.infrastructure.persistence.service.OTPService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@RequiredArgsConstructor
public class AccountEventService {
    final AccountRepository accountRepository;
    final OTPService otpService;
    final PasswordEncoder passwordEncoder;
    final EmailService emailService;

    @EventHandler
    public void on(AccountActiveEvent accountActiveEvent){
        Optional<Account> account = accountRepository.findById(accountActiveEvent.getIdAccount());
        account.get().setStatus(UserStatus.ACTIVE);
        account.get().setUpdateAt(accountActiveEvent.getExecuteAt());
        accountRepository.save(account.get());
    }

    @EventHandler
    public void on(AccountChangePasswordEvent accountChangePasswordEvent){
        Optional<Account> account = accountRepository.findById(accountChangePasswordEvent.getIdAccount());
        account.get().setPassword(accountChangePasswordEvent.getNewPassword());
        account.get().setUpdateAt(accountChangePasswordEvent.getExecuteAt());
        accountRepository.save(account.get());
    }


}

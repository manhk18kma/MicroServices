package TTCS.IdentityService.infrastructure.persistence.service;

import TTCS.IdentityService.infrastructure.persistence.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountService {
     final AccountRepository accountRepository;
     final PasswordEncoder passwordEncoder;
     final OTPService otpService;
     final EmailService emailService;


//
//
//    public void activeAccount(AccountActiveRequest accountActiveRequest) {
//        Account account = accountRepository.findByEmail(accountActiveRequest.getEmail());
//
//        if (otpService.verifyOTP(account.getSecretKey(), accountActiveRequest.getOtp()) && account.getStatus() == UserStatus.INACTIVE) {
//            AccountActiveCommand accountActiveCommand = AccountActiveCommand.builder()
//                    .id(account.getId())
//                    .userStatus(UserStatus.ACTIVE)
//                    .build();
//            account.setStatus(UserStatus.ACTIVE);
//            accountRepository.save(account);
//            commandGateway.sendAndWait(accountActiveCommand);
//        }
//    }


}

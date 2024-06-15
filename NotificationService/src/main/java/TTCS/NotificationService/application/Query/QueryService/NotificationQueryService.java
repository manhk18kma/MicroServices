package TTCS.NotificationService.application.Query.QueryService;

import TTCS.NotificationService.Domain.Model.EmailOTP;
import TTCS.NotificationService.application.Exception.AxonException.AxonErrorCode;
import TTCS.NotificationService.application.Exception.AxonException.AxonException;
import TTCS.NotificationService.application.Query.Query.EmailOTPQueryGetAll;
import TTCS.NotificationService.application.Query.Query.EmailOTPQueryGetByID;
import TTCS.NotificationService.infrastructure.persistence.Repository.EmailOTPRepository;
import TTCS.NotificationService.presentation.DTO.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationQueryService {
    final EmailOTPRepository emailOTPRepository;
    final QueryGateway queryGateway;
    public CompletableFuture<PageResponse> getAllEmailOTP(int pageNo, int pageSize) {
        EmailOTPQueryGetAll queryGetAll = new EmailOTPQueryGetAll(pageNo, pageSize);
        long totalElements = emailOTPRepository.countAllBy();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        CompletableFuture future = queryGateway.query(queryGetAll, ResponseTypes.multipleInstancesOf(EmailOTP.class));
        List<EmailOTP> list = (List<EmailOTP>) future.join();
        List<EmailOTP> accountDetailsResponses = list.stream().map(emailOTP ->
                EmailOTP.builder()
                        .idOTP(emailOTP.getIdOTP())
                        .to(emailOTP.getTo())
                        .otp(emailOTP.getOtp())
                        .executeAt(emailOTP.getExecuteAt())
                        .expiredAt(emailOTP.getExpiredAt())
                        .idAccount(emailOTP.getIdAccount())
                        .status(emailOTP.isStatus())
                        .build()).toList();
        return future.thenApply(result -> {
                    return PageResponse.builder()
                            .size(pageSize)
                            .totalElements((int) totalElements)
                            .totalPages(totalPages)
                            .number(pageNo)
                            .items(accountDetailsResponses)
                            .build();
                })
                .exceptionally(ex -> {
                    throw new AxonException(AxonErrorCode.UNCATEGORIZED_EXCEPTION);
                })
                ;
    }

    public CompletableFuture<PageResponse> getAllEmailOTPByIDAccount(String idAccount, int pageNo, int pageSize) {
        EmailOTPQueryGetByID queryGetByID = new EmailOTPQueryGetByID( idAccount,pageNo, pageSize);
        long totalElements = emailOTPRepository.countAllByIdAccount(idAccount);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        CompletableFuture future = queryGateway.query(queryGetByID, ResponseTypes.multipleInstancesOf(EmailOTP.class));
        List<EmailOTP> list = (List<EmailOTP>) future.join();
        List<EmailOTP> accountDetailsResponses = list.stream().map(emailOTP ->
                EmailOTP.builder()
                        .idOTP(emailOTP.getIdOTP())
                        .to(emailOTP.getTo())
                        .otp(emailOTP.getOtp())
                        .executeAt(emailOTP.getExecuteAt())
                        .expiredAt(emailOTP.getExpiredAt())
                        .idAccount(emailOTP.getIdAccount())
                        .status(emailOTP.isStatus())
                        .build()).toList();
        return future.thenApply(result -> {
                    return PageResponse.builder()
                            .size(pageSize)
                            .totalElements((int) totalElements)
                            .totalPages(totalPages)
                            .number(pageNo)
                            .items(accountDetailsResponses)
                            .build();
                })
                .exceptionally(ex -> {
                    throw new AxonException(AxonErrorCode.UNCATEGORIZED_EXCEPTION);
                })
                ;
    }
}

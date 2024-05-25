package TTCS.IdentityService.application.Query.QueryService;

import TTCS.IdentityService.application.Exception.AppException.AppErrorCode;
import TTCS.IdentityService.application.Exception.AppException.AppException;
import TTCS.IdentityService.application.Exception.AxonException.AxonErrorCode;
import TTCS.IdentityService.application.Exception.AxonException.AxonException;
import TTCS.IdentityService.application.Query.Query.AccountQueryGetAll;
import TTCS.IdentityService.application.Query.Query.AccountQueryGetById;
import TTCS.IdentityService.domain.model.Account;
import TTCS.IdentityService.infrastructure.persistence.repository.AccountRepository;
import TTCS.IdentityService.infrastructure.persistence.service.AccountService;
import TTCS.IdentityService.presentation.query.dto.response.AccountDetailsResponse;
import TTCS.IdentityService.presentation.query.dto.response.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountQueryService {
    final AccountRepository accountRepository;
    final AccountService accountService;
    final QueryGateway queryGateway;


    public CompletableFuture<PageResponse> getAllAccountWithSortBy(int pageNo, int pageSize) {
        AccountQueryGetAll accountQueryGetAll = new AccountQueryGetAll(pageNo, pageSize);
        int totalElements = accountRepository.countAllBy();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        CompletableFuture future = queryGateway.query(accountQueryGetAll, ResponseTypes.multipleInstancesOf(Account.class));
        List<Account> list = (List<Account>) future.join();
        List<AccountDetailsResponse> accountDetailsResponses = list.stream().map(account ->
                AccountDetailsResponse.builder()
                        .id(account.getIdAccount())
                        .username(account.getUsername())
                        .email(account.getEmail())
                        .status(account.getStatus())
                        .idProfile(account.getIdProfile())
                        .build()
        ).toList();
        return future.thenApply(result -> {
            return PageResponse.builder()
                    .size(pageSize)
                    .totalElements(totalElements)
                    .totalPages(totalPages)
                    .number(pageNo)
                    .items(accountDetailsResponses)
                    .build();
        }).exceptionally(ex -> {
            throw new AxonException(AxonErrorCode.UNCATEGORIZED_EXCEPTION);
        });
    }



    public CompletableFuture<AccountDetailsResponse> getById(String id) {
        AccountQueryGetById accountQueryGetById = new AccountQueryGetById(id);
        CompletableFuture future = queryGateway.query(accountQueryGetById, ResponseTypes.instanceOf(Account.class));
        Account account = (Account) future.join();

        if(account==null){
            throw new AppException(AppErrorCode.ACCOUNT_NOT_EXISTED);
        }
        return future.thenApply(result -> {
            return AccountDetailsResponse.builder()
                    .id(account.getIdAccount())
                    .username(account.getUsername())
                    .email(account.getEmail())
                    .status(account.getStatus())
                    .idProfile(account.getIdProfile())
                    .build();
        }).exceptionally(ex -> {
            throw new AxonException(AxonErrorCode.UNCATEGORIZED_EXCEPTION);
        });
    }
}
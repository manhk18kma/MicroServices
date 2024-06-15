package TTCS.IdentityService.application.Query.QueryHandler;

import TTCS.IdentityService.application.Query.Query.AccountQueryGetAll;
import TTCS.IdentityService.application.Query.Query.AccountQueryGetById;
import TTCS.IdentityService.domain.model.Account;
import TTCS.IdentityService.infrastructure.persistence.repository.AccountRepository;
import TTCS.IdentityService.infrastructure.persistence.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountQueryHandler {
    final AccountRepository accountRepository;
    final AccountService accountService;

    @QueryHandler
    public List<Account> handle(AccountQueryGetAll accountQueryGetAll) {
        int pageNo = accountQueryGetAll.getPageNo();
        int pageSize = accountQueryGetAll.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Account> page = accountRepository.findAll(pageable);
        return page.getContent();
    }


    @QueryHandler
    public Optional<Account> handle(AccountQueryGetById accountQueryGetById) {
        return accountRepository.findById(accountQueryGetById.getIdAccount());
    }


}

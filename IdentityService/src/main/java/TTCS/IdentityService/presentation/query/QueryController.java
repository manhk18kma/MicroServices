package TTCS.IdentityService.presentation.query;

import TTCS.IdentityService.application.Query.QueryHandler.AccountQueryHandler;
import TTCS.IdentityService.application.Query.QueryService.AccountQueryService;
import TTCS.IdentityService.presentation.command.dto.response.ResponseData;
import TTCS.IdentityService.presentation.query.dto.response.AccountDetailsResponse;
import TTCS.IdentityService.presentation.query.dto.response.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/identity")
public class QueryController {
    final AccountQueryService accountQueryService;

    @GetMapping
    public ResponseData<PageResponse> getAll(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy) throws ExecutionException, InterruptedException {
        CompletableFuture<PageResponse> response = accountQueryService.getAllAccountWithSortBy(pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(), "List of accounts", new Date(), response.get());
    }

//    @PreAuthorize("#profileUpdateRequest.idProfile == authentication.name")
    @GetMapping("/{id}")
//    @PostAuthorize("returnObject.data.idProfile == authentication.name and hasRole('USER')")
    public ResponseData<AccountDetailsResponse> getAccountById(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Username : {}",authentication.getName());
        CompletableFuture<AccountDetailsResponse> account = accountQueryService.getById(id);
        System.out.println(account.get().getIdProfile());
        return new ResponseData<>(HttpStatus.OK.value(), "Account details", new Date(), account.get());
    }

}

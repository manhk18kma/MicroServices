package TTCS.IdentityService.presentation.query;


import TTCS.IdentityService.application.Query.Query.AccountQueryGetById;
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
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/identityQuery")
public class QueryController {
    final AccountQueryService accountQueryService;

        @GetMapping("/getAll")
        public ResponseData<PageResponse> getAll(
                @RequestParam(defaultValue = "0") int pageNo,
                @RequestParam(defaultValue = "10") int pageSize,
                @RequestParam(required = false) String sortBy) throws ExecutionException, InterruptedException {
            CompletableFuture<PageResponse> response = accountQueryService.getAllAccountWithSortBy(pageNo, pageSize);
            return new ResponseData<PageResponse>(HttpStatus.OK.value(), "accounts", response.get());
        }


    @GetMapping("/{idAccount}")
    public ResponseData<AccountDetailsResponse> getAccountById(@PathVariable("idAccount")  String idAccount) throws ExecutionException, InterruptedException {
        CompletableFuture<AccountDetailsResponse> account = accountQueryService.getById(idAccount);
        return new ResponseData<AccountDetailsResponse>(HttpStatus.OK.value(), "accounts", account.get());
    }

}

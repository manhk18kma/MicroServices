package TTCS.IdentityService.presentation.query;

import TTCS.IdentityService.application.Query.QueryService.AccountQueryService;
import TTCS.IdentityService.presentation.command.dto.response.ResponseData;
import TTCS.IdentityService.presentation.query.dto.response.AccountDetailsResponse;
import TTCS.IdentityService.presentation.query.dto.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/accounts")
@Tag(name = "Query controller", description = "APIs for querying account information")
public class QueryController {
    final AccountQueryService accountQueryService;

    @Operation(summary = "Get all accounts", description = "Retrieve a list of accounts with pagination and optional sorting")
    @GetMapping
    public ResponseData<PageResponse> getAll(
            @Parameter(description = "Page number (default: 0)", required = false)
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page number must be zero or positive") int pageNo,
            @Parameter(description = "Page size (default: 10)", required = false)
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be positive") int pageSize,
            @Parameter(description = "Field to sort by (optional)", required = false)
            @RequestParam(required = false) String sortBy
    ) throws ExecutionException, InterruptedException {
        CompletableFuture<PageResponse> response = accountQueryService.getAllAccountWithSortBy(pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(), "List of accounts", new Date(), response.get());
    }

    @Operation(summary = "Get account by ID", description = "Retrieve detailed information of an account by its ID")
    @GetMapping("/{id}")
    @PostAuthorize("returnObject.data.idProfile == authentication.name and hasRole('USER')")
    public ResponseData<AccountDetailsResponse> getAccountById(
            @Parameter(description = "Account ID to retrieve details", required = true)
            @PathVariable("id") @NotNull @NotBlank(message = "ID must not be blank") String id
    ) throws ExecutionException, InterruptedException {
        CompletableFuture<AccountDetailsResponse> account = accountQueryService.getById(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Account details", new Date(), account.get());
    }
}

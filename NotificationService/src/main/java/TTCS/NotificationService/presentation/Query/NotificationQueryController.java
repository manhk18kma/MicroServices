package TTCS.NotificationService.presentation.Query;

import TTCS.NotificationService.application.Query.QueryService.NotificationQueryService;
import TTCS.NotificationService.presentation.DTO.PageResponse;
import TTCS.NotificationService.presentation.DTO.ResponseData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/notificationQuery")
public class NotificationQueryController {
    final NotificationQueryService service;

    @GetMapping("/getAllEmailOTP")
    public ResponseData<PageResponse> getAllEmailOTP(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy) throws ExecutionException, InterruptedException {
        CompletableFuture<PageResponse> response = service.getAllEmailOTP(pageNo, pageSize);
        return new ResponseData<PageResponse>(HttpStatus.OK.value(), "accounts", response.get());
    }

    @GetMapping("/getAllEmailOTPById")
    public ResponseData<PageResponse> getAllEmailOTPByIDAccount(
            @RequestParam String idAccount,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ExecutionException, InterruptedException {
        CompletableFuture<PageResponse> response = service.getAllEmailOTPByIDAccount(idAccount  , pageNo , pageSize);
        return new ResponseData<PageResponse>(HttpStatus.OK.value(), "accounts", response.get());
    }
}

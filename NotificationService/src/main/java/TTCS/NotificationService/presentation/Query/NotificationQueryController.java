package TTCS.NotificationService.presentation.Query;

import TTCS.NotificationService.application.Query.QueryService.NotificationQueryService;
import TTCS.NotificationService.infrastructure.messaging.NotificationProfileService;
import TTCS.NotificationService.presentation.DTO.PageResponse;
import TTCS.NotificationService.presentation.DTO.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notification Query", description = "APIs for querying notifications")

public class NotificationQueryController {
    final NotificationQueryService service;
    final NotificationProfileService notificationProfileService;





    @PreAuthorize("#idProfile == authentication.name and hasRole('USER')")
    @Operation(summary = "Get Notifications by Profile ID", description = "Retrieve notifications for a specific profile with pagination")
    @GetMapping("/{idProfile}")
    public ResponseData<PageResponse> getNotificationsByIdProfile(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number (default: 0)", required = true) int pageNo,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size (default: 10)", required = true) int pageSize,
            @PathVariable @Parameter(description = "Profile ID for which notifications are retrieved", required = true) String idProfile) throws ExecutionException, InterruptedException {

        PageResponse response = notificationProfileService.getNotificationsByIdProfile(pageNo, pageSize, idProfile);
        return new ResponseData<>(HttpStatus.OK.value(), "Notifications", new Date(), response);
    }
}


//    @GetMapping("/getAllEmailOTPById")
//    public ResponseData<PageResponse> getAllEmailOTPByIDAccount(
//            @RequestParam String idAccount,
//            @RequestParam(defaultValue = "0") int pageNo,
//            @RequestParam(defaultValue = "10") int pageSize
//    ) throws ExecutionException, InterruptedException {
//        CompletableFuture<PageResponse> response = service.getAllEmailOTPByIDAccount(idAccount  , pageNo , pageSize);
//        return new ResponseData<PageResponse>(HttpStatus.OK.value(), "accounts", response.get());
//    }


//    @GetMapping("/getAllEmailOTP")
//    public ResponseData<PageResponse> getAllEmailOTP(
//            @RequestParam(defaultValue = "0") int pageNo,
//            @RequestParam(defaultValue = "10") int pageSize,
//            @RequestParam(required = false) String sortBy) throws ExecutionException, InterruptedException {
//        CompletableFuture<PageResponse> response = service.getAllEmailOTP(pageNo, pageSize);
//        return new ResponseData<PageResponse>(HttpStatus.OK.value(), "accounts", response.get());
//    }

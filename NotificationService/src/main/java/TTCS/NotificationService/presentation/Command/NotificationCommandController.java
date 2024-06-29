package TTCS.NotificationService.presentation.Command;

import TTCS.NotificationService.infrastructure.messaging.NotificationProfileService;
import TTCS.NotificationService.presentation.DTO.CheckNotificationResponse;
import TTCS.NotificationService.presentation.DTO.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notification Command", description = "APIs for handling notifications commands")
public class NotificationCommandController {

    final NotificationProfileService notificationProfileService;

    @PostMapping("/profile/{idProfile}")
    @Operation(summary = "Check Notification Status", description = "Check the status of a notification for a specific profile")
    public ResponseData<CheckNotificationResponse> checkNotification(
            @PathVariable @Parameter(description = "ID of the  profile", example = "123", required = true) String idProfile) {

        CheckNotificationResponse response = notificationProfileService.checkNotification(idProfile);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Checked notifications successfully", new Date(), response);
    }
}
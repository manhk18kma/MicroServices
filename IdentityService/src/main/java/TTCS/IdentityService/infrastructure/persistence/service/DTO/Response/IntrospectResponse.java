package TTCS.IdentityService.infrastructure.persistence.service.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class IntrospectResponse {
    boolean valid;
}

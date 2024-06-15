package TTCS.ProfileService.presentation.command.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ProfileResponse implements Serializable {
    private String idProfile;
}

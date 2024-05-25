package TTCS.IdentityService.presentation.query.dto.response;

import TTCS.IdentityService.domain.model.Account;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageResponse<T> implements Serializable {
    int size;
    int totalElements;
    int totalPages;
    int number;
    T items;
}

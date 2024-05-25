package TTCS.ProfileService.presentation.query.dto.response;

import lombok.*;

import java.io.Serializable;

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

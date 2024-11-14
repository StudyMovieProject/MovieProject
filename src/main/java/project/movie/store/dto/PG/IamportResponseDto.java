package project.movie.store.dto.PG;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IamportResponseDto {
    private String code;
    private PaymentResponseDto response;
}

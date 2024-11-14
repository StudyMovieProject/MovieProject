package project.movie.store.dto.PG;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    private String status;
    private Integer payPrice;
    private String impUid;
    private String payCode;
}

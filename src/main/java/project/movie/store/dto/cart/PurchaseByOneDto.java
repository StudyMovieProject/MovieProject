package project.movie.store.dto.cart;

import lombok.Getter;
import lombok.Setter;
import project.movie.store.domain.item.Item;

@Getter
@Setter
public class PurchaseByOneDto {
    private Item item;
    private Integer itemQty;
}

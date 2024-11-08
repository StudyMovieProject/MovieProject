package project.movie.store.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="item_code", nullable=false)
    private Integer itemCode;

    @Column(name = "item_type", nullable=false, length = 30)
    private String itemType;

    @Column(name="item_name", nullable = false, length = 50)
    private String itemName;

    @Column(name="item_detail", nullable = false, length = 100)
    private String itemDetail;

    @Column(name="place", nullable = false, length = 500)
    private String place;

    @Column(name="exp", nullable = false, length = 300)
    private String exp;

    @Column(name="price", nullable = false)
    private Integer price;

    @Column(name="sale_price")
    private Integer salePrice;

    @Column(name="item_image", nullable = false, length = 100)
    private String itemImage;

    @Column(name="item_status", nullable = false)
    private Integer itemStatus;

    protected Item(){}

    public Item(Integer itemCode, String itemType, String itemName,
                String itemDetail, String place, String exp, Integer price, Integer salePrice,
                String itemImage, Integer itemStatus){
        this.itemCode = itemCode;
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.place = place;
        this.exp = exp;
        this.price = price;
        this.salePrice = salePrice;
        this.itemImage = itemImage;
        this.itemStatus = itemStatus;
    }
}

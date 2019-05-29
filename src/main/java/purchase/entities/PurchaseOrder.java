package purchase.entities;

import lombok.Data;

@Data
public class PurchaseOrder {

    private Long id;
    private Long buyerId;
    private Long itemId;

}

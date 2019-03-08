package purchase;

import lombok.Data;

@Data
class Purchase {

    private Long id;
    private Long buyerId;
    private Long itemId;

}

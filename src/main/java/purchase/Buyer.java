package purchase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Buyer {

    private Long id;
    private String name;
    private Order[] purchasedItems;
    private Store preferredStore;

}

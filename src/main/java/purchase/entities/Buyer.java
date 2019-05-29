package purchase.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Buyer {

    private Long id;
    private String name;
    private Purchase[] purchasedItems;
    private Store preferredStore;

}

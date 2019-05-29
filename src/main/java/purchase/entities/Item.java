package purchase.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {

    private Long id;
    private String title;
    private Store store;

}

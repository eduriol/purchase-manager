package purchase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Item {

    private Long id;
    private String title;
    private Store store;

}

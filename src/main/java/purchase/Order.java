package purchase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Order {

    private String orderNumber;
    private String itemTitle;

}

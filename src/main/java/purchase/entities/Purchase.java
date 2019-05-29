package purchase.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Purchase {

    private String purchaseNumber;
    private String itemTitle;

}

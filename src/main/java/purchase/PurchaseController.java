package purchase;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
class PurchaseController {

    @PostMapping("/purchase")
    void addPurchase(@RequestBody Purchase newPurchase) {
        BuyerManagerClient buyerManagerClient = new BuyerManagerClient();
        buyerManagerClient.addPurchase(newPurchase);
    }

}

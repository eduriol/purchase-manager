package purchase;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import purchase.entities.Buyer;
import purchase.entities.Item;
import purchase.entities.Purchase;
import purchase.entities.PurchaseOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PurchaseOrderController {

    @PostMapping("/purchase")
    void addPurchase(@RequestBody PurchaseOrder newPurchaseOrder) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        Buyer buyer = restTemplate.getForObject("http://localhost:8081/buyer-manager/buyer/" + newPurchaseOrder.getBuyerId(), Buyer.class);
        Item item = restTemplate.getForObject("http://localhost:8082/item-manager/item/" + newPurchaseOrder.getItemId(), Item.class);
        List<Purchase> oldPurchasedItems;
        String nextPurchaseNumber;
        if (buyer.getPurchasedItems() != null) {
            oldPurchasedItems = new ArrayList<>(Arrays.asList(buyer.getPurchasedItems()));
            nextPurchaseNumber = getNextPurchaseNumber(buyer);
        } else {
            oldPurchasedItems = new ArrayList<>();
            nextPurchaseNumber = "1";
        }
        Purchase newPurchase = Purchase.builder()
                .purchaseNumber(nextPurchaseNumber)
                .itemTitle(item.getTitle())
                .build();
        oldPurchasedItems.add(newPurchase);
        Purchase[] newPurchasedItems = new Purchase[oldPurchasedItems.size()];
        newPurchasedItems = oldPurchasedItems.toArray(newPurchasedItems);
        Buyer newBuyer = Buyer.builder()
                .id(buyer.getId())
                .name(buyer.getName())
                .preferredStore(buyer.getPreferredStore())
                .purchasedItems(newPurchasedItems)
                .build();
        SimpleKafkaProducer simpleKafkaProducer = new SimpleKafkaProducer();
        Gson gson = new Gson();
        simpleKafkaProducer.sendKafkaMessage(gson.toJson(newBuyer), "add-purchase-topic");
    }

    private String getNextPurchaseNumber(Buyer buyer) {
        int nextPurchaseNumber = 0;
        for (Purchase purchase : buyer.getPurchasedItems()) {
            if (Integer.parseInt(purchase.getPurchaseNumber()) > nextPurchaseNumber) {
                nextPurchaseNumber = Integer.parseInt(purchase.getPurchaseNumber());
            }
        }
        return String.valueOf(nextPurchaseNumber + 1);
    }

}

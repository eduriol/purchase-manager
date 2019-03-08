package purchase;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuyerManagerClient {

    public void addPurchase(Purchase newPurchase) {
        RestTemplate restTemplate = new RestTemplate();
        Buyer buyer = restTemplate.getForObject("http://localhost:8081/buyer-manager/buyer/" + newPurchase.getBuyerId(), Buyer.class);
        Item item = restTemplate.getForObject("http://localhost:8082/item-manager/item/" + newPurchase.getItemId(), Item.class);
        List<Order> oldPurchasedItems = new ArrayList<>(Arrays.asList(buyer.getPurchasedItems()));
        Order newOrder = Order.builder()
                .orderNumber(getNextOrderNumber(buyer))
                .itemTitle(item.getTitle())
                .build();
        oldPurchasedItems.add(newOrder);
        Order[] newPurchasedItems = new Order[oldPurchasedItems.size()];
        newPurchasedItems = oldPurchasedItems.toArray(newPurchasedItems);
        Buyer newBuyer = Buyer.builder()
                .id(buyer.getId())
                .name(buyer.getName())
                .preferredStore(buyer.getPreferredStore())
                .purchasedItems(newPurchasedItems)
                .build();
        HttpEntity<Buyer> httpEntity = new HttpEntity<Buyer>(newBuyer);
        restTemplate.exchange("http://localhost:8081/buyer-manager/buyer/" + buyer.getId(), HttpMethod.PUT, httpEntity, String.class);
    }

    private String getNextOrderNumber(Buyer buyer) {
        int nextOrderNumber = 0;
        for (Order order: buyer.getPurchasedItems()) {
            if (Integer.parseInt(order.getOrderNumber()) > nextOrderNumber) {
                nextOrderNumber = Integer.parseInt(order.getOrderNumber());
            }
        }
        return String.valueOf(nextOrderNumber + 1);
    }

}

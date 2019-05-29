package contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import purchase.entities.Buyer;
import purchase.entities.Item;
import purchase.PurchaseOrderController;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=PurchaseOrderController.class)
@AutoConfigureStubRunner(
        ids = {
            "io.github.eduriol.expoqa:item-manager:+:stubs:9082",
            "io.github.eduriol.expoqa:buyer-manager:+:stubs:9081"
        },
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
public class PurchaseOrderContractTest {

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testContractToGetItem() throws IOException {
        Item expectedItem = objectMapper.readValue(
                new File("src/test/resources/item1.json"),
                Item.class
        );
        Item actualItem = objectMapper.readValue(
                restTemplate.getForObject("http://localhost:9082/item/1", String.class),
                Item.class
        );
        assertThat(expectedItem, is(equalTo(actualItem)));
    }

    @Test
    public void testContractToGetBuyer() throws IOException {
        Buyer expectedBuyer = objectMapper.readValue(
                new File("src/test/resources/buyer1.json"),
                Buyer.class
        );
        Buyer actualBuyer = objectMapper.readValue(
                restTemplate.getForObject("http://localhost:9081/buyer/1", String.class),
                Buyer.class
        );
        assertThat(expectedBuyer, is(equalTo(actualBuyer)));
    }

}

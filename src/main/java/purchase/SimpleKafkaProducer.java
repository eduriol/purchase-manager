package purchase;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class SimpleKafkaProducer {

    private KafkaProducer<String, String> kafkaProducer;

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public SimpleKafkaProducer() throws IOException {
        InputStream input = new FileInputStream("src/main/resources/kafka.properties");
        Properties producerProperties = new Properties();
        producerProperties.load(input);
        String environment = "";
        if (System.getProperty("environment") != null) {
            environment = System.getProperty("environment");
        }
        if (environment.equals("compose")) {
            producerProperties.setProperty("bootstrap.servers", "kafka1:19092");
        }
        kafkaProducer = new KafkaProducer<>(producerProperties);
    }

    public void sendKafkaMessage(String payload, String topic) {
        LOGGER.info("Sending Kafka message: " + payload);
        kafkaProducer.send(new ProducerRecord<>(topic, payload));
    }

}

package com.baozi.pulsar.issue.i12091;

import java.util.concurrent.TimeUnit;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Reader;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;

/**
 * https://github.com/apache/pulsar/issues/12091
 */
public class ProducerTest2 {

    public static void main(String[] args) throws PulsarClientException, InterruptedException {

        String serviceUrl = "pulsar://localhost:6650";
        String topic = "test-retention";
        PulsarClient client = PulsarClient.builder().serviceUrl(serviceUrl).build();

        Producer<String> producer = client.newProducer(Schema.STRING).topic(topic).create();

        for (int i = 5100; i < 6000; i++) {
            producer.newMessage().value("hello baozi" + i).send();
        }

        System.out.println("send end");
        System.exit(0);
    }
}

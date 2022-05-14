package com.baozi.pulsar.issue.ci545;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.client.impl.schema.JSONSchema;

/**
 * https://github.com/apache/pulsar/issues/12091
 */
public class ProducerTest {

    public static void main(String[] args) throws PulsarClientException, InterruptedException {

        String serviceUrl = "pulsar://localhost:6650";
        String topic = "sample/ns1/my-topic4-partition-4";
        PulsarClient client = PulsarClient.builder().serviceUrl(serviceUrl).build();

        Producer<String> producer = client.newProducer(Schema.STRING).topic(topic).create();


        for (int i = 0; i < 10000000; i++) {
            producer.newMessage().value("test " + i).send();
        }
    }
}

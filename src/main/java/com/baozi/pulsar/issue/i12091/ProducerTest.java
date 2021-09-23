package com.baozi.pulsar.issue.i12091;

import java.util.concurrent.TimeUnit;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;

/**
 * https://github.com/apache/pulsar/issues/12091
 */
public class ProducerTest {

    public static void main(String[] args) throws PulsarClientException, InterruptedException {

        String serviceUrl = "pulsar://localhost:6650";
        String topic = "my-topic4";
        PulsarClient client = PulsarClient.builder().serviceUrl(serviceUrl).build();

        Producer<byte[]> producer = client.newProducer().topic(topic).create();

        producer.newMessage().deliverAfter(15000, TimeUnit.MILLISECONDS)
                .value("hello baozi".getBytes()).send();

        Thread.sleep(20000);

        Consumer<byte[]> subscribe = client.newConsumer()
                .topic(topic)
                .subscriptionName("my-subscribe")
                .ackTimeout(10, TimeUnit.SECONDS)
                .subscriptionType(SubscriptionType.Exclusive)
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                .subscribe();

        while (true) {
            Message<byte[]> receive = subscribe.receive();
            System.out.println(new String(receive.getValue()));
            subscribe.acknowledge(receive);
        }
    }
}

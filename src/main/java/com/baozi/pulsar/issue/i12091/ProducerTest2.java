package com.baozi.pulsar.issue.i12091;

import java.util.concurrent.TimeUnit;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Reader;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;

/**
 * https://github.com/apache/pulsar/issues/12091
 */
public class ProducerTest2 {

    public static void main(String[] args) throws PulsarClientException, InterruptedException {

        String serviceUrl = "pulsar://localhost:6650";
        String topic = "my-topic-reader";
        PulsarClient client = PulsarClient.builder().serviceUrl(serviceUrl).build();

        Producer<byte[]> producer = client.newProducer().topic(topic).create();

        producer.newMessage().deliverAfter(15000, TimeUnit.MILLISECONDS)
                .value("hello baozi".getBytes()).send();

        Thread.sleep(20000);

        Reader<byte[]> reader =
                client.newReader().topic(topic).readerName("my-reader").startMessageId(MessageId.earliest).create();

        while (true) {
            Message<byte[]> message = reader.readNext();
            System.out.println(new String(message.getValue()));
        }
    }
}

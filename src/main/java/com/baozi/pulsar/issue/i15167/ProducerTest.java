package com.baozi.pulsar.issue.i15167;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.pulsar.client.admin.BrokerStats;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminBuilder;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

/**
 * https://github.com/apache/pulsar/issues/12091
 */
public class ProducerTest {

    public static void main(String[] args) throws PulsarClientException, InterruptedException, PulsarAdminException {

        PulsarAdmin admin = PulsarAdmin.builder()
                .serviceHttpUrl("http://localhost:8080")
                .build();
        BrokerStats brokerStats = admin.brokerStats();
        System.out.println(brokerStats.getLoadReport());
    }
}

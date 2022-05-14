package com.baozi.pulsar.issue;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.PulsarClient;

/**
 * @author baozi
 */
public class PulsarAdminTest {

    public static void main(String[] args) throws Exception {
        String serviceUrl = "https://localhost:8080";
        String topic = "public/default/test_avro";
        PulsarAdmin admin = PulsarAdmin.builder().serviceHttpUrl(serviceUrl).build();

        admin.topics();

    }
}

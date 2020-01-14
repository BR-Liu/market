package com.brliu.configuration;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfiguration {

    @Value("${rocketmq.namesvr-host}")
    private String host;

    @Value("${rocketmq.namesvr-port}")
    private String port;

    @Value("${rocketmq.provider-group}")
    private String provider_group;

    @Bean(name = "DefaultMQProducer")
    public DefaultMQProducer getDefaultMQProducer() {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setNamesrvAddr(host + ":" + port);
        defaultMQProducer.setProducerGroup(provider_group);
        return defaultMQProducer;
    }

    @Bean(name = "DefaultMQConsumer")
    public DefaultMQPushConsumer getDefaultMQConsumer() {
        DefaultMQPushConsumer defaultMQConsumer = new DefaultMQPushConsumer();
        defaultMQConsumer.setNamesrvAddr(host + ":" + port);
        defaultMQConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        return defaultMQConsumer;
    }


}

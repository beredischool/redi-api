package org.redischool.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * Created by ReDI on 2/3/2017.
 */
@Configuration
public class JmsConfig {

    @Bean
    public Queue queue() {
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
        return new ActiveMQQueue("activate");
    }
}

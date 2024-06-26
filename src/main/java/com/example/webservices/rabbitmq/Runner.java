package com.example.webservices.rabbitmq;

import com.example.webservices.WebServicesApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


//@Component
//public class Runner implements CommandLineRunner {
//
//    private final RabbitTemplate rabbitTemplate;
//    private final RabbitMQReceiver receiver;
//
//    public Runner(RabbitMQReceiver receiver, RabbitTemplate rabbitTemplate) {
//        this.receiver = receiver;
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Sending message...");
//        rabbitTemplate.convertAndSend(WebServicesApplication.topicExchangeName, "foo.bar.baz", "1,Hello from RabbitMQ!");
//        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
//    }
//
//}
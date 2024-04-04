package com.example.webservices.rabbitmq;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

@Component
public class RabbitMQReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message){
        String[] messages = message.split(",");
        System.out.println("Received <" +messages[0].toString()+ messages[1].toString()+">");
        latch.countDown();
    }
    public CountDownLatch getLatch(){
        return latch;
    }
}

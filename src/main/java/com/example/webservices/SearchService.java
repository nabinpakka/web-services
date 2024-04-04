package com.example.webservices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import com.example.webservices.rabbitmq.RabbitMQReceiver;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SearchService {

    //specify the library index file.
    public static String indexFileLocation = "/Users/hubbleloo/Desktop/web-services/src/main/resources/static" +
            "" + File.separator + "index";
    public static String indexFileName = "Index.txt";
    //a flag to exit the program
    public static boolean exit=false;

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQReceiver receiver;

    private Hashtable<String, String> resultTable = new Hashtable<String, String>();

    public SearchService(RabbitTemplate rabbitTemplate, RabbitMQReceiver receiver){
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    public void sendReceivedMessageToRabbitMQ(String message) throws InterruptedException {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(WebServicesApplication.topicExchangeName, "foo.bar.baz", message);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

    @RabbitListener(queues = {WebServicesApplication.queueName})
    public void onUserRegistration(String message) {
        System.out.println("User Registration Event Received: "+ message);
        searchTermInIndex(message);
    }
    public void searchTermInIndex(String message){
        String[] messages = message.split(",");
        System.out.println("Received <" +messages[0].toString()+ messages[1].toString()+">");
        String result = findTermInIndex(messages[1]);

        resultTable.put(messages[0], result);
    }
    private String findTermInIndex(String term){
        File file = new File(indexFileLocation + File.separator + indexFileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {

                String [] spiltWords =  st.split("\\|.\\|");
                if(spiltWords[0].equals(term)){
                    System.out.println(st);
                    return st;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String retrieveFromQueue(String id){
        return resultTable.get(id);
    }
}

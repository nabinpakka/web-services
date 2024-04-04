package com.example.webservices;

import com.example.webservices.rabbitmq.RabbitMQReceiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class APITester {
    int MIN =1;
    int MAX = 100;

    private HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    public static void main(String[] args) {
        String[] terms = {"card","cards","role","energy", "pick", "golf", "women", "pink", "woman"};
        List<Integer> ids = new ArrayList<>();

        APITester apiTester = new APITester();
        UniqueRandomNumberGenerator randomNumberGenerator = new UniqueRandomNumberGenerator();

        for (String term: terms){
            int id = randomNumberGenerator.generateUniqueRandomNumber(apiTester.MIN, apiTester.MAX);
            ids.add(id);
            String message = String.valueOf(id) + "," + term;
            apiTester.sendSearchTerm(message);
        }

        System.out.println("Waiting for 30 seconds before retrieving result using ID");

        try {
            Thread.sleep(30000); // 30 seconds = 30,000 milliseconds
        } catch (InterruptedException e) {
            // Handle interrupted exception (if necessary)
        }

        for (int id: ids){
            apiTester.getResultUsingId(id);
        }
    }

    private void sendSearchTerm(String term){

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:8080/post?term="+term))
                    .build();
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println("Status code: " + response.statusCode());
//            System.out.println("Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getResultUsingId(int id){

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:8080/search?id="+id))
                    .build();
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());


            System.out.println("ID:" + id + "--->" + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

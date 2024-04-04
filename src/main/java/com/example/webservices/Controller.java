package com.example.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private SearchService service;

    @GetMapping("/post")
    public void searchTermInIndex(@RequestParam(name="term") String term) throws InterruptedException {
        service.sendReceivedMessageToRabbitMQ(term);
    }

    @GetMapping("/search")
    public String getResultUsingId(@RequestParam(name = "id") String id){
        return service.retrieveFromQueue(id);
    }
}

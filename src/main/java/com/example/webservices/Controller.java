package com.example.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private SearchService service;

    @GetMapping("/search")
    public String searchTermInIndex(@RequestParam(name="term") String term){
        return service.searchTermInIndex(term);
    }
}

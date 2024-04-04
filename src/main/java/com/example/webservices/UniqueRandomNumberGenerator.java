package com.example.webservices;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class UniqueRandomNumberGenerator {

    private Set<Integer> generatedNumbers = new HashSet<>();
    private Random random = new Random();

    public int generateUniqueRandomNumber(int min, int max) {
        int randomNumber;
        do {
            randomNumber = random.nextInt(max - min + 1) + min;
        } while (generatedNumbers.contains(randomNumber));

        generatedNumbers.add(randomNumber);
        return randomNumber;
    }
}

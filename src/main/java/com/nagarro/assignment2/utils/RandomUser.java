package com.nagarro.assignment2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.assignment2.model.UserEntity;

import reactor.core.publisher.Mono;

public class RandomUser {
    @Autowired
    private WebClient webClient;

    public RandomUser(WebClient webClient) {

        this.webClient = webClient;
    }

    // Step 1: Get random user
    public UserEntity getRandomUser() {

        String userStatus = new String();
        String receivedGender = new String();
        String receivedNationality = "User TO_BE_VERIFIED";
        UserEntity user = null;

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Mono<JsonNode> jsonMono = webClient
                .get()
                .uri("https://randomuser.me/api/")
                .retrieve()
                .bodyToMono(JsonNode.class);

        Future<JsonNode> jsonFuture = jsonMono.toFuture();

        // Step 2: Get nationality using Executor framework
        Future<JsonNode> nationalityFuture = executorService.submit(() -> {
            JsonNode jsonNode = jsonFuture.get();
            JsonNode resultNode = jsonNode.path("results").get(0);

            String firstName = resultNode.path("name").path("first").asText();

            Mono<JsonNode> nationalityMono = webClient.get()
                    .uri("https://api.nationalize.io/?name=" + firstName)
                    .retrieve()
                    .bodyToMono(JsonNode.class);

            return nationalityMono.block();
        });

        // Step 3: Get gender using Executor framework
        Future<JsonNode> genderFuture = executorService.submit(() -> {
            JsonNode jsonNode = jsonFuture.get();
            JsonNode resultNode = jsonNode.path("results").get(0);
            String firstName = resultNode.path("name").path("first").asText();

            Mono<JsonNode> genderMono = webClient.get()
                    .uri("https://api.genderize.io/?name=" + firstName)
                    .retrieve()
                    .bodyToMono(JsonNode.class);

            return genderMono.block();
        });

        // Step 4: Validation and marking
        try {
            JsonNode jsonNode = jsonFuture.get();
            JsonNode resultNode = jsonNode.path("results").get(0);

            System.out.println(resultNode);

            receivedNationality = resultNode.path("nat").asText();

            System.out.println(receivedNationality);
            receivedGender = resultNode.path("gender").asText();

            JsonNode nationalityResponse = nationalityFuture.get();
            JsonNode genderResponse = genderFuture.get();

            if (nationalityResponse.has("country")) {
                // JsonNode countryNode = nationalityResponse.path("country").get(0);
                // String matchedCountry = countryNode.path("country_id").asText();

                // System.out.println(matchedCountry);
                
                JsonNode countryNode = nationalityResponse.path("country");
                System.out.println(countryNode);

                if (receivedGender.equals(genderResponse.path("gender").asText())) {
                    for(JsonNode i : countryNode){
                        
                        String matchedCountry = i.path("country_id").asText();
                        if(receivedNationality.equals(matchedCountry)){
                            userStatus = "User VERIFIED";
                            break;

                        }
                    }
                    // userStatus = "User VERIFIED";

                    
                } 
            }
            System.out.println(userStatus);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Shutdown executor service
        executorService.shutdown();

       try{ 
        // saving data to model class using builder..
        JsonNode jsonNode = jsonFuture.get();
        JsonNode resultNode = jsonNode.path("results").get(0);
        String firstName = resultNode.path("name").path("first").asText();
        String lastName = resultNode.path("name").path("last").asText();
        String fullName = firstName + " " + lastName;

            user = UserEntity.builder()
                .name(fullName)
                .age(Integer.valueOf(resultNode.path("dob").path("age").asText()))
                .dob(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(resultNode.path("dob").path("date").asText()))
                .gender(receivedGender)
                .nationality(receivedNationality)
                .verificationStatus(userStatus)
                .dateCreated(new Date())
                .dateModified(new Date())
                .build();
       } catch (Exception e) {
            e.printStackTrace();
        }
        return user;

        // ExecutorService executorService = Executors.newFixedThreadPool(2);

        // // ObjectMapper objectMapper = new ObjectMapper();

        // Mono<JsonNode> jsonMono = webClient
        // .get()
        // .uri("https://randomuser.me/api/")
        // .retrieve()
        // .bodyToMono(JsonNode.class);

        // jsonMono.subscribe(jsonNode -> {
        // JsonNode resultNode = jsonNode.path("results").get(0);

        // String fullName = resultNode.path("name").path("first").asText() + " " +
        // resultNode.path("name").path("last").asText();
        // int age = resultNode.path("dob").path("age").asInt();
        // String gender = resultNode.path("gender").asText();
        // String dob = resultNode.path("dob").path("date").asText();
        // String nationality = resultNode.path("nat").asText();

        // });
        // performs all task in single thread... 1 thread is free so...
        // Future<JsonNode> jsonFuture = jsonMono.toFuture();

        // // Step 2 and Step 3: Execute API calls in parallel using Executor framework
        // executorService.submit(() -> {
        // try {
        // JsonNode jsonNode = jsonFuture.get();
        // System.out.println(jsonNode);
        // JsonNode resultNode = jsonNode.path("results").get(0);

        // // String fullName = resultNode.path("name").path("first").asText() + " " +
        // // resultNode.path("name").path("last").asText();
        // String fullName = resultNode.path("name").path("first").asText();
        // System.out.println("Full Name: " + fullName);

        // // Step 2: Get nationality using Executor framework
        // Mono<JsonNode> nationalityMono = webClient.get()
        // .uri("https://api.nationalize.io/?name=" + fullName)
        // .retrieve()
        // .bodyToMono(JsonNode.class);

        // JsonNode nationalityResponse = nationalityMono.block();

        // // Process nationalityResponse

        // // Step 3: Get gender using Executor framework
        // Mono<JsonNode> genderMono = webClient.get()
        // .uri("https://api.genderize.io/?name=" + fullName)
        // .retrieve()
        // .bodyToMono(JsonNode.class);

        // JsonNode genderResponse = genderMono.block();

        // // Process genderResponse

        // // Step 4: Validation and marking

        // String receivedNationality = resultNode.path("nat").asText();
        // System.out.println("receivedNationality " + receivedNationality);
        // String receivedGender = resultNode.path("gender").asText();
        // System.out.println("receivedGender " + receivedGender);

        // if (nationalityResponse.has("country")) {

        // System.out.println("country " + nationalityResponse.path("country").get(0));
        // JsonNode countryNode = nationalityResponse.path("country").get(0);
        // String matchedCountry = countryNode.path("country_id").asText();

        // if (receivedNationality.equals(matchedCountry)
        // && receivedGender.equals(genderResponse.path("gender").asText())) {
        // System.out.println("User VERIFIED");
        // } else {
        // System.out.println("User TO_BE_VERIFIED");
        // }
        // } else {
        // System.out.println("User TO_BE_VERIFIED");
        // }

        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // });

        // // Shutdown executor service
        // executorService.shutdown();

    }

}

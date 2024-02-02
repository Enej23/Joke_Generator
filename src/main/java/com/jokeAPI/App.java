package com.jokeAPI;
import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class App 
{
    public static void main( String[] args ) {
        System.out.println("Welcome to the joke generator!!");
        HashMap<Integer, String> categoires = new HashMap<>();
        categoires.put(1, "Any");
        categoires.put(2, "Miscellaneous");
        categoires.put(3, "Programming");
        categoires.put(4, "Dark");
        categoires.put(5, "Pun");
        categoires.put(6, "Spooky");
        categoires.put(7, "Christmas");
        Scanner scanner = new Scanner(System.in);

        while(true) {

            System.out.println("Choose a category of the joke:");
            for (Map.Entry<Integer, String> entry : categoires.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            int choice;
            do {
                System.out.println("Enter your choice (1-7):");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // consume the invalid input
                }
                choice = scanner.nextInt();

                if (choice <= 0 || choice >= 8) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } while (choice <= 0 || choice >= 8);

            System.out.println("Enter the number of jokes");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // consume the invalid input
            }

            int numOfJokes = scanner.nextInt();


            String apiUrl = "https://v2.jokeapi.dev/joke/" + categoires.get(choice) + "?amount=" + numOfJokes; // Change 'Any' to the desired category
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();
            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String jsonResponse = response.body();
                    extractJoke(jsonResponse);
                } else {
                    System.out.println("Error: Unable to fetch joke. HTTP status code: " + response.statusCode());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Do you wish to receive more jokes?");
            System.out.println("1: Continue");
            System.out.println("0: Exit the app");
            int input = scanner.nextInt();
            if(input == 0)
                break;
        }
    }

    private static void extractJoke(String jsonResponse) {
        Gson gson = new Gson();

        JokeList jokeListWrapper = gson.fromJson(jsonResponse, JokeList.class);
        List<JokeData> jokes = jokeListWrapper.getJokes();

        if (jokes == null){
            JokeData jokedata = gson.fromJson(jsonResponse, JokeData.class);
            if (jokedata.getType().equals("single")) {
                System.out.println(jokedata.getJoke());
            } else {
                System.out.println(jokedata.getSetup());
                System.out.println(jokedata.getDelivery());
            }
        }
        else{
            for(JokeData joke : jokes) {
                if (joke.getType().equals("single")) {
                    System.out.println(joke.getJoke());
                } else {
                    System.out.println(joke.getSetup());
                    System.out.println(joke.getDelivery());
                }
                System.out.println("");
            }
        }
    }
}

package sa4e.ueb2.WishSimulator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class APITester extends Thread {
    String apiURL;
    AtomicInteger callCount;

    APITester(String apiURL, AtomicInteger callCount) {
        this.apiURL = apiURL;
        this.callCount = callCount;
    }

    @Override
    public void run() {
        HttpClient client = HttpClient.newHttpClient();

        while(!interrupted()) {
            try{
                String jsonStrong = String.format("{\"id\": %d, \"wishClaimer\": \"%s\", \"targetAddress\": \"%s\", \"wish\": \"%s\", \"status\": %d}",
                        new Random().nextLong(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 1);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(apiURL))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonStrong))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() <= 200 && response.statusCode() < 300) {
                    callCount.incrementAndGet();
                } else {
                    System.out.println("Fehler bei der Anfrage: " + response.statusCode());
                }
            } catch (Exception e) {
                return;
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        int threadCount = 100;
        int numOfRuns = 50;
        PrintWriter out = new PrintWriter("results.csv");

        String apiURL = "http://localhost:4242/api";
        AtomicInteger callCount = new AtomicInteger(0);

        APITester[] threads = new APITester[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new APITester(apiURL, callCount);
            threads[i].start();
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ignore) {}

        for (int i = 0; i < numOfRuns; i++) {
            System.out.println("START");
            int start = callCount.get();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ignore) {}

            int end = callCount.get();
            System.out.println("END");
            out.println(end - start);

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ignore) {}
        }

        out.close();

        for (int i = 0; i < threadCount; i++) {
            threads[i].interrupt();
        }
    }
}


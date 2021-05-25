import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class HealthCheck {

    public static void main(String[] args) throws InterruptedException, IOException {
        String port = System.getenv("PORT");

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:" + port + "/actuator/health"))
                        .header("accept", "application/json")
                        .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        if (response.statusCode() != 200 || !response.body().contains("UP")) {
            throw new RuntimeException("Healthcheck failed");
        }
    }
}

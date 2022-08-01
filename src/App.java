import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    private static JsonParser jsonParser;

    public static void main(String[] args) throws Exception {
        
        //connect using HTTP and search for top 250 movies
        String url = "https://imdb-api.com/en/API/Top250Movies/k_3pzfcagx";
        URI adress = URI.create(url);
        
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(adress).GET().build(); // using var just to know that it works fine here

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body  = response.body();

        //get usefull data (movie name, poster and rating)
        jsonParser = new JsonParser();
        List<Map<String, String>> moviesList = jsonParser.parse(body);
       
        // show info
        for (Map<String,String> movie : moviesList) {
            System.out.println(movie.get("title"));

            System.out.println(movie.get("image"));

            System.out.println(movie.get("imDbRating"));
            System.out.println(movie.get("imDbRatingCount"));
        }
    }
}

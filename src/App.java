import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class App {
    private static JsonParser jsonParser;

    public static void main(String[] args) throws Exception {

        //read information from a .properties file
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(Paths.get(".").toAbsolutePath().normalize().toString() + "/.properties"));
        } catch (IOException e) {
            System.out.println("Impossible to read the properties file: " + e.getMessage());
            System.exit(-1);
        }

        //connect using HTTP and search for top 250 movies
        String url = properties.getProperty("imdbUrl") + properties.getProperty("imdbKey");
        URI adress = URI.create(url);
        
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(adress).GET().build(); // using var just to know that it works fine here

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body  = response.body();

        //get usefull data (movie name, poster and rating)
        jsonParser = new JsonParser();
        List<Map<String, String>> moviesList = jsonParser.parse(body);
       
        // show info
        System.out.println("\u001b[37m \u001b[44m \u001b[1mMost popular movies in IMDB:\u001b[m");
        for (Map<String,String> movie : moviesList) {

            String movieRating = (String) movie.get("imDbRating");

            if (movieRating.isEmpty()) {
                continue;
            }
            var movieName = movie.get("title");
            var moviemoviePosterUrl = movie.get("image");
            var movieRatingCount = movie.get("imDbRatingCount");

            var starsRating = Double.parseDouble(movieRating) / 2;
            int intPartStars = (int) starsRating;
            Double decimalPartStars =  starsRating - intPartStars;
            var starsString = "";
            for (int i = 0; i < intPartStars; ++i) {
                starsString += "⭐";
            }
            if (decimalPartStars >= 0.50) {
                starsString += "✨"; //representing half star
            }

            System.out.println("\u001b[37m \u001b[44m \u001b[1mMovie title:\u001b[m" + " " + movieName);
            System.out.println("\u001b[1mPoster URL:\u001b[m" + " " + moviemoviePosterUrl);
            System.out.println("\u001b[1mRating:\u001b[m" + " " + starsString + " based on " + movieRatingCount + " votes\u001b[m");
            System.out.println("");
        }
    }
}

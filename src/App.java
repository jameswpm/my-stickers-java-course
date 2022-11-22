import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        //ask user if he wants to list or rate a movie
        int option = 100;
        Scanner sc = new Scanner(System.in);        
        
        while (option != 0) {
            System.out.print("Do you want to list the movies or rate one of the movies?\n1) List all movies\n2) Rate a random movie\n3) Generate Stickers package\n0) Quit the program\n");
            option = sc.nextInt();
            switch(option) {
                case 1: 
                    listMovies(properties);
                    option = 0;
                    break;
                case 2:
                    rateAMovie(properties);
                    option = 0;
                    break;
                case 3:
                    generateStickers(properties);
                    option = 0;
                    break;
                case 0 :
                    break;
                default:
                    System.out.print("Unrecognized option. Please select again\n"); 
            }
        }
        
        sc.close();
    }

    private static void rateAMovie(Properties properties) throws IOException, InterruptedException, SQLException {
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

        List<Map<String, String>> moviesFilter = pickRandom(moviesList, 1);
        Map<String, String> movie = moviesFilter.get(0);
        var movieName = movie.get("title");
        
        System.out.println("The movie is: " + movieName + ". Rate it with 0 to 5 stars typing something between 0 and 5.\n");
        Scanner sc = new Scanner(System.in);
        int rate = sc.nextInt();

        //while to avoid out of bound options
        while (rate < 0 || rate > 5) {
            System.out.println("Unparseble input. Try again\n");
            rate = sc.nextInt();
        }

        //store the movie id, movie name and user rate into a sqlite file
        Create.createNewDatabase("my_movies.db");
        CreateTable.createNewTable();
        InsertMovie insert = new InsertMovie();
        insert.insert(movieName, rate);

        //list already rated movies
        SelectMovies select = new SelectMovies();
        ResultSet rs = select.selectAll();

        System.out.println("These are your current rated movies:\n");
        while (rs.next()) {  
            System.out.println(rs.getInt("id") +  "\t" +   
                               rs.getString("name") + "\t" +  
                               rs.getDouble("rate"));  
        }

        sc.close();
    }

    private static void listMovies(Properties properties) throws IOException, InterruptedException {
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

    private static void generateStickers(Properties properties) throws IOException, InterruptedException {
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
       
        StickersGenerator generator = new StickersGenerator();
        // generate stickers and save them into output folder
        System.out.println("\u001b[37m \u001b[44m \u001b[1mGenerating Stickers for movies in IMDB:\u001b[m");
        for (Map<String,String> movie : moviesList) {

            String movieRating = (String) movie.get("imDbRating");

            if (movieRating.isEmpty()) {
                continue;
            }
            var movieName = movie.get("title");
            var moviemoviePosterUrl = movie.get("image");

            System.out.println("\u001b[37m \u001b[44m \u001b[1mMovie title:\u001b[m" + " " + movieName);
            
            InputStream movieURL = new URL(moviemoviePosterUrl).openStream();
            try {
                generator.create(movieURL, movieName);
            } catch (Exception e) {
                System.out.println("Impossible to generate sticker for this movie. The error was: " + e.getMessage());
                continue;
            }
            System.out.println("Sticker generated");            

            System.out.println("");
        }
    }

    public static List<Map<String, String>> pickRandom(List<Map<String, String>> list, int n) {
        if (n > list.size()) {
            throw new IllegalArgumentException("not enough elements");
        }
        Random random = new Random();
        return IntStream
                .generate(() -> random.nextInt(list.size()))
                .distinct()
                .limit(n)
                .mapToObj(list::get)
                .collect(Collectors.toList());
    }
}
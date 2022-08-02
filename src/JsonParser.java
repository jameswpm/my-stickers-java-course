import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    public List<Map<String, String>> parse(String json) throws JsonMappingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        MoviesList readValue = mapper.readValue(json, MoviesList.class);
                
        List<Map<String, String>> data = new ArrayList<>();

        for (Movie movie : readValue.getItems()) {

            Map<String, String> movieAttributes = new HashMap<>();
            
            movieAttributes.put("title", movie.getTitle());
            movieAttributes.put("image", movie.getImage());
            movieAttributes.put("imDbRatingCount", movie.getImDbRatingCount());
            movieAttributes.put("imDbRating", movie.getImDbRating());   
        
            data.add(movieAttributes);
        }

        return data;
    }
    
}

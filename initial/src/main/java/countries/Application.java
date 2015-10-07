package countries;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Country{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return "{" + "name: " + getName() +"}";
		}
	}
	
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        
        ParameterizedTypeReference<List<Country>> typeRef = new ParameterizedTypeReference<List<Country>>() {};
        ResponseEntity<List<Country>> response = restTemplate.exchange("http://restcountries.eu/rest/v1/name/united states of america"
        				, HttpMethod.GET, null, typeRef);
      
        for(Country c : response.getBody()){
        	System.out.println(c);
        }
    }
}
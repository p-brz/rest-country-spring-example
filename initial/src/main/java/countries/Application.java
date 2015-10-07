package countries;

import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import countries.input.IInput;
import countries.input.ScannerInput;
import countries.scanner.DefaultScanner;
import edu.stanford.ejalbert.BrowserLauncher;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    	IInput inputMethod = new ScannerInput();
    	String countryName = inputMethod.getCountryName();
    	
    	if(countryName.isEmpty()){
    		System.out.println("Você deve informar um país");
    		System.exit(1);
    	}
    	
        RestTemplate restTemplate = new RestTemplate();
        
        ParameterizedTypeReference<List<Country>> typeRef = new ParameterizedTypeReference<List<Country>>() {};
        ResponseEntity<List<Country>> response = null;
        
        try{
        	response = restTemplate.exchange("http://restcountries.eu/rest/v1/name/" + countryName
        				, HttpMethod.GET, null, typeRef);
        }catch(Exception e){
        	System.out.println("Não encontramos países para estes parâmetros");
        	return;
        }
      
        
        List<Country> allCountries = response.getBody();
        int foundCountries = allCountries.size();
        if(foundCountries > 1){
        	System.out.println("Encontramos " + allCountries.size() + " correspondências: ");
        }else if(foundCountries == 0){
        	System.out.println("Não encontramos países para estes parâmetros");
        	return;
        }
        for(Country c : allCountries){
        	System.out.println("");
        	String cName = c.getName();
        	String cCapital = c.getCapital();
        	
        	String cComplemento = "";
        	if(!cCapital.isEmpty()){
        		cComplemento = String.format("tem como capital '%s'", cCapital); 
        	}else{
        		cComplemento = "não possui capital registrada na API";
        	}
        	
        	String text = String.format("O país de nome '%s' %s", cName, cComplemento);
        	System.out.println(text);
        	
        	if(c.getLatlng() != null && c.getLatlng().size() >= 2){
            	int lat = c.getLatlng().get(0);
            	int lng = c.getLatlng().get(1);
            	
            	Scanner scanner = DefaultScanner.getInstance();
            	System.out.println("Gostaria de visualizar um mapa com o país selecionado? (y/N)");
            	
            	String mapResponse = scanner.nextLine();
            	scanner.close();
            	if(mapResponse.toLowerCase().startsWith("y")){
            		String url = String.format("http://www.openstreetmap.org/#map=4/%d/%d&layers=Q", lat, lng);
            		BrowserLauncher launcher = new BrowserLauncher();
            		launcher.openURLinBrowser(url);
            	}
        	}else{
        		System.out.println("Este país não tem localização informada");
        	}
        	
        	DefaultScanner.closeScanner();
        }
        System.out.println("");
        System.out.println("Valeu falou!");
        return;
    }
}
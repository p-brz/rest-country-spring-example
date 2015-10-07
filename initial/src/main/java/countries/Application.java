package countries;

import java.text.NumberFormat;
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
    	String countryName = this.getCountryName(inputMethod);
    	
    	List<Country> allCountries = this.getCountryListFromAPI(countryName);
    	this.checkCountryListSize(allCountries.size());
    	
    	int processedCountries = 0;
        for(Country c : allCountries){
        	processedCountries++;
        	this.handleCountry(c, processedCountries);
        }
        
        DefaultScanner.closeScanner();
        this.printExit();
        return;
    }
    
    private String getCountryName(IInput inputMethod){
    	String countryName = inputMethod.getCountryName();
    	
    	if(countryName.isEmpty()){
    		System.out.println("Você deve informar um país");
    		System.exit(1);
    	}
    	
    	return countryName;
    }
    
    private List<Country> getCountryListFromAPI(String countryName){
    	RestTemplate restTemplate = new RestTemplate();
        
        ParameterizedTypeReference<List<Country>> typeRef = new ParameterizedTypeReference<List<Country>>() {};
        ResponseEntity<List<Country>> response = null;
        
        try{
        	response = restTemplate.exchange("http://restcountries.eu/rest/v1/name/" + countryName
        				, HttpMethod.GET, null, typeRef);
        }catch(Exception e){
        	System.out.println("Não encontramos países para estes parâmetros");
        	System.exit(0);
        }
      
        
        List<Country> allCountries = response.getBody();
        return allCountries;
    }
    
    private void checkCountryListSize(int foundCountries){
        if(foundCountries > 1){
        	System.out.println("Encontramos " + foundCountries + " correspondências: ");
        }else if(foundCountries == 1){
        	System.out.println("Encontramos " + foundCountries + " correspondência: ");
        }else if(foundCountries == 0){
        	System.out.println("Não encontramos países para estes parâmetros");
        	return;
        }
    }
    
    private void handleCountry(Country c, int number){
    	this.printCountryInfo(c, number);
    	this.printCountryMap(c);
    }
    
    private void printCountryInfo(Country c, int number){
    	System.out.println("");
    	String cName = c.getName();
    	String cRegion = c.getRegion();
    	String cCapital = c.getCapital();
    	int population = c.getPopulation();
    	
    	if(cCapital.isEmpty()){
    		cCapital = "Não informada pela API";
    	}
    	
    	
    	String formattedPopulation = NumberFormat.getInstance().format(population);
    	System.out.println(String.format("%s) %s", "" + number, cName));
    	System.out.println(String.format("\t-Capital: %s", cCapital));
    	System.out.println(String.format("\t-Região: %s", cRegion));
    	System.out.println(String.format("\t-População: %s", formattedPopulation));
    }
    
    private void printCountryMap(Country c){
    	if(c.getLatlng() != null && c.getLatlng().size() >= 2){
        	int lat = c.getLatlng().get(0);
        	int lng = c.getLatlng().get(1);
        	
        	Scanner scanner = DefaultScanner.getInstance();
        	System.out.println();
        	System.out.println("\tGostaria de visualizar um mapa com o país selecionado? (y/N)");
        	System.out.print("\t> ");
        	
        	String mapResponse = scanner.nextLine();
        	if(mapResponse.toLowerCase().startsWith("y")){
        		String url = String.format("http://www.openstreetmap.org/#map=5/%d/%d&layers=Q", lat, lng);
        		try{
        			BrowserLauncher launcher = new BrowserLauncher();
            		launcher.openURLinBrowser(url);
        		}catch(Exception e){
        			System.out.println("Não foi possível abrir o browser automaticamente");
        			System.out.println("URL do mapa: " + url);
        		}
        	}
    	}else{
    		System.out.println("Este país não tem localização informada");
    	}
    }
    
    private void printExit(){
    	System.out.println("");
    	System.out.println("\t!! Isso é tudo pessoal !!");
    	System.out.println("");
        System.out.println(" - Baseado no exemplo 'Consuming a RESTful Web Service'!");
        System.out.println(" - Disponível em 'http://spring.io/guides/gs/consuming-rest/'");
        System.out.println("");
        System.out.println("\tDesenvolvimento Web - Server Side");
        System.out.println(" - Dupla:");
        System.out.println("    * Alison Bento");
        System.out.println("    * Paulo Leonardo");
        System.out.println("");
        System.out.println("");
        
    }
    
    
}
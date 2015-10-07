package countries;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Country{
	private String name;
	private String capital;
	private String region;
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	private int population;
	
	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
	private List<Integer> latlng;
	
	public List<Integer> getLatlng() {
		return latlng;
	}

	public void setLatlng(List<Integer> latLng) {
		this.latlng = latLng;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

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
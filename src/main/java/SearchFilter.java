
public class SearchFilter implements PlantFilter {
	String query;
	public SearchFilter(String query) {
		this.query = parseString(query);
	}
	
	private String parseString(String str) {
		return str.toLowerCase().strip();
	}
	
	public boolean include(PlantSpecies plant) {
		String common = parseString(plant.getCommonName());
		String species = parseString(plant.getGenusName());
		String genus = parseString(plant.getSpeciesName());
		
		return common.contains(query) || species.contains(query) || genus.contains(query);
	}
}

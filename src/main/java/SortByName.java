import java.util.Comparator;

public class SortByName implements Comparator<PlantSpecies>{
	final String name = "Sort by common name";

	@Override
	public int compare(PlantSpecies o1, PlantSpecies o2) {
		return o1.getCommonName().compareToIgnoreCase(o2.getCommonName());
	}
	
	@Override
	public String toString() {
		return name;
	}
}

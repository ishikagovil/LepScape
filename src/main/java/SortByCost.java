import java.util.Comparator;

/**
 * Comparator for plants based on increasing cost
 * @author jinay
 *
 */
public class SortByCost implements Comparator<PlantSpecies>{
	final String name = "Sort by cost";

	@Override
	public int compare(PlantSpecies o1, PlantSpecies o2) {
		return o1.getCost() - o2.getCost();
	}
	
	@Override
	public String toString() {
		return name;
	}
}

import java.util.Comparator;

/**
 * Comparator on plants based on decreasing lep support
 * @author Jinay Jain
 *
 */
public class SortByLeps implements Comparator<PlantSpecies>{
	final String name = "Sort by insects supported";

	@Override
	public int compare(PlantSpecies o1, PlantSpecies o2) {
		return o2.getLepsSupported() - o1.getLepsSupported();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}

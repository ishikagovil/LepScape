
/**
 * An interface used to filter the plants by a criteria
 *
 */
@FunctionalInterface
public interface PlantFilter {
	public boolean include(PlantSpecies plant);
}

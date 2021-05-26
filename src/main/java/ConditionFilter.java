
/**
 * Filters plants by the conditions they're supported in
 * @author Jinay Jain
 *
 */
public class ConditionFilter implements PlantFilter {
	Conditions cond;
	
	public ConditionFilter(Conditions cond) {
		this.cond = cond;
	}
	
	public boolean include(PlantSpecies plant) {
		boolean matchMoist = plant.getMoistureType() == MoistureType.ANY 
				|| cond.getMoistureType() == MoistureType.ANY 
				|| cond.getMoistureType() == plant.getMoistureType();
		boolean matchDirt = plant.getSoilType() == SoilType.ANY 
				|| cond.getSoilType() == SoilType.ANY 
				|| cond.getSoilType() == plant.getSoilType();
		boolean matchSun = plant.getLightType() == LightType.ANY 
				|| cond.getSunlightType() == LightType.ANY 
				|| cond.getSunlightType() == plant.getLightType();
		
		return matchMoist && matchDirt && matchSun;
	}
}

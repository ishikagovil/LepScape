import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class CSVtoPlants {

    public static Map<String, PlantSpecies> readFile(String fileName) {
		Map<String, PlantSpecies> listPlants = new HashMap<>();

        String speciesName;
        String genusName;
        String commonName;
        String description;
        String tempWoody;
        int cost;
        boolean isWoody;
		
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(fileName));
		    String line; 
		    reader.readLine();
            while ((line = reader.readLine()) != null) {    
                String[] parts = line.split(","); // separate the line by comma
                genusName = parts[0];
                speciesName = parts[1];
                commonName = parts[2];
                description = parts[3];
                tempWoody = parts[7];
                if (tempWoody.equals("h")) {
                    cost = 6;
                    isWoody = false;
                } else {
                    cost = 20;
                    isWoody = true;
                }
                PlantSpecies newData = new PlantSpecies(speciesName, genusName, commonName, description, 0, 0, cost, 0, isWoody); //create object
                listPlants.put(commonName, newData);
            }            
            reader.close();   
		}
		catch (Exception e) {            
            System.err.format("Exception occurred trying to read '%s'.", fileName);            
            e.printStackTrace();        
        }
		return listPlants;
	}
    
    // debugging; guaranteeing working
    /*
    public static void main(String[] args) {
        Map<String, PlantSpecies> plantDir = readFile("testdata.csv");

        for (String p : plantDir.keySet()) {
            String key = p;
            String plant = plantDir.get(p).toString();
            System.out.println(key + " " + plant);
        }
    }
    */

}

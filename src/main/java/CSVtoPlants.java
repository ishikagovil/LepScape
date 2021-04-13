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
        String imgUrl;
		
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
                imgUrl = parts[4];
                tempWoody = parts[7];
                if (tempWoody.equals("h")) {
                    cost = 6;
                    isWoody = false;
                } else {
                    cost = 20;
                    isWoody = true;
                }
                PlantSpecies newData = new PlantSpecies(speciesName, genusName, commonName, description, 5, 5, cost, 0, isWoody); //create object
                listPlants.put(genusName + " " + speciesName, newData);     // key by genus + species
            }            
            reader.close();   
		}
		catch (Exception e) {            
            System.err.format("Exception occurred trying to read '%s'.", fileName);            
            e.printStackTrace();        
        }
		return listPlants;
	}

    public static Map<String, String> readFileForImg(String fileName) {
		Map<String, String> plantImg = new HashMap<>();
        
        String genusName, speciesName, commonName;
        String imgUrl;
		
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(fileName));
		    String line; 
		    reader.readLine();
            while ((line = reader.readLine()) != null) {    
                String[] parts = line.split(","); // separate the line by comma
                genusName = parts[0];
                speciesName = parts[1];
                commonName = parts[2];
                imgUrl = parts[4];
                plantImg.put(genusName + " " + speciesName, imgUrl);                // key by genus + species
            }            
            reader.close();   
		}
		catch (Exception e) {            
            System.err.format("Exception occurred trying to read '%s'.", fileName);            
            e.printStackTrace();        
        }
		return plantImg;
	}
    
    // debugging; guaranteeing working
    
    public static void main(String[] args) {
        Map<String, String> plantImg = readFileForImg("../resources/testdata.csv");

        /*for (String p : plantDir.keySet()) {
            String key = p;
            String plant = plantDir.get(p).toString();
            System.out.println(key + " " + plant);
        }*/
        for (String p : plantImg.keySet()) {
            String key = p;
            String img = plantImg.get(p);
            System.out.println(key + " " + img);
        }
    }
}

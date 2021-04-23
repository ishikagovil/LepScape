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
        String tempWoody;
        int cost;
        boolean isWoody;
        int spread;
        int leps;
        int lightReq;
        int soilReq;
        int moistReq;
		
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(fileName));
		    String line; 
		    reader.readLine();
            while ((line = reader.readLine()) != null) {    
                String[] parts = line.split(","); // separate the line by comma
                genusName = parts[0];
                speciesName = parts[1];
                commonName = parts[2];
                spread = Integer.parseInt(parts[3]);
                leps = Integer.parseInt(parts[4]);
                tempWoody = parts[5];
                if (tempWoody.equals("h")) {
                    cost = 6;
                    isWoody = false;
                } else {
                    cost = 20;
                    isWoody = true;
                }
                lightReq = Integer.parseInt(parts[6]);
                soilReq = Integer.parseInt(parts[7]);
                moistReq = Integer.parseInt(parts[8]);

                PlantSpecies newData = new PlantSpecies(speciesName, genusName, commonName, spread, leps, cost, isWoody, lightReq, soilReq, moistReq); //create object
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

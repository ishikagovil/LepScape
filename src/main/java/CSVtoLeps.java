import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CSVtoLeps {

    public static Map<String, Lep> readFile(String fileName) {
		Map<String, Lep> listLeps = new HashMap<>();

        String genusName;
        String speciesName;
        String commonName;
        String description;
        ArrayList<PlantSpecies> thrivesIn;
		
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
                // for loop to generate ArrayList for plants thrive in
                Lep newData = new Lep(speciesName, genusName, commonName); //create object
                newData.setDescription(description);
                // use newData.setThrivesIn to put initialize ArrayList
                listLeps.put(genusName + " " + speciesName, newData);
            }            
            reader.close();   
		}
		catch (Exception e) {            
            System.err.format("Exception occurred trying to read '%s'.", fileName);            
            e.printStackTrace();        
        }
		return listLeps;
	}
    
    // debugging; guaranteeing working
    
    public static void main(String[] args) {
        Map<String, Lep> lepDir = readFile("../resources/testleps.csv");

        for (String p : lepDir.keySet()) {
            String key = p;
            String lep = lepDir.get(p).toString();
            System.out.println(key + " " + lep);
        }
    }
    

}

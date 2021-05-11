import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Use for converting CSV file of plant data into useable data for the program.
 * @author dharjianto
 *
 */
public class CSVtoPlants {

	/**
	 * Reads in the file to make PlantSpecies objects of every plant species available.
	 * @param fileName
	 * @return Map<String, PlantSpecies>
	 */
	public static Map<String, PlantSpecies> readFile(String fileName) {
		Map<String, PlantSpecies> listPlants = new HashMap<>();

        String speciesName;
        String genusName;
        String commonName;
        String tempWoody;
        int cost;
        boolean isWoody;
        double spread;
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
                spread = Double.parseDouble(parts[3]);
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
                listPlants.put(genusName + "-" + speciesName, newData);     // key by genus-species
            }            
            reader.close();   
		}
		catch (Exception e) {            
            System.err.format("Exception occurred trying to read '%s'.", fileName);            
            e.printStackTrace();        
        }
		return listPlants;
	}

    /**
     * Reads in the file to get the key for images of each PlantSpecies.
     * @param fileName
     * @return Map<String, ImageView>
     */
	public static Map<String, ImageView> readFileForImg(String fileName) {
		Map<String, ImageView> plantImg = new HashMap<>();
        
        String genusName, speciesName;
        String imgSrc, filePath;
        
        System.out.println("beginning images");
		
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(fileName));
		    String line; 
		    reader.readLine();
            while ((line = reader.readLine()) != null) {    
                String[] parts = line.split(","); // separate the line by comma
                genusName = parts[0];
                speciesName = parts[1];
                imgSrc = genusName + "-" + speciesName + ".png";		// file name ex: Justicia-americana.png
                filePath = "plantimg/" + imgSrc;
                //System.out.println(filePath);
                Image img = new Image(filePath);    // file path to get image names; change if different file path
                //System.out.println("fetching images");
                ImageView imv = new ImageView();
                imv.setImage(img);
                imv.setFitWidth(100);                       // resizing to be 100 x 100px; can change value
                imv.setPreserveRatio(true);
                imv.setSmooth(true);
                imv.setCache(true);
                plantImg.put(genusName + "-" + speciesName, imv);   // key: genusName-speciesName
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
        Map<String, ImageView> plantImg = readFileForImg("../resources/finalPlantListWithInfo.csv");

        /*for (String p : plantDir.keySet()) {
            String key = p;
            String plant = plantDir.get(p).toString();
            System.out.println(key + " " + plant);
        }*/
        for (String p : plantImg.keySet()) {
            String key = p;
            ImageView img = plantImg.get(p);
            System.out.println(key + " " + img);
        }
    }
}

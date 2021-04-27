import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CSVtoLeps {

    public static Map<String, Lep> readFile(String fileName) {
		Map<String, Lep> listLeps = new HashMap<>();

        String genusName;
        String speciesName;
        String commonName;
        String thrives;
		
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(fileName));
		    String line; 
		    reader.readLine();
            while ((line = reader.readLine()) != null) {    
                String[] parts = line.split(","); // separate the line by comma
                genusName = parts[0];
                speciesName = parts[1];
                commonName = parts[2];
                thrives = parts[3];
                // for loop to generate ArrayList for plants thrive in
                String key = genusName + "-" + speciesName;
                if (listLeps.get(key) == null) {
                    Lep newData = new Lep(speciesName, genusName, commonName); //create object
                    listLeps.put(genusName + "-" + speciesName, newData); // use newData.setThrivesIn to put initialize ArrayList
                }
                listLeps.get(key).getThrivesInGenus().add(thrives);
            }            
            reader.close();   
		}
		catch (Exception e) {            
            //System.err.format("Exception occurred trying to read '%s'.", fileName);            
            //e.printStackTrace();        
        }
		return listLeps;
	}
    
    public static Map<String, ImageView> readFileForImg(String fileName) {
		Map<String, ImageView> lepImg = new HashMap<>();
        
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
                imgSrc = genusName + "-" + speciesName + ".jpg";		// file name ex: Justicia-americana.png
                filePath = "lepimg/" + imgSrc;
               // System.out.println(filePath);
                Image img = new Image(filePath);    // file path to get image names; change if different file path
               // System.out.println("fetching images");
                ImageView imv = new ImageView();
                imv.setImage(img);
                imv.setFitWidth(100);                       // resizing to be 100 x 100px; can change value
                imv.setPreserveRatio(true);
                imv.setSmooth(true);
                imv.setCache(true);
                lepImg.put(genusName + "-" + speciesName, imv);   // key: genusName "-" speciesName
            }            
            reader.close();   
		}
		catch (Exception e) {            
            //System.err.format("Exception occurred trying to read '%s'.", fileName);            
            //e.printStackTrace();        
        }
		return lepImg;
	}
    
    // debugging; guaranteeing working
    public static void main(String[] args) {
        Map<String, Lep> lepDir = readFile("../resources/finalLepList.csv");

        for (String p : lepDir.keySet()) {
            String key = p;
            ArrayList<String> lep = lepDir.get(key).getThrivesInGenus();
            System.out.println(key + " | " + lep);
        }
    }
    

}

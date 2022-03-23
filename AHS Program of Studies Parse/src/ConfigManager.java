import java.nio.file.Paths;
import java.util.*;
import java.io.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class ConfigManager {
    String configPath = "src/config.json";
    Config config;

    public ConfigManager(){
    }

    public ConfigManager(String configPath){
        this.configPath = configPath;
    }

    public Config loadConfig(){

        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON file to map
            config = mapper.readValue(Paths.get(configPath).toFile(), Config.class);

            if(config.getDebug()) {
                System.out.println("Loaded Config...");
            }
            return config;
            //System.out.println(config.toString());

        } catch (Exception ex) {
            System.out.println("Error loading config");
            ex.printStackTrace();
            return null;
        }


    }

    public void writeConfig(){
        Config config = new Config();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            // creates the json
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        } catch (JsonProcessingException e) { e.printStackTrace(); }

        try {
            FileWriter writer = new FileWriter(configPath);
            writer.write(jsonString);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Config getConfig() {return config;}
}

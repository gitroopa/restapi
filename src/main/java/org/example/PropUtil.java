package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {
    public String getProperty(String key) {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("./src/test/resources/config/systemtest_env_config.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop.getProperty(key);

    }

    public File loadFile(String filePath) {
        File file_from_givenLocation = null;
        try{
             file_from_givenLocation = new File(filePath);
            if(!file_from_givenLocation.exists()){
                System.out.println("File does not exist in this location: "+filePath);
            }
        }catch(Exception exp){
            System.out.println("Exception in loading the file! "+exp);
        }
        return file_from_givenLocation;
    }

    public String loadFileContentes(String filePath) throws IOException {
        System.out.println("File path received: "+filePath);
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        String data = FileUtils.readFileToString(file, "UTF-8");
        return data;
    }

}

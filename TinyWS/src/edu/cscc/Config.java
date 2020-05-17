package edu.cscc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Config.java – reads configuration information for the web server at start-up
 * Process web server configuration
 * CSCI-2469 Java II Group Project
 * design and implement a tiny web server
 * date 20200406
 * @author Kelly Waddell
 * @author Mutasem Alhariri 
 * @author Robert Doult
 * @since 20200406
 * @version 1.5  
 */ 

public class Config {
    public static final String PORT = "port";
    public static final String DEFAULTPAGE = "defaultPage";
    public static final String DEFAULTFOLDER = "defaultFolder";

    private static final String CONFIG_FILE = "./TinyWS.xml";
    private static Properties properties;

    /**
     * Constructor
     */
    public Config() {
        try {
            readProperties();
        } catch (FileNotFoundException e) {
            TinyWS.fatalError("Cannot open file: "+CONFIG_FILE);
        } catch (IOException e) {
            TinyWS.fatalError(e.getMessage());
        }
    }

    /**
     * Initialize properties
     * @throws IOException - thrown if cannot read configuration file
     */
    public void readProperties() throws IOException {
    	//FileInputStream is meant for reading streams of raw bytes such as image data.
    	//The File object has to point to the file you want to read this is File(CONFIG_FILE)
    	FileInputStream fin = new FileInputStream(new File(CONFIG_FILE));
    	//here we creates an empty property list with no default values
    	//the initial capacity of a Properties object created with this constructor is unspecified.
    	properties = new Properties();
    	//loads all of the properties represented by the XML document on the specified input 
    	//stream into this properties table
    	//needed to use IOException because if reading from the specified input stream results in an IOException.
    	properties.loadFromXML(fin);    	    	
    	//close the stream and release the resources that were busy in the stream
    	fin.close();
    }

    /**
     * Get a property value
     * @param key - key associated with property
     * @return property string
     */
    //TODO*******************
    //Searches for the property with the specified key in this property list.
    //If the key is not found in this property list,
    //the default property list,and its defaults, recursively, are then checked
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Debug - list all current properties
     */
    //here Enumeration to used loop through Properties
    // return an enumeration of all the key
    public void dumpProperties() {
        Enumeration<Object> enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            TinyWS.log(key + ": " + value);
        }
    }
}

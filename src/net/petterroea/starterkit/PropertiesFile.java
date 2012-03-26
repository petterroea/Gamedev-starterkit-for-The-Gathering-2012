package net.petterroea.starterkit;

import java.io.*;
import java.util.*;

/**
 * Old codefile that i have used since hMod(Inspired by one of the code files in hMod). Works good^^
 * 
 * @author Petterroea
 */

public class PropertiesFile {
	/**
	 * The complete file name
	 */
	private String product;
	/**
	 * The file we are wrapping
	 */
    private Properties propertiesFile = new Properties();
    /**
     * The file extension to be used
     */
    private String fileExtension = ".properties";
    /**
     * Creates or loads a new PropertiesFile based on the given name, without a file extension
     * @param name The file name
     */
    public PropertiesFile(String name)
	{
		this.product = name + fileExtension;
		File theFile = new File(product);
		try {
			
			if(theFile.exists())
			{
				reload();
			}
			else
			{
				save();
			}
		} catch (IOException ex)
		{
			System.out.println("Failed to load settings file: " + product);
			System.out.println("The Exeption is: " + ex);
		}
	}
    /**
     * Creates or loads a PropertiesFile based on the given file
     * @param theFile File to load from
     */
    public PropertiesFile(File theFile)
	{
		try {
			
			if(theFile.exists())
			{
				reload();
			}
			else
			{
				save();
			}
		} catch (IOException ex)
		{
			System.out.println("Failed to load settings file: " + product);
			System.out.println("The Exeption is: " + ex);
		}
	}
    /**
     * Reloads the file
     * @throws IOException
     */
	void reload() throws IOException
	{
		FileInputStream FileInStream = null;
		try{
			FileInStream = new FileInputStream(product);
			propertiesFile.load(FileInStream);
		} catch (IOException ex) {
			System.out.println("Failed to load settings file: " + product);
			System.out.println("The Exeption is: " + ex);
		} finally {
			try {
				if(FileInStream != null) {
					FileInStream.close(); 
					} 
				}catch (IOException ex) {
					System.out.println("Failed to load settings file: " + product);
					System.out.println("The Exeption is: " + ex);
				}
			}
		}
	/**
	 * Saves the properties file
	 */
		void save() {
			FileOutputStream FileOutStream = null;
			try{
				FileOutStream = new FileOutputStream(product);
				propertiesFile.store(FileOutStream, null);
			} catch (IOException ex) {
				System.out.println("Failed to load settings file: " + product);
				System.out.println("The Exeption is: " + ex);
			} finally {
				try {
					if(FileOutStream != null) {
						FileOutStream.close(); 
						} 
					}catch (IOException ex) {
						System.out.println("Failed to load settings file: " + product);
						System.out.println("The Exeption is: " + ex);
					}
				}
			}
		/**
		 * Checks if a property/key called name exists
		 * @param name The name to check
		 * @return True if it exists
		 */
		public boolean containsKey(String name) 
		{
	        return propertiesFile.containsKey(name);
	    }
		/**
		 * Removes the key/property from the file
		 * @param name The name to remove
		 */
		public void removeKey(String name) 
		{
	        if (propertiesFile.containsKey(name)) 
	        {
	            propertiesFile.remove(name);
	            save();
	        }
	    }
		/**
		 * Gets a string with the property behind name
		 * @param name Name of the property to get
		 * @return A string containing the property
		 */
		public String getProperty(String name) 
		{
	        return propertiesFile.getProperty(name);
	    }
		/**
		 * Gets a string with the property behind name
		 * @param name Name of the property to get
		 * @return A string containing the property
		 */
		public String getString(String name) 
		{
			if(containsKey(name))
			{
				return getProperty(name);
			}
			return "";
			
		}
		/**
		 * Sets a string property. Autosaves
		 * @param name Name of property
		 * @param value Value of property
		 */
		public void setString(String name, String value)
		{
			propertiesFile.put(name, value);
			save();
		}
		/**
		 * Gets a integer with the property behind name. You can get an error if the property is not int-formatted
		 * @param name Name of the property to get
		 * @return A int containing the property
		 */
		public int getInt(String name)
		{
			if(containsKey(name))
			{
				return Integer.parseInt(getProperty(name));
			}
			return 0;
		}
		/**
		 * Sets a integer property. Autosaves
		 * @param name The name of the property
		 * @param value Value to be set
		 */
		public void setInt(String name, int value)
		{
			propertiesFile.put(name, String.valueOf(value));
			
			save();
		}
		/**
		 * Gets a boolean with the property behind name. You can get an error if the property is not true or false.
		 * @param name Name of the property to get
		 * @return A boolean containing the property
		 */
		public boolean getBool(String name)
		{
			if(containsKey(name))
			{
				return Boolean.parseBoolean(getProperty(name));
			}
			return false;
		}
		/**
		 * Sets a boolean property. Autosaves
		 * @param name Name of the property
		 * @param value Value of the property
		 */
		public void setBool(String name, boolean value)
		{
			propertiesFile.put(name, String.valueOf(value));
			save();
		}
	}
	
package com.mjh.xmlscraper;

/**
 * An enum which contains all of the credentials needed to make a connection to
 * the database. It is expected that the DATABASE property will stay consistent 
 * throughout, unless testing is being conducted. 
 * 
 * @author MaxHarrisMJH@gmail.com
 */
public enum DatabaseCredentials {
	/**
	 * DRIVER constant which contains the database's driver details.
	 */
    DRIVER("jdbc:mysql"),
    
    /**
	 * HOST constant which contains the database's host details.
	 */
    HOST("192.168.0.69"),
    
    /**
	 * PORT constant which contains the database's port details.
	 */
    PORT("3306"),
    
    /**
	 * DATABASE constant which contains the database's database details.
	 */
    DATABASE("smartpark_v2"), // EITHER test_smartpark OR smartpark_v2
    
    /**
	 * USERNAME constant which contains the database's username details.
	 */
    USERNAME("test"),
    
    /**
	 * PASSWORD constant which contains the database's password details.
	 */
    PASSWORD("test");
    
	/**
	 * Property which allows each constant to have a String value.
	 */
    private final String credential;
    
    /**
     * Assigns a String value to each constant within the enum.
     * 
     * @param credential The specific credential needed to gain access to the database.
     */
    private DatabaseCredentials(String credential) {
        this.credential = credential;
    }
    
    /**
     * Returns a specific enum constant's value.
     * 
     * @return A specific enum constant's value.
     */
    public String getCredential() {
        return this.credential;
    }
}
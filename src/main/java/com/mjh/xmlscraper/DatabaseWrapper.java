package com.mjh.xmlscraper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class connects to the database and also allows database manipulation.
 * 
 * This class allows the data taken from the XML URL to be placed into the
 * database. If a connection is successfully established, data can then be 
 * added to the database.
 * 
 * @author MaxHarrisMJH@gmail.com
 */
public class DatabaseWrapper {
	/*---- Properties ----*/
	/**
	 * Property which stores the database URL of the database.
	 */
	private String databaseURL;
	
	/**
	 * Property which stores the username of the database.
	 */
	private String username;
	
	/**
	 * Property which stores the password of the database.
	 */
	private String password;
	
	/*---- Constructor ----*/
	/**
	 * Constructor that initialises all of the class' properties. The credentials
	 * are set by the DatabaseCredentials enum and its values.
	 */
	public DatabaseWrapper() {
		this.databaseURL = DatabaseCredentials.DRIVER.getCredential() + "://" + DatabaseCredentials.HOST.getCredential() + ":" + DatabaseCredentials.PORT.getCredential() + "/" + DatabaseCredentials.DATABASE.getCredential();
		this.username = DatabaseCredentials.USERNAME.getCredential();
		this.password = DatabaseCredentials.PASSWORD.getCredential();
	}
	
	/*---- Methods ----*/
	/**
	 * Check to see if a connection can be made to the database. This is used mainly for testing.
	 * 
	 * @return true if a connection can be made to the database, or false otherwise.
	 */
	public boolean canConnect() {
		// Check to see if a connection can be made to the database.
		try(Connection connection = DriverManager.getConnection(this.databaseURL, this.username, this.password)) {
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Allows a query to be executed to manipulate or grab data from the database. In order for the
	 * method to work, a query is required so that a prepared statement can be made and then
	 * filled with values. The values must be in a form of Integer => Object. Each key represents 
	 * the place in the query where a piece of data should be entered. For example, a value used after
	 * a WHERE clause.
	 * 
	 * @param query String representation of a predefined query.
	 * @param indexValues A mapping of values so that they can be injected into the query where needed.
	 * @return The result of the query. If the query was successful and has data, the Boolean value will be true, alongside an array of the returned values. If it fails, the boolean will be false and the values false too.
	 */
	public Map<Boolean, ArrayList<ArrayList<Object>>> executeQuery(String query, Map<Integer, Object> indexValues) {
		// A mapping of the returned results from the prepared statement.
		Map<Boolean, ArrayList<ArrayList<Object>>> results = new HashMap<>();
		ArrayList<Object> rows = new ArrayList<>();
		
		// Try and establish a connection to the database.
		try(Connection connection = DriverManager.getConnection(this.databaseURL, this.username, this.username);
			PreparedStatement preparedStatement = connection.prepareStatement(query)) 
		{	
			// Prepare entered query.
			for(int i = 0; i < indexValues.size(); i++) {
				preparedStatement.setObject(i + 1, indexValues.get(i));
			}
			
			// Check to see if query will return anything (SELECT).
			if(preparedStatement.execute()) {
				ResultSet resultSet = preparedStatement.getResultSet();
				
				// If the SELECT query returns nothing.
				if(!resultSet.next()) {
					results.put(false, new ArrayList<ArrayList<Object>>());
					rows.add(false);	
					results.get(false).add(rows);
				} else {
					results.put(true, new ArrayList<ArrayList<Object>>());
					
					do {
						// If there are multiple rows returned, rows has to be re-instantiated.
						rows = new ArrayList<>();
						for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
							rows.add(resultSet.getObject(i + 1));
						}
						results.get(true).add(rows);
					} while(resultSet.next());
				}
			} else {
				// If Query is INSERT, UPDATE, and DELETE, see if any rows where added / changed.
				if(preparedStatement.getUpdateCount() != -1 && preparedStatement.getUpdateCount() != 0) {
					results.put(true, new ArrayList<ArrayList<Object>>());
					rows.add(preparedStatement.getUpdateCount());
					results.get(true).add(rows);
				} else {
					results.put(false, new ArrayList<ArrayList<Object>>());
					rows.add(false);
					results.get(false).add(rows);
				}
			}
		} catch(SQLException e) {
			// If any errors occur (with database connection or prepared statement) set the boolean to false to identify such issue.
			e.printStackTrace();
			results.put(false, new ArrayList<ArrayList<Object>>());
			rows.add(false);
			results.get(false).add(rows);
		}
		return results;
	}
}

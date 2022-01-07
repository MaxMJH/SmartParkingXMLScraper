package com.mjh.xmlscraper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseWrapper {
	/*---- Fields ----*/
	private String databaseURL;
	private String username;
	private String password;
	
	/*---- Constructor ----*/
	public DatabaseWrapper(String databaseURL, String username, String password) {
		this.databaseURL = databaseURL;
		this.username = username;
		this.password = password;
	}
	
	/*---- Methods ----*/
	public boolean canConnect() {
		try(Connection connection = DriverManager.getConnection(this.databaseURL, this.username, this.password)) {
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Map<Boolean, ArrayList<ArrayList<Object>>> executeQuery(String query, Map<Integer, Object> indexValues) {
		Map<Boolean, ArrayList<ArrayList<Object>>> results = new HashMap<>();
		ArrayList<Object> rows = new ArrayList<>();
		
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
				if(preparedStatement.getUpdateCount() != -1) {
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
			e.printStackTrace();
		}
		return results;
	}
}

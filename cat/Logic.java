package com.cat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class Logic {

    private String userName;
    private String email;
    private String password;
    private String lastName;
    private boolean result = false;
    private boolean emailResult = false;
    private boolean blockRegistration = false;
    private Timestamp created;
    private Timestamp accessed;
    private Timestamp createdRetrieved;
    private Timestamp accessedRetrieved;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    // Logic constructor for creating an instance during user authentication
    public Logic(String password, String email, Timestamp accessed) {
        this.email = email;
        this.password = password;
        this.accessed = accessed;
        loginUser();
    }

    // Logic constructor for creating an instance during user registration
    public Logic(String userName, String password, String email, String lastName, Timestamp created) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.created = created;
        registerUser();
    }

    private void registerUser() {
        Database database = new Database();
        emailResult = checkDuplicates(database);

        if (!emailResult) {
            // Register the user only if email is not a duplicate
            database.registerUser(userName, email, password, lastName, created);
        }
    }

    
    
    // Getter methods to retrieve details
    public boolean getResult() {
        return result;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserLastName() {
        return this.lastName;
    }
    
    public boolean getEmailResult() {
    	return blockRegistration;
    }
    
    public Date accessedRetrieved() {
    	return accessedRetrieved;
    }
    
    public Date createdRetrieved() {
    	return createdRetrieved;
    }
    
    
    // setting dates 
    public Date getAccessedRetrieved() {
        return accessedRetrieved != null ? new Date(accessedRetrieved.getTime()) : null;
    }

    public Date getCreatedRetrieved() {
        return createdRetrieved != null ? new Date(createdRetrieved.getTime()) : null;
    }

    // If the user is logging in, this method gives a value if the user is in the database or not
    private void loginUser() {
        Database database = new Database();
        result = database.authenticateUser(email, password, accessed);
        if (result) {
            // If authentication is successful, retrieve additional data from the database
            retrieveUserData(database);
        }
    }

    // Retrieve additional user data after successful login
    private void retrieveUserData(Database database) {
        try (Connection connection = database.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";
            try {
            	preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Retrieve and use data from the result set
                    userName = resultSet.getString("name");
                    lastName = resultSet.getString("lastname");
                    createdRetrieved = resultSet.getTimestamp("timeCreated");
                    accessedRetrieved = resultSet.getTimestamp("timeAccessed");
                    // You can store or use the retrieved data as needed
                }
            } finally {
            	try {
            		if (connection != null) {
            			connection.close();  
            		}
            		
            		if (preparedStatement != null) {
                		preparedStatement.close();
                	}
            		
            		if (resultSet != null) {
            			resultSet.close();
            		}
            	} catch (SQLException e) {
            		e.printStackTrace();
            	}
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Additional methods or modifications as needed
    
    private boolean checkDuplicates(Database database) {
    	
    	try (Connection connection = database.getConnection()) {
    		String query = "SELECT * FROM users WHERE email = ?";
        	
        	try {
        		
        		preparedStatement = connection.prepareStatement(query);
        		preparedStatement.setString(1, email);
        		
        		resultSet = preparedStatement.executeQuery();
        		
        		return resultSet.next();
        	} finally {
            	try {
            		if (connection != null) {
            			connection.close();  
            		}
            		
            		if (preparedStatement != null) {
                		preparedStatement.close();
                	}
            		
            		if (resultSet != null) {
            			resultSet.close();
            		}
            	} catch (SQLException e) {
            		e.printStackTrace();
            	}
            }
    	} catch (SQLException e) {
    		throw new RuntimeException(e);
    	}
    	
    }

	public void setCreated(Timestamp created2) {
		this.created = created2;
		
	}

	public void setAccessed(Timestamp accessed2) {
		this.accessed = accessed2;
		
	}
}

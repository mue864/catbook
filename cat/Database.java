package com.cat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class Database {

    private Connection connection;
    private Timestamp dateAccessed;
    private PreparedStatement preparedStatement;
    // Constructor for creating a Database instance
    public Database() {
        try {
            // Establish a connection to the MySQL database
        	this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/catbook?connectTimeout=20000000", "root", "root");

        } catch (SQLException e) {
            // If there's an exception during connection, throw a RuntimeException with the original exception as its cause
            throw new RuntimeException(e);
        }
    }

    // Returns a database connection instance
    public Connection getConnection() {
        return connection;
    }
    
    public void setDates(Timestamp accessed) {
    	this.dateAccessed = accessed;
    }

 /// Method for registering a user in the database
    public void registerUser(String name, String email, String password, String lastname, Timestamp dateCreated) {
        String query = "INSERT INTO users (name, email, password, lastname, timeCreated) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/catbook", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set values for the parameters in the prepared statement
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, lastname);
            preparedStatement.setTimestamp(5, dateCreated);

            // Execute the update (insert) operation
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            // If there's an exception during database interaction, throw a RuntimeException with the original exception as its cause
            throw new RuntimeException(e);
        }
    }

    

    // Method for authenticating a user based on email and password
    public boolean authenticateUser(String email, String password, Timestamp accessed) {
        try {
            // SQL query for selecting a user with the given email and password
            String query = "SELECT * from users WHERE email = ? AND password = ? AND timeAccessed = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Set values for the parameters in the prepared statement
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                preparedStatement.setTimestamp(3, accessed);  // Set the third parameter
                // Execute the query and retrieve the result set
                ResultSet resultSet = preparedStatement.executeQuery();

                // Return true if there is at least one row in the result set (authentication successful)
                return resultSet.next();
            }
        } catch (SQLException e) {
            // If there's an exception during database interaction, throw a RuntimeException with the original exception as its cause
            throw new RuntimeException(e);
        }
    }


    
    
}

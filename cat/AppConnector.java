package com.cat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

public class AppConnector extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Retrieve parameters from the HTTP request
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String lastName = req.getParameter("userLastName");

        HttpSession session = req.getSession(true);
        Timestamp accessed = new Timestamp(session.getLastAccessedTime());

        // Declare a Logic object
        Logic logic;

        // Check if the user is trying to log in or register
        if (userName == null && lastName == null) {
            // If userName and lastName are null, it's a login attempt
            // Create a Logic object for login
            logic = new Logic(password, email, accessed);

            // Check if the login is successful
            if (logic.getResult()) {
                logic.setAccessed(accessed);
                // Set an attribute in the session indicating the logged-in user
                req.getSession().setAttribute("loggedInUser", logic.getUserName());
                // Redirect to the loggedIn.jsp page upon successful login
                res.sendRedirect("loggedIn.jsp");

            } else {
                // If login fails, display an error message
                res.getWriter().println("Login failed. Please check your credentials.");
                res.sendRedirect("index.html");

            }

        } else {
            // If userName or lastName is provided, it's a registration attempt
            // Create a Logic object for registration
            Timestamp created = new Timestamp(session.getCreationTime());
            logic = new Logic(userName, password, email, lastName, created);

            logic.setCreated(created);
            // Set an attribute in the session indicating the logged-in user
            req.getSession().setAttribute("loggedInUser", logic.getUserName());
            // Redirect to the loggedIn.jsp page after successful registration
            res.sendRedirect("loggedIn.jsp");

        }
    }
}

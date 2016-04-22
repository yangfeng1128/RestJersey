package edu.gatech.project3for6310.services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.dao.UserDAO;
import edu.gatech.project3for6310.dao.UserDAOImpl;
import edu.gatech.project3for6310.entity.User;

@Path("/user")
public class UserService {
	
	private static UserDAO userDAO=new UserDAOImpl();
	
	@Path("/authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response VerifyUser(User user) {
		String username = user.getUsername();
		String password = user.getPassword();

		Document userDoc = userDAO.getOneUser(username);		

        // User does not exist.
        if (userDoc != null) {
            String storedPassword = userDoc.getString("password");

            if (password != null && password.equals(storedPassword)) {
                userDoc.remove("password");
                return Response.status(200).entity(userDoc.toJson()).build();
            }
        }

		return Response.status(401).build();	
	}
}

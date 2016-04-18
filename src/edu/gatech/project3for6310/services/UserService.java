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
import edu.gatech.project3for6310.entity.User;

@Path("/user")
public class UserService {
	@Inject
	private static UserDAO userDAO;
	
	@Path("/authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response VerifyUser(User user)
	{
		String username=user.getUsername();
		String password=user.getPassword();
		Document userDoc = userDAO.getOneUser(username);		
		String storedPassword = null;
		if (user !=null)
		{
		 storedPassword = userDoc.getString("password");
		}
		boolean isVerified = false;
		if (password!=null && password.equals(storedPassword))
		{
			userDoc.put("password", "N.A");
			isVerified=true;
		}
		if (isVerified ==true)
		{
		return Response.status(200).entity(userDoc.toJson()).header("verified",isVerified).build();
		} else {
		return Response.status(401).header("verified",isVerified).build();	
		}
	}

	
}

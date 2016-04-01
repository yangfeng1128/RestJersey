package edu.gatech.project3for6310.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.dao.UserDAO;

@Path("/user")
public class UserService {
	@Inject
	private static UserDAO userDAO;
	
	@Path("/{username}/{password}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response VerifyUser(@PathParam("username") String username, @PathParam("password") String password)
	{
		Document user = userDAO.getOneUser(username);
		String storedPassword = null;
		if (user !=null)
		{
		 storedPassword = user.getString("password");
		}
		boolean isVerified = false;
		if (password!=null && password.equals(storedPassword))
		{
			isVerified=true;
		}
		if (isVerified ==true)
		{
		return Response.status(200).entity(user.toJson()).header("verified",isVerified).build();
		} else {
		return Response.status(401).header("verified",isVerified).build();	
		}
	}

	
}

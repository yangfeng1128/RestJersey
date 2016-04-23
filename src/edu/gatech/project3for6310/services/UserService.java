package edu.gatech.project3for6310.services;

import edu.gatech.project3for6310.dao.UserDAO;
import edu.gatech.project3for6310.dao.UserDAOImpl;
import edu.gatech.project3for6310.entity.User;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
public class UserService {

    private static UserDAO userDAO = new UserDAOImpl();

    @Path("/authenticate")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
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

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<Document> users = userDAO.getAllUsers();
        JSONArray sb = new JSONArray();

        for (Document d : users) {
            d.remove("password");
            sb.put(d);
        }

        return Response.status(200).entity(sb.toString()).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneUser(@PathParam("id") String id) {
        Document user = userDAO.getOneUserById(id);

        if (user == null) {
            JSONObject sb = new JSONObject();
            sb.append("Exception:", "not found.");
            return Response.status(404).entity(sb.toString()).build();
        }

        user.remove("password");
        return Response.status(200).entity(user.toJson()).build();
    }
}

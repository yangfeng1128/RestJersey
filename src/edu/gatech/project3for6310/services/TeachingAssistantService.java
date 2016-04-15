package edu.gatech.project3for6310.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.json.JSONObject;

import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.dao.TeachingAssistantDAO;
import edu.gatech.project3for6310.entity.TeachingAssistant;

@Path("/teachingassistants")
public class TeachingAssistantService {
 
	@Inject
	private static TeachingAssistantDAO teachingAssistantDAO;
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTeachingAssistants(){
		
		List<Document> teachingAssistants =teachingAssistantDAO.getAllTeachingAssistants();
		JSONObject sb = new JSONObject();
		for(Document d:teachingAssistants)
		{
			sb.append(d.getString("id"),d.toJson());
		}
		return Response.status(200).entity(sb.toString()).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeachingAssistant(@PathParam("id") String id){
		
		Document teachingAssistant =teachingAssistantDAO.getOneTeachingAssistant(id);
		if(teachingAssistant ==null)
		{
			return Response.status(400).build();
		}
		return Response.status(200).entity(teachingAssistant.toJson()).build();
	}
	
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTeachingAssistant(@PathParam("id") String id, TeachingAssistant teachingAssistant){
		boolean success=teachingAssistantDAO.updateTeachingAssistant(id, teachingAssistant);
	    String res = null;
	    if (success != true)
	    {
	    	res="updated successfully";
	    	return Response.status(200).entity(res).header("isUpdated",success).build();
	    } else {
	    	res="not updated";
	    	return Response.status(400).header("isUpdated", success).build();
	    }
		
	}
	
	@Path("/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTeachingAssistant(@PathParam("id") String id, TeachingAssistant teachingAssistant){
		boolean success=teachingAssistantDAO.createTeachingAssistant(id, teachingAssistant);
	    String res = null;
	    if (success != true)
	    {
	    	res="created successfully";
	    	return Response.status(200).entity(res).header("isCreated",success).build();
	    } else {
	    	res="not created";
	    	return Response.status(400).header("isCreated", success).build();
	    }
		
	}
	
	@Path("/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTeachingAssistant(@PathParam("id") String id){
		boolean success=teachingAssistantDAO.deleteTeachingAssistant(id);
	    String res = null;
	    if (success != true)
	    {
	    	res="deleted successfully";
	    	return Response.status(200).entity(res).header("isDeleted",success).build();
	    } else {
	    	res="not deleted";
	    	return Response.status(400).header("isDeleted", success).build();
	    }
		
	}
}

package edu.gatech.project3for6310.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.json.JSONObject;

import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.dao.ProfessorDAO;
import edu.gatech.project3for6310.entity.Professor;

@Path("/professors")
public class ProfessorService {

	@Inject
	private static ProfessorDAO professorDAO;
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProfessors(){
		
		List<Document> professors =professorDAO.getAllProfessors();
		JSONObject sb = new JSONObject();
		for(Document d:professors)
		{
			sb.append(d.getString("id"),d.toJson());
		}
		return Response.status(200).entity(sb.toString()).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfessor(@PathParam("id") String id){
		
		Document professor =professorDAO.getOneProfessor(id);
		if(professor ==null)
		{
			return Response.status(400).build();
		}
		return Response.status(200).entity(professor.toJson()).build();
	}
	
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProfessor(@PathParam("id") String id, Professor professor){
		boolean success=professorDAO.updateProfessor(id, professor);
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
}

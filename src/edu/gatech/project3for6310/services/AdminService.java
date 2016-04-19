package edu.gatech.project3for6310.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.json.JSONObject;

import edu.gatech.project3for6310.entity.Student;

@Path("/admin")
public class AdminService {

	private static SimulationService simulationService = SimulationService.getInstance();
	@Path("/submit")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response submit( ){
		String requestId ="admin"+"_"+String.valueOf(System.currentTimeMillis());
		simulationService.addAdminRequest(requestId);
		SimulationService.start();
		JSONObject sb= new JSONObject();
		sb.put("result", "submitted");
	    return Response.status(200).entity(sb.toString()).header("isUpdated",true).build();

	}
}

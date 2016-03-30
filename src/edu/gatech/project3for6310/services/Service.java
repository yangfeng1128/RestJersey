package edu.gatech.project3for6310.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/student")
public class Service {
	@GET
	@Produces("application/json")
	public Response student(){
		JSONObject j = new JSONObject();
		j.put("1", "student1");
		return Response.status(200).entity(j.toString()).build();
	}

}

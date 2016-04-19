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
import org.json.JSONArray;

import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.dao.SimulationRecordDAO;
import edu.gatech.project3for6310.entity.SimulationRecord;

@Path("/simulationrecords")
public class SimulationRecordService {

	@Inject
	private static SimulationRecordDAO simulationRecordDAO;
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSimulationRecords(){
		
		List<Document> simulationRecords =simulationRecordDAO.getAllSimulationRecords();
		JSONArray sb = new JSONArray();
		for(Document d:simulationRecords)
		{
			sb.put(d);
		}
		return Response.status(200).entity(sb.toString()).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSimulationRecord(@PathParam("id") String id){
		
		Document simulationRecord =simulationRecordDAO.getOneSimulationRecord(id);
		if(simulationRecord ==null)
		{
			return Response.status(404).build();
		}
		return Response.status(200).entity(simulationRecord.toJson()).build();
	}
	
}

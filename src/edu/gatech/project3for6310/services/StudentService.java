package edu.gatech.project3for6310.services;

import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
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
import org.json.JSONArray;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.dao.StudentDAO;
import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.utils.ObjectConversion;

@Path("/students")
public class StudentService {
	
	@Inject
	private static StudentDAO studentDAO;
	private static SimulationService simulationService = SimulationService.getInstance();
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents(){
		
		List<Document> students =studentDAO.getAllStudents();
		JSONArray sb = new JSONArray();
		for(Document d:students)
		{
			sb.put(d);
		}
		return Response.status(200).entity(sb.toString()).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneStudent(@PathParam("id") String id){
		
		Document student =studentDAO.getOneStudent(id);
		if(student ==null)
		{
			/*
			Student newStudent = new Student();
			newStudent.setId(id);
			Document doc = ObjectConversion.studentToDocument(newStudent);
			courseDAO.saveOneStudent(doc);
			return Response.status(200).entity(doc.toJson()).build();
			*/
			JSONObject sb = new JSONObject();
			sb.append("Exception:", "not found.");
			return Response.status(404).entity(sb.toString()).build();
		}
		return Response.status(200).entity(student.toJson()).build();
	}
	
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStudent(@PathParam("id") String id, Student student){
		String requestId ="student_"+id+"_"+String.valueOf(System.currentTimeMillis());
		student.setIsSimulated(false);
		student.setRequestId(requestId);
		boolean success=studentDAO.updateStudent(id, student);
		simulationService.addStudentRequest(requestId);
		SimulationService.start();
		JSONObject sb= new JSONObject();
		
	    String res = null;
	    if (success)
	    {
	    	res="updated successfully";
	    	sb.put("result", res);
	    	return Response.status(200).entity(sb.toString()).header("isUpdated",success).build();
	    } else {
	    	res="not updated";
	    	sb.put("result", res);
	    	return Response.status(400).entity(sb.toString()).header("isUpdated", success).build();
	    }
		
	}
	
	
	

}

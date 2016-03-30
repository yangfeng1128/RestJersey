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

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.dao.StudentDAO;
import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Student;

@Path("/student")
public class StudentService {
	
	@Inject
	private static StudentDAO studentDAO;
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents(){
		
		List<Document> students =studentDAO.getAllStudents();
		StringBuilder sb = new StringBuilder();
		for(Document d:students)
		{
			sb.append(d.toJson());
		}
		return Response.status(200).entity(sb.toString()).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneStudent(@PathParam("id") int id){
		
		Document student =studentDAO.getOneStudent(id);
		return Response.status(200).entity(student.toJson()).build();
	}
	
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStudentChoice(Student student){
		
	    Document success=studentDAO.updateStudent(student);
	    String res = null;
	    if (success != null)
	    {
	    	res="updated successfully";
	    } else {
	    	res="not updated";
	    }
		return Response.status(200).entity(res).build();
	}
	
	

}

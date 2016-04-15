package edu.gatech.project3for6310.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.json.JSONObject;

import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.dao.CourseDAO;
import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.utils.ObjectConversion;

@Path("/courses")
public class CourseService {

	@Inject
	private static CourseDAO courseDAO;
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCourses(){
		
		List<Document> courses =courseDAO.getAllCourses();
		JSONObject sb = new JSONObject();
		for(Document d:courses)
		{
			sb.append(d.getString("id"),d.toJson());
		}
		return Response.status(200).entity(sb.toString()).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourse(@PathParam("id") String id){
		
		Document course =courseDAO.getOneCourse(id);
		if(course ==null)
		{
			return Response.status(400).build();
		}
		return Response.status(200).entity(course.toJson()).build();
	}
	
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCourse(@PathParam("id") String id, Course course){
		boolean success=courseDAO.updateCourse(id, course);
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

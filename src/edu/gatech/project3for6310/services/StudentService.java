package edu.gatech.project3for6310.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.gatech.project3for6310.dbconnection.MongoConnection;

@Path("/student")
public class StudentService {
	
	private static MongoClient mongoClient = MongoConnection.getInstance().getClient();
	
	@GET
	@Produces("application/json")
	public Response student(){
		MongoDatabase database = mongoClient.getDatabase("6310Project3");
		MongoCollection<Document> collection = database.getCollection("student");
		Document first = collection.find().first();
		String j = first.toJson();
		return Response.status(200).entity(j).build();
	}

}

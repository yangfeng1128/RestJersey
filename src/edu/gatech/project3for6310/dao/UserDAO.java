package edu.gatech.project3for6310.dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import edu.gatech.project3for6310.dbconnection.MongoConnection;

public class UserDAO {
	
	private static MongoConnection conn = MongoConnection.getInstance();
	private static MongoCollection<Document> userCollection;
	
	public UserDAO()
	{
		userCollection=conn.getCollection("user");
	}
	
	public Document getOneUser(String username)
	{
		return userCollection.find(eq("username",username)).first();
	}
}

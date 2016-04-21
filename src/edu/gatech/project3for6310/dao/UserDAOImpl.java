package edu.gatech.project3for6310.dao;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.User;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class UserDAOImpl implements UserDAO{
	private static  MongoClient mongoClient =MongoConnection.getInstance().getClient();
	private static MongoDatabase db =mongoClient.getDatabase("6310Project3");
	private static MongoCollection<Document> collection=db.getCollection("course");
	
	
	public List<Document> getAllUsers() {
		List<Document> list= new ArrayList<Document>();
	    FindIterable<Document> docs =collection.find().projection(excludeId());
	    Iterator<Document> idocs= docs.iterator();
	    while (idocs.hasNext())
	    {
	    	list.add(idocs.next());
	    }
		return list;
		
	}

	public Document getOneUser(String username) {
		return collection.find(eq("username", username)).projection(excludeId()).first();
	}

	public boolean updateUser(String id, User user) {
		Document doc = ObjectConversion.userToDocument(user);
		collection.replaceOne(eq("id",id), doc);
		return true;
	}
	

}

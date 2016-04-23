package edu.gatech.project3for6310.dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import java.util.List;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.User;
import edu.gatech.project3for6310.utils.ObjectConversion;

public interface UserDAO {
	
	
	public List<Document> getAllUsers(); 

	public Document getOneUser(String username);

	public Document getOneUserById(String id);

	public boolean updateUser(String id, User user); 
	

}

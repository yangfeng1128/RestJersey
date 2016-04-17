package edu.gatech.project3for6310.dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import java.util.List;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.User;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class UserDAO {
	
	private static BasicDAO<User> dao = new BasicDAO<User>(User.class);
	public List<Document> getAllUsers() {
		return dao.getAll();
		
	}

	public Document getOneUser(String username) {
		return dao.getByUserName(username);
	}

	public boolean updateUser(String id, User user) {
		Document doc = ObjectConversion.userToDocument(user);
		dao.updateById(id, doc);
		return true;
	}
	

}

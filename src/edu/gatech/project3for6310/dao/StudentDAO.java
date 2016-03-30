package edu.gatech.project3for6310.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Student;

public class StudentDAO {
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private static MongoCollection<Document> collection;
	public StudentDAO()
	{
		mongoClient =MongoConnection.getInstance().getClient();
		database = mongoClient.getDatabase("6310Project3");
		collection = database.getCollection("student");
	}
	
	public List<Document> getAllStudents()
	{
		
		return collection.find().into(new ArrayList<Document>());
	}

	public Document getOneStudent(int id) {
		return collection.find(eq("id",id)).first();
	}

	public Document updateStudent(Student student) {
		Document doc =new Document("id",student.getId())
				.append("firstName", student.getFirstName())
				.append("lastName",student.getLastName());
		return collection.findOneAndReplace(eq("id",student.getId()), doc);
		
	}
	
}

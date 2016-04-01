package edu.gatech.project3for6310.dao;

import java.util.ArrayList;

import java.util.List;

import org.bson.Document;


import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.*;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Student;

public class StudentDAO {
	private static MongoConnection conn = MongoConnection.getInstance();
	private static MongoCollection<Document> studentCollection;
	public StudentDAO()
	{
		studentCollection = conn.getCollection("student");
	}
	
	public List<Document> getAllStudents()
	{
		
		return studentCollection.find().into(new ArrayList<Document>());
	}

	public Document getOneStudent(int id) {
		return studentCollection.find(eq("id",id)).first();
	}

	public Document updateStudent(Student student) {
		Document doc =new Document("id",student.getId())
				.append("firstName", student.getFirstName())
				.append("lastName",student.getLastName());
		return studentCollection.findOneAndReplace(eq("id",student.getId()), doc);
		
	}
	
}

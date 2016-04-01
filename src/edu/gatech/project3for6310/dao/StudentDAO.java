package edu.gatech.project3for6310.dao;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.utils.ObjectConversion;

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

	public Document getOneStudent(String id) {
		return studentCollection.find(eq("id",id)).first();
	}

	public boolean updateStudent(String id,Student student) {
		try{
		Document updatedStudent = ObjectConversion.studentToDocument(student);
		UpdateResult doc=studentCollection.replaceOne(eq("id",id), updatedStudent);
		return true;
		} catch(MongoException e)
		{
			return false;
		}
		
	}
	
}

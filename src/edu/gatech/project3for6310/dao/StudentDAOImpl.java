package edu.gatech.project3for6310.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class StudentDAOImpl implements StudentDAO {
	private static  MongoClient mongoClient =MongoConnection.getInstance().getClient();
	private static MongoDatabase db =mongoClient.getDatabase("6310Project3");
	private static MongoCollection<Document> collection=db.getCollection("student");

	public List<Document> getAllStudents() {
		List<Document> list= new ArrayList<Document>();
	    FindIterable<Document> docs =collection.find().projection(excludeId());
	    Iterator<Document> idocs= docs.iterator();
	    while (idocs.hasNext())
	    {
	    	list.add(idocs.next());
	    }
		return list;
		
	}

	public Document getOneStudent(String id) {
		return collection.find(eq("id", id)).projection(excludeId()).first();
	}

	public boolean updateStudent(String id, Student student) {
		try{
		Document doc = ObjectConversion.studentToDocument(student);
		collection.replaceOne(eq("id",id), doc);
		return true;
		} catch( Exception e)
		{
			return false;
		}
	}
	
	public boolean saveOneStudent(Document doc)
	{
		try{
			collection.insertOne(doc);
		return true;
		} catch(Exception e)
		{
		return false; 	
		}
	}

	public void updateStudent(String id, Document doc) {
		collection.replaceOne(eq("id",id), doc);
	}

	
	
	
}

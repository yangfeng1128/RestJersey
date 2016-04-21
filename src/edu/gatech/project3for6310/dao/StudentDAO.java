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
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.utils.ObjectConversion;

public interface StudentDAO {
	
	public List<Document> getAllStudents();

	public Document getOneStudent(String id);

	public boolean updateStudent(String id, Student student);
	
	public boolean saveOneStudent(Document doc);
	

	public void updateStudent(String id, Document sDoc); 

	
	
	
}

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

public class StudentDAOImpl implements StudentDAO {
	private static BasicDAO<Student> dao = new BasicDAO<Student>(Student.class);
	public List<Document> getAllStudents() {
		return dao.getAll();
		
	}

	public Document getOneStudent(String id) {
		return dao.getById(id);
	}

	public boolean updateStudent(String id, Student student) {
		try{
		Document doc = ObjectConversion.studentToDocument(student);
		dao.updateById(id, doc);
		return true;
		} catch( Exception e)
		{
			return false;
		}
	}
	
	public boolean saveOneStudent(Document doc)
	{
		try{
		dao.save(doc);
		return true;
		} catch(Exception e)
		{
		return false; 	
		}
	}

	public void updateStudent(String id, Document sDoc) {
		dao.updateById(id, sDoc);	
	}

	
	
	
}

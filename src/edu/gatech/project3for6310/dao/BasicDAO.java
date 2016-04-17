package edu.gatech.project3for6310.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Student;

public class BasicDAO<T> {
	private static MongoConnection conn = MongoConnection.getInstance();
	private static MongoCollection<Document> collection;
	private final Class<T> clazz;
	
	public BasicDAO (Class<T> clazz)
	{
		this.clazz = clazz;
		String c =clazz.getName().toLowerCase();
		int p= c.lastIndexOf(".");
		String cn = c.substring(p+1);
		System.out.println(cn);
		collection=conn.getCollection(cn);
	//	collection.createIndex(new Document("id",1));
	}
	
	public void save(Document doc)
	{
		collection.insertOne(doc);
	}
	
	public void deleteById(String id)
	{
		collection.deleteOne(eq("id", id));
	}
	
	public Document getById(String id)
	{
		return collection.find(eq("id", id)).first();
	}
	
	public void updateById(String id, Document doc)
	{
		collection.replaceOne(eq("id",id), doc);
	}
	public List<Document> getAll()
	{
		List<Document> list= new ArrayList<Document>();
	 
		return list;
	}
	public Document getByUserName(String username) {
		return collection.find(eq("username", username)).first();
	}
	
	public static void main(String[] args)
	{
		BasicDAO<Student> s= new BasicDAO<Student>(Student.class);
	    System.out.println(s.collection.find(eq("id","04569")).first());
	}


}
 
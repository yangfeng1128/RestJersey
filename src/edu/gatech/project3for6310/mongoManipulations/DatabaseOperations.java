package edu.gatech.project3for6310.mongoManipulations;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.utils.ObjectConversion;

import static com.mongodb.client.model.Filters.*;
public class DatabaseOperations {
	private static MongoClient mongoClient =MongoConnection.getInstance().getClient(); ;
	private static MongoDatabase database=mongoClient.getDatabase("6310Project3");;
	private static MongoCollection<Document> userCollection=database.getCollection("user");
	private static MongoCollection<Document> studentCollection=database.getCollection("student");
	
	public static void main(String[] args){
		
		/*userCollection.insertOne(new Document().append("username", "fyang3")		
				.append("password","1234")
				.append("fullname", "Feng Yang")
				.append("role","student")
				.append("isAdmin",false)
				.append("studentID","04567")); */
		List<String> li = new ArrayList<String>();
		li.add("6550"); 
		studentCollection.deleteOne(eq("id","04568"));
		studentCollection.insertOne(new Document().append("id", "04568")		
				.append("numDesiredCourse", 1)
				.append("preferredCources", new Document().append("6550", 1).append("6250", 2))
				.append("requestId","2")
				.append("rcmCources",li)
				.append("isSimulated",false)); 
		Document doc = studentCollection.find(eq("id","04568")).first();
		Student stu = ObjectConversion.documentToStudent(doc);
		System.out.println(stu.getId());
		
	}
}

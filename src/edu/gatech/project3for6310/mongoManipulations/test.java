package edu.gatech.project3for6310.mongoManipulations;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.entity.TeachingAssistant;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class test {
	private  MongoClient mongoClient =MongoConnection.getInstance().getClient();
	private MongoDatabase database=mongoClient.getDatabase("6310Project3");
	public static void main(String[] args) {
		test t1= new test();
		MongoCollection<Document> students=t1.database.getCollection("student");
		Document stu= students.find().first();
		Student s= ObjectConversion.documentToStudent(stu);
		test t2= new test();
		MongoCollection<Document> courses=t2.database.getCollection("course");
		Document crs= courses.find().first();
		Course c= ObjectConversion.documentToCourse(crs);

	}

}

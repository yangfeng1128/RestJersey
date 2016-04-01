package edu.gatech.project3for6310.mongoManipulations;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.gatech.project3for6310.dbconnection.MongoConnection;

public class DatabaseOperations {
	private static MongoClient mongoClient =MongoConnection.getInstance().getClient(); ;
	private static MongoDatabase database=mongoClient.getDatabase("6310Project3");;
	private static MongoCollection<Document> userCollection=database.getCollection("user");
	
	public static void main(String[] args){
		userCollection.insertOne(new Document().append("username", "fyang3")		
				.append("password","1234")
				.append("fullname", "Feng Yang")
				.append("role","student")
				.append("isAdmin",false)
				.append("studentID","04567"));
	}
}

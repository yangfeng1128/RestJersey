package edu.gatech.project3for6310.dbconnection;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
	private static MongoConnection mongoConnection;
	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	private MongoConnection()
	{
		MongoClient mgClient = new MongoClient("localhost",27017);
		MongoConnection.mongoClient=mgClient;
		mongoDatabase= mongoClient.getDatabase("6310Project3");
	}
	
	public static MongoConnection getInstance()
	{
		if (mongoConnection==null)
		{
			mongoConnection = new MongoConnection();
		} 
		return mongoConnection;
	}
	
	public MongoClient getClient()
	{
		return mongoClient;
	}
	public MongoCollection<Document> getCollection(String collection)
	{
		return mongoDatabase.getCollection(collection);
	}
	
}

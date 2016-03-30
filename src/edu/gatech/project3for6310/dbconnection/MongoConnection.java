package edu.gatech.project3for6310.dbconnection;

import com.mongodb.MongoClient;

public class MongoConnection {
	private static MongoConnection mongoConnection;
	private static MongoClient mongoClient;
	private MongoConnection()
	{
		MongoClient mgClient = new MongoClient("localhost",27017);
		this.mongoClient=mgClient;
	}
	
	public static MongoConnection getInstance()
	{
		if (mongoConnection==null)
		{
			mongoConnection = new MongoConnection();
		} 
		return mongoConnection;
	}
	
	public static MongoClient getClient()
	{
		return mongoClient;
	}
}

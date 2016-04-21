package edu.gatech.project3for6310.dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.SimulationRecord;

public class SimulationRecordDAOImpl implements SimulationRecordDAO{
	private static  MongoClient mongoClient =MongoConnection.getInstance().getClient();
	private static MongoDatabase db =mongoClient.getDatabase("6310Project3");
	private static MongoCollection<Document> collection=db.getCollection("simulationrecord");
	
	public List<Document> getAllSimulationRecords() {
		List<Document> list= new ArrayList<Document>();
	    FindIterable<Document> docs =collection.find().projection(excludeId());
	    Iterator<Document> idocs= docs.iterator();
	    while (idocs.hasNext())
	    {
	    	list.add(idocs.next());
	    }
		return list;
		
	}

	public Document getOneSimulationRecord(String id) {
		return collection.find(eq("id", id)).projection(excludeId()).first();
	}

	public void create(Document doc) {
		try{
			collection.insertOne(doc);
		}catch(Exception e)
		{
			
		}
		
	}

}

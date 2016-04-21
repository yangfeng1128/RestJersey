package edu.gatech.project3for6310.dao;

import java.util.List;

import org.bson.Document;

import edu.gatech.project3for6310.entity.SimulationRecord;

public interface SimulationRecordDAO {

	
	public List<Document> getAllSimulationRecords(); 

	public Document getOneSimulationRecord(String id); 

	public void create(Document srDoc); 

}

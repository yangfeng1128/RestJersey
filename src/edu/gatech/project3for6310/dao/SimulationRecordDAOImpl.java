package edu.gatech.project3for6310.dao;

import java.util.List;

import org.bson.Document;

import edu.gatech.project3for6310.entity.SimulationRecord;

public class SimulationRecordDAOImpl implements SimulationRecordDAO{

	private static BasicDAO<SimulationRecord> dao = new BasicDAO<SimulationRecord>(SimulationRecord.class);
	public List<Document> getAllSimulationRecords() {
		return dao.getAll();
		
	}

	public Document getOneSimulationRecord(String id) {
		return dao.getById(id);
	}

	public void create(Document srDoc) {
		try{
		 dao.save(srDoc);	
		}catch(Exception e)
		{
			
		}
		
	}

}

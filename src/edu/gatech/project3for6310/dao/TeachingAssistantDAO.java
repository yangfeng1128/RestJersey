package edu.gatech.project3for6310.dao;

import java.util.List;

import org.bson.Document;

import edu.gatech.project3for6310.entity.TeachingAssistant;
import edu.gatech.project3for6310.utils.ObjectConversion;

public interface TeachingAssistantDAO {

	
	public List<Document> getAllTeachingAssistants(); 

	public Document getOneTeachingAssistant(String id); 

	public boolean updateTeachingAssistant(String id, TeachingAssistant teachingAssistant); 
	public void updateTeachingAssistant(String id,Document doc); 


	public boolean createTeachingAssistant(String id, TeachingAssistant teachingAssistant);

	public boolean deleteTeachingAssistant(String id); 

}

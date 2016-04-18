package edu.gatech.project3for6310.dao;

import java.util.List;

import org.bson.Document;

import edu.gatech.project3for6310.entity.TeachingAssistant;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class TeachingAssistantDAO {

	private static BasicDAO<TeachingAssistant> dao = new BasicDAO<TeachingAssistant>(TeachingAssistant.class);
	public List<Document> getAllTeachingAssistants() {
		return dao.getAll();
		
	}

	public Document getOneTeachingAssistant(String id) {
		return dao.getById(id);
	}

	public boolean updateTeachingAssistant(String id, TeachingAssistant teachingAssistant) {
		try{
		Document doc = ObjectConversion.teachingAssistantToDocument(teachingAssistant);
		dao.updateById(id, doc);
		return true;
		} catch (Exception e)
		{
			return false;
		}
	}
	public void updateTeachingAssistant(String id,Document doc) {
		try{
	
		dao.updateById(id, doc);

		} catch (Exception e)
		{

		}
	}


	public boolean createTeachingAssistant(String id, TeachingAssistant teachingAssistant) {
		try{
		Document doc = ObjectConversion.teachingAssistantToDocument(teachingAssistant);
		dao.save(doc);
		return true;
		} catch (Exception e)
		{
			return false;
		}
	}
	

	public boolean deleteTeachingAssistant(String id) {
		try{
		dao.deleteById(id);
		return true;
		} catch (Exception e)
		{
			return false;
		}
	}

}

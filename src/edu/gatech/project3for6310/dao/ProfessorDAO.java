package edu.gatech.project3for6310.dao;

import java.util.List;

import org.bson.Document;

import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.utils.ObjectConversion;

public interface ProfessorDAO {

	
	public List<Document> getAllProfessors(); 

	public Document getOneProfessor(String id); 

	public boolean updateProfessor(String id, Professor professor); 

	public void updateProfessor(String id, Document pDoc); 
}

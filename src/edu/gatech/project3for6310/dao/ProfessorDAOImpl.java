package edu.gatech.project3for6310.dao;

import java.util.List;

import org.bson.Document;

import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class ProfessorDAOImpl implements ProfessorDAO {

	private static BasicDAO<Professor> dao = new BasicDAO<Professor>(Professor.class);
	public List<Document> getAllProfessors() {
		return dao.getAll();
		
	}

	public Document getOneProfessor(String id) {
		return dao.getById(id);
	}

	public boolean updateProfessor(String id, Professor professor) {
		try{
		Document doc = ObjectConversion.professorToDocument(professor);
		dao.updateById(id, doc);
		return true;
		} catch(Exception e)
		{
			return false;
		}
	}

	public void updateProfessor(String id, Document pDoc) {
		try{
		
			dao.updateById(id, pDoc);
	
			} catch(Exception e)
			{

			}
		
	}

}
package edu.gatech.project3for6310.dao;

import java.util.List;

import org.bson.Document;

import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class CourseDAOImpl implements CourseDAO {
    private static BasicDAO<Course> dao = new BasicDAO<Course>(Course.class);
	
	public List<Document> getAllCourses() {	
		return dao.getAll();
	}

	public Document getOneCourse(String id) {
		
		return dao.getById(id);
	}


	public boolean updateCourse(String id, Course course) {
		try{
		Document doc = ObjectConversion.courseToDocument(course);
		dao.updateById(id, doc);
		return true;
		} catch(Exception e)
		{
		return false;
		}
	}
	public void updateCourse(String id,Document doc) {
		try{
	
		dao.updateById(id, doc);

		} catch(Exception e)
		{

		}
	}
}

package edu.gatech.project3for6310.dao;

import java.util.List;

import org.bson.Document;

import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class CourseDAO {
    private static BasicDAO<Course> dao = new BasicDAO<Course>(Course.class);
	
	public List<Document> getAllCourses() {	
		return dao.getAll();
	}

	public Document getOneCourse(String id) {
		
		return dao.getById(id);
	}


	public boolean updateCourse(String id, Course course) {
		Document doc = ObjectConversion.courseToDocument(course);
		dao.updateById(id, doc);
		return false;
	}

}

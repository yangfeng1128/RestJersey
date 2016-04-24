package edu.gatech.project3for6310.dao;

import java.util.List;

import org.bson.Document;

import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.utils.ObjectConversion;

public interface CourseDAO {
  
	
	public List<Document> getAllCourses(); 

	public Document getOneCourse(String id); 
	


	public boolean updateCourse(String id, Course course); 
	public void updateCourse(String id,Document doc);

	public boolean createCourse(String id, Course course);

	public boolean deleteCourse(String id); 
}

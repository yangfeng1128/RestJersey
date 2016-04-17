package edu.gatech.project3for6310.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;

import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.entity.TeachingAssistant;
import edu.gatech.project3for6310.entity.User;

public class ObjectConversion {

	public static Document studentToDocument(Student student)
	{
		Document studentDoc = new Document();
		studentDoc.append("id",student.getId())
					.append("fullName", student.getFullName())
		           .append("numDesiredCourse", student.getNumDesiredCourse())
				   .append("preferredCources", student.getPreferredCources())
				   .append("requestId",student.getRequestId())
				   .append("rcmCources",student.getRcmCources())
				   .append("isSimulated", student.getIsSimulated())
				   .append("courseTaken", student.getCourseTaken());
		
		return studentDoc;
	}
	
	public static Student documentToStudent(Document doc)
	{
		Student stu = new Student();
		Document courses = (Document) doc.get("preferredCources");
		List<String> preferredCourses = (List<String>) doc.get("preferredCourses");
		List<String> rcmCourses = (List<String>) doc.get("rcmCources");
		List<String> courseTaken = (List<String>) doc.get("courseTaken");
		stu.setId(doc.getString("id"));
		stu.setFullName(doc.getString("fullName"));
		stu.setNumDesiredCourse(doc.getInteger("numDesiredCourse"));
		stu.setPreferredCources(preferredCourses);
		stu.setRequestId(doc.getString("requestId"));
		stu.setRcmCources(rcmCourses);
		stu.setIsSimulated(doc.getBoolean("isSimulated"));
		stu.setCourseTaken(courseTaken);
		return stu;
	}

	public static Document courseToDocument(Course course) {
		return null;
	}

	public static Document professorToDocument(Professor professor) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Document teachingAssistantToDocument(TeachingAssistant teachingAssistant) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Document userToDocument(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}

package edu.gatech.project3for6310.utils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;

import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.entity.SimulationRecord;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.entity.TeachingAssistant;
import edu.gatech.project3for6310.entity.User;

public class ObjectConversion {

	public static Document studentToDocument(Student student)
	{
		Document studentDoc = new Document();
		studentDoc.append("id",student.getId())
				   .append("fullName", student.getFullName())
				   .append("email", student.getEmail())
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
		List<String> preferredCourses = (List<String>) doc.get("preferredCourses");
		List<String> rcmCourses = (List<String>) doc.get("rcmCources");
		List<String> courseTaken = (List<String>) doc.get("courseTaken");
		stu.setId(doc.getString("id"));
		stu.setFullName(doc.getString("fullName"));
		stu.setEmail(doc.getString("email"));
		stu.setNumDesiredCourse(doc.getInteger("numDesiredCourse"));
		stu.setPreferredCources(preferredCourses);
		stu.setRequestId(doc.getString("requestId"));
		stu.setRcmCources(rcmCourses);
		stu.setIsSimulated(doc.getBoolean("isSimulated"));
		stu.setCourseTaken(courseTaken);
		return stu;
	}

	public static Document userToDocument(User user)
	{
		Document userDoc = new Document();
		userDoc.append("id",user.getId())
				   .append("fullName", user.getFullname())
				   .append("email", user.getEmail())
		           .append("username", user.getUsername())
				   .append("password", user.getPassword())
				   .append("role",user.getRole())
				   .append("isAdmin",user.getIsAdmin());	
		return userDoc;
	}
	
	public static User documentToUser(Document doc){
		User user = new User();
		user.setId(doc.getString("id"));
		user.setFullname(doc.getString("fullname"));
		user.setEmail(doc.getString("email"));
		user.setUsername(doc.getString("username"));
		user.setPassword(doc.getString("password"));
		user.setRole(doc.getString("role"));
		user.setIsAdmin(doc.getBoolean("isAdmin"));
		return user;
		
	}
	
	
	public static Document courseToDocument(Course course) {
		Document courseDoc = new Document();
		courseDoc.append("id",course.getId())
				   .append("isShadow", course.getIsShadow())
				   .append("courseName", course.getCourseName())
		           .append("assignedProfessor", course.getAssignedProfessor())
				   .append("isOffered", course.getIsOffered())
				   .append("isMandatory",course.getIsMandatory())
				   .append("assignedTA",course.getAssignedTA())
				   .append("prerequisites", course.getPrerequisites());
		return courseDoc;
	}
	
	public static Course documentToCourse(Document doc)
	{
		Course course = new Course();
		List<String> assignedta= (List<String>) doc.get("assignedTA");
		List<String> prerequisites= (List<String>) doc.get("prerequisites");
		course.setId(doc.getString("id"));
		course.setIsShadow(doc.getBoolean("isShadow"));
		course.setCourseName(doc.getString("courseName"));
		course.setAssignedProfessor(doc.getString("assignedProfessor"));
		course.setIsOffered(doc.getBoolean("isOffered"));
		course.setIsMandatory(doc.getBoolean("isMandatory"));
		course.setAssignedTA(assignedta);
		course.setPrerequisites(prerequisites);
		return course;	
	}

	public static Document professorToDocument(Professor professor) {
		Document professorDoc = new Document();
		professorDoc.append("id",professor.getId())
				   .append("isShadow", professor.getIsShadow())
				   .append("fullName", professor.getFullName())
		           .append("capableCourses", professor.getCapableCourses())
				   .append("courseAssigned", professor.getCourseAssigned());
				
		return professorDoc;
	}

	public static Professor documentToProfessor(Document doc)
	{
		Professor professor = new Professor();
		List<String> capableCourses= (List<String>) doc.get("capableCourses");
		List<String> courseAssigned= (List<String>) doc.get("courseAssigned");
		professor.setId(doc.getString("id"));
		professor.setIsShadow(doc.getBoolean("isShadow"));
		professor.setFullName(doc.getString("fullName"));
		professor.setCapableCourses(capableCourses);
		professor.setCourseAssigned(courseAssigned);
		return professor;
	}
	public static Document teachingAssistantToDocument(TeachingAssistant teachingAssistant) {
		Document teachingAssistantDoc = new Document();
		teachingAssistantDoc.append("id",teachingAssistant.getId())
				   .append("isShadow", teachingAssistant.getIsShadow())
		           .append("capableCourses", teachingAssistant.getCapableCourses())
				   .append("courseAssigned", teachingAssistant.getCourseAssigned());		
		return teachingAssistantDoc;
	}
	
	public static TeachingAssistant documentToTeachingAssistant(Document doc)
	{
		TeachingAssistant teachingassistant = new TeachingAssistant();
		List<String> capableCourses= (List<String>) doc.get("capableCourses");
		teachingassistant.setId(doc.getString("id"));
		teachingassistant.setIsShadow(doc.getBoolean("isShadow"));
		teachingassistant.setCapableCourses(capableCourses);
		teachingassistant.setCourseAssigned(doc.getString("courseAssigned"));
		return teachingassistant;
	}
	
	public static SimulationRecord documentToSimulationRecord(Document doc)
	{
		SimulationRecord simulationRecord = new SimulationRecord();
		Map<String, String[]> studentPreference = new HashMap<String, String[]>();
		
		simulationRecord.setId(doc.getString("id"));
		simulationRecord.setIsShadowMode(doc.getBoolean("isShadowMode"));
		simulationRecord.setSimulatedTime(doc.getString("simulatedTime"));
		
		simulationRecord.setCapableCourses(capableCourses);
		simulationRecord.setCourseAssigned(courseAssigned);
		return simulationRecord;
	}
	


}

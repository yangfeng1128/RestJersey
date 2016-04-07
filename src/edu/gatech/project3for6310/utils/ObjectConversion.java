package edu.gatech.project3for6310.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;

import edu.gatech.project3for6310.entity.Student;

public class ObjectConversion {

	public static Document studentToDocument(Student student)
	{
		Document studentDoc = new Document();
		Document studentCs= new Document();
		Map<String,Integer> studentCourses = student.getPreferredCources();
		if (studentCourses !=null)
		{
			for (Map.Entry<String, Integer> entry:studentCourses.entrySet())
			{
				studentCs.append(entry.getKey(), entry.getValue());
			}
		}

		studentDoc.append("id",student.getId())
					.append("fullName", student.getFullName())
		           .append("numDesiredCourse", student.getNumDesiredCourse())
				   .append("preferredCources", studentCs)
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
		Map<String, Integer> courseMap = new HashMap<String, Integer>();
		for(Entry<String, Object> entry: courses.entrySet())
		{
			courseMap.put(entry.getKey(), (Integer)entry.getValue());
		}
		List<String> rcmCourses = (List<String>) doc.get("rcmCources");
		List<String> courseTaken = (List<String>) doc.get("courseTaken");
		stu.setId(doc.getString("id"));
		stu.setFullName(doc.getString("fullName"));
		stu.setNumDesiredCourse(doc.getInteger("numDesiredCourse"));
		stu.setPreferredCources(courseMap);
		stu.setRequestId(doc.getString("requestId"));
		stu.setRcmCources(rcmCourses);
		stu.setIsSimulated(doc.getBoolean("isSimulated"));
		stu.setCourseTaken(courseTaken);
		return stu;
	}
}

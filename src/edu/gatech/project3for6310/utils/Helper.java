package edu.gatech.project3for6310.utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.project3for6310.entity.*;

public class Helper {
	
	private static Helper singletonHelper = new Helper(); 
	
	private Helper(){}
	
	public static Helper getInstance(){
		return singletonHelper; 
	}
	
	public List<Student> getStudents(){
		List<Student> students; 
		List<String> preferredCourses; 
		Student student; 
		
		students = new ArrayList<Student>(); 
		
		// student 1
		student = new Student(); 
		student.setId("1"); 
		student.setFullName("Bob Smith");
		student.setNumDesiredCourse(2);
		preferredCourses = new ArrayList<String>(); 
		preferredCourses.add("1");	// 1 = Eng 101 	- preference 1
		preferredCourses.add("2"); 	// 2 = Math 101	- preference 2	
		preferredCourses.add("3"); 	// 3 = Soc 101	- preference 3
		student.setPreferredCources(preferredCourses);
		student.setCourseTaken(Arrays.asList("4", "5", "6"));	// 4=Eng 100, 5=Math 100, and 6=Soc 100
		students.add(student); 
		
		// student 2
		student = new Student(); 
		student.setId("2"); 
		student.setFullName("Lisa Dorgan");
		student.setNumDesiredCourse(2);
		preferredCourses = new ArrayList<String>();  
		preferredCourses.add("1");	// 1 = Eng 101 	- preference 1
		preferredCourses.add("2"); 	// 2 = Math 101	- preference 2	
		preferredCourses.add("3"); 	// 3 = Soc 101	- preference 3
		student.setPreferredCources(preferredCourses);
		student.setCourseTaken(Arrays.asList("4", "5", "6"));	// 4=Eng 100, 5=Math 100, and 6=Soc 100
		students.add(student);
		
		// student 3
		student = new Student(); 
		student.setId("3"); 
		student.setFullName("James Johnson");
		student.setNumDesiredCourse(2);
		preferredCourses = new ArrayList<String>(); 
		preferredCourses.add("1");	// 1 = Eng 101 	- preference 1
		preferredCourses.add("3"); 	// 3 = Soc 101	- preference 2
		preferredCourses.add("2"); 	// 2 = Math 101	- preference 3	
		student.setPreferredCources(preferredCourses);
		student.setCourseTaken(Arrays.asList("4", "5", "6"));	// 4=Eng 100, 5=Math 100, and 6=Soc 100
		students.add(student); 
		
		return students; 
	}

	public List<Professor> getProfessors(){
		List<Professor> professors; 
		Professor professor; 
		
		professors = new ArrayList<Professor>(); 
		
		// professor 1
		professor = new Professor(); 
		professor.setId("1");
		professor.setFullName("David Williams");
		professor.setCapableCourses(Arrays.asList("1", "2"));
		professor.setCourseAssigned(Arrays.asList("1"));
		professors.add(professor); 
		
		// professor 2
		professor = new Professor(); 
		professor.setId("2");
		professor.setFullName("Joseph JONES");
		professor.setCapableCourses(Arrays.asList("1", "2"));
		professor.setCourseAssigned(Arrays.asList("2"));
		professors.add(professor); 
		
		// professor 3
		professor = new Professor(); 
		professor.setId("3");
		professor.setFullName("Abbie JACKSON");
		professor.setCapableCourses(Arrays.asList("1", "2", "3"));
		professor.setCourseAssigned(Arrays.asList("3"));
		professors.add(professor); 
		
		return professors; 
	}
	
	public List<TeachingAssistant> getTeachingAssistants(){
		List<TeachingAssistant> teachingAssistants; 
		TeachingAssistant teachingAssistant; 
		
		teachingAssistants = new ArrayList<TeachingAssistant>(); 
		
		// teachingAssistant 1
		teachingAssistant = new TeachingAssistant(); 
		teachingAssistant.setId("1");
	//	teachingAssistant.setFullName("Janee Herlihy");
		teachingAssistant.setCapableCourses(Arrays.asList("1", "2"));
		teachingAssistant.setCourseAssigned(Arrays.asList("1"));
		teachingAssistants.add(teachingAssistant); 
		
		// teachingAssistant 2
		teachingAssistant = new TeachingAssistant(); 
		teachingAssistant.setId("2");
	//	teachingAssistant.setFullName("Sharita Luque");
		teachingAssistant.setCapableCourses(Arrays.asList("1", "2"));
		teachingAssistant.setCourseAssigned(Arrays.asList("2"));
		teachingAssistants.add(teachingAssistant); 
		
		// teachingAssistant 3
		teachingAssistant = new TeachingAssistant(); 
		teachingAssistant.setId("3");
	//	teachingAssistant.setFullName("Astrid Dunnigan");
		teachingAssistant.setCapableCourses(Arrays.asList("1", "2", "3"));
		teachingAssistant.setCourseAssigned(Arrays.asList("3"));
		teachingAssistants.add(teachingAssistant); 
		
		return teachingAssistants; 
	}

	public List<Course> getCourses(){
		List<Course> courses; 
		Course course; 
		
		courses = new ArrayList<Course>(); 
		
		// course 1
		course = new Course(); 
		course.setId("1");
		course.setCourseName("Eng 101");
		course.setAssignedProfessor("1");
		course.setIsOffered(true);
		course.setIsMandatory(true); 
		course.setAssignedTA(Arrays.asList("1")); 
		course.setPrerequisites(Arrays.asList("4"));
		courses.add(course); 
		
		// course 2
		course = new Course(); 
		course.setId("2");
		course.setCourseName("Math 101");
		course.setAssignedProfessor("2");
		course.setIsOffered(true);
		course.setIsMandatory(true); 
		course.setAssignedTA(Arrays.asList("2")); 
		course.setPrerequisites(Arrays.asList("5"));
		courses.add(course); 
		
		// course 3
		course = new Course(); 
		course.setId("3");
		course.setCourseName("Soc 101");
		course.setAssignedProfessor("3");
		course.setIsOffered(true);
		course.setIsMandatory(true); 
		course.setAssignedTA(Arrays.asList("3")); 
		course.setPrerequisites(Arrays.asList("6"));
		courses.add(course); 
		
		return courses; 
	}
}

package edu.gatech.project3for6310.mongoManipulations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.IndexOptions;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.entity.TeachingAssistant;
import edu.gatech.project3for6310.entity.User;
import edu.gatech.project3for6310.services.SimulationService;
import edu.gatech.project3for6310.utils.ObjectConversion;

import static com.mongodb.client.model.Filters.*;
public class DatabaseOperations {
	private static MongoClient mongoClient =MongoConnection.getInstance().getClient();
	private static MongoDatabase database=mongoClient.getDatabase("6310Project3");
	
	
	public static void main(String[] args){
		database.drop();
		database=mongoClient.getDatabase("6310Project3");
     	loadUsers();
		loadStudents();
		loadCourses();
		loadProfessors();
		loadTeachingAssistants();
		loadSimulationRecord();
		//SimulationService.getInstance().addAdminRequest(""); // added by Monir for testing
	}


	private static void loadSimulationRecord() {
		database.createCollection("simulationrecord");
		MongoCollection<Document> collection=database.getCollection("simulationrecord");
		collection.createIndex(new Document("id",1), new IndexOptions().unique(true));	
	}


	private static void loadProfessors() {
		database.createCollection("professor");
		MongoCollection<Document> collection=database.getCollection("professor");
		collection.createIndex(new Document("id",1), new IndexOptions().unique(true));
		String professorfile="resources/custormized/instructor2016Summer.csv";
		try{ 
		    BufferedReader br = new BufferedReader(new FileReader(professorfile));
		     String line = br.readLine();
		     while((line=br.readLine())!=null)
		     {
		    	 String[] professor=line.split(",");
		    	 Professor newProfessor= new Professor();
		    	 newProfessor.setId(professor[0]);
		    	 String available = professor[1];
		    	 if (available.equals("0"))
		    	 {
		    		 newProfessor.setAvailable(false);
		    	 } else {
		    		 newProfessor.setAvailable(true);
		    	 }
		    	 String assignedCourse = professor[2];
		    	 //3/4/5
		    	 List<String> cs= null;
		    	 if (!assignedCourse.equals("0"))
		    	 {
		    		 cs= new ArrayList<String>();
		    		 String[] courses = assignedCourse.split("/");
		    		 cs.addAll(Arrays.asList(courses));
		    	 }
		    	 newProfessor.setCourseAssigned(cs);
		    	 List<String> capableCourses = new ArrayList<String>();
		    	 for (int i=3; i< professor.length; i++)
		    	 {
		    		 capableCourses.add(professor[i]);
		    	 }
		    	 
		    	 newProfessor.setCapableCourses(capableCourses);
		    	 Document doc= ObjectConversion.professorToDocument(newProfessor);
		    	 collection.insertOne(doc);
		     }	  
		     br.close();
		} catch(IOException e)
		{
			e.printStackTrace();
		}	
		
		
	}


	private static void loadTeachingAssistants() {
		database.createCollection("teachingassistant");
		MongoCollection<Document> collection=database.getCollection("teachingassistant");
		collection.createIndex(new Document("id",1), new IndexOptions().unique(true));
		String teachingAssistantfile="resources/custormized/TApool2016Summer.csv";
		try{ 
		    BufferedReader br = new BufferedReader(new FileReader(teachingAssistantfile));
		     String line = br.readLine();
		     while((line=br.readLine())!=null)
		     {
		    	 String[] teachingAssistant=line.split(",");
		    	 TeachingAssistant newTeachingAssistant= new TeachingAssistant();
		    	 newTeachingAssistant.setId(teachingAssistant[0]);
		    	 String available = teachingAssistant[1];
		    	 if (available.equals("0"))
		    	 {
		    		 newTeachingAssistant.setAvailable(false);
		    	 } else {
		    		 newTeachingAssistant.setAvailable(true);
		    	 }
		    	 List<String> capableCourses = new ArrayList<String>();
		    	 for (int i=2; i< teachingAssistant.length; i++)
		    	 {
		    		 capableCourses.add(teachingAssistant[i]);
		    	 }
		    	 newTeachingAssistant.setCapableCourses(capableCourses);
		    	 Document doc= ObjectConversion.teachingAssistantToDocument(newTeachingAssistant);
		    	 collection.insertOne(doc);
		     }	  
		     br.close();
		} catch(IOException e)
		{
			e.printStackTrace();
		}	
		
	}


	private static void loadCourses() {
		database.createCollection("course");
		MongoCollection<Document> collection=database.getCollection("course");
		collection.createIndex(new Document("id",1), new IndexOptions().unique(true));
		Map<String,List<String>> prerequisiteMap = new HashMap<String,List<String>>();
		String coursefile="resources/custormized/courses2016Summer.csv";
		String prerequisitesfile="resources/custormized/course_dependencies2016Summer.csv";
		try{ 
			BufferedReader br = new BufferedReader(new FileReader(prerequisitesfile));
		     String line = br.readLine();
		     while((line=br.readLine())!=null)
		     {
		    	 String[] str= line.split(",");
		    	 String dependent = str[1];
		    	 String prereq= str[0];
		    	 if (prerequisiteMap.containsKey(dependent))
		    	 {
		    		 prerequisiteMap.get(dependent).add(prereq);
		    	 } else {
		    		 List<String> p= new ArrayList<String>();
		    		 p.add(prereq);
		    	     prerequisiteMap.put(dependent, p);
		    	 }
		     }
		     br.close();
		     br = new BufferedReader(new FileReader(coursefile));
		      line = br.readLine();
		     while((line=br.readLine())!=null)
		     {
		    	 String[] course=line.split(",");
		    	 Course newCourse= new Course();
		    	 String id= course[0];
		    	 newCourse.setId(id);
		    	 newCourse.setCourseName(course[1]);
		    	 String isMandatory = course[2];
		    	 if (isMandatory.equals("0"))
		    	 {
		    		 newCourse.setIsMandatory(false);
		    	 } else {
		    		 newCourse.setIsMandatory(true);
		    	 }
		    	 newCourse.setPrerequisites(prerequisiteMap.get(id));
		    	 Document doc= ObjectConversion.courseToDocument(newCourse);
		    	 collection.insertOne(doc);
		     }	  
		     br.close();
		} catch(IOException e)
		{
			e.printStackTrace();
		}	
		
	}


	private static void loadStudents() {
		database.createCollection("student");
		MongoCollection<Document> userCollection=database.getCollection("student");
		userCollection.createIndex(new Document("id",1), new IndexOptions().unique(true));
		Map<String,List<String>> requestMap = new HashMap<String,List<String>>();
		String studentfile="resources/custormized/studentrecords2016Summer.csv";
		String requestfile="resources/custormized/studentdemand2016Summer.csv";
		try{
			BufferedReader br = new BufferedReader(new FileReader(requestfile));
			String line = br.readLine();
			String processedId= null;
			List<String> courses= null;
		    while ((line=br.readLine())!=null)
		    {
		      String[] str=line.split(",");
		      String id = str[0];
		      if (!id.equals(processedId))
		      {
		    	  if (processedId !=null)
		    	  {
		    	  requestMap.put(processedId, courses); 
		    	  }
		    	  processedId = id;
		    	  courses= new ArrayList<String>();
		      } 
		      String courseid= str[1];
		      courses.add(courseid); 

		    }
		    requestMap.put(processedId, courses); 
		    br.close();
			 br = new BufferedReader(new FileReader(studentfile));
		     line = br.readLine();
		     while((line=br.readLine())!=null)
		     {
		    	 String[] student=line.split(",");
		    	 Student newStudent= new Student();
		    	 String id= student[0];
		    	 newStudent.setId(student[0]);
		    	 newStudent.setFullName(student[1]+" "+student[2]);
		    	 List<String> coursesTaken= new ArrayList<String>();
		    	 for (int i=3; i<student.length; i++)
		    	 {
		    		 coursesTaken.add(student[i]);
		    	 }
		    	 newStudent.setCourseTaken(coursesTaken);  	 
		    	 newStudent.setPreferredCourses(requestMap.get(id));
		    	 newStudent.setNumDesiredCourse(2);
		    	 Document doc= ObjectConversion.studentToDocument(newStudent);
		    	 userCollection.insertOne(doc);
		    	 
		     }	
		     br.close();
		} catch(IOException e)
		{
			e.printStackTrace();
		}	
		
	}


	private static void loadUsers() {
		database.createCollection("user");
		MongoCollection<Document> userCollection=database.getCollection("user");
		userCollection.createIndex(new Document("id",1), new IndexOptions().unique(true));
		userCollection.createIndex(new Document("username",1),new IndexOptions().unique(true));	
		String rolefile="resources/custormized/roles2016Summer.csv";
		String userfile="resources/custormized/users2016Summer.csv";
		Map<String, String> roleMap = new HashMap<String,String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(rolefile));
		    String line = br.readLine();
		    while ((line=br.readLine())!=null)
		    {
		      String[] role=line.split(",");
		      String roleId = role[0];
		      String roleName= role[1];
		      roleMap.put(roleId, roleName);     
		    }
		    br.close();
		     br = new BufferedReader( new FileReader(userfile));
		     line=br.readLine();
		     while((line=br.readLine())!=null)
		     {
		    	 String[] user=line.split(",");
		    	 User newUser= new User();
		    	 newUser.setId(user[0]);
		    	 newUser.setFullname(user[1]+" "+user[2]);
		    	 newUser.setUsername(user[3]);
		    	 newUser.setPassword(user[4]);
		    	 String role= roleMap.get(user[5]);
		    	 newUser.setRole(role);  	 
		    	 if (role.equals("Admin"))
		    	 {
		    		 newUser.setIsAdmin(true);
		    	 } else {
		    		 newUser.setIsAdmin(false);
		    	 }
		    	 Document userDoc= ObjectConversion.userToDocument(newUser);
		    	 userCollection.insertOne(userDoc);
		     }
		     br.close();
		} catch(IOException e)
		{
			e.printStackTrace();
		}	
	}
}

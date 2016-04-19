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
import java.util.Map.Entry;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.entity.SimulationRecord;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.entity.TeachingAssistant;




import edu.gatech.project3for6310.scheduling.Scheduler;
import edu.gatech.project3for6310.utils.Helper;

public class DatabaseOperationsMonir {
	/*
	private static MongoClient mongoClient =MongoConnection.getInstance().getClient(); ;
	private static MongoDatabase database=mongoClient.getDatabase("6310Project3");;
	private static MongoCollection<Document> userCollection=database.getCollection("user");
	private static MongoCollection<Document> studentCollection=database.getCollection("student");
	*/
	
	public static void main(String[] argss){
		
		/*userCollection.insertOne(new Document().append("username", "fyang3")		
				.append("password","1234")
				.append("fullname", "Feng Yang")
				.append("role","student")
				.append("isAdmin",false)
				.append("studentID","04567")); */
		
		// added by Monir (comment lines 24-27 and 411-52 and uncomment the line below to test Monir's code)
		testScheduler(); 
		// end of added by Monir
		
		/*
		List<String> li = new ArrayList<String>();
		li.add("6550"); 
		studentCollection.deleteMany(eq("id","04568"));
		studentCollection.insertOne(new Document().append("id", "04568")		
				.append("numDesiredCourse", 1)
				.append("preferredCources", new Document().append("6550", 1).append("6250", 2))
				.append("requestId","2")
				.append("rcmCources",li)
				.append("isSimulated",false)); 
		Document doc = studentCollection.find(eq("id","04568")).first();
		Student stu = ObjectConversion.documentToStudent(doc);
		System.out.println(stu.getId());
		*/
	}
	
	// method added by Monir
	private static void testScheduler(){
		SimulationRecord simRecord; 
		Scheduler scheduler; 
		Helper helper; 
		List<Student> students; 
		List<Course> courses; 
		List<Professor> professors; 
		List<TeachingAssistant> teachingAssistants; 
		Map<String, List<String>> studentRecommendations; 
		Map<String, List<String>> professorRecommendations; 
		Map<String, List<String>> taRecommendations; 
		
		helper = Helper.getInstance(); 
		students = helper.getStudents(); 
		professors = helper.getProfessors(); 
		teachingAssistants = helper.getTeachingAssistants(); 
		courses = helper.getCourses(); 
		
		scheduler = Scheduler.getInstance(); 
		try { 
			simRecord = scheduler.getSimulationResult(students, courses, professors, teachingAssistants); 
			if(simRecord != null){
				studentRecommendations = simRecord.getStudentRecommendation(); 
				System.out.println("\n************** Student Recommendations **************");
				for(Entry<String, List<String>> studentEntry : studentRecommendations.entrySet()){
					System.out.println("[Student With ID: " + studentEntry.getKey() + 
							"] Takes Courses: [" + String.join(",", studentEntry.getValue()) + "]");
				}
				
				professorRecommendations = simRecord.getProfessorAssignment(); 
				System.out.println("\n************** Professor Recommendations **************"); 
				for(Entry<String, List<String>> professorEntry : professorRecommendations.entrySet()){
					System.out.println("[Professor With ID: " + professorEntry.getKey() +
							"] Teaches Courses: [" + String.join(",", professorEntry.getValue()) + "]"); 
				}
				
				taRecommendations = simRecord.getTaAssignment(); 
				System.out.println("\n************** TA Recommendations *****************"); 
				for(Entry<String, List<String>> taEntry : taRecommendations.entrySet()){
					System.out.println("[TA With ID: " + taEntry.getKey() +
							"] Teaches Courses: [" + String.join(",", taEntry.getValue()) + "]"); 
				}
			}else{
				System.out.println("SimulationRecord return null!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}




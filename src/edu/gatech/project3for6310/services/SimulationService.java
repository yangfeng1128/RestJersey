package edu.gatech.project3for6310.services;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.Engine.Engine;
import edu.gatech.project3for6310.Engine.SimulationEngine;
import edu.gatech.project3for6310.dao.BasicDAO;
import edu.gatech.project3for6310.dao.CourseDAO;
import edu.gatech.project3for6310.dao.ProfessorDAO;
import edu.gatech.project3for6310.dao.SimulationRecordDAO;
import edu.gatech.project3for6310.dao.StudentDAO;
import edu.gatech.project3for6310.dao.TeachingAssistantDAO;
import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.entity.SimulationRecord;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.entity.TeachingAssistant;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class SimulationService {
	
	private static SimulationService simulation;
	private static final LinkedBlockingQueue<String> studentModeQueue = new LinkedBlockingQueue<String>();
	private static final LinkedBlockingQueue<String> adminModeQueue = new LinkedBlockingQueue<String>();
	private static Engine studentEngine= new SimulationEngine();
	private static Thread thread;
	
	private SimulationService()
	{
		start();
	}
	
	public static synchronized SimulationService getInstance()
	{
		if (simulation == null)
		{
			simulation = new SimulationService();
		}
		return simulation;
	}
	
	public void addStudentRequest(String requestId)
	{
		studentModeQueue.add(requestId);
	}

	// if a new request comes in, and the simulation thread is running, the request will be processed in
	// the alive thread. If the thread is dead, start a new thread.
	public static void start() {
		if (thread !=null && thread.isAlive())
		{
			return;
		} else {
			Runnable task= new Simulate();
			thread= new Thread(task);
			thread.start();	
		}
			
	}

	
	public void addAdminRequest(String requestId) {
		
		adminModeQueue.add(requestId);
	}
	private static class Simulate implements Runnable
	{
		private static MongoClient mongoClient; 
		private static MongoDatabase database;		
		private static List<Student> students;
		private static List<Course> courses;
		private static List<Professor> professors;
		private static List<TeachingAssistant> teachingassistants;

		public Simulate(){
			String host = System.getenv("MONGO_HOST");
	        if (host == "") {
	            host = "localhost";
	        }

	        mongoClient = new MongoClient(host, 27017);
			database= mongoClient.getDatabase("6310Project3");
	
		}
		@Override
		public void run() {
			boolean finished = false;
			while(!finished)
			{
				List<String> studentrequests =new ArrayList<String>();
				List<String> adminrequests = new ArrayList<String>();
				studentModeQueue.drainTo(studentrequests);
				adminModeQueue.drainTo(adminrequests);
				createSimulationObjects(studentrequests,adminrequests);
				SimulationRecord sr = studentEngine.getSimulationResult(students, courses, professors, teachingassistants);
				updateDatabase(studentrequests,sr);
			  	
			  if 	(studentModeQueue.size() ==0 && adminModeQueue.size()==0)
			  {
				  finished=true;
				  mongoClient.close();
			  }
			}
			
		}
		private static void updateDatabase(List<String>studentrequests, SimulationRecord sr) {
			if (sr==null) return;
			 Map<String, List<String>> requestMap=sr.getStudentPreference();
			 Map<String, List<String>> courseRecommended=sr.getCourseRecommended();
			 Map<String, String> professorAssignment=sr.getProfessorAssignment();
			 Map<String, List<String>> tAAssignment=sr.getTAAssignment();
			 
			 
			MongoCollection<Document> simulationrecorddao= database.getCollection("simulationrecord");
			Document srDoc= ObjectConversion.simulationRecordToDocument(sr);
			simulationrecorddao.insertOne(srDoc);
			 
			 /*
			 Map<String, String> requestMap = new HashMap<String,String>();
			 for (String s:studentrequests)
			 {
				 String[] st=s.split("_");
				 String id= st[1];
				 requestMap.put(id, s);
			 }
			 */
			MongoCollection<Document> studentdao=database.getCollection("student");
			MongoCollection<Document> coursedao=database.getCollection("course");
			MongoCollection<Document> professordao=database.getCollection("professor");
			MongoCollection<Document> teachingassistantdao=database.getCollection("teachingassistant");
			 for (Entry<String, List<String>> entry:courseRecommended.entrySet())
			 {
				 String id= entry.getKey();
				 List<String> courses = entry.getValue();
				 Document sDoc=studentdao.find(eq("id", id)).first();
				 sDoc.put("rcmCources", courses);
				 String request=sDoc.getString("preferredCources");
				 if (request.equals(requestMap.get(id)))
				 {
					 sDoc.put("isSimulated", true);
				 }
				 studentdao.replaceOne(eq("id",id),sDoc);
			 }
			 
			 for (Entry<String, String> entry:professorAssignment.entrySet())
			 {
				 String courseid= entry.getKey();
				 String professorid = entry.getKey();
				 Document cDoc=coursedao.find(eq("id", courseid)).first();
				 cDoc.put("assignedProfessor", professorid);
				 cDoc.put("assignedTA", tAAssignment.get(courseid));
				 coursedao.replaceOne(eq("id",courseid), cDoc);
			 }
			 
			 
			 for (Entry<String, String> entry:professorAssignment.entrySet())
			 {
				 String courseid= entry.getKey();
				 String professorid = entry.getKey();
				 Document pDoc=professordao.find(eq("id", professorid)).first();
				 List<String> courseAssigned=(List<String>) pDoc.get("courseAssigned");
				 if (courseAssigned == null)
				 {
					 courseAssigned= new ArrayList<String>(); 
				 }
				 courseAssigned.add(courseid);
				 pDoc.put("courseAssigned", courseAssigned);
				 professordao.replaceOne(eq("id",professorid), pDoc);
			 }
		
			 for (Entry<String, List<String>> entry:tAAssignment.entrySet())
			 {
				 String courseid= entry.getKey();
				 for(String id:entry.getValue())
				 {
					 Document tDoc=teachingassistantdao.find(eq("id", id)).first();
					 List<String> courseAssigned=(List<String>) tDoc.get("courseAssigned");
					 if (courseAssigned == null)
					 {
						 courseAssigned= new ArrayList<String>(); 
					 }
					 courseAssigned.add(courseid);
					 tDoc.put("courseAssigned", courseAssigned);
					 teachingassistantdao.replaceOne(eq("id",id), tDoc);
				 }
			 }
			 
			
		}


		private static void createSimulationObjects(List<String> studentrequests, List<String> adminrequests) {
		        studentrequests=null;
		        adminrequests=null;
				students=new ArrayList<Student>();
				MongoCollection<Document> studentdao=database.getCollection("student");
				MongoCursor<Document> docs=studentdao.find().iterator();
				while (docs.hasNext())
				{
					Student s= ObjectConversion.documentToStudent(docs.next());
					students.add(s);
				}
				
				MongoCollection<Document> coursedao=database.getCollection("course");
				courses=new ArrayList<Course>();
				MongoCursor<Document> docC=coursedao.find().iterator();
				while (docC.hasNext())
				{
					Course c= ObjectConversion.documentToCourse(docC.next());
					courses.add(c);
				}

				MongoCollection<Document> professordao=database.getCollection("professor");
				professors=new ArrayList<Professor>();
				MongoCursor<Document> docsP=professordao.find().iterator();
				while (docsP.hasNext())
				{
					Professor p= ObjectConversion.documentToProfessor(docsP.next());
					professors.add(p);
				}
			
				MongoCollection<Document> teachingassistantdao=database.getCollection("teachingassistant");
				teachingassistants=new ArrayList<TeachingAssistant>();
				MongoCursor<Document> docsT=teachingassistantdao.find().iterator();
				while(docsT.hasNext())
				{
					TeachingAssistant t= ObjectConversion.documentToTeachingAssistant(docsT.next());
					teachingassistants.add(t);
				}
			
			/*
			if (adminrequests.size()!=0)
			{
				courses=new ArrayList<Course>();
				List<Document> docs=coursedao.getAllCourses();
				for (Document doc: docs)
				{
					Course obj= ObjectConversion.documentToCourse(doc);
					courses.add(obj);
				}
				professors=new ArrayList<Professor>();
				List<Document> docsP=professordao.getAllProfessors();
				for (Document doc: docsP)
				{
					Professor obj= ObjectConversion.documentToProfessor(doc);
					professors.add(obj);
				}
				teachingassistants=new ArrayList<TeachingAssistant>();
				List<Document> docsT=teachingassistantdao.getAllTeachingAssistants();
				for (Document doc: docsT)
				{
					TeachingAssistant obj= ObjectConversion.documentToTeachingAssistant(doc);
					teachingassistants.add(obj);
				}
			}
			if (studentrequests.size()!=0)
			{
				Map<String,Student> stuMap= new HashMap<String,Student>();
				for (Student s:students)
				{
					stuMap.put(s.getId(), s);
				}
				for (String r:studentrequests)
				{
					String[] rq= r.split("_");
					String id= rq[1];
					Document doc=studentdao.getOneStudent(id);
					Student stu=ObjectConversion.documentToStudent(doc);
					stuMap.put(id, stu);
				}
				students=new ArrayList<Student>();
				students.addAll(stuMap.values());
			}*/
			
			
		}

		
	}

}

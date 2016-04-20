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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


import edu.gatech.project3for6310.Engine.Engine;
import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.entity.SimulationRecord;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.entity.TeachingAssistant;
import edu.gatech.project3for6310.scheduling.Scheduler;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class SimulationService {
	
	private static SimulationService simulation;
	private static final LinkedBlockingQueue<String> studentModeQueue = new LinkedBlockingQueue<String>();
	private static final LinkedBlockingQueue<String> adminModeQueue = new LinkedBlockingQueue<String>();
	private static Engine studentEngine= Scheduler.getInstance();
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
			if (sr==null)return;
			 Map<String, List<String>> requestMap=sr.getStudentPreference();
			 Map<String, List<String>> courseRecommended=sr.getStudentPreference();
			 Map<String, List<String>> professorAssignment=sr.getProfessorAssignment();
			 Map<String, List<String>> tAAssignment=sr.getTaAssignment();
			 
			 
			MongoCollection<Document> simulationrecorddao= database.getCollection("simulationrecord");
			long count=simulationrecorddao.count();
			count++;	
	        sr.setId(String.valueOf(count));
			
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
			Map<String,String> courseProfMap = new HashMap<String, String>();
			Map<String, List<String>> courseTaMap= new HashMap<String, List<String>>();
			 for (Entry<String, List<String>> entry:courseRecommended.entrySet())
			 {
				 String id= entry.getKey();
				 List<String> cs = entry.getValue();
				 Document sDoc=studentdao.find(eq("id", id)).first();
				 sDoc.put("rcmCources", cs);
				 List<String> request=(List<String>) sDoc.get("preferredCources");
				 if (hasSameContent(request,requestMap.get(id)))
				 {
					 sDoc.put("isSimulated", true);
				 } else {
					 sDoc.put("isSimulated", false); 
				 }
				 studentdao.replaceOne(eq("id",id),sDoc);
			 }
			 
			 for (Entry<String, List<String>> entry:professorAssignment.entrySet())
			 {
				 String professorid = entry.getKey();
				 List<String> courseids= entry.getValue();	
				 Document pDoc=professordao.find(eq("id", professorid)).first();
				 pDoc.put("courseAssigned", courseids);
				 professordao.replaceOne(eq("id",professorid), pDoc);
				 for (String c: courseids)
				 {
					 if (!courseProfMap.containsKey(c))
					 {
						 courseProfMap.put(c, professorid);
					 }
				 }
			 }	
			 for (Entry<String, List<String>> entry:tAAssignment.entrySet())
			 {
				 String tAid= entry.getKey();
				 List<String> courseids = entry.getValue();
				 Document tDoc=teachingassistantdao.find(eq("id", tAid)).first();
				 tDoc.put("courseAssigned", courseids);
				 teachingassistantdao.replaceOne(eq("id",tAid), tDoc);
				 for (String ci: courseids)
				 {
					 if (!courseTaMap.containsKey(ci))
					 {
						 List<String> taAssiged= new ArrayList<String>();
						 taAssiged.add(tAid);
						 courseTaMap.put(ci, taAssiged);
						 
					 } else {
						 List<String> taAssiged= courseTaMap.get(ci);
						 taAssiged.add(tAid);
					 }
				 }
			 }
			 MongoCursor<Document> coursesDoc=coursedao.find().iterator();
			 while (coursesDoc.hasNext())
			 {
				 Document cDoc= coursesDoc.next();
				 String id=cDoc.getString("id");
				 if (courseProfMap.containsKey(id))
				 {
					 cDoc.put("isOffered", true);
					 cDoc.put("assignedProfessor", courseProfMap.get(id));
					 cDoc.put("assignedTA", courseTaMap.get(id));
					 coursedao.replaceOne(eq("id",id), cDoc);
				 }
			 }
		}


		private static boolean hasSameContent(List<String> request, List<String> list) {
			if (list == null) return false;
			if (request.size() !=list.size()) return false;
			for (int i=0; i< request.size();i++)
			{
				if (!request.get(i).equals(list.get(i)))
			    {
					return false;
				}
			}
			return true;
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
					Course co= ObjectConversion.documentToCourse(docC.next());
					courses.add(co);
				}

				MongoCollection<Document> professordao=database.getCollection("professor");
				professors=new ArrayList<Professor>();
				MongoCursor<Document> docsP=professordao.find().iterator();
				while (docsP.hasNext())
				{
					Professor p= ObjectConversion.documentToProfessor(docsP.next());
					if(p.getAvailable()==true)
					{
					professors.add(p);
					}
				}
			
				MongoCollection<Document> teachingassistantdao=database.getCollection("teachingassistant");
				teachingassistants=new ArrayList<TeachingAssistant>();
				MongoCursor<Document> docsT=teachingassistantdao.find().iterator();
				while(docsT.hasNext())
				{
					TeachingAssistant t= ObjectConversion.documentToTeachingAssistant(docsT.next());
					if(t.getAvailable()==true)
					{
					teachingassistants.add(t);
					}
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

package edu.gatech.project3for6310.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

import org.bson.Document;

import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.Engine.Engine;
import edu.gatech.project3for6310.dao.CourseDAO;
import edu.gatech.project3for6310.dao.ProfessorDAO;
import edu.gatech.project3for6310.dao.SimulationRecordDAO;
import edu.gatech.project3for6310.dao.StudentDAO;
import edu.gatech.project3for6310.dao.TeachingAssistantDAO;
import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.entity.SimulationRecord;
import edu.gatech.project3for6310.entity.Student;
import edu.gatech.project3for6310.entity.TeachingAssistant;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class SimulationService {
	@Inject
	private static CourseDAO coursedao;
	@Inject
	private static ProfessorDAO professordao;
	@Inject
	private static SimulationRecordDAO simulationrecorddao;
	@Inject
	private static StudentDAO studentdao;
	@Inject
	private static TeachingAssistantDAO teachingassistantdao;
	private static List<Student> students;
	private static List<Course> courses;
	private static List<Professor> professors;
	private static List<TeachingAssistant> teachingassistants;
	
	private static SimulationService simulation;
	private static final LinkedBlockingQueue<String> studentModeQueue = new LinkedBlockingQueue<String>();
	private static final LinkedBlockingQueue<String> adminModeQueue = new LinkedBlockingQueue<String>();
	private static Engine studentEngine;
	
	private SimulationService()
	{
		if (students==null)
		{
			students=new ArrayList<Student>();
			List<Document> docs=studentdao.getAllStudents();
			for (Document doc: docs)
			{
				Student stu= ObjectConversion.documentToStudent(doc);
				students.add(stu);
			}
		}	
		if (courses==null)
		{
			courses=new ArrayList<Course>();
			List<Document> docs=coursedao.getAllCourses();
			for (Document doc: docs)
			{
				Course stu= ObjectConversion.documentToCourse(doc);
				courses.add(stu);
			}
		}	
		if (professors==null)
		{
			professors=new ArrayList<Professor>();
			List<Document> docs=professordao.getAllProfessors();
			for (Document doc: docs)
			{
				Professor stu= ObjectConversion.documentToProfessor(doc);
				professors.add(stu);
			}
		}
		
		if (teachingassistants==null)
		{
			teachingassistants=new ArrayList<TeachingAssistant>();
			List<Document> docs=teachingassistantdao.getAllTeachingAssistants();
			for (Document doc: docs)
			{
				TeachingAssistant stu= ObjectConversion.documentToTeachingAssistant(doc);
				teachingassistants.add(stu);
			}
		}
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

	public static synchronized void start() {
		if (studentModeQueue.size()==0 && adminModeQueue.size()==0)
		{
			return;
		}
		List<String> studentrequests =new ArrayList<String>();
		List<String> adminrequests = new ArrayList<String>();
		studentModeQueue.drainTo(studentrequests);
		adminModeQueue.drainTo(adminrequests);
		createSimulationObjects(studentrequests,adminrequests);
		SimulationRecord sr = studentEngine.getSimulationResult(students, courses, professors, teachingassistants);
		updateDatabase(studentrequests,sr);
		
	}

	private static void updateDatabase(List<String>studentrequests, SimulationRecord sr) {
		 Map<String, List<String>> courseRecommended=sr.getCourseRecommended();
		 Map<String, String> professorAssignment=sr.getProfessorAssignment();
		 Map<String, List<String>> tAAssignment=sr.getTAAssignment();
		 
		 Document srDoc= ObjectConversion.simulationRecordToDocument(sr);
		 simulationrecorddao.create(srDoc);
		 
		 Map<String, String> requestMap = new HashMap<String,String>();
		 for (String s:studentrequests)
		 {
			 String[] st=s.split("_");
			 String id= st[1];
			 requestMap.put(id, s);
		 }
		 for (Entry<String, List<String>> entry:courseRecommended.entrySet())
		 {
			 String id= entry.getKey();
			 List<String> courses = entry.getValue();
			 Document sDoc=studentdao.getOneStudent(id);
			 sDoc.put("rcmCources", courses);
			 String request=sDoc.getString("requestId");
			 if (request.equals(requestMap.get(id)))
			 {
				 sDoc.put("isSimulated", true);
			 }
			 studentdao.updateStudent(id, sDoc);
		 }
		 for (Entry<String, String> entry:professorAssignment.entrySet())
		 {
			 String courseid= entry.getKey();
			 String professorid = entry.getKey();
			 Document cDoc=coursedao.getOneCourse(courseid);
			 cDoc.put("assignedProfessor", professorid);
			 cDoc.put("assignedTA", tAAssignment.get(courseid));
			 coursedao.updateCourse(courseid, cDoc);
			 
			 Document pDoc=professordao.getOneProfessor(professorid);
			 List<String> courseAssigned=(List<String>) pDoc.get("courseAssigned");
			 if (courseAssigned == null)
			 {
				 courseAssigned= new ArrayList<String>(); 
			 }
			 courseAssigned.add(courseid);
			 pDoc.put("courseAssigned", courseAssigned);
			 professordao.updateProfessor(professorid, pDoc);
		 }
		 
		 for (Entry<String, List<String>> entry:tAAssignment.entrySet())
		 {
			 String courseid= entry.getKey();
			 for(String id:entry.getValue())
			 {
				 Document tDoc=teachingassistantdao.getOneTeachingAssistant(id);
				 List<String> courseAssigned=(List<String>) tDoc.get("courseAssigned");
				 if (courseAssigned == null)
				 {
					 courseAssigned= new ArrayList<String>(); 
				 }
				 courseAssigned.add(courseid);
				 tDoc.put("courseAssigned", courseAssigned);
				 teachingassistantdao.updateTeachingAssistant(id, tDoc);
			 }
		 }
		 
		
	}


	private static void createSimulationObjects(List<String> studentrequests, List<String> adminrequests) {
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
		}
		
	}

	public void addAdminRequest(String requestId) {
		
		adminModeQueue.add(requestId);
	}

}

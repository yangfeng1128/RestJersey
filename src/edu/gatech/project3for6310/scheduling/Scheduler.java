/**
 * 
 */
package edu.gatech.project3for6310.scheduling;
import edu.gatech.project3for6310.Engine.Engine;
import edu.gatech.project3for6310.entity.*;
import edu.gatech.project3for6310.utils.*;
import java.util.*;
import java.util.Map.Entry;

import gurobi.*;

/**
 * @author monir
 *
 */
public class Scheduler implements Engine{
	
	final static int STUDENTS_PER_PROFESSOR = 2; 
	final static int STUDENTS_PER_TA = 2; 
	final static double STUDENT_WEIGHT = 200.0; 
	
	private static Scheduler singletonScheduler = new Scheduler(); 
	private Map<Integer, Student> studentMap; 
	private Map<Integer, Professor> professorMap; 
	private Map<Integer, TeachingAssistant> teachingAssistantMap;  
	private Map<Integer, Course> courseMap; 
	private GRBModel model;
	private GRBVar[][] studentCourseVars;
	private GRBVar[][] courseProfessorVars;
	private GRBVar[][] courseTaVars; 
	private GRBVar x; 
	
	private Scheduler(){}
	
	public static Scheduler getInstance(){
		return singletonScheduler; 
	}
	
	public SimulationRecord getSimulationResult(List<Student> students,List<Course> courses, 
						List<Professor> professors, List<TeachingAssistant> teachingAssistants){
		GRBEnv env; 
		GRBLinExpr expr; 
		SimulationRecord simRecord; 
		
		convertListsToMaps(students, courses, professors, teachingAssistants); 
		try{
			env = new GRBEnv(); 
			env.set(GRB.IntParam.LogToConsole, 0);
			model = new GRBModel(env); 
			
			// create Gurobi variables
			createVariables(); 							
			// integrate new variables
			model.update(); 							
			// set objective: maximize x
			expr = new GRBLinExpr(); 					
			expr.addTerm(1.0, x); 
			model.setObjective(expr, GRB.MAXIMIZE); 
			
			addStudentConstraints(); 
			addCourseConstraints(); 
			addProfessorConstraints(); 
			addTAConstraints(); 
			addOptimizationConstraint();

			//na(); 
			
			// Optimize the model
			model.optimize(); 
			simRecord = getSimulation(); 
			model.write("yourFile.sol"); 
			model.write("yourFile.lp"); 
			//return (float) model.get(GRB.DoubleAttr.ObjVal); 
			return simRecord; 
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return null; 
		}	
	}
	
	private SimulationRecord getSimulation()throws Exception{
		double[][] studentCourseResults;
		double[][] professorCourseResults;
		double[][] taCourseResults; 
		
		SimulationRecord simRecord; 
		Map<String, List<String>> studentPreference; 
		Map<String, List<String>> studentRecommendation; 
		Map<String, List<String>> professorAssignment; 
		Map<String, List<String>> taAssignment; 
		
		studentPreference = new HashMap<String, List<String>>(); 
		studentRecommendation = new HashMap<String, List<String>>(); 
		professorAssignment = new HashMap<String, List<String>>(); 
		taAssignment = new HashMap<String, List<String>>(); 
		
		simRecord = new SimulationRecord(); 
		Student student; 
		Course course; 
		Professor professor; 
		TeachingAssistant ta; 
		int studentCount; 
		int courseCount; 
		int profCount; 
		int taCount; 
		
		studentCount = studentMap.size(); 
		courseCount = courseMap.size(); 
		profCount = professorMap.size(); 
		taCount = teachingAssistantMap.size(); 
		
		try {
			studentCourseResults = model.get(GRB.DoubleAttr.X, studentCourseVars);
			for(int i=0; i<studentCount; i++){
				student = studentMap.get(i);
				studentPreference.put(student.getId(), student.getPreferredCourses()); 
				studentRecommendation.put(student.getId(), new ArrayList<String>()); 
				for(int j=0; j<courseCount; j++){
					if(studentCourseResults[i][j] > 0){
						course = courseMap.get(j); 
						studentRecommendation.get(student.getId()).add(course.getId()); 
					}
				}
			}
			
			professorCourseResults = model.get(GRB.DoubleAttr.X, courseProfessorVars);
			for(int i=0; i<profCount; i++){
				professor = professorMap.get(i); 
				professorAssignment.put(professor.getId(), new ArrayList<String>()); 
				for(int j=0; j<courseCount; j++){
					if(professorCourseResults[j][i] > 0){
						course = courseMap.get(j); 
						professorAssignment.get(professor.getId()).add(course.getId()); 
					}
				}
			}
			
			taCourseResults = model.get(GRB.DoubleAttr.X, courseTaVars);
			for(int i=0; i<taCount; i++){
				ta = teachingAssistantMap.get(i); 
				taAssignment.put(ta.getId(), new ArrayList<String>()); 
				for(int j=0; j<courseCount; j++){
					if(taCourseResults[j][i] > 0){
						course = courseMap.get(j); 
						taAssignment.get(ta.getId()).add(course.getId()); 
					}
				}
			}
		} catch (GRBException ex) {
			throw new Exception(ex.getMessage()); 
		}
		
		simRecord.setStudentPreference(studentPreference); 
		simRecord.setStudentRecommendation(studentRecommendation);
		simRecord.setProfessorAssignment(professorAssignment);
		simRecord.setTaAssignment(taAssignment);
		
		return simRecord; 
	}
	
	/**
	 * adds the student constraints
	 * @throws Exception
	 */
	private void addStudentConstraints()throws Exception{
		try{
			addStudentNotMetPrereqConstraint(); 
			addSudentMaxCourseLimitConstraint(); 
		}catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}
	
	/**
	 * adds the course constraints
	 * @throws Exception
	 */
	private void addCourseConstraints()throws Exception{
		try{
			addCourseMaxProfessorLimitConstraint(); 
			addStudentsPerProfessorLimitConstraint(); 
			addStudentsPerTAsLimitConstraint();
			/*
			 * maybe the list of courses can be changed to be only the 
			 * courses that are offered, so I don't have to worry
			 * about this constraint
			 */
			//addCoursesNotOfferedConstraint(); 
		}catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}
	
	private void addProfessorConstraints()throws Exception{
		try{
			addProfessorCannotTeachCourseConstraint(); 
		}
		catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}
	
	private void addTAConstraints()throws Exception{
		try{
			addTACannotTeachCourseConstraint(); 
		}
		catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}
	
	/**
	 * create Gurobi variables
	 * 
	 * @throws Exception
	 */
	private void createVariables()throws Exception{
		int studentCount;
		int courseCount; 
		int professorCount; 
		int taCount; 
		
		studentCount = studentMap.size(); 
		courseCount = courseMap.size(); 
		professorCount = professorMap.size(); 
		taCount = teachingAssistantMap.size(); 
		
		studentCourseVars = new GRBVar[studentCount][courseCount]; 
		courseProfessorVars = new GRBVar[courseCount][professorCount];
		courseTaVars = new GRBVar[courseCount][taCount]; 
		
		try{
			x = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "x"); 
			// create student-course variables 
			for(int i : studentMap.keySet()){
				for(int j : courseMap.keySet())
					studentCourseVars[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "S" + i + "_C" + j);
			}
			// create course-professor variables
			for(int j : courseMap.keySet()){
				for(int k : professorMap.keySet())
					courseProfessorVars[j][k] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "C" + j + "_P" + k);
			}
			// create course-TA variables
			for(int j : courseMap.keySet()){
				for(int l : teachingAssistantMap.keySet()){
					courseTaVars[j][l] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "C" + j + "_TA" + l);
				}
			}
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
	}
	
	/**
	 * create an expression which is the sum of all the student and 
	 * the courses that they don't meet the prerequisites for and 
	 * create a constraint by setting it to zero
	 * @throws Exception
	 */
	private void addStudentNotMetPrereqConstraint()throws Exception{
		GRBLinExpr expr;
		Student student; 
		Course course; 
		int sKey; 
		int cKey;
		
		try{
			expr = new GRBLinExpr();
			for(Entry<Integer, Student> studentEntry : studentMap.entrySet()){
				sKey = studentEntry.getKey(); 
				student = studentEntry.getValue(); 
				for(Entry<Integer, Course> courseEntry : courseMap.entrySet()){
					cKey = courseEntry.getKey(); 
					course = courseEntry.getValue(); 
					// skip if this course is not offered
					if(!course.getIsOffered())
						continue;
					if(!student.meetsPrerequisites(course))
						expr.addTerm(1, studentCourseVars[sKey][cKey]);
				}
			}
			model.addConstr(expr, GRB.EQUAL, 0, "studentsNotMetPrerequistesConstraint"); 
		}catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}
	
	/**
	 * for every student create an expression which is the sum of the courses 
	 * that the student will take and use that expression to create a constraint
	 * limiting the number of courses the student can take
	 * @throws Exception
	 */
	private void addSudentMaxCourseLimitConstraint()throws Exception{
		GRBLinExpr expr;
		Student student; 
		Course course; 
		int sKey; 
		int cKey; 
		
		try{
			for(Entry<Integer, Student> studentEntry : studentMap.entrySet()){
				expr = new GRBLinExpr();
				sKey = studentEntry.getKey(); 
				student = studentEntry.getValue(); 
				for(Entry<Integer, Course> courseEntry : courseMap.entrySet()){
					cKey = courseEntry.getKey(); 
					course = courseEntry.getValue(); 
					// skip if this course is not offered
					if(!course.getIsOffered())
						continue;
					// skip if student doesn't meet the prerequisites for this course
					if(!student.meetsPrerequisites(course))
						continue;
					expr.addTerm(1, studentCourseVars[sKey][cKey]);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, student.getCourseLimit(), "studentMaxCourseLimit_S" + sKey); 
			}
		}catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}
	
	/**
	 * for every course create an expression which is the sum of the professors
	 * that the course will be taught by and use that expression to create a
	 * constraint limiting the number of professors teaching that course
	 * @throws Exception
	 */
	private void addCourseMaxProfessorLimitConstraint()throws Exception{
		GRBLinExpr expr;
		Course course;
		Professor professor;
		int cKey; 
		int pKey; 
		
		try{
			for(Entry<Integer, Course> courseEntry : courseMap.entrySet()){
				cKey = courseEntry.getKey(); 
				course = courseEntry.getValue();
				// skip if this course is not being offered
				if(!course.getIsOffered())
					continue;
				expr = new GRBLinExpr();
				for(Entry<Integer, Professor> profEntry : professorMap.entrySet()){
					pKey = profEntry.getKey(); 
					professor = profEntry.getValue(); 
					// skip if this professor can't teach this class
					if(!professor.canTeach(course))
						continue; 
					expr.addTerm(1, courseProfessorVars[cKey][pKey]);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, 1, "courseMaxProfLimit_C" + cKey); 
			}
		}catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}
	
	/**
	 * we will add two constraints in this method
	 * 1.) 	the first constraint will make sure that the sum of the students
	 * 		taking a class will be less than or equal to the number of students
	 * 		a professor can teach which also ensures that there is a professor
	 * 		when there is at least one student
	 * 2.) 	the second constraint will make sure that no professor is teaching 
	 * 		a class if there are no students taking that class
	 * @throws Exception
	 */
	private void addStudentsPerProfessorLimitConstraint()throws Exception{
		GRBLinExpr expr;		// the sum of all students taking a course
		GRBLinExpr profExpr1;	// the sum of all professors teaching a course times the # of students each professor can teach
		GRBLinExpr profExpr2; 	// the sum of all professors teaching a course
		Student student; 
		Course course;
		Professor professor;
		int sKey; 
		int cKey; 
		int pKey; 
		
		try{
			for(Entry<Integer, Course> courseEntry : courseMap.entrySet()){
				cKey = courseEntry.getKey(); 
				course = courseEntry.getValue(); 
				// skip if this course is not offered
				if(!course.getIsOffered())
					continue; 
				// the following expression is the sum of this course and all students pairs
				expr = new GRBLinExpr(); 
				for(Entry<Integer, Student> studentEntry : studentMap.entrySet()){
					sKey = studentEntry.getKey(); 
					student = studentEntry.getValue(); 
					// skip if student doesn't meet the prerequisites for this course
					if(!student.meetsPrerequisites(course))
						continue;
					expr.addTerm(1, studentCourseVars[sKey][cKey]); 
				}
				profExpr1 = new GRBLinExpr();
				profExpr2 = new GRBLinExpr(); 
				
				for(Entry<Integer, Professor> profEntry : professorMap.entrySet()){
					pKey = profEntry.getKey(); 
					professor = profEntry.getValue(); 
					// skip if this professor can't teach this class
					if(!professor.canTeach(course))
						continue;
					profExpr1.addTerm(STUDENTS_PER_PROFESSOR, courseProfessorVars[cKey][pKey]);  
					profExpr2.addTerm(1.0, courseProfessorVars[cKey][pKey]);
				}
				// the sum of students taking a course has to be less than or equal to 
				// the number of professors teaching that course times the # of students
				// that each professor can teach
				model.addConstr(expr, GRB.LESS_EQUAL, profExpr1, "studentsPerProfessorLimit_C" + cKey);
				// the number of professors teaching a course has to be less than or equal to 
				// the number of students taking that course
				model.addConstr(profExpr2, GRB.LESS_EQUAL, expr, "coursesLessThanStudents_C" + cKey); 
			}
		}catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}

	/**
	 * we will add two constraints in this method
	 * 1.)		the first constraint will ensure that the sum of the students
	 * 			taking a course will be less than or equal to the number of TAs
	 * 			in that course times the number of students each TA can teach, which
	 * 			also ensures that there are enough TAs per group of students
	 * 2.) 		the second constraint will ensure that no TA is teaching a class
	 * 			if there are no students taking that class
	 * @throws Exception
	 */
	private void addStudentsPerTAsLimitConstraint()throws Exception{
		GRBLinExpr studentExpr1;
		GRBLinExpr studentExpr2; 
		GRBLinExpr taExpr1; 	// the sum of all TAs teaching a course times the # of students a TA can teach
		GRBLinExpr taExpr2; 	// the sum of all TAs teaching a course divided by the # of students a TA can teach
		Student student; 
		Course course;
		TeachingAssistant ta;
		int sKey; 
		int cKey; 
		int taKey; 
		
		try{
			for(Entry<Integer, Course> courseEntry : courseMap.entrySet()){
				cKey = courseEntry.getKey(); 
				course = courseEntry.getValue(); 
				// skip if this course is not offered
				if(!course.getIsOffered())
					continue; 
				// the following expression is the sum of this course and all students pairs
				studentExpr1 = new GRBLinExpr(); 
				studentExpr2 = new GRBLinExpr(); 
				
				for(Entry<Integer, Student> studentEntry : studentMap.entrySet()){
					sKey = studentEntry.getKey(); 
					student = studentEntry.getValue(); 
					// skip if student doesn't meet the prerequisites for this course
					if(!student.meetsPrerequisites(course))
						continue;
					studentExpr1.addTerm(1.0, studentCourseVars[sKey][cKey]); 
					studentExpr2.addTerm(1.0/STUDENTS_PER_TA, studentCourseVars[sKey][cKey]);
				}
				// the following expression is the sum of this course and all TA pairs
				taExpr1 = new GRBLinExpr(); 
				taExpr2 = new GRBLinExpr(); 
				for(Entry<Integer, TeachingAssistant> taEntry : teachingAssistantMap.entrySet()){
					taKey = taEntry.getKey(); 
					ta = taEntry.getValue(); 
					// skip if this TA can't teach this class
					if(!ta.canTeach(course))
						continue;
					
					taExpr1.addTerm(STUDENTS_PER_TA, courseTaVars[cKey][taKey]); 
					taExpr2.addTerm(1.0, courseTaVars[cKey][taKey]); 
				}
				model.addConstr(studentExpr1, GRB.LESS_EQUAL, taExpr1, "studentsPerTAsLimit_C" + cKey); 
				studentExpr2.addConstant(0.99999);
				model.addConstr(taExpr2, GRB.LESS_EQUAL, studentExpr2, "TAsLessThanStudent_C" + cKey); 
			}
		}catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}

	/**
	 * for every professor create an expression containing all the courses
	 * that they are not capable of teaching and set that expression to zero
	 * @throws Exception
	 */
	private void addProfessorCannotTeachCourseConstraint()throws Exception{
		Course course; 
		Professor professor; 
		int cKey; 
		int pKey; 
		GRBLinExpr expr; 
		
		try{
			for(Entry<Integer, Professor> profEntry : professorMap.entrySet()){
				pKey = profEntry.getKey(); 
				professor = profEntry.getValue(); 
				
				expr = new GRBLinExpr();
				for(Entry<Integer, Course> courseEntry : courseMap.entrySet()){
					cKey = courseEntry.getKey(); 
					course = courseEntry.getValue();
					// skip if this course is not being offered
					if(!course.getIsOffered())
						continue;
					if(!professor.canTeach(course))
						expr.addTerm(1, courseProfessorVars[cKey][pKey]); 
				}
				model.addConstr(expr, GRB.EQUAL, 0, "professorCannotTeachCourse_P" + pKey); 
			}
		}
		catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}
	
	/**
	 * for every TA create an expression containing all the courses that 
	 * they are not capable of teaching and set that expression to zero 
	 * @throws Exception
	 */
	private void addTACannotTeachCourseConstraint()throws Exception{
		Course course; 
		TeachingAssistant ta; 
		int cKey; 
		int taKey; 
		GRBLinExpr expr; 
		
		try{
			for(Entry<Integer, TeachingAssistant> taEntry : teachingAssistantMap.entrySet()){
				taKey = taEntry.getKey(); 
				ta = taEntry.getValue(); 
				
				expr = new GRBLinExpr();
				for(Entry<Integer, Course> courseEntry : courseMap.entrySet()){
					cKey = courseEntry.getKey(); 
					course = courseEntry.getValue();
					// skip if this course is not being offered
					if(!course.getIsOffered())
						continue;
					if(!ta.canTeach(course))
						expr.addTerm(1, courseTaVars[cKey][taKey]); 
				}
				model.addConstr(expr, GRB.EQUAL, 0, "taCannotTeachCourse_TA" + taKey); 
			}
		}
		catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}

	/**
	 * creates an expression which is the sum of all the student-course pairs
	 * times the preferences of each student for each course pair and sets 
	 * the expression to be greater or equal to x
	 * @throws Exception
	 */
	private void addOptimizationConstraint()throws Exception{
		GRBLinExpr studentsExpr; 
		GRBLinExpr professorExpr; 
		GRBLinExpr taExpr; 
		int sKey; 
		int cKey; 
		int pKey; 
		int taKey; 
		int coursePreferrence; 
		Student student; 
		Course course; 
		Professor professor; 
		TeachingAssistant ta; 
		
		try{
			studentsExpr = new GRBLinExpr(); 
			// student-course pair greater or equal to "X"
			for(Entry<Integer, Student> studentEntry : studentMap.entrySet()){
				sKey = studentEntry.getKey(); 
				student = studentEntry.getValue(); 
				for(Entry<Integer, Course> courseEntry : courseMap.entrySet()){
					cKey = courseEntry.getKey(); 
					course = courseEntry.getValue(); 
					// skip if course is not offered
					if(!course.getIsOffered())
						continue; 
					// skip if student doesn't meet prerequisites 
					if(!student.meetsPrerequisites(course))
						continue; 
					coursePreferrence = student.getCoursePreferrence(course); 
					studentsExpr.addTerm(STUDENT_WEIGHT/coursePreferrence, studentCourseVars[sKey][cKey]);
				}
			}

			for(Entry<Integer, Professor> profEntry : professorMap.entrySet()){
				pKey = profEntry.getKey(); 
				professor = profEntry.getValue(); 
				professorExpr = new GRBLinExpr(); 
				
				for(Entry<Integer, Course> courseEntry : courseMap.entrySet()){
					cKey = courseEntry.getKey(); 
					course = courseEntry.getValue();
					// skip if this course is not being offered
					if(!course.getIsOffered())
						continue;
					// skip if professor cannot teach this course
					if(!professor.canTeach(course))
						continue; 
					professorExpr.addTerm(-1.0, courseProfessorVars[cKey][pKey]); 
				}
				professorExpr.add(studentsExpr);
				model.addConstr(professorExpr, GRB.GREATER_EQUAL, x, "maxStudentMinCoursePerProfessor_P" + pKey);
			}
			
			for(Entry<Integer, TeachingAssistant> taEntry : teachingAssistantMap.entrySet()){
				taKey = taEntry.getKey(); 
				ta = taEntry.getValue(); 
				taExpr = new GRBLinExpr(); 
				
				for(Entry<Integer, Course> courseEntry : courseMap.entrySet()){
					cKey = courseEntry.getKey(); 
					course = courseEntry.getValue();
					// skip if this course is not being offered
					if(!course.getIsOffered())
						continue;
					// skip if TA cannot teach this course
					if(!ta.canTeach(course))
						continue; 
					taExpr.addTerm(-1.0, courseTaVars[cKey][taKey]); 
				}
				taExpr.add(studentsExpr);
				model.addConstr(taExpr, GRB.GREATER_EQUAL, x, "maxStudentMinCoursePerTA_TA" + taKey);
			}
			
		}catch(Exception ex){
			throw new Exception(ex.getMessage()); 
		}
	}
	
	/**
	 * Convert the students, professors, TAs, and courses 
	 * lists to maps. The key used for every pair is the index
	 * of the object in the list. The reason for using an integer
	 * key for every object is to make it easier to create Gurobi 
	 * variables and reference them by this key index. 
	 */
	private void convertListsToMaps(List<Student> students,List<Course> courses, 
								List<Professor> professors, List<TeachingAssistant> teachingAssistants){
		
		int key; 
		
		studentMap = new HashMap<Integer, Student>(); 
		professorMap = new HashMap<Integer, Professor>(); 
		teachingAssistantMap = new HashMap<Integer, TeachingAssistant>(); 
		courseMap = new HashMap<Integer, Course>(); 
				
		key = 0;
		for(Student student : students)
			studentMap.put(key++, student); 
		
		key = 0; 
		for(Professor professor : professors)
			professorMap.put(key++, professor); 
		
		key = 0; 
		for(TeachingAssistant ta : teachingAssistants)
			teachingAssistantMap.put(key++, ta); 
		
		key = 0; 
		for(Course course : courses)
			courseMap.put(key++, course); 
	}
}
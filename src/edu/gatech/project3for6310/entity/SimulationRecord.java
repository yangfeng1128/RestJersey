package edu.gatech.project3for6310.entity;


import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonIgnoreProperties("_id")
public class SimulationRecord {
	 
	private String id;													// id of this simulation
	private String adminId;    											// if simulated by an administrator, this is their id;
	private String simulatedTime;										// date and time when this simulation was created
	private Map<String, List<String>>  studentPreference;   			// <studentid,preferred classes with preference>
	private Map<String, List<String>>  studentRecommendation;  			// student id, list of courses recommended
	private Map<String, List<String>>  professorAssignment;				// course id, professor id
	private Map<String, List<String>>  taAssignment;  					// course id, list of teaching assistant
	
	public SimulationRecord(){
		this.id = ""; 
		this.adminId = ""; 
		this.simulatedTime = ""; 
		this.studentPreference = null; 
		this.studentRecommendation = null; 
		this.professorAssignment = null; 
		this.taAssignment = null; 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getSimulatedTime() {
		return simulatedTime;
	}

	public void setSimulatedTime(String simulatedTime) {
		this.simulatedTime = simulatedTime;
	}

	public Map<String, List<String>> getStudentPreference() {
		return studentPreference;
	}

	public void setStudentPreference(Map<String, List<String>> studentPreference) {
		this.studentPreference = studentPreference;
	}

	public Map<String, List<String>> getStudentRecommendation() {
		return studentRecommendation;
	}

	public void setStudentRecommendation(Map<String, List<String>> studentRecommendation) {
		this.studentRecommendation = studentRecommendation;
	}

	public Map<String, List<String>> getProfessorAssignment() {
		return professorAssignment;
	}

	public void setProfessorAssignment(Map<String, List<String>> professorAssignment) {
		this.professorAssignment = professorAssignment;
	}

	public Map<String, List<String>> getTaAssignment() {
		return taAssignment;
	}

	public void setTaAssignment(Map<String, List<String>> taAssignment) {
		this.taAssignment = taAssignment;
	}
	

  
  
}

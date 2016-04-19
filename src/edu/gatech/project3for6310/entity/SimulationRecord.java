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
  private String id;
  private String adminId;
  private String simulatedTime;
  private Map<String,List<String>> studentPreference;
  private Map<String, List<String>> courseRecommended;
  private Map<String, List<String>> professorAssignment;
  private Map<String, List<String>> tAAssignment;
  
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
public Map<String, List<String>> getCourseRecommended() {
	return courseRecommended;
}
public void setCourseRecommended(Map<String, List<String>> courseRecommended) {
	this.courseRecommended = courseRecommended;
}
public Map<String, List<String>> getProfessorAssignment() {
	return professorAssignment;
}
public void setProfessorAssignment(Map<String, List<String>> professorAssignment) {
	this.professorAssignment = professorAssignment;
}
public Map<String, List<String>> getTAAssignment() {
	return tAAssignment;
}
public void setTAAssignment(Map<String, List<String>> tAAssignment) {
	this.tAAssignment = tAAssignment;
}

  
  
}

package edu.gatech.project3for6310.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonIgnoreProperties("_id")
public class Course {
 private String id;
 private String courseName;
 private String assignedProfessor;
 boolean isOffered;
 boolean isMandatory;
 List<String> assignedTA;
 List<String> prerequisites;
 

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getCourseName() {
	return courseName;
}
public void setCourseName(String courseName) {
	this.courseName = courseName;
}
public String getAssignedProfessor() {
	return assignedProfessor;
}
public void setAssignedProfessor(String assignedProfessor) {
	this.assignedProfessor = assignedProfessor;
}
public boolean getIsOffered() {
	return isOffered;
}
public void setIsOffered(boolean isOffered) {
	this.isOffered = isOffered;
}
public boolean getIsMandatory() {
	return isMandatory;
}
public void setIsMandatory(boolean isMandatory) {
	this.isMandatory = isMandatory;
}
public List<String> getAssignedTA() {
	return assignedTA;
}
public void setAssignedTA(List<String> assignedTA) {
	this.assignedTA = assignedTA;
}
public List<String> getPrerequisites() {
	return prerequisites;
}
public void setPrerequisites(List<String> prerequisites) {
	this.prerequisites = prerequisites;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((assignedProfessor == null) ? 0 : assignedProfessor.hashCode());
	result = prime * result + ((assignedTA == null) ? 0 : assignedTA.hashCode());
	result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + (isMandatory ? 1231 : 1237);
	result = prime * result + (isOffered ? 1231 : 1237);
	result = prime * result + ((prerequisites == null) ? 0 : prerequisites.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Course other = (Course) obj;
	if (assignedProfessor == null) {
		if (other.assignedProfessor != null)
			return false;
	} else if (!assignedProfessor.equals(other.assignedProfessor))
		return false;
	if (assignedTA == null) {
		if (other.assignedTA != null)
			return false;
	} else if (!assignedTA.equals(other.assignedTA))
		return false;
	if (courseName == null) {
		if (other.courseName != null)
			return false;
	} else if (!courseName.equals(other.courseName))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (isMandatory != other.isMandatory)
		return false;
	if (isOffered != other.isOffered)
		return false;
	if (prerequisites == null) {
		if (other.prerequisites != null)
			return false;
	} else if (!prerequisites.equals(other.prerequisites))
		return false;
	return true;
}
 
 
}

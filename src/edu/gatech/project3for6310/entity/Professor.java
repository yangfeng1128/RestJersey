package edu.gatech.project3for6310.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonIgnoreProperties("_id")
public class Professor {
 private String id;
 private String fullName;
 private boolean available;
private List<String> capableCourses;
 private List<String> courseAssigned;
 
 public boolean getAvailable() {
	return available;
}
public void setAvailable(boolean available) {
	this.available = available;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getFullName() {
	return fullName;
}
public void setFullName(String fullName) {
	this.fullName = fullName;
}
public List<String> getCapableCourses() {
	return capableCourses;
}
public void setCapableCourses(List<String> capableCourses) {
	this.capableCourses = capableCourses;
}
public List<String> getCourseAssigned() {
	return courseAssigned;
}
public void setCourseAssigned(List<String> courseAssigned) {
	this.courseAssigned = courseAssigned;
}


public boolean canTeach(Course course){
	return capableCourses.contains(course.getId()); 
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((capableCourses == null) ? 0 : capableCourses.hashCode());
	result = prime * result + ((courseAssigned == null) ? 0 : courseAssigned.hashCode());
	result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	Professor other = (Professor) obj;
	if (capableCourses == null) {
		if (other.capableCourses != null)
			return false;
	} else if (!capableCourses.equals(other.capableCourses))
		return false;
	if (courseAssigned == null) {
		if (other.courseAssigned != null)
			return false;
	} else if (!courseAssigned.equals(other.courseAssigned))
		return false;
	if (fullName == null) {
		if (other.fullName != null)
			return false;
	} else if (!fullName.equals(other.fullName))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	return true;
}
 
 
 
}

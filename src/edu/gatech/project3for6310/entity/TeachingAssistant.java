package edu.gatech.project3for6310.entity;

import java.util.List;

public class TeachingAssistant {

	private String id;
	private List<String> capableCourses;
	String courseAssigned;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getCapableCourses() {
		return capableCourses;
	}
	public void setCapableCourses(List<String> capableCourses) {
		this.capableCourses = capableCourses;
	}
	public String getCourseAssigned() {
		return courseAssigned;
	}
	public void setCourseAssigned(String courseAssigned) {
		this.courseAssigned = courseAssigned;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capableCourses == null) ? 0 : capableCourses.hashCode());
		result = prime * result + ((courseAssigned == null) ? 0 : courseAssigned.hashCode());
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
		TeachingAssistant other = (TeachingAssistant) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

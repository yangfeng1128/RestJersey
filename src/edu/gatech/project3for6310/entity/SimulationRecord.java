package edu.gatech.project3for6310.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class SimulationRecord {
  private String id;
  private boolean isShadowMode;
  private String adminId;
  private String simulatedTime;
  private Map<String,String[]> studentPreference;
  private Map<String, List<String>> courseRecommended;
  private Map<String, String> professorAssignment;
  private Map<String, List<String>> tAAssignment;
  
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public boolean getIsShadowMode() {
	return isShadowMode;
}
public void setIsShadowMode(boolean isShadowMode) {
	this.isShadowMode = isShadowMode;
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
public Map<String, String[]> getStudentPreference() {
	return studentPreference;
}
public void setStudentPreference(Map<String, String[]> studentPreference) {
	this.studentPreference = studentPreference;
}
public Map<String, List<String>> getCourseRecommended() {
	return courseRecommended;
}
public void setCourseRecommended(Map<String, List<String>> courseRecommended) {
	this.courseRecommended = courseRecommended;
}
public Map<String, String> getProfessorAssignment() {
	return professorAssignment;
}
public void setProfessorAssignment(Map<String, String> professorAssignment) {
	this.professorAssignment = professorAssignment;
}
public Map<String, List<String>> gettAAssignment() {
	return tAAssignment;
}
public void settAAssignment(Map<String, List<String>> tAAssignment) {
	this.tAAssignment = tAAssignment;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((adminId == null) ? 0 : adminId.hashCode());
	result = prime * result + ((courseRecommended == null) ? 0 : courseRecommended.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + (isShadowMode ? 1231 : 1237);
	result = prime * result + ((professorAssignment == null) ? 0 : professorAssignment.hashCode());
	result = prime * result + ((simulatedTime == null) ? 0 : simulatedTime.hashCode());
	result = prime * result + ((studentPreference == null) ? 0 : studentPreference.hashCode());
	result = prime * result + ((tAAssignment == null) ? 0 : tAAssignment.hashCode());
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
	SimulationRecord other = (SimulationRecord) obj;
	if (adminId == null) {
		if (other.adminId != null)
			return false;
	} else if (!adminId.equals(other.adminId))
		return false;
	if (courseRecommended == null) {
		if (other.courseRecommended != null)
			return false;
	} else if (!courseRecommended.equals(other.courseRecommended))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (isShadowMode != other.isShadowMode)
		return false;
	if (professorAssignment == null) {
		if (other.professorAssignment != null)
			return false;
	} else if (!professorAssignment.equals(other.professorAssignment))
		return false;
	if (simulatedTime == null) {
		if (other.simulatedTime != null)
			return false;
	} else if (!simulatedTime.equals(other.simulatedTime))
		return false;
	if (studentPreference == null) {
		if (other.studentPreference != null)
			return false;
	} else if (!studentPreference.equals(other.studentPreference))
		return false;
	if (tAAssignment == null) {
		if (other.tAAssignment != null)
			return false;
	} else if (!tAAssignment.equals(other.tAAssignment))
		return false;
	return true;
}
  
  
}

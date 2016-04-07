package edu.gatech.project3for6310.entity;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fengyang
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Student {
    private String id;
    private String fullName;
	private int numDesiredCourse;
    private Map<String, Integer> preferredCources;
    private String requestId;
    private List<String> rcmCources;
    private boolean isSimulated;
    private List<String> courseTaken;
    
    
    
    
    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<String> getCourseTaken() {
		return courseTaken;
	}

	public void setCourseTaken(List<String> courseTaken) {
		this.courseTaken = courseTaken;
	}

	
 

    public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public boolean getIsSimulated() {
		return isSimulated;
	}

	public void setIsSimulated(boolean isSimulated) {
		this.isSimulated = isSimulated;
	}
    
    public Student(){}

    public int getNumDesiredCourse() {
		return numDesiredCourse;
	}

	public void setNumDesiredCourse(int numDesiredCourse) {
		this.numDesiredCourse = numDesiredCourse;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Integer> getPreferredCources() {
		return preferredCources;
	}

	public void setPreferredCources(Map<String, Integer> preferredCources) {
		this.preferredCources = preferredCources;
	}


	public List<String> getRcmCources() {
		return rcmCources;
	}

	public void setRcmCources(List<String> rcmCources) {
		this.rcmCources = rcmCources;
	}

    


}
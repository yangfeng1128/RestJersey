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
    private String email;
	private int numDesiredCourse;
    private List<String> preferredCources;
    private String requestId;
    private List<String> rcmCources;
    private boolean isSimulated;
    private List<String> courseTaken;
    
    
    
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPreferredCources(List<String> preferredCources) {
		this.preferredCources = preferredCources;
	}

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



	public List<String> getRcmCources() {
		return rcmCources;
	}

	public void setRcmCources(List<String> rcmCources) {
		this.rcmCources = rcmCources;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseTaken == null) ? 0 : courseTaken.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isSimulated ? 1231 : 1237);
		result = prime * result + numDesiredCourse;
		result = prime * result + ((preferredCources == null) ? 0 : preferredCources.hashCode());
		result = prime * result + ((rcmCources == null) ? 0 : rcmCources.hashCode());
		result = prime * result + ((requestId == null) ? 0 : requestId.hashCode());
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
		Student other = (Student) obj;
		if (courseTaken == null) {
			if (other.courseTaken != null)
				return false;
		} else if (!courseTaken.equals(other.courseTaken))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (isSimulated != other.isSimulated)
			return false;
		if (numDesiredCourse != other.numDesiredCourse)
			return false;
		if (preferredCources == null) {
			if (other.preferredCources != null)
				return false;
		} else if (!preferredCources.equals(other.preferredCources))
			return false;
		if (rcmCources == null) {
			if (other.rcmCources != null)
				return false;
		} else if (!rcmCources.equals(other.rcmCources))
			return false;
		if (requestId == null) {
			if (other.requestId != null)
				return false;
		} else if (!requestId.equals(other.requestId))
			return false;
		return true;
	}

    


}
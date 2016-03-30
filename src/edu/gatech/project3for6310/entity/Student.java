package edu.gatech.project3for6310.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fengyang
 */
@XmlRootElement
public class Student {
    private int id;
    private String firstName;

    private String lastName;
    
    public Student(){}
    
    public Student(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public String toString()
    {
        return String.format("Student[id=%s, firstName='%s', lastName='%s']",id,firstName,lastName);
    }
}
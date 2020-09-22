package com.example.students.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "studentid")
	private Integer studentId;
	private String lastname;
	private String firstname;
	private String adress;
	private String city;
	
	public Student() {
		
	}
	
	public Student(Integer studentId, String lastname, String firstname, String adress, String city) {
		this.studentId = studentId;
		this.lastname = lastname;
		this.firstname = firstname;
		this.adress = adress;
		this.city = city;
	}
	
	public Integer getStudentId() {
		return studentId;
	}
	
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", lastname=" + lastname + ", firstname=" + firstname + ", adress="
				+ adress + ", city=" + city + "]";
	}
	
	
	

}

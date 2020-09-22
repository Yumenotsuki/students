package com.example.students.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.students.exceptions.ResourceNotFoundException;
import com.example.students.models.Student;
import com.example.students.repositories.StudentRepository;

@RestController
@RequestMapping(path="/api")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	//voir tous les élèves
	@GetMapping(path="/students")
	public List<Student> getStudents() {
		return studentRepository.findAll();
	}
	
	//Voir le détail d'un élève
	@GetMapping(path="/student/{studentId}")
	public Student findByStudentId(@PathVariable Integer studentId) {
		return studentRepository.findByStudentId(studentId);
	}
	
	//créer un nouvel élève
	@PostMapping(path="/create")
	public Student createStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	//modifier les infos d'un élèves
	@PutMapping(path="/update/{studentId}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value="studentId") Integer studentId, @RequestBody Student studentDetails) throws ResourceNotFoundException {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + studentId));
		
		student.setLastname(studentDetails.getLastname());
		student.setFirstname(studentDetails.getFirstname());
		student.setAdress(studentDetails.getAdress());
		student.setCity(studentDetails.getCity());
		final Student updatedStudent = studentRepository.save(student);
		return ResponseEntity.ok(updatedStudent);
		
	}
	
	//supprimer un élèves
	@DeleteMapping(path="/delete/{studentId}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "studentId") Integer studentId) throws ResourceNotFoundException {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + studentId));
		studentRepository.delete(student);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
				
	}
	
}

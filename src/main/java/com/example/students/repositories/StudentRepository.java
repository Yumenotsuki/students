package com.example.students.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.students.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
	Student findByStudentId(Integer studentId);
	List<Student> findAll();

}

package com.example.course_project.repository;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_project.entity.Student;

@Transactional
@Repository
public interface StudentDao extends JpaRepository<Student,String>{

	/* 用學生證號找學生 */
	public Optional<Student> findByStudentId(int studentId);
}

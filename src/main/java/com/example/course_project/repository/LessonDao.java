package com.example.course_project.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_project.entity.Lesson;

@Transactional
@Repository
public interface LessonDao extends JpaRepository<Lesson,Integer>{

	public List<Lesson> findByLessonName(String lessonName);
	
	public Optional<Lesson> findByLessonId(int lessonId);
	
	
}

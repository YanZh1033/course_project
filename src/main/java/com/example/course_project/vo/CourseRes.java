package com.example.course_project.vo;

import java.util.List;

import com.example.course_project.entity.Lesson;
import com.example.course_project.entity.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseRes {
	
	@JsonProperty("lesson_info")
	private Lesson lesson;
	
	@JsonProperty("all_lesson_info")
	private List<Lesson> lessonList;
	
	@JsonProperty("student_info")
	private Student student;
	
	private String message;

	public CourseRes() {

	}
	
	public CourseRes(String message) {
		this.message = message;
	}
	
	public CourseRes(Lesson lesson, String message) {
		this.lesson = lesson;
		this.message = message;
	}
	
	public CourseRes(List<Lesson> lessonList, String message) {
		this.lessonList = lessonList;
		this.message = message;
	}
	
	public CourseRes(Student student, String message) {
		this.student = student;
		this.message = message;
	}
	
	public CourseRes(Student student, List<Lesson> lessonList, String message) {
		this.student = student;
		this.lessonList = lessonList;
		this.message = message;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

package com.example.course_project.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lesson")
public class Lesson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lesson_id")
	private int lessonId;
	
	@Column(name = "lesson_name")
	private String lessonName;
	
	@Column(name = "day")
	private String day;
	
	@Column(name = "start_time")
	private LocalTime startTime;
	
	@Column(name = "end_time")
	private LocalTime endTime;
	
	@Column(name = "credits")
	private int credits;
	

	public Lesson() {

	}


	public Lesson(String lessonName, String day, LocalTime startTime, LocalTime endTime, int credits) {
		this.lessonName = lessonName;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.credits = credits;
	}
	
	public void updateLesson(String lessonName, String day, LocalTime startTime, LocalTime endTime, int credits) {
		this.lessonName = lessonName;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.credits = credits;
	}



	public int getLessonId() {
		return lessonId;
	}


	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}


	public String getLessonName() {
		return lessonName;
	}


	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}


	public String getDay() {
		return day;
	}


	public void setDay(String day) {
		this.day = day;
	}


	public LocalTime getStartTime() {
		return startTime;
	}


	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}


	public LocalTime getEndTime() {
		return endTime;
	}


	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}


	public int getCredits() {
		return credits;
	}


	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	

}

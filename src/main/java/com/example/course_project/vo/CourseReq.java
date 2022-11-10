package com.example.course_project.vo;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseReq {
	
	@JsonProperty("lesson_id")
	private int lessonId;
	
	@JsonProperty("lesson_name")
	private String lessonName;
	
	@JsonProperty("day")
	private String day;
	
	@JsonProperty("start_time")
	private String startTime;
	
	@JsonProperty("end_time")
	private String endTime;
	
	@JsonProperty("credits")
	private int credits;
	
	@JsonProperty("student_id")
	private int studentId;
	
	@JsonProperty("student_name")
	private String studentName;
	
	@JsonProperty("lesson_id_set")
	private Set<Integer> lessonIdSet;
	
	@JsonProperty("add_0_or_drop_1")
	private int addOrDrop;

	public CourseReq() {
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Set<Integer> getLessonIdSet() {
		return lessonIdSet;
	}

	public void setLessonIdSet(Set<Integer> lessonIdSet) {
		this.lessonIdSet = lessonIdSet;
	}

	public int getAddOrDrop() {
		return addOrDrop;
	}

	public void setAddOrDrop(int addOrDrop) {
		this.addOrDrop = addOrDrop;
	}


}

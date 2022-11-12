package com.example.course_project.service.ifs;

import java.util.Set;

import com.example.course_project.vo.CourseRes;

public interface CourseService {
	
	/*---------------------------------------------------*/
	
	/* 新增課堂(課堂代號、課堂名稱、星期幾、開始時間、結束時間、學分) */
	public CourseRes createLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits);
	
	/* 更改課堂資訊([課堂代號]、課堂名稱、星期幾、開始時間、結束時間、學分) */
	public CourseRes updateLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits);
	
	/* 刪除課堂(課堂代號) */
	public CourseRes deleteLesson(int lessonId);

	/* 新增學生(學號、姓名) */
	public CourseRes createStudent(int studentId, String studentName);
	
	/* 更改學生資訊([學號]、姓名) */
	public CourseRes updateStudent(int studentId, String studentName);
	
	/* 刪除學生(學號) */
	public CourseRes deleteStudent(int studentId);
	
	/* 選課(學號、課堂名稱) */
	public CourseRes courseSelection(int StudentId, Set<Integer> lessonIdSet);
	
	/* 加退選(學號、課堂代號) 0:加選，1:退選*/
	public CourseRes addOrDropLesson(int aw, int studentId, Set<Integer> lessonIdSet) ; 
	
	/* 依學號查詢選上的課資訊 */
	public CourseRes getSelectLessonInfo(int studentId);
	
	/* 尋找符合的所有課堂資訊(課堂代碼) */
	public CourseRes getLessonInfoByLessonId(int lessonId);
	
	/* 尋找符合的所有課堂資訊(課堂名稱) */
	public CourseRes getLessonInfoByLessonName(String lessonName);
	
}
package com.example.course_project.service.ifs;

import java.time.LocalTime;
import java.util.List;

import com.example.course_project.vo.CourseRes;

public interface CourseService {
	
	/* 判斷輸入時間字串是否符合規定格式，符合:返回該字串LoaclTime類型 */
	public LocalTime checkTimeFormat(String timeStr);
	
	/*---------------------------------------------------*/
	
	/* 新增課堂(課堂名稱、星期幾、開始時間、結束時間、學分) */
	public CourseRes createLesson(String lessonName, String day, String startTime, String endTime, int credits);
	
	/* 更改課堂資訊(課堂代號、課堂名稱、星期幾、開始時間、結束時間、學分) */
	public CourseRes updateLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits);
	
	/* 刪除課堂 */
	public CourseRes deleteLesson(int lessonId);

	
	/* 新增學生(學號、姓名) */
	public CourseRes createStudent(int studentId, String studentName);
	
	/* 選課(學號、課堂名稱) */
	public CourseRes courseSelection(int studentId,List<String> lessonName);
	
	/* 加退選(學號、舊課堂代號、新課堂代號) */
	public CourseRes changeLesson(int studentId, String oldLessonName, String newLessonName);
	
	/* 尋找符合的所有課堂資訊(課堂代碼) */
	public CourseRes getLessonInfoByLessonId(int lessonId);
	
	/* 尋找符合的所有課堂資訊(課堂名稱) */
	public CourseRes getLessonInfoByLessonName(String lessonName);
	
}

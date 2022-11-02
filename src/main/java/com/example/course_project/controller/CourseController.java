package com.example.course_project.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_project.service.ifs.CourseService;
import com.example.course_project.vo.CourseReq;
import com.example.course_project.vo.CourseRes;

@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	/* 新增課堂(課堂名稱、星期幾、開始時間、結束時間、學分) */
	@PostMapping(value = "/api/createLesson")
	public CourseRes createLesson(@RequestBody CourseReq req){
		return courseService.createLesson(req.getLessonName(),req.getDay(),req.getStartTime(),req.getEndTime(),req.getCredits());
	}
	
	/* 更改課堂資訊(課堂代號) */
	@PostMapping(value = "/api/updateLesson")
	public CourseRes updateLesson(@RequestBody CourseReq req) {
		return courseService.updateLesson(req.getLessonId(),req.getLessonName(),req.getDay(),req.getStartTime(),req.getEndTime(),req.getCredits());
	}
	
	/* 刪除課堂 */
	public CourseRes deleteLesson(@RequestBody CourseReq req) {
		return courseService.deleteLesson(req.getLessonId());
	}
	
	/* 新增學生(學號、姓名) */
	@PostMapping(value = "/api/createStudent")
	public CourseRes createStudent(@RequestBody CourseReq req) {
		return courseService.createStudent(req.getStudentId(), req.getStudentName());
	}
	/* 尋找符合的所有課堂資訊(課堂代碼) */
	@PostMapping(value = "/api/getLessonInfoByLessonId")
	public CourseRes getLessonInfoByLessonId(@RequestBody CourseReq req) {
		return courseService.getLessonInfoByLessonId(req.getLessonId());
	}
	
	/* 尋找符合的所有課堂資訊(課堂名稱) */
	@PostMapping(value = "/api/getLessonInfoByLessonName")
	public CourseRes getLessonInfoByLessonName(@RequestBody CourseReq req) {
		return courseService.getLessonInfoByLessonName(req.getLessonName());
	}
	

} 

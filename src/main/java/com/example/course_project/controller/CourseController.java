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
	
	/* 新增課堂(課堂代號、課堂名稱、星期幾、開始時間、結束時間、學分) */
	@PostMapping(value = "/api/createLesson")
	public CourseRes createLesson(@RequestBody CourseReq req){
		return courseService.createLesson(req.getLessonId(),req.getLessonName(),req.getDay(),req.getStartTime(),req.getEndTime(),req.getCredits());
	}
	
	/* 更改課堂資訊([課堂代號]、課堂名稱、星期幾、開始時間、結束時間、學分) */
	@PostMapping(value = "/api/updateLesson")
	public CourseRes updateLesson(@RequestBody CourseReq req) {
		return courseService.updateLesson(req.getLessonId(),req.getLessonName(),req.getDay(),req.getStartTime(),req.getEndTime(),req.getCredits());
	}
	
	/* 刪除課堂(課堂代號) */
	@PostMapping(value = "/api/deleteLesson")
	public CourseRes deleteLesson(@RequestBody CourseReq req) {
		return courseService.deleteLesson(req.getLessonId());
	}
	
	/* 新增學生(學號、姓名) */
	@PostMapping(value = "/api/createStudent")
	public CourseRes createStudent(@RequestBody CourseReq req) {
		return courseService.createStudent(req.getStudentId(), req.getStudentName());
	}
	
	/* 更改學生資訊([學號]、姓名) */
	@PostMapping(value = "/api/updateStudent")
	public CourseRes updateStudent(@RequestBody CourseReq req) {
		return courseService.updateStudent(req.getStudentId(), req.getStudentName());
	}
	
	/* 刪除學生(學號) */
	@PostMapping(value = "/api/deleteStudent")
	public CourseRes deleteStudent(@RequestBody CourseReq req) {
		return courseService.deleteStudent(req.getStudentId());
	}
	
	/* 選課(學號、課堂代號) */
	@PostMapping(value = "/api/courseSelection")
	public CourseRes courseSelection(@RequestBody CourseReq req) {
		return courseService.courseSelection(req.getStudentId(), req.getLessonIdSet());
	}
	
	/* 加退選(學號、課堂代號) 0:加選，1:退選*/
	@PostMapping(value = "/api/addOrDropLesson")
	public CourseRes addOrDropLesson(@RequestBody CourseReq req) {
		return courseService.addOrDropLesson(req.getAddOrDrop(), req.getStudentId(), req.getLessonIdSet());
	}
	
	/* 依學號查詢選上的課資訊 */
	@PostMapping(value = "/api/getSelectLessonInfo")
	public CourseRes getSelectLessonInfo(@RequestBody CourseReq req) {
		return courseService.getSelectLessonInfo(req.getStudentId());
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

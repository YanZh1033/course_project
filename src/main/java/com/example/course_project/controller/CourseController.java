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
	
	/* �s�W�Ұ�(�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	@PostMapping(value = "/api/createLesson")
	public CourseRes createLesson(@RequestBody CourseReq req){
		return courseService.createLesson(req.getLessonName(),req.getDay(),req.getStartTime(),req.getEndTime(),req.getCredits());
	}
	
	/* ���Ұ��T(�Ұ�N��) */
	@PostMapping(value = "/api/updateLesson")
	public CourseRes updateLesson(@RequestBody CourseReq req) {
		return courseService.updateLesson(req.getLessonId(),req.getLessonName(),req.getDay(),req.getStartTime(),req.getEndTime(),req.getCredits());
	}
	
	/* �R���Ұ� */
	public CourseRes deleteLesson(@RequestBody CourseReq req) {
		return courseService.deleteLesson(req.getLessonId());
	}
	
	/* �s�W�ǥ�(�Ǹ��B�m�W) */
	@PostMapping(value = "/api/createStudent")
	public CourseRes createStudent(@RequestBody CourseReq req) {
		return courseService.createStudent(req.getStudentId(), req.getStudentName());
	}
	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�N�X) */
	@PostMapping(value = "/api/getLessonInfoByLessonId")
	public CourseRes getLessonInfoByLessonId(@RequestBody CourseReq req) {
		return courseService.getLessonInfoByLessonId(req.getLessonId());
	}
	
	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�W��) */
	@PostMapping(value = "/api/getLessonInfoByLessonName")
	public CourseRes getLessonInfoByLessonName(@RequestBody CourseReq req) {
		return courseService.getLessonInfoByLessonName(req.getLessonName());
	}
	

} 

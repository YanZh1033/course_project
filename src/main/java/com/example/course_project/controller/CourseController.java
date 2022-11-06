package com.example.course_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_project.service.ifs.CourseService;
import com.example.course_project.vo.CourseReq;
import com.example.course_project.vo.CourseRes;

import springfox.documentation.annotations.ApiIgnore;

@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	/* �s�W�Ұ�(�Ұ�N���B�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	@PostMapping(value = "/api/createLesson")
	public CourseRes createLesson(@RequestBody CourseReq req){
		return courseService.createLesson(req.getLessonId(),req.getLessonName(),req.getDay(),req.getStartTime(),req.getEndTime(),req.getCredits());
	}
	
	/* ���Ұ��T([�Ұ�N��]�B�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	@PostMapping(value = "/api/updateLesson")
	public CourseRes updateLesson(@RequestBody CourseReq req) {
		return courseService.updateLesson(req.getLessonId(),req.getLessonName(),req.getDay(),req.getStartTime(),req.getEndTime(),req.getCredits());
	}
	
	/* �R���Ұ�(�Ұ�N��) */
	@PostMapping(value = "/api/deleteLesson")
	public CourseRes deleteLesson(@RequestBody CourseReq req) {
		return courseService.deleteLesson(req.getLessonId());
	}
	
	/* �s�W�ǥ�(�Ǹ��B�m�W) */
	@PostMapping(value = "/api/createStudent")
	public CourseRes createStudent(@RequestBody CourseReq req) {
		return courseService.createStudent(req.getStudentId(), req.getStudentName());
	}
	
	/* ���ǥ͸�T([�Ǹ�]�B�m�W) */
	@PostMapping(value = "/api/updateStudent")
	public CourseRes updateStudent(@RequestBody CourseReq req) {
		return courseService.updateStudent(req.getStudentId(), req.getStudentName());
	}
	
	/* �R���ǥ�(�Ǹ�) */
	@PostMapping(value = "/api/deleteStudent")
	public CourseRes deleteStudent(@RequestBody CourseReq req) {
		return courseService.deleteStudent(req.getStudentId());
	}
	
	/* ���(�Ǹ��B�Ұ�N��) */
	@PostMapping(value = "/api/courseSelection")
	public CourseRes courseSelection(@RequestBody CourseReq req) {
		return courseService.courseSelection(req.getStudentId(), req.getLessonIdSet());
	}
	
	/* [TestV1]���(�Ǹ��B�Ұ�N��) */
	@ApiIgnore
	@PostMapping(value = "/api/courseSelectionV1")
	public CourseRes courseSelectionV1(@RequestBody CourseReq req) {
		return courseService.courseSelectionV1(req.getStudentId(), req.getLessonIdSet());
	}
	
	/* �h��(�Ǹ��B�Ұ�N��) */
	@PostMapping(value = "/api/dropOutLesson")
	public CourseRes dropOutLesson(@RequestBody CourseReq req) {
		return courseService.dropOutLesson(req.getStudentId(), req.getLessonIdSet());
	}
	
	/* �̾Ǹ��d�߿�W���Ҹ�T */
	@PostMapping(value = "/api/getSelectLessonInfo")
	public CourseRes getSelectLessonInfo(@RequestBody CourseReq req) {
		return courseService.getSelectLessonInfo(req.getStudentId());
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

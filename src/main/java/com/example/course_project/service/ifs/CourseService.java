package com.example.course_project.service.ifs;

import java.time.LocalTime;
import java.util.List;

import com.example.course_project.vo.CourseRes;

public interface CourseService {
	
	/* �P�_��J�ɶ��r��O�_�ŦX�W�w�榡�A�ŦX:��^�Ӧr��LoaclTime���� */
	public LocalTime checkTimeFormat(String timeStr);
	
	/*---------------------------------------------------*/
	
	/* �s�W�Ұ�(�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	public CourseRes createLesson(String lessonName, String day, String startTime, String endTime, int credits);
	
	/* ���Ұ��T(�Ұ�N���B�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	public CourseRes updateLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits);
	
	/* �R���Ұ� */
	public CourseRes deleteLesson(int lessonId);

	
	/* �s�W�ǥ�(�Ǹ��B�m�W) */
	public CourseRes createStudent(int studentId, String studentName);
	
	/* ���(�Ǹ��B�Ұ�W��) */
	public CourseRes courseSelection(int studentId,List<String> lessonName);
	
	/* �[�h��(�Ǹ��B�½Ұ�N���B�s�Ұ�N��) */
	public CourseRes changeLesson(int studentId, String oldLessonName, String newLessonName);
	
	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�N�X) */
	public CourseRes getLessonInfoByLessonId(int lessonId);
	
	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�W��) */
	public CourseRes getLessonInfoByLessonName(String lessonName);
	
}

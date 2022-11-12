package com.example.course_project.service.ifs;

import java.util.Set;

import com.example.course_project.vo.CourseRes;

public interface CourseService {
	
	/*---------------------------------------------------*/
	
	/* �s�W�Ұ�(�Ұ�N���B�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	public CourseRes createLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits);
	
	/* ���Ұ��T([�Ұ�N��]�B�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	public CourseRes updateLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits);
	
	/* �R���Ұ�(�Ұ�N��) */
	public CourseRes deleteLesson(int lessonId);

	/* �s�W�ǥ�(�Ǹ��B�m�W) */
	public CourseRes createStudent(int studentId, String studentName);
	
	/* ���ǥ͸�T([�Ǹ�]�B�m�W) */
	public CourseRes updateStudent(int studentId, String studentName);
	
	/* �R���ǥ�(�Ǹ�) */
	public CourseRes deleteStudent(int studentId);
	
	/* ���(�Ǹ��B�Ұ�W��) */
	public CourseRes courseSelection(int StudentId, Set<Integer> lessonIdSet);
	
	/* �[�h��(�Ǹ��B�Ұ�N��) 0:�[��A1:�h��*/
	public CourseRes addOrDropLesson(int aw, int studentId, Set<Integer> lessonIdSet) ; 
	
	/* �̾Ǹ��d�߿�W���Ҹ�T */
	public CourseRes getSelectLessonInfo(int studentId);
	
	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�N�X) */
	public CourseRes getLessonInfoByLessonId(int lessonId);
	
	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�W��) */
	public CourseRes getLessonInfoByLessonName(String lessonName);
	
}
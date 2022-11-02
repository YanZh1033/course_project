package com.example.course_project.service.impl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.course_project.vo.CourseRes;
import com.example.course_project.constants.CourseRtnCode;
import com.example.course_project.entity.Lesson;
import com.example.course_project.entity.Student;
import com.example.course_project.repository.LessonDao;
import com.example.course_project.repository.StudentDao;
import com.example.course_project.service.ifs.CourseService;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private LessonDao lessonDao;
	
	@Autowired
	private StudentDao studentDao;
	
	
	/* �P�_��J�ɶ��r��O�_�ŦX�W�w�榡�A�ŦX:��^�Ӧr��LoaclTime���� */
	public LocalTime checkTimeFormat(String timeStr) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime localTime = LocalTime.parse(timeStr, dtf);
			String localTimeStr = localTime.format(dtf);
			//����J�ɶ��榡�O�_�P�W�w�ɶ��榡�ۦP(�r��)
			if(localTimeStr.equals(timeStr)) {	
				return localTime;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/* �P�_�P���X�O�_�ŦX;���~��^true */
	private boolean checkDay(String day) {
		String[] week = {"��","�@","�G","�T","�|","��","��"};
		boolean b = false;
		for(int i=0; i<week.length; i++) {
			if(!week[i].equals(day)) {
				b = true;
				continue;
			}else {
				b = false;
				break;
			}
		}
		return b;
	}
	
	/* �P�_�r��O�_���Ŧr��Bnull�B��;���~��^true */
	private boolean checkStringHasText(List<String> strList) {
		boolean b = false;
		for(String item : strList) {
			if(!StringUtils.hasText(item)) {
				b = true;
				return b;
			}else {
				b = false;
			}
		}
		return b;
	}
	
	/*------------------------------------------------------------------------------*/
	/* �s�W�Ұ�(�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	public CourseRes createLesson(String lessonName, String day, String startTime, String endTime, int credits){
		List<String> strList = Arrays.asList(lessonName,day);
		//�P�_�r��O�_���Ŧr��Bnull�B�šB�Ʀr�p��0
		if(new CourseServiceImpl().checkStringHasText(strList) || credits<0) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		//�P�_�P���X�O�_�ŦX
		if(new CourseServiceImpl().checkDay(day)) {
			return new CourseRes(CourseRtnCode.DAY_ERROR.getMessage());
		}
		//checkTimeFormat():�P�_�ɶ��榡�O�_�ŦX:��^LocalTime��
		LocalTime startLocalTime = new CourseServiceImpl().checkTimeFormat(startTime);
		LocalTime endLocalTime = new CourseServiceImpl().checkTimeFormat(endTime);
		//�ɶ��榡���ŦX�榡��^null�A�B�}�l�ɶ�����񵲧��ɶ���
		if(startLocalTime == null || endLocalTime == null || startLocalTime.getHour()>endLocalTime.getHour()) {
			return new CourseRes(CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}
		Lesson lesson = new Lesson(lessonName,day,startLocalTime,endLocalTime,credits);
		lessonDao.save(lesson);
		return new CourseRes(lesson,CourseRtnCode.CREATE_SUCCESSFUL.getMessage());
	}
	
	/* ���Ұ��T(�Ұ�N���B�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	public CourseRes updateLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits) {
		//String�������ѼƩ�iList��
		List<String> strList = Arrays.asList(lessonName,day);
		//�P�_�r��O�_���Ŧr��Bnull�B�šB�Ʀr�p��0
		if(new CourseServiceImpl().checkStringHasText(strList) || lessonId<=0 || credits<0) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		//�P�_�P���X�O�_�ŦX
		if(new CourseServiceImpl().checkDay(day)) {
			return new CourseRes(CourseRtnCode.DAY_ERROR.getMessage());
		}
		//checkTimeFormat():�P�_�ɶ��榡�O�_�ŦX:��^LocalTime��
		LocalTime startLocalTime = new CourseServiceImpl().checkTimeFormat(startTime);
		LocalTime endLocalTime = new CourseServiceImpl().checkTimeFormat(endTime);
		//�ɶ��榡���ŦX�榡��^null�A�B�}�l�ɶ�����񵲧��ɶ���
		if(startLocalTime == null || endLocalTime == null || startLocalTime.getHour()>endLocalTime.getHour()) {
			return new CourseRes(CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			Lesson lesson = lessonOp.get();
			//�s���Ѽ��л\�ª�
			lesson.updateLesson(lessonName, day, startLocalTime, endLocalTime, credits);
			lessonDao.save(lesson);
			return new CourseRes(lesson,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		}else {
			
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	/* �R���Ұ� */
	public CourseRes deleteLesson(int lessonId) {
		if(lessonId<=0) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			Lesson lesson = lessonOp.get();
			lessonDao.delete(lesson);
			return new CourseRes(CourseRtnCode.DELETE_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	

	/* �s�W�ǥ�(�Ǹ��B�m�W) */
	public CourseRes createStudent(int studentId, String studentName) {
		if(studentId<=0 || !StringUtils.hasText(studentName)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			return new CourseRes(CourseRtnCode.DATA_EXISTS.getMessage());
		}else {
			Student newStudent = new Student(studentId,studentName);
			studentDao.save(newStudent);
			return new CourseRes(newStudent,CourseRtnCode.CREATE_SUCCESSFUL.getMessage());
		}
	}

	
	/* ���(�Ǹ��B�Ұ�N��) */
	public CourseRes courseSelection(int studentId, List<String> lessonName) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/* �[�h��(�Ǹ��B�½Ұ�N���B�s�Ұ�N��) */
	public CourseRes changeLesson(int studentId, String oldlessonName, String newlessonname) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�N�X) */
	public CourseRes getLessonInfoByLessonId(int lessonId) {
		if(lessonId <= 0) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			Lesson lesson = lessonOp.get();
			return new CourseRes(lesson, CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}

	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�W��) */
	public CourseRes getLessonInfoByLessonName(String lessonName) {
		if(!StringUtils.hasText(lessonName)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		List<Lesson> lessonList = lessonDao.findByLessonName(lessonName);
		if(lessonList.isEmpty()) {
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}else {
			return new CourseRes(lessonList,CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}
	}

}
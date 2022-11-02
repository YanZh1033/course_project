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
	
	
	/* 判斷輸入時間字串是否符合規定格式，符合:返回該字串LoaclTime類型 */
	public LocalTime checkTimeFormat(String timeStr) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime localTime = LocalTime.parse(timeStr, dtf);
			String localTimeStr = localTime.format(dtf);
			//比對輸入時間格式是否與規定時間格式相同(字串)
			if(localTimeStr.equals(timeStr)) {	
				return localTime;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/* 判斷星期幾是否符合;錯誤返回true */
	private boolean checkDay(String day) {
		String[] week = {"日","一","二","三","四","五","六"};
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
	
	/* 判斷字串是否為空字串、null、空;錯誤返回true */
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
	/* 新增課堂(課堂名稱、星期幾、開始時間、結束時間、學分) */
	public CourseRes createLesson(String lessonName, String day, String startTime, String endTime, int credits){
		List<String> strList = Arrays.asList(lessonName,day);
		//判斷字串是否為空字串、null、空、數字小於0
		if(new CourseServiceImpl().checkStringHasText(strList) || credits<0) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		//判斷星期幾是否符合
		if(new CourseServiceImpl().checkDay(day)) {
			return new CourseRes(CourseRtnCode.DAY_ERROR.getMessage());
		}
		//checkTimeFormat():判斷時間格式是否符合:返回LocalTime類
		LocalTime startLocalTime = new CourseServiceImpl().checkTimeFormat(startTime);
		LocalTime endLocalTime = new CourseServiceImpl().checkTimeFormat(endTime);
		//時間格式不符合格式返回null，且開始時間不能比結束時間晚
		if(startLocalTime == null || endLocalTime == null || startLocalTime.getHour()>endLocalTime.getHour()) {
			return new CourseRes(CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}
		Lesson lesson = new Lesson(lessonName,day,startLocalTime,endLocalTime,credits);
		lessonDao.save(lesson);
		return new CourseRes(lesson,CourseRtnCode.CREATE_SUCCESSFUL.getMessage());
	}
	
	/* 更改課堂資訊(課堂代號、課堂名稱、星期幾、開始時間、結束時間、學分) */
	public CourseRes updateLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits) {
		//String類型的參數放進List中
		List<String> strList = Arrays.asList(lessonName,day);
		//判斷字串是否為空字串、null、空、數字小於0
		if(new CourseServiceImpl().checkStringHasText(strList) || lessonId<=0 || credits<0) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		//判斷星期幾是否符合
		if(new CourseServiceImpl().checkDay(day)) {
			return new CourseRes(CourseRtnCode.DAY_ERROR.getMessage());
		}
		//checkTimeFormat():判斷時間格式是否符合:返回LocalTime類
		LocalTime startLocalTime = new CourseServiceImpl().checkTimeFormat(startTime);
		LocalTime endLocalTime = new CourseServiceImpl().checkTimeFormat(endTime);
		//時間格式不符合格式返回null，且開始時間不能比結束時間晚
		if(startLocalTime == null || endLocalTime == null || startLocalTime.getHour()>endLocalTime.getHour()) {
			return new CourseRes(CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			Lesson lesson = lessonOp.get();
			//新的參數覆蓋舊的
			lesson.updateLesson(lessonName, day, startLocalTime, endLocalTime, credits);
			lessonDao.save(lesson);
			return new CourseRes(lesson,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		}else {
			
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	/* 刪除課堂 */
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
	

	/* 新增學生(學號、姓名) */
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

	
	/* 選課(學號、課堂代號) */
	public CourseRes courseSelection(int studentId, List<String> lessonName) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/* 加退選(學號、舊課堂代號、新課堂代號) */
	public CourseRes changeLesson(int studentId, String oldlessonName, String newlessonname) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/* 尋找符合的所有課堂資訊(課堂代碼) */
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

	/* 尋找符合的所有課堂資訊(課堂名稱) */
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
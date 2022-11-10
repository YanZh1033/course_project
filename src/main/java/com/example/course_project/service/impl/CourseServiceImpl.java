package com.example.course_project.service.impl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

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
	
	/*-----------------------------------------------------------------------------------------------------*/
	/* 新增課堂(課堂代號、課堂名稱、星期幾、開始時間、結束時間、學分) */
	@Override
	public CourseRes createLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits){
		String lessonIdStr = lessonId+"";
		String creditsStr = credits+"";
		List<String> strList = Arrays.asList(lessonIdStr,lessonName,day,creditsStr);
		//checkTimeFormat():判斷時間格式是否符合:返回LocalTime類
		LocalTime startLocalTime = checkTimeFormat(startTime);
		LocalTime endLocalTime = checkTimeFormat(endTime);
		
		if(checkStringHasText(strList) || lessonId<=0) {
			return new CourseRes(("參數值不能為空或小於0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(credits <=0 || credits>3) {
			return new CourseRes(("學分必須是介於1~3"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(!checkDay(day)) {
			return new CourseRes(("星期格式不符"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(startLocalTime == null || endLocalTime == null) {
			return new CourseRes(("時間格式不符"),CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}else if(startLocalTime.isAfter(endLocalTime)) {
			return new CourseRes(("開始時間不能晚於結束時間"),CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			return new CourseRes(("課堂代碼已存在"),CourseRtnCode.DATA_EXISTS.getMessage());
		}else {
			Lesson lesson = new Lesson(lessonId,lessonName,day,startLocalTime,endLocalTime,credits);
			lessonDao.save(lesson);
			return new CourseRes(lesson,CourseRtnCode.CREATE_SUCCESSFUL.getMessage());
		}
	}
	
	/* 更改課堂資訊([課堂代號]、課堂名稱、星期幾、開始時間、結束時間、學分) */
	@Override
	public CourseRes updateLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits) {
		String lessonIdStr = lessonId+"";
		String creditsStr = credits+"";
		List<String> strList = Arrays.asList(lessonIdStr,lessonName,day,creditsStr);
		LocalTime startLocalTime = checkTimeFormat(startTime);
		LocalTime endLocalTime = checkTimeFormat(endTime);
		
		if(checkStringHasText(strList) || lessonId<=0) {
			return new CourseRes(("參數值不能為空或小於0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(credits <=0 || credits>3) {
			return new CourseRes(("學分必須是介於1~3"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(!checkDay(day)) {
			return new CourseRes(("星期格式不符"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(startLocalTime == null || endLocalTime == null) {
			return new CourseRes(("時間格式不符"),CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}else if(startLocalTime.isAfter(endLocalTime)) {
			return new CourseRes(("開始時間不能晚於結束時間"),CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			Lesson lesson = lessonOp.get();
			lesson.updateLesson(lessonName, day, startLocalTime, endLocalTime, credits);
			lessonDao.save(lesson);
			return new CourseRes(lesson,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(("無此課堂"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	/* 刪除課堂(課堂代號) */
	@Override
	public CourseRes deleteLesson(int lessonId) {
		String lessonIdStr = lessonId+"";
		if(lessonId<=0 || !StringUtils.hasText(lessonIdStr)) {
			return new CourseRes(("課堂代號不能為空或小於0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			Lesson lesson = lessonOp.get();
			lessonDao.delete(lesson);
			return new CourseRes(CourseRtnCode.DELETE_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(("無此課堂"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	
	/*-----------------------------------------------------------------------------------------------------*/
	/* 新增學生(學號、姓名) */
	@Override
	public CourseRes createStudent(int studentId, String studentName) {
		String studentIdStr = studentId+"";
		List<String> strList = Arrays.asList(studentIdStr,studentName);
		if(checkStringHasText(strList) || studentId<=0) {
			return new CourseRes(("參數值不能為空或小於0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			return new CourseRes(("學號已存在"),CourseRtnCode.DATA_EXISTS.getMessage());
		}else {
			Student newStudent = new Student(studentId,studentName);
			studentDao.save(newStudent);
			return new CourseRes(newStudent,CourseRtnCode.CREATE_SUCCESSFUL.getMessage());
		}
	}
	
	/* 更改學生資訊([學號]、姓名) */
	@Override
	public CourseRes updateStudent(int studentId, String studentName) {
		String studentIdStr = studentId+"";
		List<String> strList = Arrays.asList(studentIdStr,studentName);
		if(checkStringHasText(strList) || studentId<=0) {
			return new CourseRes(("參數值不能為空或小於0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			student.setStudentName(studentName);
			studentDao.save(student);
			return new CourseRes(student,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		}
		return new CourseRes(("找不到此學生"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
	}
	
	/* 刪除學生(學號) */
	@Override
	public CourseRes deleteStudent(int studentId) {
		String studentIdStr = studentId+"";
		if(studentId<=0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(("學號不能為空或小於0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			studentDao.delete(student);
			return new CourseRes(CourseRtnCode.DELETE_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(("找不到此學生"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	
	/*-----------------------------------------------------------------------------------------------------*/	
	/* 選課(學號、課堂代號) */
	@Override
	public CourseRes courseSelection(int studentId, Set<Integer> lessonIdSet) {
		String studentIdStr = studentId+"";
		if(studentId <= 0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(("學號不能為空或小於0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(lessonIdSet.isEmpty()) {
			return new CourseRes(("選課代號不能為空"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			String notFoundId = checkHasLesson(lessonIdSet);
			if(StringUtils.hasText(student.getLessonId())) {
				return new CourseRes(("學生已選過課，請至加選選課"),CourseRtnCode.SELECT_LESSON_ALREADY.getMessage());
			}else if(StringUtils.hasText(notFoundId)) {
				return new CourseRes(("無此課堂:"+notFoundId),CourseRtnCode.DATA_NOT_FOUND.getMessage());
			}
			List<Lesson> lessonOp = lessonDao.findAllByLessonIdIn(lessonIdSet);
			Map<Integer,String> reMap = checkClash(lessonOp);
			for(Entry<Integer,String> item : reMap.entrySet()) {
				switch(item.getKey()) {
				case 1:
					return new CourseRes(("已有相同名稱課:"+item.getValue()),CourseRtnCode.CLASH_LESSON.getMessage());
				case 2:
					return new CourseRes(("此兩課堂時間衝堂:"+item.getValue()),CourseRtnCode.CLASH_LESSON.getMessage());
				case 3:
					return new CourseRes(("學分超過10"),CourseRtnCode.CREDITS_ERROR.getMessage());
				default:
					String lessonInfoStr = item.getValue();
					student.setLessonId(lessonInfoStr);
				}
			}
			studentDao.save(student);
			return new CourseRes(student,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		} else {
			return new CourseRes(("無此學生"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	/* 加退選(學號、課堂代號) 0:加選，1:退選*/
	public CourseRes addOrDropLesson(int addDrop, int studentId, Set<Integer> lessonIdSet) {
		String studentIdStr = studentId+"";
		String addDropStr = addDrop+"";
		if(studentId <= 0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(("學號不能為空或小於0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(lessonIdSet.isEmpty()) {
			return new CourseRes(("選課代號不能為空"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if((addDrop != 0 && addDrop != 1) || !StringUtils.hasText(addDropStr)) {
			return new CourseRes(("0:加選，1:退選，請確認輸入"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
				String notFoundId = checkHasLesson(lessonIdSet);
				String addDropResult = addOrDrop(addDrop, student, lessonIdSet);
				if(!StringUtils.hasText(student.getLessonId())) {
					return new CourseRes(("學生尚未選課，請先進行選課"),CourseRtnCode.NOT_SELECT_LESSON.getMessage());
				}else if(StringUtils.hasText(notFoundId)) {
					return new CourseRes(("無此課堂:"+notFoundId),CourseRtnCode.DATA_NOT_FOUND.getMessage());
				}
				if(addDrop == 0) {
					if(StringUtils.hasText(addDropResult)) {
						return new CourseRes(("已選擇相同的課堂:"+addDropResult),CourseRtnCode.SELECT_LESSON_ALREADY.getMessage());
					}
				}
				if(addDrop == 1) {
					if(StringUtils.hasText(addDropResult)) {
						return new CourseRes(("已選課堂中並無此課堂:"+addDropResult),CourseRtnCode.DATA_NOT_FOUND.getMessage());
					}
				}
			List<Lesson> lessonOp = lessonDao.findAllByLessonIdIn(lessonIdSet);
			Map<Integer,String> reMap = checkClash(lessonOp);
			for(Entry<Integer,String> item : reMap.entrySet()) {
				switch(item.getKey()) {
				case 1:
					return new CourseRes(("已有相同名稱課:"+item.getValue()),CourseRtnCode.CLASH_LESSON.getMessage());
				case 2:
					return new CourseRes(("此兩課堂時間衝堂:"+item.getValue()),CourseRtnCode.CLASH_LESSON.getMessage());
				case 3:
					return new CourseRes(("學分超過10"),CourseRtnCode.CREDITS_ERROR.getMessage());
				default:
					String lessonInfoStr = item.getValue();
					student.setLessonId(lessonInfoStr);
				}
			}
			studentDao.save(student);
			return new CourseRes(student,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		} else {
			return new CourseRes(("無此學生"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	/* 依學號查詢選上的課資訊 */
	@Override
	public CourseRes getSelectLessonInfo(int studentId) {
		String studentIdStr = studentId+"";
		if(studentId <= 0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(("學號不得為空或小於0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			//判斷學生是否選課
			if(!StringUtils.hasText(student.getLessonId())) {
				return new CourseRes(("此學生尚未選課"),CourseRtnCode.NOT_SELECT_LESSON.getMessage());
			}
			//找學生選的所有的課程資訊
			Map<String,String> lessonInfo = studentLessonInfoToMap(student.getLessonId());
			//將學生選課資訊字串Id轉成int並存入陣列中
			Set<Integer> lessonIdSet = new HashSet<>();
			for(Entry<String,String> item : lessonInfo.entrySet()) {
				lessonIdSet.add(Integer.parseInt(item.getKey()));
			}
			//把儲存課程id陣列帶入所有課堂表並返回符合的課堂
			List<Lesson> lessonList = lessonDao.findAllByLessonIdIn(lessonIdSet);
			return new CourseRes(student, lessonList,CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(("找不到此學生"),CourseRtnCode.DATA_NOT_FOUND.getMessage()); 
		}
	}
	
	/* 尋找符合的所有課堂資訊(課堂代碼) */
	@Override
	public CourseRes getLessonInfoByLessonId(int lessonId) {
		String lessonIdStr = lessonId+"";
		if(lessonId <= 0 || !StringUtils.hasLength(lessonIdStr)) {
			return new CourseRes(("學號不得為空或小於0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			Lesson lesson = lessonOp.get();
			return new CourseRes(lesson, CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(("找不到此課堂"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}

	/* 尋找符合的所有課堂資訊(課堂名稱) */
	@Override
	public CourseRes getLessonInfoByLessonName(String lessonName) {
		if(!StringUtils.hasText(lessonName)) {
			return new CourseRes(("課堂名稱不得為空"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		List<Lesson> lessonList = lessonDao.findByLessonName(lessonName);
		if(lessonList.isEmpty()) {
			return new CourseRes(("沒有該名稱的課堂"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}else {
			return new CourseRes(lessonList,CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}
	}
	
	
	/*(內部方法)-----------------------------------------------------------------------------------------------------*/
	/* 判斷為空字串、null、空; [參數:字串陣列]、[返回:(是空字串、null、空)->true] */
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
	
	/* 判斷星期格式; [參數:星期字串]、[返回:(不為任一星期)->true] */
	private boolean checkDay(String day) {
		List<String> week = Arrays.asList("日","一","二","三","四","五","六");
		return week.contains(day);
	}
	
	/* 判斷時間格式; [參數:時間字串]、[返回:(符合格式)->轉為LocalTime] */
	private LocalTime checkTimeFormat(String timeStr) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime localTime = LocalTime.parse(timeStr, dtf);
			return localTime;
		} catch (Exception e) {
			return null;
		}
	}
	
	/* 取回學生的課堂資訊; [參數:student的課堂字串]、[返回:Map<課堂代號,課堂名稱>(String)] */
	private Map<String,String> studentLessonInfoToMap(String lessonInfo) {
		Map<String,String> map = new HashMap<>();
		//lessonInfo:=(String){1=java, 2=C++}、拆分map(1,java)儲存
		String[] str = lessonInfo.split(",");
		for(String item : str){
			String str2 = item.trim();
			String[] info = str2.split("=");
			map.put(info[0],info[1]);
		}
		return map;
	}
	
	/* 確認輸入的選課是否有相符DB中課堂; [參數:選擇的課堂代號]、[返回:輸入不存在於DB的課堂代號] */
	private String checkHasLesson(Set<Integer> selectLesson) {
		List<Lesson> allLesson = lessonDao.findAll();
		List<Integer> allLessonId = new ArrayList<>();
		Set<Integer> selectLesson2 = new HashSet<>();
		selectLesson2.addAll(selectLesson);
		for(Lesson itemAll : allLesson) {
			 allLessonId.add(itemAll.getLessonId());
		}
		selectLesson2.removeAll(allLessonId);
		String setToStr = selectLesson2.toString();
		String nonId = setToStr.substring(1,setToStr.length()-1);
		return nonId;
	}
	
	/* 判斷衝堂; [參數:從DB求得的選擇課堂代號]、[返回:switch()判斷值，錯誤值] */
	private Map<Integer,String> checkClash(List<Lesson> selectLesson) {
		int allCredits = 0;
		List<String> lessonNameList = new ArrayList<>();	//課堂名稱，為判斷是否同名
		Map<Integer,String> lessonInfoMap = new HashMap<>();//<課堂代號，課堂名稱>
		List<List<String>> idTimeList = new ArrayList<>();//存此次選擇的所有課堂時間資訊[(String)課堂代號+星期+開始時間+結束時間](多個)
		Map<Integer,String> reMap = new HashMap<>();	//返回值:為利用switch判斷多個結果，並返回該次錯誤的值<switch判斷值,錯誤資料>
		
		for(Lesson lessonInfo:selectLesson) {
			//課堂名稱，為判斷是否同名
			lessonNameList.add(lessonInfo.getLessonName());
			//單次課堂資訊(課堂代號,課堂星期,課堂開始時間,課堂結束時間)，並存多個
			List<String> selectTimeInfo = Arrays.asList(String.valueOf(lessonInfo.getLessonId()),lessonInfo.getDay(),String.valueOf(lessonInfo.getStartTime()),String.valueOf(lessonInfo.getEndTime()));
			idTimeList.add(selectTimeInfo);
			//儲存所有選課資訊(課堂代號，課堂名稱)，為轉字串存入BD中
			lessonInfoMap.put(lessonInfo.getLessonId(), lessonInfo.getLessonName());
			allCredits += lessonInfo.getCredits();
		}
		/* 判斷課堂名稱是否重複; [返回:<1,相同的課堂名稱>] */
		for(int i=0; i<lessonNameList.size(); i++) {
			String name1 = lessonNameList.get(i);
			for(int j=i+1; j<lessonNameList.size(); j++) {
				String name2 = lessonNameList.get(j);
				if(name1.equalsIgnoreCase(name2)) {
					reMap.put(1, name1);
					return reMap;
				}
			}
		}
		/* 判斷是否同時間; [返回:<2,兩衝堂的課程代碼>]、(1,五,09:00,10:00)*/	
		for(int i=0; i<idTimeList.size(); i++) {
			String day1 = idTimeList.get(i).get(1);
			for(int j=i+1; j<idTimeList.size(); j++) {
				String day2 = idTimeList.get(j).get(1);
				if(day1.equalsIgnoreCase(day2)) {
					LocalTime startTime1 = checkTimeFormat(idTimeList.get(i).get(2));
					LocalTime endTime1 = checkTimeFormat(idTimeList.get(i).get(3));
					LocalTime startTime2 =  checkTimeFormat(idTimeList.get(j).get(2));
					LocalTime endTime2 = checkTimeFormat(idTimeList.get(j).get(3));
					if(isBetween(startTime1, endTime1, startTime2, endTime2)) {
						reMap.put(2,idTimeList.get(i).get(0)+","+idTimeList.get(j).get(0));
						return reMap;
					}
				}
			}
		}
		/* 判斷學分[返回值:<3,null>] */
		if(allCredits>10) {
			reMap.put(3, null);
			return reMap;
		}
		reMap.put(0, lessonInfoMap.toString().substring(1,lessonInfoMap.toString().length()-1));
		return reMap;
	}
	
	/* 判斷時間衝堂; [參數:範圍開始時間,範圍結束時間,判斷時間]、[返回:衝堂:true] */
	private boolean isBetween(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
		return !(start1.isAfter(end2)) && !(end1.isBefore(start2));
	}
	
	/* 判斷此次選的課是否已經選過; [參數:[加選:0;退選:1],學生,此次選擇的課堂]、[返回:重複選的課程代號] */
	private String addOrDrop(int addDrop, Student student, Set<Integer> lessonIdSet){
		Set<Integer> oldLessonIdSet = new HashSet<>();	//學生已選的課堂代號
		List<Integer> sameNameList = new ArrayList<>();
		Set<Integer> lessonIdSet2 = new HashSet<>();
		lessonIdSet2.addAll(lessonIdSet);
		String lessonStrInfo = student.getLessonId();
		String[] str = lessonStrInfo.split(",");
		for(String item : str){
			String str2 = item.trim();
			String[] info = str2.split("=");
			oldLessonIdSet.add(Integer.parseInt(info[0]));
		}
		if(addDrop == 0) {
			for(Integer oldLessonIdItem:oldLessonIdSet) {
				for(Integer lessonItem:lessonIdSet) {
					if(oldLessonIdItem == lessonItem) {
						sameNameList.add(lessonItem);
					}
				}
			}
			String listToStr = sameNameList.toString();
			String sameName = listToStr.substring(1,listToStr.length()-1);
			lessonIdSet.addAll(oldLessonIdSet);
			return sameName;
		} else if(addDrop == 1) {
			if(!oldLessonIdSet.containsAll(lessonIdSet)) {
				lessonIdSet2.removeAll(oldLessonIdSet);
				String listToStr = lessonIdSet2.toString();
				String dropLesson = listToStr.substring(1,listToStr.length()-1);
				return dropLesson;
			}else {
				oldLessonIdSet.removeAll(lessonIdSet2);
				lessonIdSet.clear();
				lessonIdSet.addAll(oldLessonIdSet);
				return null;
			}
		}
		return null;
	}	
	
	
}
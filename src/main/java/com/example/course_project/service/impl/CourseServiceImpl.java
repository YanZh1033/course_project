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
	/* �s�W�Ұ�(�Ұ�N���B�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	@Override
	public CourseRes createLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits){
		String lessonIdStr = lessonId+"";
		String creditsStr = credits+"";
		List<String> strList = Arrays.asList(lessonIdStr,lessonName,day,creditsStr);
		//checkTimeFormat():�P�_�ɶ��榡�O�_�ŦX:��^LocalTime��
		LocalTime startLocalTime = checkTimeFormat(startTime);
		LocalTime endLocalTime = checkTimeFormat(endTime);
		
		if(checkStringHasText(strList) || lessonId<=0) {
			return new CourseRes(("�ѼƭȤ��ର�ũΤp��0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(credits <=0 || credits>3) {
			return new CourseRes(("�Ǥ������O����1~3"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(!checkDay(day)) {
			return new CourseRes(("�P���榡����"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(startLocalTime == null || endLocalTime == null) {
			return new CourseRes(("�ɶ��榡����"),CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}else if(startLocalTime.isAfter(endLocalTime)) {
			return new CourseRes(("�}�l�ɶ�����ߩ󵲧��ɶ�"),CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			return new CourseRes(("�Ұ�N�X�w�s�b"),CourseRtnCode.DATA_EXISTS.getMessage());
		}else {
			Lesson lesson = new Lesson(lessonId,lessonName,day,startLocalTime,endLocalTime,credits);
			lessonDao.save(lesson);
			return new CourseRes(lesson,CourseRtnCode.CREATE_SUCCESSFUL.getMessage());
		}
	}
	
	/* ���Ұ��T([�Ұ�N��]�B�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	@Override
	public CourseRes updateLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits) {
		String lessonIdStr = lessonId+"";
		String creditsStr = credits+"";
		List<String> strList = Arrays.asList(lessonIdStr,lessonName,day,creditsStr);
		LocalTime startLocalTime = checkTimeFormat(startTime);
		LocalTime endLocalTime = checkTimeFormat(endTime);
		
		if(checkStringHasText(strList) || lessonId<=0) {
			return new CourseRes(("�ѼƭȤ��ର�ũΤp��0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(credits <=0 || credits>3) {
			return new CourseRes(("�Ǥ������O����1~3"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(!checkDay(day)) {
			return new CourseRes(("�P���榡����"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(startLocalTime == null || endLocalTime == null) {
			return new CourseRes(("�ɶ��榡����"),CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}else if(startLocalTime.isAfter(endLocalTime)) {
			return new CourseRes(("�}�l�ɶ�����ߩ󵲧��ɶ�"),CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			Lesson lesson = lessonOp.get();
			lesson.updateLesson(lessonName, day, startLocalTime, endLocalTime, credits);
			lessonDao.save(lesson);
			return new CourseRes(lesson,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(("�L���Ұ�"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	/* �R���Ұ�(�Ұ�N��) */
	@Override
	public CourseRes deleteLesson(int lessonId) {
		String lessonIdStr = lessonId+"";
		if(lessonId<=0 || !StringUtils.hasText(lessonIdStr)) {
			return new CourseRes(("�Ұ�N�����ର�ũΤp��0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			Lesson lesson = lessonOp.get();
			lessonDao.delete(lesson);
			return new CourseRes(CourseRtnCode.DELETE_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(("�L���Ұ�"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	
	/*-----------------------------------------------------------------------------------------------------*/
	/* �s�W�ǥ�(�Ǹ��B�m�W) */
	@Override
	public CourseRes createStudent(int studentId, String studentName) {
		String studentIdStr = studentId+"";
		List<String> strList = Arrays.asList(studentIdStr,studentName);
		if(checkStringHasText(strList) || studentId<=0) {
			return new CourseRes(("�ѼƭȤ��ର�ũΤp��0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			return new CourseRes(("�Ǹ��w�s�b"),CourseRtnCode.DATA_EXISTS.getMessage());
		}else {
			Student newStudent = new Student(studentId,studentName);
			studentDao.save(newStudent);
			return new CourseRes(newStudent,CourseRtnCode.CREATE_SUCCESSFUL.getMessage());
		}
	}
	
	/* ���ǥ͸�T([�Ǹ�]�B�m�W) */
	@Override
	public CourseRes updateStudent(int studentId, String studentName) {
		String studentIdStr = studentId+"";
		List<String> strList = Arrays.asList(studentIdStr,studentName);
		if(checkStringHasText(strList) || studentId<=0) {
			return new CourseRes(("�ѼƭȤ��ର�ũΤp��0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			student.setStudentName(studentName);
			studentDao.save(student);
			return new CourseRes(student,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		}
		return new CourseRes(("�䤣�즹�ǥ�"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
	}
	
	/* �R���ǥ�(�Ǹ�) */
	@Override
	public CourseRes deleteStudent(int studentId) {
		String studentIdStr = studentId+"";
		if(studentId<=0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(("�Ǹ����ର�ũΤp��0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			studentDao.delete(student);
			return new CourseRes(CourseRtnCode.DELETE_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(("�䤣�즹�ǥ�"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	
	/*-----------------------------------------------------------------------------------------------------*/	
	/* ���(�Ǹ��B�Ұ�N��) */
	@Override
	public CourseRes courseSelection(int studentId, Set<Integer> lessonIdSet) {
		String studentIdStr = studentId+"";
		if(studentId <= 0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(("�Ǹ����ର�ũΤp��0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(lessonIdSet.isEmpty()) {
			return new CourseRes(("��ҥN�����ର��"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			String notFoundId = checkHasLesson(lessonIdSet);
			if(StringUtils.hasText(student.getLessonId())) {
				return new CourseRes(("�ǥͤw��L�ҡA�Цܥ[����"),CourseRtnCode.SELECT_LESSON_ALREADY.getMessage());
			}else if(StringUtils.hasText(notFoundId)) {
				return new CourseRes(("�L���Ұ�:"+notFoundId),CourseRtnCode.DATA_NOT_FOUND.getMessage());
			}
			List<Lesson> lessonOp = lessonDao.findAllByLessonIdIn(lessonIdSet);
			Map<Integer,String> reMap = checkClash(lessonOp);
			for(Entry<Integer,String> item : reMap.entrySet()) {
				switch(item.getKey()) {
				case 1:
					return new CourseRes(("�w���ۦP�W�ٽ�:"+item.getValue()),CourseRtnCode.CLASH_LESSON.getMessage());
				case 2:
					return new CourseRes(("����Ұ�ɶ��İ�:"+item.getValue()),CourseRtnCode.CLASH_LESSON.getMessage());
				case 3:
					return new CourseRes(("�Ǥ��W�L10"),CourseRtnCode.CREDITS_ERROR.getMessage());
				default:
					String lessonInfoStr = item.getValue();
					student.setLessonId(lessonInfoStr);
				}
			}
			studentDao.save(student);
			return new CourseRes(student,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		} else {
			return new CourseRes(("�L���ǥ�"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	/* �[�h��(�Ǹ��B�Ұ�N��) 0:�[��A1:�h��*/
	public CourseRes addOrDropLesson(int addDrop, int studentId, Set<Integer> lessonIdSet) {
		String studentIdStr = studentId+"";
		String addDropStr = addDrop+"";
		if(studentId <= 0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(("�Ǹ����ର�ũΤp��0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if(lessonIdSet.isEmpty()) {
			return new CourseRes(("��ҥN�����ର��"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}else if((addDrop != 0 && addDrop != 1) || !StringUtils.hasText(addDropStr)) {
			return new CourseRes(("0:�[��A1:�h��A�нT�{��J"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
				String notFoundId = checkHasLesson(lessonIdSet);
				String addDropResult = addOrDrop(addDrop, student, lessonIdSet);
				if(!StringUtils.hasText(student.getLessonId())) {
					return new CourseRes(("�ǥͩ|����ҡA�Х��i����"),CourseRtnCode.NOT_SELECT_LESSON.getMessage());
				}else if(StringUtils.hasText(notFoundId)) {
					return new CourseRes(("�L���Ұ�:"+notFoundId),CourseRtnCode.DATA_NOT_FOUND.getMessage());
				}
				if(addDrop == 0) {
					if(StringUtils.hasText(addDropResult)) {
						return new CourseRes(("�w��ܬۦP���Ұ�:"+addDropResult),CourseRtnCode.SELECT_LESSON_ALREADY.getMessage());
					}
				}
				if(addDrop == 1) {
					if(StringUtils.hasText(addDropResult)) {
						return new CourseRes(("�w��Ұ󤤨õL���Ұ�:"+addDropResult),CourseRtnCode.DATA_NOT_FOUND.getMessage());
					}
				}
			List<Lesson> lessonOp = lessonDao.findAllByLessonIdIn(lessonIdSet);
			Map<Integer,String> reMap = checkClash(lessonOp);
			for(Entry<Integer,String> item : reMap.entrySet()) {
				switch(item.getKey()) {
				case 1:
					return new CourseRes(("�w���ۦP�W�ٽ�:"+item.getValue()),CourseRtnCode.CLASH_LESSON.getMessage());
				case 2:
					return new CourseRes(("����Ұ�ɶ��İ�:"+item.getValue()),CourseRtnCode.CLASH_LESSON.getMessage());
				case 3:
					return new CourseRes(("�Ǥ��W�L10"),CourseRtnCode.CREDITS_ERROR.getMessage());
				default:
					String lessonInfoStr = item.getValue();
					student.setLessonId(lessonInfoStr);
				}
			}
			studentDao.save(student);
			return new CourseRes(student,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		} else {
			return new CourseRes(("�L���ǥ�"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	/* �̾Ǹ��d�߿�W���Ҹ�T */
	@Override
	public CourseRes getSelectLessonInfo(int studentId) {
		String studentIdStr = studentId+"";
		if(studentId <= 0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(("�Ǹ����o���ũΤp��0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			//�P�_�ǥͬO�_���
			if(!StringUtils.hasText(student.getLessonId())) {
				return new CourseRes(("���ǥͩ|�����"),CourseRtnCode.NOT_SELECT_LESSON.getMessage());
			}
			//��ǥͿ諸�Ҧ����ҵ{��T
			Map<String,String> lessonInfo = studentLessonInfoToMap(student.getLessonId());
			//�N�ǥͿ�Ҹ�T�r��Id�নint�æs�J�}�C��
			Set<Integer> lessonIdSet = new HashSet<>();
			for(Entry<String,String> item : lessonInfo.entrySet()) {
				lessonIdSet.add(Integer.parseInt(item.getKey()));
			}
			//���x�s�ҵ{id�}�C�a�J�Ҧ��Ұ��ê�^�ŦX���Ұ�
			List<Lesson> lessonList = lessonDao.findAllByLessonIdIn(lessonIdSet);
			return new CourseRes(student, lessonList,CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(("�䤣�즹�ǥ�"),CourseRtnCode.DATA_NOT_FOUND.getMessage()); 
		}
	}
	
	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�N�X) */
	@Override
	public CourseRes getLessonInfoByLessonId(int lessonId) {
		String lessonIdStr = lessonId+"";
		if(lessonId <= 0 || !StringUtils.hasLength(lessonIdStr)) {
			return new CourseRes(("�Ǹ����o���ũΤp��0"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		if(lessonOp.isPresent()) {
			Lesson lesson = lessonOp.get();
			return new CourseRes(lesson, CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(("�䤣�즹�Ұ�"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}

	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�W��) */
	@Override
	public CourseRes getLessonInfoByLessonName(String lessonName) {
		if(!StringUtils.hasText(lessonName)) {
			return new CourseRes(("�Ұ�W�٤��o����"),CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		List<Lesson> lessonList = lessonDao.findByLessonName(lessonName);
		if(lessonList.isEmpty()) {
			return new CourseRes(("�S���ӦW�٪��Ұ�"),CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}else {
			return new CourseRes(lessonList,CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}
	}
	
	
	/*(������k)-----------------------------------------------------------------------------------------------------*/
	/* �P�_���Ŧr��Bnull�B��; [�Ѽ�:�r��}�C]�B[��^:(�O�Ŧr��Bnull�B��)->true] */
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
	
	/* �P�_�P���榡; [�Ѽ�:�P���r��]�B[��^:(�������@�P��)->true] */
	private boolean checkDay(String day) {
		List<String> week = Arrays.asList("��","�@","�G","�T","�|","��","��");
		return week.contains(day);
	}
	
	/* �P�_�ɶ��榡; [�Ѽ�:�ɶ��r��]�B[��^:(�ŦX�榡)->�ରLocalTime] */
	private LocalTime checkTimeFormat(String timeStr) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime localTime = LocalTime.parse(timeStr, dtf);
			return localTime;
		} catch (Exception e) {
			return null;
		}
	}
	
	/* ���^�ǥͪ��Ұ��T; [�Ѽ�:student���Ұ�r��]�B[��^:Map<�Ұ�N��,�Ұ�W��>(String)] */
	private Map<String,String> studentLessonInfoToMap(String lessonInfo) {
		Map<String,String> map = new HashMap<>();
		//lessonInfo:=(String){1=java, 2=C++}�B���map(1,java)�x�s
		String[] str = lessonInfo.split(",");
		for(String item : str){
			String str2 = item.trim();
			String[] info = str2.split("=");
			map.put(info[0],info[1]);
		}
		return map;
	}
	
	/* �T�{��J����ҬO�_���۲�DB���Ұ�; [�Ѽ�:��ܪ��Ұ�N��]�B[��^:��J���s�b��DB���Ұ�N��] */
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
	
	/* �P�_�İ�; [�Ѽ�:�qDB�D�o����ܽҰ�N��]�B[��^:switch()�P�_�ȡA���~��] */
	private Map<Integer,String> checkClash(List<Lesson> selectLesson) {
		int allCredits = 0;
		List<String> lessonNameList = new ArrayList<>();	//�Ұ�W�١A���P�_�O�_�P�W
		Map<Integer,String> lessonInfoMap = new HashMap<>();//<�Ұ�N���A�Ұ�W��>
		List<List<String>> idTimeList = new ArrayList<>();//�s������ܪ��Ҧ��Ұ�ɶ���T[(String)�Ұ�N��+�P��+�}�l�ɶ�+�����ɶ�](�h��)
		Map<Integer,String> reMap = new HashMap<>();	//��^��:���Q��switch�P�_�h�ӵ��G�A�ê�^�Ӧ����~����<switch�P�_��,���~���>
		
		for(Lesson lessonInfo:selectLesson) {
			//�Ұ�W�١A���P�_�O�_�P�W
			lessonNameList.add(lessonInfo.getLessonName());
			//�榸�Ұ��T(�Ұ�N��,�Ұ�P��,�Ұ�}�l�ɶ�,�Ұ󵲧��ɶ�)�A�æs�h��
			List<String> selectTimeInfo = Arrays.asList(String.valueOf(lessonInfo.getLessonId()),lessonInfo.getDay(),String.valueOf(lessonInfo.getStartTime()),String.valueOf(lessonInfo.getEndTime()));
			idTimeList.add(selectTimeInfo);
			//�x�s�Ҧ���Ҹ�T(�Ұ�N���A�Ұ�W��)�A����r��s�JBD��
			lessonInfoMap.put(lessonInfo.getLessonId(), lessonInfo.getLessonName());
			allCredits += lessonInfo.getCredits();
		}
		/* �P�_�Ұ�W�٬O�_����; [��^:<1,�ۦP���Ұ�W��>] */
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
		/* �P�_�O�_�P�ɶ�; [��^:<2,��İ󪺽ҵ{�N�X>]�B(1,��,09:00,10:00)*/	
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
		/* �P�_�Ǥ�[��^��:<3,null>] */
		if(allCredits>10) {
			reMap.put(3, null);
			return reMap;
		}
		reMap.put(0, lessonInfoMap.toString().substring(1,lessonInfoMap.toString().length()-1));
		return reMap;
	}
	
	/* �P�_�ɶ��İ�; [�Ѽ�:�d��}�l�ɶ�,�d�򵲧��ɶ�,�P�_�ɶ�]�B[��^:�İ�:true] */
	private boolean isBetween(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
		return !(start1.isAfter(end2)) && !(end1.isBefore(start2));
	}
	
	/* �P�_�����諸�ҬO�_�w�g��L; [�Ѽ�:[�[��:0;�h��:1],�ǥ�,������ܪ��Ұ�]�B[��^:���ƿ諸�ҵ{�N��] */
	private String addOrDrop(int addDrop, Student student, Set<Integer> lessonIdSet){
		Set<Integer> oldLessonIdSet = new HashSet<>();	//�ǥͤw�諸�Ұ�N��
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
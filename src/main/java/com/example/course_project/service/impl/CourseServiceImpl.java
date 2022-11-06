package com.example.course_project.service.impl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
	
	/*------------------------------------------------------------------------------*/
	/* �s�W�Ұ�(�Ұ�N���B�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	public CourseRes createLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits){
		String lessonIdStr = lessonId+"";
		String creditsStr = credits+"";
		List<String> strList = Arrays.asList(lessonIdStr,lessonName,day,creditsStr);
		//�P�_�r��ŵ��Bid�j��0�B�Ǥ�1~3
		if(new CourseServiceImpl().checkStringHasText(strList) || lessonId<=0 || credits<=0 || credits>3) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		//�P�_�P���X�O�_�ŦX
		if(new CourseServiceImpl().checkDay(day)) {
			return new CourseRes(CourseRtnCode.DAY_FORMAT_ERROR.getMessage());
		}
		//checkTimeFormat():�P�_�ɶ��榡�O�_�ŦX:��^LocalTime��
		LocalTime startLocalTime = new CourseServiceImpl().checkTimeFormat(startTime);
		LocalTime endLocalTime = new CourseServiceImpl().checkTimeFormat(endTime);
		//�ɶ��榡���ŦX�榡��^null�A�B�}�l�ɶ�����񵲧��ɶ���
		if(startLocalTime == null || endLocalTime == null || startLocalTime.isAfter(endLocalTime)) {
			return new CourseRes(CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		//�ҵ{�N�X�O�_�w�s�b
		if(lessonOp.isPresent()) {
			return new CourseRes(CourseRtnCode.DATA_EXISTS.getMessage());
		}else {
			Lesson lesson = new Lesson(lessonId,lessonName,day,startLocalTime,endLocalTime,credits);
			lessonDao.save(lesson);
			return new CourseRes(lesson,CourseRtnCode.CREATE_SUCCESSFUL.getMessage());
		}
	}
	
	/* ���Ұ��T([�Ұ�N��]�B�Ұ�W�١B�P���X�B�}�l�ɶ��B�����ɶ��B�Ǥ�) */
	public CourseRes updateLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits) {
		String lessonIdStr = lessonId+"";
		String creditsStr = credits+"";
		List<String> strList = Arrays.asList(lessonIdStr,lessonName,day,creditsStr);
		//�P�_�r��O�_���Ŧr��Bnull�B�šB�Ʀr�p��0
		if(new CourseServiceImpl().checkStringHasText(strList) || lessonId<=0 || credits<=0 || credits>3) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		//�P�_�P���X�O�_�ŦX
		if(new CourseServiceImpl().checkDay(day)) {
			return new CourseRes(CourseRtnCode.DAY_FORMAT_ERROR.getMessage());
		}
		//�P�_�ɶ��榡
		LocalTime startLocalTime = new CourseServiceImpl().checkTimeFormat(startTime);
		LocalTime endLocalTime = new CourseServiceImpl().checkTimeFormat(endTime);
		//�ɶ��榡���ŦX�榡��^null�A�B�}�l�ɶ�����񵲧��ɶ���
		if(startLocalTime == null || endLocalTime == null || startLocalTime.isAfter(endLocalTime)) {
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
	
	/* �R���Ұ�(�Ұ�N��) */
	public CourseRes deleteLesson(int lessonId) {
		String lessonIdStr = lessonId+"";
		if(lessonId<=0 || !StringUtils.hasText(lessonIdStr)) {
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
	
	/*------------------------------------------------------------------------------*/
	/* �s�W�ǥ�(�Ǹ��B�m�W) */
	public CourseRes createStudent(int studentId, String studentName) {
		String studentIdStr = studentId+"";
		List<String> strList = Arrays.asList(studentIdStr,studentName);
		if(studentId<=0 || new CourseServiceImpl().checkStringHasText(strList)) {
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
	
	/* ���ǥ͸�T([�Ǹ�]�B�m�W) */
	public CourseRes updateStudent(int studentId, String studentName) {
		String studentIdStr = studentId+"";
		List<String> strList = Arrays.asList(studentIdStr,studentName);
		if(studentId<=0 || new CourseServiceImpl().checkStringHasText(strList)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			student.setStudentName(studentName);
			studentDao.save(student);
			return new CourseRes(student,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		}
		return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
	}
	
	/* �R���ǥ�(�Ǹ�) */
	public CourseRes deleteStudent(int studentId) {
		String studentIdStr = studentId+"";
		if(studentId<=0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			studentDao.delete(student);
			return new CourseRes(CourseRtnCode.DELETE_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	
	/*------------------------------------------------------------------------------*/
	/* ���(�Ǹ��B�Ұ�N��) */
	public CourseRes courseSelection(int studentId, Set<Integer> lessonIdSet) {
		int credits = 0;
		Map<Integer,String> lessonMap = new HashMap<>(); 	//Map(�ҵ{�N��=�ҵ{�W��)
		List<String> lessonNameList = new ArrayList<>();	//DB�ŦXid���Ҧ��ҵ{�W��
		Set<String> lessonNameSet = new HashSet<>();		//���h���ۦP���ҵ{�W��
		Set<Integer> oldLessonIdSet = new HashSet<>();		//�x�s�ǥͤw�諸�Ұ�N��
		List<Map<String,String>> dayTimeStrList = new ArrayList<>(); //�x�s�Ұ�P���ɶ��r��ƾ�(��09:0010:00)�A�}�C:�x�s�Ҧ��ŦX�Ұ�
		List<Integer> allLessonId = new ArrayList<>();		//�Ҧ��Ұ�N��
		
		String studentIdStr = studentId+"";
		if(studentId<=0 || !StringUtils.hasText(studentIdStr) || CollectionUtils.isEmpty(lessonIdSet)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			//�N�ǥ��¦����Ұ�N���X�֨즹����ܪ��Ұ�N����
			if(StringUtils.hasText(student.getLessonId())) {
				Map<String,String> lessonInfo = new CourseServiceImpl().getStudentLesson(student.getLessonId());
				for(Entry<String,String> itemLessonInfo : lessonInfo.entrySet()) {
					oldLessonIdSet.add(Integer.parseInt(itemLessonInfo.getKey()));
				}
				lessonIdSet.addAll(oldLessonIdSet);
			}
			//�P�_�O�_����J���Ұ�N�X
			List<Lesson> allLesson = lessonDao.findAll();
			for(Lesson itemAllLesson : allLesson) {
				allLessonId.add(itemAllLesson.getLessonId());
			}
			if(!allLessonId.containsAll(lessonIdSet)) {
				return new CourseRes(CourseRtnCode.NOT_FOUND_LESSON.getMessage());
			}
			List<Lesson> lessonList = lessonDao.findAllByLessonIdIn(lessonIdSet);
			for(Lesson lesson :lessonList) {
				//�o�X�ŦX���Ұ�A�N�W�٩�JList�PSet��(���P�_�O�_�����ƪ��Ұ�)
				lessonNameList.add(lesson.getLessonName());
				lessonNameSet.add(lesson.getLessonName());
				//�p��Ǥ�
				credits += lesson.getCredits();
				//<�ҵ{�N��,�ҵ{�W��>
				lessonMap.put(lesson.getLessonId(), lesson.getLessonName());
				//<�]�w�ȧP�_�ΰѼ�,�P���ɶ��r��>:("Day","��09:0010:00)
				Map<String,String> dayTimeMap = new HashMap<>();
				dayTimeMap.put("Day",lesson.getDay()+lesson.getStartTime().toString()+lesson.getEndTime().toString());
				dayTimeStrList.add(dayTimeMap);
			}
			if(lessonNameList.size() != lessonNameSet.size() || new CourseServiceImpl().checkClash(dayTimeStrList)) {
				return new CourseRes(CourseRtnCode.CLASH_LESSON.getMessage());
			}else if(credits > 10) {
				return new CourseRes(CourseRtnCode.CREDITS_ERROR.getMessage());
			}
			String lessonMapStr = lessonMap.toString().substring(1,lessonMap.toString().length()-1);
			student.setLessonId(lessonMapStr);
			studentDao.save(student);
			return new CourseRes(student,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		}else{
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}

	
	/* **(TestV1)���(+�[��)(�Ǹ��B�Ұ�N��) */
	//�����~:�p�G �Ұ�}�l�ɶ��ۦP �N�л\�� �Ұ󵲧��ɶ�
	public CourseRes courseSelectionV1(int studentId, Set<Integer> lessonIdSet) {
		int credits = 0;
		Map<Integer,String> lessonMap = new HashMap<>(); 	//Map(�ҵ{�N��=�ҵ{�W��)
		List<String> lessonNameList =  new ArrayList<>();	//DB�ŦXid���Ҧ��ҵ{�W��
		Set<String> lessonNameSet = new HashSet<>();		//���h���ۦP���ҵ{�W��
		Map<LocalTime,LocalTime> lessonTimes = new HashMap<>();	//�x�s��Ұ�}�l�P����
		Map<Integer,String> lessonIdAndDay = new HashMap<>();	//�Ұ�N���B�P��
		if(studentId <= 0 || CollectionUtils.isEmpty(lessonIdSet)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			//���ǥ͡A�N��J���Ұ�N���a�JDB�A��^�ŦX���Ҧ��Ұ�
			List<Lesson> lessonList = lessonDao.findAllByLessonIdIn(lessonIdSet);
			for(Lesson lesson :lessonList) {
				lessonIdAndDay.put(lesson.getLessonId(), lesson.getDay());
				//�o�X�ŦX���Ұ�A�N�W�٩�JList�PSet��(���P�_�O�_�����ƪ��Ұ�)
				lessonNameList.add(lesson.getLessonName());
				lessonNameSet.add(lesson.getLessonName());
				//�p��Ǥ�
				credits += lesson.getCredits();
				//(�Ұ�}�l�ɶ��B�����ɶ�)
				lessonTimes.put(lesson.getStartTime(), lesson.getEndTime());
				//(�Ұ�N�X�B�Ұ�W��)
				lessonMap.put(lesson.getLessonId(), lesson.getLessonName());
				}
			if(lessonNameList.size() != lessonNameSet.size()) {
				return new CourseRes(CourseRtnCode.CLASH_LESSON.getMessage());
			}else if(credits > 10) {
				return new CourseRes(CourseRtnCode.CREDITS_ERROR.getMessage());
			}			
			//�O�_�İ�
			if(new CourseServiceImpl().checkLessonTimeAndDay(lessonIdAndDay, lessonTimes)) {
				return new CourseRes(CourseRtnCode.CLASH_LESSON.getMessage());
			}
			Student student = studentOp.get();
			String lessonMapStr = lessonMap.toString().substring(1,lessonMap.toString().length()-1);
			student.setLessonId(lessonMapStr);
			studentDao.save(student);
			return new CourseRes(student,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	
	/* �h��(�Ǹ��B�Ұ�N��) */
	public CourseRes dropOutLesson(int studentId, Set<Integer> lessonIdSet) {
		List<Integer> deleteLessonId = new ArrayList<>();	//�s�J�n�h�諸�Ұ�N��
		List<String> deleteId = new ArrayList<>();			//�x�s�w��W�P��J�۲šA�N��n�R�����Ұ�
		List<Integer> studentAllLessonId = new ArrayList<>();	//�ǥͤw�g�諸�Ұ�
		
		String studentIdStr = studentId+"";
		if(studentId <= 0 || !StringUtils.hasText(studentIdStr) || CollectionUtils.isEmpty(lessonIdSet)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			List<Lesson> lessonList = lessonDao.findAllByLessonIdIn(lessonIdSet);
			//�s�J�n�h�諸�Ұ�N��
			for(Lesson itemLessonList:lessonList) {
				deleteLessonId.add(itemLessonList.getLessonId());
			}
			//�P�_��J���h��Ұ�N�X�O�_�s�b
			if(!deleteLessonId.containsAll(lessonIdSet)) {
				return new CourseRes(CourseRtnCode.NOT_FOUND_LESSON.getMessage());
			}
			Student student = studentOp.get();
 			Map<String,String> lessonInfo = new CourseServiceImpl().getStudentLesson(student.getLessonId());
			for(Integer itemDelete : deleteLessonId) {
				for(Entry<String,String> itemLessonInfo:lessonInfo.entrySet()) {
					studentAllLessonId.add(Integer.parseInt(itemLessonInfo.getKey()));
					//�p�G��J�h��Ұ�N�X����ǥͤw��W���Ұ�N�X->�x�s��deleteId�}�C��
					if(itemDelete.toString().equals(itemLessonInfo.getKey())) {
						deleteId.add(itemDelete.toString());
					}
				}
			}
			//�P�_�n�h��Ұ�N�X�O�_�ŦX�ǥͤw��W���Ұ�N�X
			if(!studentAllLessonId.containsAll(lessonIdSet)) {
				return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
			}
			//�`���n�R�����Ұ�N���A�a�J�ǥͤw�����Ұ�N���A�p�G�ۦP�h�R��
			for(String itemDeleteId : deleteId) {
				lessonInfo.remove(itemDeleteId);
			}
			String lessonMapStr = lessonInfo.toString().substring(1,lessonInfo.toString().length()-1);
			student.setLessonId(lessonMapStr);
			studentDao.save(student);
			return new CourseRes(student,CourseRtnCode.UPDATE_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}
	}
	
	
	/*------------------------------------------------------------------------------*/
	/* �̾Ǹ��d�߿�W���Ҹ�T */
	public CourseRes getSelectLessonInfo(int studentId) {
		String studentIdStr = studentId+"";
		if(studentId <= 0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			//�P�_�ǥͬO�_���
			if(!StringUtils.hasText(student.getLessonId())) {
				return new CourseRes(CourseRtnCode.NOT_SELECT_LESSON.getMessage());
			}
			//��ǥͿ諸�Ҧ����ҵ{��T
			Map<String,String> lessonInfo = new CourseServiceImpl().getStudentLesson(student.getLessonId());
			//�N�ǥͿ�Ҹ�T�r��Id�নint�æs�J�}�C��
			Set<Integer> lessonIdSet = new HashSet<>();
			for(Entry<String,String> item : lessonInfo.entrySet()) {
				lessonIdSet.add(Integer.parseInt(item.getKey()));
			}
			//���x�s�ҵ{id�}�C�a�J�Ҧ��Ұ��ê�^�ŦX���Ұ�
			List<Lesson> lessonList = lessonDao.findAllByLessonIdIn(lessonIdSet);
			return new CourseRes(student, lessonList,CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage()); 
		}
	}
	
	
	/* �M��ŦX���Ҧ��Ұ��T(�Ұ�N�X) */
	public CourseRes getLessonInfoByLessonId(int lessonId) {
		String lessonIdStr = lessonId+"";
		if(lessonId <= 0 || !StringUtils.hasLength(lessonIdStr)) {
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
		if(CollectionUtils.isEmpty(lessonList)) {
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
		}else {
			return new CourseRes(lessonList,CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}
	}
	
	
	/*(������k)------------------------------------------------------------------------------*/
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
	
	/* �P�_�ɶ��榡; [�Ѽ�:�ɶ��r��]�B[��^:(�ŦX�榡)->�ରLocalTime] */
	private LocalTime checkTimeFormat(String timeStr) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime localTime = LocalTime.parse(timeStr, dtf);
			String localTimeStr = localTime.format(dtf);
			//��� (�r��̳W�w�ɶ��榡���) ��� (��J�ɶ��榡) �O�_�۵�
			if(localTimeStr.equals(timeStr)) {	
				return localTime;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/* ���^�ǥͪ��Ұ��T; [�Ѽ�:student���Ұ�r��]�B[��^:Map<�Ұ�N��,�Ұ�W��>(String)] */
	private Map<String,String> getStudentLesson(String lessonInfo) {
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
	
	/* �P�_�İ�; [�Ѽ�:�꦳(�P��+�Ұ�ɶ�)�r��}�C]�B[��^:���İ�:true] */
	private boolean checkClash(List<Map<String,String>> dayTimeStrList){
		boolean b = false;
		//�q0�`���Ӧr��O�_���ۦP���P��
		for(int i=0; i<dayTimeStrList.size()-1; i++) {
			//Map<"Day","��09:0010:00">�A�Nvalue��Jday1
			String dayTime1 = dayTimeStrList.get(i).get("Day");
			//�q�}�C���`�� j>i:�קK����ۤv,
			for(int j=dayTimeStrList.size()-1; j>i; j--) {
				String dayTime2 = dayTimeStrList.get(j).get("Day");
				//���o�r��index[0]:��09:0010:00 =�� �A �h���P���� -> �P���ۦP�~��ɶ��r����LocalTime
				if(dayTime1.substring(0,1).equals(dayTime2.substring(0,1))) {
					//day1�Ұ󪺶}�l�ɶ�
					LocalTime startLocalTime = new CourseServiceImpl().checkTimeFormat(dayTime1.substring(1,6));
					//day1�Ұ󪺵����ɶ�
					LocalTime endLocalTime = new CourseServiceImpl().checkTimeFormat(dayTime1.substring(6,dayTime1.length()));
					//day2�Ұ󪺶}�l�ɶ�
					LocalTime time = new CourseServiceImpl().checkTimeFormat(dayTime2.substring(1,6));
					if(time.isAfter(startLocalTime)&&time.isBefore(endLocalTime)) {			
						return b = true;
					}
				}
			}
		}
		return b;
	}

	/* **(TestV1)�P�_�İ�; [�Ѽ�:Map<�Ұ�N��,�P��>,Map<�}�l�ɶ�,�����ɶ�>]�B[��^:(���İ�)->true] */
	private boolean checkLessonTimeAndDay(Map<Integer,String> dayMap, Map<LocalTime,LocalTime> timeMap) {
		for(Entry<Integer,String> checkDay : dayMap.entrySet()) {
			for(Entry<Integer,String> checkDay2 : dayMap.entrySet()) {
				if(checkDay.getValue().equals(checkDay2.getValue())){
					if(checkDay.getKey() != checkDay2.getKey()) {
						for(Entry<LocalTime,LocalTime> checkTime : timeMap.entrySet()) {
							for(Entry<LocalTime,LocalTime> checkTime2 : timeMap.entrySet()) {
								if(checkTime2.getKey().isAfter(checkTime.getKey()) || checkTime2.getKey().isBefore(checkTime.getValue())) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}
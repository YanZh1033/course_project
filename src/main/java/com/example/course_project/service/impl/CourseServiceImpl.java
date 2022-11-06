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
	/* 新增課堂(課堂代號、課堂名稱、星期幾、開始時間、結束時間、學分) */
	public CourseRes createLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits){
		String lessonIdStr = lessonId+"";
		String creditsStr = credits+"";
		List<String> strList = Arrays.asList(lessonIdStr,lessonName,day,creditsStr);
		//判斷字串空等、id大於0、學分1~3
		if(new CourseServiceImpl().checkStringHasText(strList) || lessonId<=0 || credits<=0 || credits>3) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		//判斷星期幾是否符合
		if(new CourseServiceImpl().checkDay(day)) {
			return new CourseRes(CourseRtnCode.DAY_FORMAT_ERROR.getMessage());
		}
		//checkTimeFormat():判斷時間格式是否符合:返回LocalTime類
		LocalTime startLocalTime = new CourseServiceImpl().checkTimeFormat(startTime);
		LocalTime endLocalTime = new CourseServiceImpl().checkTimeFormat(endTime);
		//時間格式不符合格式返回null，且開始時間不能比結束時間晚
		if(startLocalTime == null || endLocalTime == null || startLocalTime.isAfter(endLocalTime)) {
			return new CourseRes(CourseRtnCode.TIME_FORMAT_ERROR.getMessage());
		}
		Optional<Lesson> lessonOp = lessonDao.findByLessonId(lessonId);
		//課程代碼是否已存在
		if(lessonOp.isPresent()) {
			return new CourseRes(CourseRtnCode.DATA_EXISTS.getMessage());
		}else {
			Lesson lesson = new Lesson(lessonId,lessonName,day,startLocalTime,endLocalTime,credits);
			lessonDao.save(lesson);
			return new CourseRes(lesson,CourseRtnCode.CREATE_SUCCESSFUL.getMessage());
		}
	}
	
	/* 更改課堂資訊([課堂代號]、課堂名稱、星期幾、開始時間、結束時間、學分) */
	public CourseRes updateLesson(int lessonId, String lessonName, String day, String startTime, String endTime, int credits) {
		String lessonIdStr = lessonId+"";
		String creditsStr = credits+"";
		List<String> strList = Arrays.asList(lessonIdStr,lessonName,day,creditsStr);
		//判斷字串是否為空字串、null、空、數字小於0
		if(new CourseServiceImpl().checkStringHasText(strList) || lessonId<=0 || credits<=0 || credits>3) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		//判斷星期幾是否符合
		if(new CourseServiceImpl().checkDay(day)) {
			return new CourseRes(CourseRtnCode.DAY_FORMAT_ERROR.getMessage());
		}
		//判斷時間格式
		LocalTime startLocalTime = new CourseServiceImpl().checkTimeFormat(startTime);
		LocalTime endLocalTime = new CourseServiceImpl().checkTimeFormat(endTime);
		//時間格式不符合格式返回null，且開始時間不能比結束時間晚
		if(startLocalTime == null || endLocalTime == null || startLocalTime.isAfter(endLocalTime)) {
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
	
	/* 刪除課堂(課堂代號) */
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
	/* 新增學生(學號、姓名) */
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
	
	/* 更改學生資訊([學號]、姓名) */
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
	
	/* 刪除學生(學號) */
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
	/* 選課(學號、課堂代號) */
	public CourseRes courseSelection(int studentId, Set<Integer> lessonIdSet) {
		int credits = 0;
		Map<Integer,String> lessonMap = new HashMap<>(); 	//Map(課程代號=課程名稱)
		List<String> lessonNameList = new ArrayList<>();	//DB符合id的所有課程名稱
		Set<String> lessonNameSet = new HashSet<>();		//為去掉相同的課程名稱
		Set<Integer> oldLessonIdSet = new HashSet<>();		//儲存學生已選的課堂代號
		List<Map<String,String>> dayTimeStrList = new ArrayList<>(); //儲存課堂星期時間字串數據(五09:0010:00)，陣列:儲存所有符合課堂
		List<Integer> allLessonId = new ArrayList<>();		//所有課堂代號
		
		String studentIdStr = studentId+"";
		if(studentId<=0 || !StringUtils.hasText(studentIdStr) || CollectionUtils.isEmpty(lessonIdSet)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			//將學生舊有的課堂代號合併到此次選擇的課堂代號中
			if(StringUtils.hasText(student.getLessonId())) {
				Map<String,String> lessonInfo = new CourseServiceImpl().getStudentLesson(student.getLessonId());
				for(Entry<String,String> itemLessonInfo : lessonInfo.entrySet()) {
					oldLessonIdSet.add(Integer.parseInt(itemLessonInfo.getKey()));
				}
				lessonIdSet.addAll(oldLessonIdSet);
			}
			//判斷是否有輸入的課堂代碼
			List<Lesson> allLesson = lessonDao.findAll();
			for(Lesson itemAllLesson : allLesson) {
				allLessonId.add(itemAllLesson.getLessonId());
			}
			if(!allLessonId.containsAll(lessonIdSet)) {
				return new CourseRes(CourseRtnCode.NOT_FOUND_LESSON.getMessage());
			}
			List<Lesson> lessonList = lessonDao.findAllByLessonIdIn(lessonIdSet);
			for(Lesson lesson :lessonList) {
				//得出符合的課堂，將名稱放入List與Set中(為判斷是否有重複的課堂)
				lessonNameList.add(lesson.getLessonName());
				lessonNameSet.add(lesson.getLessonName());
				//計算學分
				credits += lesson.getCredits();
				//<課程代號,課程名稱>
				lessonMap.put(lesson.getLessonId(), lesson.getLessonName());
				//<設定值判斷用參數,星期時間字串>:("Day","五09:0010:00)
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

	
	/* **(TestV1)選課(+加選)(學號、課堂代號) */
	//有錯誤:如果 課堂開始時間相同 將覆蓋掉 課堂結束時間
	public CourseRes courseSelectionV1(int studentId, Set<Integer> lessonIdSet) {
		int credits = 0;
		Map<Integer,String> lessonMap = new HashMap<>(); 	//Map(課程代號=課程名稱)
		List<String> lessonNameList =  new ArrayList<>();	//DB符合id的所有課程名稱
		Set<String> lessonNameSet = new HashSet<>();		//為去掉相同的課程名稱
		Map<LocalTime,LocalTime> lessonTimes = new HashMap<>();	//儲存單課堂開始與結束
		Map<Integer,String> lessonIdAndDay = new HashMap<>();	//課堂代號、星期
		if(studentId <= 0 || CollectionUtils.isEmpty(lessonIdSet)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			//找到學生，將輸入的課堂代號帶入DB，返回符合的所有課堂
			List<Lesson> lessonList = lessonDao.findAllByLessonIdIn(lessonIdSet);
			for(Lesson lesson :lessonList) {
				lessonIdAndDay.put(lesson.getLessonId(), lesson.getDay());
				//得出符合的課堂，將名稱放入List與Set中(為判斷是否有重複的課堂)
				lessonNameList.add(lesson.getLessonName());
				lessonNameSet.add(lesson.getLessonName());
				//計算學分
				credits += lesson.getCredits();
				//(課堂開始時間、結束時間)
				lessonTimes.put(lesson.getStartTime(), lesson.getEndTime());
				//(課堂代碼、課堂名稱)
				lessonMap.put(lesson.getLessonId(), lesson.getLessonName());
				}
			if(lessonNameList.size() != lessonNameSet.size()) {
				return new CourseRes(CourseRtnCode.CLASH_LESSON.getMessage());
			}else if(credits > 10) {
				return new CourseRes(CourseRtnCode.CREDITS_ERROR.getMessage());
			}			
			//是否衝堂
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
	
	
	/* 退選(學號、課堂代號) */
	public CourseRes dropOutLesson(int studentId, Set<Integer> lessonIdSet) {
		List<Integer> deleteLessonId = new ArrayList<>();	//存入要退選的課堂代號
		List<String> deleteId = new ArrayList<>();			//儲存已選上與輸入相符，代表要刪除的課堂
		List<Integer> studentAllLessonId = new ArrayList<>();	//學生已經選的課堂
		
		String studentIdStr = studentId+"";
		if(studentId <= 0 || !StringUtils.hasText(studentIdStr) || CollectionUtils.isEmpty(lessonIdSet)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			List<Lesson> lessonList = lessonDao.findAllByLessonIdIn(lessonIdSet);
			//存入要退選的課堂代號
			for(Lesson itemLessonList:lessonList) {
				deleteLessonId.add(itemLessonList.getLessonId());
			}
			//判斷輸入的退選課堂代碼是否存在
			if(!deleteLessonId.containsAll(lessonIdSet)) {
				return new CourseRes(CourseRtnCode.NOT_FOUND_LESSON.getMessage());
			}
			Student student = studentOp.get();
 			Map<String,String> lessonInfo = new CourseServiceImpl().getStudentLesson(student.getLessonId());
			for(Integer itemDelete : deleteLessonId) {
				for(Entry<String,String> itemLessonInfo:lessonInfo.entrySet()) {
					studentAllLessonId.add(Integer.parseInt(itemLessonInfo.getKey()));
					//如果輸入退選課堂代碼等於學生已選上的課堂代碼->儲存到deleteId陣列中
					if(itemDelete.toString().equals(itemLessonInfo.getKey())) {
						deleteId.add(itemDelete.toString());
					}
				}
			}
			//判斷要退選課堂代碼是否符合學生已選上的課堂代碼
			if(!studentAllLessonId.containsAll(lessonIdSet)) {
				return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage());
			}
			//循環要刪除的課堂代號，帶入學生已有的課堂代號，如果相同則刪除
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
	/* 依學號查詢選上的課資訊 */
	public CourseRes getSelectLessonInfo(int studentId) {
		String studentIdStr = studentId+"";
		if(studentId <= 0 || !StringUtils.hasText(studentIdStr)) {
			return new CourseRes(CourseRtnCode.VALUE_REQUIRED.getMessage());
		}
		Optional<Student> studentOp = studentDao.findByStudentId(studentId);
		if(studentOp.isPresent()) {
			Student student = studentOp.get();
			//判斷學生是否選課
			if(!StringUtils.hasText(student.getLessonId())) {
				return new CourseRes(CourseRtnCode.NOT_SELECT_LESSON.getMessage());
			}
			//找學生選的所有的課程資訊
			Map<String,String> lessonInfo = new CourseServiceImpl().getStudentLesson(student.getLessonId());
			//將學生選課資訊字串Id轉成int並存入陣列中
			Set<Integer> lessonIdSet = new HashSet<>();
			for(Entry<String,String> item : lessonInfo.entrySet()) {
				lessonIdSet.add(Integer.parseInt(item.getKey()));
			}
			//把儲存課程id陣列帶入所有課堂表並返回符合的課堂
			List<Lesson> lessonList = lessonDao.findAllByLessonIdIn(lessonIdSet);
			return new CourseRes(student, lessonList,CourseRtnCode.FOUND_SUCCESSFUL.getMessage());
		}else {
			return new CourseRes(CourseRtnCode.DATA_NOT_FOUND.getMessage()); 
		}
	}
	
	
	/* 尋找符合的所有課堂資訊(課堂代碼) */
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

	/* 尋找符合的所有課堂資訊(課堂名稱) */
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
	
	
	/*(內部方法)------------------------------------------------------------------------------*/
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
	
	/* 判斷時間格式; [參數:時間字串]、[返回:(符合格式)->轉為LocalTime] */
	private LocalTime checkTimeFormat(String timeStr) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime localTime = LocalTime.parse(timeStr, dtf);
			String localTimeStr = localTime.format(dtf);
			//對比 (字串依規定時間格式轉化) 比較 (輸入時間格式) 是否相等
			if(localTimeStr.equals(timeStr)) {	
				return localTime;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/* 取回學生的課堂資訊; [參數:student的課堂字串]、[返回:Map<課堂代號,課堂名稱>(String)] */
	private Map<String,String> getStudentLesson(String lessonInfo) {
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
	
	/* 判斷衝堂; [參數:串有(星期+課堂時間)字串陣列]、[返回:有衝堂:true] */
	private boolean checkClash(List<Map<String,String>> dayTimeStrList){
		boolean b = false;
		//從0循環該字串是否有相同的星期
		for(int i=0; i<dayTimeStrList.size()-1; i++) {
			//Map<"Day","五09:0010:00">，將value放入day1
			String dayTime1 = dayTimeStrList.get(i).get("Day");
			//從陣列尾循環 j>i:避免對比到自己,
			for(int j=dayTimeStrList.size()-1; j>i; j--) {
				String dayTime2 = dayTimeStrList.get(j).get("Day");
				//取得字串index[0]:五09:0010:00 =五 ， 則為星期五 -> 星期相同才把時間字串轉LocalTime
				if(dayTime1.substring(0,1).equals(dayTime2.substring(0,1))) {
					//day1課堂的開始時間
					LocalTime startLocalTime = new CourseServiceImpl().checkTimeFormat(dayTime1.substring(1,6));
					//day1課堂的結束時間
					LocalTime endLocalTime = new CourseServiceImpl().checkTimeFormat(dayTime1.substring(6,dayTime1.length()));
					//day2課堂的開始時間
					LocalTime time = new CourseServiceImpl().checkTimeFormat(dayTime2.substring(1,6));
					if(time.isAfter(startLocalTime)&&time.isBefore(endLocalTime)) {			
						return b = true;
					}
				}
			}
		}
		return b;
	}

	/* **(TestV1)判斷衝堂; [參數:Map<課堂代號,星期>,Map<開始時間,結束時間>]、[返回:(有衝堂)->true] */
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
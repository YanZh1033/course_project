package com.example.course_project;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest(classes = CourseProjectApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CourseTest {
	
	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@BeforeAll
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	/* 新增課堂(課堂代號、課堂名稱、星期幾、開始時間、結束時間、學分) */
	@SuppressWarnings("unchecked")
	@Test
	public void createLessonControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("lesson_id", 8);
		map.put("lesson_name", "java");
		map.put("day", "五");
		map.put("start_time", "10:00");
		map.put("end_time", "12:00");
		map.put("credits", 2);
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/createLesson")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Created successful"), "Message error!");
		Map<String,Object> rtnInfo =  (Map<String, Object>)resData.get("lesson_info");
		Assert.isTrue(((Integer)rtnInfo.get("lesson_id")).equals(8), "Account error" );
		System.out.println(rtnInfo);
	}
	
	/* 更改課堂資訊([課堂代號]、課堂名稱、星期幾、開始時間、結束時間、學分) */
	@SuppressWarnings("unchecked")
	@Test
	public void updateLessonControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("lesson_id", 8);
		map.put("lesson_name", "java");
		map.put("day", "三");
		map.put("start_time", "14:00");
		map.put("end_time", "16:00");
		map.put("credits", 3);
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/updateLesson")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Update successful"), "Message error!");
		Map<String,Object> rtnInfo =  (Map<String, Object>)resData.get("lesson_info");
		Assert.isTrue(((Integer)rtnInfo.get("lesson_id")).equals(8), "Account error" );
		System.out.println(rtnInfo);
		System.out.println(resData);
	}
	
	/* 刪除課堂(課堂代號) */
	@Test
	public void deleteLessonControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("lesson_id", 8);
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/deleteLesson")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Data delete successful"), "Message error!");
	}

	/* 新增學生(學號、姓名) */
	@SuppressWarnings("unchecked")
	@Test
	public void createStudentControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("student_id", 3);
		map.put("student_name", "Alice");
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/createStudent")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Created successful"), "Message error!");
		Map<String,Object> rtnInfo =  (Map<String, Object>)resData.get("student_info");
		Assert.isTrue(((Integer)rtnInfo.get("student_id")).equals(3), "Account error" );
		System.out.println(rtnInfo);
	}

	/* 更改學生資訊([學號]、姓名) */
	@SuppressWarnings("unchecked")
	@Test
	public void updateStudentControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("student_id", 3);
		map.put("student_name", "Alice2");
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/updateStudent")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Update successful"), "Message error!");
		Map<String,Object> rtnInfo =  (Map<String, Object>)resData.get("student_info");
		Assert.isTrue(((Integer)rtnInfo.get("student_id")).equals(3), "Account error" );
		System.out.println(rtnInfo);
	}

	/* 刪除學生(學號) */
	@Test
	public void deleteStudentControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("student_id", 3);
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/deleteStudent")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Data delete successful"), "Message error!");
	}

	/* 選課(學號、課堂名稱) */
	@SuppressWarnings("unchecked")
	@Test
	public void courseSelectionControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		Set<Integer> idSet = new HashSet<>();
		idSet.add(1);
		idSet.add(2);
		map.put("student_id", 3);
		map.put("lesson_id_set", idSet);
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/courseSelection")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Update successful"), "Message error!");
		Map<String,Object> rtnInfo =  (Map<String, Object>)resData.get("student_info");
		Assert.isTrue(((Integer)rtnInfo.get("student_id")).equals(3), "Account error" );
		System.out.println(rtnInfo);
	}

	/* 退選(學號、課堂代號) */
	@SuppressWarnings("unchecked")
	@Test
	public void dropOutLessonControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		Set<Integer> idSet = new HashSet<>();
		idSet.add(1);
		idSet.add(2);
		map.put("student_id", 3);
		map.put("lesson_id_set", idSet);
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/dropOutLesson")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Update successful"), "Message error!");
		Map<String,Object> rtnInfo =  (Map<String, Object>)resData.get("student_info");
		Assert.isTrue(((Integer)rtnInfo.get("student_id")).equals(3), "Account error" );
		System.out.println(rtnInfo);
	}

	/* 依學號查詢選上的課資訊 */
	@SuppressWarnings("unchecked")
	@Test
	public void getSelectLessonInfoControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("student_id", 3);
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/getSelectLessonInfo")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Data found successful"), "Message error!");
		Map<String,Object> rtnInfo =  (Map<String, Object>)resData.get("student_info");
		Assert.isTrue(((Integer)rtnInfo.get("student_id")).equals(3), "Account error" );
		System.out.println(rtnInfo);
	}

	/* 尋找符合的所有課堂資訊(課堂代碼) */
	@SuppressWarnings("unchecked")
	@Test
	public void getLessonInfoByLessonIdControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("lesson_id", 1);
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/getLessonInfoByLessonId")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Data found successful"), "Message error!");
		Map<String,Object> rtnInfo =  (Map<String, Object>)resData.get("lesson_info");
		Assert.isTrue(((Integer)rtnInfo.get("lesson_id")).equals(1), "Account error" );
		System.out.println(rtnInfo);
	}

	/* 尋找符合的所有課堂資訊(課堂名稱) */
	@SuppressWarnings("unchecked")
	@Test
	public void getLessonInfoByLessonNameControllerTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("lesson_name", "java");
		ObjectMapper objectMapper = new ObjectMapper();
		String mapString = objectMapper.writeValueAsString(map);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/getLessonInfoByLessonName")
				.contentType(CONTENT_TYPE)
				.content(mapString));
		String resString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String,Object> resData = jsonParser.parseMap(resString);
		String rtnMessage = (String) resData.get("message");
		System.out.println(rtnMessage);
		Assert.isTrue(rtnMessage.equals("Data found successful"), "Message error!");
		List<Map<String,Object>> rtnInfo =  (List<Map<String, Object>>)resData.get("all_lesson_info");
		System.out.println(rtnInfo);
	}
	
}

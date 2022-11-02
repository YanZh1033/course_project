package com.example.course_project.constants;

public enum CourseRtnCode {

	
	CREATE_SUCCESSFUL("200","Created successful"),				//創建成功
	UPDATE_SUCCESSFUL("200","Update successful"),				//更新成功
	FOUND_SUCCESSFUL("200","Data found successful"),			//成功找到資料
	DELETE_SUCCESSFUL("200","Data delete successful"),			//成功刪除資料
	VALUE_REQUIRED("400","Value cannot be null or empty or negative"),	//輸入值為空、null、空字串、負數
	TIME_FORMAT_ERROR("400","Time format Error"),				//時間格式錯誤
	DATA_EXISTS("400","Date is exists"),						//資料已存在
	DAY_ERROR("400","Day Error"),								//星期錯誤
	DATA_NOT_FOUND("400","Data not found");						//無此資料

	private String code;
	private String message;
	
	private CourseRtnCode(String code,String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}

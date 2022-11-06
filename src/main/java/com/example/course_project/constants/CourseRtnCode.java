package com.example.course_project.constants;

public enum CourseRtnCode {

	
	CREATE_SUCCESSFUL("200","Created successful"),				//創建成功
	UPDATE_SUCCESSFUL("200","Update successful"),				//更新成功
	FOUND_SUCCESSFUL("200","Data found successful"),			//成功找到資料
	DELETE_SUCCESSFUL("200","Data delete successful"),			//成功刪除資料
	VALUE_REQUIRED("400","Value Error (Cannot be null or empty or negative)"),	//輸入值為空、null、空字串、負數
	NOT_SELECT_LESSON("404","Not select lesson yet"),			//尚未選課
	CLASH_LESSON("400","Clash with another lesson"),			//課堂衝突
	NOT_FOUND_LESSON("404","Not found lesson"),					//無此課堂
	TIME_FORMAT_ERROR("400","Time format Error"),				//時間格式錯誤
	DAY_FORMAT_ERROR("400","Day format Error"),					//星期錯誤
	CREDITS_ERROR("400","Credits Error"),						//學分錯誤
	DATA_NOT_FOUND("404","Data not found"),						//無此資料
	DATA_EXISTS("403","Data exists");							//資料已存在
	
	
	
	
	

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

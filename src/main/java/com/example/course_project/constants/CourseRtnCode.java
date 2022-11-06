package com.example.course_project.constants;

public enum CourseRtnCode {

	
	CREATE_SUCCESSFUL("200","Created successful"),				//�Ыئ��\
	UPDATE_SUCCESSFUL("200","Update successful"),				//��s���\
	FOUND_SUCCESSFUL("200","Data found successful"),			//���\�����
	DELETE_SUCCESSFUL("200","Data delete successful"),			//���\�R�����
	VALUE_REQUIRED("400","Value Error (Cannot be null or empty or negative)"),	//��J�Ȭ��šBnull�B�Ŧr��B�t��
	NOT_SELECT_LESSON("404","Not select lesson yet"),			//�|�����
	CLASH_LESSON("400","Clash with another lesson"),			//�Ұ�Ĭ�
	NOT_FOUND_LESSON("404","Not found lesson"),					//�L���Ұ�
	TIME_FORMAT_ERROR("400","Time format Error"),				//�ɶ��榡���~
	DAY_FORMAT_ERROR("400","Day format Error"),					//�P�����~
	CREDITS_ERROR("400","Credits Error"),						//�Ǥ����~
	DATA_NOT_FOUND("404","Data not found"),						//�L�����
	DATA_EXISTS("403","Data exists");							//��Ƥw�s�b
	
	
	
	
	

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

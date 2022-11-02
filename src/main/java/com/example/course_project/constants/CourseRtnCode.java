package com.example.course_project.constants;

public enum CourseRtnCode {

	
	CREATE_SUCCESSFUL("200","Created successful"),				//�Ыئ��\
	UPDATE_SUCCESSFUL("200","Update successful"),				//��s���\
	FOUND_SUCCESSFUL("200","Data found successful"),			//���\�����
	DELETE_SUCCESSFUL("200","Data delete successful"),			//���\�R�����
	VALUE_REQUIRED("400","Value cannot be null or empty or negative"),	//��J�Ȭ��šBnull�B�Ŧr��B�t��
	TIME_FORMAT_ERROR("400","Time format Error"),				//�ɶ��榡���~
	DATA_EXISTS("400","Date is exists"),						//��Ƥw�s�b
	DAY_ERROR("400","Day Error"),								//�P�����~
	DATA_NOT_FOUND("400","Data not found");						//�L�����

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

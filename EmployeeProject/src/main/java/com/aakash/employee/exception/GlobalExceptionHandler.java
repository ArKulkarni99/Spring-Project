package com.aakash.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = DataNotFoundException.class)
	public ResponseEntity<ErrorMsg> handlerDataNotFoundException (DataNotFoundException dnfe){
		String errMsg = dnfe.getMessage();
		String errCode = "EMPEX001";
		
		ErrorMsg errorMsg = new ErrorMsg();
		errorMsg.setMsg(errMsg);
		errorMsg.setCode(errCode);
		
		return new ResponseEntity<ErrorMsg>(errorMsg, HttpStatus.NOT_FOUND);
	}
}


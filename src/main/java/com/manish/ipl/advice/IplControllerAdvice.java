package com.manish.ipl.advice;

import java.util.NoSuchElementException;

import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.manish.ipl.exception.MatchException;
import com.manish.ipl.model.ExceptionResponse;
/**
 * 
 * @author manish.kumar14
 *	This class is to handle the exception at global level.
 */
@RestControllerAdvice
public class IplControllerAdvice extends ResponseEntityExceptionHandler{
//Put the exception in the response body
	@ExceptionHandler(MatchException.class)
	public ResponseEntity<ExceptionResponse> handleEmptyInput(MatchException emptyinputException){
		ExceptionResponse eResponse = new ExceptionResponse(emptyinputException.getErrorCode(), emptyinputException.getErrorMessage());
		return new ResponseEntity<ExceptionResponse>(eResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ExceptionResponse> handleNoSuchElementInput(NoSuchElementException noSuchElementException){
		ExceptionResponse eResponse = new ExceptionResponse("000",noSuchElementException.getStackTrace().toString());
		return new ResponseEntity<ExceptionResponse>(eResponse, HttpStatus.NOT_FOUND);
//		return new ResponseEntity<String>(" No value is present in the DB ", HttpStatus.NOT_FOUND);
	} 
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>(" Please change the method type. "+ ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(JobInstanceAlreadyCompleteException.class)
	public ResponseEntity<String> handleJobInstanceAlreadyCompleteException(JobInstanceAlreadyCompleteException jobInstanceAlreadyCompleteException){
		return new ResponseEntity<String>(" This job had already run and completed before. "+jobInstanceAlreadyCompleteException.getStackTrace().toString(), HttpStatus.IM_USED);
	}
	
	@ExceptionHandler(JobExecutionAlreadyRunningException.class)
	public ResponseEntity<String> handleJobExecutionAlreadyRunningException(JobExecutionAlreadyRunningException jobExecutionAlreadyRunningException){
		return new ResponseEntity<String>("This job is already running. "+jobExecutionAlreadyRunningException.getStackTrace().toString(), HttpStatus.PROCESSING);
	}
}

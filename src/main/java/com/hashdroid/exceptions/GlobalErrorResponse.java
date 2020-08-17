package com.hashdroid.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GlobalErrorResponse {
	//General error message about nature of error
    private String message;
 
    //Specific errors in API request processing
    private List<String> details;
    
	int status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime timestamp;
	
	public GlobalErrorResponse() {
		this.timestamp = LocalDateTime.now();
	}
	
    public GlobalErrorResponse(String message, List<String> details) {
        this();
    	
    	this.message = message;
        this.details = details;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

    
}

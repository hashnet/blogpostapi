package com.hashdroid.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashdroid.services.Translator;

import net.minidev.json.parser.ParseException;

@RestController
@CrossOrigin(origins="*")
public class WelcomeController {
	@Autowired
	Translator translator;
	
	@GetMapping("/welcome")
	public ResponseEntity<JsonNode> getWelcome() throws ParseException, JsonMappingException, JsonProcessingException {
		String jsonString = "{\"response\": \"" + translator.toLocale("prompt.welcome") + "\", "
				+ "\"errorMsg\": \"" + translator.toLocale("error.no.error") +    "\"}";
		 
	    JsonNode jsonNode = new ObjectMapper().readTree(jsonString);
	    
		return ResponseEntity.ok(jsonNode);
	}
}

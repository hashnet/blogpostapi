package com.hashdroid.services;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Translator {

	@Autowired
	private MessageSource messageSource;
	
	public String toLocale(String msgCode) {
		Locale locale = LocaleContextHolder.getLocale();
		
		return messageSource.getMessage(msgCode, null, locale);
	}
}
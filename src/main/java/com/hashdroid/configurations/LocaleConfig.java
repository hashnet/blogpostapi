package com.hashdroid.configurations;

import java.util.Arrays;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.hashdroid.locale.resolver.ReqParamThenAcceptHeaderLocaleResolver;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {
	@Bean
	public LocaleResolver localeResolver() {
//		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver() {
//			@Override
//			public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
//				if (locale != null) {
//					setSupportedLocales(Arrays.asList(locale));
//				}
//			}
//		};
		
		ReqParamThenAcceptHeaderLocaleResolver localeResolver = new ReqParamThenAcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		localeResolver.setSupportedLocales(Arrays.asList(new Locale("en"), new Locale("es")));
		localeResolver.setParamName("lang");

		return localeResolver;
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setUseCodeAsDefaultMessage(true); // when a message is not found, it will return the message code
		messageSource.setBasenames("translations/prompt", "translations/error");

		return messageSource;
	}

}
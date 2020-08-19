package com.hashdroid.locale.resolver;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

/**
 * {@link LocaleResolver} implementation that simply uses the locale specified
 * in request parameter and if not then uses the primary locale specified in the
 * "accept-language" header of the HTTP request (that is, the locale sent by the
 * client browser, normally that of the client's OS).
 *
 * <p>
 * Note: Does not support {@code setLocale}, since the accept header can only be
 * changed through changing the client's locale settings.
 * 
 */
public class ReqParamThenAcceptHeaderLocaleResolver implements LocaleResolver {
	
	/**
	 * Default name of the locale specification parameter: "locale".
	 */
	public static final String DEFAULT_PARAM_NAME = "locale";

	private String paramName = DEFAULT_PARAM_NAME;
	
	@Nullable
	private String[] httpMethods;

	private final List<Locale> supportedLocales = new ArrayList<>(4);

	@Nullable
	private Locale defaultLocale;

	
	/**
	 * Set the name of the parameter that contains a locale specification
	 * in a locale change request. Default is "locale".
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * Return the name of the parameter that contains a locale specification
	 * in a locale change request.
	 */
	public String getParamName() {
		return this.paramName;
	}

	/**
	 * Configure the HTTP method(s) over which the locale can be changed.
	 * @param httpMethods the methods
	 */
	public void setHttpMethods(@Nullable String... httpMethods) {
		this.httpMethods = httpMethods;
	}

	/**
	 * Return the configured HTTP methods.
	 */
	@Nullable
	public String[] getHttpMethods() {
		return this.httpMethods;
	}

	/**
	 * Configure supported locales to check against the requested locales
	 * determined via {@link HttpServletRequest#getLocales()}. If this is not
	 * configured then {@link HttpServletRequest#getLocale()} is used instead.
	 * @param locales the supported locales
	 */
	public void setSupportedLocales(List<Locale> locales) {
		this.supportedLocales.clear();
		this.supportedLocales.addAll(locales);
	}

	/**
	 * Return the configured list of supported locales.
	 */
	public List<Locale> getSupportedLocales() {
		return this.supportedLocales;
	}

	/**
	 * Configure a fixed default locale to fall back on if the request does not
	 * have an "Accept-Language" header.
	 * <p>By default this is not set in which case when there is "Accept-Language"
	 * header, the default locale for the server is used as defined in
	 * {@link HttpServletRequest#getLocale()}.
	 * @param defaultLocale the default locale to use
	 */
	public void setDefaultLocale(@Nullable Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	/**
	 * The configured default locale, if any.
	 * <p>This method may be overridden in subclasses.
	 */
	@Nullable
	public Locale getDefaultLocale() {
		return this.defaultLocale;
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale defaultLocale = getDefaultLocale();
		List<Locale> supportedLocales = getSupportedLocales();
		
		Locale requestLocale = null;
		
		// first try to get the locale from request parameter
		String newLocale = request.getParameter(getParamName());
		if (newLocale != null) {
			if (checkHttpMethod(request.getMethod())) {
				requestLocale = parseLocaleValue(newLocale);
			}
		}
		
		// if not provided then try to get the locale from Accept-Language header
		if (requestLocale == null && request.getHeader("Accept-Language") != null) {
			requestLocale = request.getLocale();
		}
		
		
		// additional validations
		if (supportedLocales.isEmpty() || supportedLocales.contains(requestLocale)) {
			return requestLocale;
		}
		
		Locale supportedLocale = findSupportedLocale(request, supportedLocales);
		if (supportedLocale != null) {
			return supportedLocale;
		}
		
		return (defaultLocale != null ? defaultLocale : requestLocale);
	}

	
	@Nullable
	private Locale findSupportedLocale(HttpServletRequest request, List<Locale> supportedLocales) {
		Enumeration<Locale> requestLocales = request.getLocales();
		Locale languageMatch = null;
		while (requestLocales.hasMoreElements()) {
			Locale locale = requestLocales.nextElement();
			if (supportedLocales.contains(locale)) {
				if (languageMatch == null || languageMatch.getLanguage().equals(locale.getLanguage())) {
					// Full match: language + country, possibly narrowed from earlier language-only match
					return locale;
				}
			}
			else if (languageMatch == null) {
				// Let's try to find a language-only match as a fallback
				for (Locale candidate : supportedLocales) {
					if (!StringUtils.hasLength(candidate.getCountry()) &&
							candidate.getLanguage().equals(locale.getLanguage())) {
						languageMatch = candidate;
						break;
					}
				}
			}
		}
		return languageMatch;
	}

	@Override
	public void setLocale(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Locale locale) {
		throw new UnsupportedOperationException(
				"Cannot change request parameter or HTTP accept header - use a different locale resolution strategy");
	}
	
	
	private boolean checkHttpMethod(String currentMethod) {
		String[] configuredMethods = getHttpMethods();
		if (ObjectUtils.isEmpty(configuredMethods)) {
			return true;
		}
		for (String configuredMethod : configuredMethods) {
			if (configuredMethod.equalsIgnoreCase(currentMethod)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Parse the given locale value as coming from a request parameter.
	 * <p>The default implementation calls {@link StringUtils#parseLocale(String)},
	 * accepting the {@link Locale#toString} format as well as BCP 47 language tags.
	 * @param localeValue the locale value to parse
	 * @return the corresponding {@code Locale} instance
	 */
	@Nullable
	protected Locale parseLocaleValue(String localeValue) {
		return StringUtils.parseLocale(localeValue);
	}

}

package org.ihtsdo.otf.sqs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

	private final ErrorAttributes errorAttributes;

	@Autowired
	public CustomErrorController(ErrorAttributes errorAttributes) {
		Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping
	public Map<String, Object> error(HttpServletRequest request) {
		return getErrorAttributes(request, getTraceParameter(request));
	}

	private boolean getTraceParameter(HttpServletRequest request) {
		String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equalsIgnoreCase(parameter);
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
		WebRequest request = new WebRequest() {
			@Override
			public String getHeader(String headerName) {
				return null;
			}

			@Override
			public String[] getHeaderValues(String headerName) {
				return new String[0];
			}

			@Override
			public Iterator<String> getHeaderNames() {
				return null;
			}

			@Override
			public String getParameter(String paramName) {
				return null;
			}

			@Override
			public String[] getParameterValues(String paramName) {
				return new String[0];
			}

			@Override
			public Iterator<String> getParameterNames() {
				return null;
			}

			@Override
			public Map<String, String[]> getParameterMap() {
				return null;
			}

			@Override
			public Locale getLocale() {
				return null;
			}

			@Override
			public String getContextPath() {
				return null;
			}

			@Override
			public String getRemoteUser() {
				return null;
			}

			@Override
			public Principal getUserPrincipal() {
				return null;
			}

			@Override
			public boolean isUserInRole(String role) {
				return false;
			}

			@Override
			public boolean isSecure() {
				return false;
			}

			@Override
			public boolean checkNotModified(long lastModifiedTimestamp) {
				return false;
			}

			@Override
			public boolean checkNotModified(String etag) {
				return false;
			}

			@Override
			public boolean checkNotModified(String etag, long lastModifiedTimestamp) {
				return false;
			}

			@Override
			public String getDescription(boolean includeClientInfo) {
				return null;
			}

			@Override
			public Object getAttribute(String name, int scope) {
				return null;
			}

			@Override
			public void setAttribute(String name, Object value, int scope) {

			}

			@Override
			public void removeAttribute(String name, int scope) {

			}

			@Override
			public String[] getAttributeNames(int scope) {
				return new String[0];
			}

			@Override
			public void registerDestructionCallback(String name, Runnable callback, int scope) {

			}

			@Override
			public Object resolveReference(String key) {
				return null;
			}

			@Override
			public String getSessionId() {
				return null;
			}

			@Override
			public Object getSessionMutex() {
				return null;
			}
		};
		return errorAttributes.getErrorAttributes(request, includeStackTrace ? ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE) : ErrorAttributeOptions.defaults());
	}

	public String getErrorPath() {
		return "/error";
	}

}

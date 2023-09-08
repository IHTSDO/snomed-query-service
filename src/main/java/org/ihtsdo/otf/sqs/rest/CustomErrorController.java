package org.ihtsdo.otf.sqs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

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
		return errorAttributes.getErrorAttributes(new ServletWebRequest(aRequest), includeStackTrace ? ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE) : ErrorAttributeOptions.defaults());
	}

	@SuppressWarnings("unused")
	public String getErrorPath() {
		return "/error";
	}

}

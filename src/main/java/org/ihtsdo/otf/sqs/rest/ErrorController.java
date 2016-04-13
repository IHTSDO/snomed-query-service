package org.ihtsdo.otf.sqs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/error")
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

	private final ErrorAttributes errorAttributes;

	@Autowired
	public ErrorController(ErrorAttributes errorAttributes) {
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
		return !"false".equals(parameter.toLowerCase());
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
		return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}

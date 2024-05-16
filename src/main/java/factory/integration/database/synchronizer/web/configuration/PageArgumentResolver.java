package factory.integration.database.synchronizer.web.configuration;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import factory.integration.database.synchronizer.web.exception.BusinessException;
import factory.integration.database.synchronizer.web.exception.ErrorMessage;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public class PageArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(PageRequestDto.class)
			&& parameter.hasParameterAnnotation(Page.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest)webRequest.getNativeRequest();
		int page;
		int size;
		try {
			page = Integer.parseInt(httpServletRequest.getParameter("page") == null ?
				"1" : httpServletRequest.getParameter("page"));
			size = Integer.parseInt(httpServletRequest.getParameter("size") == null ?
				"10" : httpServletRequest.getParameter("size"));
		} catch (NumberFormatException exception) {
			throw new BusinessException(ErrorMessage.INVALID_FORMAT, HttpStatus.BAD_REQUEST.value());
		}
		return new PageRequestDto(page, size);
	}
}

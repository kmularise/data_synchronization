package factory.integration.database.synchronizer.web.exception;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public String businessExceptionPage(Model model, HttpServletResponse response,
		BusinessException businessException) {
		response.setStatus(businessException.getStatus());
		model.addAttribute("status", businessException.getStatus());
		model.addAttribute("message", businessException.getMessage());
		return "error_page.html";
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SQLException.class)
	public String sqlExceptionPage(Model model, SQLException exception) {
		log.error(exception.getMessage());
		model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
		model.addAttribute("message", exception.getMessage());
		return "error_page.html";
	}
}

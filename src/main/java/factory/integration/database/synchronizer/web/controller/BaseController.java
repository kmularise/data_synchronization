package factory.integration.database.synchronizer.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
	@GetMapping("/")
	public String home() {
		return "base";
	}
}
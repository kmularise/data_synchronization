package factory.integration.database.synchronizer.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import factory.integration.database.synchronizer.mapper.scheduler.TaskInfoDto;
import factory.integration.database.synchronizer.web.service.TaskInfoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskInfoController {
	private final TaskInfoService taskInfoService;

	@GetMapping("/task")
	public String getAllTask(Model model) {
		List<TaskInfoDto> taskList = taskInfoService.getAllTask();
		model.addAttribute("schedulers", taskList);
		return "task_info_detail";
	}

	@PostMapping("/task/insert")
	public void addTask(@ModelAttribute TaskInfoDto taskInfoDto) {

	}

	@PostMapping("/task/update")
	public void updateTask() {

	}

	@PostMapping("/task/delete")
	public void deleteTask() {

	}
}

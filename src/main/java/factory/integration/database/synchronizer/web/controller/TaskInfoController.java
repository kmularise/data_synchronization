package factory.integration.database.synchronizer.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTask;
import factory.integration.database.synchronizer.web.service.TaskInfoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskInfoController {
	private final TaskInfoService taskInfoService;

	@GetMapping("/task")
	public String getSyncTaskPage(
		@RequestParam(defaultValue = "1") Integer page,
		@RequestParam(defaultValue = "10") Integer size,
		Model model) {
		List<SyncTask> taskList = taskInfoService.getTaskPage(page, size);
		model.addAttribute("schedulers", taskList);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("totalPages", taskInfoService.getTotalPage(size));
		return "task_info_detail";
	}
}

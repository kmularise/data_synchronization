package factory.integration.database.synchronizer.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTask;
import factory.integration.database.synchronizer.web.configuration.Page;
import factory.integration.database.synchronizer.web.dto.SyncTaskDto;
import factory.integration.database.synchronizer.web.service.TaskInfoService;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import factory.integration.database.synchronizer.web.util.PageResponseDto;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskInfoController {
	private final TaskInfoService taskInfoService;

	@GetMapping("/tasks")
	public String getSyncTaskPage(
		@Page PageRequestDto pageRequestDto,
		Model model) {
		PageResponseDto<SyncTask> pageResponseDto = taskInfoService.getTaskPage(pageRequestDto);
		model.addAttribute("schedulers", pageResponseDto.getContent());
		model.addAttribute("page", pageRequestDto.getPage());
		model.addAttribute("size", pageRequestDto.getSize());
		model.addAttribute("totalPages", pageResponseDto.getTotalPage());
		return "task_info_detail";
	}

	@PostMapping("/tasks/insert")
	public String addSyncTask(@ModelAttribute SyncTaskDto syncTaskDto) {
		taskInfoService.addTask(syncTaskDto);
		return "redirect:/tasks";
	}

	@PostMapping("/tasks/delete")
	public String deleteSyncTask(@RequestParam Long id) {
		taskInfoService.deleteTask(id);
		return "redirect:/tasks";
	}

	@PostMapping("/tasks/enable")
	public String enableSyncTask(@RequestParam Long id) {
		taskInfoService.enableTask(id);
		return "redirect:/tasks";
	}

	@PostMapping("/tasks/disable")
	public String disableSyncTask(@RequestParam Long id) {
		taskInfoService.disableTask(id);
		return "redirect:/tasks";
	}
}

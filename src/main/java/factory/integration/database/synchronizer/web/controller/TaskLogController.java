package factory.integration.database.synchronizer.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskLog;
import factory.integration.database.synchronizer.web.service.TaskLogService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskLogController {
	private final TaskLogService taskLogService;

	@GetMapping("/task-logs")
	public String getSyncTaskLogsPage(
		@RequestParam(defaultValue = "1") Integer page,
		@RequestParam(defaultValue = "10") Integer size,
		Model model
	) {
		List<SyncTaskLog> syncTaskLogs = taskLogService.selectPage(page, size);
		model.addAttribute("syncTaskLogs", syncTaskLogs);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		return "sync_log_detail";
	}
}

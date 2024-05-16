package factory.integration.database.synchronizer.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTaskLog;
import factory.integration.database.synchronizer.web.configuration.Page;
import factory.integration.database.synchronizer.web.service.TaskLogService;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskLogController {
	private final TaskLogService taskLogService;

	@GetMapping("/task-logs")
	public String getSyncTaskLogsPage(
		@Page PageRequestDto pageRequestDto,
		Model model
	) {
		List<SyncTaskLog> syncTaskLogs = taskLogService.selectPage(pageRequestDto);
		model.addAttribute("syncTaskLogs", syncTaskLogs);
		model.addAttribute("page", pageRequestDto.getPage());
		model.addAttribute("size", pageRequestDto.getSize());
		return "sync_log_detail";
	}
}

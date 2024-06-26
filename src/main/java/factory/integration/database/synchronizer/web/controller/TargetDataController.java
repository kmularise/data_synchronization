package factory.integration.database.synchronizer.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import factory.integration.database.synchronizer.web.configuration.Page;
import factory.integration.database.synchronizer.web.service.TargetDataService;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import factory.integration.database.synchronizer.web.util.PageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TargetDataController {
	private final TargetDataService targetDataService;

	@GetMapping("/table/target/collection")
	public String getAllTable(Model model) {
		List<String> targetTables = targetDataService.getAllTables();
		model.addAttribute("tableNames", targetTables);
		return "target_table_list";
	}

	@GetMapping("/table/target")
	public String showTargetTable(@RequestParam String tableName,
		@Page PageRequestDto pageRequestDto,
		Model model) {
		List<String> columns = targetDataService.getTableColumns(tableName);
		PageResponseDto<Map<String, Object>> pageResponseDto = targetDataService.getPage(tableName, pageRequestDto);
		model.addAttribute("tableName", tableName);
		model.addAttribute("columns", columns);
		model.addAttribute("data", pageResponseDto.getContent());
		model.addAttribute("size", pageRequestDto.getSize());
		model.addAttribute("page", pageRequestDto.getPage());
		model.addAttribute("totalPages", pageResponseDto.getTotalPage());
		return "target_table_detail";
	}
}

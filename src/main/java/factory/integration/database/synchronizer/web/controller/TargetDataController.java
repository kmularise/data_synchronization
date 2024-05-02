package factory.integration.database.synchronizer.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import factory.integration.database.synchronizer.web.service.TargetDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TargetDataController {
	private final TargetDataService targetDataService;

	@GetMapping("/table/target")
	public String showTargetTable(@RequestParam String tableName, Model model) {
		List<String> columns = targetDataService.getTableColumns(tableName);
		List<Map<String, Object>> maps = targetDataService.selectAll(tableName);
		model.addAttribute("columns", columns);
		model.addAttribute("data", maps);
		return "target_table_detail";
	}
}

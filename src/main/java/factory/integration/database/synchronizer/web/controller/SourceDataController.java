package factory.integration.database.synchronizer.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import factory.integration.database.synchronizer.web.configuration.Page;
import factory.integration.database.synchronizer.web.service.SourceDataService;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import factory.integration.database.synchronizer.web.util.PageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SourceDataController {
	private final SourceDataService sourceDataService;

	@GetMapping("table/source/collection")
	public String showAllTables(Model model) {
		List<String> tableNames = sourceDataService.getAllTables();
		model.addAttribute("tableNames", tableNames);
		return "source_table_list";
	}

	@GetMapping("/table/source")
	public String showSourceTable(@RequestParam String tableName,
		@Page PageRequestDto pageRequestDto,
		Model model) {
		List<String> columns = sourceDataService.getTableColumns(tableName);
		PageResponseDto<Map<String, Object>> pageResponseDto = sourceDataService.getPage(tableName, pageRequestDto);
		model.addAttribute("tableName", tableName);
		model.addAttribute("columns", columns);
		model.addAttribute("data", pageResponseDto.getContent());
		model.addAttribute("size", pageRequestDto.getSize());
		model.addAttribute("page", pageRequestDto.getPage());
		model.addAttribute("totalPages", pageResponseDto.getTotalPage());
		return "source_table_page";
	}

	@PostMapping("/table/source/insert")
	public String insertData(@RequestParam Map<String, Object> map) {
		String tableName = (String)map.get("tableName");
		Map<String, Object> data = getData(map, tableName);
		sourceDataService.insert(tableName, data);
		return "redirect:/table/source?tableName=" + tableName;
	}

	@PostMapping("/table/source/update")
	public String updateData(@RequestParam Map<String, Object> map) {
		String tableName = (String)map.get("tableName");
		Map<String, Object> data = getData(map, tableName);
		sourceDataService.update(tableName, data);
		return "redirect:/table/source?tableName=" + tableName;
	}

	@PostMapping("/table/source/delete")
	public String deleteData(@RequestParam Map<String, Object> map) {
		String tableName = (String)map.get("tableName");
		Map<String, Object> data = getData(map, tableName);
		sourceDataService.delete(tableName, data);
		return "redirect:/table/source?tableName=" + tableName;
	}

	private Map<String, Object> getData(Map<String, Object> map, String tableName) {
		List<String> columns = sourceDataService.getTableColumns(tableName);
		Map<String, Object> data = new HashMap<>();
		for (String column : columns) {
			if (map.get(column) != null && !"".equals(map.get(column))) {
				data.put(column, map.get(column));
			}
		}
		return data;
	}
}

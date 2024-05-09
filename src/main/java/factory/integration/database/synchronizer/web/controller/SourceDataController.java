package factory.integration.database.synchronizer.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import factory.integration.database.synchronizer.common.util.CursorPage;
import factory.integration.database.synchronizer.web.service.SourceDataService;
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
		return "source_table_list";
	}
	
	@GetMapping("/table/source")
	public String showSourceTable(@RequestParam String tableName,
		@RequestParam(required = false) Long nextCursor,
		@RequestParam(required = false) Long prevCursor,
		@RequestParam(defaultValue = "20") int size,
		Model model) {
		List<String> columns = sourceDataService.getTableColumns(tableName);
		if (prevCursor == null) {
			CursorPage<Map<String, Object>> page = sourceDataService.selectNextPage(tableName, nextCursor, size);
			model.addAttribute("columns", columns);
			model.addAttribute("data", page.getContent());
			model.addAttribute("tableName", tableName);
			model.addAttribute("nextCursor", page.getNextCursor());
			model.addAttribute("prevCursor", page.getPrevCursor());
			model.addAttribute("hasNext", page.hasNext());
			model.addAttribute("hasPrev", page.hasPrev());
			return "source_table_page";
		}
		CursorPage<Map<String, Object>> page = sourceDataService.selectPrevPage(tableName, prevCursor, size);
		model.addAttribute("columns", columns);
		model.addAttribute("data", page.getContent());
		model.addAttribute("tableName", tableName);
		model.addAttribute("nextCursor", page.getNextCursor());
		model.addAttribute("prevCursor", page.getPrevCursor());
		model.addAttribute("hasNext", page.hasNext());
		model.addAttribute("hasPrev", page.hasPrev());
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

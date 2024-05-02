package factory.integration.database.synchronizer.scheduler.job;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SyncTableInfo {
	private String tableName;
	private Boolean insertFlag;
	private Boolean deleteFlag;
	private Boolean updateFlag;
	private List<String> excludedColumns;
}

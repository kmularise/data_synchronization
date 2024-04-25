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
	private Boolean hasInserted;
	private Boolean hasDeleted;
	private Boolean hasUpdated;
	private List<String> excludedColumns;
}

package factory.integration.database.synchronizer.scheduler.job;

import java.util.List;

import factory.integration.database.synchronizer.mapper.scheduler.SyncTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SyncTaskInfoRequest {
	private Long jobId;
	private String tableName;
	private Boolean insertFlag;
	private Boolean deleteFlag;
	private Boolean updateFlag;
	private List<String> excludedColumns;

	public SyncTaskInfoRequest(SyncTask syncTask) {
		this.jobId = syncTask.getId();
		this.tableName = syncTask.getTableName();
		this.deleteFlag = syncTask.getDeleteFlag();
		this.insertFlag = syncTask.getInsertFlag();
		this.updateFlag = syncTask.getUpdateFlag();
		this.excludedColumns = syncTask.getExcludedColumns();
	}
}

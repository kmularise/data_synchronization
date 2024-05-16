package factory.integration.database.synchronizer.mapper.scheduler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SyncTask {
	private Long id;
	private String jobName;
	private Integer periodMinutes;
	private String tableName;
	private Boolean isActive;
	private Boolean insertFlag;
	private Boolean updateFlag;
	private Boolean deleteFlag;
	private List<String> excludedColumns;

	public SyncTask(String jobName, Integer periodMinutes, String tableName, Boolean isActive,
		Boolean insertFlag,
		Boolean updateFlag, Boolean deleteFlag, List<String> excludedColumns) {
		this.jobName = jobName;
		this.periodMinutes = periodMinutes;
		this.tableName = tableName;
		this.isActive = isActive;
		this.insertFlag = insertFlag;
		this.updateFlag = updateFlag;
		this.deleteFlag = deleteFlag;
		this.excludedColumns = excludedColumns;
	}

	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		SyncTask syncTask = (SyncTask)object;
		return id.equals(syncTask.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}

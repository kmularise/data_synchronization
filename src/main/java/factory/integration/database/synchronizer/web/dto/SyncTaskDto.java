package factory.integration.database.synchronizer.web.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class SyncTaskDto {
	private final String jobName;
	private final Integer periodMinutes;
	private final String tableName;
	private final Boolean isActive;
	private final Boolean insertFlag;
	private final Boolean updateFlag;
	private final Boolean deleteFlag;
	private final List<String> excludedColumns;

	public SyncTaskDto(String jobName, Integer periodMinutes, String tableName, Boolean isActive, Boolean insertFlag,
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

	@Override
	public String toString() {
		return "SyncTaskDto{" +
			"jobName='" + jobName + '\'' +
			", periodMinutes=" + periodMinutes +
			", tableName='" + tableName + '\'' +
			", isActive=" + isActive +
			", insertFlag=" + insertFlag +
			", updateFlag=" + updateFlag +
			", deleteFlag=" + deleteFlag +
			", excludedColumns=" + excludedColumns +
			'}';
	}
}

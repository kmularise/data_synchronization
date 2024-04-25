package factory.integration.database.synchronizer.mapper.scheduler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class SchedulerDto {
	private Long id;
	private String jobName;
	private Integer periodMinutes;
	private String tableName;
	private Boolean isActive;
	private Boolean hasInserted;
	private Boolean hasUpdated;
	private Boolean hasDeleted;
	private List<String> excludedColumns;

	public boolean isActive() {
		return isActive;
	}
}

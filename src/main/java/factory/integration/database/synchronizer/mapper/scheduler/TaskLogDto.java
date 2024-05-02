package factory.integration.database.synchronizer.mapper.scheduler;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class TaskLogDto {
	private Long id;
	private Long syncTaskId;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private Status status;

	public TaskLogDto(Long syncTaskId, LocalDateTime startTime, LocalDateTime endTime, Status status) {
		this.syncTaskId = syncTaskId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}
}

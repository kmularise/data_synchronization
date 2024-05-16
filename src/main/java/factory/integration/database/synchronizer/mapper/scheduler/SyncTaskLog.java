package factory.integration.database.synchronizer.mapper.scheduler;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SyncTaskLog {
	private Long id;
	private Long syncTaskId;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private TaskStatus status;

	public SyncTaskLog(Long syncTaskId, LocalDateTime startTime, TaskStatus status) {
		this.syncTaskId = syncTaskId;
		this.startTime = startTime;
		this.status = status;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		SyncTaskLog that = (SyncTaskLog)object;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "SyncTaskLog{" +
			"id=" + id +
			", syncTaskId=" + syncTaskId +
			", startTime=" + startTime +
			", endTime=" + endTime +
			", status=" + status +
			'}';
	}
}

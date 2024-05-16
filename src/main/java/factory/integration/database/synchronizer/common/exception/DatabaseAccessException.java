package factory.integration.database.synchronizer.common.exception;

import lombok.Getter;

@Getter
public class DatabaseAccessException extends RuntimeException {

	private final String message;

	public DatabaseAccessException(String message) {
		super(message);
		this.message = message;
	}
}

package factory.integration.database.synchronizer.web.exception;

public interface ErrorMessage {
	String INVALID_TABLE = "table name is invalid";
	String INVALID_COLUMN = "column name is invalid";
	String PAGE_NON_POSITIVE = "page is zero or negative";
	String SIZE_NON_POSITIVE = "size is zero or negative";
	String PERIOD_NON_POSITIVE = "period is zero or negative";
	String NOT_FOUND = "resource is not found";
	String INVALID_FORMAT = "value has invalid format";
}

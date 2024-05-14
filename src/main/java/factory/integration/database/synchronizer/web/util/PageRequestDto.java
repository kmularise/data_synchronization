package factory.integration.database.synchronizer.web.util;

import org.springframework.http.HttpStatus;

import factory.integration.database.synchronizer.web.exception.BusinessException;
import factory.integration.database.synchronizer.web.exception.ErrorMessage;
import lombok.Getter;

@Getter
public class PageRequestDto {
	private final int page;
	private final int size;

	public PageRequestDto(int page, int size) {
		validateCurrentPage(page);
		validateSize(size);
		this.page = page;
		this.size = size;
	}

	private static void validateCurrentPage(int page) {
		if (page <= 0) {
			throw new BusinessException(ErrorMessage.PAGE_NON_POSITIVE, HttpStatus.BAD_REQUEST.value());
		}
	}

	private static void validateSize(int size) {
		if (size <= 0) {
			throw new BusinessException(ErrorMessage.SIZE_NON_POSITIVE, HttpStatus.BAD_REQUEST.value());
		}
	}

	public long getFirst() {
		return (long)(page - 1) * size;
	}
}

package factory.integration.database.synchronizer.web.util;

import java.util.List;

import lombok.Getter;

@Getter
public class PageResponseDto<T> {
	private final int totalPage;
	private final List<T> content;

	public PageResponseDto(int size, long count, List<T> content) {
		this.totalPage = getTotalPage(size, count);
		this.content = content;
	}

	private static int getTotalPage(int size, long count) {
		return (int)(count / (long)size) + 1;
	}
}

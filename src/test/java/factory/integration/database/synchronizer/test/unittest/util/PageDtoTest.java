package factory.integration.database.synchronizer.test.unittest.util;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import factory.integration.database.synchronizer.web.exception.BusinessException;
import factory.integration.database.synchronizer.web.util.PageRequestDto;
import factory.integration.database.synchronizer.web.util.PageResponseDto;

public class PageDtoTest {
	@DisplayName("현재 페이지가 양수일 때, 페이지 객체의 유효성이 검증됩니다.")
	@ParameterizedTest
	@ValueSource(ints = {1, 3, 6, 15, Integer.MAX_VALUE})
	void checkValidPage(int page) {
		Assertions.assertDoesNotThrow(() -> new PageRequestDto(page, 10));
	}

	@DisplayName("현재 페이지가 음수이거나 0일 때, 유효하지 않은 페이지입니다.")
	@ParameterizedTest
	@ValueSource(ints = {-1, -6, -7, -15, 0, Integer.MIN_VALUE})
	void checkInvalidPage(int page) {
		Assertions.assertThrows(BusinessException.class, () -> new PageRequestDto(page, 10));
	}

	@DisplayName("페이지 크기가 양수일 때, 페이지 객체의 유효성이 검증됩니다.")
	@ParameterizedTest
	@ValueSource(ints = {1, 3, 6, 15, Integer.MAX_VALUE})
	void checkValidSize(int size) {
		Assertions.assertDoesNotThrow(() -> new PageRequestDto(1, size));
	}

	@DisplayName("페이지 크기가 음수이거나 0일 때, 유효하지 않은 페이지입니다.")
	@ParameterizedTest
	@ValueSource(ints = {-1, -6, -7, -15, 0, Integer.MIN_VALUE})
	void checkInvalidSize(int size) {
		Assertions.assertThrows(BusinessException.class, () -> new PageRequestDto(size, 10));
	}

	@DisplayName("유효한 현재 페이지와 페이지 크기가 주어졌을 때, 첫번째 요소의 인덱스를 계산합니다.")
	@ParameterizedTest
	@CsvSource({
		"10, 1, 0",
		"10, 2, 10",
		"20, 3, 40",
	})
	void calculateFirstItemIndex(int size, int page, int expectedFirst) {
		PageRequestDto pageRequestDto = new PageRequestDto(page, size);
		Assertions.assertEquals(expectedFirst, pageRequestDto.getFirst());
	}

	@CsvSource({
		"101, 10, 11",
		"7, 20, 1",
	})
	@DisplayName("데이터의 총 개수와 페이지 크기가 주어졌을 때, 전체 페이지 수를 계산합니다.")
	@ParameterizedTest
	void calculateTotalPage(int count, int size, int expectedTotalPage) {
		PageResponseDto<Map<String, Object>> pageDto = new PageResponseDto<>(size, count, new ArrayList<>());
		Assertions.assertEquals(expectedTotalPage, pageDto.getTotalPage());
	}
}

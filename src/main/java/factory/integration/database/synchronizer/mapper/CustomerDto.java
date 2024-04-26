package factory.integration.database.synchronizer.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class CustomerDto {
	private Long id;
	private String name;

	public CustomerDto(String name) {
		this.name = name;
	}
}

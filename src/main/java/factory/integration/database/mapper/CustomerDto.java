package factory.integration.database.mapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CustomerDto {
	private Long id;
	private String name;
}

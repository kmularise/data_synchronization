package factory.integration.database.synchronizer.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class CarDto {
	private Long id;
	private String name;
	private Integer price;

	public CarDto(String name, Integer price) {
		this.name = name;
		this.price = price;
	}
}

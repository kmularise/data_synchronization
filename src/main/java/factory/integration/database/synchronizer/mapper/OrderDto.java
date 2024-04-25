package factory.integration.database.synchronizer.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class OrderDto {
	private Long id;
	private Integer count;
	private Long customerId;
	private Long carId;

	public OrderDto(Integer count, Long customerId, Long carId) {
		this.count = count;
		this.customerId = customerId;
		this.carId = carId;
	}
}

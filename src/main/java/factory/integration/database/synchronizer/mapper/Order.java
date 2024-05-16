package factory.integration.database.synchronizer.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Order {
	private Long id;
	private Integer count;
	private Long customerId;
	private Long carId;

	public Order(Integer count, Long customerId, Long carId) {
		this.count = count;
		this.customerId = customerId;
		this.carId = carId;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		Order order = (Order)object;
		return id.equals(order.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}

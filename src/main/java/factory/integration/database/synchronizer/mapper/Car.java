package factory.integration.database.synchronizer.mapper;

import lombok.Getter;

@Getter
public class Car {
	private Long id;
	private String name;
	private Integer price;

	public Car(String name, Integer price) {
		this.name = name;
		this.price = price;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		Car car = (Car)object;
		return id.equals(car.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}

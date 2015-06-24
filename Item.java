package simulated_annealing;

public class Item {
	private float volume;
	private float weight;
	private float cost;
	private float value;
	private float profit;
	


	public Item(float volume, float weight, float cost, float value) {
		this.volume = volume;
		this.weight = weight;
		this.cost = cost;
		this.value = value;
		this.profit = value - cost;
	}

	public float getVolume() {
		return volume;
	}

	public float getWeight() {
		return weight;
	}

	public float getCost() {
		return cost;
	}

	public float getValue() {
		return value;
	}

	public float getProfit() {
		return profit;
	}

	@Override
	public String toString() {
		return "Volume: " + volume + " Weight: " + weight + " Cost: " + cost + " Value: " + value + " Profit: " + profit;
	}
}

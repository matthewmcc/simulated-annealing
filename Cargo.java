package simulated_annealing;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Cargo {
	
	private static ArrayList<Item> items;
	private float weight 	= 0;
	private float volume 	= 0;
	private float cost 		= 0;
	private float value 	= 0;
	
	public Cargo() {
		items = new ArrayList<Item>();
	}

	public Item getItem(int i) {
		return items.get(i);
	}

	public void addItem(Item i) {
		weight	+= i.getWeight();
		volume	+= i.getVolume();
		cost	+= i.getCost();
		value	+= i.getValue();
		items.add(i);
		
		roundAll();
	}
	
	public void removeItem(int i){
		weight	-= items.get(i).getWeight();
		volume	-= items.get(i).getVolume();
		cost	-= items.get(i).getCost();
		value	-= items.get(i).getValue();
		items.remove(i);
		
		roundAll();
	}
	
	public Cargo copy(){
		Cargo newCargo = new Cargo();
		for(Item it : items) {
			newCargo.addItem(it);
		}
		return newCargo;
	}
	
	public boolean contains(Item i){
		return items.contains(i);
	}
	
	public int numberOfItems(){
		return items.size();
	}
	
	public float totalVolume(){
		return volume;

	}
	public float totalWeight(){
		return weight;
	}
	public float totalCost(){
		return cost;
	}
	public float totalValue(){
		return value;
	}
	public float totalProfit(){
		return round(value - cost, 2);
	}
	
	public void roundAll() {
		weight = round(weight, 2);
		cost = round(cost, 2);
		volume = round(volume, 2);
		value = round(value, 2);
	}
	
	/**
     * Round to certain number of decimals
     * 
     * @param d
     * @param decimalPlace
     * @return
     */
    public float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}

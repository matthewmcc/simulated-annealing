package simulated_annealing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Simulated_annealing {
	
	private static List<Item> dock = new ArrayList<Item>();
	
	public static float maxVolume;
	public static float maxWeight;
	public static float maxCost;
	public static Random ran = new Random();
	
	public static long timeAllowed;
	
	public static void main(String[] args) throws FileNotFoundException{
		
		if (args.length != 5) {
			System.out.println("Usage: <Max Volume> <Max Weight> <Max Cost> <Time Allowed> <Filename>");
		}
		
		maxVolume = Float.parseFloat(args[0]);
		maxWeight = Float.parseFloat(args[1]);
		maxCost = Float.parseFloat(args[2]);
		timeAllowed = (long) (Math.pow(10, 9) * Float.parseFloat(args[3]));
		
		File f = new File(args[4]);
		Scanner sc = new Scanner(f);
		
		while(sc.hasNext()) {
			Item it = new Item(sc.nextFloat(), sc.nextFloat(),
					sc.nextFloat(), sc.nextFloat());
			
			dock.add(it);
		}
		
		Cargo cargo = new Cargo();
		cargo = generateCargo();
		
		long startTime = System.nanoTime();
		long currentTime = startTime;
		int cycles = 0;
		
		while(startTime + timeAllowed > currentTime){

			cargo = anneal(cargo,  (cargo.numberOfItems() * percentage(currentTime, startTime)) / 100);

			currentTime = System.nanoTime();
			cycles++;
		}
		
		System.out.println("Cycles = " + cycles +
				'\n' + "Total Volume = " + cargo.totalVolume() + 
				'\n' + "Total Weight = " + cargo.totalWeight() + 
				'\n' + "Total Cost = " + cargo.totalCost() + 
				'\n' + "Profit = " + cargo.totalProfit());
		
		sc.close();
	}
	
	/**
	 * Returns the percentage of time past from the start to the end of the
	 * runtime
	 * @param currentTime
	 * @param startTime
	 * @return
	 */
	public static int percentage(long currentTime, long startTime) {
		return (int) (100 - (((currentTime - startTime) * 100 ) / timeAllowed));
	}
	
	/**
	 * Adds items to the ships cargo until the ship sinks, then remove
	 * items until ship resurfaces
	 * @return cargo, full ship
	 */
	public static Cargo generateCargo(){
		Cargo cargo = new Cargo();
		Item it;
		
		while(!breakConstraints(cargo)) {
			it = dock.get(ran.nextInt(dock.size()));
			if (!cargo.contains(it)) cargo.addItem(it);
		}
		
		while (breakConstraints(cargo)) {
			cargo.removeItem(ran.nextInt(cargo.numberOfItems()));
		}
		
		return cargo;
	}
	
	/**
	 * Removes temperature many items for the cargo and reads items until
	 * the ship sinks
	 * @param cargo
	 * @param temperture
	 * @return
	 */
	public static Cargo anneal(Cargo cargo, int temperature) {
		Cargo newCargo = cargo.copy();
		Item it;
		
		for(int i = 0; newCargo.numberOfItems() != 0 && i < temperature; i++) {
			newCargo.removeItem(ran.nextInt(newCargo.numberOfItems()));
		}
		
		while(!breakConstraints(newCargo)) {
			it = dock.get(ran.nextInt(dock.size()));
			if (!newCargo.contains(it)) newCargo.addItem(it);
		}
		
		while (breakConstraints(newCargo)) {
			newCargo.removeItem(ran.nextInt(newCargo.numberOfItems()));
		}
		
		if (cargo.totalProfit() > newCargo.totalProfit())
			return cargo;
		else return newCargo;
	}
	
	/**
	 * Check to see if a ship sinks given it's cargo load, or if the cargo
	 * breaks the bank
	 * @param cargo
	 * @return
	 */
	public static boolean breakConstraints(Cargo cargo) {
		if(cargo.totalCost() > maxCost || cargo.totalVolume() > maxVolume ||
				cargo.totalWeight() > maxWeight)
			return true;
			
		return false;
	}
}

package assignment5;

import java.lang.reflect.Constructor;
import java.util.List;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected final String look(int direction, boolean steps) {return "";}
	
	/* rest is unchanged from Project 4 */
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	/**
	 * Invoke of walk() will update a critter's position
	 * @param direction: Integer from 0 to 7
	 * @return void
	 */
	protected final void walk(int direction) {
		this.energy -= Params.walk_energy_cost;
		switch (direction) {
		case 0: 
			x_coord = travelX(1);
			break;	// if this case is true, then don't check the rest of the cases
		case 1:
			x_coord = travelX(1);
			y_coord = travelY(-1);
			break;
		case 2:
			y_coord = travelY(-1);
			break;
		case 3:
			x_coord = travelX(-1);
			y_coord = travelY(-1);
			break;
		case 4:
			x_coord = travelX(-1);
			break;
		case 5:
			x_coord = travelX(-1);
			y_coord = travelY(1);
			break;
		case 6:
			y_coord = travelY(1);
			break;
		default:
			x_coord = travelX(1);
			y_coord = travelY(1);
			break;
		}
	}
	
	/**
	 * Invoke of run() will update a critter's position
	 * @param direction: Integer from 0 to 7
	 * @return void
	 */
	protected final void run(int direction) {
		this.energy -= Params.run_energy_cost;
		switch (direction) {
		case 0: 
			x_coord = travelX(2);
			break;	// if this case is true, then don't check the rest of the cases
		case 1:
			x_coord = travelX(2);
			y_coord = travelY(-2);
			break;
		case 2:
			y_coord = travelY(-2);
			break;
		case 3:
			x_coord = travelX(-2);
			y_coord = travelY(-2);
			break;
		case 4:
			x_coord = travelX(-2);
			break;
		case 5:
			x_coord = travelX(-2);
			y_coord = travelY(2);
			break;
		case 6:
			y_coord = travelY(2);
			break;
		default:
			x_coord = travelX(2);
			y_coord = travelY(2);
			break;
		}
	}
	
	
	/** The reproduce method will initialize the offspring critter
	 * The offspring has been created in the doTimeStep function and is passed as a parameter
	 * The offspring has 1/2 of their parent's energy (round factors down)
	 * Reduce the parent energy to 1/2 (round factors up)
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy<Params.min_reproduce_energy) {	// only reproduce if parent has enough energy
			return;
		}
		offspring.energy = (this.energy)/2;	// round down
		this.energy = (int) Math.ceil((this.energy)/2);	// round up
		offspring.x_coord = this.x_coord;	// initialize the offspring position
		offspring.y_coord = this.y_coord;
		switch (direction) {					// move the offspring
		case 0: 
			offspring.x_coord = offspring.travelX(1);
			break;
		case 1:
			offspring.x_coord = offspring.travelX(1);
			offspring.y_coord = offspring.travelY(-1);
			break;
		case 2:
			offspring.y_coord = offspring.travelY(-1);
			break;
		case 3:
			offspring.x_coord = offspring.travelX(-1);
			offspring.y_coord = offspring.travelY(-1);
			break;
		case 4:
			offspring.x_coord = offspring.travelX(-1);
			break;
		case 5:
			offspring.x_coord = offspring.travelX(-1);
			offspring.y_coord = offspring.travelY(1);
			break;
		case 6:
			offspring.y_coord = offspring.travelY(1);
			break;
		default:
			offspring.x_coord = offspring.travelX(1);
			offspring.y_coord = offspring.travelY(1);
			break;
		}
		//babies.add(offspring);
	}	
	
	/**
	 * This method worldTimeStep will proceed one time-step
	 * Update the position of all the critters
	 * If two or more critters occupy the same location, then make them fight
	 * After the conflicts are resolved, critters will reproduce  
	 * Remove all the critters with energy <= zero
	 * New Algae are added to the world map 
	 */
	public static void worldTimeStep() {
		for(Critter c: population) {		// invoke doTimeStep method on every living critter in the critter collection
			c.doTimeStep();
		}
		for(Critter i: population) {
			for(Critter j:population) {
				if(!i.equals(j)) {
					//encounter
					if(!babies.contains(i) && !babies.contains(j)) {//new born babies cannot fight
						if(i.x_coord == j.x_coord && i.y_coord == j.y_coord) {
							if(i.isAlive()==true && j.isAlive() ==true) {
								int iPower = 0; int jPower =0;		//the amount of energy each critter will use to battle (random)
								if(i.fight(j.toString())==true) {	//if i wants to fight
									iPower = getRandomInt(i.energy);
								}
								if(j.fight(i.toString())==true) {	// if j wants to fight as well
									jPower = getRandomInt(j.energy);
								}
								
								if(iPower>jPower) {
									i.energy += (j.energy)/2; 
									j.energy = 0;	// this critter is dead
								}
								else if(jPower> iPower) {
									j.energy += (i.energy)/2;
									i.energy = 0; 	// this critter is dead
								}
								else {
									//decide the winner using flipping a coin. 1 means i win, 0 means j win
									int coinflip = getRandomInt(2);
									if(coinflip==1) {
										i.energy += (j.energy)/2; 
										j.energy = 0;	// this critter is dead
									}
									else {
										j.energy += (j.energy)/2; 
										i.energy = 0;	// this critter is dead
									}
								}
							}
						}
					}
					
				}
				
			}
		}
		// add all the babies in
		for(Critter b: babies) {
			if(b.energy>0) {
				population.add(b);
			}
		}
		// all critters
		for(Critter c: population) {
			c.energy -= Params.rest_energy_cost;
		}
		// clear all the dead critters
		List<Critter> deadCritters = new java.util.ArrayList<Critter>();
		for(Critter c: population) {
			if(c.energy<=0) {
				deadCritters.add(c);
			}
		}
		for(Critter c: deadCritters) {
			population.remove(c);
		}
		//create algae
		for(int i = 0; i < Params.refresh_algae_count; i++) {
				try {
					Critter.makeCritter("Algae");		// call the default constructor of algae 
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidCritterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public static void displayWorld(Object pane) {} 
	/* Alternate displayWorld, where you use Main.<pane> to reach into your
	   display component.
	   // public static void displayWorld() {}
	*/
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			critter_class_name = "assignment5." + critter_class_name;
			Class<?> c = Class.forName(critter_class_name);
			Constructor<?> cons = c.getConstructor();
			// create a critter based on the given critter_class_name
			Critter cr = (Critter) cons.newInstance();
			// initialize the position and energy for the critters
			cr.x_coord = Critter.getRandomInt(Params.world_width);
			cr.y_coord = Critter.getRandomInt(Params.world_height);
			cr.energy = Params.start_energy;
			population.add(cr);
		}
		catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		catch (IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		catch (InstantiationException e) {
			throw new InvalidCritterException(critter_class_name);
		} 
		catch (IllegalArgumentException e) {
			throw new InvalidCritterException(critter_class_name);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		critter_class_name = "assignment5." + critter_class_name;
		Class<?> c = null;
		try {
			c = Class.forName(critter_class_name);
		} 
		catch(ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		catch (IllegalArgumentException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		List<Critter> result = new java.util.ArrayList<Critter>();
		for(Critter crit : population) {
			if(c.isInstance(crit)) {
				result.add(crit);
			}
		}
		return result;
	}
	
	
	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	public static String runStats(List<Critter> critters) {return "";}
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
	}
	
	//----------------Helper Functions---------------------
	protected void setEnergy(int energy) {
		this.energy =energy;
	}
	/**
	 * Create a method that returns a random number
	 */
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	/**
	 * Create the 2D torus of the game map (wrap to the other side at the edge)
	 * @param distanceToTravel
	 * @return correct x coordinate to go
	 */
	private final int travelX(int distanceToTravel) {
		if(x_coord + distanceToTravel > Params.world_width - 1) {
			return x_coord + distanceToTravel - Params.world_width;
		}
		else if(x_coord + distanceToTravel < 0) {
			return x_coord + distanceToTravel + Params.world_width;
		}
		else {
			return x_coord + distanceToTravel;
		}
	}
	
	/**
	 * Create the 2D torus of the game map (wrap to the other side at the edge)
	 * @param distanceToTravel
	 * @return correct y coordinate to go
	 */
	private final int travelY(int distanceToTravel) {
		if(this.y_coord + distanceToTravel > Params.world_height - 1) {
			return y_coord + distanceToTravel - Params.world_height;
		}
		else if(this.y_coord+distanceToTravel < 0) {
			return y_coord + distanceToTravel + Params.world_height;
		}
		else {
			return this.y_coord + distanceToTravel;
		}	
	}
	
	/**
	 * Return true if the critter is alive, false otherwise
	 */
	public boolean isAlive() {
		if(this.energy>0) {
			return true;
		}
		return false;
	}
	
	//-------------------Implement the TestCritter class (Algea)---------------------
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
}

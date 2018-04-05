package assignment5;
/* CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Ronghao Zhang
 * rz4453
 * 15505
 * Wenxuan Wu
 * ww6726
 * 15505
 * Slip days used: <0>
 * Spring 2018
 */
import assignment5.Critter.CritterShape;

/*
 * Costume critter 
 * RonCritter1 "R"	(Assassin)
 * Fight:
 * 	Only fights if the energy is above 100, always eats algae (when HP is below 50)
 * 
 * Turning:
 * 	Random direction
 * 
 * Walk or Run:
 * 	Always Runs
 * 
 * Reproduction:
 * 	Only reproduce when the energy is above 150
 * 
 */
public class Critter4 extends Critter {
	private int dir;
	private int algae_consumed = 0;
	private int fight_encountered = 0;
	
	// new for assignment5
	@Override
	public CritterShape viewShape() { return CritterShape.CIRCLE; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.VIOLET; }
	public static String runStats(java.util.List<Critter> ron2s) {
		int total_crits = 0;
		int total_energy = 0;
		int total_algae = 0;
		int total_fight = 0;

		for (Object obj : ron2s) {
			total_crits++;
			Critter4 c = (Critter4) obj;
			total_energy += c.getEnergy();
			total_algae += c.algae_consumed;
			total_fight += c.fight_encountered;
		}

		System.out.print("" + total_crits + " total RonCritter2s    ");
		System.out.print("Average energy: " + total_energy / total_crits + "	");
		System.out.print("Total algae consumed: " + total_algae + "		");
		System.out.print("Total fights encountered: " + total_fight);
		System.out.println();
		return "";
	}
	
	// same as assignment4
	@Override
	public String toString () {
		return "R";
	}
	
	@Override
	public void doTimeStep() {
		dir = Critter.getRandomInt(8);
		run(dir);
		if (getEnergy() > 150) {
			Critter4 child = new Critter4();
			reproduce(child, Critter.getRandomInt(8));
		}
	}

	@Override
	public boolean fight(String opponent) {
		if(opponent.equals("@") && getEnergy() < 50) {
			algae_consumed ++;
			return true;
		}
		else if(this.getEnergy() > 100) {
			fight_encountered ++;
			return true;
		}
		return false;
	}

	
}
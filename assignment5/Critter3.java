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
 * Critter3 "r"	(Monk)
 * Fight:
 * 	Piece lover, never fights, only eats algae (when HP is below 50)
 * 
 * Turning:
 * 	Always go East
 * 
 * Walk or Run:
 * 	Always walks
 * 
 * Reproduction:
 * 	Never reproduce
 * 
 */
public class Critter3 extends Critter {
	
	private int algae_consumed = 0;
	
	// new for assignment5
	@Override
	public CritterShape viewShape() { return CritterShape.CIRCLE; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.RED; }
	public static String runStats(java.util.List<Critter> ron1s) {
		int total_crits = 0;
		int total_energy = 0;
		int total_algae = 0;

		for (Object obj : ron1s) {
			total_crits++;
			Critter3 c = (Critter3) obj;
			total_energy += c.getEnergy();
			total_algae += c.algae_consumed;
		}

		System.out.print("" + total_crits + " total Critter3s    ");
		System.out.print("Average energy: " + total_energy / total_crits + "	");
		System.out.print("Total algae consumed: " + total_algae);
		System.out.println();
		
		return"";
	}
	
	// same as assignment4
	@Override
	public String toString() {
		return "r";
	}
	
	@Override
	public void doTimeStep() {
		walk(0);
	}

	@Override
	public boolean fight(String opponent) {
		if(opponent.equals("@") && getEnergy() < 50) {
			algae_consumed ++;
			return true;
		}
		return false;
	}
	
	
	
	
}

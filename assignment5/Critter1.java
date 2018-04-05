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

/**
 * Critter1 : "W"
 * This Critter will act like a regular critter. Except it will always win a match if it encounters the smaller w
 *  Critter will run or walk depends on its current energy
 * 
 * @author w1001
 *
 */
public class Critter1 extends Critter {
	private int dir;
	private int fightCount;
	
	// new for assignment5
	public CritterShape viewShape() { return CritterShape.CIRCLE; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.AQUA; }
	
	public static String runStats(java.util.List<Critter> Critter1) {
		int fight_Counts = 0;
		for(Critter c: Critter1) {
			Critter1 W = (Critter1) c;
			fight_Counts += W.fightCount;
		}
		System.out.print(Critter1.size() + " total Critter1s ");
		System.out.print(", " + fight_Counts + " fightCounts");
		System.out.println();

		return "";
	}
	// same as assignment4 
	public Critter1() {
		dir = Critter.getRandomInt(8);
		fightCount = 0;
	}
	@Override
	public void doTimeStep() {
		if(getEnergy()>100) {
			run(dir);
		}
		else {
			walk(dir);
		}
		dir = Critter.getRandomInt(8);
	}

	@Override
	public boolean fight(String opponent) {
		this.fightCount++;
		return true;
	}

	@Override
	public String toString () {
		return "W";
	}
	
}
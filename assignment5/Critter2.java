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
 * Critter2 : "w" or "S"
 * This Critter will act like a regular critter. it will lose a battle against W, but if it will against a Craig or algae twice, it can mutate to a S(super)
 * and contains 10000 energy point 
 *  Critter will run or walk depends on its current energy
 * 
 * @author w1001
 *
 */
public class Critter2 extends Critter {
	private int dir;
	private int fightCount;
	private boolean upgrade;
	// new for assignment5
	@Override
	public CritterShape viewShape() { return CritterShape.TRIANGLE; }
	@Override
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.ORANGE; }
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLACK; }

	public static String runStats(java.util.List<Critter> Critter2) {
		int fight_Counts = 0;
		for(Critter c: Critter2) {
			Critter2 w = (Critter2) c;
			fight_Counts += w.fightCount;
		}
		System.out.print(Critter2.size() + " total Critter2s ");
		System.out.print(", " + fight_Counts + " fightCounts");
		System.out.println();
		return "";
	}
	
	// same as assignment4 
	public Critter2() {
		dir = Critter.getRandomInt(8);
		fightCount = 0;
	
	}
	
	@Override
	public void doTimeStep() {
		if(this.fightCount>3) {
			upgrade = true;
			setEnergy(10000);
		}
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
		if(opponent == "W") {
			return false;
		}
		this.fightCount++;
		return true;
	}

	@Override
	public String toString () {
		if(upgrade) {
			return "S";
		}
		return "w";
	}

}
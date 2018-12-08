// James Potratz CSIS 222 - MCC
// This class runs the battleship game. 
// Its purpose is to call the actiongame.java file as defined in the spec posted on blackboard

package battleship;

public class battleship extends gameboard {

	// Global difficulty counter
	public static int difficulty;

	// Call actiongame.java
	public static void main(String[] args) {
		actiongame.main(args);
	}
}

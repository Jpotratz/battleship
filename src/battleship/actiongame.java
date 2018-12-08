// James Potratz CSIS 222 - MCC
// This class contains the game logic for battleship.
// Contains methods to determine if you won, lost, and if you actually hit an object. 

package battleship;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class actiongame extends gameboard {

	// Create a 2d array to track which areas have already been hit
	public static int[][] trackHits;

	// This function determines whether you successfully hit a ship
	public static boolean determineHit(int x, int y) {
		// Decrease your missile count
		missiles = missiles - 1;
		// If this area has already been hit, check if you lost, return false
		if (hasBeenHit(x, y)) {
			checkIfLost();
			return false;
		}
		// update where you shot the missile at
		updateLastAction(x, y);
		// If the missile hit a ship, check if you won/lost, and hit the ship. Return
		// true
		if (gameboard.shipPositions[x][y] != 'Z') {
			findShip(x, y).hitShip();
			checkIfWon();
			checkIfLost();
			return true;
			// If the missile missed, check if you lost, return false, and call missShip.
			// calls from carrier, because object has already been initialized. Could be any
			// ship object called here.
		} else {
			carrier.missShip();
			checkIfLost();
			return false;
		}
	}

	// Function to locate which ship is located at this location. This function
	// should only be called if you actually hit a location.
	public static Ship findShip(int x, int y) {
		// Is this a carrier?
		if (gameboard.shipPositions[x][y] == 'R') {
			return carrier;
		}
		// Is this a battleship?
		if (gameboard.shipPositions[x][y] == 'T') {
			return battle;
		}
		// Is this a destroyer?
		if (gameboard.shipPositions[x][y] == 'Y') {
			return destroyer;
		}
		// Is this a submarine?
		if (gameboard.shipPositions[x][y] == 'S') {
			return submarine;
		}
		// It must be a patrol
		else {
			return patrol;
		}
	}

	// Function to calculate user accuracy
	public static String getAccuracy(int missilesFiredAcc, int hitsAcc) {
		// Create a new decimalformat object to limit it to 2 decimal places
		DecimalFormat df = new DecimalFormat("##.##");
		// Set rounding mode
		df.setRoundingMode(RoundingMode.DOWN);
		// Cast variables to doubles
		double floatMissiles = (double) missilesFiredAcc;
		double floatHits = (double) hitsAcc;
		// Calculate the accuracy
		double accuracy = (floatHits / floatMissiles) * 100;
		// Round the accuracy
		round(accuracy, 2);
		// Return the accuray at 2 decimals
		return df.format(accuracy);
	}

	// Function to round numbers, utilizes a BigDecimal object
	public static double round(double d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	// Update the last action string, write to trackHits array
	public static void updateLastAction(int xcoord, int ycoord) {
		lastAction = "You shot a missile at " + xcoord + ", " + convertCoord(ycoord);
		trackHits[xcoord][ycoord] = 1;
	}

	// Update the whatHappened screen
	public static void updateWhatHappened(String string) {
		whatHappened = string;
	}

	// Convert a y-coordinate input to return the Char equivalent.
	public static char convertCoord(int ycoord) {
		char tempChar = (char) ('A' + (ycoord - 1));
		return tempChar;
	}

	// Check if you won the game
	public static void checkIfWon() {
		// Win condition
		if (shipsRemaining == 0) {
			// Create a new alert object, with a button. Lets you know you won
			Alert alert = new Alert(AlertType.CONFIRMATION, "You won! Thanks for playing!", ButtonType.FINISH);
			alert.showAndWait();
			// Close the program after clicking finish
			Platform.exit();
		}
	}

	// Check if you lost the game
	public static void checkIfLost() {
		// Lose condition. You can win with missile being 0, so make sure they still
		// have ships remaining as well
		if (missiles == 0 && shipsRemaining > 0) {
			// Popup window letting you know you lost
			Alert alert = new Alert(AlertType.CONFIRMATION, "You ran out of missiles! You lose! \n Thanks for playing!",
					ButtonType.FINISH);
			alert.showAndWait();
			// Close the program after clicking finish
			Platform.exit();
		}
	}

	// Initialize the trackHits array to be 0
	// 0 represents not hit, 1 represents hit
	public static void initializeTrackHits(int size) {
		trackHits = new int[size][size];
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				trackHits[row][col] = 0;
	}

	// Function to see if the area has already been shot
	public static boolean hasBeenHit(int x, int y) {
		if (trackHits[x][y] == 1) {
			updateWhatHappened("You already shot a missile here bozo!");
			// Update scoreboard with new whathappened string
			gameboard.updateScoreboard();
			return true;
		} else
			return false;

	}

	// Launch the Gameboard, which launches the game. Calls from gameboard.java
	public static void main(String[] args) {
		gameboard.launch(args);
	}

}

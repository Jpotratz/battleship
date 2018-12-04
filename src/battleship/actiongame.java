// James Potratz CSIS 222 - MCC
// This class contains the game logic for battleship.

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
		updateLastAction(x, y);
		if (gameboard.shipPositions[x][y] != 'Z') {
			findShip(x, y).hitShip();
			checkIfWon();
			checkIfLost();
			return true;
		} else {
			carrier.missShip();
			checkIfLost();
			return false;
		}
	}

	// Function to locate which ship is located at this location
	public static Ship findShip(int x, int y) {
		if (gameboard.shipPositions[x][y] == 'R') {
			return carrier;
		}
		if (gameboard.shipPositions[x][y] == 'T') {
			return battle;
		}
		if (gameboard.shipPositions[x][y] == 'Y') {
			return destroyer;
		}
		if (gameboard.shipPositions[x][y] == 'S') {
			return submarine;
		} else {
			return patrol;
		}
	}

	// Function to calculate user accuracy
	public static String getAccuracy(int missilesFiredAcc, int hitsAcc) {
		DecimalFormat df = new DecimalFormat("##.##");
		df.setRoundingMode(RoundingMode.DOWN);
		double floatMissiles = (double) missilesFiredAcc;
		double floatHits = (double) hitsAcc;
		double accuracy = (floatHits / floatMissiles) * 100;
		round(accuracy, 2);
		return df.format(accuracy);
	}

	// Function to round numbers
	public static double round(double d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public static void updateLastAction(int xcoord, int ycoord) {
		lastAction = "You shot a missile at " + xcoord + ", " + convertCoord(ycoord);
	}

	public static void updateWhatHappened(String string) {
		whatHappened = string;
	}

	// Convert a y-coordinate input to return the Char equivalent.
	public static char convertCoord(int ycoord) {
		char tempChar = (char) ('A' + (ycoord - 1));
		return tempChar;
	}

	public static void checkIfWon() {
		if (shipsRemaining == 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "You won!", ButtonType.FINISH);
			alert.showAndWait();
			Platform.exit();
		}
	}

	public static void checkIfLost() {
		if (missiles == 0 && shipsRemaining > 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "You ran out of missiles! You lose!", ButtonType.FINISH);
			alert.showAndWait();
			Platform.exit();
		}
	}

	// Initialize the trackHits array to be 0
	// 0 represents not hit, 1 represents hit
	public static void initializeTrackHits(int size) {
		shipPositions = new char[size][size];
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				shipPositions[row][col] = 0;
	}

	// Launch the Gameboard, which launches the game
	public static void main(String[] args) {
		gameboard.launch(args);
	}

}

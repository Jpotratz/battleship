// James Potratz CSIS 222 - MCC
// This class contains the code to build the game board, placement of pieces
// tracking score and other code for the game

package battleship;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class gameboard extends Application {

	// Create a new Tile object map
	private Tile[][] board;
	// Creates a new pane to play Battleship on
	private Pane root = new Pane();
	// Creates the ships
	public static carrierShip carrier = new carrierShip();
	public static battleshipShip battle = new battleshipShip();
	public static destroyerShip destroyer = new destroyerShip();
	public static submarineShip submarine = new submarineShip();
	public static patrolShip patrol = new patrolShip();
	// Missile counter
	protected static int missiles;
	// Missiles fired counter
	private static int missilesFired;
	// Create a new scoreboard
	public static Scoreboard scoreboard = new Scoreboard();
	// Boundary variable for the map pane
	public int boundary = 0;
	// Create a 2D array to track ship positions separate from display
	public static char[][] shipPositions;
	// Variable to track how many ships are remaining
	public static int shipsRemaining = 5;
	// Variable to track how many times a user has hit ships
	public static int hits = 0;
	// String to hold the text of the last action
	public static String lastAction = "test";
	// String to hold the text of what occured
	public static String whatHappened = "test";

	// Generate the content for the pane. Includes Scoreboard position, tiles, ship
	// placement and missile count
	public Parent createContent(int level) {
		// Content creation for Beginner level
		if (level == 1) {
			board = new Tile[7][7];
			// Sets the size of the pane "root" to 700x700 pixels
			root.setPrefSize(1005, 700);
			// Creating the playing field using for loops, Y values is i
			for (int i = 0; i < 7; i++) {
				// X values is j
				for (int j = 0; j < 7; j++) {
					// Create a tile, and then translate it 100 pixels x and y
					Tile tile = new Tile(j, i);
					tile.setTranslateX(j * 50);
					tile.setTranslateY(i * 50);
					// Display the object tile
					root.getChildren().add(tile);
					// Populate the array board, used in checking ship positioning
					board[j][i] = tile;
				}
			}
			initializeShipPositionArray(7);
			scoreboard.setTranslateX(700);
			scoreboard.setTranslateY(0);
			root.getChildren().add(scoreboard);
			drawCoordinatesPerDifficulty();
			missiles = 30;
			initializeScoreboard();
			generateShips();

		}
		// Content creation for Standard level
		if (level == 2) {
			board = new Tile[10][10];
			// Sets the size of the pane "root" to 1000x1000 pixels
			root.setPrefSize(1005, 700);
			// Creating the playing field using for loops, Y values is i
			for (int i = 0; i < 10; i++) {
				// X values is j
				for (int j = 0; j < 10; j++) {
					// Create a tile, and then translate it 100 pixels x and y
					Tile tile = new Tile(j, i);
					tile.setTranslateX(j * 50);
					tile.setTranslateY(i * 50);
					// Display the object tile
					root.getChildren().add(tile);
					// Populate the array board, used in checking combos
					board[j][i] = tile;
				}
			}
			initializeShipPositionArray(10);
			scoreboard.setTranslateX(700);
			scoreboard.setTranslateY(0);
			root.getChildren().add(scoreboard);
			drawCoordinatesPerDifficulty();
			missiles = 50;
			initializeScoreboard();
			generateShips();
		}
		// Content creation for Advanced level
		if (level == 3) {
			board = new Tile[13][13];
			// Sets the size of the pane "root" to 1300x1300 pixels
			root.setPrefSize(1005, 700);
			// Creating the playing field using for loops, Y values is i
			for (int i = 0; i < 13; i++) {
				// X values is j
				for (int j = 0; j < 13; j++) {
					// Create a tile, and then translate it 100 pixels x and y
					Tile tile = new Tile(j, i);
					tile.setTranslateX(j * 50);
					tile.setTranslateY(i * 50);
					// Display the object tile
					root.getChildren().add(tile);
					// Populate the array board, used in checking combos
					board[j][i] = tile;
				}
			}
			initializeShipPositionArray(13);
			scoreboard.setTranslateX(700);
			scoreboard.setTranslateY(0);
			root.getChildren().add(scoreboard);
			drawCoordinatesPerDifficulty();
			missiles = 75;
			initializeScoreboard();
			generateShips();
		}
		// Display the pane
		return root;
	}

	private void generateShips() {
		generateShipPlacement(carrier);
		generateShipPlacement(battle);
		generateShipPlacement(destroyer);
		generateShipPlacement(submarine);
		generateShipPlacement(patrol);

	}

	public static class ScoreText extends Text {
		public ScoreText() {
			this.setWrappingWidth(300);
		}
	}

	public static class Scoreboard extends StackPane {

		private ScoreText scoretext = new ScoreText();

		public Scoreboard() {
			// Create border for scoreboard
			Rectangle border = new Rectangle(300, 700);
			// Set the fill to transparent
			border.setFill(null);
			// Set the border to black
			// border.setStroke(Color.BLACK);
			// Increase the font of the text object to 32 Pixels
			scoretext.setFont(Font.font(26));
			// Set the alignment of the objects to the center
			setAlignment(Pos.CENTER);
			// Add objects of the stackpane text and border
			getChildren().addAll(border, scoretext);
		}

		private void drawScore(String words) {
			scoretext.setText(words);
		}

	}

	public static void updateScoreboard() {
		scoreboard.drawScore("Battleship" + "\nCreated by James Potratz" + "\n\n\nScoreboard" + "\n\nMissiles left: "
				+ missiles + "\nShips left: " + shipsRemaining + "\nAccuracy: "
				+ actiongame.getAccuracy(missilesFired, hits) + "\n\n\nLast Action \n" + lastAction + "\n"
				+ whatHappened);
	}

	public static void initializeScoreboard() {
		scoreboard.drawScore("Battleship" + "\nCreated by James Potratz" + "\n\n\nScoreboard" + "\n\nMissiles left: "
				+ missiles + "\nShips left: " + shipsRemaining + "\nAccuracy: " + 100.00);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(createContent(battleship.difficulty)));
		primaryStage.show();
		primaryStage.toFront();
	}

	private class Tile extends StackPane {

		// Application is empty by default
		private Text text = new Text();
		// X and Y coordinates
		public int xcoord;
		public int ycoord;

		public Tile(int x, int y) {
			// Creating a border for the rectangle
			Rectangle border = new Rectangle(50, 50);
			// Set the fill to transparent
			border.setFill(null);
			// Store the location of the tile
			storeLocation(x, y);
			// Set the border to black
			border.setStroke(Color.BLACK);
			// Increase the font of the text object to 72 Pixels
			text.setFont(Font.font(32));
			// Set the alignment of the objects to the center
			setAlignment(Pos.CENTER);
			// Add objects of the stackpane text and border
			getChildren().addAll(border, text);
			setOnMouseClicked(event -> {
				// If left click
				if (event.getButton() == MouseButton.PRIMARY) {
					missilesFired = missilesFired + 1;
					if (actiongame.determineHit(this.getXCoord(), this.getYCoord())) {
						// Call the drawChar function
						drawChar(actiongame.findShip(getXCoord(), getYCoord()).getShipIcon());
					} else
						drawChar('X');
				}
			});

		};

		private void drawChar(char letter) {
			String temp = "" + letter;
			text.setText(temp);
		}

		private void drawInt(int number) {
			String temp = "" + number;
			text.setText(temp);
		}

		public String getValue() {
			return text.getText();
		}

		public void storeLocation(int x, int y) {
			xcoord = x;
			ycoord = y;
		}

		public int getXCoord() {
			return xcoord;
		}

		public int getYCoord() {
			return ycoord;
		}

	}

	static public class Ship {

		public int shipLength;
		public int shipHealth;
		public boolean isSunk = false;
		public char shipIcon;
		public String shipName;

		// Constructor for Ship object
		public Ship() {
		}

		// Damage the ship because it has been hit
		public void hitShip() {
			shipHealth = shipHealth - 1;
			missiles = missiles - 1;
			actiongame
					.updateWhatHappened("You have hit their " + getShipName() + "! Remaining HP is " + getShipHealth());
			if (shipHealth == 0) {
				sinkShip();
			}
			hits = hits + 1;
			updateScoreboard();
		}

		// Let the player know you missed the ship
		public void missShip() {
			missiles = missiles - 1;
			actiongame.updateWhatHappened("You missed!");
			updateScoreboard();
		}

		// Sink the ship because its health is 0
		public void sinkShip() {
			actiongame.updateWhatHappened("You have sunk their " + getShipName() + "!");
			isSunk = true;
			shipsRemaining = shipsRemaining - 1;
			updateScoreboard();
		}

		// Get the ships sunk status
		public boolean getIsSunk() {
			return isSunk;
		}

		// Get the ship health
		public int getShipHealth() {
			return shipHealth;
		}

		// Get the name of the ship
		public String getShipName() {
			return shipName;
		}

		// Get the ships Icon
		public char getShipIcon() {
			return shipIcon;
		}

		// Get the ship length
		public int getShipLength() {
			return shipLength;
		}

	}

	public static class carrierShip extends Ship {

		public carrierShip() {
			shipLength = 5;
			shipHealth = 5;
			shipIcon = 'R';
			shipName = "Carrier USS Mississippi";
		}
	}

	private static class battleshipShip extends Ship {

		public battleshipShip() {
			shipLength = 4;
			shipHealth = 4;
			shipIcon = 'T';
			shipName = "Battleship USS Arizona";
		}
	}

	private static class destroyerShip extends Ship {

		public destroyerShip() {
			shipLength = 3;
			shipHealth = 3;
			shipIcon = 'Y';
			shipName = "Destroyer USS Decatur";
		}
	}

	private static class submarineShip extends Ship {

		public submarineShip() {
			shipLength = 3;
			shipHealth = 3;
			shipIcon = 'S';
			shipName = "Submarine USS Barracuda";
		}
	}

	private static class patrolShip extends Ship {

		public patrolShip() {
			shipLength = 2;
			shipHealth = 2;
			shipIcon = 'P';
			shipName = "Patrol Ship USS Pegasus";
		}
	}

	public void drawCoordinatesPerDifficulty() {
		if (battleship.difficulty == 1) {
			drawCoordinatesHelper(1);
		}
		if (battleship.difficulty == 2) {
			drawCoordinatesHelper(2);
		}
		if (battleship.difficulty == 3) {
			drawCoordinatesHelper(3);
		}

	}

	public void drawCoordinatesHelper(int difficulty) {
		char coordinateLetter = 'A';
		int coordinateInteger = 1;
		if (difficulty == 1) {
			boundary = 7;
		}
		if (difficulty == 2) {
			boundary = 10;
		}
		if (difficulty == 3) {
			boundary = 13;
		}

		for (int i = 1; i < boundary; i++) {
			board[0][i].drawChar(coordinateLetter);
			board[i][0].drawInt(coordinateInteger);
			coordinateLetter += 1;
			coordinateInteger += 1;
		}
	}

	// Function to generate ship placement
	// General rules: Ship cannot be placed on top of coordinates.
	// Ship cannot extend off board.
	// Ship cannot
	public void generateShipPlacement(Ship ship) {
		// Define array boundary as pane boundary - 1. ARRAYS START AT 0
		int arrayBoundary = boundary - 1;
		// Generate random x, y coordinates between the boundaries. min is 1 because
		// coordinates are at 0
		int startingPosX = randomInt(1, arrayBoundary);
		int startingPosY = randomInt(1, arrayBoundary);
		// Define a variable to randomly choose whether to draw the ship horizantally or
		// vertically
		int drawVertOrHori = randomInt(1, 2);
		// Define boolean for while loops. Used to check if the position can be drawn
		boolean validCoords = false;
		// boolean for difficulty 1 logic
		boolean validone = false;

		// Separate logic for difficulty 1 since the map is small

		for (int i = 1; i <= arrayBoundary; i++) {
			for (int j = 1; j <= arrayBoundary; j++) {
				startingPosX = i;
				startingPosY = j;
			}
		}

		if (battleship.difficulty != 6)

		{
			while (!validCoords) {
				// Loop to ensure the ship can be drawn right, down, and not in an occupied
				// space.
				try {
					if ((startingPosX + ship.getShipLength() > arrayBoundary && drawVertOrHori == 2)
							|| (isOccupiedHoriz(startingPosX, startingPosY, ship) && drawVertOrHori == 2)
							|| (startingPosY + ship.getShipLength() > arrayBoundary && drawVertOrHori == 1)
							|| (isOccupiedVert(startingPosX, startingPosY, ship) && drawVertOrHori == 1)) {
						startingPosX = randomInt(1, arrayBoundary);
						startingPosY = randomInt(1, arrayBoundary);
					} else {
						validCoords = true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					throw e;
				}
			}
		}
		System.out.println(startingPosX);
		System.out.println(startingPosY);

		if (drawVertOrHori == 1) {
			drawShipVert(startingPosX, startingPosY, ship);
		}
		if (drawVertOrHori == 2) {
			drawShipHori(startingPosX, startingPosY, ship);
		}
	}

	// Function to draw the ship vertically
	public void drawShipVert(int startingX, int startingY, Ship ship) {
		for (int i = 0; i < ship.getShipLength(); i++) {
			shipPositions[startingX][startingY + i] = ship.getShipIcon();
			// board[startingX][startingY + i].drawChar(ship.getShipIcon());
		}
	}

	// Function to draw the ship horizantally
	public void drawShipHori(int startingX, int startingY, Ship ship) {
		for (int i = 0; i < ship.getShipLength(); i++) {
			shipPositions[startingX + i][startingY] = ship.getShipIcon();
			// board[startingX + i][startingY].drawChar(ship.getShipIcon());
		}
	}

	// Returns true if occupied
	public boolean isOccupiedHoriz(int xcoord, int ycoord, Ship ship) {
		boolean check = false;
		if (ship.getShipIcon() == 'R') {
			return false;
		}
		try {
			for (int i = 0; i < ship.getShipLength(); i++) {
				if (shipPositions[xcoord + i][ycoord] != 'Z') {
					check = true;
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return check;
	}

	// Returns true if occupied
	public boolean isOccupiedVert(int xcoord, int ycoord, Ship ship) {
		boolean check = false;
		if (ship.getShipIcon() == 'R') {
			return false;
		}
		try {
			for (int i = 0; i < ship.getShipLength(); i++) {
				if (shipPositions[xcoord][ycoord + i] != 'Z') {
					check = true;
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return check;
	}

	// Generate a random integer between certain values. Used when generating ship
	// placement
	// min and max are inclusive
	public static int randomInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	// Initialize the array of characters to be 'Z'
	public static void initializeShipPositionArray(int size) {
		shipPositions = new char[size][size];
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				shipPositions[row][col] = 'Z';
	}

	// Launch the applications
	public static void main(String[] args) {
		launch(args);
	}
}
// James Potratz CSIS 222 - MCC
// For the game of battleship.
// This class contains the code to build the game board, placement of pieces
// tracking score, ship object creation.
// Utilizes JavaFX for gameboard handling

package battleship;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class gameboard extends Application {

	// Declare tile object map
	public static Tile[][] board;
	// Creates a new pane to play Battleship on
	private Pane root = new Pane();
	// Creates the ship objects
	public static carrierShip carrier = new carrierShip();
	public static battleshipShip battle = new battleshipShip();
	public static destroyerShip destroyer = new destroyerShip();
	public static submarineShip submarine = new submarineShip();
	public static patrolShip patrol = new patrolShip();
	// Missile counter
	protected static int missiles;
	// Missiles fired counter
	private static int missilesFired;
	// Create a new scoreboard object
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
	public static String lastAction;
	// String to hold the text of what occurred
	public static String whatHappened;
	// New constructors for each difficulty button
	public static ButtonType beginner = new ButtonType("Beginner 6x6");
	public static ButtonType standard = new ButtonType("Standard 9x9");
	public static ButtonType advanced = new ButtonType("Advanced 12x12");
	public static ButtonType revealMap = new ButtonType("Reveal the map");
	// Boolean for revealing the map using a cheat
	public static boolean revealMapCheat = false;
	// variables for resolution of pane
	public static final int Y_RESOLUTION = 1005;
	public static final int X_RESOLUTION = 700;

	// Generate the content for the pane. Includes Scoreboard position, tiles, ships
	// placement, arrays and missile count
	// Array sizes will typically be 1 larger than the difficulty setting due to the
	// coordinates
	public Parent createContent() {
		// Content creation for Beginner level
		if (battleship.difficulty == 1) {
			// Create board array for beginner
			board = new Tile[7][7];
			// Sets the size of the pane
			root.setPrefSize(Y_RESOLUTION, X_RESOLUTION);
			// Creating the playing field using for loops, Y values is i

			for (int i = 0; i < 7; i++) {
				// X values is j
				for (int j = 0; j < 7; j++) {
					// Create a tile, and then translate it 50 pixels x and y
					Tile tile = new Tile(j, i);
					tile.setTranslateX(j * 50);
					tile.setTranslateY(i * 50);
					// Display the object tile
					root.getChildren().add(tile);
					// Populate the array board
					board[j][i] = tile;
				}
			}
			// call the initializeTrackHits method for beginner difficulty
			actiongame.initializeTrackHits(7);
			// call the initializeShipPositionArray method for beginner difficulty
			initializeShipPositionArray(7);
			// Move the scoreboard into its correct position
			scoreboard.setTranslateX(700);
			scoreboard.setTranslateY(0);
			// Display the scoreboard object
			root.getChildren().add(scoreboard);
			// Draw the coordinates to the pane
			drawCoordinatesPerDifficulty();
			// Set missile count for beginner
			missiles = 30;
			// Set the initial message of the scoreboard
			initializeScoreboard();
			// Generate ship positions. Note on beginner difficulty this map result in an
			// infinite loop.
			generateShips();

		}
		// Content creation for Standard level
		if (battleship.difficulty == 2) {
			// Create board array for standard
			board = new Tile[10][10];
			// Sets the size of the pane
			root.setPrefSize(Y_RESOLUTION, X_RESOLUTION);
			// Creating the playing field using for loops, Y values is i
			for (int i = 0; i < 10; i++) {
				// X values is j
				for (int j = 0; j < 10; j++) {
					// Create a tile, and then translate it 50 pixels x and y
					Tile tile = new Tile(j, i);
					tile.setTranslateX(j * 50);
					tile.setTranslateY(i * 50);
					// Display the object tile
					root.getChildren().add(tile);
					// Populate the array board
					board[j][i] = tile;
				}
			}
			// Call initialize functions for standard
			actiongame.initializeTrackHits(10);
			initializeShipPositionArray(10);
			// Move scoreboard into place and display
			scoreboard.setTranslateX(700);
			scoreboard.setTranslateY(0);
			root.getChildren().add(scoreboard);
			// Draw coordinates
			drawCoordinatesPerDifficulty();
			// Initialize missile count
			missiles = 50;
			// Display first scoreboard message
			initializeScoreboard();
			// Generate ship positions
			generateShips();
		}
		// Content creation for Advanced level
		if (battleship.difficulty == 3) {
			// Create board for advanced
			board = new Tile[13][13];
			// Sets the size of the pane "root" to 1300x1300 pixels
			root.setPrefSize(Y_RESOLUTION, X_RESOLUTION);
			// Creating the playing field using for loops, Y values is i
			for (int i = 0; i < 13; i++) {
				// X values is j
				for (int j = 0; j < 13; j++) {
					// Create a tile, and then translate it 50 pixels x and y
					Tile tile = new Tile(j, i);
					tile.setTranslateX(j * 50);
					tile.setTranslateY(i * 50);
					// Display the object tile
					root.getChildren().add(tile);
					// Populate the array board
					board[j][i] = tile;
				}
			}
			// Difficulty initialization methods for advanced.
			actiongame.initializeTrackHits(13);
			initializeShipPositionArray(13);
			// Move scoreboard into place and display
			scoreboard.setTranslateX(700);
			scoreboard.setTranslateY(0);
			root.getChildren().add(scoreboard);
			// Draw coordinates
			drawCoordinatesPerDifficulty();
			// Set missile count
			missiles = 75;
			// Display first scoreboard message
			initializeScoreboard();
			// Generate ship positions
			generateShips();
		}
		// Display the pane
		return root;
	}

	// Helper method to place each type of ship. calls the generateShipPlacement
	// method
	private void generateShips() {
		generateShipPlacement(carrier);
		generateShipPlacement(battle);
		generateShipPlacement(destroyer);
		generateShipPlacement(submarine);
		generateShipPlacement(patrol);

	}

	// Constructor for setting the wrapping width of scoreboard text to 300 pixels
	public static class ScoreText extends Text {
		// Default constructor
		public ScoreText() {
			this.setWrappingWidth(300);
		}
	}

	// Class that creates a scoreboard. Uses same extension as tile.
	public static class Scoreboard extends StackPane {
		// Create the scoreboard message object that will be used
		private ScoreText scoretext = new ScoreText();

		// Default constructor
		public Scoreboard() {
			// Create border for scoreboard
			Rectangle border = new Rectangle(300, 700);
			// Set the fill to transparent
			border.setFill(null);
			// Increase the font of the text object to 26 Pixels
			scoretext.setFont(Font.font(26));
			// Set the alignment of the objects to the center
			setAlignment(Pos.CENTER);
			// Add objects of the stackpane text and border
			getChildren().addAll(border, scoretext);
		}

		// Method to display the scoreboard messages
		private void drawScore(String words) {
			scoretext.setText(words);
		}

	}

	// Helper method to update the scoreboard. Calls the drawScore function from
	// Scoreboard class
	public static void updateScoreboard() {
		scoreboard.drawScore("Battleship" + "\nCreated by James Potratz" + "\n\n\nScoreboard" + "\n\nMissiles left: "
				+ missiles + "\nShips left: " + shipsRemaining + "\nAccuracy: "
				+ actiongame.getAccuracy(missilesFired, hits) + "\n\n\nLast Action \n" + lastAction + "\n"
				+ whatHappened);
	}

	// Helper method for first displaying the scoreboard. Slightly different than
	// updateScoreboard, but the same premise
	public static void initializeScoreboard() {
		scoreboard.drawScore("Battleship" + "\nCreated by James Potratz" + "\n\n\nScoreboard" + "\n\nMissiles left: "
				+ missiles + "\nShips left: " + shipsRemaining + "\nAccuracy: " + 100.00);
	}

	@Override
	// Starts the game
	public void start(Stage primaryStage) throws Exception {
		// Call getDifficulty to see what type of game the user would like to play
		getDifficulty();
		// See if the user wants to cheat
		cheats();
		// Display the content
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();
		// Bring the game to the front, makes it the active window on desktop
		// environment
		primaryStage.toFront();
	}

	// Class that is used for displaying the objects
	public class Tile extends StackPane {

		// Application is empty by default
		private Text text = new Text();
		// X and Y coordinates. Initialized by the constructor
		public int xcoord;
		public int ycoord;

		// Default constructor for a tile object. asks for x and y values so its
		// position is known recursively.
		public Tile(int x, int y) {
			// Creating a border for the rectangle
			Rectangle border = new Rectangle(50, 50);
			// Set the fill to transparent
			border.setFill(null);
			// Store the location of the tile
			storeLocation(x, y);
			// Set the border to black
			border.setStroke(Color.BLACK);
			// Increase the font of the text object to 32 Pixels
			text.setFont(Font.font(32));
			// Set the alignment of the objects to the center
			setAlignment(Pos.CENTER);
			// Add objects of the stackpane text and border
			getChildren().addAll(border, text);
			// This function defines what happens when you left click. Most of the functions
			// called are from actiongame class
			setOnMouseClicked(event -> {
				// If left click
				if (event.getButton() == MouseButton.PRIMARY) {
					// Check if it is a coordinate tile
					if (!(this.getXCoord() == 0 || this.getYCoord() == 0)) {
						// If not coordinate, increment missilesFired
						missilesFired = missilesFired + 1;
						// Check if you hit a ship with your missile
						if (actiongame.determineHit(this.getXCoord(), this.getYCoord())) {
							// If hit, call the drawChar function to write what ship it was to the tile
							drawChar(actiongame.findShip(getXCoord(), getYCoord()).getShipIcon());
						} else {
							// If not a hit, draw an X. If you hit the same spot twice, this will also draw
							// an X
							drawChar('X');
						}
					}
				}

			});

		};

		// Helper function to draw the char
		public void drawChar(char letter) {
			String temp = "" + letter;
			text.setText(temp);
		}

		// Helper function to draw an int to a tile. Used for coordinates
		private void drawInt(int number) {
			String temp = "" + number;
			text.setText(temp);
		}

		// Helper method to see what value is currently in the tile. Currently not used
		public String getValue() {
			return text.getText();
		}

		// Helper method to write the location of the tile within the tile object itself
		public void storeLocation(int x, int y) {
			xcoord = x;
			ycoord = y;
		}

		// Helper method to see where the tile is located on the X coordinate
		public int getXCoord() {
			return xcoord;
		}

		// Helper method to see where the tile is located on the y coordinate
		public int getYCoord() {
			return ycoord;
		}

	}

	// Class that defines a basic ship.
	static public class Ship {
		// Variables that hold important ship information
		public int shipLength;
		public int shipHealth;
		public boolean isSunk = false;
		public char shipIcon;
		public String shipName;

		// Constructor for Ship object. Must be implemented by subclasses
		public Ship() {
		}

		// Helper method to damage the ship because it has been hit
		public void hitShip() {
			shipHealth = shipHealth - 1;
			actiongame.updateWhatHappened(
					"You have hit their " + getShipName() + "! \nNice Hit! \nRemaining HP is " + getShipHealth());
			if (shipHealth == 0) {
				sinkShip();
			}
			hits = hits + 1;
			updateScoreboard();
		}

		// Helper method to let the player know you missed the ship
		public void missShip() {
			actiongame.updateWhatHappened("You missed! You suck!");
			updateScoreboard();
		}

		// Helper method to sink the ship because its health is 0
		public void sinkShip() {
			actiongame.updateWhatHappened("You have sunk their " + getShipName() + "!\nYou are good at this!");
			isSunk = true;
			shipsRemaining = shipsRemaining - 1;
			updateScoreboard();
		}

		// Helper method to call the ship sunk status
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

	// Class extended ship to define a carrier. Contains default constructor
	// information
	public static class carrierShip extends Ship {

		public carrierShip() {
			shipLength = 5;
			shipHealth = 5;
			shipIcon = 'R';
			shipName = "Carrier USS Mississippi";
		}
	}

	// Class extended ship to define a battleship. Contains default constructor
	// information
	private static class battleshipShip extends Ship {

		public battleshipShip() {
			shipLength = 4;
			shipHealth = 4;
			shipIcon = 'T';
			shipName = "Battleship USS Arizona";
		}
	}

	// Class extended ship to define a destroyer. Contains default constructor
	// information
	private static class destroyerShip extends Ship {

		public destroyerShip() {
			shipLength = 3;
			shipHealth = 3;
			shipIcon = 'Y';
			shipName = "Destroyer USS Decatur";
		}
	}

	// Class extended ship to define a Submarine. Contains default constructor
	// information
	private static class submarineShip extends Ship {

		public submarineShip() {
			shipLength = 3;
			shipHealth = 3;
			shipIcon = 'S';
			shipName = "Submarine USS Barracuda";
		}
	}

	// Class extended ship to define a Patrol. Contains default constructor
	// information
	private static class patrolShip extends Ship {

		public patrolShip() {
			shipLength = 2;
			shipHealth = 2;
			shipIcon = 'P';
			shipName = "Patrol Ship USS Pegasus";
		}
	}

	// Helper method to draw the coordinates to the board. uses
	// drawCoordinatesHelper
	public void drawCoordinatesPerDifficulty() {
		// If beginner
		if (battleship.difficulty == 1) {
			drawCoordinatesHelper(1);
		}
		// If standard
		if (battleship.difficulty == 2) {
			drawCoordinatesHelper(2);
		}
		// If advanced
		if (battleship.difficulty == 3) {
			drawCoordinatesHelper(3);
		}

	}

	// Helper method that determines coordinates for each difficulty
	public void drawCoordinatesHelper(int difficulty) {
		// Initialize variables to the beginning values. They will be incremented.
		char coordinateLetter = 'A';
		int coordinateInteger = 1;
		// Set the boundary of the board for each difficulty. Boundary is 1 greater than
		// the difficulty map size
		if (difficulty == 1) {
			boundary = 7;
		}
		if (difficulty == 2) {
			boundary = 10;
		}
		if (difficulty == 3) {
			boundary = 13;
		}
		// Write the coordinates to the board. Increment each as it proceeds through the
		// for loop
		for (int i = 1; i < boundary; i++) {
			board[0][i].drawChar(coordinateLetter);
			board[i][0].drawInt(coordinateInteger);
			// ACSII chars can be incremented
			coordinateLetter += 1;
			coordinateInteger += 1;
		}
	}

	// Function to generate ship placement
	// General rules: Ship cannot be placed on top of coordinates.
	// Ship cannot extend off board.
	// Ship cannot be placed where there is already a ship
	public void generateShipPlacement(Ship ship) {
		// Define array boundary as pane boundary - 1. ARRAYS START AT 0
		int arrayBoundary = boundary - 1;
		// Generate random x, y coordinates between the boundaries. min is 1 because
		// coordinates are at 0
		int startingPosX = randomInt(1, arrayBoundary);
		int startingPosY = randomInt(1, arrayBoundary);
		// Define a variable to randomly choose whether to draw the ship horizantally or
		// vertically. 1 is vertical, 2 is horizantal
		int drawVertOrHori = randomInt(1, 2);
		// Define boolean for while loops. Used to check if the position can be drawn
		boolean validCoords = false;

		// Main logic while loops for calculating ship placement
		while (!validCoords) {
			// Loop to ensure the ship can be drawn right, down, and not in an occupied
			// space. The if statement can be broken down as follows.
			// Can it be drawn right without exceeding boundary, if it is being drawn right
			// Is it occupied to the right, if it is being drawn right
			// can it be drawn down without exceeding boundary, if it is being drawn down
			// Is it occupied downward, if it is being drawn down
			try {
				// If any of these statements are true, re-generate the starting position of the
				// ship
				if ((startingPosX + ship.getShipLength() > arrayBoundary && drawVertOrHori == 2)
						|| (isOccupiedHoriz(startingPosX, startingPosY, ship) && drawVertOrHori == 2)
						|| (startingPosY + ship.getShipLength() > arrayBoundary && drawVertOrHori == 1)
						|| (isOccupiedVert(startingPosX, startingPosY, ship) && drawVertOrHori == 1)) {
					startingPosX = randomInt(1, arrayBoundary);
					startingPosY = randomInt(1, arrayBoundary);
				}
				// Else, the ship is valid. End the while loop
				else {
					validCoords = true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				throw e;
			}
		}
		// If being drawn down, draw it. Coordinates already confirmed valid
		if (drawVertOrHori == 1) {
			drawShipVert(startingPosX, startingPosY, ship);
		} // If being drawn right, draw it. Coordinates already confirmed valid
		if (drawVertOrHori == 2) {
			drawShipHori(startingPosX, startingPosY, ship);
		}
	}

	// Function to draw the ship vertically
	public void drawShipVert(int startingX, int startingY, Ship ship) {
		// For loop will continue for the length of the ship
		for (int i = 0; i < ship.getShipLength(); i++) {
			// Write the ship positions to the array
			shipPositions[startingX][startingY + i] = ship.getShipIcon();
			// If you are cheating, write it to the board
			if (revealMapCheat) {
				board[startingX][startingY + i].drawChar(ship.getShipIcon());
			}
		}
	}

	// Function to draw the ship horizantally
	public void drawShipHori(int startingX, int startingY, Ship ship) {
		// For loop will continue for the length of the ship
		for (int i = 0; i < ship.getShipLength(); i++) {
			// Write the ship positions to the array
			shipPositions[startingX + i][startingY] = ship.getShipIcon();
			// If you are cheating, write it to the board
			if (revealMapCheat) {
				board[startingX + i][startingY].drawChar(ship.getShipIcon());
			}
		}
	}

	// Function to check if the board will be occupied horizantally for the length
	// of the ship
	// Passes in starting coordinates and ship object
	public boolean isOccupiedHoriz(int xcoord, int ycoord, Ship ship) {
		// Boolean helper definition
		boolean check = false;
		// Return false immediatly if its a carrier. No other ships have been drawn yet.
		// See generateShips()
		if (ship.getShipIcon() == 'R') {
			return false;
		}
		// Check if occupied for the length of the ship horizantally
		try {
			for (int i = 0; i < ship.getShipLength(); i++) {
				// shipPositions array is initialized to Z. If there is anything but but Z
				// there, it is occupied.
				if (shipPositions[xcoord + i][ycoord] != 'Z') {
					// It is occupied, return true, break
					check = true;
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return check;
	}

	// Function to check if the board will be occupied vertically for the length of
	// the ship
	// Passes in starting coordinates and ship object
	public boolean isOccupiedVert(int xcoord, int ycoord, Ship ship) {
		// Boolean helper definition
		boolean check = false;
		// Return false immediately if its a carrier. No other ships have been drawn
		// yet. See generateShips()
		if (ship.getShipIcon() == 'R') {
			return false;
		}
		// Check if occupied for the length of the ship vertically
		try {
			for (int i = 0; i < ship.getShipLength(); i++) {
				// Check if the value is not Z. If it isn't Z, it is occupied
				if (shipPositions[xcoord][ycoord + i] != 'Z') {
					// It is occupied, return true, break
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

	// Initialize the array of characters to be 'Z' in the shipPositions array
	public static void initializeShipPositionArray(int size) {
		shipPositions = new char[size][size];
		for (int row = 1; row < size; row++)
			for (int col = 1; col < size; col++)
				shipPositions[row][col] = 'Z';
	}

	// This function creates an alert box asking you to choose your difficulty.
	public static void getDifficulty() {
		// New alert object with three button objects
		Alert alert = new Alert(AlertType.CONFIRMATION, " ", beginner, standard, advanced);
		// Set the title, header and content message
		alert.setTitle("Welcome to Battleship!");
		alert.setHeaderText(
				"Please choose your difficulty \n\n Please note: Beginner sometimes fails to load due to the small size, simply restart.");
		alert.setContentText("Created by James Potratz for CSIS 222");
		alert.showAndWait();
		// Check which button was clicked
		if (alert.getResult() == beginner) {
			battleship.difficulty = 1;
		}
		if (alert.getResult() == standard) {
			battleship.difficulty = 2;
		}
		if (alert.getResult() == advanced) {
			battleship.difficulty = 3;
		}
	}

	// This function is to make the teachers grading easier. Hi Mr(s). Carol
	public static void cheats() {
		// New alert object with 2 buttons
		Alert alert = new Alert(AlertType.CONFIRMATION, "Would you like to cheat?", revealMap, ButtonType.NO);
		// Set title , header and content message
		alert.setTitle("Cheats");
		alert.setHeaderText(
				"Would you like to cheat? \n Please note, when clicking on a ship, it will already be revealed.");
		alert.setContentText("Created by James Potratz for CSIS 222");
		alert.showAndWait();
		// If you are cheating, set the cheat boolean to true.
		if (alert.getResult() == revealMap) {
			revealMapCheat = true;
		}
	}

	// Launch the applications
	public static void main(String[] args) {
		launch(args);
	}
}
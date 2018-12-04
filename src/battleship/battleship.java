// James Potratz CSIS 222 - MCC
// This class runs the battleship game. 

package battleship;

import java.util.Scanner;

public class battleship extends gameboard {

	// Standard scanner creation
	public static Scanner input = new Scanner(System.in);
	// Difficulty counter
	public static int difficulty;

	public static void main(String[] args) {
		System.out.println("Welcome to Battleship! This program was created by James Potratz");
		System.out.println("Enter 1 for Beginner (6x6)");
		System.out.println("Enter 2 for Standard (9x9)");
		System.out.println("Enter 3 for Advanced (12x12)");
		/*
		 * do { System.out.println("Please enter a difficulty number 1-3"); while
		 * (!input.hasNextInt()) {
		 * System.out.println("Please enter a difficulty number 1-3"); input.next(); }
		 * difficulty = input.nextInt(); } while (difficulty >= 4 || difficulty <= 0);
		 */
		difficulty = 1;
		System.out.println("Starting game...");
		actiongame.main(args);
	}
}

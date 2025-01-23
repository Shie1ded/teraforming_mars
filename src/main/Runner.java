package main;

import java.util.Scanner;

import frames.Setup;

@SuppressWarnings("unused")
public class Runner {

	public static Scanner reader = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Starting game \'Terraforming Mars\'");
		Setup runner = new Setup();
	}
}

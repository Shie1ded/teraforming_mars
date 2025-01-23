package player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import board.Resources;
import main.Game;

public class Player {
	public static int total_number;		// total number of players
	public Game game;				// access to all game vars, easy ability to access cards and board
	public int number; 					// player number, ie "player 1"
	public ArrayList<Card> hand;		// cards in the players hand
	public Company company;				// company that the player picked
	public ArrayList<Card> played;		// cards that have been played by the player
	public ArrayList<Ability> activated_abilities;	// list of all abilities taken on a turn (no duplicates)
	public ArrayList<Ability> permanent_abilities;	// list of all abilities that passively give abilities (such as discounts)
	public ArrayList<Ability> end_of_game_abilities;	// list of abilities that give some special point increase;
	public int terraform_rating_before = 20;
	// all the resources
	public int terraform_rating = 20;
	public int megacredit_generation = 1;
	public int megacredits = 0;
	public int steel_generation = 1;
	public int steel = 0;
	public int titanium_generation = 1;
	public int titanium = 0;
	public int plant_generation = 1;
	public int plants = 0;
	public int energy_generation = 1;
	public int energy = 0;
	public int heat_generation = 1;
	public int heat = 0;
	
	// constructor, set player number
	public Player(Game game) {
		this.game = game;
		hand = new ArrayList<Card>();
		played = new ArrayList<Card>();
		activated_abilities = new ArrayList<Ability>();
		permanent_abilities = new ArrayList<Ability>();
		end_of_game_abilities = new ArrayList<Ability>();
		total_number++;
		number = total_number;
	}

	// increase / decrease a resource
	public void increaseResource(Resources resource, int number) {
		switch (resource) {
		case MegaCredit:
			megacredits += number;
			break;
		case Steel:
			steel += number;
			break;
		case Titanium:
			titanium += number;
			break;
		case Plant:
			plants += number;
			break;
		case Energy:
			energy += number;
			break;
		case Heat:
			heat += number;
			break;
		case Card:
			for(int i = 0; i<number; i++) {
				if(game.canDrawCard(1)) {
					Card temp = game.getNextCard();
					hand.add(temp);
					System.out.println("\nAdded the following card to your hand:");
					temp.print();
				} else {
					System.out.println("\nDeck is empty, could not draw a card.");
					break;
				}
			}
			Game.sleep();
			break;
		default:
			System.err.println("Invalid resource in increaseResource: " + resource);
			System.exit(-1);
		}
	}
	
	// check to see if you can afford the resources with the card removing resources
	public boolean hasMoney(Resources resources[], int number, Resources resources_remove[]) {
		if(!hasMoney(resources, number) ) return false;
		boolean hasSteel = false, hasTitanium = false;
		for(Resources e: resources) {
			if(e.equals(Resources.Steel)) hasSteel = true;
			if(e.equals(Resources.Titanium)) hasTitanium = true;
		}
		int num_steel_remove = 0;
		if(hasSteel) 
			for(Resources e: resources_remove) 
				if(e.equals(Resources.Steel)) 
					num_steel_remove++;
		
		int num_titanium_remove = 0;
		if(hasTitanium) 
			for(Resources e: resources_remove) 
				if(e.equals(Resources.Titanium)) 
					num_titanium_remove++;
		
		if(num_steel_remove == 0 && num_titanium_remove == 0) return true;
		int total_number = 0;
		int num_increase = PermanentAbilities.valuableResources(this);
		for(Resources e: resources) {
			if(e.equals(Resources.Steel)) total_number += (2 + num_increase) * (getResource(Resources.Steel) - num_steel_remove);
			else if(e.equals(Resources.Titanium)) total_number += (3 + num_increase) * (getResource(Resources.Titanium) - num_titanium_remove);
			else total_number += getResource(e);
		}
		return total_number >= number;
	}
	
	// check to see if you can afford the resource
	public boolean hasMoney(Resources resources[], int number) {
		int total_number = 0;
		int num_increase = PermanentAbilities.valuableResources(this);
		if(PermanentAbilities.payWithOtherResource(this) != null) {
			Resources[] temp = new Resources[resources.length];
			for(int i = 0; i<resources.length; i++) temp[i] = resources[i];
			resources = new Resources[resources.length + 1];
			int i = 0;
			for(; i<temp.length; i++) resources[i] = temp[i];
			resources[i] = PermanentAbilities.payWithOtherResource(this);
		}
		for(Resources e: resources) {
			if(e.equals(Resources.Steel)) total_number += (2 + num_increase) * getResource(Resources.Steel);
			else if(e.equals(Resources.Titanium)) total_number += (3 + num_increase) * getResource(Resources.Titanium);
			else total_number += getResource(e);
		}
		return total_number >= number;
	}
	
	// pay for something with resource
	public void purchaseWithResource(Resources resources[], int number) {
		int num_increase = PermanentAbilities.valuableResources(this);
		if(PermanentAbilities.payWithOtherResource(this) != null) {
			Resources[] temp = new Resources[resources.length];
			for(int i = 0; i<resources.length; i++) temp[i] = resources[i];
			resources = new Resources[resources.length + 1];
			int i = 0;
			for(; i<temp.length; i++) resources[i] = temp[i];
			resources[i] = PermanentAbilities.payWithOtherResource(this);
		}
		if(resources.length > 1) {
			while(true) {
				int num_choices = 0;
				for(Resources e: resources) {
					if(getResource(e) > 0) num_choices++;
				}
				if(num_choices == 1) {
					for(int i = 0; i<resources.length; i++) {
						if(getResource(resources[i]) > 0) {
							Resources e = resources[i];
							if(e.equals(Resources.Steel)) {
								increaseResource(Resources.Steel, -1*(number+1+num_increase)/(2 + num_increase));
							} else if(e.equals(Resources.Titanium)) {
								increaseResource(Resources.Titanium, -1*(number+2 + num_increase)/(3 + num_increase));
							} else increaseResource(e, -1*number);
							return;
						}
					}
				}
				System.out.println("\nCost: " + number);
				for(int i = 0; i < resources.length; i++) {
					System.out.print("[" + (i+1) + "] " + resources[i].toString() + ":\t " + getResource(resources[i]));
					if(resources[i].equals(Resources.Steel)) System.out.print("x" + (2 + num_increase) + ": " + getResource(Resources.Steel)*(2 + num_increase));
					else if(resources[i].equals(Resources.Titanium)) System.out.print("x" + (3 + num_increase) + ": " + getResource(Resources.Titanium)*(3 + num_increase));
					System.out.println();
				}
				int choice = game.getUserInt("Select resource to use", "Not a valid resource.", 1, resources.length) - 1;
				Resources resource = resources[choice];
				if(getResource(resource) < 1) {
					System.err.println("You do not have any " + resource.toString() + " to spend.");
					continue;
				}
				if(resource.equals(Resources.Steel)) {
					while(number > 0 && getResource(Resources.Steel) > 0) {
						increaseResource(Resources.Steel, -1);
						number -= (2 + num_increase);
					}
				} else if(resource.equals(Resources.Titanium)) {
					Game.sleep();
					while(number > 0 && getResource(Resources.Titanium) > 0) {
						increaseResource(Resources.Titanium, -1);
						number -= (3 + num_increase);
					}
				} else {
					while(number > 0 && getResource(resource) > 0) {
						increaseResource(resource, -1);
						number -= 1;
					}
				}
				if(number < 1) return;
			}
		} else {
			switch(resources[0]) {
			case Steel:
				increaseResource(resources[0], -1*(number+1+num_increase)/(2 + num_increase));
				break;
			case Titanium:
				increaseResource(resources[0], -1*(number+2 + num_increase)/(3 + num_increase));
				break;
			default:
				increaseResource(resources[0], -1*number);
			}
		}
	}

	// get the number of a given resource
	public int getResource(Resources resource) {
		switch (resource) {
		case MegaCredit:
			return megacredits;
		case Steel:
			return steel;
		case Titanium:
			return titanium;
		case Plant:
			return plants;
		case Energy:
			return energy;
		case Heat:
			return heat;
		default:
			System.err.println("Invalid resource in getResource: " + resource);
			System.exit(-1);
			return -1;
		}
	}
	
	// get the generation of a resource
	public int getResourceGeneration(Resources resource) {
		switch (resource) {
		case MegaCredit:
			return megacredit_generation;
		case Steel:
			return steel_generation;
		case Titanium:
			return titanium_generation;
		case Plant:
			return plant_generation;
		case Energy:
			return energy_generation;
		case Heat:
			return heat_generation;
		default:
			System.err.println("Invalid resource in getResourceGeneration: " + resource);
			System.exit(-1);
			return -1;
		}
	}
	
	// increase / decrease the production of a resource
	public void increaseResourceGeneration(Resources resource, int number) {
		switch (resource) {
		case MegaCredit:
			megacredit_generation += number;
			break;
		case Steel:
			steel_generation += number;
			break;
		case Titanium:
			titanium_generation += number;
			break;
		case Plant:
			plant_generation += number;
			break;
		case Energy:
			energy_generation += number;
			break;
		case Heat:
			heat_generation += number;
			break;
		default:
			System.err.println("Invalid resource in increaseResourceGeneration: " + resource);
			System.exit(-1);
		}
		if(number > 0 && PermanentAbilities.Manutech(this)) increaseResource(resource, number);
	}

	// check to see if you have resource >= value
	public boolean checkResource(Resources resource, int min_number) {
		switch (resource) {
		case MegaCredit:
			return megacredits >= min_number;
		case Steel:
			return steel >= min_number;
		case Titanium:
			return titanium >= min_number;
		case Plant:
			return plants >= min_number;
		case Energy:
			return energy >= min_number;
		case Heat:
			return heat >= min_number;
		default:
			return false;
		}
	}

	// check to see if you have resource generation >= value
	public boolean checkResourceGeneration(Resources resource, int min_number) {
		switch (resource) {
		case MegaCredit:
			return megacredit_generation + 5 >= min_number;
		case Steel:
			return steel_generation >= min_number;
		case Titanium:
			return titanium_generation >= min_number;
		case Plant:
			return plant_generation >= min_number;
		case Energy:
			return energy_generation >= min_number;
		case Heat:
			return heat_generation >= min_number;
		default:
			return false;
		}
	}

	// on setup, lets players choose their company
	public void chooseCompany(Company[] choices, Scanner reader, boolean played_before) {
//		if(!played_before) {
//			Company[] temp = new Company[choices.length];
//			for(int i = 0; i<choices.length; i++) temp[i] = choices[i];
//			choices = new Company[temp.length + 1];
//			int k = 0;
//			for(; k<temp.length; k++) choices[k] = temp[k];
//			choices[k] = Company.createDefaultCompany();
//		}
//		if(choices.length == 1) {
//			System.out.print("Company: ");
//			choices[0].print();
//			company = choices[0];
//			company.owner = this;
//			company.loadInitialStats();
//			return;
//		}
//		for (int i = 0; i < choices.length; i++) {
//			System.out.print("[" + (i + 1) + "] ");
//			choices[i].print();
//		}
//		while (true) {
//			System.out.println("\nType \'d\' to describe an ability.");
//			System.out.print("\nChoose a company:\t");
//			try {
//				String in = reader.nextLine();
//				if(in.equals("d")) {
//					game.abilities.describe();
//					continue;
//				}
//				int n = Integer.valueOf(in);
//				if (n < 1 || n > choices.length) {
//					System.out.println("Please choose a value between 1 and " + (choices.length));
//					continue;
//				}
//				company = choices[n - 1];
//				company.owner = this;
//				company.loadInitialStats();
//				System.out.println("Selected company " + choices[n - 1].name);
//				break;
//			} catch (Exception e) {
//				System.err.println("Invalid. Please insert a valid number");
//			}
//		}
	}

	// on setup, lets player choose their preludes
	public void choosePreludes(ArrayList<Card> choices, Scanner reader, int num_to_add) {
		int picked = 0;
		while (true) {
			for (int i = 0; i < choices.size(); i++) {
				System.out.print("[" + (i + 1) + "] ");
				choices.get(i).print();
			}
			System.out.println("\nType \'d\' to describe an ability.");
			System.out.print("\nChoose " + num_to_add + " preludes:\t");
			try {
				String in = reader.nextLine();
				if(in.equals("d")) {
					game.abilities.describe();
					continue;
				}
				int n = Integer.valueOf(in);
				if (n < 1 || n > choices.size()) {
					System.out.println("Please choose a value between 1 and " + (choices.size()));
					continue;
				}
				hand.add(choices.remove(n-1));
				picked++;
				if(picked == num_to_add) {
					for(Card e: choices) game.prelude_discard.add(e);
					break;
				}
			} catch (Exception e) {
				System.err.println("Invalid. Please insert a valid number");
			}
		}
	}
	
	// research phase card choosing
	public void chooseCards(Card[] choices, Scanner reader) {
		System.out.println("\n-------- Shop --------");
		int price = PermanentAbilities.cardPurchasePrice(this);
		Resources[] money = {Resources.MegaCredit};
		if (!hasMoney(money,price)) {
			System.out.println("You cannot afford to purchase any cards.");
		} else {
			while (true) {
				if (choices.length == 0) {
					System.out.println("All cards purchased.");
					break;
				}
				else if(!hasMoney(money,price)) {
					System.out.println("Cannot purchase any more cards.");
					break;
				}
				for (int i = 0; i < choices.length; i++) {
					System.out.print("[" + (i + 1) + "] ");
					choices[i].print();
				}
				System.out.println("\nType \'d\' to describe the action.");
				System.out.print("\nDo you want to purchase a card (y/n)?\t");
				try {
					String in = reader.nextLine();
					in = in.trim().toLowerCase();
					if(in.equals("d")) {
						game.abilities.describe();
						continue;
					}
					if (in.equals("y") || in.equals("yes")) {
						boolean loop = true;
						if(choices.length == 1) {
							purchaseWithResource(money, price);
							hand.add(choices[0]);
							choices = new Card[0];
							continue;
						}
						while (loop) {
							System.out.print("Which card would you like to purchase?\t");
							try {
								in = reader.nextLine();
								System.out.println();
								int n = Integer.valueOf(in);
								if (n < 1 || n > choices.length) {
									System.out.println("Please choose a value between 1 and " + (choices.length));
									continue;
								}
								purchaseWithResource(money, price);
								hand.add(choices[n-1]);
								Card[] temp = new Card[choices.length - 1];
								int j = 0;
								for(int i = 0; i<choices.length; i++) {
									if(i != n - 1) {
										temp[j] = choices[i];
										j++;
									}
								}
								choices = temp;
								loop = false;
							} catch (Exception e) {
								System.err.println("Invalid. Please insert a valid number");
							}
						}
					} else if (in.equals("n") || in.equals("no")) {
						break;
					} else {
						System.err.print("\nNot a valid input.");
					}
				} catch (Exception e) {
					System.err.println("Error, invalid entry.");
				}
			}
		}
		for (Card i : choices)
			game.discard.add(i);
		Game.sleep();
	}
	
	// !!!
	// single player state
	public void setSinglePlayer() {
		terraform_rating = 14;
		System.err.println("Single player not fully implemented");
	}

	// generate all resources between generations
	public void generate_resources() {
		megacredits += terraform_rating + megacredit_generation;
		steel += steel_generation;
		titanium += titanium_generation;
		plants += plant_generation;
		heat += heat_generation + energy;
		energy = energy_generation;
	}

	// print all the information of a player
	public void print() {
		System.out.println("Player " + number + " of " + total_number);
		System.out.println("\tRating: " + terraform_rating);
		System.out.print("\tCompany: ");
		if (company != null)
			System.out.println(company.name);
		else
			System.out.println("None");
		System.out.print("\tHand: ");
		if (hand != null && hand.size() > 0) {
			System.out.println();
			for (Card card : hand)
				System.out.println("\t\tCard: " + card.name);
		} else
			System.out.println("Empty");
		System.out.print("\tPlayed ");
		if (played != null) {
			System.out.println(played.size() + " cards.");
		} else
			System.out.println("0 cards");
		System.out.println("\tResources: \tType\t\t\tProduction\tNumber");
		System.out.println("\t\t\tMegacredit($): \t\t" + megacredit_generation + "\t\t" + megacredits);
		System.out.println("\t\t\tSteel: \t\t\t" + steel_generation + "\t\t" + steel);
		System.out.println("\t\t\tTitanium: \t\t" + titanium_generation + "\t\t" + titanium);
		System.out.println("\t\t\tPlants: \t\t" + plant_generation + "\t\t" + plants);
		System.out.println("\t\t\tEnergy: \t\t" + energy_generation + "\t\t" + energy);
		System.out.println("\t\t\tHeat: \t\t\t" + heat_generation + "\t\t" + heat);
		if(activated_abilities.size() > 0) {
			System.out.print("\tActions Performed:\t");
			for(Ability i: activated_abilities) System.out.print(i.toString() + " ");
			System.out.println();
		}
		if(permanent_abilities.size() > 0) {
			System.out.print("\tPermanent Abilities:\t");
			for(Ability i: permanent_abilities) System.out.print(i.toString() + " ");
			System.out.println();
		}
		if(end_of_game_abilities.size() > 0) {
			System.out.print("\tEnd of game Abilities:\t");
			for(Ability i: end_of_game_abilities) System.out.print(i.toString() + " ");
			System.out.println();
		}
	}
	
	// print all the cards in the hand
	public void printHand() {
		if(hand.size() < 1) {
			System.out.println("Hand is empty");
			return;
		}
		System.out.println("Hand:");
		int j = 1;
		for(Card i: hand) {
			System.out.print("[" + j + "]");
			i.print();
			j++;
		}
	}
	
	// print all the played cards
	public void printPlayedCards() {
		if(played.size() < 1) {
			System.out.println("No cards have been played.");
			return;
		}
		System.out.println("Played Cards:");
		for(Card i: played) i.print();
	}
	
	// get all points from the played cards
	public int calculateVPs() {
		int points = 0;
		for(Card i: played) {
			points += i.points;
		}
		return points;
	}
	
	// show which player is going
	public void printPlayerBar() {
		System.out.println("\n-------- PLAYER " + number + " --------");
	}
	
	// see if an ability has already been activated
	public boolean findAbility(Ability ability) {
		for(Ability i: activated_abilities) if(i.equals(ability)) return true;
		return false;
	}
	
	// add an ability to the activated ability list
	public void addAbility(Ability ability) {
		activated_abilities.add(ability);
	}
	
	// reset all activated abilities
	public void clearAbilities() {
		activated_abilities.clear();
	}
	
	// get all abilities from all cars
	public Ability[] getCardAbilities() {
		ArrayList<Ability> temp = new ArrayList<Ability>();
		for(Card i: played) if(i.activate_abilities != null) for(Ability j: i.activate_abilities) temp.add(j);
		Ability[] all_abilities = new Ability[temp.size()];
		all_abilities = temp.toArray(all_abilities);
		return all_abilities;
	}

	// get all the tags of card and company
	public Tag[] getAllTags() {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		if(company.tag != null) for(Tag i:company.tag) tags.add(i);
		for(Card i:played) if(i.tag != null) for(Tag j:i.tag) tags.add(j);
		Tag[] all_tags = new Tag[tags.size()];
		all_tags = tags.toArray(all_tags);
		return all_tags;
	}
}

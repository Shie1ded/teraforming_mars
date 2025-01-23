package board;

import java.util.ArrayList;

import main.Game;
import player.Player;
import player.ReusableAbilityFunctions;
import player.Tag;

public class Awards {
	private String[] awards = {"Landlord", "Banker", "Scientist", "Thermalist", "Miner"};
	private String[] milestones = {"Terraformer", "Mayor", "Gardener", "Builder", "Planner"};
	private Game game;
	public String[] funded = {};
	public String[] taken = {};
	public Player[] taken_players = {};
	private ArrayList<Player> winners = new ArrayList<Player>();
	private ArrayList<Player> contenders = new ArrayList<Player>();
	
	public Awards(Game game) {this.game = game;}
	
	public void describe() {
		System.out.print("\nWhich award / milestone would you like to describe (please type, case-sensitive)): ");
		String award = game.reader.nextLine();
		switch(award) {
		case "Landlord":
			System.out.println("The player with the most number of claimed tiles (cities, greeneries, custom tiles)");
			break;
		case "Banker":
			System.out.println("The player with the most MegaCredit generation.");
			break;
		case "Scientist":
			System.out.println("The player with the most Science tags on all of their played cards (including companies).");
			break;
		case "Thermalist":
			System.out.println("The player with the most Heat resources at the end of the game.");
			break;
		case "Miner":
			System.out.println("The player with the most Titanium and Steel resources at the end of the game.");
			break;
		case "Terraformer":
			System.out.println("You must have a terraform rating of at least 35 to claim this milestone.");
			break;
		case "Mayor":
			System.out.println("You must have at least 3 Cities in your control to claim this milestone.");
			break;
		case "Gardener":
			System.out.println("You must have at least 3 Greenerries in your control to claim this milestone.");
			break;
		case "Builder":
			System.out.println("You must have at least 8 claimed tiles in your control to claim this milestone (cities, greeneries, custom tiles).");
			break;
		case "Planner":
			System.out.println("You must have played at least 16 cards (not including a company card) to claim this milestone.");
			break;
		default:
			System.err.println("Unrecognized milestone / award: " + award);
		}
		System.out.println();
	}
	
	public boolean fundAward(Player player) {
		if(funded.length >= 3) {
			System.out.println("3 awards have already been funded. You cannot fund another award.");
			return false;
		}
		Resources[] money = {Resources.MegaCredit};
		if(!player.hasMoney(money, awardCost())) {
			System.err.println("You do not have enough MegaCredit to fund an award. The next award costs " + awardCost() + " MegaCredit");
			return false;
		}
		int choice = -1;
		while(true) {
			System.out.println("Which award would you like to fund?");
			for(int i = 0; i<awards.length; i++) System.out.println("\t[" + (i+1) + "] " + awards[i]);
			choice = getUserInt("Choice", "Invalid.", 1, awards.length) - 1;
			if(choice == -1) return false;
			for(String e: funded) 
				if(awards[choice].equals(e)) {
					System.out.println("This award has already been funded. Please select another award.");
					continue;
				}
			break;
		}
		player.purchaseWithResource(money, awardCost());
		funded = addElement(funded, awards[choice]);
		
		return true;
	}
	
	public boolean fundFreeAward() {
		if(funded.length >= 3) {
			System.out.println("3 awards have already been funded. You cannot fund another award.");
			return false;
		}
		int choice = -1;
		while(true) {
			System.out.println("Which award would you like to fund?");
			for(int i = 0; i<awards.length; i++) System.out.println("\t[" + (i+1) + "] " + awards[i]);
			choice = getUserInt("Choice", "Invalid.", 1, awards.length) - 1;
			if(choice == -1) {
				System.err.println("You must fund an award.");
				continue;
			};
			for(String e: funded) 
				if(awards[choice].equals(e)) {
					System.out.println("This award has already been funded. Please select another award.");
					continue;
				}
			break;
		}
		funded = addElement(funded, awards[choice]);
		
		return true;
	}
	
	public boolean getMilestone(Player player) {
		if(taken.length >= 3) {
			System.out.println("3 milestones have already been funded. You cannot fund another milestone.");
			return false;
		}
		Resources[] money = {Resources.MegaCredit};
		if(!player.hasMoney(money, 8)) {
			System.err.println("You do not have enough MegaCredit to fund a milestone.");
			return false;
		}
		int choice = -1;
		while(true) {
			System.out.println("Which milestone would you like to fund?");
			for(int i = 0; i<milestones.length; i++) System.out.println("\t[" + (i+1) + "] " + milestones[i]);
			choice = getUserInt("Choice", "Invalid.", 1, milestones.length) - 1;
			if(choice == -1) return false;
			for(String e: taken) {
				if(milestones[choice].equals(e)) {
					System.out.println("This milestone has already been funded. Please select another milestone.");
					continue;
				}
				else if(!qualifyForMilestone(player, milestones[choice])) {
					System.err.println("You do not qualify for this milestone.");
					continue;
				}
			}
			break;
		}
		player.purchaseWithResource(money, 8);
		taken = addElement(taken, milestones[choice]);
		taken_players = addElement(taken_players, player);
		
		return true;
	}

	public int milestonePoints(Player player) {
		int points = 0;
		for(Player p: taken_players) if(p.equals(player)) points += 5;
		return points;
	}
	
	public int awardPoints(Player player) {
		int points = 0;
		for(Player p: winners) if(p.equals(player)) points += 5;
		for(Player p: contenders) if(p.equals(player)) points += 2;
		return points;
	}
	
	public void determineWinners() {
		for(String e: funded) determineWinner(e);
	}
	
	private void determineWinner (String award) {
		switch(award) {
		case "Landlord":
			landlord();
			break;
		case "Banker":
			banker();
			break;
		case "Scientist":
			scientist();
			break;
		case "Thermalist":
			thermalist();
			break;
		case "Miner":
			miner();
			break;
		}
	}
	
	private int getUserInt(String question, String failure_message, int min, int max) {
		while (true) {
			System.out.println("\nType \'d\' to describe an award / milestone. Type q to quit.");
			System.out.print("\n" + question + ":\t");
			try {
				String in = game.reader.nextLine();
				if(in.equals("d")) {
					describe();
					continue;
				}
				if(in.equals("q")) {
					return -1;
				}
				int n = Integer.valueOf(in);
				if (n < min || n > max) {
					System.out.println(failure_message);
					continue;
				}
				return n;
			} catch (Exception e) {
				System.err.println("Please insert a valid number");
			}
		}
	}
	
	private int awardCost() {
		switch(funded.length) {
		case 0:
			return 8;
		case 1:
			return 14;
		case 2:
			return 20;
		default:
			System.err.println("Error, getting the cost of the fourth award when only three awards should be funded.");
			System.exit(-1);
			return -1;
		}
	}
	
	private String[] addElement(String[] array, String value) {
		String[] temp = new String[array.length];
		for(int i = 0; i<array.length; i++) temp[i] = array[i];
		array = new String[temp.length + 1];
		int i = 0;
		for(; i<temp.length; i++) array[i] = temp[i];
		array[i] = value;
		return array;
	}
	
	private Player[] addElement(Player[] array, Player value) {
		Player[] temp = new Player[array.length];
		for(int i = 0; i<array.length; i++) temp[i] = array[i];
		array = new Player[temp.length];
		int i = 0;
		for(; i<temp.length; i++) array[i] = temp[i];
		array[i] = value;
		return array;
	}

	private boolean qualifyForMilestone(Player player, String milestone) {
		switch (milestone) {
		case "Terraformer":
			return player.terraform_rating >= 35;
		case "Mayor":
			return numTile(player, TileType.City) >= 3;
		case "Gardener":
			return numTile(player, TileType.Greenery) >= 3;
		case "Builder":
			return numProperties(player) >= 8;
		case "Planner":
			return player.played.size() >= 16;
		default:
			System.err.println("Unknown milestone: " + milestone);
			return false;
		}
	}
	
	private int numTile(Player player, TileType type) {
		int number = 0;
		for(int i = 0; i<game.board.board.length; i++)
			for(int j = 0; j<game.board.board[i].length; j++)
				if(game.board.board[i][j].type != null && game.board.board[i][j].type.equals(type) && game.board.board[i][j].owner.equals(player))
					number++;
		return number;
	}
	
	private int numProperties(Player player) {
		int number = 0;
		for(int i = 0; i<game.board.board.length; i++)
			for(int j = 0; j<game.board.board[i].length; j++)
				if(game.board.board[i][j].owner.equals(player))
					number++;
		return number;
	}

	private void landlord() {
		int f = 0;
		int s = 0;
		Player first = null;
		Player second = null;
		
		for(Player p: game.players) {
			if(numProperties(p) > f) {
				if(second != null && f > s) {
					second = first;
					s = f;
				} else {
					second = null;
				}
				first = p;
				f = numProperties(p);
			} else if(numProperties(p) == f) {
				if(s != f) {
					s = f;
					second = p;
				} else {
					first = null;
					second = null;
				}
			} else if(numProperties(p) > s) {
				s = numProperties(p);
				second = p;
			} else if(numProperties(p) == s) {
				second = null;
			}
		}
		if(f == s) {
			if(first != null) contenders.add(first);
		} else {
			if(first != null) winners.add(first);
		}
		if(second != null) contenders.add(second);
	}
	
	private void banker() {
		int f = 0;
		int s = 0;
		Player first = null;
		Player second = null;
		
		for(Player p: game.players) {
			if(p.getResourceGeneration(Resources.MegaCredit) > f) {
				if(second != null && f > s) {
					second = first;
					s = f;
				} else {
					second = null;
				}
				first = p;
				f = p.getResourceGeneration(Resources.MegaCredit);
			} else if(p.getResourceGeneration(Resources.MegaCredit) == f) {
				if(s != f) {
					s = f;
					second = p;
				} else {
					first = null;
					second = null;
				}
			} else if(p.getResourceGeneration(Resources.MegaCredit) > s) {
				s = p.getResourceGeneration(Resources.MegaCredit);
				second = p;
			} else if(p.getResourceGeneration(Resources.MegaCredit) == s) {
				second = null;
			}
		}
		if(f == s) {
			if(first != null) contenders.add(first);
		} else {
			if(first != null) winners.add(first);
		}
		if(second != null) contenders.add(second);
	}

	private void scientist() {
		int f = 0;
		int s = 0;
		Player first = null;
		Player second = null;
		
		for(Player p: game.players) {
			if(ReusableAbilityFunctions.getNumTags(p, Tag.Science) > f) {
				if(second != null && f > s) {
					second = first;
					s = f;
				} else {
					second = null;
				}
				first = p;
				f = ReusableAbilityFunctions.getNumTags(p, Tag.Science);
			} else if(ReusableAbilityFunctions.getNumTags(p, Tag.Science) == f) {
				if(s != f) {
					s = f;
					second = p;
				} else {
					first = null;
					second = null;
				}
			} else if(ReusableAbilityFunctions.getNumTags(p, Tag.Science) > s) {
				s = ReusableAbilityFunctions.getNumTags(p, Tag.Science);
				second = p;
			} else if(ReusableAbilityFunctions.getNumTags(p, Tag.Science) == s) {
				second = null;
			}
		}
		if(f == s) {
			if(first != null) contenders.add(first);
		} else {
			if(first != null) winners.add(first);
		}
		if(second != null) contenders.add(second);
	}

	private void thermalist() {
		int f = 0;
		int s = 0;
		Player first = null;
		Player second = null;
		
		for(Player p: game.players) {
			if(p.getResource(Resources.Heat) > f) {
				if(second != null && f > s) {
					second = first;
					s = f;
				} else {
					second = null;
				}
				first = p;
				f = p.getResource(Resources.Heat);
			} else if(p.getResource(Resources.Heat) == f) {
				if(s != f) {
					s = f;
					second = p;
				} else {
					first = null;
					second = null;
				}
			} else if(p.getResource(Resources.Heat) > s) {
				s = p.getResource(Resources.Heat);
				second = p;
			} else if(p.getResource(Resources.Heat) == s) {
				second = null;
			}
		}
		if(f == s) {
			if(first != null) contenders.add(first);
		} else {
			if(first != null) winners.add(first);
		}
		if(second != null) contenders.add(second);
	}

	private void miner() {
		int f = 0;
		int s = 0;
		Player first = null;
		Player second = null;
		
		for(Player p: game.players) {
			if(p.getResource(Resources.Steel) + p.getResource(Resources.Titanium) > f) {
				if(second != null && f > s) {
					second = first;
					s = f;
				} else {
					second = null;
				}
				first = p;
				f = p.getResource(Resources.Steel) + p.getResource(Resources.Titanium);
			} else if(p.getResource(Resources.Steel) + p.getResource(Resources.Titanium) == f) {
				if(s != f) {
					s = f;
					second = p;
				} else {
					first = null;
					second = null;
				}
			} else if(p.getResource(Resources.Steel) + p.getResource(Resources.Titanium) > s) {
				s = p.getResource(Resources.Steel) + p.getResource(Resources.Titanium);
				second = p;
			} else if(p.getResource(Resources.Steel) + p.getResource(Resources.Titanium) == s) {
				second = null;
			}
		}
		if(f == s) {
			if(first != null) contenders.add(first);
		} else {
			if(first != null) winners.add(first);
		}
		if(second != null) contenders.add(second);
	}

	public void print() {
		if(funded.length > 1) {
			System.out.print("Funded awards: ");
			for(String e: funded) System.out.print(e + ",");
			System.out.println();
		}
		if(taken.length > 1) {
			System.out.print("Taken milestones: ");
			for(String e: taken) System.out.print(e + ",");
			System.out.println();
		}
	}
}

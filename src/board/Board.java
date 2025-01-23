package board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import player.PermanentAbilities;
import player.Player;

public class Board {
	public BoardTile[][] board = new BoardTile[11][];
	Scanner reader;
	
	public int water = 0;
	public int temperature = -30;
	public int oxygen = 0;
	public int current_generation = 1;
	public int maximum_generation = 100;
	
	// initialize the correct size of the board
	public Board(Scanner reader) {
		this.reader = reader;
		
		// create the board size
		board[0] = new BoardTile[1];
		board[1] = new BoardTile[1];
		board[2] = new BoardTile[5];
		board[3] = new BoardTile[6];
		board[4] = new BoardTile[7];
		board[5] = new BoardTile[8];
		board[6] = new BoardTile[9];
		board[7] = new BoardTile[8];
		board[8] = new BoardTile[7];
		board[9] = new BoardTile[6];
		board[10] = new BoardTile[5];
		for(int i = 0; i<board.length; i++) for(int j = 0; j<board[i].length; j++) board[i][j] = new BoardTile();
		
		// set the board elements
		board[0][0].designateCity("Phobos Space Haven");
		
		board[1][0].designateCity("Ganymede Colony");
		
		board[2][0].setbonusResources(Resources.Steel, Resources.Steel);
		board[2][1].setbonusResources(Resources.Steel, Resources.Steel);
		board[2][1].designated_water = true;
		board[2][3].setbonusResources(Resources.Card);
		board[2][3].designated_water = true;
		board[2][4].designated_water = true;
		
		board[3][1].name = "Tharsis Tholus";
		board[3][1].setbonusResources(Resources.Steel);
		board[3][5].designated_water = true;
		board[3][5].setbonusResources(Resources.Card, Resources.Card);
		
		board[4][0].name = "Ascraeus Mons";
		board[4][0].setbonusResources(Resources.Card);
		board[4][6].setbonusResources(Resources.Steel);
		
		for(int i = 0; i<board[5].length; i++) board[5][i].setbonusResources(Resources.Plant);
		board[5][0].name = "Panonis Mons";
		board[5][0].setbonusResources(Resources.Plant, Resources.Titanium);
		board[5][4].setbonusResources(Resources.Plant, Resources.Plant);
		board[5][7].designated_water = true;
		board[5][7].setbonusResources(Resources.Plant, Resources.Plant);
		
		for(int i = 0; i<board[6].length; i++) board[6][i].setbonusResources(Resources.Plant, Resources.Plant);
		board[6][0].name = "Arsia Mons";
		board[6][2].designateCity("Noctis City");
		board[6][3].designated_water = true;
		board[6][4].designated_water = true;
		board[6][5].designated_water = true;
		
		for(int i = 0; i<board[7].length; i++) board[7][i].setbonusResources(Resources.Plant);
		board[7][1].setbonusResources(Resources.Plant, Resources.Plant);
		board[7][5].designated_water = true;
		board[7][6].designated_water = true;
		board[7][7].designated_water = true;
		
		board[8][5].setbonusResources(Resources.Plant);
		
		board[9][0].setbonusResources(Resources.Steel, Resources.Steel);
		board[9][2].setbonusResources(Resources.Card);
		board[9][3].setbonusResources(Resources.Card);
		board[9][5].setbonusResources(Resources.Titanium);
		
		board[10][0].setbonusResources(Resources.Steel);
		board[10][1].setbonusResources(Resources.Steel, Resources.Steel);
		board[10][4].designated_water = true;
		board[10][4].setbonusResources(Resources.Titanium, Resources.Titanium);
	}
	
	// set single player game state
	public void setSinglePlayer() { maximum_generation = 14; }
	
	// check to see if the board is finished
	public boolean finished() {
		if(water == 9 && temperature == 8 && oxygen == 14) return true;
		if(current_generation == maximum_generation - 1) {
			System.err.println("This is the last generation!");
			return false;
		}
		if(current_generation >= maximum_generation) return true;
		return false;
	}
	
	public void print() {
		for(int i = 0; i<board.length; i++) {
			for(int j = 0; j<board[i].length; j++) {
				if(j != 0) System.out.print(",");
				board[i][j].print();
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printAll(Player[] players) {
		System.out.println("\n-------- Board --------");
		System.out.println("Generation: " + current_generation);
		System.out.println("Oxygen: " + oxygen);
		System.out.println("Temperature: " + temperature);
		System.out.println("Water Remaining: " + (9-water));
		System.out.println();
		System.out.println("Terraform Rating:");
		for(Player i: players) System.out.println("\tPlayer " + i.number + ", TR: " + i.terraform_rating);
		System.out.println();
		players[0].game.awards.print();
		System.out.println();
		print();
	}
	
	public void increaseTemperature(Player player) {
		if(temperature >= 8) {
			System.err.println("Temperature increase ineffective. Already at max temperature.");
		} else {
			temperature += 2;
			player.terraform_rating ++;
			if(temperature == -24 || temperature == -20) {
				System.out.println("Recieved a bonus heat increment for temperature increase.");
				player.increaseResourceGeneration(Resources.Heat, 1);
			} else if(temperature == 0) increaseWater(player);
		}
	}
	
	public void increaseOxygen(Player player) {
		if(oxygen >= 14) {
			System.err.println("Oxygen increase ineffective. Already at max oxygen.");
		} else {
			oxygen += 1;
			player.terraform_rating ++;
			if(oxygen == 8) {
				System.out.println("Recieved a bonus temperature increase.");
				increaseTemperature(player);
			}
		}
	}
	
	public void increaseWater(Player player) {
		Queue<BoardTile> options = new LinkedList<BoardTile>();
		if (water == 9) {
			System.err.println("Water increase ineffective. Already at max Water.");
		} else {
			int counter = 1;
			int counter2 =1;
			for (int i=0;i<board.length;i++) {
				for(int j=0;j<board[i].length;j++) {
					if(board[i][j].designated_water && !isClaimed(i,j)) {
						System.out.print(counter + ",");
						options.add(board[i][j]);
						counter++;
					} else {
						board[i][j].print();
						System.out.print(",");
					}
				}
				System.out.println("");
			}
			int counter3 = 1;
			while(options.size() > 0) {
				System.out.print("["+counter3+"] Bonus Resources: \t");
				BoardTile temp = options.remove();
				if(temp.bonusResources.length > 0) for(Resources e: temp.bonusResources) System.out.print(e.toString() + ", ");
				else System.out.print("None");
				System.out.println();
				counter3++;
			}
			int in = player.game.getUserInt("Which water tile would you like to choose", "That not a water tile", 1, counter-1);
			
			for (int i=0;i<board.length;i++) {
				for(int j=0;j<board[i].length;j++) {
					if(board[i][j].designated_water && !isClaimed(i,j)) {
						if(counter2 == in) {
							claimWater(player, i, j);
						}
						counter2++;
					} 
				}
			}
			print();
		}
	}
	
	public void waterAdjacent(int i, int j) {
		Resources[] money = {Resources.MegaCredit, Resources.MegaCredit};
		
		// set resources before and after same line
		board[i][j-1].addBonusResource(money);
		if(j != board[i].length-1) board[i][j+1].addBonusResource(money);
		
		// set resources below
		if(i < 6) {
			board[i+1][j].addBonusResource(money);
			board[i+1][j+1].addBonusResource(money);
		} else if(i < 8) {
			board[i+1][j-1].addBonusResource(money);
			if(j != board[i].length-1) board[i+1][j].addBonusResource(money);
		} 
		
		// set resources above
		if(i > 6) {
			board[i-1][j].addBonusResource(money);
			board[i-1][j+1].addBonusResource(money);
		}
		else if(i > 2) {
			board[i-1][j-1].addBonusResource(money);
			if(j != board[i].length-1) board[i-1][j].addBonusResource(money);
		}
	}
	
	public void placeGreenery(Player player) {
		if(!freeLandSpace()) {
			System.out.println("There is no more room on the board for a greenery.");
			return;
		}
		
		boolean hasTile = false;
		for(int i = 0; i<board.length; i++) 
			for(int j = 0; j<board[i].length; j++) 
				if(isClaimed(i,j) && board[i][j].owner != null && board[i][j].owner.equals(player) && adjacentFreeSpace(i,j) ){
			hasTile = true;
		}
		if(hasTile) {
			// place next to a place you own
			Queue<BoardTile> options = new LinkedList<BoardTile>();
			int counter = 1;
			int counter2 = 1;
			int counter3 = 1;
			for (int i=0;i<board.length;i++) {
				for(int j=0;j<board[i].length;j++) {
					if(isFreeLand(i,j) && adjacentToMyTile(player, i, j)) {
						System.out.print(counter + ",");
						options.add(board[i][j]);
						counter++;
					} else {
						board[i][j].print();
						System.out.print(",");
					}
				}
				System.out.println("");
			}
			while(options.size() > 0) {
				System.out.print("["+counter3+"] Bonus Resources: \t");
				BoardTile temp = options.remove();
				if(temp.bonusResources.length > 0) for(Resources e: temp.bonusResources) System.out.print(e.toString() + ", ");
				else System.out.print("None");
				System.out.println();
				counter3++;
			}
			int in = player.game.getUserInt("Which tile would you like to place your Greenery on?", "You cant place a Greenery here, choose a different tile ", 1, counter-1);
			
			for(int i=2;i<board.length;i++) {
				for(int j=0;j<board[i].length;j++) {
					if(isFreeLand(i,j) && adjacentToMyTile(player, i, j)) {
						if(counter2==in) {
							claimGreenery(player,i,j);
							BoardTile tile = board[i][j];
							tile.getBonusResources(player);
						}
						counter2++;
					}
				}
			}
		} else {
			// place wherever you want
			Queue<BoardTile> options = new LinkedList<BoardTile>();
			int counter = 1;
			int counter2 = 1;
			int counter3 = 1;
			for (int i=0;i<board.length;i++) {
				for(int j=0;j<board[i].length;j++) {
					if(isFreeLand(i,j)) {
						System.out.print(counter + ",");
						options.add(board[i][j]);
						counter++;
					} else {
						board[i][j].print();
						System.out.print(",");
					}
				}
				System.out.println("");
			}
			while(options.size() > 0) {
				System.out.print("["+counter3+"] Bonus Resources: \t");
				BoardTile temp = options.remove();
				if(temp.bonusResources.length > 0) for(Resources e: temp.bonusResources) System.out.print(e.toString() + ", ");
				else System.out.print("None");
				System.out.println();
				counter3++;
			}
			int in = player.game.getUserInt("Which tile would you like to place your Greenery on?", "You cant place a Greenery here, choose a different tile ", 1, counter-1);
			
			for(int i=2;i<board.length;i++) {
				for(int j=0;j<board[i].length;j++) {
					if(isFreeLand(i,j)) {
						if(counter2==in) {
							claimGreenery(player,i,j);
							BoardTile tile = board[i][j];
							tile.getBonusResources(player);
						}
						counter2++;
					}
				}
			}
		}
	}
	
	public boolean adjacentFreeSpace(int i, int j) {
		if(i < 2 || i > 10) return false;
		if(j != 0) if(isFreeLand(i,j-1)) return true;
		if(j != board[i].length-1) if(isFreeLand(i,j+1)) return true;
		
		if(i < 7) {
			if(i != 2) {
				if(isFreeLand(i-1,j-1)) return true;								// 1
				if(j != board[i].length) if(isFreeLand(i-1,j)) return true;			// 2
			}
			if(i < 6) {
				if(isFreeLand(i+1,j)) return true;									// 5
				if(isFreeLand(i+1,j+1)) return true;								// 6
			}
		}
		if( i > 5) {
			if(i != 10) {
				if(isFreeLand(i+1,j-1)) return true;								// 4
				if(j != board[i].length) if(isFreeLand(i+1,j)) return true;			// 5
			}
			if(i > 6) {
				if(isFreeLand(i-1,j)) return true;									// 2
				if(isFreeLand(i-1,j+1)) return true;								// 3
			}
		}
		return false;
	}
	
	public boolean adjacentTile(int i, int j) {
		if(i < 2 || i > 10) return false;
		if(j != 0) if(!isFreeLand(i,j-1)) return false;
		if(j != board[i].length-1) if(!isFreeLand(i,j+1)) return false;
		
		if(i < 7) {
			if(i != 2) {
				if(!isFreeLand(i-1,j-1)) return false;									// 1
				if(j != board[i].length) if(!isFreeLand(i-1,j)) return false;			// 2
			}
			if(i < 6) {
				if(!isFreeLand(i+1,j)) return false;									// 5
				if(!isFreeLand(i+1,j+1)) return false;									// 6
			}
		}
		if( i > 5) {
			if(i != 10) {
				if(!isFreeLand(i+1,j-1)) return false;									// 4
				if(j != board[i].length) if(!isFreeLand(i+1,j)) return false;			// 5
			}
			if(i > 6) {
				if(!isFreeLand(i-1,j)) return false;									// 2
				if(!isFreeLand(i-1,j+1)) return false;								// 3
			}
		}
		return true;
	}
	
	public boolean adjacentToMyTile(Player player, int i, int j) {
		if(i < 2 || i > 10) return false;
		if(j != 0) if(isOwner(player, i,j-1)) return true;
		if(j != board[i].length-1) if(isOwner(player, i,j+1)) return true;
		
		if(i < 7) {
			if(i != 2) {
				if(j != 0) if(isOwner(player, i-1,j-1)) return true;								// 1
				if(j != board[i].length-1) if(isOwner(player, i-1,j)) return true;		// 2
			}
			if(i < 6) {
				if(isOwner(player, i+1,j)) return true;									// 5
				if(isOwner(player, i+1,j+1)) return true;								// 6
			}
		}
		if( i > 5) {
			if(i != 10) {
				if(j != 0) if(isOwner(player, i+1,j-1)) return true;								// 4
				if(j != board[i].length-1) if(isOwner(player, i+1,j)) return true;		// 5
			}
			if(i > 6) {
				if(isOwner(player, i-1,j)) return true;									// 2
				if(isOwner(player, i-1,j+1)) return true;								// 3
			}
		}
		return false;
	}

	public boolean adjacentToGreenery(int i, int j) {
		if(i < 2 || i > 10) return false;
		if(j != 0) if(isGreenery(i,j-1)) return true;
		if(j != board[i].length-1) if(isGreenery(i,j+1)) return true;
		
		if(i < 7) {
			if(i != 2) {
				if(j != 0) if(isGreenery(i-1,j-1)) return true;								// 1
				if(j != board[i].length-1) if(isGreenery(i-1,j)) return true;		// 2
			}
			if(i < 6) {
				if(isGreenery(i+1,j)) return true;									// 5
				if(isGreenery(i+1,j+1)) return true;								// 6
			}
		}
		if( i > 5) {
			if(i != 10) {
				if(j != 0) if(isGreenery(i+1,j-1)) return true;								// 4
				if(j != board[i].length-1) if(isGreenery(i+1,j)) return true;		// 5
			}
			if(i > 6) {
				if(isGreenery(i-1,j)) return true;									// 2
				if(isGreenery(i-1,j+1)) return true;								// 3
			}
		}
		return false;
	}
	
	public int numAdjacentCities(int i, int j) {
		if(i < 2 || i > 10) return 0;
		int numCities = 0;
		if(j != 0) if(isCity(i,j-1)) numCities++;
		if(j != board[i].length-1) if(isCity(i,j+1)) numCities++;
		
		if(i < 7) {
			if(i != 2) {
				if(j != 0) if(isCity(i-1,j-1)) numCities++;						// 1
				if(j != board[i].length-1) if(isCity(i-1,j)) numCities++;		// 2
			}
			if(i < 6) {
				if(isCity(i+1,j)) numCities++;									// 5
				if(isCity(i+1,j+1)) numCities++;								// 6
			}
		}
		if( i > 5) {
			if(i != 10) {
				if(j != 0) if(isCity(i+1,j-1)) numCities++;						// 4
				if(j != board[i].length-1) if(isCity(i+1,j)) numCities++;		// 5
			}
			if(i > 6) {
				if(isCity(i-1,j)) numCities++;									// 2
				if(isCity(i-1,j+1)) numCities++;								// 3
			}
		}
		return numCities;
	}
	
	public boolean isOwner(Player player, int i, int j) {
		if(board[i][j].owner == null) return false;
		return board[i][j].owner.equals(player);
	}
	
	public boolean isFreeLand(int i, int j) {
		 if(isClaimed(i,j)) return false;
		 if(board[i][j].designated_water || board[i][j].designated_city) return false;
		 return true;
		
	}
	
	public boolean isGreenery(int i, int j) {
		if(board[i][j].type != null && board[i][j].type.equals(TileType.Greenery)) return true;
		return false;
	}
	
	public boolean isCity(int i, int j) {
		if(board[i][j].type != null && board[i][j].type.equals(TileType.City)) return true;
		return false;
	}
	
	public void placeCity(Player player) {
		Queue<BoardTile> options = new LinkedList<BoardTile>();
		/* 1) is there a free space to place it
		 * 2a)check no tiles next to it are claimed
		 * 2b)check no tiles next to it are cities
		 * 3) add player claiming
		 * 4) space not claimed
		 * 5) not a designated city space
		 * 6) forest
		*/
		if(!freeCitySpace()) {
			System.err.print("There is no more room on the board for a city.");
		} else {
			int counter = 1;
			int counter2 =1;
			int counter3 =1;
			for (int i=0;i<board.length;i++) {
				for(int j=0;j<board[i].length;j++) {
					if(!board[i][j].designated_water && !board[i][j].designated_city && !isClaimed(i,j) && !board[i][j].city_adjacent) {
						System.out.print(counter + ",");
						options.add(board[i][j]);
						counter++;
					} else {
						board[i][j].print();
						System.out.print(",");
					}
				}
				System.out.println("");
			}
			while(options.size() > 0) {
				System.out.print("["+counter3+"] Bonus Resources: \t");
				BoardTile temp = options.remove();
				if(temp.bonusResources.length > 0) for(Resources e: temp.bonusResources) System.out.print(e.toString() + ", ");
				else System.out.print("None");
				System.out.println();
				counter3++;
			}
			int in = player.game.getUserInt("Which tile would you like to place your city on?", "You cant place a city here, choose a different tile ", 1, counter-1);
			
			for(int i=2;i<board.length;i++) {
				for(int j=0;j<board[i].length;j++) {
					if(!isClaimed(i,j) && !board[i][j].city_adjacent && !board[i][j].designated_city && !board[i][j].designated_water) {
						if(counter2==in) {
							claimCity(player,i,j);
							BoardTile tile = board[i][j];
							tile.getBonusResources(player);
						}
						counter2++;
					}
				}
			}
		}
	}
	
	public void cityAdjacencyUpdater(int i, int j) {
		if(i < 2 && i >= 0) {
			return;
		} else if (i==2) {		/*this is all in the right location*/
			if (j==0) {
				board[i][j+1].city_adjacent = true;
				board[i+1][j].city_adjacent = true;
				board[i+1][j+1].city_adjacent = true;
			} else if (j==board[i].length-1) {
				board[i][j-1].city_adjacent = true;
				board[i+1][j].city_adjacent = true;
				board[i+1][j+1].city_adjacent = true;
			} else {
				board[i][j-1].city_adjacent = true;
				board[i][j+1].city_adjacent = true;
				board[i+1][j].city_adjacent = true;
				board[i+1][j+1].city_adjacent = true;
			}
		} else if (i>2 && i<6) { /*locations here are good*/
			if (j==0) {
				board[i][j+1].city_adjacent = true;
				board[i+1][j].city_adjacent = true;
				board[i+1][j+1].city_adjacent = true;
				board[i-1][j].city_adjacent = true;
			} else if (j==board[i].length-1) {
				board[i][j-1].city_adjacent = true;
				board[i+1][j].city_adjacent = true;
				board[i+1][j+1].city_adjacent = true;
				board[i-1][j-1].city_adjacent = true;
			} else {
				board[i][j-1].city_adjacent = true;
				board[i][j+1].city_adjacent = true;
				board[i+1][j].city_adjacent = true;
				board[i+1][j+1].city_adjacent = true;
				board[i-1][j-1].city_adjacent = true;
				board[i-1][j].city_adjacent = true;
			}
			
		} else if (i==6) {/*locations here are good*/
			if (j==0) {
				board[i][j+1].city_adjacent = true;
				board[i-1][j].city_adjacent = true;
				board[i+1][j].city_adjacent = true;
			} else if (j==board[i].length-1) {
				board[i][j-1].city_adjacent = true;
				board[i+1][j-1].city_adjacent = true;
				board[i-1][j-1].city_adjacent = true;
			} else {
				board[i][j-1].city_adjacent = true;
				board[i][j+1].city_adjacent = true;
				board[i+1][j].city_adjacent = true;
				board[i+1][j-1].city_adjacent = true;
				board[i-1][j-1].city_adjacent = true;
				board[i-1][j].city_adjacent = true;
			}
		} else if (i>6 && i < board.length-1) {/*locations here are good*/
			if (j==0) {
				board[i][j+1].city_adjacent = true;
				board[i-1][j].city_adjacent = true;
				board[i+1][j].city_adjacent = true;
				board[i-1][j+1].city_adjacent = true;
			} else if (j==board[i].length-1) {
				board[i][j-1].city_adjacent = true;
				board[i-1][j].city_adjacent = true;
				board[i+1][j-1].city_adjacent = true;
				board[i-1][j+1].city_adjacent = true;
			} else {
				board[i][j-1].city_adjacent = true;
				board[i][j+1].city_adjacent = true;
				board[i-1][j].city_adjacent = true;
				board[i-1][j+1].city_adjacent = true;
				board[i+1][j].city_adjacent = true;
				board[i+1][j-1].city_adjacent = true;
			}
		} else if (i==board.length-1) {/*locations here are good*/
			if (j==0) {
				board[i][j+1].city_adjacent = true;
				board[i-1][j].city_adjacent = true;
				board[i-1][j+1].city_adjacent = true;
			} else if (j==board[i].length-1) {
				board[i][j-1].city_adjacent = true;
				board[i-1][j].city_adjacent = true;
				board[i-1][j+1].city_adjacent = true;
			} else {
				board[i][j-1].city_adjacent = true;
				board[i][j+1].city_adjacent = true;
				board[i-1][j].city_adjacent = true;
				board[i-1][j+1].city_adjacent = true;
			}
		} else {
			System.err.print("congrats you found an easer egg. now choose a normal place you fuck");
		}
	}
	
	public boolean freeLandSpace() {
		for(int i = 0; i<board.length; i++) 
			for(int j = 0; j<board[i].length; j++) {
				BoardTile e = board[i][j];
				if(!isClaimed(i,j) &&!e.designated_city && !e.designated_water)
					return true;
			}
		return false;
	}
	
	public boolean freeCitySpace() {
		if(!freeLandSpace()) return false;
		for(int i = 0; i<board.length; i++) 
			for(int j = 0; j<board[i].length; j++) {
				BoardTile e = board[i][j];
				if(!isClaimed(i,j) && !e.city_adjacent && !e.designated_city && !e.designated_water)
					return true;
			}
		return false;
	}
	
	public void setOwner(Player player, int i, int j) {
		board[i][j].owner = player;
	}
	
	public void claimCity(Player player, int i, int j) {
		board[i][j].type = TileType.City;
		setOwner(player, i, j);
		cityAdjacencyUpdater(i,j);
		PermanentAbilities.GlobalCityPlaced(player.game.players);
		PermanentAbilities.YourCityPlaced(player);
	}
	
	public void claimGreenery(Player player, int i, int j) {
		board[i][j].type = TileType.Greenery;
		setOwner(player, i, j);
		increaseOxygen(player);
		PermanentAbilities.AddHerbivore(player);
	}
	
	public void claimWater(Player player, int i, int j) {
		waterAdjacent(i,j);
		BoardTile tile = board[i][j];
		tile.getBonusResources(player);
		board[i][j].type = TileType.Water;
		water++;
		player.terraform_rating++;
		PermanentAbilities.GlobalWaterPlaced(player.game.players);
	}
	
	public int allCitiesFromRow(int row) {
		int count = 0;
		for(int i = row; i < board.length; i++) for(int j = 0; j < board[i].length; j++) if(isClaimed(i, j) && board[i][j].type == TileType.City) count++;
		return count;
		
	}
	
	public boolean isClaimed(int i, int j) {
		return !(board[i][j].type == null);
	}
	
	public String getName(int i, int j) {
		if(board[i][j].name == null || board[i][j].name.length() == 0) {
			System.err.println("Trying to access an invalid name for board index " + i + " " + j);
			System.exit(-1);
		}
		return board[i][j].name;
	}
	
	public void claimCustomTile(Player player, int i, int j) {
		board[i][j].owner = player;
		board[i][j].getBonusResources(player);
		board[i][j].type = TileType.Dead_Custom_Tile;
	}
	
	public void claimCustomTile(Player player, TileType t, int i, int j) {
		board[i][j].owner = player;
		board[i][j].getBonusResources(player);
		board[i][j].type = t;
	}
	
	public ArrayList<Resources> getBonusResources(int i, int j) {
		ArrayList<Resources> list = new ArrayList<Resources>();
		for(Resources e: board[i][j].bonusResources) list.add(e);
		return list;
	}
	
	public BoardTile[][] getBoard() { return board; }
	
	public int numGreeneries(Player player) {
		int number = 0;
		for(int i = 0; i<board.length; i++) 
			for(int j = 0; j<board[i].length; j++) 
				if(isGreenery(i,j) && board[i][j].owner != null && board[i][j].owner.equals(player))
					number++;
		
		return number;	
	}
	
	public int pointsFromCities(Player player) {
		int number = 0;
		for(int i = 0; i<board.length; i++) 
			for(int j = 0; j<board[i].length; j++) 
				if(isCity(i,j) && board[i][j].owner != null && board[i][j].owner.equals(player)) {
					number += numAdjacentGreeneries(i,j);
				}
		
		return number;	
	}
	
	public int numAdjacentGreeneries(int i, int j) {
		if(i < 2 || i > 10) return 0;
		int num_greeneries = 0;
		if(j != 0) if(isGreenery(i,j-1)) num_greeneries++;
		if(j != board[i].length-1) if(isGreenery(i,j+1)) num_greeneries++;
		
		if(i < 7) {
			if(i != 2) {
				if(j != 0) if(isGreenery(i-1,j-1)) num_greeneries++;					// 1
				if(j != board[i].length-1) if(isGreenery(i-1,j)) num_greeneries++;		// 2
			}
			if(i < 6) {
				if(isGreenery(i+1,j)) num_greeneries++;									// 5
				if(isGreenery(i+1,j+1)) num_greeneries++;								// 6
			}
		}
		if( i > 5) {
			if(i != 10) {
				if(j != 0) if(isGreenery(i+1,j-1)) num_greeneries++;					// 4
				if(j != board[i].length-1) if(isGreenery(i+1,j)) num_greeneries++;		// 5
			}
			if(i > 6) {
				if(isGreenery(i-1,j)) num_greeneries++;									// 2
				if(isGreenery(i-1,j+1)) num_greeneries++;								// 3
			}
		}
		return num_greeneries;
	}
	
	public void customTilePoints() {
		for(int i = 0; i<board.length; i++) for(int j = 0; j<board[i].length; j++) {
			BoardTile tile = board[i][j];
			if(tile.type == null) continue;
			if(tile.type.equals(TileType.City) || tile.type.equals(TileType.Greenery) || tile.type.equals(TileType.Water) || tile.type.equals(TileType.Dead_Custom_Tile)) continue;
			switch(tile.type) {
			case Commercial_District:
				tile.owner.terraform_rating += numAdjacentCities(i,j);
				break;
			case Capital:
				tile.owner.terraform_rating += numAdjacentWater(i,j);
				break;
			default:
				System.err.println("End of game points for custom tile " + tile.type.toString() + " is not implemented.");
			}
		}
	}
	
	public int numAdjacentWater(int i, int j) {
		if(i < 2 || i > 10) return 0;
		int num_water = 0;
		if(j != 0) if(isWater(i,j-1)) num_water++;
		if(j != board[i].length-1) if(isWater(i,j+1)) num_water++;
		
		if(i < 7) {
			if(i != 2) {
				if(j != 0) if(isWater(i-1,j-1)) num_water++;					// 1
				if(j != board[i].length-1) if(isWater(i-1,j)) num_water++;		// 2
			}
			if(i < 6) {
				if(isWater(i+1,j)) num_water++;									// 5
				if(isWater(i+1,j+1)) num_water++;								// 6
			}
		}
		if( i > 5) {
			if(i != 10) {
				if(j != 0) if(isWater(i+1,j-1)) num_water++;					// 4
				if(j != board[i].length-1) if(isWater(i+1,j)) num_water++;		// 5
			}
			if(i > 6) {
				if(isWater(i-1,j)) num_water++;									// 2
				if(isWater(i-1,j+1)) num_water++;								// 3
			}
		}
		return num_water;
	}
	
	private boolean isWater(int i, int j) {
			if(board[i][j].type != null && board[i][j].type.equals(TileType.Water)) return true;
			return false;
		}
}
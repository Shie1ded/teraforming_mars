package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import board.Awards;
import board.Board;
import board.Resources;
import player.Abilities;
import player.Ability;
import player.Card;
import player.Company;
import player.PermanentAbilities;
import player.Player;
import player.Tag;

public class Game extends GameHelper {
	public Abilities abilities = new Abilities(this);
	public Awards awards = new Awards(this);
	public GameLoader loader = new GameLoader();
	
	public Game(Scanner reader) {
		super(reader);
		Player.total_number = 0;
	}
	
	public void GameSetup() {
		quickSetup(false);
		setup();
		for (Player i : players) {
			i.clearAbilities();
			i.terraform_rating_before = i.terraform_rating;
		}
	}
	
	public boolean GameRunner() {
			System.out.println("\n-------- Research Phase --------");
			System.out.println("Please choose cards that you would like to keep");
			sleep();
			researchPhase();
			System.out.println("\n-------- Action Phase --------");
			sleep();
			actionPhase();
			productionPhase();
			if (board.finished())
				return false;
			turnOrderPhase();
			return true;
	}
	
	public void GameEnd() {
		teardown();
		reader.close();
	}
	
	public void setup(int num_players, int num_company_choices, boolean corporate_era, boolean prelude, boolean custom, boolean played_before) {
		// Create the players
		players = new Player[num_players];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(this);
		}

		// Create the board
		board = new Board(reader);
		if (num_players == 1)
			board.setSinglePlayer();
		board.print();

		// Create and shuffle the deck
		Card[] deck_arr = shuffle(Card.createDeck("cards"));
		deck = new LinkedList<Card>();
		for (Card i : deck_arr) {
			deck.add(i);
		}
		if(corporate_era) {
			deck_arr = shuffle(Card.createDeck("corporate_era_cards"));
			for (Card i : deck_arr) {
				deck.add(i);
			}
			Card[] temp = new Card[deck.size()];
			for(int i = 0; i<temp.length; i++) temp[i] = deck.remove();
			deck_arr = shuffle(temp);
			for (Card i : deck_arr) {
				deck.add(i);
			}
		}
		if(prelude) {
			deck_arr = shuffle(Card.createDeck("prelude_cards"));
			for (Card i : deck_arr) {
				deck.add(i);
			}
			Card[] temp = new Card[deck.size()];
			for(int i = 0; i<temp.length; i++) temp[i] = deck.remove();
			deck_arr = shuffle(temp);
			for (Card i : deck_arr) {
				deck.add(i);
			}
			
			Card[] prelude_arr = shuffle(Card.createDeck("preludes"));
			preludes = new LinkedList<Card>();
			prelude_discard = new LinkedList<Card>();
			for(Card e: prelude_arr)
				preludes.add(e);
			
			if(custom) {
				prelude_arr = shuffle(Card.createDeck("custom_preludes"));
				for(Card e: prelude_arr)
					preludes.add(e);
				temp = new Card[preludes.size()];
				for(int i = 0; i<temp.length; i++) temp[i] = preludes.remove();
				prelude_arr = shuffle(temp);
				for (Card i : prelude_arr) {
					preludes.add(i);
				}
			}
			
		}
		if(custom) {
			deck_arr = shuffle(Card.createDeck("custom_cards"));
			for (Card i : deck_arr) {
				deck.add(i);
			}
			Card[] temp = new Card[deck.size()];
			for(int i = 0; i<temp.length; i++) temp[i] = deck.remove();
			deck_arr = shuffle(temp);
			for (Card i : deck_arr) {
				deck.add(i);
			}
		}
		discard = new LinkedList<Card>();

		// Create and shuffle the companies
		Company[] company_arr = shuffle(Company.createDeck("companies"));
		companies = new LinkedList<Company>();
		for (Company i : company_arr)
			companies.add(i);
		if(corporate_era) {
			company_arr = shuffle(Company.createDeck("corporate_era_companies"));
			for (Company i : company_arr)
				companies.add(i);
			Company[] temp = new Company[companies.size()];
			for(int i = 0; i<temp.length; i++) temp[i] = companies.remove();
			company_arr = shuffle(temp);
			for (Company i : company_arr) {
				companies.add(i);
			}
		}
		if(prelude) {
			company_arr = shuffle(Company.createDeck("prelude_companies"));
			for (Company i : company_arr)
				companies.add(i);
			Company[] temp = new Company[companies.size()];
			for(int i = 0; i<temp.length; i++) temp[i] = companies.remove();
			company_arr = shuffle(temp);
			for (Company i : company_arr) {
				companies.add(i);
			}
		}
		if(custom) {
			company_arr = shuffle(Company.createDeck("custom_companies"));
			for (Company i : company_arr)
				companies.add(i);
			Company[] temp = new Company[companies.size()];
			for(int i = 0; i<temp.length; i++) temp[i] = companies.remove();
			company_arr = shuffle(temp);
			for (Company i : company_arr) {
				companies.add(i);
			}
		}

		if (companies.size() / num_players < num_company_choices) {
			System.err.println(
					"There are not enough companies to allow players to pick " + num_company_choices + " companies.");
			System.err.println("There are " + companies.size() + " companies, and " + num_players + " players.");
			System.exit(-1);
		}
	}
	
	public Company[] getChoices(int num_company_choices, boolean played_before) {
		Company[] choices = new Company[num_company_choices];
		for (int j = 0; j < num_company_choices; j++) {
			choices[j] = companies.remove();
		}
		if(!played_before) {
			Company[] temp = new Company[choices.length];
			for(int i = 0; i<choices.length; i++) temp[i] = choices[i];
			choices = new Company[temp.length + 1];
			int k = 0;
			for(; k<temp.length; k++) choices[k] = temp[k];
			choices[k] = Company.createDefaultCompany();
		}
		return choices;
	}
	
	public ArrayList<Card> getChoices(Player p) {
		ArrayList<Card> choices = new ArrayList<Card>();
		for(int j = 0; j<4; j++) 
			choices.add(removePrelude());
		if(PermanentAbilities.OuterResearch(p)) 
			choices.add(removePrelude());
		return choices;
	}

	// setup all the players, companies, etc
	private void setup() {
		int num_players = 0;
		int num_company_choices = 0;
		boolean corporate_era = false, prelude = false, custom = false, played_before = true;
		if (!quickSetup) {
			// determine game state variables
			num_players = getUserInt("Number of players", "You must have 1-6 players.", 1, 6);
			num_company_choices = getUserInt("Number of company choices (recommended 2)",
					"Invalid number.", 1, 10);
			num_new_cards = getUserInt("Number of cards drawn per player (recommended 4)",
					"You must have between 1-10 cards.", 1, 10);
			corporate_era = getUserInt("Do you want to include Corporate Era cards?\n\t[1] Yes\n\t[2] No\nChoice", "Invalid. Choose 1 or 2.", 1, 2) == 1;
			prelude = getUserInt("Do you want to include Prelude cards?\n\t[1] Yes\n\t[2] No\nChoice", "Invalid. Choose 1 or 2.", 1, 2) == 1;
			custom = getUserInt("Do you want to include custom cards?\n\t[1] Yes\n\t[2] No\nChoice", "Invalid. Choose 1 or 2.", 1, 2) == 1;
			played_before = getUserInt("Are any players new to the game?\n\t[1] Yes\n\t[2] No\nChoice", "Invalid. Choose 1 or 2.", 1, 2) == 2;
		} else {
			num_players = 2;
			System.out.println(num_players + " players.");
			num_company_choices = 1;
			System.out.println("Automatically choosing company");
			num_new_cards = 4;
			System.out.println(num_new_cards + " card options per generation");
		}

		setup(num_players, num_company_choices, corporate_era, prelude, custom, played_before);
	}

	// switch the turn order
	private void turnOrderPhase() {
		if (players.length == 1) {
			players[0].clearAbilities();
			board.current_generation++;
			loader.saveGame(this);
			return;
		}
		Player[] new_order = new Player[players.length];
		for (int i = 0; i < players.length - 1; i++) {
			new_order[i] = players[i + 1];
		}
		new_order[players.length - 1] = players[0];
		players = new_order;

		for (Player i : players) {
			i.clearAbilities();
			i.terraform_rating_before = i.terraform_rating;
		}
		board.current_generation++;
		loader.saveGame(this);
	}

	// buy cards
	private void researchPhase() {
		int num_extra = 0;
		for(Player p: players) if(PermanentAbilities.OuterResearch(p)) num_extra = 2;
			
		
		// if out of cards, add in the discards
		if (deck.size() < num_new_cards * players.length + num_extra)
			if(!canDrawCard(1)) return;
		
		if (deck.size() < num_new_cards * players.length + num_extra) {
			System.out.println("There are not enough cards!");
			return;
		}

		// if there are enough cards
		Card[][] new_cards = new Card[num_new_cards][players.length];
		for (int i = 0; i < new_cards.length; i++) {
			for (int j = 0; j < new_cards[i].length; j++) {
				if (deck.size() == 0) {
					System.out.println("There are not enough cards! Make use of what you have.");
					break;
				}
				new_cards[i][j] = deck.remove();
			}
			if (deck.size() == 0)
				break;
		}

		for (int i = 0; i < players.length; i++) {
			if(board.current_generation == 1 && players[i].company.name.endsWith("beginner corp")) continue;
			Card[] new_player_card = new Card[num_new_cards];
			for (int j = 0; j < new_cards.length; j++) {
				new_player_card[j] = new_cards[j][i];
			}
			if(PermanentAbilities.OuterResearch(players[i]) && canDrawCard(2)) {
				Card[] temp = new Card[new_player_card.length];
				for(int k = 0; i<temp.length; i++) temp[k] = new_player_card[k];
				new_player_card = new Card[temp.length + 2];
				int k = 0;
				for(; i<temp.length; i++) temp[k] = new_player_card[k];
				new_player_card[k++] = deck.remove();
				new_player_card[k] = deck.remove();
			}
			players[i].printPlayerBar();
			players[i].print();
			players[i].printHand();
			sleep();
			players[i].chooseCards(new_player_card, reader);
		}
	}

	// take all actions
	private void actionPhase() {
		Queue<Player> all_players = new LinkedList<Player>();
		for (Player player : players)
			all_players.add(player);
		while (all_players.size() > 0) {
			int actions_taken = 0;
			Player player = all_players.remove();
			player.printPlayerBar();
			boolean loop = true;
			while (loop) {
				printActionMenu();
				System.out.println("Actions Remaining: " + (2 - actions_taken));
				int n = getUserInt("Select Option", "Please choose a value between 1 and 12.", 1, 12);
				switch (n) {
				case 1:
					player.print();
					break;
				case 2:
					player.printHand();
					break;
				case 3:
					player.printPlayedCards();
					break;
				case 4:
					board.printAll(players);
					break;
				case 5:
					printScores();
					break;
				case 6:
					if (inventoryAction(player))
						actions_taken++;
					break;
				case 7:
					if (standardProjectAction(player))
						actions_taken++;
					break;
				case 8:
					if (cardAction(player))
						actions_taken++;
					break;
				case 9:
					if (playCard(player))
						actions_taken++;
					break;
				case 10:
					if(awards.fundAward(player))
						actions_taken++;
					break;
				case 11:
					if(awards.getMilestone(player))
						actions_taken++;
				case 12:
					loop = false;
					break;
				}
				if (actions_taken == 2)
					loop = false;
			}

			if (actions_taken > 0)
				all_players.add(player);
		}

	}

	// produce resources
	private void productionPhase() {
		for (Player i : players)
			i.generate_resources();
	}

	// end of game, determine winners and whatnot
	private void teardown() {
		EndOfGame endgame = new EndOfGame(this);
		endgame.teardown();
	}
	
	public boolean standardProjectAction(Player player) {
		Ability[] standardProjects = { Ability.Sell_Patent, Ability.Power_Plant, Ability.Asteroid, Ability.Aquifier,
				Ability.Greenery, Ability.City };
		return actionPlayer(player, standardProjects, "Standard Project Actions");
	}

	public boolean inventoryAction(Player player) {
		Ability[] inventoryActions = { Ability.Convert_Greenery, Ability.Convert_Temperature };
		return actionPlayer(player, inventoryActions, "Inventory Actions");
	}

	public boolean cardAction(Player player) {
		Ability[] cardActions = player.getCardAbilities();
		if(PermanentAbilities.companyAbility(player) != null) {
			Ability[] temp = new Ability[cardActions.length];
			for(int i = 0; i<cardActions.length; i++) temp[i] = cardActions[i];
			cardActions = new Ability[temp.length + 1];
			int i = 0;
			for(; i<temp.length; i++) cardActions[i] = temp[i];
			cardActions[i] = PermanentAbilities.companyAbility(player);
		}
		if (cardActions.length < 1) {
			System.out.println("You have not played cards that generate actions.");
			return false;
		}
		return actionPlayer(player, cardActions, "Card Actions");
	}

	public boolean actionPlayer(Player player, Ability[] actions, String consoleText) {
		while (true) {
			System.out.println("\nType \'d\' to describe the action. Type \'q\' to go back.");
			sleep();
			System.out.println("\n" + consoleText + ": ");
			for (int i = 0; i < actions.length; i++)
				System.out.println("[" + (i + 1) + "] " + actions[i].toString());
			System.out.print("\nSelect action:\t");
			try {
				String in = reader.nextLine();
				System.out.println();
				if (in.equals("d")) {
					abilities.describe();
					continue;
				} else if (in.equals("q"))
					return false;
				int n = Integer.valueOf(in);
				if (n < 1 || n > actions.length) {
					System.out.println("Please choose a value between 1 and " + (actions.length));
					continue;
				}
				if (abilities.activate(player, actions[n - 1]))
					return true;
				return false;
			} catch (Exception e) {
				System.err.println("Invalid. Please insert a valid number");
			}
		}
	}

	public boolean playCard(Player player) {
		if (player.hand.size() < 1) {
			System.err.println("No cards to play.");
			return false;
		}
		while (true) {
			player.printHand();
			System.out.println("\nType \'d\' to describe the action.");
			System.out.print("\nDo you want to play a card (y/n)?\t");
			try {
				String in = reader.nextLine();
				in = in.trim().toLowerCase();
				if(in.equals("d")) {
					abilities.describe();
					continue;
				}
				if (in.equals("y") || in.equals("yes")) {
					if (player.hand.size() == 1)
						return playCard(player, 0);
					while (true) {
						System.out.print("Which card would you like to play?\t");
						try {
							in = reader.nextLine();
							System.out.println();
							int n = Integer.valueOf(in);
							if (n < 1 || n > player.hand.size()) {
								System.out.println("Please choose a value between 1 and " + (player.hand.size()));
								continue;
							}
							return playCard(player, n - 1);
						} catch (Exception e) {
							System.out.println("Invalid. Please insert a valid number");
						}
					}
				} else if (in.equals("n") || in.equals("no")) {
					return false;
				} else {
					System.err.print("\nNot a valid input.");
				}
			} catch (Exception e) {
				System.err.println("Error, invalid entry.");
				return false;
			}
		}
	}

	public boolean playCard(Player player, int card_index) {
		Card card = player.hand.get(card_index);
		boolean credicor = false;
		if(card.cost >= 20) credicor = true;
		card = PermanentAbilities.pipeCardBefore(player, card);
		
		// can they afford it (money and resources)
		if (card.resources_remove != null && card.resources_remove.length > 0) {
			if (!player.hasMoney(card.getPayableResources(), card.cost, card.resources_remove)) {
				System.err.println(card.name + " costs " + card.cost
						+ " Megacredits, and removes resources. You can't afford it.");
				card = PermanentAbilities.undoPipeCardBefore(player, card);
				return false;
			}
			if (!canAffordResources(groupResources(card.resources_remove), player)) {
				System.err.println("You do not have the required resources to play this card.");
				card = PermanentAbilities.undoPipeCardBefore(player, card);
				return false;
			}
		} else if (!player.hasMoney(card.getPayableResources(), card.cost)) {
			System.err.println(card.name + " costs " + card.cost + " Megacredits. You can't afford it.");
			card = PermanentAbilities.undoPipeCardBefore(player, card);
			return false;
		}
		if (card.resource_generation_remove != null && card.resource_generation_remove.length > 0) {
			if (!canAffordResourceGeneration(groupResources(card.resource_generation_remove), player)) {
				System.err.println("You do not have the required resource generation to play this card.");
				card = PermanentAbilities.undoPipeCardBefore(player, card);
				return false;
			}
		}

		// do the global requirements allow them to play it
		if (card.min_oxygen > board.oxygen) {
			System.err.println("The oxygen is not high enough to play this card.");
			card = PermanentAbilities.undoPipeCardBefore(player, card);
			return false;
		}
		if (card.max_oxygen < board.oxygen) {
			System.err.println("The oxygen is too high to play this card");
			card = PermanentAbilities.undoPipeCardBefore(player, card);
			return false;
		}
		if (card.min_water > board.water) {
			System.err.println("The water is not high enough to play this card.");
			card = PermanentAbilities.undoPipeCardBefore(player, card);
			return false;
		}
		if (card.max_water < board.water) {
			System.err.println("The water is too high to play this card");
			card = PermanentAbilities.undoPipeCardBefore(player, card);
			return false;
		}
		if (card.min_temperature > board.temperature) {
			System.err.println("The temperature is not high enough to play this card.");
			card = PermanentAbilities.undoPipeCardBefore(player, card);
			return false;
		}
		if (card.max_temperature < board.temperature) {
			System.err.println("The temperature is too high to play this card");
			card = PermanentAbilities.undoPipeCardBefore(player, card);
			return false;
		}

		// do the card / ability requirements allow them to play it
		if (card.min_tags != null && card.min_tags.length > 0) {
			if (!hasTags(groupTags(player.getAllTags()), groupTags(card.min_tags))) {
				System.err.println("You don't have the required tags to play this card");
				card = PermanentAbilities.undoPipeCardBefore(player, card);
				return false;
			}
		}
		
		if (card.abilities != null)
			for (Ability i : card.abilities)
				if (Abilities.hasRequirement(i))
					if (!Abilities.satisfiesRequirement(i, player)) {
						System.err.println("You do not satisfy the ability requirements required to play this card.");
						card = PermanentAbilities.undoPipeCardBefore(player, card);
						return false;
					}
		
		// remove resources
		if (card.resources_remove != null)
			for (Resources i : card.resources_remove)
				player.increaseResource(i, -1);
		player.purchaseWithResource(card.getPayableResources(), card.cost);
		if (card.resource_generation_remove != null)
			for (Resources i : card.resource_generation_remove)
				player.increaseResourceGeneration(i, -1);
		if (card.resources_remove_global != null) 
			if(players.length != 1) 
				globalResourceDeduction(card.resources_remove_global);
		if (card.resource_generation_remove_global != null)
			if(players.length != 1)
				globalResourceGenerationDeduction(card.resource_generation_remove_global);

		// add resources
		if (card.resources_add != null)
			for (Resources i : card.resources_add)
				player.increaseResource(i, 1);
		if (card.resource_generation_add != null)
			for (Resources i : card.resource_generation_add)
				player.increaseResourceGeneration(i, 1);
		
		if (card.abilities != null)
			for (Ability i : card.abilities)
				abilities.activate(player, i);
		
		card = PermanentAbilities.pipeCardAfter(reader, player, card);
		card = PermanentAbilities.undoPipeCardBefore(player, card);
		if(credicor) PermanentAbilities.Credicor(player);
		
		player.played.add(card);
		player.hand.remove(card);
		
		return true;
	}

	public HashMap<Resources, Integer> groupResources(Resources[] resources) {
		if (resources == null || resources.length == 0)
			return null;
		HashMap<Resources, Integer> num_resources = new HashMap<Resources, Integer>();
		ArrayList<Resources> list = new ArrayList<Resources>();
		for (Resources i : resources)
			list.add(i);
		while (list.size() > 0) {
			int number = 1;
			for (int j = 1; j < list.size(); j++) {
				if (list.get(0) == list.get(j)) {
					number++;
					list.remove(j);
					j--;
				}
			}
			num_resources.put(list.get(0), number);
			list.remove(0);
		}
		return num_resources;
	}

	public boolean canAffordResources(HashMap<Resources, Integer> map, Player player) {
		Set<Entry<Resources, Integer>> set = map.entrySet();
		Iterator<Entry<Resources, Integer>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<Resources, Integer> mentry = (Map.Entry<Resources, Integer>) it.next();
			if (!player.checkResource(mentry.getKey(), mentry.getValue()))
				return false;
		}
		return true;
	}

	public boolean canAffordResourceGeneration(HashMap<Resources, Integer> map, Player player) {
		Set<Entry<Resources, Integer>> set = map.entrySet();
		Iterator<Entry<Resources, Integer>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<Resources, Integer> mentry = (Map.Entry<Resources, Integer>) it.next();
			if (!player.checkResourceGeneration(mentry.getKey(), mentry.getValue()))
				return false;
		}
		return true;
	}

	public HashMap<Tag, Integer> groupTags(Tag[] tags) {
		if (tags == null || tags.length == 0)
			return null;
		HashMap<Tag, Integer> num_tags = new HashMap<Tag, Integer>();
		ArrayList<Tag> list = new ArrayList<Tag>();
		for (Tag i : tags)
			list.add(i);
		while (list.size() > 0) {
			int number = 1;
			for (int j = 1; j < list.size(); j++) {
				if (list.get(0) == list.get(j)) {
					number++;
					list.remove(j);
					j--;
				}
			}
			num_tags.put(list.get(0), number);
			list.remove(0);
		}
		return num_tags;
	}

	public boolean hasTags(HashMap<Tag, Integer> player_tags, HashMap<Tag, Integer> requirements) {
		if (player_tags == null || player_tags.size() == 0)
			return false;
		Set<Entry<Tag, Integer>> set = requirements.entrySet();
		Iterator<Entry<Tag, Integer>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<Tag, Integer> mentry = (Map.Entry<Tag, Integer>) it.next();
			if (!player_tags.containsKey(mentry.getKey()))
				return false;
			if (player_tags.get(mentry.getKey()) < mentry.getValue())
				return false;
		}
		return true;
	}

	public boolean globalResourceGenerationDeduction(Resources[] input) {
		HashMap<Resources, Integer> resources = groupResources(input);
		Set<Entry<Resources, Integer>> set = resources.entrySet();
		Iterator<Entry<Resources, Integer>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<Resources, Integer> mentry = (Map.Entry<Resources, Integer>) it.next();
			if (!decreaseResourceGenerationOfPlayer(mentry.getKey(), mentry.getValue()))
				return false;
		}
		return true;
	}

	public boolean decreaseResourceGenerationOfPlayer(Resources resource, int number) {
		int total_generation = 0;
		for (Player i : players)
			total_generation += i.getResourceGeneration(resource);
		if (total_generation < number) {
			System.err
					.println("You cannot decrease " + number + " " + resource.toString() + " production from players");
			return false;
		}
		for (int j = 0; j < number; j++) {
			System.out.println("\nSelect Player to decrease " + resource.toString() + " generation:");
			for (int i = 0; i < players.length; i++)
				System.out.println("\t[" + (i + 1) + "] Player " + players[i].number + " " + resource.toString()
						+ " generation: " + players[i].getResourceGeneration(resource));
			int player_choice = getUserInt("Player", "There are only players 1-" + players.length + ".", 1,
					players.length) - 1;
			if (!players[player_choice].checkResourceGeneration(resource, 1)) {
				System.out.println("Player " + (player_choice + 1) + " does not have enough generation resources.");
				j--;
				continue;
			}
			players[player_choice].increaseResourceGeneration(resource, -1);
		}
		return true;
	}

	public boolean globalResourceDeduction(Resources[] input) {
		HashMap<Resources, Integer> resources = groupResources(input);
		Set<Entry<Resources, Integer>> set = resources.entrySet();
		Iterator<Entry<Resources, Integer>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<Resources, Integer> mentry = (Map.Entry<Resources, Integer>) it.next();
			if (!decreaseResourceOfPlayer(mentry.getKey(), mentry.getValue()))
				return false;
		}
		return true;
	}

	public boolean decreaseResourceOfPlayer(Resources resource, int number) {
		boolean existsPlayer = false;
		for(Player e: players) if(e.checkResource(resource, 1)) existsPlayer = true;
		if(!existsPlayer) return false;
		System.out.println("\nSelect Player to decrease " + number + " " + resource.toString() + ":");
		for (int i = 0; i < players.length; i++)
			System.out.println("\t[" + (i + 1) + "] Player " + players[i].number + " " + resource.toString() + ": "
					+ players[i].getResource(resource));
		int player_choice = getUserInt("Player", "There are only 1-" + players.length + " players.", 1, players.length)
				- 1;
		
		// custom ability that blocks the removal of plants
		boolean canRemove = true;
		if(resource.equals(Resources.Plant)) for(Ability e: players[player_choice].permanent_abilities) if(e.equals(Ability.BlockedCounters)) canRemove = false;
		
		if(canRemove) {
			for (int i = 0; i < number; i++)
				if (players[player_choice].checkResource(resource, 1))
					players[player_choice].increaseResource(resource, -1);
		}
		return true;
	}

	public int getUserInt(String question, String failure_message, int min, int max) {
		while (true) {
			System.out.println("\nType \'d\' to describe an action.");
			System.out.print("\n" + question + ":\t");
			try {
				String in = reader.nextLine();
				if(in.equals("d")) {
					abilities.describe();
					continue;
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
	
	public boolean canDrawCard(int size) {
		if(deck.size() >= size) return true;
		
		Card[] temp_arr = new Card[discard.size()];
		int i = 0;
		while (discard.size() > 0) {
			temp_arr[i] = discard.remove();
			i++;
		}
		temp_arr = shuffle(temp_arr);
		for (Card c : temp_arr)
			deck.add(c);
		if (!(deck.size() >= size)) {
			System.out.println("There are no more cards in the deck!");
			return false;
		}
		return true;
	}
}

class GameHelper {
	public static boolean quickSetup = false;
	public static void quickSetup(boolean t) {quickSetup = t;}
	
	public Player[] players;
	public Board board;
	public Queue<Card> deck;
	public Queue<Card> discard;
	public Queue<Card> preludes;
	public Queue<Card> prelude_discard;
	public Queue<Company> companies;
	public final Scanner reader;
	protected int num_new_cards = 4;
	
	public GameHelper(Scanner reader) {this.reader = reader;}

	// shuffle the deck of cards and companies
	protected <T> T[] shuffle(T[] cards) {
		Collections.shuffle(Arrays.asList(cards));
		return cards;
	}

	// get a card
	public Card getNextCard() {
		return deck.remove();
	}

	// add a delay
	public static void sleep() {
		if(!quickSetup) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.out.println("System sleep disrupted.");
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	// print the menu of possible actions
	public static void printActionMenu() {
		System.out.println("\n-------- Action Menu --------");
		System.out.println("[1]\tView inventory");
		System.out.println("[2]\tView hand");
		System.out.println("[3]\tView played cards");
		System.out.println("[4]\tView board");
		System.out.println("[5]\tView points");
		System.out.println("[6]\tInventory action");
		System.out.println("[7]\tStandard Project action");
		System.out.println("[8]\tCard action");
		System.out.println("[9]\tPlay card");
		System.out.println("[10]\tApply for awards");
		System.out.println("[11]\tApply for milestone");
		System.out.println("[12]\tPass\n");
	}

	// print all of the scores of the players
	public void printScores() {
		System.out.println("\nScores:");
		for (Player i : players)
			System.out.println("\tPlayer " + i.number + ", TR: " + i.terraform_rating + ", VPs: " + i.calculateVPs());
		System.out.println();
	}
	
	public Card removePrelude() {
		if(preludes.size() > 0) return preludes.remove();
		else if(prelude_discard.size() == 0) {
			System.err.println("There are no more prelude cards.");
			System.exit(-1);
		}
		Card[] temp = new Card[prelude_discard.size()];
		for(int i = 0; i<temp.length; i++) temp[i] = prelude_discard.remove();
		Card[] prelude_arr = shuffle(temp);
		for(Card e: prelude_arr) preludes.add(e);
		return preludes.remove();
	}
}
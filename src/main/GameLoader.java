package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import board.Awards;
import board.Board;
import board.BoardTile;
import board.Resources;
import board.TileType;
import player.Ability;
import player.Card;
import player.Company;
import player.Player;

public class GameLoader {
	private boolean corporate_era, prelude, custom;
	private FileManager manager = new FileManager("game_save");
	
	public GameLoader() {}

	public void initialBounderies(boolean corporate_era, boolean prelude, boolean custom) {
		this.corporate_era = corporate_era;
		this.prelude = prelude;
		this.custom = custom;
	}
	
	@SuppressWarnings("unchecked")
	public void saveGame(Game game) {
		// create the overall object
		JSONObject JSON = new JSONObject();
		JSON.put("can_load", 1);
		
		// create the game object
		JSONObject json_game = new JSONObject();
		json_game.put("quickSetup", Game.quickSetup ? 1:0);
		json_game.put("corporate_era", corporate_era ? 1:0);
		json_game.put("prelude", prelude ? 1:0);
		json_game.put("custom", custom ? 1:0);
		json_game.put("num_new_cards", game.num_new_cards);
		json_game.put("deck", getCardNames(game.deck));
		json_game.put("discard", getCardNames(game.discard));
		if(prelude) {
			json_game.put("preludes", getCardNames(game.preludes));
			json_game.put("prelude_discard", getCardNames(game.prelude_discard));
		}
		json_game.put("water", game.board.water);
		json_game.put("temperature", game.board.temperature);
		json_game.put("oxygen", game.board.oxygen);
		json_game.put("current_generation", game.board.current_generation);
		json_game.put("maximum_generation", game.board.maximum_generation);
		JSON.put("game", json_game);
		
		// create the board object
		JSONObject json_board = new JSONObject();
		json_board.put("current_generation", game.board.current_generation);
		json_board.put("maximum_generation", game.board.maximum_generation);
		json_board.put("water", game.board.water);
		json_board.put("oxygen", game.board.oxygen);
		json_board.put("temperature", game.board.temperature);
		JSONArray board = new JSONArray();
		for(BoardTile[] f: game.board.board) 
			for(BoardTile e: f) {
				JSONObject tile = new JSONObject();
				if(e.owner != null)
					tile.put("owner", e.owner.number);
				if(e.bonusResources.length > 0) {
					JSONArray resources = new JSONArray();
					for(Resources r: e.bonusResources)
						resources.add(r.toString());
					tile.put("bonusResources", resources);
				}
				if(e.name != null)
					tile.put("name", e.name);
				tile.put("designated_water", e.designated_water ? 1:0);
				tile.put("designated_city", e.designated_city ? 1:0);
				tile.put("city_adjacent", e.city_adjacent ? 1:0);
				if(e.type != null)
					tile.put("type", e.type.toString());
				board.add(tile);
			}
		JSON.put("board", board);
		
		// create the awards object
		JSONObject awards = new JSONObject();
		awards.put("funded", fromStringArr(game.awards.funded));
		awards.put("taken", fromStringArr(game.awards.taken));
		JSONArray taken_players = new JSONArray();
		for(Player e: game.awards.taken_players) taken_players.add(e.number);
		awards.put("taken_players", taken_players);
		JSON.put("awards", awards);
		
		
		Resources[] all_resources = {Resources.MegaCredit, Resources.Steel, Resources.Titanium, Resources.Plant, Resources.Energy, Resources.Heat};
		
		// create the players object
		JSONArray players = new JSONArray();
		for(Player p: game.players) {
			JSONObject player = new JSONObject();
			player.put("number", p.number);
			player.put("terraform_rating", p.terraform_rating);
			player.put("company", p.company.name);
			JSONArray generation = new JSONArray();
			JSONArray resources = new JSONArray();
			for(Resources r: all_resources) {
				JSONObject resource = new JSONObject();
				resource.put(r.toString(), p.getResourceGeneration(r));
				generation.add(resource);
				resource = new JSONObject();
				resource.put(r.toString(), p.getResource(r));
				resources.add(resource);
			}
			player.put("generation", generation);
			player.put("resources", resources);
			player.put("hand", getCardNames(packQueue(p.hand)));
			JSONArray played = new JSONArray();
			for(Card e: p.played) {
				JSONObject card = new JSONObject();
				card.put("name", e.name);
				if(e.counter > 0) card.put("counter", e.counter);
				played.add(card);
			}
			player.put("played", played);
			player.put("permanent_abilities", fromArrayList(p.permanent_abilities));
			player.put("end_of_game_abilities", fromArrayList(p.end_of_game_abilities));
			players.add(player);
		}
		JSON.put("players", players);
		manager.JSON = JSON;
		manager.writeJSONfile();
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray getCardNames(Queue<Card> input) {
		JSONArray return_arr = new JSONArray();
		for(Card e: unpackQueue(input)) return_arr.add(e.name);
		return return_arr;
	}
	
	@SuppressWarnings("unchecked")
	private <T> JSONArray fromArrayList(ArrayList<T> input) {
		JSONArray temp = new JSONArray();
		for(T e: input) temp.add(e.toString());
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	private <T> JSONArray fromStringArr(T[] input) {
		JSONArray temp = new JSONArray();
		for(T e: input) temp.add(e);
		return temp;
	}
	
	private <T> ArrayList<T> unpackQueue(Queue<T> input) {
		ArrayList<T> return_arr = new ArrayList<T>();
		while(input.size() > 0) return_arr.add(input.remove());
		for(T e: return_arr) input.add(e);
		return return_arr;
	}
	
	private <T> Queue<T> packQueue(ArrayList<T> input) {
		Queue<T> return_arr = new LinkedList<T>();
		for(T e: input) return_arr.add(e);
		return return_arr;
	}
	
	@SuppressWarnings("unchecked")
	public Game loadGame(Scanner reader) {
		manager.readJSONfile();
		JSONObject JSON = manager.JSON;
		
		Game game = new Game(reader);
		game.loader = this;
		JSONObject temp = (JSONObject) JSON.get("game");
		Resources[] all_resources = {Resources.MegaCredit, Resources.Steel, Resources.Titanium, Resources.Plant, Resources.Energy, Resources.Heat};
		
		corporate_era = Integer.parseInt(String.valueOf(temp.get("corporate_era"))) == 1;
		prelude = Integer.parseInt(String.valueOf(temp.get("prelude"))) == 1;
		custom = Integer.parseInt(String.valueOf(temp.get("custom"))) == 1;
		
		// load companies
		ArrayList<Company> companies = createArrayList(Company.createDeck("companies"));

		if(corporate_era)
			companies = addArrayList(companies, Company.createDeck("corporate_era_companies"));
		if(prelude)
			companies = addArrayList(companies, Company.createDeck("prelude_companies"));
		if(custom)
			companies = addArrayList(companies, Company.createDeck("custom_companies"));
		
		// load cards
		ArrayList<Card> cards = createArrayList(Card.createDeck("cards"));
		if(corporate_era)
			cards = addArrayList(cards, Card.createDeck("corporate_era_cards"));
		if(prelude) {
			cards = addArrayList(cards, Card.createDeck("prelude_cards"));
			cards = addArrayList(cards, Card.createDeck("preludes"));
			if(custom)
				cards = addArrayList(cards, Card.createDeck("custom_preludes"));

		}
		if(custom)
			cards = addArrayList(cards, Card.createDeck("custom_cards"));
		
		// load players
		JSONArray temp_arr = (JSONArray) JSON.get("players");
		Player[] players = new Player[temp_arr.size()];
		for(int i = 0; i<temp_arr.size(); i++) {
			temp = (JSONObject) temp_arr.get(i);
			Player p = new Player(game);
			p.number = Integer.parseInt(String.valueOf( temp.get("number")));
			p.terraform_rating = Integer.parseInt(String.valueOf( temp.get("terraform_rating")));
			p.company = findCompany(companies, (String) temp.get("company"));
			p.company.owner = p;
			JSONArray generation = (JSONArray) temp.get("generation");
			for(int j = 0; j < generation.size(); j++) {
				JSONObject resource = (JSONObject) generation.get(j);
				for(Resources r: all_resources)
					if(resource.containsKey(r.toString()))
						p.increaseResourceGeneration(r, Integer.parseInt(String.valueOf( resource.get(r.toString()))) - 1);
			}
			
			JSONArray resources = (JSONArray) temp.get("resources");
			for(int j = 0; j < resources.size(); j++) {
				JSONObject resource = (JSONObject) resources.get(j);
				for(Resources r: all_resources)
					if(resource.containsKey(r.toString()))
						p.increaseResource(r, Integer.parseInt(String.valueOf( resource.get(r.toString()))));
			}
			
			ArrayList<String> hand = (ArrayList<String>) temp.get("hand"); 
			for(String e: hand) 
				p.hand.add(findCard(cards, e));
			
			JSONArray played = (JSONArray) temp.get("played");
			for(int j = 0; j<played.size(); j++) {
				JSONObject play = (JSONObject) played.get(j);
				Card e = findCard(cards, (String) play.get("name"));
				if(play.containsKey("counter"))
					e.counter = Integer.parseInt(String.valueOf( play.get("counter")));
				p.played.add(e);
			}
			
			ArrayList<String> permanent_abilities = (ArrayList<String>) temp.get("permanent_abilities"); 
			for(String e: permanent_abilities) 
				p.permanent_abilities.add(Ability.valueOf(e));
			
			ArrayList<String> end_of_game_abilities = (ArrayList<String>) temp.get("end_of_game_abilities"); 
			for(String e: end_of_game_abilities) 
				p.end_of_game_abilities.add(Ability.valueOf(e));
			
			players[i] = p;
			p.print();
		}
		game.players = players;
		
		// load awards
		temp = (JSONObject) JSON.get("awards");
		Awards awards = new Awards(game);
		awards.funded = toArray((ArrayList<String>) temp.get("funded"));
		awards.taken = toArray((ArrayList<String>) temp.get("taken"));
		String[] taken_players = toArray((ArrayList<String>) temp.get("taken_players"));
		awards.taken_players = new Player[taken_players.length];
		for(int i = 0; i<taken_players.length; i++)
			for(Player p: game.players)
				if(p.number == Integer.valueOf(taken_players[i]))
					awards.taken_players[i] = p;
				
		// load board
		temp = (JSONObject) JSON.get("game");
		Board board = new Board(game.reader);
		board.water = Integer.valueOf(String.valueOf(temp.get("water")));
		board.temperature = Integer.valueOf(String.valueOf(temp.get("temperature")));
		board.oxygen = Integer.valueOf(String.valueOf(temp.get("oxygen")));
		board.current_generation = Integer.valueOf(String.valueOf(temp.get("current_generation")));
		board.maximum_generation = Integer.valueOf(String.valueOf(temp.get("maximum_generation")));

		BoardTile[][] board_tiles = new BoardTile[11][];
		board_tiles[0] = new BoardTile[1];
		board_tiles[1] = new BoardTile[1];
		board_tiles[2] = new BoardTile[5];
		board_tiles[3] = new BoardTile[6];
		board_tiles[4] = new BoardTile[7];
		board_tiles[5] = new BoardTile[8];
		board_tiles[6] = new BoardTile[9];
		board_tiles[7] = new BoardTile[8];
		board_tiles[8] = new BoardTile[7];
		board_tiles[9] = new BoardTile[6];
		board_tiles[10] = new BoardTile[5];
		
		temp_arr = (JSONArray) JSON.get("board");
		int i = 0, j = 0;
		for(int k = 0; k<temp_arr.size(); k++) {
			temp = (JSONObject) temp_arr.get(k);
			BoardTile temp_tile = new BoardTile();
			temp_tile.designated_water = Integer.valueOf(String.valueOf(temp.get("designated_water"))) == 1;
			temp_tile.designated_city = Integer.valueOf(String.valueOf(temp.get("designated_city"))) == 1;
			temp_tile.city_adjacent = Integer.valueOf(String.valueOf(temp.get("city_adjacent"))) == 1;
			if(temp.containsKey("type")) 
				temp_tile.type = TileType.valueOf((String) temp.get("type"));
			if(temp.containsKey("name"))
				temp_tile.name = (String) temp.get("name");
			if(temp.containsKey("bonusResources")) {
				ArrayList<String> resources = (ArrayList<String>) temp.get("bonusResources");
				temp_tile.bonusResources = new Resources[resources.size()];
				for(int l = 0; l<resources.size(); l++)
					temp_tile.bonusResources[l] = Resources.valueOf(resources.get(l));
			}
			if(temp.containsKey("owner")) {
				for(Player p: game.players)
					if(p.number == Integer.valueOf(String.valueOf(temp.get("owner")) ))
						temp_tile.owner = p;
			}
			board_tiles[i][j] = temp_tile;
			if(j == board_tiles[i].length - 1) {
				i++;
				j=0;
			} else j++;
		}
		board.board = board_tiles;
		
		game.board = board;
		
		
		
		// load game
		temp = (JSONObject) JSON.get("game");
		game.deck = new LinkedList<Card>();
		game.discard = new LinkedList<Card>();
		ArrayList<String> discard = (ArrayList<String>) temp.get("discard"); 
		for(String e: discard) 
			game.discard.add(findCard(cards, e));
		
		if(prelude) {
			game.prelude_discard = new LinkedList<Card>();
			ArrayList<String> prelude_discard = (ArrayList<String>) temp.get("prelude_discard"); 
			for(String e: prelude_discard) 
				game.prelude_discard.add(findCard(cards, e));
			
			game.preludes = new LinkedList<Card>();
			ArrayList<String> preludes = (ArrayList<String>) temp.get("preludes"); 
			for(String e: preludes) 
				game.preludes.add(findCard(cards, e));
		}
		
		ArrayList<String> deck = (ArrayList<String>) temp.get("deck"); 
		for(String e: deck) 
			game.deck.add(findCard(cards, e));
		
		game.num_new_cards = Integer.valueOf(String.valueOf(temp.get("num_new_cards")));
		Game.quickSetup(Integer.valueOf(String.valueOf(temp.get("quickSetup"))) == 1);
		return game;
	}
	
	private <T> ArrayList<T> addArrayList(ArrayList<T> arr, T[] input) {
		for(T e: input) arr.add(e);
		return arr;
	}
	
	private <T> ArrayList<T> createArrayList(T[] input) {
		ArrayList<T> arr = new ArrayList<T>();
		for(T e: input) arr.add(e);
		return arr;
	}
	
	private Company findCompany(ArrayList<Company> input, String name) {
		for(Company e: input) if(e.name.equals(name)) return e;
		System.err.println("Company " + name + " not found.");
		System.exit(-1);
		return null;
	}
	
	private Card findCard(ArrayList<Card> input, String name) {
		for(Card e: input) if(e.name.equals(name)) return e;
		System.err.println("Card " + name + " not found.");
		System.exit(-1);
		return null;
	}
	
	private String[] toArray(ArrayList<String> input) {
		String[] temp = new String[input.size()];
		for(int i = 0; i<input.size(); i++) temp[i] = input.get(i);
		return temp;
	}
}

class FileManager {
	public FileManager(String filename) {this.filename = filename;}
	
	public JSONObject JSON;
	public String filename;
	
	public void readJSONfile() {
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(filename + ".json")){
			Object obj = jsonParser.parse(reader);
			JSONObject actions = (JSONObject) obj;
			JSON = actions;
			
		} catch(Exception e) {
			System.err.println("\nError in readJSONFile\n");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void writeJSONfile() {
		try {
			File f = new File(filename + ".json");
			FileWriter file = new FileWriter(f);
			file.write(JSON.toString());
			file.close();
		} catch(IOException e) {
			System.err.println("\nError in Main writeJSONFile\n");
			e.printStackTrace();
		}
	}
}
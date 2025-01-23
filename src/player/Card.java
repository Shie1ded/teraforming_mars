package player;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import board.Resources;


public class Card {
	public Tag[] tag;
	public int cost;
	public String name;
	public Counter counter_type;
	public int counter = 0;
	public boolean steal_counter = true;
	
	// variables for card requirements
	public int min_oxygen = 0;
	public int max_oxygen = 14;
	public int min_water = 0;
	public int max_water = 9;
	public int min_temperature = -30;
	public int max_temperature = 8;
	public Tag[] min_tags;
	
	// variables for card abilities
	public Resources[] resources_add;
	public Resources[] resources_remove;
	public Resources[] resources_remove_global;
	public Resources[] resource_generation_add;
	public Resources[] resource_generation_remove;
	public Resources[] resource_generation_remove_global;

	public int points = 0;
	public Ability[] abilities;							// abilities that trigger as you play the card
	public Ability[] activate_abilities;				// abilities that you can trigger whenever
	
	// constructor
	public Card(String name, int cost) {
		this.cost = cost;
		this.name = name.toUpperCase();
	}
	
	// create all the cards in the deck (to start game)
	public static Card[] createDeck(String filename) {
		ArrayList<Card> deck = new ArrayList<Card>();
		
		JSONObject deck_json = CardReader.readJSONfile(filename);
		if(!(deck_json.containsKey("cards"))) {
			System.out.println("Card array not found in json file cards.");
			System.exit(-1);
		}
		JSONArray cards = (JSONArray) deck_json.get("cards");
		for(int i = 0; i<cards.size(); i++) {
			JSONObject temp_card_json = (JSONObject) cards.get(i);
			Card temp_card = new Card((String) temp_card_json.get("name"), Integer.valueOf((String) temp_card_json.get("cost")));
			if(temp_card_json.containsKey("points")) temp_card.points = Integer.valueOf((String) temp_card_json.get("points"));
			if(temp_card_json.containsKey("min_oxygen")) temp_card.min_oxygen = Integer.valueOf((String) temp_card_json.get("min_oxygen"));
			if(temp_card_json.containsKey("max_oxygen")) temp_card.max_oxygen = Integer.valueOf((String) temp_card_json.get("max_oxygen"));
			if(temp_card_json.containsKey("min_water")) temp_card.min_water = Integer.valueOf((String) temp_card_json.get("min_water"));
			if(temp_card_json.containsKey("max_water")) temp_card.max_water = Integer.valueOf((String) temp_card_json.get("max_water"));
			if(temp_card_json.containsKey("min_temperature")) temp_card.min_temperature = Integer.valueOf((String) temp_card_json.get("min_temperature"));
			if(temp_card_json.containsKey("max_temperature")) temp_card.max_temperature = Integer.valueOf((String) temp_card_json.get("max_temperature"));
			if(temp_card_json.containsKey("min_tags")) {
				JSONArray temp_tag_arr = (JSONArray) temp_card_json.get("min_tags");
				temp_card.min_tags = new Tag[temp_tag_arr.size()];
				for(int j = 0; j<temp_tag_arr.size(); j++) {
					temp_card.min_tags[j] = Tag.valueOf((String)temp_tag_arr.get(j));
				}
			}
			if(temp_card_json.containsKey("counter_type")) {
				temp_card.counter_type = Counter.valueOf((String) temp_card_json.get("counter_type"));
				if(temp_card_json.containsKey("counter")) temp_card.counter = Integer.valueOf((String) temp_card_json.get("counter"));
				if(temp_card_json.containsKey("steal_counter")) temp_card.steal_counter = Boolean.valueOf((String) temp_card_json.get("steal_counter"));
			}
			if(temp_card_json.containsKey("abilities")) {
				JSONArray temp_ability_arr = (JSONArray) temp_card_json.get("abilities");
				temp_card.abilities = new Ability[temp_ability_arr.size()];
				for(int j = 0; j<temp_ability_arr.size(); j++) {
					temp_card.abilities[j] = Ability.valueOf((String)temp_ability_arr.get(j));
				}
			}
			if(temp_card_json.containsKey("activate_abilities")) {
				JSONArray temp_ability_arr = (JSONArray) temp_card_json.get("activate_abilities");
				temp_card.activate_abilities = new Ability[temp_ability_arr.size()];
				for(int j = 0; j<temp_ability_arr.size(); j++) {
					temp_card.activate_abilities[j] = Ability.valueOf((String)temp_ability_arr.get(j));
				}
			}
			if(temp_card_json.containsKey("tag")) {
				if(temp_card_json.containsKey("tag")) {
					JSONArray temp_tag_arr = (JSONArray) temp_card_json.get("tag");
					temp_card.tag = new Tag[temp_tag_arr.size()];
					for(int j = 0; j<temp_tag_arr.size(); j++) {
						temp_card.tag[j] = Tag.valueOf((String)temp_tag_arr.get(j));
					}
				}
			}
			if(temp_card_json.containsKey("resources_add")) {
				JSONArray temp_resources_arr = (JSONArray) temp_card_json.get("resources_add");
				temp_card.resources_add = new Resources[temp_resources_arr.size()];
				for(int j = 0; j<temp_resources_arr.size(); j++) {
					temp_card.resources_add[j] = Resources.valueOf((String)temp_resources_arr.get(j));
				}
			}
			if(temp_card_json.containsKey("resources_remove")) {
				JSONArray temp_resources_arr = (JSONArray) temp_card_json.get("resources_remove");
				temp_card.resources_remove = new Resources[temp_resources_arr.size()];
				for(int j = 0; j<temp_resources_arr.size(); j++) {
					temp_card.resources_remove[j] = Resources.valueOf((String)temp_resources_arr.get(j));
				}
			}
			if(temp_card_json.containsKey("resources_remove_global")) {
				JSONArray temp_resources_arr = (JSONArray) temp_card_json.get("resources_remove_global");
				temp_card.resources_remove_global = new Resources[temp_resources_arr.size()];
				for(int j = 0; j<temp_resources_arr.size(); j++) {
					temp_card.resources_remove_global[j] = Resources.valueOf((String)temp_resources_arr.get(j));
				}
			}
			if(temp_card_json.containsKey("resource_generation_add")) {
				JSONArray temp_resource_generation_arr = (JSONArray) temp_card_json.get("resource_generation_add");
				temp_card.resource_generation_add = new Resources[temp_resource_generation_arr.size()];
				for(int j = 0; j<temp_resource_generation_arr.size(); j++) {
					temp_card.resource_generation_add[j] = Resources.valueOf((String)temp_resource_generation_arr.get(j));
				}
			}
			if(temp_card_json.containsKey("resource_generation_remove")) {
				JSONArray temp_resource_generation_arr = (JSONArray) temp_card_json.get("resource_generation_remove");
				temp_card.resource_generation_remove = new Resources[temp_resource_generation_arr.size()];
				for(int j = 0; j<temp_resource_generation_arr.size(); j++) {
					temp_card.resource_generation_remove[j] = Resources.valueOf((String)temp_resource_generation_arr.get(j));
				}
			}
			if(temp_card_json.containsKey("resource_generation_remove_global")) {
				JSONArray temp_resource_generation_arr = (JSONArray) temp_card_json.get("resource_generation_remove_global");
				temp_card.resource_generation_remove_global = new Resources[temp_resource_generation_arr.size()];
				for(int j = 0; j<temp_resource_generation_arr.size(); j++) {
					temp_card.resource_generation_remove_global[j] = Resources.valueOf((String)temp_resource_generation_arr.get(j));
				}
			}
			deck.add(temp_card);
		}
		Card[] return_deck = new Card[deck.size()];
		return_deck = deck.toArray(return_deck);
		return return_deck;
	}

	// print all the variables of the card
	public void print() {
		System.out.println("Card: " + name);
		System.out.println("\tCost: \t\t\t\t" + cost);
		System.out.print("\tTags: \t\t\t\t");
		if(tag != null) {
			for(Tag i: tag) System.out.print(i.toString() + " ");
			System.out.println();
		}
		else System.out.println("None");
		
		System.out.print("\tRequirements:");
		if(min_oxygen != 0) System.out.println("\t\t\tmin_oxygen: " + min_oxygen);
		else if(max_oxygen != 14) System.out.println("\t\t\tmax_oxygen: " + max_oxygen);
		else if(min_water != 0) System.out.println("\t\t\tmin_water: " + min_water);
		else if(max_water != 9) System.out.println("\t\t\tmax_water: " + max_water);
		else if(min_temperature != -30) System.out.println("\t\t\tmin_temperature: " + min_temperature);
		else if(max_temperature != 8) System.out.println("\t\t\tmax_temperature: " + max_temperature);
		else if(min_tags != null) {
			System.out.print("\t\t\tmin_tags: ");
			for(Tag i: min_tags) System.out.print(i.toString() + " ");
			System.out.println();
		}
		else System.out.println("\t\t\tNone");
		
		if(resources_add != null) {
			System.out.print("\tResources (+): \t\t\t");
			for(Resources i: resources_add) System.out.print(i.toString() + " ");
			System.out.println();
		}
		if(resources_remove != null) {
			System.out.print("\tResources (-): \t\t\t");
			for(Resources i: resources_remove) System.out.print(i.toString() + " ");
			System.out.println();
		}
		if(resources_remove_global != null) {
			System.out.print("\tResources (glob): \t\t");
			for(Resources i: resources_remove_global) System.out.print(i.toString() + " ");
			System.out.println();
		}
		if(resource_generation_add != null) {
			System.out.print("\tResource Generation (+): \t");
			for(Resources i: resource_generation_add) System.out.print(i.toString() + " ");
			System.out.println();
		}
		if(resource_generation_remove != null) {
			System.out.print("\tResource Generation (-): \t");
			for(Resources i: resource_generation_remove) System.out.print(i.toString() + " ");
			System.out.println();
		}
		if(resource_generation_remove_global != null) {
			System.out.print("\tResource Generation (glob): \t");
			for(Resources i: resource_generation_remove_global) System.out.print(i.toString() + " ");
			System.out.println();
		}
		System.out.print("\tInstant Abilities: \t\t");
		if(abilities != null) {
			for(Ability i: abilities) System.out.print(i.toString() + " ");
			System.out.println();
		} else System.out.println("None");
		if(activate_abilities != null) {
			System.out.print("\tActivated Abilities: \t\t");
			if(activate_abilities != null) {
				for(Ability i: activate_abilities) System.out.print(i.toString() + " ");
				System.out.println();
			} else System.out.println("None");
		}
		if(counter_type != null) {
			System.out.println("\tResource Counter Type: \t\t" + counter_type); 
			System.out.println("\tCounter:\t\t\t" + counter);
			System.out.println("\tRemoveable: \t\t\t" + steal_counter);
		}
		if(points != 0) System.out.println("\tPoints: \t\t\t" + points);
	}
	
	public Resources[] getPayableResources() {
		ArrayList<Resources> resources = new ArrayList<Resources>();
		resources.add(Resources.MegaCredit);
		if(tag != null && tag.length > 0) {
			for(Tag e: tag) {
				if(e.equals(Tag.Building)) resources.add(Resources.Steel);
				else if(e.equals(Tag.Space)) resources.add(Resources.Titanium);
			}
		}
		Resources[] all_resources = new Resources[resources.size()];
		all_resources = resources.toArray(all_resources);
		return all_resources;
	}
}

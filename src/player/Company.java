package player;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import board.Resources;

public class Company {
	public Tag[] tag;
	public String name;
	public int megacredits;
	public Resources[] resources;
	public Resources[] resource_generation;
	public Ability[] abilities;
	
	public Player owner;
	
	// constructor
	private Company(String name, int megacredits) {
		this.name = name;
		this.megacredits = megacredits;
	}
	
	// create all the companies (to start game)
	public static Company[] createDeck(String filename) {
		ArrayList<Company> deck = new ArrayList<Company>();
		
		JSONObject deck_json = CardReader.readJSONfile(filename);
		if(!(deck_json.containsKey("companies"))) {
			System.out.println("Company array not found in json file cards.");
			System.exit(-1);
		}
		JSONArray companies = (JSONArray) deck_json.get("companies");
		for(int i = 0; i<companies.size(); i++) {
			JSONObject temp_company_json = (JSONObject) companies.get(i);
			Company temp_company = new Company((String) temp_company_json.get("name"), Integer.valueOf((String) temp_company_json.get("megacredits")));
			
			if(temp_company_json.containsKey("tag")) {
				JSONArray temp_tag_arr = (JSONArray) temp_company_json.get("tag");
				temp_company.tag = new Tag[temp_tag_arr.size()];
				for(int j = 0; j<temp_tag_arr.size(); j++) {
					temp_company.tag[j] = Tag.valueOf((String)temp_tag_arr.get(j));
				}
			}
			if(temp_company_json.containsKey("resources")) {
				JSONArray temp_resources_arr = (JSONArray) temp_company_json.get("resources");
				temp_company.resources = new Resources[temp_resources_arr.size()];
				for(int j = 0; j<temp_resources_arr.size(); j++) {
					temp_company.resources[j] = Resources.valueOf((String)temp_resources_arr.get(j));
				}
			}
			if(temp_company_json.containsKey("resource_generation")) {
				JSONArray temp_resource_generation_arr = (JSONArray) temp_company_json.get("resource_generation");
				temp_company.resource_generation = new Resources[temp_resource_generation_arr.size()];
				for(int j = 0; j<temp_resource_generation_arr.size(); j++) {
					temp_company.resource_generation[j] = Resources.valueOf((String)temp_resource_generation_arr.get(j));
				}
			}
			if(temp_company_json.containsKey("abilities")) {
				JSONArray temp_abilities_arr = (JSONArray) temp_company_json.get("abilities");
				temp_company.abilities = new Ability[temp_abilities_arr.size()];
				for(int j = 0; j<temp_abilities_arr.size(); j++) {
					temp_company.abilities[j] = Ability.valueOf((String)temp_abilities_arr.get(j));
				}
			}
			deck.add(temp_company);
		}
		
		Company[] return_deck = new Company[deck.size()];
		return_deck = deck.toArray(return_deck);
		return return_deck;
	}
	
	public void loadInitialStats() {
		if(resources != null) for(Resources resource: resources) owner.increaseResource(resource, 1);
		if(resource_generation != null) for(Resources resource: resource_generation) owner.increaseResourceGeneration(resource, 1);
		owner.megacredits += megacredits;
		if(abilities != null) 
			for(Ability a: abilities)
				owner.game.abilities.activate(owner, a);	
	}
	
	// test print
	public void print() {
		System.out.println("Company: " + name);
		System.out.println("\tStarting Megacredits: \t" + megacredits);
		
		System.out.print("\tTags: \t\t\t");
		if(tag != null) {
			for(Tag i: tag) System.out.print(i.toString() + " ");
			System.out.println();
		}
		else System.out.println("None");
		
		System.out.print("\tResources: \t\t");
		if(resources != null) {
			for(Resources i: resources) System.out.print(i.toString() + " ");
			System.out.println();
		}
		else System.out.println("None");
		
		System.out.print("\tResource Generation: \t");
		if(resource_generation != null) {
			for(Resources i: resource_generation) System.out.print(i.toString() + " ");
			System.out.println();
		}
		else System.out.println("None");
		
		
		System.out.print("\tAbilities: \t\t");
		if(abilities != null) {
			for(Ability i: abilities) System.out.print(i.toString() + " ");
			System.out.println();
		} else System.out.println("None");
		if(owner != null) System.out.println("\tBelonging to player " + owner.number);
	}
		
	public static Company createDefaultCompany() {
		Company temp = new Company("beginner corp", 42);
		temp.resources = new Resources[10];
		for(int i = 0; i<temp.resources.length; i++) temp.resources[i] = Resources.Card;
		return temp;
	}
	
	
}

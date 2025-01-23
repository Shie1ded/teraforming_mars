package player;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// File should be finished

public class CardReader {
	
	private static JSONObject JSON_in = new JSONObject();
	
	public static JSONObject getObject() { return JSON_in; }
	
	public static JSONObject readJSONfile(String filename) {
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(filename + ".json")){
			Object obj = jsonParser.parse(reader);
			JSONObject actions = (JSONObject) obj;
			JSON_in = actions;
			return JSON_in;
			
		} catch(Exception e) {
			System.err.println("\nError in readJSONFile\n");
			e.printStackTrace();
			System.exit(-1);
			return null;
		}
	}
}

package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import board.Resources;
import main.Game;

public interface ReusableAbilityFunctions {

	// pay for a card if the player has enough money / other resources
	static public boolean pay(Player player, String name, int cost) {
		Resources[] money = { Resources.MegaCredit };
		if (!player.hasMoney(money, cost)) {
			System.err.println(name + " costs " + cost + " MegaCredits. You can't afford it.");
			return false;
		}
		player.purchaseWithResource(money, cost);
		return true;
	}

	// get the number of tags of a type of tag from a player
	static public int getNumTags(Player player, Tag tag) {
		Tag[] tags = player.getAllTags();
		if (tags == null || tags.length == 0)
			return 0;
		HashMap<Tag, Integer> all_tags = player.game.groupTags(tags);
		Set<Entry<Tag, Integer>> set = all_tags.entrySet();
		Iterator<Entry<Tag, Integer>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<Tag, Integer> mentry = (Map.Entry<Tag, Integer>) it.next();
			if (mentry.getKey().equals(tag))
				return mentry.getValue();
		}
		return 0;
	}

	// get the number of a type of tag from all players
	static public int getNumTagsAll(Player player, Tag tag) {
		int total = 0;
		for (Player e : player.game.players)
			getNumTags(e, tag);
		return total;
	}
	
	// increase the resource (counter) on a card
	static public void increaseCounter(Player player, String cardname) {
		for(Card e: player.played) if(e.name != null && e.name.equals(cardname)) {
			if(e.counter_type == null) {
				System.err.println("You are trying to put a counter on a card that does not accept counters.");
				System.exit(-1);
			}
			e.counter++;
		}
	}
	
	// decrease the resource (counter) on a card
	static public void decreaseCounter(Card card, int number) {
		if(card.counter < number) {
			System.err.println("You are trying to remove more resources than this card has.");
			System.exit(-1);
		}
		card.counter -= number;
	}
	
	// return whether a card has a counter
	static public boolean hasCounter(Player player, Counter counter) {
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(counter)) return true;
		return false;
	}
	
	// choose which card to add a counter to
	static public void chooseCounter(Player player, Counter counter, int number) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(counter)) cards.add(e);
		int choice = 0;
		if(cards.size() > 1) {
			System.out.println("Cards with counters:");
			for(int i = 0; i<cards.size(); i++) {
				System.out.println("\t[" + (i+1) + "] " + cards.get(i).name);
			}
			choice = player.game.getUserInt("Choice", "Not a valid number.", 1, cards.size()) - 1;
		}
		for(int i = 0; i < number; i++) ReusableAbilityFunctions.increaseCounter(player,cards.get(choice).name);
	}
	
	// can you remove a counter from this other player
	static public boolean canRemoveCounter(Player player, Counter counter) {
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(counter) && e.steal_counter && e.counter > 0) return true;
		return false;
	}
	
	// choose counter to remove
	static public void removeCounter(Player player, Counter counter, int number) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(counter) && e.steal_counter && e.counter > 0) cards.add(e);
		int choice = 0;
		if(cards.size() > 1) {
			System.out.println("Cards with counters:");
			for(int i = 0; i<cards.size(); i++) {
				System.out.println("\t[" + (i+1) + "] " + cards.get(i).name);
			}
			choice = player.game.getUserInt("Choice", "Not a valid number.", 1, cards.size()) - 1;
		}
		for(int i = 0; i < number && cards.get(choice).counter > 0; i++) ReusableAbilityFunctions.decreaseCounter(cards.get(i), 1);
	}
	
	// get the number of counters on a card
	static public int numCounter(Player player, String cardname) {
		for(Card e: player.played) if(e.name != null && e.name.equals(cardname)) {
			if(e.counter_type == null) {
				System.err.println("This card does not have a counter on it");
				return 0;
			}
			return e.counter;
		}
		return 0;
	}
	
	// reset counters on a card
	static public void removeCounters(Player player, String cardname) {
		for(Card e: player.played) if(e.name != null && e.name.equals(cardname)) {
			if(e.counter_type == null) {
				System.err.println("This card does not have a counter on it");
				return;
			}
			e.counter = 0;
		}
	}
	
	// draw a number of cards with a specific tag
	static public void drawTagCards(Player player, Tag tag, int number) {
		Game game = player.game;
		int num_added = 0;
		boolean shuffled = false;
		while(true) {
			while(game.deck.size() > 0) {
				if(num_added == number) return;
				boolean added = false;
				Card temp = game.deck.remove();
				if(temp.tag != null)
					for(Tag t: temp.tag)
						if(t.equals(tag)) {
							added = true;
							temp.print();
							player.hand.add(temp);
							num_added++;
							shuffled = false;
						}
				if(!added) game.discard.add(temp);
			}
			if(!game.canDrawCard(1)) return;
			if(shuffled) return;
			shuffled = true;
		}
	}
}

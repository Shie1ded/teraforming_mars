package player;

import java.util.Scanner;

import board.Resources;

public interface PermanentAbilities {
	public static Card pipeCardBefore(Player player, Card card) {
		if(player.permanent_abilities == null) return card;
		if(player.permanent_abilities.size() == 0) return card;
		for(int i = 0; i<player.permanent_abilities.size(); i++) {
			switch(player.permanent_abilities.get(i)) {
			case Global_ParameterTemp:
				player.permanent_abilities.remove(i);
				i--;
			case Global_Parameter:
				card = globalParameter(card);
				break;
			case Global_ParameterTemp2:
				player.permanent_abilities.remove(i);
				i--;
				card = globalParameterRemove(card);
				break;
			case GlobalDiscount:
				card = globalDiscount(card);
				break;
			case GlobalDiscount2:
				card = globalDiscount2(card);
				break;
			case SpaceDiscount:
				card = spaceDiscount(card);
				break;
			case EarthDiscount:
				card = earthDiscount(card);
				break;
			case DiscountTemp:
				player.permanent_abilities.remove(i);
				i--;
				card = discountTemp(card);
				break;
			case DiscountTemp2:
				player.permanent_abilities.remove(i);
				i--;
				card = discountTemp2(card);
				break;
			case Thorgate:
				card = powerDiscount(card);
				break;
			case Psychrophiles:
				card = psychrophiles(player, card);
				break;
			case BuildingDiscount:
				card = buildingDiscount(card);
				break;
			case ScienceDiscount:
				card = scienceDiscount(card);
				break;
			default:
				continue;
			}
		}
		return card;
	}
	
	public static Card undoPipeCardBefore(Player player, Card card) {
		if(player.permanent_abilities == null) return card;
		if(player.permanent_abilities.size() == 0) return card;
		for(Ability a: player.permanent_abilities) {
			switch(a) {
			case Global_Parameter:
				card = undoGlobalParameter(card);
				break;
			case GlobalDiscount:
				card = undoGlobalDiscount(card);
				break;
			case GlobalDiscount2:
				card = undoGlobalDiscount2(card);
				break;
			case SpaceDiscount:
				card = undoSpaceDiscount(card);
				break;
			case EarthDiscount:
				card = undoEarthDiscount(card);
				break;
			case Thorgate:
				card = undoPowerDiscount(card);
				break;
			case Psychrophiles:
				card = undoPsychrophiles(player, card);
				break;
			case BuildingDiscount:
				card = undoBuildingDiscount(card);
				break;
			case ScienceDiscount:
				card = undoScienceDiscount(card);
				break;
			default:
				continue;
			}
		}
		return card;
	}
	
	public static Card pipeCardAfter(Scanner reader, Player player, Card card) {
		if(player.permanent_abilities == null) return card;
		if(player.permanent_abilities.size() == 0) return card;
		for(Ability a: player.permanent_abilities) {
			switch(a) {
			case BlockedCounters:
				if(card.counter_type != null && (card.counter_type.equals(Counter.Animal) || card.counter_type.equals(Counter.Microbe)))
					card.steal_counter = false;
				break;
			case SpaceEventMegaCredit:
				if(SpaceEventMegaCredit(card)) {
					player.increaseResource(Resources.MegaCredit, 3);
					player.increaseResource(Resources.Heat, 3);
				}
				break;
			case EventMegaCredit:
				if(hasTag(card, Tag.Event)) 
					for(Tag t: card.tag) 
						if(t.equals(Tag.Event))
							player.increaseResource(Resources.MegaCredit, 3);
				break;
			case EventMegaCredit2:
				if(hasTag(card, Tag.Event)) 
					for(Tag t: card.tag) 
						if(t.equals(Tag.Event))
							player.increaseResource(Resources.MegaCredit, 2);
				break;
			case ScienceDrawCard:
				if(hasTag(card, Tag.Science)) 
					for(Tag t: card.tag) 
						if(t.equals(Tag.Science) && player.game.canDrawCard(1))
							scienceDrawCard(player);
				break;
			case ScienceDiscardCard:
				if(hasTag(card, Tag.Science)) 
					for(Tag t: card.tag) 
						if(t.equals(Tag.Science))
							optionalDiscard(reader, player);
				break;
			case Place_Ecological_Zone:
				if(hasTag(card, Tag.Animal) || hasTag(card, Tag.Plant)) 
					for(Tag t: card.tag) 
						if(t.equals(Tag.Animal) || t.equals(Tag.Plant))
							ReusableAbilityFunctions.increaseCounter(player, "ECOLOGICAL ZONE");
				break;
			case Decomposers:
				if(hasTag(card, Tag.Animal) || hasTag(card, Tag.Plant) || hasTag(card, Tag.Microbe))
					for(Tag t: card.tag) 
						if(t.equals(Tag.Animal) || t.equals(Tag.Plant) || t.equals(Tag.Microbe))
							ReusableAbilityFunctions.increaseCounter(player, "DECOMPOSERS");
				break;
			case ViralEnhancers:
				if(hasTag(card, Tag.Animal) || hasTag(card, Tag.Plant) || hasTag(card, Tag.Microbe))
					for(Tag t: card.tag) 
						if(t.equals(Tag.Animal) || t.equals(Tag.Plant) || t.equals(Tag.Microbe))
							viralEnhancres(player, card);
				break;
			case Psychrophiles:
				if(hasTag(card, Tag.Plant)) 
					for(Tag t: card.tag) 
						if(t.equals(Tag.Plant))
							ReusableAbilityFunctions.removeCounters(player, "PSYCHROPHILES");
				break;
			case PointLuna:
				if(hasTag(card, Tag.Earth)) 
					for(Tag t: card.tag) 
						if(t.equals(Tag.Earth) && player.game.canDrawCard(1))
							player.increaseResource(Resources.Card, 1);
				break;
			case Vitor:
				if(card.points > 0 || card.counter_type != null) player.increaseResource(Resources.MegaCredit, 3);
				break;
			default:
				continue;
			}
		}
		if(card.tag != null) for(Tag t: card.tag) pipeTagThrough(player, t);
		return card;
	}
	
	private static void pipeTagThrough(Player player, Tag tag) {
		switch(tag) {
		case Jovian:
			SaturnSystems(player);
			break;
		default:
			;
		}
	}
	
	private static Card globalParameter(Card card) {
		card.max_oxygen += 2;
		card.min_oxygen -= 2;
		card.max_temperature += 4;
		card.min_temperature -= 4;
		card.max_water += 2;
		card.min_water -= 2;
		return card;
	}
	
	private static Card globalParameterRemove(Card card) {
		card.max_oxygen = 14;
		card.min_oxygen = 0;
		card.max_temperature = 8;
		card.min_temperature = -30;
		card.max_water = 9;
		card.min_water = 0;
		return card;
	}

	private static Card undoGlobalParameter(Card card) {
		card.max_oxygen -= 2;
		card.min_oxygen += 2;
		card.max_temperature -= 4;
		card.min_temperature += 4;
		card.max_water -= 2;
		card.min_water += 2;
		return card;
	}

	private static Card globalDiscount(Card card) {
		card.cost -= 2;
		return card;
	}
	
	private static Card undoGlobalDiscount(Card card) {
		card.cost += 2;
		return card;
	}
	
	private static Card globalDiscount2(Card card) {
		card.cost -= 1;
		return card;
	}
	
	private static Card undoGlobalDiscount2(Card card) {
		card.cost += 1;
		return card;
	}
	
	private static Card spaceDiscount(Card card) {
		if(card.tag != null) 
			for(Tag t: card.tag) 
				if(t.equals(Tag.Space))
					card.cost -=2;
		return card;
	}
	
	private static Card undoSpaceDiscount(Card card) {
		if(card.tag != null) 
			for(Tag t: card.tag) 
				if(t.equals(Tag.Space))
					card.cost +=2;
		return card;
	}
	
	private static Card earthDiscount(Card card) {
		if(card.tag != null) 
			for(Tag t: card.tag) 
				if(t.equals(Tag.Earth))
					card.cost -=3;
		return card;
	}
	
	private static Card undoEarthDiscount(Card card) {
		if(card.tag != null) 
			for(Tag t: card.tag) 
				if(t.equals(Tag.Earth))
					card.cost +=3;
		return card;
	}
	
	private static Card powerDiscount(Card card) {
		if(card.tag != null) 
			for(Tag t: card.tag) 
				if(t.equals(Tag.Power))
					card.cost -=3;
		return card;
	}
	
	private static Card undoPowerDiscount(Card card) {
		if(card.tag != null) 
			for(Tag t: card.tag) 
				if(t.equals(Tag.Power))
					card.cost +=3;
		return card;
	}
	
	private static Card psychrophiles(Player player, Card card) {
		if(card.tag != null)
			for(Tag t: card.tag)
				if(t.equals(Tag.Plant))
					card.cost -= 2 * ReusableAbilityFunctions.numCounter(player, "PSYCHROPHILES");
		return card;
	}
	
	private static Card undoPsychrophiles(Player player, Card card) {
		if(card.tag != null)
			for(Tag t: card.tag)
				if(t.equals(Tag.Plant))
					card.cost += 2 * ReusableAbilityFunctions.numCounter(player, "PSYCHROPHILES");
		return card;
	}
	
	private static Card buildingDiscount(Card card) {
		if(card.tag != null) 
			for(Tag t: card.tag) 
				if(t.equals(Tag.Building))
					card.cost -=2;
		return card;
	}
	
	private static Card undoBuildingDiscount(Card card) {
		if(card.tag != null) 
			for(Tag t: card.tag) 
				if(t.equals(Tag.Building))
					card.cost +=2;
		return card;
	}
	
	private static Card scienceDiscount(Card card) {
		if(card.tag != null) 
			for(Tag t: card.tag) 
				if(t.equals(Tag.Science))
					card.cost -=2;
		return card;
	}
	
	private static Card undoScienceDiscount(Card card) {
		if(card.tag != null) 
			for(Tag t: card.tag) 
				if(t.equals(Tag.Science))
					card.cost +=2;
		return card;
	}

	private static boolean SpaceEventMegaCredit(Card card) {
		boolean hasEvent = false;
		boolean hasSpace = false;
		if(card.tag != null)
			for(Tag t: card.tag) {
				if(t.equals(Tag.Space)) hasSpace = true;
				else if(t.equals(Tag.Event)) hasEvent = true;
			}
		if(hasEvent && hasSpace) return true;
		return false;
	}
	
	private static boolean hasTag(Card card, Tag tag) {
		if(card.tag != null)
			for(Tag t: card.tag)
				if(t.equals(tag)) 
					return true;
		return false;
	}
	
	private static void scienceDrawCard(Player player) {
		System.out.println("HIT");
		for(Card e: player.played) if(e.name != null && e.name.equals("OLYMPUS CONFERENCE")) {
			if(e.counter > 0 && player.game.canDrawCard(1)) {
				player.increaseResource(Resources.Card, 1);
				ReusableAbilityFunctions.decreaseCounter(e, 1);
			} else ReusableAbilityFunctions.increaseCounter(player, "OLYMPUS CONFERENCE");
		}
	}
	
	private static void optionalDiscard(Scanner reader, Player player) {
		if(player.hand.size() < 2) return;
		while (true) {
			player.printHand();
			System.out.print("\nDo you want to discard a card (y/n)?\t");
			try {
				String in = reader.nextLine();
				in = in.trim().toLowerCase();
				if (in.equals("y") || in.equals("yes")) {
					while (true) {
						System.out.print("Which card would you like to discard?\t");
						try {
							in = reader.nextLine();
							System.out.println();
							int n = Integer.valueOf(in);
							if (n < 1 || n > player.hand.size()) {
								System.out.println("Please choose a value between 1 and " + (player.hand.size()));
								continue;
							}
							if(player.hand.get(n-1).name.equals("MARS UNIVERSITY")) {
								System.out.println("You cannot discard this card.");
								continue;
							}
							player.game.discard.add(player.hand.remove(n-1));
							player.increaseResource(Resources.Card, 1);
							return;
						} catch (Exception e) {
							System.err.println("Invalid. Please insert a valid number");
						}
					}
				} else if (in.equals("n") || in.equals("no")) {
					return;
				} else {
					System.err.print("\nNot a valid input.");
				}
			} catch (Exception e) {
				System.err.println("Error, invalid entry.");
				continue;
			}
		}
	}
	
	public static void viralEnhancres(Player player, Card card) {
		if((hasTag(card, Tag.Microbe) || hasTag(card, Tag.Animal)) && card.counter_type != null) 
			ReusableAbilityFunctions.increaseCounter(player, card.name);
		else if(hasTag(card, Tag.Plant) || hasTag(card, Tag.Microbe) || hasTag(card, Tag.Animal)) 
			player.increaseResource(Resources.Plant, 1);
	}
	
	public static Card discountTemp(Card card) {
		card.cost -= 8;
		return card;
	}
	
	public static Card discountTemp2(Card card) {
		card.cost -= 25;
		return card;
	}
	
	public static void DiscountProjects (Player player) {
		if(player.permanent_abilities != null) 
			for(Ability a: player.permanent_abilities) 
				if(a.equals(Ability.DiscountProjects)) 
					player.increaseResource(Resources.MegaCredit, 3);
	}
	
	public static void AddHerbivore(Player player) {
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.Herbivores))
					ReusableAbilityFunctions.increaseCounter(player, "HERBIVORES");
	}
	
	public static void GlobalWaterPlaced(Player[] players) {
		for(Player p: players) 
			if(p.permanent_abilities != null)
				for(Ability a: p.permanent_abilities)
					if(a.equals(Ability.ArcticAlgae))
						p.increaseResource(Resources.Plant, 2);
	}
	
	public static void GlobalCityPlaced(Player[] players) {
		for(Player p: players) 
			if(p.permanent_abilities != null)
				for(Ability a: p.permanent_abilities)
					switch(a) {
					case RoverConstruction:
						p.increaseResource(Resources.MegaCredit, 2);
						break;
					case Pets:
						ReusableAbilityFunctions.increaseCounter(p, "PETS");
						break;
					case ImmigrantCity:
						p.increaseResourceGeneration(Resources.MegaCredit, 1);
						break;
					default:
						continue;
					}
	}
	
	public static void YourCityPlaced(Player player) {
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.Tharsis))
					player.increaseResource(Resources.MegaCredit, 3);
	}
	
	public static int valuableResources(Player player) {
		int num_increase = 0;
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.ValuableResources))
					num_increase++;
		return num_increase;
	}
	
	public static Resources payWithOtherResource(Player player) {
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.PayWithHeat))
					return Resources.Heat;
		return null;
	}
	
	public static void Credicor(Player player) {
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.Credicor))
					player.increaseResource(Resources.MegaCredit, 4);
	}
	
	public static boolean Ecoline(Player player) {
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.Ecoline))
					return true;
		return false;
	}
	
	public static void Mining(Player player) {
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.Mining))
					player.increaseResourceGeneration(Resources.Steel, 1);
	}
	
	public static boolean Thorgate(Player player) {
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.Thorgate))
					return true;
		return false;
	}
	
	public static boolean Manutech(Player player) {
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.Manutech))
					return true;
		return false;
	}
	
	public static boolean Viron(Player player) {
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.Viron))
					return true;		
		return false;
	}
	
	public static boolean OuterResearch(Player player) {
		if(player.permanent_abilities != null)
			for(Ability a: player.permanent_abilities)
				if(a.equals(Ability.OuterResearch))
					return true;		
		return false;
	}
	
	public static Ability companyAbility(Player player) {
		if(player.company.abilities != null)
			for(Ability a: player.company.abilities)
				switch(a) {
				case UNMI:
					return a;
				case Robinson:
					return a;
				case AddFloater:
					return a;
				case Heat_to_Resource:
					return a;
				default:
					continue;
				}
		return null;
	}
	
	public static int cardPurchasePrice(Player player) {
		int cost = 3;
		if(player.company.abilities != null)
			for(Ability a: player.company.abilities)
				switch(a) {
				case Polyphemos:
					cost += 2;
					break;
				case Terralabs:
					cost -= 2;
				default:
					continue;
				}
		return cost;
	}
	
	public static void SaturnSystems(Player player) {
		for(Player p: player.game.players)
			if(p.permanent_abilities != null)
				for(Ability a: p.permanent_abilities)
					if(a.equals(Ability.SaturnSystem))
						p.increaseResourceGeneration(Resources.MegaCredit, 1);
	}
}

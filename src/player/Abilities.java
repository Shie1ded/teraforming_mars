package player;

import java.util.ArrayList;
import java.util.Scanner;

import board.BoardTile;
import board.Resources;
import board.TileType;
import main.Game;

public class Abilities {
	private Game game;
	private Scanner reader;

	public Abilities(Game game) {
		this.reader = game.reader;
		this.game = game;
	}

	// -----------------------------------------------
	// -------------- Overall functions --------------
	// -----------------------------------------------

	public void describe() {
		System.out.print("Which ability would you like to describe (please type, case-sensitive)): ");
		String in = reader.nextLine();
		String message = describe(Ability.valueOf(in));
		System.out.println(message);
	}

	// describes what the ability does
	public static String describe(Ability ability) {
		switch (ability) {

		// Parameter abilities
		case Temperature:
			return "If the temperature is not maxed (8˚C), increase the temperature by 1 (2˚C) and increase your terraform rating by 1.";
			
		case Water:
			return 
					"If all water has not been placed, place 1 water and increase your terraform rating by 1.";
			
		case Oxygen:
			return 
					"If the oxygen is not maxed (14%), increase the oxygen by 1% and increase your terraform rating by 1.";
			
		case Terraform_Rating:
			return 
					"Increase your terraform rating by 1. This will give you 1 point at the end of the game, and will give you 1 MegaCredit every generation.";

			// General abilities
		case Place_City:
			return 
					"If there is space on the board, place a City. You gain 1 point at the end of the game for each Greenery adjacent to your City";
			
		case Place_Greenery:
			return 
					"If there is free land space on the board, place a Greenery on a valid space on the board. If you have a City with a free adjacent space, you must place the greenery next to the City. If oxygen is not maxed (14%), increase the oxygen and increase your terraform rating by 1.";
			

		// Standard project abilities
		case Sell_Patent:
			return "Discard cards in your hand for 1 MegaCredit each.";
			
		case Power_Plant:
			return "Increases your energy generation by 1 for 11 MegaCredits";
			
		case Asteroid:
			return 
					"If the temperature is not maxed (8˚C), increases the temperature by 1 (2˚C) for 14 MegaCredits. Increases your terraform rating by 1.";
			
		case Aquifier:
			return 
					"If all water has not been placed, place 1 water for 18 MegaCredits. Increases your terraform rating by 1.";
			
		case Greenery:
			return 
					"If there is free land space on the board, place a Greenery on a valid space on the board for 23 MegaCredits. If you have a city with a free adjacent space, you must place the Greenery next to the City. If oxygen is not maxed (14%), increase the oxygen and increase your terraform rating by 1.";
			
		case City:
			return 
					"If there is free land space on the board, place a City on a valid space on the board for 25 MegaCredits. Increases MegaCredit generation by 1.";
			

		// Inventory abilities
		case Convert_Greenery:
			return 
					"If you have 8 Plant units, and there is free land space on the board, place a Greenery on a valid space on the board. If oxygen is not maxed (14%), increase the oxygen and increase your terraform rating by 1.";
			
		case Convert_Temperature:
			return 
					"If you have 8 Heat units, and the temperature is not maxed (8˚C), increases the temperature by 1 (2˚C). Increases your terraform rating by 1.";
			

		// Custom placement abilities
		case Place_Noctis_City:
			return 
					"Place a city tile at the spot designated for Noctis City, ignoring all other placement rules.";
			
		case Place_Phobos_Space_Haven:
			return 
					"Place a city tile at the spot designated for Phobos Space Haven, ignoring all other placement rules.";
			
		case Place_Ganymede_Colony:
			return 
					"Place a city tile at the spot designated for Ganymede Colony, ignoring all other placement rules.";
			
		case Place_Mine:
			return 
					"Place a custom tile on any space with a Steel or Titanium placement bonus. Increase the corresponding resource generation.";
			
		case Place_Nuclear_Zone:
			return "Place a custom tile on any space on the board, following normal placement rules.";
			
		case Place_Restricted_Area:
			return 
					"Place a restriction tile anywhere on the board, and once a round you are able to buy a card for 2 MegaCredits";
			
		case Place_Volcano:
			return 
					"Place a custom tile on one of four preset locations, if they are available (Tharsis Tholus, Ascraeus Mons, Pavonis Mons, Arsia Mons)";
			
		case Place_Volcano_City:
			return 
					"Place a City tile on one of four preset locations, if they are available (Tharsis Tholus, Ascraeus Mons, Pavonis Mons, Arsia Mons)";
			
		case Place_Commercial_District:
			return 
					"Place a custom tile on any valid land space. At the end of the game, earn 1 point for each adjacent city tile.";
			
		case Place_Capital:
			return 
					"Place a custom tile on any valid land space. At the end of the game, earn 1 point for each adjacent water tile.";
			
		case Place_Ecological_Zone:
			return "Requires that you have a Greenery. Place a custom tile on a valid land space next to any greenery. Whenever you play a card with an Animal or Plant tag, add a counter to this card.";
			
		case Place_Industrial_Center:
			return "Place a custom tile on a valid land space next to any city.";
			
		case Place_Urbanized_Area:
			return "Place a City tile on a valid land space next to at least 2 other City tiles";
			
		case Place_Mohole_Area:
			return "Place a custom tile on an area reserved for ocean and gain +4 heat production";
			
		case Place_Flooding:
			return "Place an ocean tile, if there are tiles adjacent to this tile you may remove 4 Megacredits from the owner of one of the tiles.";
			
		case Place_Blocker:
			return "Place a dead tile on any valid land space on the board, blocking opponents from playing a tile. Gain placement bonus, if any.";
			
		case Place_Natural_Preserve:
			return "Place a custom tile a valid land space next to no other tile";
			
		case Place_Research_Outpost:
			return "Place a City tile on a valid land space next to no other tile";
			
		case Place_Artificial_Lake:
			return "Place a Water tile on a valid land space.";
			
		case Place_Protected_Valley:
			return "Place a greenry tile on an reserved ocean space";
			
		case Place_Mangrove:
			return "Place a greenry tile on an reserved ocean space";
			
		case Water_On_Land:
			return "If all water has not been placed, place a water tile on any land space.";
			
		case Land_On_Water:
			return "Place a Greenery on a spot reserved for water, ignoring normal placement rules.";
			

		case Plant_for_City:
			return "Gain 1 Plant resource for every City in play (all Cities of all players).";
			
		case MegaCredit_for_City:
			return 
					"Increase your MegaCredit generation by 1 for every City in play on Mars (all Cities of all players, except for the two designated Colonies).";
			
		case Energy_for_City:
			return 
					"Increase your Energy generation by 1 for every City in play (all Cities of all players).";
			
		case Energy_for_Power:
			return 
					"Gain 1 Energy generation for every Power tag on all the cards you have currently played (including this card).";
			
		case Plant_for_Microbes:
			return 
					"Gain 1 Plant generation for every 2 Microbe tags on all the cards you have currently played (including this card).";
			
		case Plant_for_Plant:
			return "Gain 1 Plant generation for every Plant tag on all the cards you have currently played.";
			
		case MegaCredit_for_Building:
			return 
					"Gain 1 MegaCredit generation for every 2 Building tags on all the cards you have currently played (including this card).";
			
		case MegaCredit_for_Space:
			return 
					"Gain 1 MegaCredit generation for every Space tag on all the cards you have currently played (including this card).";
			
		case MegaCredit_for_Earth:
			return 
					"Gain 1 MegaCredit generation for every Earth tag on all the cards you have currently played (including this card).";
			
		case MegaCredit_for_Earth2:
			return 
					"Gain 1 MegaCredit generation for every Earth tag on all the cards you have currently played.";
			
		case TR_for_Jovian:
			return 
					"Increase your terraform rating by 1 for each Jovian tag on all the cards you have currently played (including this card).";
			
		case MegaCredit_for_Opponent_Space:
			return 
					"Gain 1 MegaCredit generation for every Titanium tags on all the cards your opponents have played (all cards' and companies' tags from all opponents).";
			
		case MegaCredit_for_All_Event:
			return 
					"Gain 1 MegaCredit for every Event tag on all the cards played by all players (all cards' and companies' tags from all opponents and yourself).";
			

		case Plant_Or_Energy:
			return 
					"You may either choose to increase your Plant generation by 1 or increase your Energy generation by 2";
			
		case Heat_for_MegaCredit:
			return 
					"You may decrease your Heat generation by any number. Increase your MegaCredit generation by that number.";
			
		case More_Plants:
			return 
					"If you have played 3 cards with Plant tags, increase your Plant generation by 4. Otherwise, increase your Plant generation by 1.";
			
		case Sabotage:
			return "You may attempt to steal either 2 Steel resources or 3 MegaCredits from one player.";
			
		case Hired_Raiders:
			return 
					"You may either choose one of the following sets of resources to decrease from another player: 3 Titanium, 4 Steel, or 7 MegaCredits.";
			
		case Card1_of_3:
			return "Look at the top 3 cards from the deck. Keep 1, discard the other 2.";
			
		case Card2_of_4:
			return "Look at the top 4 cards from the deck. Keep 2, discard the other 2.";
			
			
		case Add2Microbe:
			return "Add 2 Microbe counters to a card.";
			
		case Add3Microbe:
			return "Add 3 Microbe counters to a card.";
			
		case AddAnimal:
			return "Add 1 Animal counter to a card.";
			
		case Add2Animal:
			return "Add 2 Animal counters to a card.";
			
		case Add4PlantOr2Animal:
			return "Gain 4 Plants or add 2 Animal counters to a card.";
			
		case Add5PlantOr4Animal:
			return "Gain 5 Plants or add 4 Animal counters to a card.";
			
		case Add3PlantOr2AnimalOr3Microbe:
			return "Gain 3 Plants or add 3 Animal or Microbe counters to a card.";
			
		case AddCounter:
			return "Add 1 counter to a card with another counter on it.";
			
		case RemoveAnimalOrPlant:
			return "Remove up to 5 Plants or 2 Animals from another player.";
			
		case DuplicateProduction:
			return "You may duplicate the positive resource generation effects from any of the cards you have played.";
			
		case FundAward:
			return "Fund an award for free.";
			
		case Prelude:
			return "Draw 3 Preludes. Choose 1 to keep, discard the other 2.";
			
		case DrawPlantCards:
			return "Draw 2 cards with Plant tags.";
			
		case DrawSpaceCards:
			return "Draw 2 cards with Space tags.";
			
		case DrawCounterCards:
			return "Draw 2 cards with counters.";
			
			
			
		// Custom activated abilities
		case Energy_to_Card:
			return "Spend 1 Energy to draw a card.";
			
		case Energy_to_MegaCredit:
			return "Spend any amount of energy to gain that amount of MegaCredits.";
			
		case Energy_to_MegaCredit_City:
			return 
					"Spend 1 Energy to gain 1 MegaCredit for every City in play on Mars (all Cities of all players, except for the two designated Colonies).";
			
		case Energy_to_TR:
			return "Decrease your Energy generation by 1. Increase your terraform rating by 1.";
			
		case Energy_to_Oxygen:
			return 
					"If oxygen is not maxed (14%), spend 3 Energy to increase the oxygen, and increase your terraform rating by 1.";
			
		case Energy_to_Oxygen_Steel:
			return 
					"Spend 4 Energy to gain 1 Steel. If oxygen is not maxed (14%), increase the oxygen and increase your terraform rating by 1.";
			
		case Energy_to_Oxygen_Steel2:
			return 
					"Spend 4 Energy to gain 2 Steel. If oxygen is not maxed (14%), increase the oxygen and increase your terraform rating by 1.";
			
		case Energy_to_Oxygen_Titanium:
			return 
					"Spend 4 Energy to gain 1 Titanium. If oxygen is not maxed (14%), increase the oxygen and increase your terraform rating by 1.";
			
		case Steel_to_MegaCredit:
			return "Spend 1 Steel to gain 5 MegaCredits.";
			
		case Heat_to_TR:
			return "Spend 8 Heat to increase your terraform rating by 1.";
			
		case Draw_2_Cards:
			return "Draw 2 cards.";
			
		case MegaCredit_to_Energy:
			return "Spend 7 MegaCredit to increase your Energy generation by 1.";
			
		case MegaCredit_to_Heat:
			return "Spend 10 MegaCredit to increase your Heat generation by 2.";
			
		case PlantSteel_to_MegaCredit:
			return "You may either spend 1 Plant or 1 Steel to gain 7 MegaCredit.";
			
		case MegaCreditSteel_to_Water:
			return 
					"If all water has not been placed, spend 8 MegaCredit to place a water. Steel may be used instead (as if you were playing a Building card.";
			
		case MegaCreditTitanium_to_Water:
			return 
					"If all water has not been placed, spend 12 MegaCredit to place a water. Titanium may be used instead (as if you were playing a Space card.";
			
		case PurchaseCard:
			return 
					"If the deck is not empty, look at the top card of the deck. You may choose to buy it for 3 MegaCredit or discard it.";
			
		case MegaCredit_to_Card:
			return "If the deck is not empty, spend 2 MegaCredit resources and draw a card from the deck.";
			
		case MegaCredit_to_Steel:
			return "Spend 7 MegaCredit to increase your Steel generation by 1.";
			
		case Add_Fleet:
			return "Spend 1 Titanium to add a counter to this card. A counter may generate points or other bonus, depending on the other card's abilities.";
		case Add_Tardigrades:
		case Add_GHGBacteria:
		case Add_Regolith:
		case Add_NitriteBacteria:
		case Add_Fish:
		case Add_Livestock:
		case Add_Bird:
		case Add_SmallAnimal:
		case Add_Psychrophiles:
			return "Add a counter to this card. A counter may generate points or other bonus, depending on the other card's abilities.";
			
		case Add_Fungus:
			return "Add a Microbe counter to another one of your cards.";
			
		case Add_ColdFungus:
			return "Either gain 1 Plant or add 2 Microbe counters to another one of your cards.";
			
		case Counter_to_TR:
			return "Remove 3 counters from this card. Increase your terraform rating by 1.";
			
		case Counter_to_Oxygen:
			return "If the oxygen is not maxed (14%), remove 2 counters from this card. Increase the oxygen by 1%, and increase your terraform rating by 1.";
			
		case Counter_to_Temperature:
			return "If the temperature is not maxed (30˚C), remove 2 counters from this card. Increase the temperature by 1 (2˚C), and increase your terraform rating by 1.";
			
		case Energy_to_Science:
			return "Spend 6 Energy to add a Science counter on this card.";
			
		case MegaCredit_Search_Microbe:
			return "If the deck is not empty, spend 1 MegaCredit to reveal and discard the top card of the deck. If the card has a Microbe tag, add a Science counter on this card.";
			
		case StealAnimal:
			return "Remove an Animal counter from any of your cards or your opponents' and place it on this card.";
			
		case StealMicrobe:
			return "Remove a Microbe counter from any of your cards or your opponents' and place it on this card.";
			
		case Heat_to_Resource:
			return "Remove up to 8 Heat resources. Gain that many resources of any other resource type except MegaCredits.";
			
			
			
		// Custom permanent abilities
		case Global_Parameter:
			return "Your global parameter requirements are +2 or -2 steps for all cards.";
			
		case Global_ParameterTemp:
			return "Your global parameter requirements will be permanently altered to +2 or -2 steps for the next card you try to play.";
			
		case Global_ParameterTemp2:
			return "All global parameter requirements will be permanently removed for the next card you try to play.";
			
		case GlobalDiscount:
			return "All cards will cost -2 MegaCredits.";
			
		case GlobalDiscount2:
			return "All cards will cost -1 MegaCredits.";
			
		case SpaceDiscount:
			return "All cards with a Space tag will cost -2 MegaCredits (per Space tag).";
			
		case EarthDiscount:
			return "All cards with a Earth tag will cost -3 MegaCredits (per Earth tag).";
			
		case BlockedCounters:
			return "Opponents may not remove Plants, Animal counters, or Microbe counters.";
			
		case SpaceEventMegaCredit:
			return "Whenever you play a card with the Space and Event tags, gain 3 MegaCredits and 3 Heat.";
			
		case EventMegaCredit:
			return "Whenever you play a card with the Event tag, gain 3 MegaCredit.";
			
		case ScienceDrawCard:
			return "Draw a card for every second card you play with a Science tag.";
			
		case ScienceDiscardCard:
			return "Whenever you play a card with a Science tag, you may discard a card and draw another card.";
			
		case Decomposers:
			return "Whenever you play a card with a Plant, Microbe, or Animal tag, add a counter to this card.";
			
		case ViralEnhancers:
			return "Whenever you play a card with a Plant, Microbe, or Animal tag, add a counter to that card. If the card does not have counters, you gain a Plant.";
			
		case DiscountProjects:
			return "Whenever you pay for a standard project (except selling a card), gain 3 MegaCredit.";
			
		case DiscountTemp:
			return "The next card you try to play will be permanently altered to cost -8 MegaCredits.";
			
		case DiscountTemp2:
			return "The next card you try to play will be permanently altered to cost -25 MegaCredits.";
			
		case Herbivores:
			return "Whenever you place a greenery, add a counter to this card.";
			
		case ArcticAlgae:
			return "Whenever anyone places an ocean, gain 2 Plants.";
			
		case RoverConstruction:
			return "Whenever anyone places a city, gain 2 MegaCredits.";
			
		case Pets:
			return "Whenever anyone places a city, add a counter to this card.";
			
		case ImmigrantCity:
			return "Whenever anyone places a city, increase your MegaCredit generation by 1.";
			
		case ValuableResources:
			return "Titanium and Steel are worth +1 MegaCredit extra.";
			
		case Psychrophiles:
			return "When you play a card with a Plant tag, the cost of that card is reduced by 2 MegaCredits per Microbe on this card. These Microbes are then removed.";
			
		case BuildingDiscount:
			return "All cards with a Building tag will cost -2 MegaCredits (per Building tag).";
			
		case ScienceDiscount:
			return "All cards with a Space tag will cost -2 MegaCredits (per Space tag).";
			
			
			
		// Company Abilities
		case PayWithHeat:
			return "You may use heat as if it were MegaCredits.";
			
		case EventMegaCredit2:
			return "Whenever you play a card with an Event tag, gain 2 MegaCredits.";
			
		case Credicor:
			return "Whenever you play a card or Standard Project with a base cost of 20 MegaCredits or more, gain 4 MegaCredits back.";
			
		case Ecoline:
			return "Greeneries cost 7 Plants instead of 8.";
			
		case Mining:
			return "Whenever you place a tile with a Steel or Titanium placement bonus, increase your Steel generation.";
			
		case Tharsis:
			return "Whenever a City is placed (by any player), increase your MegaCredit generation by 1. Whenever you place a City, gain an additional 3 MegaCredits.";
			
		case Thorgate:
			return "Cards with a Power tag and the Standard Project 'Power Plant' cost 3 MegaCredits less.";
			
		case UNMI:
			return "If your terraform rating was raised this generation, you may pay 3 MegaCredits to raise it again.";
			
		case SaturnSystem:
			return "Whenever any player plays a Jovian tag, including this one, increase your MegaCredit generation by 1.";
			
		case PointLuna:
			return "Whenever you play a card with an Earth tag, if there are cards left in the deck, draw a card (per Earth tag).";
			
		case Robinson:
			return "Pay 4 MegaCredits to increase the resource generation of your lowest generation amount.";
			
		case Vitor:
			return "Whenever you play a card with 1 or more Victory Points gain 3 MegaCredits.";
			
		case Celestic:
			return "Creates a custom card in your played deck.";
			
		case AddFloater:
			return "Add a counter to any card.";
			
		case Manutech:
			return "Whenever you increase the production of a resource, also gain that resource.";
			
		case Viron:
			return "Lets you activate any ability a second time, assuming you can meet the requirements of the ability.";
			
		case Polyphemos:
			return "Cards cost 5 MegaCredits to purchase into your hand (instead of 3 MegaCredits).";
			
		case Terralabs:
			return "Cards cost 1 MegaCredit to purchase into your hand. Decrease your terraform rating by 1.";
			
		case OuterResearch:
			return "You get to choose 2 from extra cards during the research phase and 1 extra prelude at the start of the game.";
			
			
			
		// Custom end-of-game abilities
		case Jovian_Points:
			return 
					"At the end of the game, this card will give 1 point for each played card with a Jovian tag.";
			
		case City_Points:
			return 
					"At the end of the game, this card will give 1 point for every 3 Cities played by all players.";
			
		case Adjacent_Water_Points:
			return "At the end of the game, this card will give 1 point for each adjacent water tile.";
			
		case PointPerCounter:
		case PointPerCounter2:
		case PointPerCounter3:
		case PointPerCounter4:
			return "At the end of the game, this card will give 1 point for every X counters on this card.";
			
		case PointPer2Counter:
			return "At the end of the game, this card will give X points for every counter on this card.";
			
		case PointIfCounter3:
			return "At the end of the game, this card will give X points if there is at least 1 counter on this card.";
			
			
			
		// Custom validation functions
		case Has_Steel_Generation:
			return 
					"Requires that the player has at least 1 Steel generation. Does not decrease Steel generation.";
			
		case Has_Titanium_Generation:
			return 
					"Requires that the player has at least 1 Titanium generation. Does not decrease Titanium generation.";
			
		case Cities_Exist:
			return "Requires that 2 Cities are in play (including yours and all opponents'.";
			
		default:
			return "No description written for ability";
			
		}
	}

	// runs the ability. Adds it to a list if the ability cannot be repeated this generation.
	public boolean activate(Player player, Ability ability) {
		boolean activateViron = false;
		if (player.findAbility(ability)) {
			if(PermanentAbilities.Viron(player)) {
				if(player.findAbility(Ability.Viron)) {
					System.err.println("You have already activated the ability " + ability.toString() + " this generation.");
					return false;
				} else {
					activateViron = true;
				}
			} else {
				System.err.println("You have already activated the ability " + ability.toString() + " this generation.");
			return false;
			}
			
		}
		boolean return_boolean = false;
		switch (ability) {

		// Parameter abilities
		case Temperature:
			Temperature(player);
			return false;
		case Water:
			Water(player);
			return false;
		case Oxygen:
			Oxygen(player);
			return false;
		case Terraform_Rating:
			TerraformRating(player);
			return false;

		// General abilities
		case Place_City:
			PlaceCity(player);
			return false;
		case Place_Greenery:
			PlaceGreenery(player);
			return false;

		// Standard project abilities
		case Sell_Patent:
			return_boolean = SellPatent(player);
			break;
		case Power_Plant:
			return_boolean = PowerPlant(player);
			break;
		case Asteroid:
			return_boolean = Asteroid(player);
			break;
		case Aquifier:
			return_boolean = Aquifier(player);
			break;
		case Greenery:
			return_boolean = Greenery(player);
			break;
		case City:
			return_boolean = City(player);
			break;

		// inventory abilities
		case Convert_Greenery:
			return_boolean = ConvertGreenery(player);
			break;
		case Convert_Temperature:
			return_boolean = ConvertTemperature(player);
			break;

		// Custom placement abilities
		case Place_Noctis_City:
			PlaceNoctisCity(player);
			return false;
		case Place_Phobos_Space_Haven:
			PlacePhobosSpaceHaven(player);
			return false;
		case Place_Ganymede_Colony:
			PlaceGanymedeColony(player);
			return false;
		case Place_Mine:
			PlaceMine(player);
			return false;
		case Place_Nuclear_Zone:
			PlaceNuclearZone(player);
			return false;
		case Place_Volcano:
			PlaceVolcano(player);
			return false;
		case Place_Volcano_City:
			PlaceVolcanoCity(player);
			return false;
		case Place_Restricted_Area:
			PlaceRestrictedArea(player);
			return false;
		case Place_Commercial_District:
			PlaceCommercialDistrict(player);
			return false;
		case Place_Capital:
			PlaceCapital(player);
			return false;
		case Place_Ecological_Zone:
			player.permanent_abilities.add(ability);
			PlaceEcologicalZone(player);
			return false;
		case Place_Industrial_Center:
			PlaceIndustrialCenter(player);
			return false;
		case Place_Urbanized_Area:
			PlaceUrbanizedArea(player);
			return false;
		case Place_Mohole_Area:
			PlaceMoholeArea(player);
			return false;
		case Place_Flooding:
			PlaceFlooding(player);
			return false;
		case Place_Blocker:
			PlaceBlocker(player);
			return false;
		case Place_Natural_Preserve:
			PlacePreserveNaturalArea(player);
			return false;
		case Place_Research_Outpost:
			PlaceResearchOutpost(player);
			return false;
		case Place_Artificial_Lake:
			PlaceArtificialLake(player);
			return false;
		case Place_Protected_Valley:
			PlaceProtectedValley(player);
			break;
		case Place_Mangrove:
			PlaceMangrove(player);
			break;
		case Water_On_Land:
			WaterOnLand(player);
			return false;
		case Land_On_Water:
			LandOnWater(player);
			return false;
		case Plant_for_City:
			PlantForCity(player);
			return false;
		case MegaCredit_for_City:
			MegaCreditForCity(player);
			return false;
		case Energy_for_City:
			EnergyForCity(player);
			return false;
		case Energy_for_Power:
			EnergyForPower(player);
			return false;
		case Plant_for_Microbes:
			PlantForMicrobes(player);
			return false;
		case Plant_for_Plant:
			PlantForPlant(player);
			return false;
		case MegaCredit_for_Building:
			MegaCreditForBuilding(player);
			return false;
		case MegaCredit_for_Space:
			MegaCreditForSpace(player);
			return false;
		case MegaCredit_for_Earth:
			MegaCreditForEarth(player);
			return false;
		case MegaCredit_for_Earth2:
			MegaCreditForEarth2(player);
			return false;
		case TR_for_Jovian:
			TRForJovian(player);
			return false;
		case MegaCredit_for_Opponent_Space:
			MegaCreditForOpponentSpace(player);
			return false;
		case MegaCredit_for_All_Event:
			MegaCreditForAllEvent(player);
			return false;

		case Plant_Or_Energy:
			PlantOrEnergy(player);
			return false;
		case Heat_for_MegaCredit:
			HeatForMegaCredit(player);
			return false;
		case More_Plants:
			MorePlants(player);
			return false;
		case Sabotage:
			Sabotage(player);
			return false;
		case Hired_Raiders:
			HiredRaiders(player);
			return false;
		case Card1_of_3:
			Card1of3(player);
			return false;
		case Card2_of_4:
			Card2of4(player);
			return false;
		case Add2Microbe:
			Add2Microbe(player);
			break;
		case Add3Microbe:
			Add3Microbe(player);
			break;
		case AddAnimal:
			AddAnimal(player);
			break;
		case Add2Animal:
			Add2Animal(player);
			break;
		case Add4PlantOr2Animal:
			Add4PlantOr2Animal(player);
			break;
		case Add5PlantOr4Animal:
			Add5PlantOr4Animal(player);
			break;
		case Add3PlantOr2AnimalOr3Microbe:
			Add3PlantOr2AnimalOr3Microbe(player);
			break;
		case AddCounter:
			AddCounter(player);
			break;
		case RemoveAnimalOrPlant:
			RemoveAnimalOrPlant(player);
			break;
		case DuplicateProduction:
			DuplicateProduction(player);
			break;
		case FundAward:
			FundAward(player);
			break;
		case Prelude:
			Prelude(player);
			break;
		case DrawPlantCards:
			DrawPlantCards(player);
			break;
		case DrawSpaceCards:
			DrawSpaceCards(player);
			break;
		case DrawCounterCards:
			DrawCounterCards(player);
			break;

		// Custom activated abilities
		case Energy_to_Card:
			return_boolean = EnergyToCard(player);
			break;
		case Energy_to_MegaCredit:
			return_boolean = EnergyToMegaCredit(player);
			break;
		case Energy_to_MegaCredit_City:
			return_boolean = EnergyToMegaCreditCity(player);
			break;
		case Energy_to_TR:
			return_boolean = EnergyToTR(player);
			break;
		case Energy_to_Oxygen:
			return_boolean = EnergyToOxygen(player);
			break;
		case Energy_to_Oxygen_Steel:
			return_boolean = EnergyToOxygenSteel(player);
			break;
		case Energy_to_Oxygen_Steel2:
			return_boolean = EnergyToOxygenSteel2(player);
			break;
		case Energy_to_Oxygen_Titanium:
			return_boolean = EnergyToOxygenTitanium(player);
			break;
		case Steel_to_MegaCredit:
			return_boolean = SteelToMegaCredit(player);
			break;
		case Heat_to_TR:
			return_boolean = HeatToTR(player);
			break;
		case Draw_2_Cards:
			return_boolean = Draw2Cards(player);
			break;
		case MegaCredit_to_Energy:
			return_boolean = MegaCreditToEnergy(player);
			break;
		case MegaCredit_to_Heat:
			return_boolean = MegaCreditToHeat(player);
			break;
		case PlantSteel_to_MegaCredit:
			return_boolean = PlantSteelToMegaCredit(player);
			break;
		case MegaCreditSteel_to_Water:
			return_boolean = MegaCreditSteelToWater(player);
			break;
		case MegaCreditTitanium_to_Water:
			return_boolean = MegaCreditTitaniumToWater(player);
			break;
		case PurchaseCard:
			return_boolean = PurchaseCard(player);
			break;
		case MegaCredit_to_Card:
			return_boolean = MegaCreditToCard(player);
			break;
		case MegaCredit_to_Steel:
			return_boolean = MegaCreditToSteel(player);
			break;
			
		case Add_Tardigrades:
			ReusableAbilityFunctions.increaseCounter(player, "TARDIGRADES");
			return_boolean = true;
			break;
		case Add_GHGBacteria:
			ReusableAbilityFunctions.increaseCounter(player, "GHG PRODUCING BACTERIA");
			player.addAbility(Ability.Counter_to_Temperature);
			return_boolean = true;
			break;
		case Add_Regolith:
			ReusableAbilityFunctions.increaseCounter(player, "REGOLITH EATERS");
			player.addAbility(Ability.Counter_to_Oxygen);
			return_boolean = true;
			break;
		case Add_NitriteBacteria:
			ReusableAbilityFunctions.increaseCounter(player, "NITRITE REDUCING BACTERIA");
			player.addAbility(Ability.Counter_to_TR);
			return_boolean = true;
			break;
		case Add_Fish:
			ReusableAbilityFunctions.increaseCounter(player, "FISH");
			return_boolean = true;
			break;
		case Add_Livestock:
			ReusableAbilityFunctions.increaseCounter(player, "LIVESTOCK");
			return_boolean = true;
			break;
		case Add_Bird:
			ReusableAbilityFunctions.increaseCounter(player, "BIRDS");
			return_boolean = true;
			break;
		case Add_SmallAnimal:
			ReusableAbilityFunctions.increaseCounter(player, "SMALL ANIMALS");
			return_boolean = true;
			break;
		case Add_Psychrophiles:
			ReusableAbilityFunctions.increaseCounter(player, "PSYCHROPHILES");
			return_boolean = true;
			break;
		case Add_Fleet:
			return_boolean = AddFleet(player);
			break;
		case Add_Fungus:
			return_boolean = AddFungus(player);
			break;
		case Add_ColdFungus:
			return_boolean = AddColdFungus(player);
			break;
		case Counter_to_TR:
			return_boolean = CounterToTR(player);
			if(return_boolean) player.addAbility(Ability.Add_NitriteBacteria);
			break;
		case Counter_to_Oxygen:
			return_boolean = CounterToOxygen(player);
			if(return_boolean) player.addAbility(Ability.Add_Regolith);
			break;
		case Counter_to_Temperature:
			return_boolean = CounterToTemperature(player);
			if(return_boolean) player.addAbility(Ability.Add_GHGBacteria);
			break;
		case Energy_to_Science:
			return_boolean = EnergyToScience(player);
			break;
		case MegaCredit_Search_Microbe:
			return_boolean = MegaCreditSearchMicrobe(player);
			break;
		case StealAnimal:
			return_boolean = StealAnimal(player);
			break;
		case StealMicrobe:
			return_boolean = StealMicrobe(player);
			break;
		case Heat_to_Resource:
			return_boolean = HeatToResource(player);
			break;
			

		// Custom passive abilities
		case Global_Parameter:
		case Global_ParameterTemp:
		case Global_ParameterTemp2:
		case GlobalDiscount:
		case GlobalDiscount2:
		case SpaceDiscount:
		case EarthDiscount:
		case SpaceEventMegaCredit:
		case EventMegaCredit:
		case ScienceDrawCard:
		case ScienceDiscardCard:
		case Decomposers:
		case ViralEnhancers:
		case DiscountProjects:
		case DiscountTemp:
		case DiscountTemp2:
		case Herbivores:
		case ArcticAlgae:
		case RoverConstruction:
		case Pets:
		case ImmigrantCity:
		case ValuableResources:
		case Psychrophiles:
		case BuildingDiscount:
		case ScienceDiscount:
			player.permanent_abilities.add(ability);
			return false;
		case BlockedCounters:
			BlockedCounters(player);
			player.permanent_abilities.add(ability);
			return false;
			
		// Company abilities
		case Terralabs:
			player.terraform_rating--;
		case PayWithHeat:
		case EventMegaCredit2:
		case Credicor:
		case Ecoline:
		case Mining:
		case Tharsis:
		case Thorgate:
		case SaturnSystem:
		case PointLuna:
		case Vitor:
		case Manutech:
		case Viron:
		case Polyphemos:
		case OuterResearch:
			player.permanent_abilities.add(ability);
			return false;
		case UNMI:
			return_boolean = UNMI(player);
			break;
		case Robinson:
			return_boolean = Robinson(player);
			break;
		case Celestic:
			Celestic(player);
			return false;
		case AddFloater:
			AddFloater(player);
			return_boolean = true;
			break;
			
			
		// Custom end-of-game abilities
		// Need to also add these abilities to EndOfGame
		case Jovian_Points:
			player.end_of_game_abilities.add(ability);
			return false;
		case City_Points:
			player.end_of_game_abilities.add(ability);
			return false;
		case Adjacent_Water_Points:
			player.end_of_game_abilities.add(ability);
			return false;
		case PointPerCounter:
			return false;
		case PointPerCounter2:
			return false;
		case PointPerCounter3:
			return false;
		case PointPerCounter4:
			return false;
		case PointPer2Counter:
			return false;
		case PointIfCounter3:
			return false;
		default:
			return false;
		}
		
		if(activateViron)
			player.addAbility(Ability.Viron);
		else if (return_boolean)
			player.addAbility(ability);
		
		return return_boolean;
	}

	// does the ability have a requirement
	public static boolean hasRequirement(Ability ability) {
		switch (ability) {
		case Place_Mine:
			return true;
		case Place_Nuclear_Zone:
			return true;
		case Place_Volcano:
			return true;
		case Place_Volcano_City:
			return true;
		case Place_Restricted_Area:
			return true;
		case Place_Commercial_District:
			return true;
		case Place_Capital:
			return true;
		case Place_Ecological_Zone:
			return true;
		case Place_Industrial_Center:
			return true;
		case Place_Urbanized_Area:
			return true;
		case Place_Mohole_Area:
			return true;
		case Place_Flooding:
			return true;
		case Place_Blocker:
			return true;
		case Place_Natural_Preserve:
			return true;
		case Place_Research_Outpost:
			return true;
		case Place_Artificial_Lake:
			return true;
		case Place_Protected_Valley:
			return true;
		case Place_Mangrove:
			return true;
		case Plant_for_City:
			return true;
		case MegaCredit_for_City:
			return true;
		case Energy_for_City:
			return true;
		case Plant_for_Microbes:
			return true;
		case Plant_for_Plant:
			return true;
		case MegaCredit_for_Building:
			return true;
		case MegaCredit_for_Opponent_Space:
			return true;
		case MegaCredit_for_All_Event:
			return true;
		case Heat_for_MegaCredit:
			return true;
		case Card2_of_4:
			return true;
		case Card1_of_3:
			return true;
		case AddCounter:
			return true;
		case Has_Steel_Generation:
			return true;
		case Has_Titanium_Generation:
			return true;
		case Cities_Exist:
			return true;
		case RemoveAnimalOrPlant:
			return true;
		case DuplicateProduction:
			return true;
		default:
			return false;
		}
	}

	// does the player satisfy the requirements of the ability
	public static boolean satisfiesRequirement(Ability ability, Player player) {
		switch (ability) {
		case Place_Mine:
			return AbilityRequirement.PlaceMine(player);
		case Place_Nuclear_Zone:
			return AbilityRequirement.PlaceNuclearZone(player);
		case Place_Volcano:
		case Place_Volcano_City:
			return AbilityRequirement.PlaceVolcano(player);
		case Place_Restricted_Area:
			return AbilityRequirement.PlaceRestrictedArea(player);
		case Place_Commercial_District:
			return AbilityRequirement.PlaceCommercialDistrict(player);
		case Place_Capital:
			return AbilityRequirement.PlaceCapital(player);
		case Place_Ecological_Zone:
			return AbilityRequirement.PlaceEcologicalZone(player);
		case Place_Industrial_Center:
			return AbilityRequirement.PlaceIndustrialCenter(player);
		case Place_Urbanized_Area:
			return AbilityRequirement.PlaceUrbanizedArea(player);
		case Place_Mohole_Area:
			return AbilityRequirement.PlaceMoholeArea(player);
		case Place_Flooding:
			return AbilityRequirement.PlaceFlooding(player);
		case Place_Blocker:
			return AbilityRequirement.PlaceBlocker(player);
		case Place_Natural_Preserve:
			return AbilityRequirement.PlacePreserveNaturalArea(player);
		case Place_Research_Outpost:
			return AbilityRequirement.PlaceResearchOutpost(player);
		case Place_Protected_Valley:
			return AbilityRequirement.PlaceProtectedValley(player);
		case Place_Mangrove:
			return AbilityRequirement.PlaceMangrove(player);
		case Place_Artificial_Lake:
			return AbilityRequirement.Place_Artificial_Lake(player);
		case Plant_for_City:
			return AbilityRequirement.PlantForCity(player);
		case MegaCredit_for_City:
			return AbilityRequirement.MegaCreditForCity(player);
		case Energy_for_City:
			return AbilityRequirement.EnergyForCity(player);
		case Plant_for_Microbes:
			return AbilityRequirement.PlantForMicrobe(player);
		case Plant_for_Plant:
			return AbilityRequirement.PlantForPlant(player);
		case MegaCredit_for_Building:
			return AbilityRequirement.MegaCreditForBuilding(player);
		case MegaCredit_for_Opponent_Space:
			return AbilityRequirement.MegaCreditForOpponentSpace(player);
		case MegaCredit_for_All_Event:
			return AbilityRequirement.MegaCreditForAllEvent(player);
		case Heat_for_MegaCredit:
			return AbilityRequirement.HeatForMegaCredit(player);
		case Card1_of_3:
			return AbilityRequirement.Card1of3(player);
		case Card2_of_4:
			return AbilityRequirement.Card2of4(player);
		case AddCounter:
			return AbilityRequirement.AddCounter(player);
		case Has_Steel_Generation:
			return AbilityRequirement.HasSteelGeneration(player);
		case Has_Titanium_Generation:
			return AbilityRequirement.HasTitaniumGeneration(player);
		case Cities_Exist:
			return AbilityRequirement.CitiesExist(player);
		case RemoveAnimalOrPlant:
			return AbilityRequirement.RemoveAnimalOrPlant(player);
		case DuplicateProduction:
			return AbilityRequirement.DuplicateProduction(player);
		default:
			return false;
		}
	}
	
	// -------------------------------------------------
	// -------------- Parameter functions --------------
	// -------------------------------------------------

	private void Temperature(Player player) {
		if (game.board.temperature >= 8) {
			System.err.println("The temperature is already at 8˚C. You will not be able to increase the temperature.");
			return;
		}
		game.board.increaseTemperature(player);
	}

	private void Water(Player player) {
		if (game.board.water >= 9) {
			System.err.println("All water has already been placed. You cannot place any more water.");
			return;
		}
		game.board.increaseWater(player);
	}

	private void Oxygen(Player player) {
		if (game.board.oxygen >= 14) {
			System.err.println("The oxygen is already at 14%. You will not be able to increase the oxygen");
			return;
		}
		game.board.increaseOxygen(player);
	}

	private void TerraformRating(Player player) {
		player.terraform_rating++;
	}

	// -----------------------------------------------
	// -------------- General functions --------------
	// -----------------------------------------------

	private void PlaceCity(Player player) {
		if (!game.board.freeCitySpace()) {
			System.err.println("All available spots on the board have been taken.");
			return;
		}
		game.board.placeCity(player);
	}

	private void PlaceGreenery(Player player) {
		if (!game.board.freeLandSpace()) {
			System.err.println("All available spots on the board have been taken.");
			return;
		}
		game.board.placeGreenery(player);
	}

	// --------------------------------------------------------
	// -------------- Standard project functions --------------
	// --------------------------------------------------------

	private boolean SellPatent(Player player) {
		if (player.hand.size() < 1) {
			System.err.println("No patents to sell");
			return false;
		}
		boolean sold = false;
		while (true) {
			if (player.hand.size() == 0)
				break;
			player.printHand();
			System.out.print("\nDo you want to sell a patent (y/n)?\t");
			try {
				String in = reader.nextLine();
				in = in.trim().toLowerCase();
				if (in.equals("y") || in.equals("yes")) {
					sold = true;
					if (player.hand.size() == 1) {
						player.increaseResource(Resources.MegaCredit, 1);
						game.discard.add(player.hand.remove(0));
						continue;
					}
					boolean loop = true;
					while (loop) {
						System.out.print("Which card would you like to sell?\t");
						try {
							in = reader.nextLine();
							System.out.println();
							int n = Integer.valueOf(in);
							if (n < 1 || n > player.hand.size()) {
								System.out.println("Please choose a value between 1 and " + (player.hand.size()));
								continue;
							}
							player.increaseResource(Resources.MegaCredit, 1);
							game.discard.add(player.hand.remove(n - 1));
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
		return sold;
	}

	private boolean PowerPlant(Player player) {
		if(PermanentAbilities.Thorgate(player)) {
			if (!ReusableAbilityFunctions.pay(player, "Power Plant", 8))
				return false;
		} else {
			if (!ReusableAbilityFunctions.pay(player, "Power Plant", 11))
				return false;
		}
		player.increaseResourceGeneration(Resources.Energy, 1);
		PermanentAbilities.DiscountProjects(player);
		return true;
	}

	private boolean Asteroid(Player player) {
		if (game.board.temperature >= 8) {
			System.err.println("The temperature is already at 8˚C.");
			return false;
		}
		if (!ReusableAbilityFunctions.pay(player, "Asteroid", 14))
			return false;
		Temperature(player);
		PermanentAbilities.DiscountProjects(player);
		return true;
	}

	private boolean Aquifier(Player player) {
		if (game.board.water >= 9) {
			System.err.println("All water has already been placed.");
			return false;
		}
		if (!ReusableAbilityFunctions.pay(player, "Aquifier", 18))
			return false;
		Water(player);
		PermanentAbilities.DiscountProjects(player);
		return true;
	}

	private boolean Greenery(Player player) {
		if (!game.board.freeLandSpace()) {
			System.err.println("All available spots have been taken on the board.");
			return false;
		}
		if (!ReusableAbilityFunctions.pay(player, "Greenery", 23))
			return false;
		if (game.board.oxygen >= 14) {
			System.out.println("Oxygen is already at capacity. Cannot increase oxygen.");
		}
		PlaceGreenery(player);
		PermanentAbilities.DiscountProjects(player);
		PermanentAbilities.Credicor(player);
		return true;
	}

	private boolean City(Player player) {
		if (!game.board.freeCitySpace()) {
			System.err.println("All available spots have been taken on the board.");
			return false;
		}
		if (!ReusableAbilityFunctions.pay(player, "City", 25))
			return false;
		PlaceCity(player);
		PermanentAbilities.DiscountProjects(player);
		PermanentAbilities.Credicor(player);
		return true;
	}

	// -------------------------------------------------
	// -------------- Inventory functions --------------
	// -------------------------------------------------

	private boolean ConvertGreenery(Player player) {
		if (!game.board.freeLandSpace()) {
			System.err.println("All available spots have been taken on the board.");
			return false;
		}
		if (game.board.oxygen >= 14) {
			System.out.println("Oxygen is already at capacity. Cannot increase oxygen.");
		}
		
		if(PermanentAbilities.Ecoline(player)) {
			if (!player.checkResource(Resources.Plant, 7)) {
				System.err.println("Convert Greenery costs 7 Plants.");
				return false;
			}
			player.increaseResource(Resources.Plant, -7);
		} else {
			if (!player.checkResource(Resources.Plant, 8)) {
				System.err.println("Convert Greenery costs 8 Plants.");
				return false;
			}
			player.increaseResource(Resources.Plant, -8);
		}
		game.board.placeGreenery(player);
		return true;
	}

	private boolean ConvertTemperature(Player player) {
		if (game.board.temperature >= 8) {
			System.err.println("The temperature is already at 8˚C.");
			return false;
		}
		if (!player.checkResource(Resources.Heat, 8)) {
			System.err.println("Convert Temperature costs 8 Heat.");
			return false;
		}
		player.increaseResource(Resources.Heat, -8);
		game.board.increaseTemperature(player);
		return true;
	}

	// --------------------------------------------------------
	// -------------- Custom placement functions --------------
	// --------------------------------------------------------

	private void PlaceNoctisCity(Player player) {
		game.board.claimCity(player, 6, 2);
	}

	private void PlacePhobosSpaceHaven(Player player) {
		game.board.claimCity(player, 0, 0);

	}

	private void PlaceGanymedeColony(Player player) {
		game.board.claimCity(player, 0, 1);
	}

	private void PlaceMine(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j) && (game.board.board[i][j].hasResource(Resources.Steel) || game.board.board[i][j].hasResource(Resources.Titanium))) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		int playersPick = game.getUserInt("Choose where you want to place the tile", "you cannt place it there", 1,
				counter - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j) && (game.board.board[i][j].hasResource(Resources.Steel) || game.board.board[i][j].hasResource(Resources.Titanium))) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					} 
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, storage1, storage2);
		if(game.board.board[storage1][storage2].hasResource(Resources.Steel)) 
			player.increaseResourceGeneration(Resources.Steel, 1);
		else
			player.increaseResourceGeneration(Resources.Titanium, 1);
	}

	private void PlaceNuclearZone(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		int playersPick = game.getUserInt("Choose where you want to place the tile", "you cannt place it there", 1,
				counter - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j)) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					} 
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, storage1, storage2);
	}

	private void PlaceVolcano(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int count = 1;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if ((i == 3 && j == 1) || ((i == 4 || i == 5 || i == 6) && j == 0)) {
					System.out.print(count + ",");
					count++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		ArrayList<Integer> availability = new ArrayList<Integer>();
		if (!game.board.isClaimed(3, 1))
			availability.add(1);
		if (!game.board.isClaimed(4, 0))
			availability.add(2);
		if (!game.board.isClaimed(5, 0))
			availability.add(3);
		if (!game.board.isClaimed(6, 0))
			availability.add(4);

		System.out.println("Choose a location to place the Volcano tile:");
		for (int i = 0; i < availability.size(); i++) {
			System.out.print("[" + (i + 1) + "] "
					+ game.board.getName(availability.get(i) + 2, availability.get(i) == 1 ? 1 : 0));
			if (availability.get(i) == 4)
				System.out.print(",\t\tBonus Resources: ");
			else
				System.out.print(",\tBonus Resources: ");

			for (Resources e : game.board.getBonusResources(availability.get(i) + 2, availability.get(i) == 1 ? 1 : 0))
				System.out.print(e.toString() + " ");
			System.out.println();
		}
		int choice = game.getUserInt("Choice",
				"That is not a valid index. Please choose a number from 1-" + availability.size(), 1,
				availability.size()) - 1;
		game.board.claimCustomTile(player, availability.get(choice) + 2, availability.get(choice) == 1 ? 1 : 0);
	}
	
	private void PlaceVolcanoCity(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int count = 1;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if ((i == 3 && j == 1) || ((i == 4 || i == 5 || i == 6) && j == 0)) {
					System.out.print(count + ",");
					count++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		ArrayList<Integer> availability = new ArrayList<Integer>();
		if (!game.board.isClaimed(3, 1))
			availability.add(1);
		if (!game.board.isClaimed(4, 0))
			availability.add(2);
		if (!game.board.isClaimed(5, 0))
			availability.add(3);
		if (!game.board.isClaimed(6, 0))
			availability.add(4);

		System.out.println("Choose a location to place the Volcano tile:");
		for (int i = 0; i < availability.size(); i++) {
			System.out.print("[" + (i + 1) + "] "
					+ game.board.getName(availability.get(i) + 2, availability.get(i) == 1 ? 1 : 0));
			if (availability.get(i) == 4)
				System.out.print(",\t\tBonus Resources: ");
			else
				System.out.print(",\tBonus Resources: ");

			for (Resources e : game.board.getBonusResources(availability.get(i) + 2, availability.get(i) == 1 ? 1 : 0))
				System.out.print(e.toString() + " ");
			System.out.println();
		}
		int choice = game.getUserInt("Choice",
				"That is not a valid index. Please choose a number from 1-" + availability.size(), 1,
				availability.size()) - 1;
		game.board.claimCity(player, availability.get(choice) + 2, availability.get(choice) == 1 ? 1 : 0);
	}

	private void PlaceRestrictedArea(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (counter2 == playersPick) {
					storage1 = i;
					storage2 = j;
					i = board.length;
					break;
				} else if (game.board.isFreeLand(i, j)) {
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, storage1, storage2);
	}

	private void PlaceCommercialDistrict(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (counter2 == playersPick) {
					storage1 = i;
					storage2 = j;
					i = board.length;
					break;
				} else if (game.board.isFreeLand(i, j)) {
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, TileType.Commercial_District, storage1, storage2);
	}

	private void PlaceCapital(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j)) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					}
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, TileType.Capital, storage1, storage2);
	}

	private void PlaceEcologicalZone(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j) && game.board.adjacentToGreenery(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j) && game.board.adjacentToGreenery(i, j)) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					}
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, storage1, storage2);
	}
	
	private void PlaceIndustrialCenter(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j) && game.board.numAdjacentCities(i, j) >= 1) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j) && game.board.numAdjacentCities(i, j) >= 1) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					}
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, storage1, storage2);
	}
	
	private void PlaceUrbanizedArea(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j) && game.board.numAdjacentCities(i, j) >= 2) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j) && game.board.numAdjacentCities(i, j) >= 2) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					}
					counter2++;
				}
			}
		}
		game.board.claimCity(player, storage1, storage2);
	}
	
	private void PlaceMoholeArea(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 =1;
		int storage1 =0;
		int storage2 =0;
		for (int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				if(board[i][j].designated_water && !game.board.isClaimed(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}
		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				if (board[i][j].designated_water && !game.board.isClaimed(i, j)) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					}
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, storage1, storage2);
	}
	
	public void PlaceFlooding(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 =1;
		int storage1 =0;
		int storage2 =0;
		boolean nextTo = false;
		for (int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				if(board[i][j].designated_water && !game.board.isClaimed(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}
		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				if (board[i][j].designated_water && !game.board.isClaimed(i, j)) {
					if (counter2 == playersPick && game.board.adjacentFreeSpace(i, j)) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					} else if (counter2 == playersPick && !game.board.adjacentFreeSpace(i, j)) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						nextTo = true;
						break;
					}
					counter2++;
				}
			}
		}
		ArrayList<Player> adjacentPlayers = new ArrayList<Player>();
		if(nextTo == true) {
			adjacentPlayers = FindAdjacentPlayers(player,storage1,storage2);
			System.out.println("[1] Yes");
			System.out.println("[2] No");
			int playerPicksAgain = game.getUserInt("Would you like to remove up to 4 MegaCredits from another player?", 
					"Invalid answer.", 1, 2);
			
			if(playerPicksAgain==1) {
				boolean existsPlayer = false;
				for(Player e: adjacentPlayers) if(e.checkResource(Resources.MegaCredit, 1)) existsPlayer = true;
				if(!existsPlayer) return;
				System.out.println("\nSelect Player to decrease " + 4 + " " + Resources.MegaCredit.toString() + ":");
				
				for (int i = 0; i < adjacentPlayers.size(); i++)
					System.out.println("\t[" + (i + 1) + "] Player " + adjacentPlayers.get(i).number + " " + Resources.MegaCredit.toString() + ": "
							+ adjacentPlayers.get(i).getResource(Resources.MegaCredit));
				
				int player_choice = game.getUserInt("Player", "There are only 1-" + adjacentPlayers.size() + " players.", 1, adjacentPlayers.size())
						- 1;
				
				for (int i = 0; i < 4; i++)
					if (adjacentPlayers.get(player_choice).checkResource(Resources.MegaCredit, 1))
						adjacentPlayers.get(player_choice).increaseResource(Resources.MegaCredit, -1);
				
			}
			if (playerPicksAgain==2) {
				System.out.println("No one lost any MegaCredits");
			}
		} 
		game.board.claimWater(player, storage1, storage2);
	}
	
	private ArrayList<Player> FindAdjacentPlayers(Player player, int loc1, int loc2) {
		ArrayList<Player> adjacentPlayers = new ArrayList<Player>();
		if (loc1<2&&loc1>=0) {
			return adjacentPlayers;
		} else if (loc1==2) {
			if (loc2==0) {
				if ((game.board.board[loc1][loc2+1].owner != null && !game.board.board[loc1][loc2+1].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2].owner != null && !game.board.board[loc1+1][loc2].owner.equals(player)) ||
					(game.board.board[loc1+1][loc2+1].owner != null && !game.board.board[loc1+1][loc2+1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			} else if (loc2==game.board.board[loc1].length-1) {
				if ((game.board.board[loc1][loc2-1].owner != null && !game.board.board[loc1][loc2-1].owner.equals(player)) ||
					(game.board.board[loc1+1][loc2].owner != null && !game.board.board[loc1+1][loc2].owner.equals(player)) ||
					(game.board.board[loc1+1][loc2+1].owner != null && !game.board.board[loc1+1][loc2+1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			} else {
				if ((game.board.board[loc1][loc2-1].owner != null && !game.board.board[loc1][loc2-1].owner.equals(player)) || 
					(game.board.board[loc1][loc2+1].owner != null && !game.board.board[loc1][loc2+1].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2].owner != null && !game.board.board[loc1+1][loc2].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2+1].owner != null && !game.board.board[loc1+1][loc2+1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			}
		} else if (loc1>2 && loc1<6) {
			if (loc2==0) {
				if ((game.board.board[loc1][loc2+1].owner != null && !game.board.board[loc1][loc2+1].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2].owner != null && !game.board.board[loc1+1][loc2].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2+1].owner != null && !game.board.board[loc1+1][loc2+1].owner.equals(player)) || 
					(game.board.board[loc1-1][loc2].owner !=null && !game.board.board[loc1-1][loc2].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			} else if (loc2==game.board.board[loc1].length-1) {
				if ((game.board.board[loc1][loc2-1].owner != null && !game.board.board[loc1][loc2-1].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2].owner != null && !game.board.board[loc1+1][loc2].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2+1].owner != null && !game.board.board[loc1+1][loc2+1].owner.equals(player)) || 
					(game.board.board[loc1-1][loc2-1].owner !=null && !game.board.board[loc1-1][loc2-1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			} else {
				if ((game.board.board[loc1][loc2-1].owner != null && !game.board.board[loc1][loc2-1].owner.equals(player)) || 
					(game.board.board[loc1][loc2+1].owner != null && !game.board.board[loc1][loc2+1].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2].owner != null && !game.board.board[loc1+1][loc2].owner.equals(player)) || 
					(game.board.board[loc1-1][loc2].owner != null && !game.board.board[loc1-1][loc2].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2+1].owner != null && !game.board.board[loc1+1][loc2+1].owner.equals(player)) || 
					(game.board.board[loc1-1][loc2-1].owner != null && !game.board.board[loc1-1][loc2-1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			}
		} else if (loc1==6) {
			if (loc2==0) {
				if ((game.board.board[loc1][loc2+1].owner != null && !game.board.board[loc1][loc2+1].owner.equals(player)) || 
					(game.board.board[loc1-1][loc2].owner != null && !game.board.board[loc1-1][loc2].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2].owner != null && !game.board.board[loc1+1][loc2].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			} else if (loc2==game.board.board[loc1].length-1) {
				if ((game.board.board[loc1][loc2-1].owner != null && !game.board.board[loc1][loc2-1].owner.equals(player)) || 
					(game.board.board[loc1+1][loc2-1].owner != null && !game.board.board[loc1+1][loc2-1].owner.equals(player)) || 
					(game.board.board[loc1-1][loc2-1].owner != null && !game.board.board[loc1-1][loc2-1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			} else {
				if ((game.board.board[loc1][loc2-1].owner != null && !game.board.board[loc1][loc2-1].owner.equals(player)) || 
					(game.board.board[loc1][loc2+1].owner != null && !game.board.board[loc1][loc2+1].owner.equals(player)) ||
					(game.board.board[loc1+1][loc2].owner != null && !game.board.board[loc1+1][loc2].owner.equals(player)) ||
					(game.board.board[loc1-1][loc2].owner != null && !game.board.board[loc1-1][loc2].owner.equals(player)) ||
					(game.board.board[loc1+1][loc2-1].owner != null && !game.board.board[loc1+1][loc2-1].owner.equals(player)) ||
					(game.board.board[loc1-1][loc2-1].owner != null && !game.board.board[loc1-1][loc2-1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			}
		} else if (loc1>6 && loc1<game.board.board.length-1) {
			if (loc2==0) {
				if ((game.board.board[loc1][loc2+1].owner != null && !game.board.board[loc1][loc2+1].owner.equals(player)) || 
					(game.board.board[loc1-1][loc2].owner != null && !game.board.board[loc1-1][loc2].owner.equals(player)) ||
					(game.board.board[loc1+1][loc2].owner != null && !game.board.board[loc1+1][loc2].owner.equals(player)) ||
					(game.board.board[loc1-1][loc2+1].owner != null && !game.board.board[loc1-1][loc2+1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			} else if (loc2==game.board.board[loc1].length-1) {
				if ((game.board.board[loc1][loc2-1].owner != null && !game.board.board[loc1][loc2-1].owner.equals(player)) || 
					(game.board.board[loc1-1][loc2].owner != null && !game.board.board[loc1-1][loc2].owner.equals(player)) ||
					(game.board.board[loc1+1][loc2-1].owner != null && !game.board.board[loc1+1][loc2-1].owner.equals(player)) ||
					(game.board.board[loc1-1][loc2+1].owner != null && !game.board.board[loc1-1][loc2+1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			} else {
				if ((game.board.board[loc1][loc2-1].owner != null && !game.board.board[loc1][loc2-1].owner.equals(player)) || 
					(game.board.board[loc1][loc2+1].owner != null && !game.board.board[loc1][loc2+1].owner.equals(player)) ||
					(game.board.board[loc1-1][loc2].owner != null && !game.board.board[loc1-1][loc2].owner.equals(player)) ||
					(game.board.board[loc1+1][loc2].owner != null && !game.board.board[loc1+1][loc2].owner.equals(player)) ||
					(game.board.board[loc1-1][loc2+1].owner != null && !game.board.board[loc1-1][loc2+1].owner.equals(player)) ||
					(game.board.board[loc1+1][loc2-1].owner != null && !game.board.board[loc1+1][loc2-1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			}
		} else if (loc1==game.board.board.length-1) {
			if (loc2==0) {
				if ((game.board.board[loc1][loc2+1].owner != null && !game.board.board[loc1][loc2+1].owner.equals(player)) || 
					(game.board.board[loc1-1][loc2].owner != null && !game.board.board[loc1-1][loc2].owner.equals(player)) ||
					(game.board.board[loc1-1][loc2+1].owner != null && !game.board.board[loc1-1][loc2+1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			} else if (loc2==game.board.board[loc1].length-1) {
				if ((game.board.board[loc1][loc2-1].owner != null && !game.board.board[loc1][loc2-1].owner.equals(player)) || 
					(game.board.board[loc1-1][loc2].owner != null && !game.board.board[loc1-1][loc2].owner.equals(player)) ||
					(game.board.board[loc1-1][loc2+1].owner != null && !game.board.board[loc1-1][loc2+1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			} else {
				if ((game.board.board[loc1][loc2-1].owner != null && !game.board.board[loc1][loc2-1].owner.equals(player)) || 
					(game.board.board[loc1][loc2+1].owner != null && !game.board.board[loc1][loc2+1].owner.equals(player)) ||
					(game.board.board[loc1-1][loc2].owner != null && !game.board.board[loc1-1][loc2].owner.equals(player)) ||
					(game.board.board[loc1-1][loc2+1].owner != null && !game.board.board[loc1-1][loc2+1].owner.equals(player))) {
					boolean alreadyInList = false;
					for(Player p: adjacentPlayers) 
						if(p.equals(game.board.board[loc1][loc2].owner))
							alreadyInList = true;
					if(!alreadyInList) adjacentPlayers.add(game.board.board[loc1][loc2].owner);
				}
			}
		} else {
			System.out.println("You shouldnt be able to get here");
		}
		return adjacentPlayers;
	}
	
	private void PlaceBlocker(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j)) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					}
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, storage1, storage2);
	}
	
	private void PlacePreserveNaturalArea(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j) && game.board.adjacentTile(i, j) 
						&& game.board.numAdjacentWater(i, j) == 0) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}
		
		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);		
		for (int i=0;i<board.length;i++) {
			for (int j=0;j<board[i].length;j++) {
				if (game.board.isFreeLand(i, j) && game.board.adjacentTile(i, j) 
						&& game.board.numAdjacentWater(i, j) == 0) {
					if(counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					} 
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, storage1, storage2);
	}
	
	private void PlaceResearchOutpost(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j) && game.board.adjacentTile(i, j) 
						&& game.board.numAdjacentWater(i, j) == 0) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}
		
		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for (int i=0;i<board.length;i++) {
			for (int j=0;j<board[i].length;j++) {
				if (game.board.isFreeLand(i, j) && game.board.adjacentTile(i, j) 
						&& game.board.numAdjacentWater(i, j) == 0) {
					if(counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					} 
					counter2++;
				}
			}
		}
		game.board.claimCity(player, storage1, storage2);
	}
	
	private void PlaceArtificialLake(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 = 1;
		int storage1 = 0;
		int storage2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}

		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (game.board.isFreeLand(i, j)) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					}
					counter2++;
				}
			}
		}
		game.board.claimWater(player, storage1, storage2);
	}

	private void PlaceProtectedValley(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 =1;
		int storage1 =0;
		int storage2 =0;
		for (int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				if(board[i][j].designated_water && !game.board.isClaimed(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}
		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				if (board[i][j].designated_water && !game.board.isClaimed(i, j)) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					}
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, storage1, storage2);
	}
	
	private void PlaceMangrove(Player player) {
		BoardTile[][] board = game.board.getBoard();
		int counter = 1;
		int counter2 =1;
		int storage1 =0;
		int storage2 =0;
		for (int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				if(board[i][j].designated_water && !game.board.isClaimed(i, j)) {
					System.out.print(counter + ",");
					counter++;
				} else {
					board[i][j].print();
					System.out.print(",");
				}
			}
			System.out.println();
		}
		int playersPick = game.getUserInt("Choose where you want to place the tile", "Invalid position.", 1,
				counter - 1);
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				if (board[i][j].designated_water && !game.board.isClaimed(i, j)) {
					if (counter2 == playersPick) {
						storage1 = i;
						storage2 = j;
						i = board.length;
						break;
					}
					counter2++;
				}
			}
		}
		game.board.claimCustomTile(player, storage1, storage2);
	}

	private void WaterOnLand(Player player) {
		System.err.println("WaterOnLand is not implemented");
	}

	private void LandOnWater(Player player) {
		System.err.println("LandOnWater is not implemented");
	}


	private void PlantForCity(Player player) {
		int num_cities = game.board.allCitiesFromRow(0);
		player.increaseResource(Resources.Plant, num_cities);
	}

	private void MegaCreditForCity(Player player) {
		int num_cities = game.board.allCitiesFromRow(2);
		player.increaseResourceGeneration(Resources.MegaCredit, num_cities);
	}

	private void EnergyForCity(Player player) {
		int num_cities = game.board.allCitiesFromRow(0);
		player.increaseResourceGeneration(Resources.Energy, num_cities);
	}

	private void EnergyForPower(Player player) {
		int num_power_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Power) + 1;
		player.increaseResourceGeneration(Resources.Energy, num_power_tags);
	}

	private void PlantForMicrobes(Player player) {
		int num_microbe_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Microbe) + 1;
		player.increaseResourceGeneration(Resources.Plant, num_microbe_tags / 2);
	}

	private void PlantForPlant(Player player) {
		int num_plant_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Plant);
		player.increaseResourceGeneration(Resources.Plant, num_plant_tags);
	}

	private void MegaCreditForBuilding(Player player) {
		int num_building_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Building) + 1;
		player.increaseResourceGeneration(Resources.MegaCredit, num_building_tags / 2);
	}

	private void MegaCreditForSpace(Player player) {
		int num_space_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Space) + 1;
		player.increaseResourceGeneration(Resources.MegaCredit, num_space_tags);
	}

	private void MegaCreditForEarth(Player player) {
		int num_earth_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Earth) + 1;
		player.increaseResourceGeneration(Resources.MegaCredit, num_earth_tags);
	}

	private void MegaCreditForEarth2(Player player) {
		int num_earth_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Earth);
		player.increaseResourceGeneration(Resources.MegaCredit, num_earth_tags);
	}

	private void TRForJovian(Player player) {
		int num_jovian_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Jovian) + 1;
		for (int i = 0; i < num_jovian_tags; i++)
			TerraformRating(player);
	}

	private void MegaCreditForOpponentSpace(Player player) {
		int num_space_tags = ReusableAbilityFunctions.getNumTagsAll(player, Tag.Space)
				- ReusableAbilityFunctions.getNumTags(player, Tag.Space);
		player.increaseResourceGeneration(Resources.MegaCredit, num_space_tags);
	}

	private void MegaCreditForAllEvent(Player player) {
		int num_event_tags = ReusableAbilityFunctions.getNumTagsAll(player, Tag.Event);
		player.increaseResource(Resources.MegaCredit, num_event_tags);
	}

	private void PlantOrEnergy(Player player) {
		int choice = game.getUserInt(
				"Choose one of the following:\\n\\t[1] Increase Plant generation by 1\\n\\t[2] Increase Energy generation by 2\nChoice",
				"Please choose 1 or 2", 1, 2);
		if (choice == 1)
			player.increaseResourceGeneration(Resources.Plant, 1);
		else if (choice == 2)
			player.increaseResourceGeneration(Resources.Energy, 2);
	}

	private void HeatForMegaCredit(Player player) {
		int heat_generation = player.getResourceGeneration(Resources.Heat);
		if (heat_generation == 0)
			return;
		int num_heat_decrease = 1;
		if (heat_generation > 1)
			num_heat_decrease = game.getUserInt("You have " + heat_generation
					+ " Heat generation. How many would you like to convert into MegaCredit generation?\nNumber",
					"You must input a value between 0 and " + heat_generation, 0, heat_generation);
		player.increaseResourceGeneration(Resources.Heat, -1 * num_heat_decrease);
		player.increaseResourceGeneration(Resources.MegaCredit, num_heat_decrease);
	}

	private void MorePlants(Player player) {
		int num_plant_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Plant);
		if (num_plant_tags >= 3)
			player.increaseResourceGeneration(Resources.Plant, 4);
		else
			player.increaseResourceGeneration(Resources.Plant, 1);
	}

	private void Sabotage(Player player) {
		System.out.println("Choose a player to remove resources from:");
		for (int i = 0; i < game.players.length; i++) {
			Player e = game.players[i];
			System.out.println("\t[" + (i + 1) + "] Player " + e.number + ": " + e.getResource(Resources.Titanium)
					+ " Titanium, " + e.getResource(Resources.Steel) + " Steel, " + e.getResource(Resources.MegaCredit)
					+ " MegaCredits");
		}
		int player_num = game.getUserInt("Player", "There are only players 1-" + game.players.length, 1,
				game.players.length) - 1;
		System.out.println();
		Player e = game.players[player_num];

		int choice = game.getUserInt(
				"Which resource do you want to remove?\n\t[1] 3 Titanium\n\t[2] 4 Steel\n\t[3] 7 MegaCredits\nChoice",
				"Please choose 1, 2, or, 3.", 1, 3);
		if (choice == 1) {
			for (int i = 0; i < e.getResource(Resources.Titanium) && i < 3; i++) {
				game.players[player_num].increaseResource(Resources.Titanium, -1);
			}
		} else if (choice == 2) {
			for (int i = 0; i < e.getResource(Resources.Steel) && i < 4; i++) {
				game.players[player_num].increaseResource(Resources.Steel, -1);
			}
		} else {
			for (int i = 0; i < e.getResource(Resources.MegaCredit) && i < 7; i++) {
				game.players[player_num].increaseResource(Resources.MegaCredit, -1);
			}
		}
	}

	private void HiredRaiders(Player player) {
		System.out.println("Choose a player to steal resources from:");
		for (int i = 0; i < game.players.length; i++) {
			Player e = game.players[i];
			System.out.println("\t[" + (i + 1) + "] Player " + e.number + ": " + e.getResource(Resources.Steel)
					+ " Steel, " + e.getResource(Resources.MegaCredit) + " MegaCredits");
		}
		int player_num = game.getUserInt("Player", "There are only players 1-" + game.players.length, 1,
				game.players.length) - 1;
		System.out.println();
		Player e = game.players[player_num];
		int num_steel = e.getResource(Resources.Steel), num_megacredits = e.getResource(Resources.MegaCredit);

		if (num_steel == 0) {
			if (num_megacredits == 0) {
				System.out.println("Player " + e.number + " doesn't have any resources to steal! Too bad.");
				return;
			}
			System.out.println("Stealing player " + e.number + "'s MegaCredits.");
			for (int i = 0; i < num_megacredits && i < 3; i++) {
				game.players[player_num].increaseResource(Resources.MegaCredit, -1);
				player.increaseResource(Resources.MegaCredit, 1);
			}
		} else if (num_megacredits == 0) {
			System.out.println("Stealing player " + e.number + "'s Steel.");
			for (int i = 0; i < num_steel && i < 2; i++) {
				game.players[player_num].increaseResource(Resources.Steel, -1);
				player.increaseResource(Resources.Steel, 1);
			}
		} else {
			int choice = game.getUserInt(
					"Which resource do you want to steal?\n\t[1] 2 Steel\n\t[2] 3 MegaCredits\nChoice",
					"Please choose 1 or 2.", 1, 2);
			if (choice == 1) {
				System.out.println("Stealing player " + e.number + "'s Steel.");
				for (int i = 0; i < num_steel && i < 2; i++) {
					game.players[player_num].increaseResource(Resources.Steel, -1);
					player.increaseResource(Resources.Steel, 1);
				}
			} else {
				for (int i = 0; i < num_megacredits && i < 3; i++) {
					game.players[player_num].increaseResource(Resources.MegaCredit, -1);
					player.increaseResource(Resources.MegaCredit, 1);
				}
			}
		}
	}

	private void Card1of3(Player player) {
		if (!game.canDrawCard(1)) {
			System.err.println("There are no cards in the deck to draw.");
			return;
		} else if (!game.canDrawCard(2)) {
			player.increaseResource(Resources.Card, 1);
			return;
		}
		int num_cards = 2;
		if (game.canDrawCard(3))
			num_cards = 3;

		System.out.println("Cards:");
		ArrayList<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < num_cards; i++) {
			System.out.print("[" + (i + 1) + "] ");
			game.deck.peek().print();
			cards.add(game.deck.remove());
		}
		int choice = game.getUserInt("Choose 1 card",
				num_cards == 2 ? "Please choose 1 or 2." : "Please choose 1, 2, or 3.", 1, num_cards);

		int i = 1;
		for (; i != choice; i++)
			game.discard.add(cards.remove(0));
		player.hand.add(cards.remove(0));
		for (i++; cards.size() > 0; i++)
			game.discard.add(cards.remove(0));
	}

	private void Card2of4(Player player) {
		if (!game.canDrawCard(1)) {
			System.err.println("There are no cards in the deck to draw.");
			return;
		} else if (!game.canDrawCard(2)) {
			player.increaseResource(Resources.Card, 1);
			return;
		} else if (!game.canDrawCard(3)) {
			player.increaseResource(Resources.Card, 2);
		}
		int num_cards = 3;
		if (game.canDrawCard(4))
			num_cards = 4;

		ArrayList<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < num_cards; i++) {
			cards.add(game.deck.remove());
		}
		for (int k = 0; k < 2; k++) {
			System.out.println("\nCards:");
			for (int i = 0; i < cards.size(); i++) {
				System.out.print("[" + (i + 1) + "] ");
				cards.get(i).print();
			}
			int choice = game.getUserInt("Choose a card", "Please choose a number between 1 and " + num_cards, 1,
					num_cards);
			player.hand.add(cards.remove(choice - 1));
		}
		while (cards.size() > 0)
			game.discard.add(cards.remove(0));
	}

	private void Add2Microbe(Player player) {
		if(ReusableAbilityFunctions.hasCounter(player, Counter.Microbe))
			ReusableAbilityFunctions.chooseCounter(player, Counter.Microbe, 2);
		else System.out.println("You do not have another card to add a counter to.");
	}
	
	private void Add3Microbe(Player player) {
		if(ReusableAbilityFunctions.hasCounter(player, Counter.Microbe))
			ReusableAbilityFunctions.chooseCounter(player, Counter.Microbe, 3);
		else System.out.println("You do not have another card to add a counter to.");
	}
	
	private void AddAnimal(Player player) {
		if(ReusableAbilityFunctions.hasCounter(player, Counter.Animal))
			ReusableAbilityFunctions.chooseCounter(player, Counter.Animal, 1);
		else System.out.println("You do not have another card to add a counter to.");
	}
	
	private void Add2Animal(Player player) {
		if(ReusableAbilityFunctions.hasCounter(player, Counter.Animal))
			ReusableAbilityFunctions.chooseCounter(player, Counter.Animal, 2);
		else System.out.println("You do not have another card to add a counter to.");
	}

	private void Add4PlantOr2Animal(Player player) {
		if(!ReusableAbilityFunctions.hasCounter(player, Counter.Animal)) {
			System.out.println("You do not have another card to add a counter to.");
			player.increaseResource(Resources.Plant, 4);
			return;
		}
		System.out.println("Choice:\n\t[1] Gain 4 Plant");
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(Counter.Animal)) cards.add(e);
		int choice = 0;
		if(cards.size() > 0) {
			for(int i = 0; i<cards.size(); i++) {
				System.out.println("\t[" + (i+2) + "] Add 2 resources to " + cards.get(i).name);
			}
			choice = game.getUserInt("Choice", "Not a valid number.", 1, cards.size() + 1);
		}
		if(choice == 1) {
			player.increaseResource(Resources.Plant, 4);
		} else {
			for(int i = 0; i<2; i++) ReusableAbilityFunctions.increaseCounter(player,cards.get(choice-2).name);
		}
	}
	
	private void Add5PlantOr4Animal(Player player) {
		if(!ReusableAbilityFunctions.hasCounter(player, Counter.Animal)) {
			System.out.println("You do not have another card to add a counter to.");
			player.increaseResource(Resources.Plant, 5);
			return;
		}
		System.out.println("Choice:\n\t[1] Gain 5 Plant");
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(Counter.Animal)) cards.add(e);
		int choice = 0;
		if(cards.size() > 0) {
			for(int i = 0; i<cards.size(); i++) {
				System.out.println("\t[" + (i+2) + "] Add 4 resources to " + cards.get(i).name);
			}
			choice = game.getUserInt("Choice", "Not a valid number.", 1, cards.size() + 1);
		}
		if(choice == 1) {
			player.increaseResource(Resources.Plant, 5);
		} else {
			for(int i = 0; i<4; i++) ReusableAbilityFunctions.increaseCounter(player,cards.get(choice-2).name);
		}
	}
	
	private void Add3PlantOr2AnimalOr3Microbe(Player player) {
		if(!ReusableAbilityFunctions.hasCounter(player, Counter.Animal) && !ReusableAbilityFunctions.hasCounter(player, Counter.Microbe)) {
			System.out.println("You do not have another card to add a counter to.");
			player.increaseResource(Resources.Plant, 3);
			return;
		}
		System.out.println("Choice:\n\t[1] Gain 3 Plant");
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(Counter.Microbe)) cards.add(e);
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(Counter.Animal)) cards.add(e);
		int choice = 0;
		if(cards.size() > 0) {
			for(int i = 0; i<cards.size(); i++) {
				if(cards.get(i).counter_type.equals(Counter.Microbe)) System.out.println("\t[" + (i+2) + "] Add 3 resources to " + cards.get(i).name);
				else System.out.println("\t[" + (i+2) + "] Add 2 resources to " + cards.get(i).name);
			}
			choice = game.getUserInt("Choice", "Not a valid number.", 1, cards.size() + 1);
		}
		if(choice == 1) {
			player.increaseResource(Resources.Plant, 5);
		} else {
			if(cards.get(choice).counter_type.equals(Counter.Microbe)) for(int i = 0; i<3; i++) ReusableAbilityFunctions.increaseCounter(player,cards.get(choice-2).name);
			else for(int i = 0; i<2; i++) ReusableAbilityFunctions.increaseCounter(player,cards.get(choice-2).name);
		}
	}
	
	private void AddCounter(Player player) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(Counter.Microbe)) cards.add(e);
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(Counter.Animal)) cards.add(e);
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(Counter.Science)) cards.add(e);
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(Counter.Fighter)) cards.add(e);
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(Counter.Floater)) cards.add(e);
		int choice = 0;
		if(cards.size() > 1) {
			for(int i = 0; i<cards.size(); i++) {
				System.out.println("\t[" + (i+1) + "] Add 1 resource to " + cards.get(i).name);
			}
			choice = game.getUserInt("Choice", "Not a valid number.", 1, cards.size()) - 1;
		}
		ReusableAbilityFunctions.increaseCounter(player,cards.get(choice).name);
	}
	
	private void RemoveAnimalOrPlant(Player player) {
		ArrayList<Player> players = new ArrayList<Player>();
		for(Player p: game.players) if(!p.equals(player)) {
			if(p.checkResource(Resources.Plant, 1) || ReusableAbilityFunctions.hasCounter(p, Counter.Animal)) players.add(p);
		}
		int choice = 0;
		if(players.size() > 0) {
			System.out.println("Players:\n");
			for(int i = 0; i<players.size(); i++) {
				System.out.print("\t[" + (i+1) + "] Player " + players.get(i).number + ", Plants: " + players.get(i).getResource(Resources.Plant) + ", Animal Counters: ");
				if(ReusableAbilityFunctions.canRemoveCounter(players.get(i), Counter.Animal)) System.out.println("true");
				else System.out.println("false");
			}
			choice = game.getUserInt("Which player would you like to remove resources from (Up to 5 plants or 2 animal counters)", "Not a valid player", 1, players.size());
		}
		Player p = players.get(choice);
		if(!ReusableAbilityFunctions.canRemoveCounter(p, Counter.Animal)) {
			for(int i = 0; p.checkResource(Resources.Plant, 1) && i < 5; i++) {
				p.increaseResource(Resources.Plant, -1);
			}
		} else if(!p.checkResource(Resources.Plant, 1)) {
			ReusableAbilityFunctions.removeCounter(p, Counter.Animal, 2);
		}
		else {
			choice = game.getUserInt("\t[1] Remove Plants\n\t[2] Remove Animal counters\nChoice", "Please choose 1 or 2.", 1, 2); 
			if(choice == 1) {
				for(int i = 0; p.checkResource(Resources.Plant, 1) && i < 5; i++) {
					p.increaseResource(Resources.Plant, -1);
				}
			} else {
				ReusableAbilityFunctions.removeCounter(p, Counter.Animal, 2);
			}
		}
	}
	
	private void DuplicateProduction(Player player) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Card e: player.played) 
			if(e.tag != null)
				for(Tag t: e.tag)
					if(t.equals(Tag.Building))
						if(e.resource_generation_add != null && e.resource_generation_add.length > 0)
							cards.add(e);
		int choice = 0;
		if(cards.size() > 1) {
			System.out.println("Choose a card to duplicate the production of:");
			for(int i = 0; i<cards.size(); i++) {
				System.out.print("\t[" + (i+1) + "] " + cards.get(i).name + ": ");
				for(Resources e: cards.get(i).resource_generation_add) System.out.print(e.toString() + ", ");
				System.out.println();
			}
			choice = game.getUserInt("Card", "Invalid choice.", 1, cards.size()) - 1;
		}
		for(Resources e: cards.get(choice).resource_generation_add) player.increaseResourceGeneration(e, 1);
	}
	
	private void FundAward(Player player) {
		game.awards.fundFreeAward();
	}
	
	private void Prelude(Player player) {
		ArrayList<Card> choices = new ArrayList<Card>();
		for(int i = 0; i<3; i++)
			choices.add(game.removePrelude());
		player.choosePreludes(choices, reader, 1);
	}
	
	private void DrawPlantCards(Player player) {
		ReusableAbilityFunctions.drawTagCards(player, Tag.Plant, 2);
	}
	
	private void DrawSpaceCards(Player player) {
		ReusableAbilityFunctions.drawTagCards(player, Tag.Space, 2);
	}
	
	private void DrawCounterCards(Player player) {
		System.out.println("Drawing counter cards");
		Game game = player.game;
		int num_added = 0;
		boolean shuffled = false;
		while(true) {
			while(game.deck.size() > 0) {
				if(num_added == 2) return;
				boolean added = false;
				Card temp = game.deck.remove();
				if(temp.counter_type != null) {
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
	
	// --------------------------------------------------------
	// -------------- Custom activated functions --------------
	// --------------------------------------------------------

	private boolean EnergyToCard(Player player) {
		if (!player.checkResource(Resources.Energy, 1)) {
			System.err.println("You do not have Enough energy.");
			return false;
		} else if (!game.canDrawCard(1)) {
			return false;
		}
		player.increaseResource(Resources.Energy, 1);
		player.increaseResource(Resources.Card, 1);
		return true;
	}

	private boolean EnergyToMegaCredit(Player player) {
		if (!player.checkResource(Resources.Energy, 1)) {
			System.err.println("You do not have enough Energy.");
			return false;
		}
		int num_convert = 0;
		if (player.getResource(Resources.Energy) == 1)
			num_convert = 1;
		else {
			num_convert = game.getUserInt(
					"You can convert any number of Energy into MegaCredits. \nYou have "
							+ player.getResource(Resources.Energy) + " Energy. \nNumber to convert",
					"Not a valid number.", 1, player.getResource(Resources.Energy));
			if (num_convert == 0)
				return false;
		}

		player.increaseResource(Resources.Energy, -1 * num_convert);
		player.increaseResource(Resources.MegaCredit, num_convert);
		return true;
	}

	private boolean EnergyToMegaCreditCity(Player player) {
		if (!player.checkResource(Resources.Energy, 1)) {
			System.err.println("You do not have enough Energy.");
			return false;
		}
		int num_cities = game.board.allCitiesFromRow(2);
		if (num_cities == 0) {
			System.err.println("There are no cities in play on Mars.");
			return false;
		}
		player.increaseResource(Resources.Energy, -1);
		player.increaseResource(Resources.MegaCredit, num_cities);
		return true;
	}

	private boolean EnergyToTR(Player player) {
		if (!player.checkResourceGeneration(Resources.Energy, 1)) {
			System.err.println("You do not have enough Energy generation.");
			return false;
		}
		player.increaseResourceGeneration(Resources.Energy, -1);
		TerraformRating(player);
		return true;
	}

	private boolean EnergyToOxygen(Player player) {
		if (!player.checkResource(Resources.Energy, 3)) {
			System.err.println("You do not have enough Energy.");
			return false;
		} else if (game.board.oxygen >= 14) {
			System.err.println("Oxygen is already at 14%.");
			return false;
		}
		player.increaseResource(Resources.Energy, -3);
		game.board.increaseOxygen(player);
		return true;
	}

	private boolean EnergyToOxygenSteel(Player player) {
		if (!player.checkResource(Resources.Energy, 4)) {
			System.err.println("You do not have enough Energy.");
			return false;
		}
		player.increaseResource(Resources.Energy, -4);
		player.increaseResource(Resources.Steel, 1);
		Oxygen(player);
		return true;
	}

	private boolean EnergyToOxygenSteel2(Player player) {
		if (!player.checkResource(Resources.Energy, 4)) {
			System.err.println("You do not have enough Energy.");
			return false;
		}
		player.increaseResource(Resources.Energy, -4);
		player.increaseResource(Resources.Steel, 2);
		Oxygen(player);
		return true;
	}

	private boolean EnergyToOxygenTitanium(Player player) {
		if (!player.checkResource(Resources.Energy, 4)) {
			System.err.println("You do not have enough Energy.");
			return false;
		}
		player.increaseResource(Resources.Energy, -4);
		player.increaseResource(Resources.Titanium, 1);
		Oxygen(player);
		return true;
	}

	private boolean SteelToMegaCredit(Player player) {
		if (!player.checkResource(Resources.Steel, 1)) {
			System.err.println("You do not have enough Steel.");
			return false;
		}
		player.increaseResource(Resources.Steel, -1);
		player.increaseResource(Resources.MegaCredit, 5);
		return true;
	}

	private boolean HeatToTR(Player player) {
		if (!player.checkResource(Resources.Heat, 8)) {
			System.err.println("You do not have enough Heat.");
			return false;
		}
		player.increaseResource(Resources.Heat, -8);
		TerraformRating(player);
		return true;
	}

	private boolean Draw2Cards(Player player) {
		if (!game.canDrawCard(1)) {
			return false;
		}
		player.increaseResource(Resources.Card, 2);
		return true;
	}

	private boolean MegaCreditToEnergy(Player player) {
		if(!ReusableAbilityFunctions.pay(player, "MegaCredit to Energy", 7)) {
			return false;
		}
		player.increaseResourceGeneration(Resources.Energy, 1);
		return true;
	}

	private boolean MegaCreditToHeat(Player player) {
		if(!ReusableAbilityFunctions.pay(player, "MegaCredit to Heat", 10)) {
			return false;
		}
		player.increaseResourceGeneration(Resources.Heat, 2);
		return true;
	}

	private boolean PlantSteelToMegaCredit(Player player) {
		if (!(player.checkResource(Resources.Plant, 1) || player.checkResource(Resources.Steel, 1))) {
			System.err.println("You do not have enough Plants or Steel.");
			return false;
		}
		Resources e;
		if (!player.checkResource(Resources.Plant, 1)) {
			e = Resources.Steel;
		} else if (!player.checkResource(Resources.Steel, 1)) {
			e = Resources.Plant;
		} else {
			int choice = game.getUserInt(
					"You can spend 1 Plant or 1 Steel. Which would you like to spend?\n\t[1] Plant\n\t[2] Steel\nChoice",
					"Please select 1 or 2.", 1, 2);
			if (choice == 1)
				e = Resources.Plant;
			else
				e = Resources.Steel;
		}
		player.increaseResource(e, -1);
		player.increaseResource(Resources.MegaCredit, 7);
		return true;
	}

	private boolean MegaCreditSteelToWater(Player player) {
		Resources[] money = { Resources.MegaCredit, Resources.Steel };
		if (!player.hasMoney(money, 8)) {
			System.err.println("You do not have enough MegaCredit or Steel.");
			return false;
		} else if (game.board.water >= 9) {
			System.err.println("All water has been placed.");
			return false;
		}
		player.purchaseWithResource(money, 8);
		Water(player);
		return true;
	}

	private boolean MegaCreditTitaniumToWater(Player player) {
		Resources[] money = { Resources.MegaCredit, Resources.Titanium };
		if (!player.hasMoney(money, 12)) {
			System.err.println("You do not have enough MegaCredit or Titanium.");
			return false;
		} else if (game.board.water >= 9) {
			System.err.println("All water has been placed.");
			return false;
		}
		player.purchaseWithResource(money, 12);
		Water(player);
		return true;
	}

	private boolean PurchaseCard(Player player) {
		Resources[] money = { Resources.MegaCredit };
		if (!game.canDrawCard(1)) {
			System.out.println("There are no cards to draw!");
			return false;
		} else if (!player.hasMoney(money, 3)) {
			System.out.println("You cannot afford this card.");
			return false;
		}
		System.out.println("Card:");
		game.deck.peek().print();
		int choice = game.getUserInt("Do you want to purchase this card?\n\t[1] Yes \n\t[2] No",
				"Please choose 1 or 2.", 1, 2);
		if (choice == 1) {
			player.purchaseWithResource(money, 3);
			player.increaseResource(Resources.Card, 1);
			return true;
		} else
			return false;
	}

	private boolean MegaCreditToCard(Player player) {
		Resources[] money = { Resources.MegaCredit };
		if (!game.canDrawCard(1)) {
			System.out.println("There are no cards to draw!");
			return false;
		} else if (!player.hasMoney(money, 2)) {
			System.out.println("You cannot afford this card.");
			return false;
		}
		player.purchaseWithResource(money, 2);
		player.increaseResource(Resources.Card, 1);
		return true;
	}
	
	private boolean MegaCreditToSteel(Player player) {
		Resources[] money = { Resources.MegaCredit };
		if (!player.hasMoney(money, 7)) {
			System.out.println("You cannot afford this ability.");
			return false;
		}
		player.purchaseWithResource(money, 3);
		player.increaseResourceGeneration(Resources.Steel, 1);
		return true;
	}
	
	private boolean AddFleet(Player player) {
		if(!player.checkResource(Resources.Titanium, 1)) {
			System.err.println("You cannot afford this ability.");
			return false;
		}
		player.increaseResource(Resources.Titanium, -1);
		ReusableAbilityFunctions.increaseCounter(player, "SECURITY FLEET");
		return true;
	}
	
	private boolean AddFungus(Player player) {
		if(!ReusableAbilityFunctions.hasCounter(player, Counter.Microbe)) {
			System.err.println("You do not have another card to add a counter to.");
			return false;
		}
		ReusableAbilityFunctions.chooseCounter(player, Counter.Microbe, 1);
		return true;
	}
	
	private boolean AddColdFungus(Player player) {
		if(!ReusableAbilityFunctions.hasCounter(player, Counter.Microbe)) {
			System.out.println("You do not have another card to add a counter to.");
			player.increaseResource(Resources.Plant, 1);
			return true;
		}
		System.out.println("Choice:\n\t[1] Gain 1 Plant");
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Card e: player.played) if(e.counter_type != null && e.counter_type.equals(Counter.Microbe)) cards.add(e);
		int choice = 0;
		if(cards.size() > 1) {
			for(int i = 0; i<cards.size(); i++) {
				System.out.println("\t[" + (i+2) + "] Add 2 resources to " + cards.get(i).name);
			}
			choice = game.getUserInt("Choice", "Not a valid number.", 1, cards.size() + 1);
		}
		if(choice == 1) {
			player.increaseResource(Resources.Plant, 1);
		} else
			ReusableAbilityFunctions.increaseCounter(player,cards.get(choice-2).name);
		return true;
	}
	
	private boolean CounterToTR(Player player) {
		for(Card e: player.played) if(e.name != null && e.name.equals("NITRITE REDUCING BACTERIA")) {
			if(e.counter < 3) {
				System.err.println("You don't have enough counters on this card.");
				return false;
			}
			ReusableAbilityFunctions.decreaseCounter(e, 3);
			TerraformRating(player);
		}
		return true;
	}
	
	private boolean CounterToOxygen(Player player) {
		if(game.board.oxygen >= 14) {
			System.err.println("Oxygen is already maxed.");
			return false;
		} 
		for(Card e: player.played) if(e.name != null && e.name.equals("REGOLITH EATERS")) {
			if(e.counter < 2) {
				System.err.println("You don't have enough counters on this card.");
				return false;
			}
			ReusableAbilityFunctions.decreaseCounter(e, 2);
			Oxygen(player);
		}
		return true;
	}
	
	private boolean CounterToTemperature(Player player) {
		if(game.board.temperature >= 8) {
			System.err.println("Temperature is already maxed.");
			return false;
		} 
		for(Card e: player.played) if(e.name != null && e.name.equals("GHG PRODUCING BACTERIA")) {
			if(e.counter < 2) {
				System.err.println("You don't have enough counters on this card.");
				return false;
			}
			ReusableAbilityFunctions.decreaseCounter(e, 3);
			Temperature(player);
		}
		return true;
	}
	
	private boolean EnergyToScience(Player player) {
		if(player.checkResource(Resources.Energy, 6)) {
			System.err.println("You do not have enough Energy.");
			return false;
		}
		player.increaseResource(Resources.Energy, -6);
		ReusableAbilityFunctions.increaseCounter(player, "PHYSICS COMPLEX");
		return false;
	}
	
	private boolean MegaCreditSearchMicrobe(Player player) {
		Resources[] money = { Resources.MegaCredit };
		if (!game.canDrawCard(1)) {
			System.err.println("There are no cards in the deck to draw.");
			return false;
		} else if (!player.hasMoney(money, 1)) {
			System.err.println("You can't afford this ability.");
			return false;
		}
		player.purchaseWithResource(money, 1);
		Card e = game.deck.remove();
		for(Tag k: e.tag) if(k.equals(Tag.Microbe)) {
			ReusableAbilityFunctions.increaseCounter(player, "SEARCH FOR LIFE");
		}
		game.discard.add(e);
		return true;
	}
	
	private boolean StealAnimal(Player player) {
		ArrayList<Player> players = new ArrayList<Player>();
		for(Player p: game.players) if(!p.equals(player)) {
			if(ReusableAbilityFunctions.canRemoveCounter(p, Counter.Animal)) players.add(p);
		}
		if(players.size() == 0) {
			if(ReusableAbilityFunctions.hasCounter(player, Counter.Animal)) {
				System.out.println("No other players have an Animal counter. Remove an Animal counter from another one of your cards.");
				players.add(player);
			}
			else {
				System.out.println("No players have an Animal counter.");
				return false;
			}
		}
		int choice = 0;
		if(players.size() > 0) {
			System.out.println("Players:\n");
			for(int i = 0; i<players.size(); i++) {
				System.out.print("\t[" + (i+1) + "] Player " + players.get(i).number);
			}
			choice = game.getUserInt("Which player would you like to remove resources from", "Not a valid player", 1, players.size());
		}
		Player p = players.get(choice);
		ReusableAbilityFunctions.removeCounter(p, Counter.Animal, 1);
		ReusableAbilityFunctions.increaseCounter(player, "PREDATORS");
		return true;
	}
	
	private boolean StealMicrobe(Player player) {
		ArrayList<Player> players = new ArrayList<Player>();
		for(Player p: game.players) if(!p.equals(player)) {
			if(ReusableAbilityFunctions.canRemoveCounter(p, Counter.Microbe)) players.add(p);
		}
		if(players.size() == 0) {
			if(ReusableAbilityFunctions.hasCounter(player, Counter.Microbe)) {
				System.out.println("No other players have a Microbe counter. Remove an Microbe counter from another one of your cards.");
				players.add(player);
			}
			else {
				System.out.println("No players have an Microbe counter.");
				return false;
			}
		}
		int choice = 0;
		if(players.size() > 0) {
			System.out.println("Players:\n");
			for(int i = 0; i<players.size(); i++) {
				System.out.print("\t[" + (i+1) + "] Player " + players.get(i).number);
			}
			choice = game.getUserInt("Which player would you like to remove resources from", "Not a valid player", 1, players.size());
		}
		Player p = players.get(choice);
		ReusableAbilityFunctions.removeCounter(p, Counter.Microbe, 1);
		ReusableAbilityFunctions.increaseCounter(player, "ANTS");
		return true;
	}
	private boolean HeatToResource(Player player) {
		if(!player.checkResource(Resources.Heat, 1)) {
			System.out.println("You don't have any Heat to convert.");
			return false;
		}
		int choice = 1;
		if(player.checkResource(Resources.Heat, 2)) {
			choice = game.getUserInt("You have " + player.getResource(Resources.Heat) + " Heat. How many do you wish to convert", "Choose a numebr between 0 and " + (player.getResource(Resources.Heat) < 8 ? player.getResource(Resources.Heat) : 8), 0, (player.getResource(Resources.Heat) < 8 ? player.getResource(Resources.Heat) : 8));
		}
		if(choice == 0) return false;
		player.increaseResource(Resources.Heat, -1*choice);
		Resources[] resources = {Resources.Steel, Resources.Titanium, Resources.Plant, Resources.Energy};
		System.out.println("\nIncrease one of the following resource by " + choice + ":");
		for(int i = 0; i<resources.length; i++) System.out.println("\t[" + (i+1) + "] " + resources[i].toString() + ":\t" + player.getResource(resources[i]));
		player.increaseResource(resources[game.getUserInt("Choice", "Please choose a value between 1 and 4", 1, 4) - 1], choice);
		return true;
	}
	

	// ------------------------------------------------------
	// -------------- Custom passive functions --------------
	// ------------------------------------------------------

	private void BlockedCounters(Player player) {
		for(Card e: player.played) 
			if(e.counter_type != null && (e.counter_type.equals(Counter.Animal) || e.counter_type.equals(Counter.Microbe)))
				e.steal_counter = false;
	}
	
	private boolean UNMI(Player player) {
		if(player.terraform_rating <= player.terraform_rating_before) {
			System.err.println("Your terraform rating has not raised this generation.");
			return false;
		}
		if(!ReusableAbilityFunctions.pay(player, "UNMI", 3)) return false;
		TerraformRating(player);
		return true;
	}
	
	private boolean Robinson(Player player) {
		Resources[] money = {Resources.MegaCredit};
		if(!player.hasMoney(money, 4)) {
			System.out.println("You need 4 MegaCredits for this ability.");
			return false;
		}
		player.purchaseWithResource(money, 4);
		ArrayList<Resources> lowest= new ArrayList<Resources>();
		Resources[] resources = {Resources.MegaCredit, Resources.Steel, Resources.Titanium, Resources.Plant, Resources.Energy, Resources.Heat};
		
		for(Resources e: resources) {
			if(lowest.size() == 0) lowest.add(e);
			else {
				if(player.getResourceGeneration(e) > player.getResourceGeneration(lowest.get(0))) {
					lowest.clear();
					lowest.add(e);
				} else if(player.getResourceGeneration(e) == player.getResourceGeneration(lowest.get(0)))
					lowest.add(e);
			}
		}
		
		if(lowest.size() == 1) player.increaseResourceGeneration(lowest.get(0), 1);
		else {
			System.out.println("Which resource generation would you like to increase:");
			for(int i = 0; i<lowest.size(); i++) System.out.println("\t[" + (i+1) + "] " + lowest.get(i).toString());
			int choice = game.getUserInt("Choice", "Invalid.", 1, lowest.size()) - 1;
			player.increaseResourceGeneration(lowest.get(choice), 1);
		}
		return true;
	}
	
	private void Celestic(Player player) {
		Card celestic = new Card("CELESTIC COUNTER", 0);
		celestic.tag = new Tag[1];
		celestic.tag[0] = Tag.Custom;
		celestic.counter_type = Counter.Floater;
		celestic.abilities = new Ability[1];
		celestic.abilities[0] = Ability.PointPerCounter3;
		player.played.add(celestic);
	}
	
	private void AddFloater(Player player) {
		AddCounter(player);
	}
	
	// ----------------------------------------------------------
	// -------------- Custom end-of-game functions --------------
	// ----------------------------------------------------------
}



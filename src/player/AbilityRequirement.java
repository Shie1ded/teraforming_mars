package player;

import java.util.ArrayList;

import board.BoardTile;
import board.Resources;
import board.TileType;

public interface AbilityRequirement {
	static public boolean PlaceMine(Player player) {
		if(!player.game.board.freeLandSpace()) 
			return false;
		for(int i = 0; i<player.game.board.board.length; i++)
			for(int j = 0; j<player.game.board.board[i].length; j++)
				if (player.game.board.isFreeLand(i, j) && (player.game.board.board[i][j].hasResource(Resources.Steel) || player.game.board.board[i][j].hasResource(Resources.Titanium)))
					return true;
		return false;
	}

	static public boolean PlaceNuclearZone(Player player) {
		if (player.game.board.freeLandSpace())
			return true;
		else
			return false;
	}

	static public boolean PlaceVolcano(Player player) {
		ArrayList<Integer> availability = new ArrayList<Integer>();
		if (!player.game.board.isClaimed(3, 1))
			availability.add(1);
		if (!player.game.board.isClaimed(4, 0))
			availability.add(2);
		if (!player.game.board.isClaimed(5, 0))
			availability.add(3);
		if (!player.game.board.isClaimed(6, 0))
			availability.add(4);
		return availability.size() > 0;
	}

	static public boolean PlaceRestrictedArea(Player player) {
		if (player.game.board.freeLandSpace())
			return true;
		else
			return false;
	}

	static public boolean PlaceCommercialDistrict(Player player) {
		if (player.game.board.freeLandSpace())
			return true;
		else
			return false;
	}

	static public boolean PlaceCapital(Player player) {
		if (player.game.board.freeLandSpace())
			return true;
		else
			return false;
	}

	static public boolean PlantForCity(Player player) {
		int num_cities = player.game.board.allCitiesFromRow(0);
		if (num_cities == 0) {
			int choice = player.game.getUserInt(
					"There are no cities in play. Do you still want to play this card?\n\t[1] Yes\n\t[2] No\nChoice",
					"Invalid selection. Please choose 1 or 2.", 1, 2);
			if (choice == 2)
				return false;
		}
		return true;
	}

	static public boolean MegaCreditForCity(Player player) {
		int num_cities = player.game.board.allCitiesFromRow(2);
		if (num_cities == 0) {
			int choice = player.game.getUserInt(
					"There are no cities in play on Mars. Do you still want to play this card?\n\t[1] Yes\n\t[2] No\nChoice",
					"Invalid selection. Please choose 1 or 2.", 1, 2);
			if (choice == 2)
				return false;
		}
		return true;
	}

	static public boolean EnergyForCity(Player player) {
		int num_cities = player.game.board.allCitiesFromRow(0);
		if (num_cities == 0) {
			int choice = player.game.getUserInt(
					"There are no cities in play. Do you still want to play this card?\n\t[1] Yes\n\t[2] No\nChoice",
					"Invalid selection. Please choose 1 or 2.", 1, 2);
			if (choice == 2)
				return false;
		}
		return true;
	}

	static public boolean PlantForMicrobe(Player player) {
		int num_microbe_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Microbe) + 1;
		if (num_microbe_tags / 2 < 1) {
			int choice = player.game.getUserInt(
					"You need 2 microbe tags to increase your Plant generation, and you only have 1. Do you still want to play this card?\n\t[1] Yes\n\t[2] No\nChoice",
					"Invalid selection. Please choose 1 or 2.", 1, 2);
			if (choice == 2)
				return false;
		}
		return true;
	}

	static public boolean PlantForPlant(Player player) {
		int num_plant_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Plant);
		if (num_plant_tags < 1) {
			int choice = player.game.getUserInt(
					"You don't have any Plant tags. Do you still want to play this card?\n\t[1] Yes\n\t[2] No\nChoice",
					"Invalid selection. Please choose 1 or 2.", 1, 2);
			if (choice == 2)
				return false;
		}
		return true;
	}

	static public boolean MegaCreditForBuilding(Player player) {
		int num_building_tags = ReusableAbilityFunctions.getNumTags(player, Tag.Building) + 1;
		if (num_building_tags / 2 < 1) {
			int choice = player.game.getUserInt(
					"You need 2 building tags to increase your MegaCredit generation, and you only have 1. Do you still want to play this card?\n\t[1] Yes\n\t[2] No\nChoice",
					"Invalid selection. Please choose 1 or 2.", 1, 2);
			if (choice == 2)
				return false;
		}
		return true;
	}

	static public boolean HeatForMegaCredit(Player player) {
		int heat_generation = player.getResourceGeneration(Resources.Heat);
		if (heat_generation == 0) {
			int choice = player.game.getUserInt(
					"You don't have any Heat generation. Do you still want to play this card?\n\t[1] Yes\n\t[2] No\nChoice",
					"Invalid selection. Please choose 1 or 2.", 1, 2);
			if (choice == 2)
				return false;
		}
		return true;
	}

	static public boolean Card1of3(Player player) {
		return player.game.canDrawCard(1);
	}

	static public boolean Card2of4(Player player) {
		return player.game.canDrawCard(1);
	}

	static public boolean HasSteelGeneration(Player player) {
		return player.checkResourceGeneration(Resources.Steel, 1);
	}

	static public boolean HasTitaniumGeneration(Player player) {
		return player.checkResourceGeneration(Resources.Titanium, 1);
	}

	static public boolean CitiesExist(Player player) {
		return player.game.board.allCitiesFromRow(0) > 1;
	}

	static public boolean MegaCreditForOpponentSpace(Player player) {
		return ReusableAbilityFunctions.getNumTagsAll(player, Tag.Space)
				- ReusableAbilityFunctions.getNumTags(player, Tag.Space) > 0;
	}

	static public boolean MegaCreditForAllEvent(Player player) {
		return ReusableAbilityFunctions.getNumTagsAll(player, Tag.Event) > 0;
	}

	static public boolean PlaceEcologicalZone(Player player) {
		if (!player.game.board.freeLandSpace())
			return false;
		if (player.game.board.numGreeneries(player) < 1) {
			System.out.println("You must have at least 1 Greenery to play this card.");
			return false;
		}
		BoardTile[][] board = player.game.board.getBoard();
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				if (board[i][j].type != null && board[i][j].type.equals(TileType.Greenery))
					if (player.game.board.adjacentFreeSpace(i, j))
						return true;

		return false;
	}
	
	static public boolean PlaceIndustrialCenter(Player player) {
		if(!player.game.board.freeLandSpace())
			return false;
		BoardTile[][] board = player.game.board.getBoard();
		for(int i = 0; i<board.length; i++)
			for(int j = 0; j<board[i].length; j++)
				if(board[i][j].type != null && board[i][j].type.equals(TileType.City))
					if(player.game.board.adjacentFreeSpace(i, j))
						return true;
		
		return false;
	}
	
	static public boolean PlaceUrbanizedArea(Player player) {
		if(!player.game.board.freeLandSpace())
			return false;
		BoardTile[][] board = player.game.board.getBoard();
		for(int i = 0; i<board.length; i++)
			for(int j = 0; j<board[i].length; j++)
				if(player.game.board.isFreeLand(i, j))
					if(player.game.board.numAdjacentCities(i, j) >= 2)
						return true;
		
		return false;
	}
	
	static public boolean PlaceMoholeArea(Player player) {
		if (!player.game.board.freeLandSpace())
			return false;
		BoardTile[][] board = player.game.board.getBoard();
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[i].length;j++)
				if(board[i][j].designated_water)
					if(!player.game.board.isClaimed(i, j))
						return true;
		return false;
	}
	
	static public boolean PlaceFlooding(Player player) {
		if (!player.game.board.freeLandSpace())
			return false;
		BoardTile[][] board = player.game.board.getBoard();
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[i].length;j++)
				if(board[i][j].designated_water)
					if(!player.game.board.isClaimed(i, j))
						return true;
		return false;
	}
	
	static public boolean PlacePreserveNaturalArea(Player player) {
		if (!player.game.board.freeLandSpace())
			return false;
		BoardTile[][] board = player.game.board.getBoard();
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[i].length;j++)
				if(player.game.board.isFreeLand(i, j))
					if(!player.game.board.adjacentTile(i, j))
						if(player.game.board.numAdjacentWater(i, j)==0)
							return true;
		
		return false;
	}
	
	static public boolean PlaceResearchOutpost(Player player) {
		if (!player.game.board.freeLandSpace())
			return false;
		BoardTile[][] board = player.game.board.getBoard();
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[i].length;j++)
				if(player.game.board.isFreeLand(i, j))
					if(!player.game.board.adjacentTile(i, j))
						if(player.game.board.numAdjacentWater(i, j)==0)
							return true;
		
		return false;
	}
	
	static public boolean PlaceProtectedValley(Player player) {
		BoardTile[][] board = player.game.board.getBoard();
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[i].length;j++)
				if(board[i][j].designated_water)
					if(!player.game.board.isClaimed(i, j))
						return true;
		return false;
	}
	
	static public boolean PlaceMangrove(Player player) {
		BoardTile[][] board = player.game.board.getBoard();
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[i].length;j++)
				if(board[i][j].designated_water)
					if(!player.game.board.isClaimed(i, j))
						return true;
		return false;
	}
	
	static boolean Place_Artificial_Lake(Player player) {
		if(player.game.board.water >= 9) {
			System.err.println("All water has already been placed.");
			return false;
		}
		if (player.game.board.freeLandSpace())
			return true;
		else
			return false;
	}
	
	static public boolean AddCounter(Player player) {
		for(Card e: player.played) if(e.counter_type != null && e.counter > 0) return true;
		return false;
	}
	
	static public boolean RemoveAnimalOrPlant(Player player) {
		for(Player p: player.game.players) if(!p.equals(player)) {
			if(p.checkResource(Resources.Plant, 1)) return true;
			else if(ReusableAbilityFunctions.hasCounter(p, Counter.Animal)) return true;
		}
		return false;
	}
	
	static public boolean PlaceBlocker(Player player) {
		if (player.game.board.freeLandSpace())
			return true;
		else
			return false;
	}
	
	static public boolean DuplicateProduction(Player player) {
		for(Card e: player.played) 
			if(e.tag != null)
				for(Tag t: e.tag)
					if(t.equals(Tag.Building))
						if(e.resource_generation_add != null && e.resource_generation_add.length > 0)
							return true;
		return false;
	}
}
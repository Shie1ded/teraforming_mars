package main;

import board.TileType;
import player.Abilities;
import player.Ability;
import player.Card;
import player.Player;
import player.ReusableAbilityFunctions;
import player.Tag;

public class EndOfGame {
	Game game;
	Player[] players;
	Abilities abilities;

	public EndOfGame(Game game) {
		this.game = game;
		this.players = game.players;
		this.abilities = game.abilities;
	}

	public void teardown() {
		// 1. generate resources
		// 2. let individuals place a greenery if they have 1. enough plants and 2.
		// enough board spcae
		// 3. count all points from cards (including special abilities that generate a
		// custom number of points
		// 3.1 points from cards normally
		// 3.2 points from counters
		// 3.3 points from cards with tag-dependent points
		// 4. count all points from the board
		// 4.1 1 point for each greenery adjacent to your city
		// 4.2 1 point for each greenery you own
		// 4.3 points from custom placement tiles with points
		// 5. count all points from awards and milestones (have to first program in the
		// awards and milestones
		// 6. add points to terraform rating
		// produce the winner
		int[] points = new int[players.length];
		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			p.generate_resources();
			p.printPlayerBar();
			Game.sleep();
			abilities.activate(p, Ability.Convert_Greenery);
			Game.sleep();
			points[i] = countPoints(p);
		}
		System.out.println("\n-------------- Final Score --------------");
		for(int i = 0; i<players.length; i++) System.out.println("\t Player " + players[i].number + ": " + points[i]);
	}

	private int countPoints(Player player) {
		// 3. count all points from cards (including special abilities that generate a
		// custom number of points
		// 3.1 points from cards normally
		// 3.2 points from counters
		// 3.3 points from cards with tag-dependent points
		// 4. count all points from the board
		// 4.1 1 point for each greenery adjacent to your city
		// 4.2 1 point for each greenery you own
		// 4.3 points from custom placement tiles with points
		// 5. count all points from awards and milestones (have to first program in the
		// awards and milestones
		// 6. add points to terraform rating
		game.awards.determineWinners();
		return cardPoints(player) + boardPoints(player) + game.awards.awardPoints(player) +
				game.awards.milestonePoints(player) + player.terraform_rating;
	}

	private int cardPoints(Player player) {
		int points = player.calculateVPs();
		for (Card c : player.played)
			if (c.counter_type != null && c.counter > 0)
				for (Ability a : c.abilities) {
					int multiply = 1;
					int divide = 1;
					switch (a) {
					case PointPerCounter2:
						divide = 2;
						break;
					case PointPerCounter3:
						divide = 3;
						break;
					case PointPerCounter4:
						divide = 4;
						break;
					case PointPer2Counter:
						multiply = 2;
						break;
					case PointIfCounter3:
						if (c.counter > 3)
							c.counter = 1;
						else
							c.counter = 0;
						break;
					default:
						continue;
					}
					points += c.counter * multiply / divide;
				}
		for (Ability a : player.end_of_game_abilities) {
			switch (a) {
			case Jovian_Points:
				points += ReusableAbilityFunctions.getNumTags(player, Tag.Jovian);
				break;
			default:
				continue;
			}
		}
		return points;
	}

	private int boardPoints(Player player) {
		int points = game.board.numGreeneries(player) + game.board.pointsFromCities(player);
		for(Ability a: player.end_of_game_abilities) {
			switch(a) {
			case City_Points:
				points += game.board.allCitiesFromRow(0) / 3;
				break;
			case Adjacent_Water_Points:
				points += capitalPoints(player);
				break;
			default:
				continue;
			}
		}
		
		return points;
	}
	
	private int capitalPoints(Player player) {
		int points = 0;
		for(int i = 0; i < game.board.board.length; i++)
			for(int j = 0; j < game.board.board[i].length; j++)
				if(game.board.board[i][j].type != null && game.board.board[i][j].type.equals(TileType.Capital))
					if(game.board.board[i][j].owner.equals(player))
						points += game.board.numAdjacentWater(i, j);
		return points;
	}
}

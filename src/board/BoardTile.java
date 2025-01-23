package board;

import player.PermanentAbilities;
import player.Player;

public class BoardTile {
	public Player owner;
	public Resources[] bonusResources = new Resources[0];
	public String name;
	public boolean designated_water = false;
	public boolean designated_city = false;
	public boolean city_adjacent = false;
	public TileType type;
	
	public void getBonusResources(Player player) {
		if(bonusResources.length > 0) 
			for(Resources e: bonusResources) {
				player.increaseResource(e, 1);
				if(e.equals(Resources.Steel) || e.equals(Resources.Titanium)) {
					PermanentAbilities.Mining(player);
				}
			}
	}
	
	// Functions to make setup easier
	public void designateCity(String name) {
		this.name = name;
		designated_city = true;
	}
	
	public void setbonusResources(Resources resource) {
		this.bonusResources = new Resources[1];
		this.bonusResources[0] = resource;
	}
	
	public void setbonusResources(Resources resource, Resources resource2) {
		this.bonusResources = new Resources[2];
		this.bonusResources[0] = resource;
		this.bonusResources[1] = resource2;
	}
	
	public void print() {
		if(type != null) System.out.print("x");
		else if(designated_water) System.out.print("w");
		else if(designated_city) System.out.print("o");
		else if(bonusResources.length > 0) System.out.print("-");
		else System.out.print(" ");
	}
	
	public void addBonusResource(Resources[] resource) {
		if(bonusResources.length == 0) {
			bonusResources = resource;
		} else {
			Resources[] newBonus = new Resources[bonusResources.length + resource.length];
			int i = 0;
			for(; i < bonusResources.length; i++) newBonus[i] = bonusResources[i];
			for(int j = 0; j<resource.length; j++) {
				newBonus[i] = resource[j];
				i++;
			}
			bonusResources = newBonus;
		}		
	}
	
	public boolean hasResource(Resources r) {
		if(bonusResources.length > 0)
			for(Resources e: bonusResources)
				if(e.equals(r))
					return true;
		return false;
	}
}
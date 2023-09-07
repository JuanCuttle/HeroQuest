package entities;

import java.util.ArrayList;

import entities.enemies.*;
import quests.BasicMap;

public class Zargon extends Player {

	private static final long serialVersionUID = 2709851702481435202L;
	protected ArrayList<Monster> monsters;
	
	public Zargon(HeroQuest heroQuest) {
		this.monsters = new ArrayList<>();
		this.monsters = this.createMonsters(heroQuest);
	}
	
	public Monster getMonster(int index) {
        return this.monsters.get(index);
    }
	
	public ArrayList<Monster> getMonsters() {
		return this.monsters;
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game) {
		BasicMap map = game.getMap();
		this.monsters = map.createMonsters(game);
		return this.monsters;
	}
}
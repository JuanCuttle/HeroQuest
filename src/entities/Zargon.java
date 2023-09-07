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
		Monster monster = this.monsters.get(index);
		if (monster != null) {
			return monster;
		}
		return null;
	}
	
	public ArrayList<Monster> getMonsters() {
		return this.monsters;
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game) {
		BasicMap map = game.getMap();
		this.monsters = map.createMonsters(game);
		return this.monsters;
	}

	@SuppressWarnings("unused")
	private void createMonstersDefault(HeroQuest jogo) {
		Goblin goblin1 = new Goblin();
		goblin1.setID((byte) 1);
		jogo.setCreatureInPosition(goblin1, 11, 19);
		
		this.monsters.add(goblin1);
		
		Goblin goblin2 = new Goblin();
		goblin2.setID((byte) 2);
		jogo.setCreatureInPosition(goblin2, 11, 31);
		this.monsters.add(goblin2);
		
		Zombie zombie1 = new Zombie();
		zombie1.setID((byte) 3);
		jogo.setCreatureInPosition(zombie1, 13, 7);
		this.monsters.add(zombie1);
		
		Zombie zombie2 = new Zombie();
		zombie2.setID((byte) 4);
		jogo.setCreatureInPosition(zombie2, 16, 7);
		this.monsters.add(zombie2);
		
		Skeleton skeleton1 = new Skeleton();
		skeleton1.setID((byte) 5);
		jogo.setCreatureInPosition(skeleton1, 14, 8);
		this.monsters.add(skeleton1);
		
		Skeleton skeleton2 = new Skeleton();
		skeleton2.setID((byte) 6);
		jogo.setCreatureInPosition(skeleton2, 15, 8);
		this.monsters.add(skeleton2);
		
		Skeleton skeleton3 = new Skeleton();
		skeleton3.setID((byte) 7);
		jogo.setCreatureInPosition(skeleton3, 21, 35);
		this.monsters.add(skeleton3);
		
		Skeleton skeleton4 = new Skeleton();
		skeleton4.setID((byte) 8);
		jogo.setCreatureInPosition(skeleton4, 21, 37);
		this.monsters.add(skeleton4);
		
		Mummy mummy1 = new Mummy();
		mummy1.setID((byte) 9);
		jogo.setCreatureInPosition(mummy1, 21, 36);
		this.monsters.add(mummy1);
		
		Mummy mummy2 = new Mummy();
		mummy2.setID((byte) 10);
		jogo.setCreatureInPosition(mummy2, 21, 38);
		this.monsters.add(mummy2);
		
		Fimir fimir1 = new Fimir();
		fimir1.setID((byte) 11);
		jogo.setCreatureInPosition(fimir1, 19, 21);
		this.monsters.add(fimir1);
		
		Fimir fimir2 = new Fimir();
		fimir2.setID((byte) 12);
		jogo.setCreatureInPosition(fimir2, 21, 21);
		this.monsters.add(fimir2);
		
		Fimir fimir3 = new Fimir();
		fimir3.setID((byte) 13);
		jogo.setCreatureInPosition(fimir3, 20, 28);
		this.monsters.add(fimir3);
		
		Fimir fimir4 = new Fimir();
		fimir4.setID((byte) 14);
		jogo.setCreatureInPosition(fimir4, 22, 28);
		this.monsters.add(fimir4);
		
		ChaosWarrior chaos_Warrior1 = new ChaosWarrior();
		chaos_Warrior1.setID((byte) 15);
		jogo.setCreatureInPosition(chaos_Warrior1, 25, 46);
		this.monsters.add(chaos_Warrior1);
		
		ChaosWarrior chaos_Warrior2 = new ChaosWarrior();
		chaos_Warrior2.setID((byte) 16);
		jogo.setCreatureInPosition(chaos_Warrior2, 24, 46);
		this.monsters.add(chaos_Warrior2);
		
		ChaosWarrior chaos_Warrior3 = new ChaosWarrior();
		chaos_Warrior3.setID((byte) 17);
		jogo.setCreatureInPosition(chaos_Warrior3, 13, 45);
		this.monsters.add(chaos_Warrior3);
		
		ChaosWarrior chaos_Warrior4 = new ChaosWarrior();
		chaos_Warrior4.setID((byte) 18);
		jogo.setCreatureInPosition(chaos_Warrior4, 13, 38);
		this.monsters.add(chaos_Warrior4);
		
		PolarWarbear polar_Warbear = new PolarWarbear();
		polar_Warbear.setID((byte) 19);
		jogo.setCreatureInPosition(polar_Warbear, 8, 43);
		this.monsters.add(polar_Warbear);
	}
}
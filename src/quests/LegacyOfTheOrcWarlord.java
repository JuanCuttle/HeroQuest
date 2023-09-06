package quests;

import java.util.ArrayList;

import entities.players.*;
import entities.Creature;
import entities.enemies.Fimir;
import entities.tiles.Furniture;
import entities.enemies.Goblin;
import entities.HeroQuest;
import entities.enemies.Monster;
import entities.enemies.Orc;
import entities.tiles.Pit;
import entities.Position;
import entities.utils.Strings;
import enums.FurnitureDirectionEnum;

public class LegacyOfTheOrcWarlord extends BasicMap {
	
	boolean foundEquipment = false;

	public LegacyOfTheOrcWarlord(HeroQuest game){
		super();
		description = Strings.LEGACY_OF_THE_ORC_WARLORD_DESCRIPTION.toString();
		generateDoors(game);
		generateTraps();
		stairsPosition[0] = 20;
		stairsPosition[1] = 9;
		barbInitialPosition[0] = 17;
		barbInitialPosition[1] = 8;
		wizInitialPosition[0] = 17;
		wizInitialPosition[1] = 9;
		elfInitialPosition[0] = 18;
		elfInitialPosition[1] = 8;
		dwarfInitialPosition[0] = 18;
		dwarfInitialPosition[1] = 9;
		
		numberOfCreatures = 19;
		
		table1Position = new byte[]{FurnitureDirectionEnum.VERTICAL.getId(), 13, 18};
		
		table2Position = new byte[]{FurnitureDirectionEnum.HORIZONTAL.getId(), 21, 26};
		
		rackPosition = new byte[]{FurnitureDirectionEnum.VERTICAL.getId(), 9, 27};
		
		bookcase1Position = new byte[]{FurnitureDirectionEnum.VERTICAL.getId(), 4, 7};
		
		generateFurniture();
	}
	
	private void generateFurniture() {
		generate3x1(bookcase1Position);
		generate2x3(table1Position);
		generate2x3(table2Position);
		generate2x3(rackPosition);
	}
	
	private void generate3x1(byte[] furniture) {
		int i;
		int j = furniture[2];
		for (int x = 0; x < 3; x++){
			i = furniture[1]+x;
			this.positions[i][j] = new Furniture((byte)i, (byte)j);
		}
	}
	
	private void generate2x3(byte[] furniture){
		if (furniture != null){
			int i;
			int j;
			if (furniture[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
				for (int x = 0; x < 2; x++){
					for (int y = 0; y < 3; y++){
						i = furniture[1]+x;
						j = furniture[2]+y;
						this.positions[i][j] = new Furniture((byte)i, (byte)j);
					}
				}
			} else {
				for (int x = 0; x < 3; x++){
					for (int y = 0; y < 2; y++){
						i = furniture[1]+x;
						j = furniture[2]+y;
						this.positions[i][j] = new Furniture((byte)i, (byte)j);
					}
				}
			}
		}
	}

	public void generateRocks(){
		for (byte i = 18; i < 20; i++){
			generateBlockade(3, i);
		}
		generateBlockade(11, 14);
		generateBlockade(13, 23);
		generateBlockade(15, 23);
		for (byte i = 26; i < 28; i++){
			generateBlockade(i, 5);
		}
	}
	
	public void generateDoors(HeroQuest game){
		generateNormalDoor(game, 5, 3);
		generateNormalDoor(game, 13, 26);
		generateNormalDoor(game, 13, 15);
		generateNormalDoor(game, 14, 22);
		generateNormalDoor(game, 15, 8);
		generateNormalDoor(game, 23, 13);
		generateNormalDoor(game, 24, 3);
		generateNormalDoor(game, 25, 10);
		generateNormalDoor(game, 25, 15);
		generateNormalDoor(game, 25, 27);
	}
	
	public void generateTraps(){
		positions[18][22].setTrap(new Pit());
		for (int i = 1; i < 3; i++){
			positions[25][i].setTrap(new Pit());
		}
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game){
			ArrayList<Monster> monsters =  new ArrayList<>();
			
			int i = 0;
			
			generateMonster(game, monsters, new Goblin(), ++i, 14, 9);
			generateMonster(game, monsters, new Goblin(), ++i, 14, 24);
			generateMonster(game, monsters, new Goblin(), ++i, 21, 12);
			generateMonster(game, monsters, new Goblin(), ++i, 23, 9);
			generateMonster(game, monsters, new Goblin(), ++i, 22, 14);
			generateMonster(game, monsters, new Goblin(), ++i, 23, 15);
			generateMonster(game, monsters, new Orc(), ++i, 9, 25);
			generateMonster(game, monsters, new Orc(), ++i, 13, 17);
			generateMonster(game, monsters, new Orc(), ++i, 13, 20);
			generateMonster(game, monsters, new Orc(), ++i, 14, 20);
			generateMonster(game, monsters, new Orc(), ++i, 15, 17);
			generateMonster(game, monsters, new Orc(), ++i, 15, 20);
			generateMonster(game, monsters, new Orc(), ++i, 23, 6);
			generateMonster(game, monsters, new Orc(), ++i, 23, 10);
			generateMonster(game, monsters, new Orc(), ++i, 26, 27);
			generateMonster(game, monsters, new Fimir(), ++i, 10, 26);
			generateMonster(game, monsters, new Fimir(), ++i, 14, 17);
			generateMonster(game, monsters, new Fimir(), ++i, 18, 18);
			
			// Grak, son of Ulag
			generateMonster(game, monsters, new Orc(), ++i, 21, 11);
			Orc grak = (Orc) monsters.get(monsters.size()-1);
			grak.setAttackDiceAmount((byte) 4);
			grak.setDefenceDiceAmount((byte) 4);
			
			return monsters;
	}
	
	public boolean foundEquipment(){
		return foundEquipment;
	}
	
	public boolean verifyWinningConditions(HeroQuest game) {
		Position equipment = game.getPosition((byte)5, (byte)5);
		foundEquipment = equipment.isVisible(); // If treasure has been found
		
		int stairsRow = stairsPosition[0];
		int stairsColumn = stairsPosition[1];
		
		boolean areAllHeroesOnStairs = true;
		ArrayList<Creature> creatureQueue = game.getCreatureQueue();
		for (int i = 0; i < creatureQueue.size(); i++) {
				Creature creature = creatureQueue.get(i);
				if (creature instanceof PlayableCharacter) {
					if (!onStairs(creature.getCurrentPosition(), stairsRow, stairsColumn)){
						areAllHeroesOnStairs = false;
						break;
					}
				}
		}
		
		return foundEquipment && areAllHeroesOnStairs;
	}
	
}

package quests;

import java.util.ArrayList;

import enums.FurnitureDirectionEnum;
import enums.StatusEnum;
import entities.enemies.ChaosSorcerer;
import entities.Creature;
import entities.tiles.Furniture;
import entities.HeroQuest;
import enums.ItemEnum;
import entities.enemies.Monster;
import entities.enemies.Mummy;
import entities.enemies.Orc;
import entities.tiles.Pit;
import entities.enemies.Skeleton;
import entities.utils.Strings;
import entities.tiles.Treasure;
import entities.enemies.Zombie;

public class TheFireMage extends BasicMap {
	
	public TheFireMage(HeroQuest game){
		super();
		description = Strings.THE_FIRE_MAGE_DESCRIPTION.toString();
		generateDoors(game);
		generateTraps();
		generateTreasures();
		stairsPosition[0] = 11;
		stairsPosition[1] = 30;
		barbInitialPosition[0] = 11;
		barbInitialPosition[1] = 30;
		wizInitialPosition[0] = 11;
		wizInitialPosition[1] = 31;
		elfInitialPosition[0] = 12;
		elfInitialPosition[1] = 30;
		dwarfInitialPosition[0] = 12;
		dwarfInitialPosition[1] = 31;
		
		numberOfCreatures = 15;
		
		table1Position = new byte[]{FurnitureDirectionEnum.HORIZONTAL.getId(), 21, 21};
		
		bookOnTablePosition = new byte[]{FurnitureDirectionEnum.VERTICAL.getId(), 13, 16};
		
		generateFurniture();
	}
	
	private void generateFurniture() {
		generate2x3(bookOnTablePosition);
		generate2x3(table1Position);
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
		for (byte i = 1; i < 3; i++){
			generateBlockade(3, i);
		}
		for (byte i = 18; i < 20; i++){
			generateBlockade(3, i);
		}
		for (byte i = 22; i < 24; i++){
			generateBlockade(10, i);
		}
		for (byte i = 35; i < 37; i++){
			generateBlockade(13, i);
		}
		generateBlockade(10, 17);
		generateBlockade(14, 3);
		generateBlockade(14, 13);
		for (byte i = 26; i < 28; i++){
			generateBlockade(i, 10);
			generateBlockade(i, 14);
		}
	}
	
	public void generateDoors(HeroQuest game){
		generateNormalDoor(game, 3, 5);
		generateNormalDoor(game, 7, 6);
		generateNormalDoor(game, 10, 3);
		generateNormalDoor(game, 10, 34);
		generateNormalDoor(game, 13, 5);
		generateNormalDoor(game, 14, 22);
		generateNormalDoor(game, 15, 6);
		generateNormalDoor(game, 15, 27);
		generateNormalDoor(game, 17, 34);
		generateNormalDoor(game, 20, 6);
		generateNormalDoor(game, 20, 27);
		generateNormalDoor(game, 25, 5);
		generateNormalDoor(game, 25, 9);
		generateNormalDoor(game, 25, 11);
		generateNormalDoor(game, 25, 22);
		generateNormalDoor(game, 25, 27);
		generateHiddenDoor(game, 15, 11);
		generateHiddenDoor(game, 18, 13);
	}
	
	public void generateTraps(){
		for (int i = 1; i < 3; i++){
			positions[i][3].setTrap(new Pit());
		}
		positions[10][14].setTrap(new Pit());
		positions[24][10].setTrap(new Pit());
		for (int i = 26; i < 28; i++){
			positions[i][11].setTrap(new Pit());
		}
		
		for (byte i = 1; i < 3; i++){
			generateBlockade(9, i);
			generateBlockade(18, i);
		}
	}
	
	public void generateTreasures(){
		Treasure questTreasure = new Treasure(150);
		questTreasure.setItem(ItemEnum.WandOfRecall);
		positions[17][30].setTreasure(questTreasure);
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game){
			ArrayList<Monster> monsters =  new ArrayList<>();
			
			int i = 0;
			
			generateMonster(game, monsters, new Orc(), ++i, 17, 31);
			generateMonster(game, monsters, new Orc(), ++i, 20, 22);
			generateMonster(game, monsters, new Orc(), ++i, 23, 23);
			generateMonster(game, monsters, new Orc(), ++i, 22, 27);
			generateMonster(game, monsters, new Orc(), ++i, 23, 28);
			generateMonster(game, monsters, new Skeleton(), ++i, 5, 4);
			generateMonster(game, monsters, new Skeleton(), ++i, 5, 5);
			generateMonster(game, monsters, new Skeleton(), ++i, 5, 6);
			generateMonster(game, monsters, new Skeleton(), ++i, 5, 7);
			generateMonster(game, monsters, new Skeleton(), ++i, 18, 23);
			generateMonster(game, monsters, new Zombie(), ++i, 1, 1);
			generateMonster(game, monsters, new Zombie(), ++i, 10, 6);
			generateMonster(game, monsters, new Mummy(), ++i, 13, 18);
			generateMonster(game, monsters, new Mummy(), ++i, 15, 18);
			generateMonster(game, monsters, new ChaosSorcerer(), ++i, 14, 18); // Balur, CREATE CHAOSSORCERER
			
			return monsters;
	}
	
	public boolean verifyWinningConditions(HeroQuest game) {
		Creature balur = game.getCreatureByID(15);
        return StatusEnum.DEAD.equals(balur.getStatus());
    }
}

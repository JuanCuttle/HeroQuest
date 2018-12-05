package quests;

import java.util.ArrayList;

import modelo.ChaosSorcerer;
import modelo.Creature;
import modelo.Furniture;
import modelo.HeroQuest;
import modelo.Items;
import modelo.Monster;
import modelo.Mummy;
import modelo.Orc;
import modelo.Pit;
import modelo.Skeleton;
import modelo.Status;
import modelo.Strings;
import modelo.Treasure;
import modelo.Zombie;

public class TheFireMage extends BasicMap {
	
	public TheFireMage(HeroQuest game){
		super();
		description = Strings.THEFIREMAGE.toString();
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
		
		table1Position = new byte[]{0, 21, 21}; // 0 for horizontal, 1 for vertical
		
		bookOnTablePosition = new byte[]{1, 13, 16};
		
		generateFurniture();
	}
	
	// Converts positions where there is furniture into class Furniture, so that they become solid
	private void generateFurniture() {
		generate2x3(bookOnTablePosition);
		generate2x3(table1Position);
	}
	
	private void generate2x3(byte[] furniture){
		if (furniture != null){
			int i = furniture[1];
			int j = furniture[2];
			if (furniture[0] == 0){ // if horizontal
				for (int x = 0; x < 2; x++){
					for (int y = 0; y < 3; y++){
						i = furniture[1]+x;
						j = furniture[2]+y;
						this.positions[i][j] = new Furniture((byte)i, (byte)j);
					}
				}
			} else { // if vertical
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
		// Rocks
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
		// Pits
		for (int i = 1; i < 3; i++){
			positions[i][3].setTrap(new Pit());
		}
		positions[10][14].setTrap(new Pit());
		positions[24][10].setTrap(new Pit());
		for (int i = 26; i < 28; i++){
			positions[i][11].setTrap(new Pit());
		}
		
		// Spears
		for (byte i = 1; i < 3; i++){
			generateBlockade(9, i);
			generateBlockade(18, i);
		}
	}
	
	public void generateTreasures(){
		Treasure questTreasure = new Treasure(150);
		questTreasure.setItem(Items.WandOfRecall);
		positions[17][30].setTreasure(questTreasure); // Quest treasure
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
	
	public boolean verificarCondicoesDeVitoria(HeroQuest game) {
		Creature balur = game.getCreaturePorID(15);
		if (balur.getStatus() == Status.DEAD){
			return true;
		}
		return false;
	}
}

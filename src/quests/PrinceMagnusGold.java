package quests;

import java.util.ArrayList;

import modelo.ChaosWarrior;
import modelo.Fimir;
import modelo.Furniture;
import modelo.Goblin;
import modelo.HeroQuest;
import modelo.Monster;
import modelo.Orc;
import modelo.Pit;
import modelo.Spear;
import modelo.Strings;
import modelo.Treasure;

public class PrinceMagnusGold extends BasicMap {
	public PrinceMagnusGold(HeroQuest game){
		super();
		description = Strings.PRINCEMAGNUSGOLD.toString();
		generateDoors(game);
		generateTreasures();
		generateTraps();
		stairsPosition[0] = 21;
		stairsPosition[1] = 27;
		barbInitialPosition[0] = 21;
		barbInitialPosition[1] = 27;
		wizInitialPosition[0] = 21;
		wizInitialPosition[1] = 28;
		elfInitialPosition[0] = 22;
		elfInitialPosition[1] = 27;
		dwarfInitialPosition[0] = 22;
		dwarfInitialPosition[1] = 28;
		
		numberOfCreatures = 14;
		
		table1Position = new byte[]{1, 21, 4}; // 0 for horizontal, 1 for vertical
		
		generateFurniture();
	}
	
	// Converts positions where there is furniture into class Furniture, so that they become solid
	private void generateFurniture() {
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
			generateBlockade(i, 17);
		}
		for (byte i = 18; i < 20; i++){
			generateBlockade(6, i);
			generateBlockade(25, i);
		}
		generateBlockade(10, 17);
		generateBlockade(10, 20);
		generateBlockade(14, 3);
		generateBlockade(14, 14);
		generateBlockade(14, 35);
		generateBlockade(14, 36);
		generateBlockade(15, 23);
		for (byte i = 26; i < 28; i++){
			generateBlockade(i, 3);
			generateBlockade(i, 34);
		}
	}
	
	public void generateDoors(HeroQuest game){
		generateNormalDoor(game, 5, 13);
		generateNormalDoor(game, 7, 9);
		generateNormalDoor(game, 11, 13);
		generateNormalDoor(game, 11, 34);
		generateNormalDoor(game, 13, 9);
		generateNormalDoor(game, 13, 30);
		generateNormalDoor(game, 14, 22);
		generateNormalDoor(game, 15, 5);
		generateNormalDoor(game, 15, 11);
		generateNormalDoor(game, 20, 6);
		generateNormalDoor(game, 24, 29);
		generateNormalDoor(game, 25, 4);
		generateNormalDoor(game, 25, 33);
		generateHiddenDoor(game, 5, 17);
		generateHiddenDoor(game, 9, 14);
	}
	
	public void generateTraps(){
		positions[10][14].setTrap(new Spear());
		positions[11][14].setTrap(new Pit());
		positions[11][23].setTrap(new Pit());
		positions[14][10].setTrap(new Pit());
	}
	
	public void generateTreasures(){
		// Return after quest
		Treasure tr = new Treasure(250);
		positions[12][17].setTreasure(tr);
		
		Treasure tr2 = new Treasure(250);
		positions[13][16].setTreasure(tr2);
		
		Treasure tr3 = new Treasure(250);
		positions[16][16].setTreasure(tr3);
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game){
			ArrayList<Monster> monsters =  new ArrayList<>();
			
			int i = 0;
			
			generateMonster(game, monsters, new Goblin(), ++i, 22, 31);
			generateMonster(game, monsters, new Goblin(), ++i, 23, 32);
			generateMonster(game, monsters, new Orc(), ++i, 12, 30);
			generateMonster(game, monsters, new Orc(), ++i, 11, 31);
			generateMonster(game, monsters, new Orc(), ++i, 12, 32);
			generateMonster(game, monsters, new Orc(), ++i, 13, 18);
			generateMonster(game, monsters, new Orc(), ++i, 15, 18);
			generateMonster(game, monsters, new Orc(), ++i, 14, 23);
			generateMonster(game, monsters, new Orc(), ++i, 17, 5);
			generateMonster(game, monsters, new Orc(), ++i, 23, 6);
			generateMonster(game, monsters, new Orc(), ++i, 23, 7);
			generateMonster(game, monsters, new Orc(), ++i, 26, 5);
			generateMonster(game, monsters, new Fimir(), ++i, 14, 18);
			generateMonster(game, monsters, new ChaosWarrior(), ++i, 14, 17);// Gulthor
			
			return monsters;
	}
	
	public boolean verificarCondicoesDeVitoria(HeroQuest game) {
		return 	game.getPosition((byte)12, (byte)17).getTreasure().getGoldAmount() == -1 && 
				game.getPosition((byte)13, (byte)16).getTreasure().getGoldAmount() == -1 &&
				game.getPosition((byte)16, (byte)16).getTreasure().getGoldAmount() == -1;
	}
}

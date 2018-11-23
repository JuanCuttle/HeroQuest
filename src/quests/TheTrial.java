package quests;

import java.util.ArrayList;

import modelo.ChaosWarrior;
import modelo.Creature;
import modelo.Fimir;
import modelo.Furniture;
import modelo.Gargoyle;
import modelo.Goblin;
import modelo.HeroQuest;
import modelo.Monster;
import modelo.Mummy;
import modelo.Orc;
import modelo.Skeleton;
import modelo.Status;
import modelo.Treasure;
import modelo.Zombie;


public class TheTrial extends BasicMap {
	
	public TheTrial(HeroQuest game){
		super();
		generateDoors(game);
		generateTreasures();
		stairsPosition[0] = 21;
		stairsPosition[1] = 4;
		barbInitialPosition[0] = 21;
		barbInitialPosition[1] = 6;
		wizInitialPosition[0] = 21;
		wizInitialPosition[1] = 7;
		elfInitialPosition[0] = 22;
		elfInitialPosition[1] = 6;
		dwarfInitialPosition[0] = 22;
		dwarfInitialPosition[1] = 7;
		
		numberOfCreatures = 24;
		
		table1Position = new byte[]{0, 14, 17}; // 0 for horizontal, 1 for vertical
		
		table2Position = new byte[]{0, 9, 10};
		
		rackPosition = new byte[]{1, 16, 5};
		
		bookOnTablePosition = new byte[]{1, 9, 4};
		
		tombPosition = new byte[]{1, 4, 15};
		
		bookcase1Position = new byte[]{0, 20, 9};
		
		bookcase2Position = new byte[]{0, 20, 22};
		
		bookcase3Position = new byte[]{0, 24, 22};
		
		thronePosition = new byte[]{0, 13, 16}; // 0 to face right, 1 to face left
		
		wepRackPosition = new byte[]{0, 22, 16};
		
		deskPosition = new byte[]{0, 22, 9};
		
		fireplacePosition = new byte[]{0, 12, 18}; // 0 to face down, 1 to face up
		
		generateFurniture();
	}
	
	// Converts positions where there is furniture into class Furniture, so that they become solid
	private void generateFurniture() {
		generate1x1(thronePosition);
		generate3x1(wepRackPosition);
		generate1x3(fireplacePosition);
		generate1x3(bookcase1Position);
		generate1x3(bookcase2Position);
		generate1x3(bookcase3Position);
		generate2x3(table1Position);
		generate2x3(table2Position);
		generate2x3(rackPosition);
		generate2x3(bookOnTablePosition);
		generate2x3(tombPosition);
		generate2x3LR(deskPosition);
	}
	
	private void generate1x3(byte[] furniture) {
		int i = furniture[1];
		int j = furniture[2];
		for (int y = 0; y < 3; y++){
			j = furniture[2]+y;
			this.positions[i][j] = new Furniture((byte)i, (byte)j);
		}
	}

	private void generate2x3LR(byte[] furniture) {
		int i = furniture[1];
		int j = furniture[2];
		for (int x = 0; x < 3; x++){
			for (int y = 0; y < 2; y++){
				i = furniture[1]+x;
				j = furniture[2]+y;
				this.positions[i][j] = new Furniture((byte)i, (byte)j);
			}
		}
	}

	private void generate3x1(byte[] furniture) {
		int i = furniture[1];
		int j = furniture[2];
		for (int x = 0; x < 3; x++){
			i = furniture[1]+x;
			this.positions[i][j] = new Furniture((byte)i, (byte)j);
		}
	}

	private void generate1x1(byte[] furniture) {
		int i = furniture[1];
		int j = furniture[2];
		this.positions[i][j] = new Furniture((byte)i, (byte)j);
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
			generateBlockade(9, i);
		}
		generateBlockade(14, 24);
		for (byte i = 26; i < 28; i++){
			generateBlockade(i, 20);
		}
	}
	
	public void generateDoors(HeroQuest game){
		generateNormalDoor(game, 5, 8);
		generateNormalDoor(game, 5, 13);
		generateNormalDoor(game, 7, 6);
		generateNormalDoor(game, 13, 6);
		generateNormalDoor(game, 13, 11);
		generateNormalDoor(game, 14, 22);
		generateNormalDoor(game, 17, 3);
		generateNormalDoor(game, 19, 15);
		generateNormalDoor(game, 22, 13);
		generateNormalDoor(game, 22, 20);
		generateNormalDoor(game, 25, 6);
		generateNormalDoor(game, 25, 11);
	}
	
	public void generateTreasures(){
		positions[8][15].setTreasure(new Treasure(84));
		positions[12][17].setTreasure(new Treasure(120));
		positions[23][24].setTreasure(new Treasure(0));
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game){
			ArrayList<Monster> monsters =  new ArrayList<>();
			
			int i = 0;
			
			generateMonster(game, monsters, new Goblin(), ++i, 10, 6);
			generateMonster(game, monsters, new Goblin(), ++i, 11, 10);
			generateMonster(game, monsters, new Goblin(), ++i, 11, 11);
			generateMonster(game, monsters, new Goblin(), ++i, 19, 5);
			generateMonster(game, monsters, new Goblin(), ++i, 21, 15);
			generateMonster(game, monsters, new Skeleton(), ++i, 5, 5);
			generateMonster(game, monsters, new Skeleton(), ++i, 5, 6);
			generateMonster(game, monsters, new Skeleton(), ++i, 4, 14);
			generateMonster(game, monsters, new Skeleton(), ++i, 6, 14);
			generateMonster(game, monsters, new Zombie(), ++i, 4, 10);
			generateMonster(game, monsters, new Zombie(), ++i, 6, 10);
			generateMonster(game, monsters, new Mummy(), ++i, 5, 10);
			// Guardian of Fellmarg's tomb, +1 attack dice
			Mummy guardianOfFellmarg = (Mummy) monsters.get(monsters.size()-1);
			guardianOfFellmarg.setAttackDiceAmount((byte) 4);
			generateMonster(game, monsters, new Mummy(), ++i, 7, 14);
			generateMonster(game, monsters, new Orc(), ++i, 9, 7);
			generateMonster(game, monsters, new Orc(), ++i, 13, 20);
			generateMonster(game, monsters, new Orc(), ++i, 16, 17);
			generateMonster(game, monsters, new Orc(), ++i, 19, 6);
			generateMonster(game, monsters, new Orc(), ++i, 21, 11);
			generateMonster(game, monsters, new Orc(), ++i, 22, 12);
			generateMonster(game, monsters, new Fimir(), ++i, 23, 15);
			generateMonster(game, monsters, new ChaosWarrior(), ++i, 16, 20);
			generateMonster(game, monsters, new ChaosWarrior(), ++i, 22, 23);
			generateMonster(game, monsters, new ChaosWarrior(), ++i, 23, 22);
			generateMonster(game, monsters, new Gargoyle(), ++i, 13, 18); // Verag
			
			return monsters;
	}
	
	public boolean verificarCondicoesDeVitoria(HeroQuest game) {
		Creature verag = game.getCreaturePorID(24);
		if (verag.getStatus() == Status.DEAD){
			return true;
		}
		return false;
	}
}

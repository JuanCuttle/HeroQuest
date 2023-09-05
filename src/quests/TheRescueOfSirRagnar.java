package quests;

import java.util.ArrayList;

import entities.Creature;
import entities.tiles.Door;
import entities.enemies.Fimir;
import entities.tiles.Furniture;
import entities.enemies.Goblin;
import entities.HeroQuest;
import entities.Items;
import entities.enemies.Monster;
import entities.enemies.Orc;
import entities.Position;
import entities.SirRagnar;
import entities.utils.Strings;
import entities.tiles.Treasure;


public class TheRescueOfSirRagnar extends BasicMap {
	
	private boolean foundRagnar = false;
	
	public TheRescueOfSirRagnar(HeroQuest game){
		super();
		description = Strings.THERESCUEOFSIRRAGNAR.toString();
		generateDoors(game);
		generateTreasures();
		stairsPosition[0] = 12;
		stairsPosition[1] = 16;
		barbInitialPosition[0] = 12;
		barbInitialPosition[1] = 18;
		wizInitialPosition[0] = 12;
		wizInitialPosition[1] = 19;
		elfInitialPosition[0] = 13;
		elfInitialPosition[1] = 18;
		dwarfInitialPosition[0] = 13;
		dwarfInitialPosition[1] = 19;
		
		numberOfCreatures = 14;
		
		table1Position = new byte[]{0, 4, 26}; // 0 for horizontal, 1 for vertical
		
		table2Position = new byte[]{1, 22, 9};
		
		bookcase1Position = new byte[]{1, 10, 25};
		
		generateFurniture();
	}
	
	// Converts positions where there is furniture into class Furniture, so that they become solid
	private void generateFurniture() {
		generate3x1(bookcase1Position);
		generate2x3(table1Position);
		generate2x3(table2Position);
	}
	
	private void generate3x1(byte[] furniture) {
		int i = furniture[1];
		int j = furniture[2];
		for (int x = 0; x < 3; x++){
			i = furniture[1]+x;
			this.positions[i][j] = new Furniture((byte)i, (byte)j);
		}
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
		for (byte i = 18; i < 20; i++){
			generateBlockade(9, i);
		}
		
		generateBlockade(14, 3);
		generateBlockade(14, 34);
		
		for (byte i = 26; i < 28; i++){
			generateBlockade(i, 14);
			generateBlockade(i, 20);
		}
	}
	
	public void generateDoors(HeroQuest game){
		generateNormalDoor(game, 6, 24);
		generateNormalDoor(game, 8, 27);
		generateNormalDoor(game, 9, 22);
		generateNormalDoor(game, 10, 29);
		generateNormalDoor(game, 13, 33);
		generateNormalDoor(game, 15, 4);
		generateNormalDoor(game, 17, 10);
		generateNormalDoor(game, 17, 18);
		generateNormalDoor(game, 22, 13);
		generateNormalDoor(game, 25, 16);
		generateHiddenDoor(game, 19, 12);
	}
	
	public void generateTreasures(){
		Treasure t = new Treasure(-1);
		t.setAsTrap(true);
		t.setItem(Items.PotionOfHealing);
		positions[19][5].setTreasure(t);
		positions[12][27].setTreasure(new Treasure(50)); // And potion of healing (4 bp)
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game){
			ArrayList<Monster> monsters =  new ArrayList<>();
		
			int i = 0;
			
			generateMonster(game, monsters, new Goblin(), ++i, 10, 21);
			generateMonster(game, monsters, new Goblin(), ++i, 10, 23);
			generateMonster(game, monsters, new Goblin(), ++i, 14, 5);
			generateMonster(game, monsters, new Goblin(), ++i, 14, 33);
			generateMonster(game, monsters, new Goblin(), ++i, 18, 23);
			generateMonster(game, monsters, new Orc(), ++i, 7, 21);
			generateMonster(game, monsters, new Orc(), ++i, 6, 22);
			generateMonster(game, monsters, new Orc(), ++i, 6, 27);
			generateMonster(game, monsters, new Orc(), ++i, 10, 32);
			generateMonster(game, monsters, new Orc(), ++i, 11, 33);
			generateMonster(game, monsters, new Orc(), ++i, 21, 10);
			generateMonster(game, monsters, new Orc(), ++i, 22, 11);
			generateMonster(game, monsters, new Fimir(), ++i, 17, 11);
			
			generateMonster(game, monsters, new SirRagnar(), ++i, 17, 8); // Sir Ragnar
			
			return monsters;
	}
	
	public boolean verifyWinningConditions(HeroQuest game) {
		
		Position p = game.getCreaturePorID(14).getCurrentPosition(); // Sir Ragnar
		
		int row = p.getRow();
		int column = p.getColumn();
		
		// If Sir Ragnar is on stairs
		if ((row == stairsPosition[0] && column == stairsPosition[1])
				| (row == stairsPosition[0]+1 && column == stairsPosition[1])
				| (row == stairsPosition[0] && column == stairsPosition[1]+1)
				| (row == stairsPosition[0]+1 && column == stairsPosition[1]+1)){
			return true;
		}
		return false;
	}
	
	public void specialOcurrence(HeroQuest game){
		if (!foundRagnar) {
			Creature sirRagnar = game.getCreaturePorID(14);
			if (sirRagnar.isVisible()) {
				foundRagnar = true;
				ArrayList<Door> doors = game.doors;
				for (Door d : doors) {
					d.openDoor();
				}
				
				for (Position[] p : this.positions) {
					for (Position pos : p) {
						pos.setVisible(true);
					}
				}
				
				for (Creature c : game.getCreatureQueue()) {
					c.setVisible(true);
				}
				
				game.getAtorJogador().refreshGUI();
			}
		}
	}
}

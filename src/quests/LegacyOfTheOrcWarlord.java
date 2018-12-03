package quests;

import java.util.ArrayList;

import modelo.Barbarian;
import modelo.Creature;
import modelo.Dwarf;
import modelo.Elf;
import modelo.Fimir;
import modelo.Furniture;
import modelo.Goblin;
import modelo.HeroQuest;
import modelo.Monster;
import modelo.Orc;
import modelo.Pit;
import modelo.Position;
import modelo.Strings;
import modelo.Wizard;

public class LegacyOfTheOrcWarlord extends BasicMap {
	
	boolean foundEquipment = false;

	public LegacyOfTheOrcWarlord(HeroQuest game){
		super();
		description = Strings.LEGACYOFTHEORCWARLORD.toString();
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
		
		numberOfCreatures = 0;//19;
		
		table1Position = new byte[]{1, 13, 18}; // 0 for horizontal, 1 for vertical
		
		table2Position = new byte[]{0, 21, 26};
		
		rackPosition = new byte[]{1, 9, 27};
		
		bookcase1Position = new byte[]{1, 4, 7};
		
		generateFurniture();
	}
	
	// Converts positions where there is furniture into class Furniture, so that they become solid
	private void generateFurniture() {
		generate3x1(bookcase1Position);
		generate2x3(table1Position);
		generate2x3(table2Position);
		generate2x3(rackPosition);
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
			
			return new ArrayList<Monster>();//monsters;
	}
	
	public boolean foundEquipment(){
		return foundEquipment;
	}
	
	public boolean verificarCondicoesDeVitoria(HeroQuest game) {
		Position equipment = game.getPosition((byte)5, (byte)5);
		foundEquipment = equipment.isVisible(); // If treasure has been found
		
		int stairsRow = stairsPosition[0];
		int stairsColumn = stairsPosition[1];
		
		boolean stairs = true;
		ArrayList<Creature> creatureQueue = game.getCreatureQueue();
		// Goes through creatures
		for (int i = 0; i < creatureQueue.size(); i++) {
				Creature criatura = creatureQueue.get(i);
				if (criatura instanceof Barbarian || criatura instanceof Wizard
						|| criatura instanceof Elf || criatura instanceof Dwarf) {
					// If there is an adventurer who isn't on stairs, game is not finished yet
					if (!onStairs(criatura.getCurrentPosition(), stairsRow, stairsColumn))
						stairs = false;
				}
		}
		
		return foundEquipment && stairs; // If collected equipment and found exit
	}
	
	private boolean onStairs(Position p, int stairsRow, int stairsColumn){
		int row = p.getRow();
		int column = p.getColumn();
		if (row == stairsRow && column == stairsColumn || row == stairsRow && column == stairsColumn+1
				|| row == stairsRow+1 && column == stairsColumn || row == stairsRow+1
				&& column == stairsColumn+1) {
			return true;
		}
		return false;
	}
}

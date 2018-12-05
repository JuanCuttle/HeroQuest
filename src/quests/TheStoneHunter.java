package quests;

import java.util.ArrayList;

import modelo.ChaosWarrior;
import modelo.Creature;
import modelo.Furniture;
import modelo.Gargoyle;
import modelo.HeroQuest;
import modelo.Items;
import modelo.Monster;
import modelo.Mummy;
import modelo.Pit;
import modelo.Skeleton;
import modelo.Status;
import modelo.Strings;
import modelo.Treasure;
import modelo.Zombie;

public class TheStoneHunter extends BasicMap {
	
	private boolean foundKarlen = false;
	
	public TheStoneHunter(HeroQuest game){
		super();
		description = Strings.THESTONEHUNTER.toString();
		generateDoors(game);
		generateTraps();
		generateTreasures();
		stairsPosition[0] = 8;
		stairsPosition[1] = 11;
		barbInitialPosition[0] = 8;
		barbInitialPosition[1] = 11;
		wizInitialPosition[0] = 8;
		wizInitialPosition[1] = 12;
		elfInitialPosition[0] = 9;
		elfInitialPosition[1] = 11;
		dwarfInitialPosition[0] = 9;
		dwarfInitialPosition[1] = 12;
		
		numberOfCreatures = 12;
		
		table1Position = new byte[]{1, 16, 27}; // 0 for horizontal, 1 for vertical
		
		wepRackPosition = new byte[]{1, 21, 33};
		
		deskPosition = new byte[]{0, 20, 21};
		
		generateFurniture();
	}
	
	// Converts positions where there is furniture into class Furniture, so that they become solid
	private void generateFurniture() {
		generate3x1(wepRackPosition);
		generate2x3(table1Position);
		generate2x3LR(deskPosition);
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
			generateBlockade(19, i);
		}
		generateBlockade(14, 3);
		generateBlockade(14, 28);
		for (byte i = 26; i < 28; i++){
			generateBlockade(i, 3);
			generateBlockade(i, 14);
		}
	}
	
	public void generateDoors(HeroQuest game){
		generateNormalDoor(game, 10, 8);
		generateNormalDoor(game, 13, 6);
		generateNormalDoor(game, 15, 8);
		generateNormalDoor(game, 15, 26);
		generateNormalDoor(game, 18, 7);
		generateNormalDoor(game, 20, 5);
		generateNormalDoor(game, 20, 27);
		generateNormalDoor(game, 22, 25);
		generateNormalDoor(game, 22, 29);
		generateNormalDoor(game, 25, 5);
		generateNormalDoor(game, 25, 11);
	}
	
	public void generateTraps(){
		positions[24][11].setTrap(new Pit());
	}
	
	public void generateTreasures(){
		Treasure borinsArmour = new Treasure(-1);
		borinsArmour.setItem(Items.BorinsArmour);
		positions[21][31].setTreasure(borinsArmour);
		
		positions[24][21].setTreasure(new Treasure(200)); // Karlen's treasure
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game){
			ArrayList<Monster> monsters =  new ArrayList<>();
			
			int i = 0;
			
			generateMonster(game, monsters, new Skeleton(), ++i, 17, 5);
			generateMonster(game, monsters, new Skeleton(), ++i, 18, 6);
			generateMonster(game, monsters, new Skeleton(), ++i, 22, 5);
			generateMonster(game, monsters, new Skeleton(), ++i, 23, 6);
			generateMonster(game, monsters, new Skeleton(), ++i, 23, 31);
			generateMonster(game, monsters, new Zombie(), ++i, 22, 23); // Karlen
			generateMonster(game, monsters, new Mummy(), ++i, 22, 31);
			generateMonster(game, monsters, new ChaosWarrior(), ++i, 10, 14);
			generateMonster(game, monsters, new ChaosWarrior(), ++i, 10, 23);
			generateMonster(game, monsters, new ChaosWarrior(), ++i, 18, 14);
			generateMonster(game, monsters, new ChaosWarrior(), ++i, 18, 23);
			ChaosWarrior stoneWarrior = (ChaosWarrior)monsters.get(monsters.size()-1);
			stoneWarrior.setDefenceDiceAmount((byte)5);
			stoneWarrior = (ChaosWarrior)monsters.get(monsters.size()-2);
			stoneWarrior.setDefenceDiceAmount((byte)5);
			stoneWarrior = (ChaosWarrior)monsters.get(monsters.size()-3);
			stoneWarrior.setDefenceDiceAmount((byte)5);
			stoneWarrior = (ChaosWarrior)monsters.get(monsters.size()-4);
			stoneWarrior.setDefenceDiceAmount((byte)5);
			generateMonster(game, monsters, new Gargoyle(), ++i, 24, 27);
			
			return monsters;
	}
	
	public boolean verificarCondicoesDeVitoria(HeroQuest game) {
		Creature karlen = game.getCreaturePorID(6);
		if (karlen.getStatus() == Status.DEAD){
			return true;
		}
		return false;
	}
	
	public void specialOcurrence(HeroQuest game){
		if (!foundKarlen){
			if (game.getCreaturePorID(6).isVisible()){
				foundKarlen = true;
				game.getAtorJogador().mostrarMensagem(Strings.FOUNDKARLEN.toString());
			}
		}
	}
}

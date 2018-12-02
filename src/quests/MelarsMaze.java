package quests;

import java.util.ArrayList;

import modelo.FallingRock;
import modelo.Furniture;
import modelo.Gargoyle;
import modelo.HeroQuest;
import modelo.Items;
import modelo.Monster;
import modelo.Mummy;
import modelo.Pit;
import modelo.Room;
import modelo.Skeleton;
import modelo.Spear;
import modelo.Strings;
import modelo.Treasure;
import modelo.Zombie;

public class MelarsMaze extends BasicMap {
	
	private boolean throneMoved = false;

	public MelarsMaze(HeroQuest game){
		super();
		description = Strings.MELARSMAZE.toString();
		generateDoors(game);
		generateTraps();
		generateTreasures();
		stairsPosition[0] = 4;
		stairsPosition[1] = 6;
		barbInitialPosition[0] = 4;
		barbInitialPosition[1] = 6;
		wizInitialPosition[0] = 4;
		wizInitialPosition[1] = 7;
		elfInitialPosition[0] = 5;
		elfInitialPosition[1] = 6;
		dwarfInitialPosition[0] = 5;
		dwarfInitialPosition[1] = 7;
		
		numberOfCreatures = 12;
		
		table1Position = new byte[]{0, 12, 16}; // 0 for horizontal, 1 for vertical
		
		bookcase1Position = new byte[]{0, 16, 16};
		
		bookcase2Position = new byte[]{0, 16, 19};
		
		thronePosition = new byte[]{1, 11, 7}; // 0 to face right, 1 to face left
		
		deskPosition = new byte[]{1, 10, 11};
		
		generateFurniture();
	}
	
	// Converts positions where there is furniture into class Furniture, so that they become solid
	private void generateFurniture() {
		generate1x1(thronePosition);
		generate1x3(bookcase1Position);
		generate1x3(bookcase2Position);
		generate2x3(table1Position);
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
			generateBlockade(13, i);
		}
		for (byte i = 18; i < 20; i++){
			generateBlockade(3, i);
			generateBlockade(19, i);
		}
		generateBlockade(10, 20);
		generateBlockade(13, 14);
		generateBlockade(14, 13);
		for (byte i = 18; i < 20; i++){
			generateBlockade(3, i);
			generateBlockade(19, i);
		}
		for (byte i = 35; i < 37; i++){
			generateBlockade(15, i);
		}
		for (byte i = 26; i < 28; i++){
			generateBlockade(i, 17);
		}
	}
	
	public void generateDoors(HeroQuest game){
		generateNormalDoor(game, 3, 9);
		generateNormalDoor(game, 5, 3);
		generateNormalDoor(game, 7, 10);
		generateNormalDoor(game, 10, 3);
		generateNormalDoor(game, 13, 9);
		generateNormalDoor(game, 11, 21);
		generateNormalDoor(game, 15, 5);
		generateNormalDoor(game, 15, 11);
		generateNormalDoor(game, 19, 15);
		generateNormalDoor(game, 20, 6);
		generateNormalDoor(game, 25, 9);
		generateNormalDoor(game, 25, 11);
		generateNormalDoor(game, 25, 14);
		generateHiddenDoor(game, 11, 8);
		generateHiddenDoor(game, 16, 13);
	}
	
	public void generateTraps(){
		for (int i = 1; i < 3; i++){
			positions[i][12].setTrap(new FallingRock());
		}
		positions[5][10].setTrap(new Pit());
		positions[17][14].setTrap(new Pit());
		positions[21][6].setTrap(new Pit());
		positions[24][11].setTrap(new Pit());
		for (int i = 1; i < 3; i++){
			positions[23][i].setTrap(new Spear());
		}
	}
	
	public void generateTreasures(){
		Treasure talismanOfLore = new Treasure(-1);
		talismanOfLore.setItem(Items.TalismanOfLore);
		positions[14][17].setTreasure(talismanOfLore);
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game){
			ArrayList<Monster> monsters =  new ArrayList<>();
			
			int i = 0;
			
			generateMonster(game, monsters, new Skeleton(), ++i, 14, 18);
			generateMonster(game, monsters, new Skeleton(), ++i, 15, 20);
			generateMonster(game, monsters, new Skeleton(), ++i, 18, 14);
			generateMonster(game, monsters, new Skeleton(), ++i, 22, 7);
			generateMonster(game, monsters, new Skeleton(), ++i, 23, 5);
			generateMonster(game, monsters, new Skeleton(), ++i, 24, 6);
			generateMonster(game, monsters, new Zombie(), ++i, 15, 19);
			generateMonster(game, monsters, new Zombie(), ++i, 18, 11);
			generateMonster(game, monsters, new Zombie(), ++i, 27, 12);
			generateMonster(game, monsters, new Mummy(), ++i, 1, 33);
			generateMonster(game, monsters, new Mummy(), ++i, 15, 18);
			generateMonster(game, monsters, new Gargoyle(), ++i, 17, 6);
			
			return monsters;
	}
	
	public boolean verificarCondicoesDeVitoria(HeroQuest game) {
		return positions[14][17].getTreasure().getItem() == null; // Quest treasure taken
	}
	
	public void moveThrone(HeroQuest game){
		if (!throneMoved ){
			thronePosition = new byte[]{1, 11, 7};
			thronePosition[1]--;
			throneMoved = true;
			positions[11][7] = new Room((byte)11, (byte)7);
			generate1x1(thronePosition);
			game.getAtorJogador().mostrarMensagem(Strings.REVEALEDTHRONEPASSAGE.toString());
		}
	}
}

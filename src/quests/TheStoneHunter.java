package quests;

import java.util.ArrayList;

import enums.FurnitureDirectionEnum;
import enums.StatusEnum;
import entities.enemies.ChaosWarrior;
import entities.Creature;
import entities.tiles.Furniture;
import entities.enemies.Gargoyle;
import entities.HeroQuest;
import enums.ItemEnum;
import entities.enemies.Monster;
import entities.enemies.Mummy;
import entities.tiles.Pit;
import entities.enemies.Skeleton;
import entities.utils.Strings;
import entities.tiles.Treasure;
import entities.enemies.Zombie;

public class TheStoneHunter extends BasicMap {
	
	private boolean foundKarlen = false;
	
	public TheStoneHunter(HeroQuest game){
		super();
		description = Strings.THE_STONE_HUNTER_DESCRIPTION.toString();
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
		
		table1Position = new byte[]{FurnitureDirectionEnum.VERTICAL.getId(), 16, 27};
		
		wepRackPosition = new byte[]{FurnitureDirectionEnum.VERTICAL.getId(), 21, 33};
		
		deskPosition = new byte[]{FurnitureDirectionEnum.HORIZONTAL.getId(), 20, 21};
		
		generateFurniture();
	}
	
	private void generateFurniture() {
		generate3x1(wepRackPosition);
		generate2x3(table1Position);
		generate2x3LR(deskPosition);
	}
	
	private void generate2x3LR(byte[] furniture) {
		int i;
		int j;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 2; y++) {
				i = furniture[1]+x;
				j = furniture[2]+y;
				this.positions[i][j] = new Furniture((byte)i, (byte)j);
			}
		}
	}

	private void generate3x1(byte[] furniture) {
		int i;
		int j = furniture[2];
		for (int x = 0; x < 3; x++) {
			i = furniture[1]+x;
			this.positions[i][j] = new Furniture((byte)i, (byte)j);
		}
	}

	private void generate2x3(byte[] furniture) {
		if (furniture != null) {
			int i;
			int j;
			if (furniture[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
				for (int x = 0; x < 2; x++) {
					for (int y = 0; y < 3; y++) {
						i = furniture[1]+x;
						j = furniture[2]+y;
						this.positions[i][j] = new Furniture((byte)i, (byte)j);
					}
				}
			} else {
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 2; y++) {
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
		borinsArmour.setItem(ItemEnum.BorinsArmour);
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
	
	public boolean verifyWinningConditions(HeroQuest game) {
		Creature karlen = game.getCreatureByID(6);
        return StatusEnum.DEAD.equals(karlen.getStatus());
    }
	
	public void specialOccurrence(HeroQuest game) {
		if (!foundKarlen) {
			Creature karlen = game.getCreatureByID(6);
			if (karlen.isVisible()) {
				foundKarlen = true;
				game.getGui().showMessagePopup(Strings.FOUND_KARLEN.toString());
			}
		}
	}

	public boolean getFoundKarlen() {
		return this.foundKarlen;
	}
}

package quests;

import java.util.ArrayList;

import entities.enemies.ChaosWarrior;
import entities.Creature;
import entities.enemies.Fimir;
import entities.tiles.Furniture;
import entities.enemies.Goblin;
import entities.HeroQuest;
import enums.ItemEnum;
import entities.enemies.Monster;
import entities.enemies.Orc;
import entities.Position;
import enums.FurnitureDirectionEnum;
import enums.StatusEnum;
import entities.utils.Strings;
import entities.tiles.Treasure;

public class LairOfTheOrcWarlord extends BasicMap {

	public LairOfTheOrcWarlord(HeroQuest game){
		super();
		description = Strings.LAIR_OF_THE_ORC_WARLORD_DESCRIPTION.toString();
		generateDoors(game);
		generateTreasures();
		stairsPosition[0] = 4;
		stairsPosition[1] = 14;
		barbInitialPosition[0] = 4;
		barbInitialPosition[1] = 14;
		wizInitialPosition[0] = 4;
		wizInitialPosition[1] = 15;
		elfInitialPosition[0] = 5;
		elfInitialPosition[1] = 14;
		dwarfInitialPosition[0] = 5;
		dwarfInitialPosition[1] = 15;
		
		numberOfCreatures = 13;
		
		table1Position = new byte[]{FurnitureDirectionEnum.HORIZONTAL.getId(), 9, 9};
		
		table2Position = new byte[]{FurnitureDirectionEnum.VERTICAL.getId(), 22, 11};
		
		bookcase1Position = new byte[]{FurnitureDirectionEnum.VERTICAL.getId(), 16, 4};
		
		wepRackPosition = new byte[]{FurnitureDirectionEnum.VERTICAL.getId(), 4, 7};
		
		fireplacePosition = new byte[]{FurnitureDirectionEnum.DOWN.getId(), 8, 5};
		
		generateFurniture();
	}
	
	private void generateFurniture() {
		generate3x1(wepRackPosition);
		generate1x3(fireplacePosition);
		generate3x1(bookcase1Position);
		generate2x3(table1Position);
		generate2x3(table2Position);
	}
	
	private void generate1x3(byte[] furniture) {
		int i = furniture[1];
		int j;
		for (int y = 0; y < 3; y++){
			j = furniture[2]+y;
			this.positions[i][j] = new Furniture((byte)i, (byte)j);
		}
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
		for (byte i = 1; i < 3; i++){
			generateBlockade(3, i);
			generateBlockade(15, i);
		}
		generateBlockade(14, 11);
	}
	
	public void generateDoors(HeroQuest game){
		generateNormalDoor(game, 5, 3);
		generateNormalDoor(game, 8, 13);
		generateNormalDoor(game, 11, 8);
		generateNormalDoor(game, 13, 5);
		generateNormalDoor(game, 15, 8);
		generateNormalDoor(game, 18, 7);
		generateNormalDoor(game, 19, 12);
		generateNormalDoor(game, 20, 5);
		generateNormalDoor(game, 23, 8);
	}
	
	public void generateTreasures(){
		Position t = positions[17][5];
		Treasure tr = new Treasure(30);
		tr.setItem(ItemEnum.PotionOfHealing);
		t.setTreasure(tr);
		
		Treasure tr2 = new Treasure(100);
		tr2.setAsTrap(true);
		positions[16][11].setTreasure(tr2);
		
		Treasure spear = new Treasure(-1);
		spear.setItem(ItemEnum.Spear);
		positions[6][6].setTreasure(spear);
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game){
			ArrayList<Monster> monsters =  new ArrayList<>();
			
			int i = 0;
			
			generateMonster(game, monsters, new Goblin(), ++i, 8, 11);
			generateMonster(game, monsters, new Goblin(), ++i, 9, 12);
			generateMonster(game, monsters, new Orc(), ++i, 5, 5);
			generateMonster(game, monsters, new Orc(), ++i, 17, 9);
			generateMonster(game, monsters, new Orc(), ++i, 18, 8);
			generateMonster(game, monsters, new Orc(), ++i, 21, 10);
			// Ulag
			Orc ulag = (Orc) monsters.get(monsters.size()-1);
			ulag.setAttackDiceAmount((byte) 4);
			ulag.setDefenceDiceAmount((byte) 5);
			ulag.setMovementModifier((byte) 2);
			generateMonster(game, monsters, new Orc(), ++i, 22, 4);
			generateMonster(game, monsters, new Orc(), ++i, 22, 7);
			generateMonster(game, monsters, new Orc(), ++i, 23, 6);
			generateMonster(game, monsters, new Fimir(), ++i, 4, 6);
			generateMonster(game, monsters, new Fimir(), ++i, 23, 5);
			generateMonster(game, monsters, new Fimir(), ++i, 24, 6);
			generateMonster(game, monsters, new ChaosWarrior(), ++i, 22, 10);
			
			return monsters;
	}
	
	public boolean verifyWinningConditions(HeroQuest game) {
		Creature ulag = game.getCreatureByID(6);
        return StatusEnum.DEAD.equals(ulag.getStatus());
    }
}

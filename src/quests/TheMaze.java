package quests;

import java.util.ArrayList;

import modelo.Barbarian;
import modelo.Creature;
import modelo.Dwarf;
import modelo.Elf;
import modelo.HeroQuest;
import modelo.Monster;
import modelo.Orc;
import modelo.Strings;
import modelo.Wizard;

public class TheMaze extends BasicMap {
		
		public TheMaze(HeroQuest game){
			super();
			description = Strings.THEMAZE.toString();
			generateDoors(game);
			stairsPosition[0] = 13;
			stairsPosition[1] = 18;
			barbInitialPosition[0] = 1;
			barbInitialPosition[1] = 1;
			wizInitialPosition[0] = 1;
			wizInitialPosition[1] = 36;
			elfInitialPosition[0] = 27;
			elfInitialPosition[1] = 18;
			dwarfInitialPosition[0] = 27;
			dwarfInitialPosition[1] = 36;
			
			numberOfCreatures = 6;
			
		}
		
		public void generateRocks(){
			// Rocks
			for (byte i = 18; i < 20; i++){
				generateBlockade(3, i);
			}
			
			generateBlockade(10, 17);
			generateBlockade(14, 24);
			generateBlockade(15, 14);
			generateBlockade(18, 20);
			
			for (byte i = 1; i < 3; i++){
				generateBlockade(13, i);
				generateBlockade(15, i);
			}
			
			for (byte i = 18; i < 20; i++){
				generateBlockade(25, i);
			}
			
			for (byte i = 26; i < 28; i++){
				generateBlockade(i, 15);
			}
		}
		
		public void generateDoors(HeroQuest game){
			generateNormalDoor(game, 3, 15);
			generateNormalDoor(game, 6, 13);
			generateNormalDoor(game, 10, 3);
			generateNormalDoor(game, 10, 8);
			generateNormalDoor(game, 9, 29);
			generateNormalDoor(game, 10, 34);
			generateNormalDoor(game, 13, 27);
			generateNormalDoor(game, 14, 15);
			generateNormalDoor(game, 14, 22);
			generateNormalDoor(game, 15, 28);
			generateNormalDoor(game, 17, 10);
			generateNormalDoor(game, 18, 3);
			generateNormalDoor(game, 19, 9);
			generateNormalDoor(game, 20, 26);
			generateNormalDoor(game, 21, 25);
			generateNormalDoor(game, 25, 10);
			generateNormalDoor(game, 25, 22);
			generateHiddenDoor(game, 8, 13);
			generateHiddenDoor(game, 13, 6);
			generateHiddenDoor(game, 15, 8);
			generateHiddenDoor(game, 17, 24);
		}
		
		
		public ArrayList<Monster> createMonsters(HeroQuest game){
				ArrayList<Monster> monsters =  new ArrayList<>();
			
				int i = 0;
				
				generateMonster(game, monsters, new Orc(), ++i, 5, 11);
				generateMonster(game, monsters, new Orc(), ++i, 10, 31);
				generateMonster(game, monsters, new Orc(), ++i, 14, 14);
				generateMonster(game, monsters, new Orc(), ++i, 14, 23);
				generateMonster(game, monsters, new Orc(), ++i, 17, 12);
				generateMonster(game, monsters, new Orc(), ++i, 21, 22);
				
				return monsters;
		}
		
		public boolean verificarCondicoesDeVitoria(HeroQuest game) {
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
						if (!onStairs(criatura.getCurrentPosition(), stairsRow, stairsColumn)){
							stairs = false;
							break;
						}
					}
			}
			return stairs;
		}

}

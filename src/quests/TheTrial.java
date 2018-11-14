package quests;

import java.util.ArrayList;

import modelo.ChaosWarrior;
import modelo.Creature;
import modelo.Door;
import modelo.Fimir;
import modelo.Gargoyle;
import modelo.Goblin;
import modelo.HeroQuest;
import modelo.Monster;
import modelo.Mummy;
import modelo.Orc;
import modelo.Skeleton;
import modelo.Status;
import modelo.Treasure;
import modelo.Wall;
import modelo.Zombie;


public class TheTrial extends BasicMap {
	
	public TheTrial(HeroQuest jogo){
		super();
		generateDoors(jogo);
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
		
		numberOfCreatures = 25;
	}
	
	public void generateRocks(){
		// Rocks
		for (byte i = 1; i < 3; i++){
			positions[3][i] = new Wall(3,i);
		}
		for (byte i = 18; i < 20; i++){
			positions[9][i] = new Wall(9,i);
		}
		positions[14][24] = new Wall(14,24);
		for (byte i = 26; i < 28; i++){
			positions[i][20] = new Wall(i,20);
		}
	}
	
	public void generateDoors(HeroQuest jogo){
		positions[5][8] = new Door(5,8,58);
		positions[5][13] = new Door(5,13,513);
		positions[7][6] = new Door(7,6,76);
		positions[13][6] = new Door(13,6,136);
		positions[13][11] = new Door(13,11,1311);
		positions[14][22] = new Door(14,22,1422);
		positions[17][3] = new Door(17,3,173);
		positions[19][15] = new Door(19,15,1915);
		positions[22][13] = new Door(22,13,2213);
		positions[22][20] = new Door(22,20,2220);
		positions[25][6] = new Door(25,6,256);
		positions[25][11] = new Door(25,11,2511);
		
		jogo.doors.add((Door)positions[5][8]);
		jogo.doors.add((Door)positions[5][13]);
		jogo.doors.add((Door)positions[7][6]);
		jogo.doors.add((Door)positions[13][6]);
		jogo.doors.add((Door)positions[13][11]);
		jogo.doors.add((Door)positions[14][22]);
		jogo.doors.add((Door)positions[17][3]);
		jogo.doors.add((Door)positions[19][15]);
		jogo.doors.add((Door)positions[22][13]);
		jogo.doors.add((Door)positions[22][20]);
		jogo.doors.add((Door)positions[25][6]);
		jogo.doors.add((Door)positions[25][11]);
	}
	
	public void generateTreasures(){
		positions[8][15].setTreasure(new Treasure(84));
		positions[12][17].setTreasure(new Treasure(120));
		positions[23][24].setTreasure(new Treasure(0));
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game){
			ArrayList<Monster> monsters =  new ArrayList<>();
		
			Goblin goblin1 = new Goblin();
			goblin1.setID((byte) 1);
			game.creatureInPosition(goblin1, 10, 6);
			monsters.add(goblin1);
			
			Goblin goblin2 = new Goblin();
			goblin2.setID((byte) 2);
			game.creatureInPosition(goblin2, 11, 10);
			monsters.add(goblin2);

			Goblin goblin3 = new Goblin();
			goblin3.setID((byte) 3);
			game.creatureInPosition(goblin3, 11, 11);
			monsters.add(goblin3);
			
			Goblin goblin4 = new Goblin();
			goblin4.setID((byte) 4);
			game.creatureInPosition(goblin4, 17, 5);
			monsters.add(goblin4);
			
			Goblin goblin5 = new Goblin();
			goblin5.setID((byte) 5);
			game.creatureInPosition(goblin5, 21, 15);
			monsters.add(goblin5);
			
			Skeleton skeleton1 = new Skeleton();
			skeleton1.setID((byte) 6);
			game.creatureInPosition(skeleton1, 5, 5);
			monsters.add(skeleton1);
			
			Skeleton skeleton2 = new Skeleton();
			skeleton2.setID((byte) 7);
			game.creatureInPosition(skeleton2, 5, 6);
			monsters.add(skeleton2);
			
			Skeleton skeleton3 = new Skeleton();
			skeleton3.setID((byte) 8);
			game.creatureInPosition(skeleton3, 4, 14);
			monsters.add(skeleton3);
			
			Skeleton skeleton4 = new Skeleton();
			skeleton4.setID((byte) 9);
			game.creatureInPosition(skeleton4, 6, 14);
			monsters.add(skeleton4);
			
			Zombie zombie1 = new Zombie();
			zombie1.setID((byte) 10);
			game.creatureInPosition(zombie1, 4, 10);
			monsters.add(zombie1);
			
			Zombie zombie2 = new Zombie();
			zombie2.setID((byte) 11);
			game.creatureInPosition(zombie2, 6, 10);
			monsters.add(zombie2);
			
			// Guardian of Fellmarg's tomb, +1 attack dice
			Mummy mummy1 = new Mummy();
			mummy1.setID((byte) 12);
			mummy1.setAttackDiceAmount((byte) 4);
			game.creatureInPosition(mummy1, 5, 10);
			monsters.add(mummy1);
			
			Mummy mummy2 = new Mummy();
			mummy2.setID((byte) 13);
			game.creatureInPosition(mummy2, 7, 14);
			monsters.add(mummy2);
			
			Mummy mummy3 = new Mummy();
			mummy3.setID((byte) 14);
			game.creatureInPosition(mummy3, 5, 10);
			monsters.add(mummy3);
			
			Orc orc1 = new Orc();
			orc1.setID((byte) 15);
			game.creatureInPosition(orc1, 9, 7);
			monsters.add(orc1);
			
			Orc orc2 = new Orc();
			orc2.setID((byte) 16);
			game.creatureInPosition(orc2, 13, 20);
			monsters.add(orc2);
			
			Orc orc3 = new Orc();
			orc3.setID((byte) 17);
			game.creatureInPosition(orc3, 16, 17);
			monsters.add(orc3);
			
			Orc orc4 = new Orc();
			orc4.setID((byte) 18);
			game.creatureInPosition(orc4, 18, 5);
			monsters.add(orc4);
			
			Orc orc5 = new Orc();
			orc5.setID((byte) 19);
			game.creatureInPosition(orc5, 21, 11);
			monsters.add(orc5);
			
			Orc orc6 = new Orc();
			orc6.setID((byte) 20);
			game.creatureInPosition(orc6, 22, 12);
			monsters.add(orc6);
			
			Fimir fimir1 = new Fimir();
			fimir1.setID((byte) 21);
			game.creatureInPosition(fimir1, 23, 15);
			monsters.add(fimir1);
			
			ChaosWarrior chaos_Warrior1 = new ChaosWarrior();
			chaos_Warrior1.setID((byte) 22);
			game.creatureInPosition(chaos_Warrior1, 16, 20);
			monsters.add(chaos_Warrior1);
			
			ChaosWarrior chaos_Warrior2 = new ChaosWarrior();
			chaos_Warrior2.setID((byte) 23);
			game.creatureInPosition(chaos_Warrior2, 22, 23);
			monsters.add(chaos_Warrior2);
			
			ChaosWarrior chaos_Warrior3 = new ChaosWarrior();
			chaos_Warrior3.setID((byte) 24);
			game.creatureInPosition(chaos_Warrior3, 23, 22);
			monsters.add(chaos_Warrior3);
			
			Gargoyle verag = new Gargoyle();
			verag.setID((byte) 25);
			game.creatureInPosition(verag, 13, 18);
			monsters.add(verag);
			
			return monsters;
	}
	
	public boolean verificarCondicoesDeVitoria(HeroQuest game) {
		Creature verag = game.getCreaturePorID(25);
		if (verag.getStatus() == Status.DEAD){
			return true;
		}
		return false;
	}
}

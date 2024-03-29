package quests;

import java.util.ArrayList;

import entities.tiles.Corridor;
import entities.tiles.Door;
import entities.tiles.FallingRock;
import services.HeroQuest;
import entities.enemies.Monster;
import entities.tiles.Pit;
import entities.Position;
import entities.tiles.Room;
import entities.tiles.Spear;
import entities.tiles.Treasure;
import entities.tiles.Wall;

public class BasicMap {
	
	public String description;
	
	public Position[][] positions; 

	protected byte totalNumberOfRows;
	protected byte totalNumberOfColumns;

	protected byte[] barbInitialPosition;
	protected byte[] wizInitialPosition;
	protected byte[] elfInitialPosition;
	protected byte[] dwarfInitialPosition;
	
	protected byte[] thronePosition;
	protected byte[] wepRackPosition;
	protected byte[] fireplacePosition;
	protected byte[] bookcase1Position;
	protected byte[] bookcase2Position;
	protected byte[] bookcase3Position;

	protected byte[] stairsPosition;
	protected byte[] table1Position;
	protected byte[] table2Position;
	protected byte[] rackPosition;
	protected byte[] bookOnTablePosition;
	protected byte[] tombPosition;
	protected byte[] deskPosition;
	protected int numberOfCreatures;
	
	public BasicMap() {
		this.totalNumberOfRows = 29;
		this.totalNumberOfColumns = 38;
		positions = new Position[totalNumberOfRows][totalNumberOfColumns];
		generateCleanMap();
		generateCorridors();
		generateWalls();
        barbInitialPosition = new byte[2];
		wizInitialPosition = new byte[2];
		elfInitialPosition = new byte[2];
		dwarfInitialPosition = new byte[2];
		stairsPosition = new byte[2];
	}
	
	public void generateWalls() {
		System.out.println("Debug: initializing walls");
		topLeftWalls();
		bottomLeftWalls();
		topRightWalls();
		bottomRightWalls();
		centralRoom();
		mapBorders();
		generateRocks();
		System.out.println("Debug: Walls initialized");
	}
	
	protected void generateRocks() {
		
	}

	protected void mapBorders() {
		int lastColumn = this.totalNumberOfColumns -1;
		int lastRow = this.totalNumberOfRows -1;
		for (byte i = 0; i < totalNumberOfColumns; i++) {
			positions[0][i] = new Wall(0,i);
			positions[lastRow][i] = new Wall(lastRow,i);
		}
		for (byte i = 0; i < totalNumberOfRows; i++) {
			positions[i][0] = new Wall(i,0);
			positions[i][lastColumn] = new Wall(i,lastColumn);
		}
	}

	protected void centralRoom() {
		// Central room
		// Columns
		for (byte i = 11; i < 18; i++){
			positions[i][15] = new Wall(i, 15);
			positions[i][22] = new Wall(i, 22);
		}
		// Rows
		for (byte j = 15; j < 23; j++){
			positions[11][j] = new Wall(11, j);
			positions[17][j] = new Wall(17, j);
		}
	}

	protected void bottomRightWalls(){
		int numberOfRows = this.totalNumberOfRows -1;
		int numberOfColumns = this.totalNumberOfColumns -1;
		// Room 1
		// Columns
		for (byte i = 3; i < 8; i++) {
			positions[numberOfRows-i][numberOfColumns-3] = new Wall(numberOfRows-i,numberOfColumns-3);
			positions[numberOfRows-i][numberOfColumns-8] = new Wall(numberOfRows-i,numberOfColumns-8);
		}
		// Rows
		for (byte i = 4; i < 8; i++) {
			positions[numberOfRows-3][numberOfColumns-i] = new Wall(numberOfRows-3,numberOfColumns-i);
			positions[numberOfRows-8][numberOfColumns-i] = new Wall(numberOfRows-8,numberOfColumns-i);
		}
		// Room 2
		// Columns
		for (byte i = 20; i < 25; i++) {
			positions[i][numberOfColumns-12] = new Wall(i,numberOfColumns-12);
		}
		// Rows
		for (byte i = 8; i < 14; i++) {
			positions[numberOfRows-3][numberOfColumns-i] = new Wall(numberOfRows-3,numberOfColumns-i);
			positions[numberOfRows-8][numberOfColumns-i] = new Wall(numberOfRows-8,numberOfColumns-i);
		}
		// Room 3
		// Columns
		positions[numberOfRows-8][numberOfColumns-13] = new Wall(numberOfRows-8, numberOfColumns-13);
		for (byte i = 3; i < 10; i++) {
			positions[numberOfRows-i][numberOfColumns-17] = new Wall(numberOfRows-i,numberOfColumns-17);
		}
		// Rows
		for (byte i = 13; i < 18; i++) {
			positions[numberOfRows-3][numberOfColumns-i] = new Wall(numberOfRows-3,numberOfColumns-i);
			positions[numberOfRows-9][numberOfColumns-i] = new Wall(numberOfRows-9,numberOfColumns-i);
		}
		// Room 5
		// Columns
		for (byte i = 8; i < 14; i++) {
			positions[numberOfRows-i][numberOfColumns-8] = new Wall(numberOfRows-i,numberOfColumns-8);
			positions[numberOfRows-i][numberOfColumns-13] = new Wall(numberOfRows-i,numberOfColumns-13);
		}
		positions[20][24] = new Room((byte)20, (byte)24);
		positions[19][25] = new Wall(19, 25);
		// Rows
		for (byte i = 9; i < 13; i++) {
			positions[numberOfRows-13][numberOfColumns-i] = new Wall(numberOfRows-13,numberOfColumns-i);
		}
		// Room 4
		// Columns
		for (byte i = 8; i < 14; i++) {
			positions[numberOfRows-i][numberOfColumns-3] = new Wall(numberOfRows-i,numberOfColumns-3);
		}
		// Rows
		for (byte i = 4; i < 8; i++) {
			positions[numberOfRows-13][numberOfColumns-i] = new Wall(numberOfRows-13,numberOfColumns-i);
		}
	}

	protected void topRightWalls(){
		int numberOfColumns = this.totalNumberOfColumns -1;
		// Room 1
		// Columns
		for (byte i = 3; i < 8; i++) {
			positions[i][numberOfColumns-3] = new Wall(i,numberOfColumns-3);
			positions[i][numberOfColumns-8] = new Wall(i,numberOfColumns-8);
		}
		// Rows
		for (byte i = 4; i < 8; i++) {
			positions[3][numberOfColumns-i] = new Wall(3,numberOfColumns-i);
			positions[8][numberOfColumns-i] = new Wall(8,numberOfColumns-i);
		}
		// Room 2
		// Columns
		for (byte i = 3; i < 8; i++) {
			positions[i][numberOfColumns-13] = new Wall(i,numberOfColumns-13);
		}
		// Rows
		for (byte i = 8; i < 14; i++) {
			positions[3][numberOfColumns-i] = new Wall(3,numberOfColumns-i);
			positions[8][numberOfColumns-i] = new Wall(8,numberOfColumns-i);
		}
		// Room 3
		// Columns
		positions[8][numberOfColumns-13] = new Wall(8, numberOfColumns-13);
		for (byte i = 3; i < 10; i++) {
			positions[i][numberOfColumns-17] = new Wall(i,numberOfColumns-17);
		}
		// Rows
		for (byte i = 13; i < 18; i++) {
			positions[3][numberOfColumns-i] = new Wall(3,numberOfColumns-i);
			positions[9][numberOfColumns-i] = new Wall(9,numberOfColumns-i);
		}
		// Room 5
		// Columns
		for (byte i = 8; i < 14; i++) {
			positions[i][numberOfColumns-8] = new Wall(i,numberOfColumns-8);
			positions[i][numberOfColumns-13] = new Wall(i,numberOfColumns-13);
		}
		// Rows
		for (byte i = 9; i < 13; i++) {
			positions[13][numberOfColumns-i] = new Wall(13,numberOfColumns-i);
		}
		// Room 4
		// Columns
		for (byte i = 8; i < 14; i++) {
			positions[i][numberOfColumns-3] = new Wall(i,numberOfColumns-3);
		}
		// Rows
		for (byte i = 4; i < 8; i++) {
			positions[13][numberOfColumns-i] = new Wall(13,numberOfColumns-i);
		}
	}
	
	protected void bottomLeftWalls(){
		int numberOfRows = this.totalNumberOfRows -1;
		// Room 1
		// Columns
		for (byte i = 3; i < 9; i++) {
			positions[numberOfRows-i][3] = new Wall(numberOfRows-i,3);
			positions[numberOfRows-i][8] = new Wall(numberOfRows-i,8);
		}
		// Rows
		for (byte i = 4; i < 8; i++) {
			positions[numberOfRows-3][i] = new Wall(numberOfRows-3,i);
			positions[numberOfRows-8][i] = new Wall(numberOfRows-8,i);
		}
		// Room 2
		// Columns
		for (byte i = 3; i < 8; i++) {
			positions[numberOfRows-i][13] = new Wall(numberOfRows-i,13);
		}
		// Rows
		for (byte i = 8; i < 14; i++) {
			positions[numberOfRows-3][i] = new Wall(numberOfRows-3,i);
			positions[numberOfRows-9][i] = new Wall(numberOfRows-9,i);
		}
		// Room 3
		// Columns
		positions[numberOfRows-8][13] = new Wall(numberOfRows-8, 13);
		for (byte i = 3; i < 10; i++) {
			positions[numberOfRows-i][17] = new Wall(numberOfRows-i,17);
		}
		// Rows
		for (byte i = 13; i < 18; i++) {
			positions[numberOfRows-3][i] = new Wall(numberOfRows-3,i);
			positions[numberOfRows-9][i] = new Wall(numberOfRows-9,i);
		}
		// Room 5
		// Columns
		for (byte i = 8; i < 14; i++) {
			positions[numberOfRows-i][7] = new Wall(numberOfRows-i,7);
			positions[numberOfRows-i][13] = new Wall(numberOfRows-i,13);
		}
		// Rows
		for (byte i = 8; i < 13; i++) {
			positions[numberOfRows-13][i] = new Wall(numberOfRows-13,i);
		}
		// Room 4
		// Columns
		for (byte i = 8; i < 14; i++) {
			positions[numberOfRows-i][3] = new Wall(numberOfRows-i,3);
		}
		// Rows
		for (byte i = 4; i < 8; i++) {
			positions[numberOfRows-13][i] = new Wall(numberOfRows-13,i);
		}
		
		for (byte i = 16; i < 19; i++){
			positions[i][10] = new Wall(i, 10);
		}
	}
	
	protected void topLeftWalls() {
		// Room 1
		// Columns
		for (byte i = 3; i < 8; i++) {
			positions[i][3] = new Wall(i,3);
			positions[i][8] = new Wall(i,8);
		}
		// Rows
		for (byte i = 4; i < 8; i++) {
			positions[3][i] = new Wall(3,i);
			positions[7][i] = new Wall(7,i);
		}
		// Room 2
		// Columns
		for (byte i = 3; i < 8; i++) {
			positions[i][13] = new Wall(i,13);
		}
		// Rows
		for (byte i = 8; i < 14; i++) {
			positions[3][i] = new Wall(3,i);
			positions[7][i] = new Wall(7,i);
		}
		// Room 3
		// Columns
		positions[8][13] = new Wall(8, 13);
		for (byte i = 3; i < 10; i++) {
			positions[i][17] = new Wall(i,17);
		}
		// Rows
		for (byte i = 13; i < 18; i++) {
			positions[3][i] = new Wall(3,i);
			positions[9][i] = new Wall(9,i);
		}
		// Room 5
		// Columns
		for (byte i = 8; i < 14; i++) {
			positions[i][8] = new Wall(i,8);
			positions[i][13] = new Wall(i,13);
		}
		// Rows
		for (byte i = 9; i < 13; i++) {
			positions[13][i] = new Wall(13,i);
		}
		// Room 4
		// Columns
		for (byte i = 8; i < 14; i++) {
			positions[i][3] = new Wall(i,3);
		}
		// Rows
		for (byte i = 4; i < 8; i++) {
			positions[13][i] = new Wall(13,i);
		}
	}

	public Position getPosition(byte row, byte column) {
		return positions[row][column];
	}

	public Position[][] getPositions() {
		return this.positions;
	}

	public void generateCleanMap() {
		System.out.println("Debug: initializing map");
		for(byte i = 0; i < totalNumberOfRows; i++)
			for(byte j = 0; j < totalNumberOfColumns; j++) {
				positions[i][j] = new Room(i,j);
			}
	}

	public void generateCorridors() {
		System.out.println("Debug: initializing corridors");
		for (byte i = 1; i < 28; i++) {
			for (byte j = 1; j < 3; j++) {
				positions[i][j] = new Corridor(i,j);
			}
			for (byte j = 35; j < 37; j++) {
				positions[i][j] = new Corridor(i,j);
			}
		}
		for (byte i = 3; i < 35; i++) {
			for (byte j = 1; j < 3; j++) {
				positions[j][i] = new Corridor(j,i);
			}
			for (byte j = 26; j < 28; j++) {
				positions[j][i] = new Corridor(j,i);
			}
		}
		for (byte i = 18; i < 20; i++) {
			for (byte j = 3; j < 10; j++) {
				positions[j][i] = new Corridor(j,i);
			}
			for (byte j = 19; j < 26; j++) {
				positions[j][i] = new Corridor(j,i);
			}
		}
		byte centerCorridor = 14;
		for (byte i = 3; i < 14; i++){
			positions[centerCorridor][i] = new Corridor(centerCorridor,i);
		}
		for (byte i = 24; i < 35; i++){
			positions[centerCorridor][i] = new Corridor(centerCorridor,i);
		}
		byte row = 10;
		for (byte i = 14; i < 24; i++){
			positions[row][i] = new Corridor(row,i);
		}
		row = 18;
		for (byte i = 14; i < 24; i++){
			positions[row][i] = new Corridor(row,i);
		}
		byte column = 14;
		for (byte i = 11; i < 18; i++){
			positions[i][column] = new Corridor(i,column);
		}
		column = 23;
		for (byte i = 11; i < 18; i++){
			positions[i][column] = new Corridor(i,column);
		}
		System.out.println("Debug: corridors initialized");
	}
	
	public void generateDoors(HeroQuest game) {
		positions[4][3] = new Door(4,3,43);
		positions[23][4] = new Door(23,4,234);
		positions[3][19] = new Door(3,19,319);
		positions[15][15] = new Door(15,15,1515);
		positions[12][19] = new Door(12,19,1219);
		positions[19][18] = new Door(19,18,1918);
		positions[3][30] = new Door(3,30,330);
		positions[6][46] = new Door(6,46,646); // hidden door
		positions[12][31] = new Door(12,31,1231);
		positions[12][42] = new Door(12,42,1242);
		positions[17][37] = new Door(17,37,1737);
		positions[18][38] = new Door(18,38,1838);
		positions[22][31] = new Door(22,31,2231);
    }
	public void generateTraps() {
		for (byte i = 27; i < 37; i++) {
			positions[7][i].setTrap(new Pit());
		}
		for (byte i = 19; i < 23; i++) {
			positions[i][24].setTrap(new Spear());
		}
		for (byte i = 16; i < 23; i+=2) {
			positions[7][i].setTrap(new FallingRock());
		}
		for (byte i = 17; i < 22; i+=2) {
			positions[8][i].setTrap(new FallingRock());
		}
		positions[15][16].setTrap(new Spear());
		positions[17][36].setTrap(new Spear());
		positions[24][29].setTrap(new FallingRock());
		positions[25][29].setTrap(new FallingRock());
	}
	
	public void generateTreasures() {
		for (byte i = 38; i < 45; i+=2) {
			positions[4][i].setTreasure(new Treasure(50));
		}
	}
	public void updatePosition(Position newPosition) {
		this.positions[newPosition.getRow()][newPosition.getColumn()] = newPosition;
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest game){
		return new ArrayList<>();
	}
	
	protected void generateNormalDoor(HeroQuest game, int row, int column) {
		positions[row][column] = new Door(row,column,Integer.parseInt(row+""+column));
		game.getDoors().add((Door)positions[row][column]);
	}
	
	protected void generateHiddenDoor(HeroQuest game, int row, int column) {
		positions[row][column] = new Door(row,column,Integer.parseInt(row+""+column));
		game.getDoors().add((Door)positions[row][column]);
		game.getDoors().get(game.getDoors().size()-1).setSecret(true);
	}
	
	protected void generateBlockade(int row, int column) {
		positions[row][column] = new Position(row,column);
		positions[row][column].setTrap(new FallingRock());
		positions[row][column].makeTrapVisible();
		positions[row][column].makeTrapTriggered();
	}
	
	protected void generateMonster(HeroQuest game, ArrayList<Monster> monsters, Monster monster, int id, int row, int column) {
		monster.setID((byte) id);
		game.setCreatureInPosition(monster, row, column);
		monsters.add(monster);
	}
	
	public byte getTotalNumberOfRows() {
		return totalNumberOfRows;
	}

	public byte getTotalNumberOfColumns() {
		return totalNumberOfColumns;
	}
	
	public byte[] getStairsPosition() {
		return stairsPosition;
	}
	
	public byte[] getBarbarianInitialPosition() {
		return barbInitialPosition;
	}

	public byte[] getWizardInitialPosition() {
		return wizInitialPosition;
	}

	public byte[] getElfInitialPosition() {
		return elfInitialPosition;
	}

	public byte[] getDwarfInitialPosition() {
		return dwarfInitialPosition;
	}
	
	public byte[] getTable1Position() {
		return table1Position;
	}

	public byte[] getTable2Position() {
		return table2Position;
	}
	
	public byte[] getRackPosition() {
		return rackPosition;
	}
	
	public byte[] getBookOnTablePosition() {
		return bookOnTablePosition;
	}
	
	public byte[] getTombPosition() {
		return tombPosition;
	}
	
	public byte[] getThronePosition() {
		return thronePosition;
	}
	
	public byte[] getWepRackPosition() {
		return wepRackPosition;
	}
	
	public byte[] getDeskPosition() {
		return deskPosition;
	}
	
	public byte[] getFireplacePosition() {
		return fireplacePosition;
	}
	
	public byte[] getBookcase1Position() {
		return bookcase1Position;
	}

	public byte[] getBookcase2Position() {
		return bookcase2Position;
	}

	public byte[] getBookcase3Position() {
		return bookcase3Position;
	}

	public boolean verifyWinningConditions(HeroQuest game) {
		boolean victory = false;
		for (int i = 0; i < game.getCreatureQueue().size(); i++) {
			if (game.getCreatureQueue().get(i).getID() > 19
					&& game.getCreatureQueue().get(i).getID() < 24) {
				Position heroPosition = game.getCreatureQueue().get(i).getCurrentPosition();
				int heroRow = heroPosition.getRow();
				int heroColumn = heroPosition.getColumn();
				if (heroRow == 24 && heroColumn == 24 || heroRow == 24 && heroColumn == 25
						|| heroRow == 25 && heroColumn == 24 || heroRow == 25
						&& heroColumn == 25) {
					victory = true;
				}
			}
		}
		return victory;
	}

	public int getCreatureQueueSize() {
		return this.numberOfCreatures + 4; // Monsters plus up to 4 adventurers
	}

	public void specialOccurrence(HeroQuest game) {
		
	}
	
	protected boolean onStairs(Position heroPosition, int stairsRow, int stairsColumn) {
		int heroRow = heroPosition.getRow();
		int heroColumn = heroPosition.getColumn();
        return heroRow == stairsRow && heroColumn == stairsColumn || heroRow == stairsRow && heroColumn == stairsColumn + 1
                || heroRow == stairsRow + 1 && heroColumn == stairsColumn || heroRow == stairsRow + 1
                && heroColumn == stairsColumn + 1;
    }
}

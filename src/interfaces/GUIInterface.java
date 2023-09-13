package interfaces;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;

import entities.*;
import enums.DirectionEnum;
import enums.ItemEnum;
import enums.TrapEvasionMovementEnum;
import enums.StatusEnum;

public interface GUIInterface {
	
	void selectCharacter() throws ClassNotFoundException;
	
	void openDoor(int doorId);
	
	void showMessagePopup(String msg);
	
	void reportError(String msg);
	
	void moveCreature(DirectionEnum direction);
	
	void attack();
	
	void castSpell();
	
	Spell selectSpell(ArrayList<Spell> availableSpells);
	
	Creature selectTarget(ArrayList<Creature> availableTargets);
	
	void refreshGUI();
	
	void searchForTreasure();
	
	void showCharacterSelectionScreen();
	
	void searchForTrapsAndHiddenDoors();
	
	void endTurn();
	
	String obtainPlayerName();
	
	String obtainServerAddress();
	
	void connectToServer();
	
	void showConnectionResultMessage(int result);

	void disconnectFromServer();
	
	void startGame();
	
	void announceHeroesWon();
	
	void announceZargonWon();
	
	void showInventory();
	
	void showInventory(int gold, ArrayList<ItemEnum> items);
	
	void showCreatureInformation(int characterID);
	
	void showCreatureInformation(byte body, byte mind, byte movement,
								 StatusEnum statusEnum, int row, int column, Byte roundsToSleep);
	
	int setTotalNumberOfPlayersInTheGame();
	
	void refreshTile(JButton button, Position position);
	
	void showVisibleCreaturesInQueue();
	
	void showTrapActivationMessage(byte damage, Creature creature);
	
	void showAttackDamageMessage(Creature target, byte damage, boolean selfInflicted);
	
	void announceCreatureDeath(Creature creature);
	
	void showEffectOfCastSpell(Creature caster, Spell spell, Creature target,
							   byte damage, StatusEnum statusEnum);
	
	void announceUnfortunateDeath(Creature creature);
	
	void updatePlayerSurroundings();
	
	int selectDoorToOpenOrClose(ArrayList<String> doorIds);
	
	void createMusic() throws Exception;
	
	void toggleMusic();
	
	void showTrapRemovalMessage();
	
	TrapEvasionMovementEnum showFallingRockMovementOptions();
	
	TrapEvasionMovementEnum showPitJumpingOptions();

	ArrayList<String> readSaveFile(String localPlayerName) throws IOException, ClassNotFoundException;

	boolean checkSaveFileExists(String localPlayerName);

	String getTitle();

	void setTitle(String s);

	void writeSaveFile(String localPlayerName, int heroType, int gold, ArrayList<ItemEnum> items) throws IOException;
}

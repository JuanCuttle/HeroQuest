package interfaces;

import java.util.ArrayList;

import entities.Creature;
import entities.Position;
import entities.actions.Action;
import enums.DirectionEnum;
import view.ClientServerProxy;

public interface LogicInterface {
	
	void openDoor(int doorId);
	
	void openDoorWithKeyboard();
	
	void move(DirectionEnum direction);
	
	Creature getCurrentCreature();
	
	void attack();
	
	void castSpell();
	
	ArrayList<Creature> getCreatureQueue();
	
	void sendAction(Action action);
	
	void processAction(Action action);
	
	void searchForTreasure();
	
	void selectCharacter() throws ClassNotFoundException;

	void searchForTrapsAndHiddenDoors();
	
	void endTurn();
	
	boolean isConnected();
	
	void setConnected(boolean connected);
	
	boolean isGameInSession();
	
	void endGame();
	
	void startNewGame();
	
	void showInventory();
	
	void showCreatureInformation(int creatureID);
	
	Position getPosition(byte i, byte j);
	
	void setLocalPlayerName(String playerName);

	void setServerAddress(String serverAddress);
	
	ClientServerProxy getClientServerProxy();
}

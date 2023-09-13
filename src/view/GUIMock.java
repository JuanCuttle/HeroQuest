package view;

import entities.Creature;
import entities.Position;
import entities.Spell;
import enums.DirectionEnum;
import enums.ItemEnum;
import enums.StatusEnum;
import enums.TrapEvasionMovementEnum;
import interfaces.GUIInterface;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class GUIMock implements GUIInterface {
    @Override
    public void selectCharacter() throws ClassNotFoundException {

    }

    @Override
    public void openDoor(int doorId) {

    }

    @Override
    public void showMessagePopup(String msg) {

    }

    @Override
    public void reportError(String msg) {

    }

    @Override
    public void moveCreature(DirectionEnum direction) {

    }

    @Override
    public void attack() {

    }

    @Override
    public void castSpell() {

    }

    @Override
    public Spell selectSpell(ArrayList<Spell> availableSpells) {
        return null;
    }

    @Override
    public Creature selectTarget(ArrayList<Creature> availableTargets) {
        return null;
    }

    @Override
    public void refreshGUI() {

    }

    @Override
    public void searchForTreasure() {

    }

    @Override
    public void showCharacterSelectionScreen() {

    }

    @Override
    public void searchForTrapsAndHiddenDoors() {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public String obtainPlayerName() {
        return null;
    }

    @Override
    public String obtainServerAddress() {
        return null;
    }

    @Override
    public void connectToServer() {

    }

    @Override
    public void showConnectionResultMessage(int result) {

    }

    @Override
    public void disconnectFromServer() {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void announceHeroesWon() {

    }

    @Override
    public void announceZargonWon() {

    }

    @Override
    public void showInventory() {

    }

    @Override
    public void showInventory(int gold, ArrayList<ItemEnum> items) {

    }

    @Override
    public void showCreatureInformation(int characterID) {

    }

    @Override
    public void showCreatureInformation(byte body, byte mind, byte movement, StatusEnum statusEnum, int row, int column, Byte roundsToSleep) {

    }

    @Override
    public int setTotalNumberOfPlayersInTheGame() {
        return 0;
    }

    @Override
    public void refreshTile(JButton button, Position position) {

    }

    @Override
    public void showVisibleCreaturesInQueue() {

    }

    @Override
    public void showTrapActivationMessage(byte damage, Creature creature) {

    }

    @Override
    public void showAttackDamageMessage(Creature target, byte damage, boolean selfInflicted) {

    }

    @Override
    public void announceCreatureDeath(Creature creature) {

    }

    @Override
    public void showEffectOfCastSpell(Creature caster, Spell spell, Creature target, byte damage, StatusEnum statusEnum) {

    }

    @Override
    public void announceUnfortunateDeath(Creature creature) {

    }

    @Override
    public void updatePlayerSurroundings() {

    }

    @Override
    public int selectDoorToOpenOrClose(ArrayList<String> doorIds) {
        return 0;
    }

    @Override
    public void createMusic() throws Exception {

    }

    @Override
    public void toggleMusic() {

    }

    @Override
    public void showTrapRemovalMessage() {

    }

    @Override
    public TrapEvasionMovementEnum showFallingRockMovementOptions() {
        return null;
    }

    @Override
    public TrapEvasionMovementEnum showPitJumpingOptions() {
        return null;
    }

    @Override
    public ArrayList<String> readSaveFile(String localPlayerName) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean checkSaveFileExists(String localPlayerName) {
        return false;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(String s) {

    }

    @Override
    public void writeSaveFile(String localPlayerName, int heroType, int gold, ArrayList<ItemEnum> items) throws IOException {

    }
}

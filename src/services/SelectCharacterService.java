package services;

import entities.Adventurer;
import entities.Zargon;
import entities.actions.SelectCharacter;
import entities.players.*;
import entities.utils.Strings;
import enums.CharacterEnum;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SelectCharacterService {

    private final HeroQuest heroQuest;

    public SelectCharacterService(HeroQuest heroQuest) {
        this.heroQuest = heroQuest;
    }

    public void selectCharacter() throws ClassNotFoundException {
        boolean doesSaveFileWithPlayerNameExist = heroQuest.checkSaveFileExists();
        if (doesSaveFileWithPlayerNameExist) {
            int choice = JOptionPane.showConfirmDialog(null, Strings.CONFIRM_LOAD_FILE);
            if (choice == JOptionPane.YES_OPTION) {
                ArrayList<String> fileInformation = null;
                try {
                    fileInformation = heroQuest.readSaveFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                processCharacterSelection(Integer.parseInt(Objects.requireNonNull(fileInformation).get(0)));
                heroQuest.setLocalAdventurerStartingGold(Integer.parseInt(fileInformation.get(1)));
            } else {
                heroQuest.showCharacterSelectionScreen();
            }
        } else {
            heroQuest.showCharacterSelectionScreen();
        }
    }

    public void processCharacterSelection(int selectedCharacterId) throws ClassNotFoundException {
        boolean isCharacterAvailable;
        Zargon playerZargon = new Zargon(heroQuest);
        Adventurer playerAdventurer = new Adventurer();
        PlayableCharacter character;
        SelectCharacter action = new SelectCharacter();
        action.setSelectedCharacterId((byte)selectedCharacterId);
        switch (CharacterEnum.getEnumById(selectedCharacterId)) {
            case ZARGON:
                isCharacterAvailable = heroQuest.getZargonAvailable();
                break;
            case BARBARIAN:
                isCharacterAvailable = heroQuest.isBarbarianAvailable();
                break;
            case WIZARD:
                isCharacterAvailable = heroQuest.isWizardAvailable();
                break;
            case ELF:
                isCharacterAvailable = heroQuest.isElfAvailable();
                break;
            case DWARF:
                isCharacterAvailable = heroQuest.isDwarfAvailable();
                break;
            default:
                heroQuest.reportError(Strings.CHARACTER_SELECTION_ERROR.toString());
                isCharacterAvailable = false;
                break;
        }
        if (isCharacterAvailable) {
            switch (CharacterEnum.getEnumById(selectedCharacterId)) {
                case ZARGON:
                    heroQuest.setLocalZargon(playerZargon);
                    action.setZargon(playerZargon);
                    break;
                case BARBARIAN:
                    character = new Barbarian();
                    character.setID((byte) (heroQuest.getCreatureQueueSize()-3));
                    playerAdventurer.setPlayableCharacter(character);
                    heroQuest.setLocalAdventurer(playerAdventurer);
                    action.setAdventurer(playerAdventurer);
                    break;
                case WIZARD:
                    character = new Wizard();
                    character.setID((byte) (heroQuest.getCreatureQueueSize()-2));
                    playerAdventurer.setPlayableCharacter(character);
                    heroQuest.setLocalAdventurer(playerAdventurer);
                    action.setAdventurer(playerAdventurer);
                    break;
                case ELF:
                    character = new Elf();
                    character.setID((byte) (heroQuest.getCreatureQueueSize()-1));
                    playerAdventurer.setPlayableCharacter(character);
                    heroQuest.setLocalAdventurer(playerAdventurer);
                    action.setAdventurer(playerAdventurer);
                    break;
                case DWARF:
                    character = new Dwarf();
                    character.setID((byte) (heroQuest.getCreatureQueueSize()));
                    playerAdventurer.setPlayableCharacter(character);
                    heroQuest.setLocalAdventurer(playerAdventurer);
                    action.setAdventurer(playerAdventurer);
                    break;
                default:
                    heroQuest.reportError(Strings.CHARACTER_SELECTION_ERROR.toString());
                    break;
            }
            heroQuest.processAction(action);
            heroQuest.sendAction(action);
        } else {
            heroQuest.reportError(Strings.CHARACTER_UNAVAILABLE.toString());
            selectCharacter();
        }
    }

    public void processSelectCharacter(SelectCharacter action) {
        PlayableCharacter character;
        Adventurer playerAdventurer;
        byte[] position;
        byte selectedCharacterId = action.getSelectedCharacterId();
        switch (CharacterEnum.getEnumById(selectedCharacterId)) {
            case ZARGON:
                heroQuest.setZargonUnavailable();
                Zargon playerZ = action.getZargon();
                for (int i = 0; i < playerZ.getMonsters().size(); i++) {
                    heroQuest.insertCreatureIntoQueue(playerZ.getMonster(i));
                }
                heroQuest.removePlayerFromQueue();
                heroQuest.insertPlayerIntoQueue(playerZ);
                break;
            case BARBARIAN:
                heroQuest.setBarbarianUnavailable();
                playerAdventurer = action.getAdventurer();
                heroQuest.removePlayerFromQueue();
                heroQuest.insertPlayerIntoQueue(playerAdventurer);
                character = playerAdventurer.getPlayableCharacter();
                heroQuest.insertCreatureIntoQueue(character);
                position = heroQuest.getBarbarianInitialPosition();
                heroQuest.setCreatureInPosition(character, position[0], position[1]);
                character.setMovement();
                heroQuest.setAreaVisible(position[0], position[1]);
                break;
            case WIZARD:
                heroQuest.setWizardUnavailable();
                playerAdventurer = action.getAdventurer();
                heroQuest.removePlayerFromQueue();
                heroQuest.insertPlayerIntoQueue(playerAdventurer);
                character = playerAdventurer.getPlayableCharacter();
                ((Wizard) character).createSpells();
                heroQuest.insertCreatureIntoQueue(character);
                position = heroQuest.getWizardInitialPosition();
                heroQuest.setCreatureInPosition(character, position[0], position[1]);
                character.setMovement();
                heroQuest.setAreaVisible(position[0], position[1]);
                break;
            case ELF:
                heroQuest.setElfUnavailable();
                playerAdventurer = action.getAdventurer();
                heroQuest.removePlayerFromQueue();
                heroQuest.insertPlayerIntoQueue(playerAdventurer);
                character = playerAdventurer.getPlayableCharacter();
                ((Elf) character).createSpells();
                heroQuest.insertCreatureIntoQueue(character);
                position = heroQuest.getElfInitialPosition();
                heroQuest.setCreatureInPosition(character, position[0], position[1]);
                character.setMovement();
                heroQuest.setAreaVisible(position[0], position[1]);
                break;
            case DWARF:
                heroQuest.setDwarfUnavailable();
                playerAdventurer = action.getAdventurer();
                heroQuest.removePlayerFromQueue();
                heroQuest.insertPlayerIntoQueue(playerAdventurer);
                character = playerAdventurer.getPlayableCharacter();
                heroQuest.insertCreatureIntoQueue(character);
                position = heroQuest.getDwarfInitialPosition();
                heroQuest.setCreatureInPosition(character, position[0], position[1]);
                character.setMovement();
                heroQuest.setAreaVisible(position[0], position[1]);
                break;
            default:
                heroQuest.reportError(Strings.CHARACTER_SELECTION_ERROR.toString());
                break;
        }
        heroQuest.sortCreatureQueueByIdReversed();
        heroQuest.showVisibleCreaturesInQueue();
    }
}

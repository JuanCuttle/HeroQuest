package entities.players;

import entities.HeroQuest;
import enums.ItemEnum;
import org.junit.Before;
import org.junit.Test;
import quests.BasicMap;
import quests.TheTrial;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class PlayableCharacterTest {

    private PlayableCharacter playableCharacter;

    @Before
    public void before() {
        playableCharacter = new PlayableCharacter(1, 1, 1, 1);
    }

    @Test
    public void shouldReturnPlayersItemsWhenQuestIsNotLegacyOfTheOrcWarlord() {
        playableCharacter.addItemToBag(ItemEnum.Spear);
        BasicMap quest = new TheTrial(new HeroQuest());

        assertEquals(Arrays.asList(ItemEnum.Spear), playableCharacter.getItems(quest));
    }
}

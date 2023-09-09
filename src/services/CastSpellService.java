package services;

import entities.Creature;
import entities.Position;
import entities.Spell;
import entities.actions.CastSpell;
import entities.enemies.Mummy;
import entities.enemies.Skeleton;
import entities.enemies.Zombie;
import entities.players.Elf;
import entities.players.Wizard;
import entities.utils.Strings;
import enums.SpellNameEnum;
import enums.StatusEnum;

import java.util.ArrayList;

public class CastSpellService {

    private final HeroQuest heroQuest;

    public CastSpellService(HeroQuest heroQuest) {
        this.heroQuest = heroQuest;
    }

    public String castSpell() {
        if (heroQuest.verifyIfItIsCurrentPlayersTurn()) {
            Creature caster = heroQuest.getCurrentCreature();
            if (caster instanceof Wizard) {
                ArrayList<Spell> availableSpells = ((Wizard) caster)
                        .getSpells();
                byte availableMind = caster.getMind();
                if (availableMind > 0) {
                    Spell selectedSpell = heroQuest.selectSpell(availableSpells);
                    Position casterCurrentPosition = caster.getCurrentPosition();
                    ArrayList<Creature> availableTargets = heroQuest.getAvailableTargets((byte) 2, casterCurrentPosition);
                    Creature selectedTarget = heroQuest.selectTarget(availableTargets);
                    boolean wasSpellSuccessful = calculateSpellSuccess(selectedTarget, selectedSpell);
                    if (wasSpellSuccessful) {
                        CastSpell action = new CastSpell();
                        if (StatusEnum.SLEEPING.equals(selectedSpell.getStatus())) {
                            byte roundsToSleep = 0;
                            byte dieRoll = 0;
                            byte targetMind = selectedTarget.getMind();
                            while(dieRoll != 5) {
                                for (byte i = 0; i < targetMind; i++) {
                                    roundsToSleep++;
                                    dieRoll = (byte)(Math.random()*6);
                                    if (dieRoll == 5) {
                                        break;
                                    }
                                }
                            }
                            action.setRoundsToSleep(roundsToSleep);
                        }
                        action.setSpell(selectedSpell);
                        action.setTargetID(selectedTarget.getID());
                        heroQuest.processAction(action);
                        heroQuest.sendAction(action);
                    }
                } else {
                    return Strings.NO_MIND_LEFT.toString();
                }
            } else if (caster instanceof Elf) {
                ArrayList<Spell> availableSpells = ((Elf) caster).getSpells();
                byte mind = caster.getMind();
                if (mind > 0) {
                    Spell selectedSpell = heroQuest.selectSpell(availableSpells);
                    Position casterPosition = caster.getCurrentPosition();
                    ArrayList<Creature> availableTargets = heroQuest.getAvailableTargets((byte) 2, casterPosition);
                    Creature selectedTarget = heroQuest.selectTarget(availableTargets);
                    boolean wasSpellSuccessful = calculateSpellSuccess(selectedTarget, selectedSpell);
                    if (wasSpellSuccessful) {
                        CastSpell action = new CastSpell();
                        action.setSpell(selectedSpell);
                        action.setTargetID(selectedTarget.getID());
                        heroQuest.processAction(action);
                        heroQuest.sendAction(action);
                    }
                } else {
                    return Strings.NO_MIND_LEFT.toString();
                }
            } else {
                return Strings.CANT_USE_SPELLS.toString();
            }
        } else {
            return Strings.NOT_YOUR_TURN.toString();
        }
        return null;
    }

    public boolean calculateSpellSuccess(Creature target, Spell spell) {
        byte spellDamage;
        boolean success = true;
        spellDamage = spell.getDamage();

        if (spell.getSpellId() == SpellNameEnum.BALL_OF_FLAME.getId()) {
            byte valueOnFirstDie = (byte)(Math.random() * 6 + 1);
            byte valueOnSecondDie = (byte)(Math.random() * 6 + 1);

            if (valueOnFirstDie > 4) {
                spellDamage++;
            }

            if (valueOnSecondDie > 4) {
                spellDamage++;
            }

            spell.setDamage(spellDamage);

            if (spellDamage == 0) {
                success = false;
                heroQuest.showMessagePopup(Strings.SPELL_FAILED.toString());
            }
        }

        if (spell.getSpellId() == SpellNameEnum.FIRE_OF_WRATH.getId()) {
            byte valueOnDie = (byte)(Math.random() * 6 + 1);

            if (valueOnDie > 4) {
                spellDamage++;
            }

            spell.setDamage(spellDamage);

            if (spellDamage == 0) {
                success = false;
                heroQuest.showMessagePopup(Strings.SPELL_FAILED.toString());
            }
        }

        if (spell.getSpellId() == SpellNameEnum.SLEEP.getId()) {

            if (isTargetUndead(target)) {
                success = false;
            } else {
                byte targetMind = target.getMind();
                byte valueOnDie;
                for (byte i = 0; i < targetMind; i++) {
                    valueOnDie = (byte)(Math.random() * 6 + 1);
                    if (valueOnDie == 6) {
                        success = false;
                        heroQuest.showMessagePopup(Strings.SPELL_FAILED.toString());
                        break;
                    }
                }
            }
        }
        return success;
    }

    public static boolean isTargetUndead(Creature target) {
        return target instanceof Zombie || target instanceof Mummy || target instanceof Skeleton;
    }

    public void processCastSpell(CastSpell action) {
        byte roundsToSleep = action.getRoundsToSleep();
        Spell castSpell = action.getSpell();
        byte targetId = action.getTargetID();
        Creature target = heroQuest.getCreatureById(targetId);
        byte damageDealt = castSpell.getDamage();
        StatusEnum spellStatusEffect = castSpell.getStatus();
        if (spellStatusEffect != null) {
            target.setStatus(spellStatusEffect);
        }
        if (roundsToSleep > 0) {
            target.setRoundsToSleep(roundsToSleep);
        }
        Creature caster = heroQuest.getCurrentCreature();
        caster.spendSpell(castSpell);
        heroQuest.showEffectOfCastSpell(caster, castSpell, target, damageDealt, spellStatusEffect);
        target.increaseBody(damageDealt);
        byte targetBP = target.getBody();
        if (targetBP <= 0) {
            heroQuest.announceCreatureDeath(target);
            heroQuest.killCreature(targetId);
        }
    }

}

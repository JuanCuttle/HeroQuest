package modelo;
/*
	Sleeping: The creature cannot move, attack or defend itself. Every turn, the creature can roll a die for each of its Mind Points
				If a 6 is rolled the spell is broken. Cannot be used against Mummies, Zombies or Skeletons
				(to make it work in multiplayer with random rolls in each player, the "turns" are calculated as soon as the creature is hit
				by the spell; The number of turns asleep is then defined and set to the creature).
	Cursed: Tempest spell effect. Causes the target to miss its next turn.
	Agility_up: Doubles the target's next movement die roll.
	Agility_down: Halves the target's next movement die roll.
	Neutral: Normal state, no buffs or debuffs.
	Rock_skin: The creature may throw an extra combat die when defending. the spell is broken when the creature suffers damage.
	Courage: The creature may throw 2 extra combat dice on its next attack. The spell is broken when it can no longer see an enemy.
	Dead: The creature is dead.
	
*/
public enum Status {
	SLEEPING, CURSED, AGILITY_UP, NEUTRAL, ROCK_SKIN, COURAGE, DEAD, AGILITY_DOWN;
	
}
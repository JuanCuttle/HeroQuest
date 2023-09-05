package entities.utils;

import view.GUI;
import enums.LanguageEnum;

public enum Strings {
	 HEROQUEST {
	      public String toString() {
	          return "HeroQuestv3.0(alpha)";
	      }
	  },
	  
	  DOOR_OUT_OF_RANGE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "N�o est� perto da porta.";
	    	  	default: return "You are too far from the door.";
	    	  }
	      }
	  },

	  NOT_YOUR_TURN {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "N�o � o jogador da vez.";
	    	  	default: return "It's not your turn.";
	    	  }
	      }
	  },
	  
	  YOURTURN {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "� sua vez.";
	    	  	default: return "It's your turn.";
	    	  }
	      }
	  },
	  
	  CANT_OPEN_DOOR {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Monstros n�o s�o capazes de abrir portas!";
	    	  	default: return "Monsters cannot open doors!";
	    	  }
	      }
	  },
	  
	  SLEEP_FREEZE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Voc� est� dormindo, n�o pode se mover!";
	    	  	default: return "You are fast asleep, and cannot move!";
	    	  }
	      }
	  },
	  
	  PHYSICS_LAWS {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Respeite as leis da f�sica!";
	    	  	default: return "Show some respect for the laws of Physics!";
	    	  }
	      }
	  },
	  
	  NO_MOVE_LEFT {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Voc� n�o tem movimento suficiente nesta rodada.";
	    	  	default: return "You have no movement left!";
	    	  }
	      }
	  },
	  
	  SLEEP_NO_ATTACK {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Voc� est� dormindo, n�o pode atacar!";
	    	  	default: return "You are fast asleep, and cannot attack!";
	    	  }
	      }
	  },
	  
	  TARGET_OUT_OF_RANGE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "N�o � poss�vel atacar um alvo t�o distante.";
	    	  	default: return "You cannot reach your target.";
	    	  }
	      }
	  },
	  
	  NOMIND {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Mind insuficiente.";
	    	  	default: return "Insufficient Mind points.";
	    	  }
	      }
	  },
	  
	  DOESNTUSESPELLS {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Seu personagem n�o usa magia.";
	    	  	default: return "Your character cannot use spells.";
	    	  }
	      }
	  },
	  
	  SPELL_FAILED {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "O encantamento parece perigoso, mas o conjurador se desconcentra e o feiti�o se desfaz...";
		    	default: return "The spell seems dangerous at first, but the caster loses his focus for a split-second and the spell vanishes from whence it came...";
	    	  }
	      }
	  },
	  
	  CHARACTER_SELECTION_ERROR {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Erro na sele��o de personagem. Tente novamente.";
	    	  	default: return "Error during character selection. Please try again.";
	    	  }
	      }
	  },
	  
	  THEPLAYER {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "O jogador ";
	    	  	default: return "The player ";
	    	  }
	      }
	  },
	  
	  FOUNDGOLD {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " encontrou algumas moedas de ouro.";
	    	  	default: return " found some gold coins.";
	    	  }
	      }
	  },
	  
	  FOUNDITEM {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " encontrou o item: ";
	    	  	default: return " has found the item: ";
	    	  }
	      }
	  },
	  
	  THECREATURE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "A criatura ";
	    	  	default: return "The creature ";
	    	  }
	      }
	  },
	  
	  WOKEUP {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " acordou!";
	    	  	default: return " woke up!";
	    	  }
	      }
	  },
	  
	  MONSTER_CANT_UNDERSTAND_COMMAND {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "O monstro n�o � capaz de entender esse comando.";
	    	  	default: return "The monster cannot understand this command.";
	    	  }
	      }
	  },
	  
	  CHARACTER_UNAVAILABLE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Personagem n�o dispon�vel";
	    	  	default: return "The selected character is unavailable";
	    	  }
	      }
	  },
	  
	  END_GAME {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "O jogo terminou, deseja fech�-lo?";
	    	  	default: return "The game has ended, do you wish to exit?";
	    	  }
	      }
	  },
	  
	  ZARGON_DOES_NOT_CARRY_GOLD {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Zargon n�o carrega gold.";
	    	  	default: return "Zargon does not carry gold.";
	    	  }
	      }
	  },
	  
	  UNKNOWN {
	      public String toString() {
	          return "???????";
	      }
	  },
	  
	  SERVER {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return ", Conectado ao servidor: ";
	    	  	default: return ", Connected to server: ";
	    	  }
	      }
	  },
	  
	  COMMAPLAYER {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return ", Jogador: ";
	    	  	default: return ", Player: ";
	    	  }
	      }
	  },
	  
	  INSTRUCTIONS {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Instru\u00E7\u00F5es";
	    	  	default: return "Instructions";
	    	  }
	      }
	  },
	  
	  MENU {
	      public String toString() {
	          return "Menu";
	      }
	  },
	  
	  SELECTCHAR {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Selecionar personagem";
	    	  	default: return "Character selection";
	    	  }
	      }
	  },
	  
	  SETTINGS {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Configura��es";
	    	  	default: return "Settings";
	    	  }
	      }
	  },
	  
	  TRIGGERMUSIC {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Iniciar/pausar a m�sica";
	    	  	default: return "Turn music on/off";
	    	  }
	      }
	  },
	  
	  LANGUAGEBUTTON {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "L�ngua";
	    	  	default: return "Language";
	    	  }
	      }
	  },
	  
	  SELECT_SPELL {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Digite o n�mero correspondente � magia que deseja usar: ";
	    	  	default: return "Type in the number corresponding to the spell you wish to use: ";
	    	  }
	      }
	  },
	  
	  SELECT_TARGET {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Digite o n�mero correspondente ao alvo escolhido: ";
	    	  	default: return "Type in the number corresponding to the desired target: ";
	    	  }
	      }
	  },
	  
	  INPUT_NAME {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Digite o nome do seu personagem: ";
	    	  	default: return "Type in the your character's name: ";
	    	  }
	      }
	  },
	  
	  OBTAIN_SERVER_ADDRESS {
	      public String toString() {
	    	  // String idServidor = ("venus.inf.ufsc.br");
	  		  // String idServidor = ("127.0.0.1");
	  		  String idServidor = ("localhost");
	  		  // String idServidor = ("web.juan.cuttle.vms.ufsc.br");
	  		  //String idServidor = ("netgames.labsoft.ufsc.br");
	          return idServidor;
	      }
	  },
	  
	  INPUTSERVERADDRESS {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Insira o endere�o do servidor";
	    	  	default: return "Type in the server's address";
	    	  }
	      }
	  },
	  
	  SUCCESSFULCONNECT {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Conexao com exito";
	    	  	default: return "Successful connection";
	    	  }
	      }
	  },
	  
	  ALREADYCONNECTED {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Tentativa de conexao com conexao previamente estabelecida";
	    	  	default: return "Conection attempt with previously established connection";
	    	  }
	      }
	  },
	  
	  FAILEDCONNECT {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Tentativa de conexao falhou";
	    	  	default: return "Failed to establish connection";
	    	  }
	      }
	  },
	  
	  SUCCESSFULDISCONNECT {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Desconexao com exito";
	    	  	default: return "Successfully disconnected";
	    	  }
	      }
	  },
	  
	  DISCBEFORECONNECT {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Tentativa de desconexao sem conexao previamente estabelecida";
		    	default: return "Attempt to disconnect without previous connection";
	    	  }
	      }
	  },
	  
	  FAILEDDISCONNECT {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Tentativa de desconexao falhou";
	    	  	default: return "Failed disconnection attempt";
	    	  }
	      }
	  },
	  
	  SUCCESSFULSTART {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Solicita��o de inicio procedida com exito";
	    	  	default: return "Successful starting request";
	    	  }
	      }
	  },
	  
	  STARTBEFORECONNECT {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Tentativa de inicio sem conexao previamente estabelecida";
	    	  	default: return "Attempt at starting without previous connection";
	    	  }
	      }
	  },
	  
	  UNINTERRUPTEDGAME {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Partida corrente nao interrompida";
	    	  	default: return "Current game not interrupted";
	    	  }
	      }
	  },
	  
	  HEROES_WON {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Parab�ns aos heróis!\n Voc�s foram vitoriosos!!!";
		    	default: return "Congratulations to the heroes!\n You were victorious!!!";
	    	  }
	      }
	  },
	  
	  ZARGON_WON {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Oh n�o, o terr�vel Zargon venceu desta vez!";
	    	  	default: return "Oh no, the evil Zargon has won!";
	    	  }
	      }
	  },
	  
	  YOU_HAVE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Voc� possui ";
	    	  	default: return "You have ";
	    	  }
	      }
	  },
	  
	  COINS_IN_INVENTORY {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " moedas de ouro no seu invent�rio.";
	    	  	default: return " gold coins in your inventory.";
	    	  }
	      }
	  },
	  
	  CURRENTBP {
	      public String toString() {
	    	  switch(GUI.language){
		    	  case Portugues: return "Body atual: ";
		    	  default: return "Current Body: ";
	    	  }
	      }
	  },
	  
	  CURRENTMP {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "\nMind restante: ";
	    	  	default: return "\nCurrent Mind: ";
	    	  }
	      }
	  },
	  
	  REMAININGMOVES {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "\nMovimento restante: ";
	    	  	default: return "\nRemaining Movement: ";
	    	  }
	      }
	  },
	  
	  CURRENTSTATUS {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "\nStatus atual: ";
	    	  	default: return "\nCurrent Status: ";
	    	  }
	      }
	  },
	  
	  LINE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "\nLinha: " ;
	    	  	default: return "\nLine: ";
	    	  }
	      }
	  },
	  
	  COLUMN {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " Coluna: ";
	    	  	default: return " Column: ";
	    	  }
	      }
	  },
	  
	  TTW {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "\nTurnos at� acordar: ";
	    	  	default: return "\nTurns left to wake: ";
	    	  }
	      }
	  },
	  
	  NUMBEROFPLAYERS {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Favor digitar a quantidade de jogadores que ir�o participar desta partida: ";
	    	  	default: return "Please type in the (total) number of players who will participate in this game: ";
	    	  }
	      }
	  },
	  
	  OHNO {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Oh n�o! ";
	    	  	default: return "Oh no! ";
	    	  }
	      }
	  },
	  
	  ACTIVATEDTRAP {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " ativou uma armadilha e perdeu ";
	    	  	default: return " has activated a trap and lost ";
	    	  }
	      }
	  },
	  
	  OFBP {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " de Body points!";
	    	  	default: return " Body points!";
	    	  }
	      }
	  },
	  
	  RECEIVED {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " recebeu ";
	    	  	default: return " received ";
	    	  }
	      }
	  },
	  
	  OFDAMAGE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " de dano.";
	    	  	default: return " of damage.";
	    	  }
	      }
	  },
	  
	  ATTEMPTSSEPPUKU {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " tenta seppuku e recebe ";
	    	  	default: return " attempts seppuku and inflicts ";
	    	  }
	      }
	  },
	  
	  DIEDHONORABLY {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " morreu honrosamente em batalha.";
	    	  	default: return " has died honorably in battle.";
	    	  }
	      }
	  },
	  
	  THE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "O ";
	    	  	default: return "The ";
	    	  }
	      }
	  },
	  
	  MURMURED_SPELL {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " murmurou algumas palavras m�gicas (";
	    	  	default: return " murmured a few magical words (";
	    	  }
	      }
	  },
	  
	  AND_THE_CREATURE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "), e a criatura ";
	    	  	default: return "), and the creature ";
	    	  }
	      }
	  },
	  
	  MODIFIED_IN {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " modificou em ";
	    	  	default: return " modified in ";
	    	  }
	      }
	  },
	  
	  BP_MODIFIED_STATUS {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " seus Body points, e conferiu a ele o estado ";
	    	  	default : return " its Body points, and conferred to it the status ";
	    	  }
	      }
	  },
	  
	  BP_MODIFIED_NOT_STATUS {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " seus Body points!";
	    	  	default: return " its Body points!";
	    	  }
	      }
	  },
	  
	  EXCLAMATION_MARK {
	      public String toString() {
	          return "!";
	      }
	  },
	  
	  DIEDONTRAP {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " morreu ao ativar uma armadilha!";
	    	  	default: return " died as it triggered a trap!";
	    	  }
	      }
	  },
	  
	  CREATURESTURN {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "A vez � da criatura ";
	    	  	default: return "The turn is of creature ";
	    	  }
	      }
	  },
	  
	  ONLINE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " a qual est� na linha ";
	    	  	default: return " which is on line ";
	    	  }
	      }
	  },
	  
	  COMMACOLUMN {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return ", coluna ";
	    	  	default: return ", column ";
	    	  }
	      }
	  },
	  
	  OFGAMEBOARD {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return " do tabuleiro.";
	    	  	default: return " of the game board.";
	    	  }
	      }
	  },
	  
	  SELECT_DOOR {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Escolha a porta a ser aberta/fechada: \n";
	    	  	default: return "Choose the door to be opened/closed: \n";
	    	  }
	      }
	  },
	  
	  DWARF_DISARMED_TRAPS {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "O An�o desarmou as armadilhas pr�ximas a ele!";
	    	  	default: return "The Dwarf has disarmed the traps close to him!";
	    	  }
	      }
	  },
	  
	  DISARMED_TREASURE_TRAP {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "O hero desarmou uma armadilha em um tesouro!";
	    	  } else {
	    		  return "The hero has disarmed a trap in a treasure chest!";
	    	  }
	      }
	  },
	  
	  THRONE_PASSAGE_REVEALED {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "O trono se moveu para revelar uma passagem secreta!";
	    	  } else {
	    		  return "The throne moved to reveal a hidden passage!";
	    	  }
	      }
	  },
	  
	  ROCKFALL {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Oh n�o, voc� est� sendo soterrado! Escolha se deseja ir para frente ou para tr�s:\n";
	    	  	default: return "Oh no, you are being buried by rocks! Decide if you want to move forwards or backwards:\n";
	    	  }
	      }
	  },
	  
	  FORWARD {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "para frente\n";
	    	  	default: return "forwards\n";
	    	  }
	      }
	  },
	  
	  BACKWARD {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "para tr�s";
	    	  	default: return "backwards";
	    	  }
	      }
	  },
	  
	  PIT_JUMP {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Voc� deseja pular o buraco?\n";
	    	  	default: return "Do you wish to jump over the pit?\n";
	    	  }
	      }
	  },
	  
	  YES {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "sim\n";
	    	  	default: return "yes\n";
	    	  }
	      }
	  },
	  
	  NO {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "n�o";
	    	  	default: return "no";
	    	  }
	      }
	  },
	  
	  ERRORABORT {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Ocorreu um erro e o jogo teve de ser abortado";
	    	  	default: return "An error has ocurred and the game had to be aborted";
	    	  }
	      }
	  },
	  
	  SELECTYOURCHAR {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Selecione seu personagem";
	    	  	default: return "Select your character";
	    	  }
	      }
	  },
	  
	  TYPECHARNUMBER {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Digite o n\u00FAmero ao lado do escolhido:";
	    	  	default: return "Type in the number beside the chosen character";
	    	  }
	      }
	  },
	  
	  ZARGONDESCRIPTION {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Zargon. Voc� controla os monstros na masmorra. Seu objetivo � matar os herois."; 
	    	  	default: return "Zargon. You control the monsters in the dungeon. Your task is to kill the heroes.";
	    	  }
	      }
	  },
	  
	  ZARGON {
	      public String toString() {
	          return "Zargon";
	      }
	  },
	  
	  BARBARIAN {
	      public String toString() {
			  switch(GUI.language){
				  case Portugues: return "Bárbaro";
				  default : return "Barbarian";
			  }
	      }
	  },
	  
	  WIZARD {
	      public String toString() {
			  switch(GUI.language){
				  case Portugues: return "Mago";
				  default : return "Wizard";
			  }
	      }
	  },
	  
	  ELF {
	      public String toString() {
			  switch(GUI.language){
				  case Portugues: return "Elfo";
				  default : return "Elf";
			  }
	      }
	  },
	  
	  DWARF {
	      public String toString() {
			  switch(GUI.language){
				  case Portugues: return "Anão";
				  default : return "Dwarf";
			  }
	      }
	  },
	  
	  NORTH {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Norte";
	    	  	default : return "North";
	    	  }
	      }
	  },
	  
	  SOUTH {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Sul";
	    	  	default: return "South";
	    	  }
	      }
	  },
	  
	  EAST {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Leste";
	    	  	default: return "East";
	    	  }
	      }
	  },
	  
	  WEST {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Oeste";
	    	  	default: return "West";
	    	  }
	      }
	  },
	  
	  LANGSELECT {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Selecione uma l�ngua";
	    	  	default: return "Select a language";
	    	  }
	      }
	  },
	  
	  SELECT {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Selecione";
	    	  	default: return "Select";
	    	  }
	      }
	  },
	  
	  INSTRUCTMANUAL {
	      public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "Manual de Instru��es";
	    	  } else {
	    		  return "Instruction Manual";
	    	  }
	      }
	  },
	  
	  GUIDE {
	      public String toString() {
	    	  switch(GUI.language){
	    	  	case Portugues: return "Voc� est� em uma masmorra. Est� frio, �mido e totalmente escuro. "
	    	  			+ "Os �nicos sons s�o de seus pr�prios batimentos card�acos, "
	    	  			+ "respira��o e ecos de �gua gotejando pelos corredores. "
	    	  			+ "N�o h� luz, nem ao longe, "
	    	  			+ "exceto pelas tochas que voc� e seus aliados carregam. "
	    	  			+ "Seu objetivo � alcan�ar a escada e encerrar seu turno para escapar "
	    	  			+ "o terr�vel destino que espera a todos que se perdem nas masmorras de Zargon. "
	    	  			+ "Eu mencionei Zargon? Ele � um feiticeiro que se especializa em magia negra. "
	    	  			+ "Ele controla monstros com ela, "
	    	  			+ "e se regozija ao destruir todos que se atrevem a entrar em seu covil. "
	    	  			+ "Lute em seu caminho e sobreviva, ou morra tentando. "
	    	  			+ "Boa sorte, e que os Deuses tenham piedade de sua alma."
	    	  			+ "\r\n\rUse as setas em seu teclado para se mover."
	    	  			+ "\r\nClique nos bot�es com seu mouse para selecionar uma a��o. "
	    	  			+ "Alternativamente, veja \"Teclado\" abaixo."
	    	  			+ "\r\nAbra/feche portas se posicionando ao lado delas "
	    	  			+ "e clicando nelas diretamente no mapa, "
	    	  			+ "ou escolhendo atrav�s do menu pelo Teclado."
	    	  			+ "\r\nTeclado: 1, 2 e 3 para conectar, "
	    	  			+ "desconectar e iniciar partida, respectivamente. "
	    	  			+ "A para atacar, S para usar magia, R para procurar por armadilhas, "
	    	  			+ "T para procurar por tesouro, O para abrir portas, "
	    	  			+ "P para selecionar personagem, I para abrir o invent�rio, "
	    	  			+ "E para encerrar seu turno e M para tocar/pausar a m�sica."
	    	  			+ "\r\n*Para come�ar a jogar, conecte se j� n�o estiver conectado. "
	    	  			+ "Ent�o, UM jogador inicia a partida "
	    	  			+ "e digita o n�mero de jogadores que ir�o participar. "
	    	  			+ "Ent�o todos selecionam seus personagens e come�am a jogar!"
	    	  			+ "\nPS: Esta vers�o cont�m um prot�tipo de saves de jogador. Para carregar um save, "
		          		+ "utilize o mesmo nome que voc� utilizou no seu �ltimo jogo "
		          		+ "ao conectar, e escolha carregar o save ao selecionar personagem.";
	    	  	default: return "You are in a dungeon. It is cold, damp and pitch-black. "
	          		+ "The only sounds are your own heartbeat, "
	          		+ "breathing and dripping water echoing through the passageways. "
	          		+ "There are no lights, even in the distance, "
	          		+ "except for the torches you and your allies carry. "
	          		+ "Your objective is to reach the ladder and end your turn to escape "
	          		+ "the terrible fate that awaits all who get lost in Zargon's halls. "
	          		+ "Have I mentioned Zargon? He is a sorcerer who specializes in dark magic. "
	          		+ "He controls monsters with it, "
	          		+ "and revels in destroying all who dare enter his lair. "
	          		+ "Fight your way through and survive, or die trying. "
	          		+ "Good luck, and may the Gods have mercy on your brave soul."
	          		+ "\r\n\r\nUse the arrows on your keyboard to move."
	          		+ "\r\nClick on the buttons with your mouse to pick an action. "
	          		+ "Alternatively, see Keyboard below."
	          		+ "\r\nOpen/close doors by standing next to them "
	          		+ "and clicking on them directly in the map, "
	          		+ "or choosing which number door you wish on the menu from Keyboard."
	          		+ "\r\nKeyboard: 1, 2 and 3 to connect, "
	          		+ "disconnect and start game, respectively. "
	          		+ "A to attack, S to use spells, R to look for traps, "
	          		+ "T to search for treasure, O to open doors, "
	          		+ "P to select character, I to open inventory, "
	          		+ "E to end your turn and M to play/pause music."
	          		+ "\r\n\r\n*To start playing, connect if not connected already. "
	          		+ "Then, one player presses \"start game\" "
	          		+ "and types the number of players in that game. "
	          		+ "Then everyone selects their characters and start playing!"
	          		+ "\nPS: This version has a prototype of player saves. To load a save file, "
	          		+ "use the same name you used in your previous playthrough "
	          		+ "as you connect and choose to load while selecting character";
	    	  	}
	      }
	  },
	  
	  CONFIRM_LOAD_FILE {
	      public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "Um arquivo com o seu nome j� existe, deseja carreg�-lo?";
	    	  } else {
	    		  return "A file with your name already exists, do you wish to load it?";
	    	  }
	      }
	  },
	  
	  FILENOTFOUND {
	      public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "Arquivo com n�o encontrado, criando um novo...";
	    	  } else {
	    		  return "File not found, creating new file...";
	    	  }
	      }
	  },
	  
	  THETRIAL {
	      public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "Voc�s aprenderam bem, meus amigos. Agora chegou a hora do seu primeiro desafio."
	    		  		+ " Voc�s precisam primeiro entrar nas catacumbas que cont�m a tumba de Fellmarg."
	    		  		+ "\nVoc�s devem localizar e destruir Verag, uma g�rgula repugnante que se esconde nas catacumbas."
	    		  		+ " Esta miss�o n�o � f�cil e voc�s devem trabalhar em equipe se quiserem sobreviver."
	    		  		+ "\nEstes s�o seus primeiros passos nas suas jornadas para tornarem-se verdadeiros Herois."
	    		  		+ " Andem com cautela, meus amigos.";
	    	  } else {
	    		  return "You have learned well, my friends. Now has come the time of your first trial."
	    		  		+ " You must first enter the catacombs which contain Fellmarg's tomb."
	    		  		+ "\nYou must seek out and destroy Verag, a foul Gargoyle who hides in the catacombs."
	    		  		+ " This Quest is not easy and you must work together in order to survive."
	    		  		+ "\nThis is your first step on the road to becoming true Heroes."
	    		  		+ " Tread carefully my friends.";
	    	  }
	      }
	  },
	  
	  THERESCUEOFSIRRAGNAR {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "Sir Ragnar, um dos mais poderosos guerreiros do Imperados, foi raptado. "
	    		  		+ "\nEle est� prisioneiro de Ulag, o General dos Orcs. Voc� deve localizar "
	    		  		+ "Sir Ragnar e traz�-lo de volta � seguran�a, o Pr�ncipe Magnus ir� "
	    		  		+ "pagar 200 gold para o personagem que resgatar Sir Ragnar. \nA recompensa "
	    		  		+ "poder� ser dividida entre m�ltiplos aventureiros, por�m, nenhuma recompensa "
	    		  		+ "ser� paga se o Sir Ragnar for morto durante a fuga.";
	    	  } else {
	    		  return "Sir Ragnar, one of the Emperor's most powerful Knights, has been kidnapped. "
	    		  		+ "\nHe is being held prisoner by Ulag, the Orc Warlord. You are to find "
	    		  		+ "Sir Ragnar and bring him back to safety, Prince Magnus will pay 200 "
	    		  		+ "gold coins to the character who rescues Sir Ragnar. \nThe reward may "
	    		  		+ "be split between several adventurers, but no reward will be paid if "
	    		  		+ "Sir Ragnar is killed whilst escaping.";
	    	  }
	      }
	  },
	  
	  THEMAZE {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "Este ser� seu �ltimo teste antes de seguir adiante. \nAtente para usar as "
	    		  		+ "habilidades que foram ensinadas a voc�. Voc� deve tentar encontrar a sa�da. "
	    		  		+ "\n V�rios Monstros foram posicionados no Labirinto. Eles tentar�o impedi-lo, ent�o tenha cuidado. "
	    		  		+ "\nQuem encontrar a sa�da primeiro ser� recompensado em 100 moedas de ouro."
	    		  		+ "\nIsto poder� ser utilizado para comprar Equipamentos para as aventuras que est�o por vir.";
	    	  } else {
	    		  return "This will be your final test before you set forth. \nBe sure to use the skills "
	    		  		+ "that you have been taught. You must attempt to find your way out. \nSeveral Monsters "
	    		  		+ "have been placed in the Maze. They will try to stop you, so take care. \nWhoever finds his way out "
	    		  		+ "first will be rewarded with 100 gold coins. \nThis may be used to buy Equipment for the adventures to come.";
	    	  }
	      }
	  },
	  
	  LAIROFTHEORCWARLORD {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "O Pr�ncipe Magnus ordenou que o General dos Orcs, Ulag, que foi respons�vel "
	    		  		+ "\npelo sequestro do Sir Ragnar, deve ser localizado e eliminado. Aquele que "
	    		  		+ "\nmatar Ulag ser� recompensado em 100 moedas de ouro. Qualquer tesouro encontrado "
	    		  		+ "\nna fortaleza tamb�m poder� ser guardado.";
	    	  } else {
	    		  return "Prince Magnus has ordered that the Orc Warlord Ulag, who was responsible for "
	    		  		+ "\nthe kidnapping of Sir Ragnar, should be sought out and killed. Whoever kills "
	    		  		+ "\nUlag will be rewarded with 100 gold coins. Any treasure found in Ulag's "
	    		  		+ "\nstronghold may also be kept.";
	    	  }
	      }
	  },
	  
	  ITEMS_OWNED {
		  public String toString(){
			  if(GUI.language == LanguageEnum.Portugues){
				  return "\nVoc� tamb�m possui os seguintes itens: \n";
			  } else {
				  return "\nYou also own the following items: \n";
			  }
		  }
	  },
	  
	  PRINCEMAGNUSGOLD {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "Tr�s ba�s de tesouro foram roubados enquanto eram levados ao Imperador."
	    		  		+ "\nUma recompensa de 200 moedas de ouro foi oferecida a qualquer um que "
	    		  		+ "\npossa retornar o ba� com TODO o ouro. Os ladr�es s�o conhecidos como um "
	    		  		+ "\nbando de Orcs, cujo esconderijo encontra-se nas Montanhas Negras. "
	    		  		+ "\nEles s�o liderados por Gulthor, um Chaos Warrior.";
	    	  } else {
	    		  return "Three treasure chests have been stolen whilst being taken to the Emperor."
	    		  		+ "\nA reward of 200 gold coins has been offered to anyone who can return "
	    		  		+ "\nthe chest and ALL the gold. The thieves are known to be a band of Orcs "
	    		  		+ "\nhiding in the Black Mountains. They are led by Gulthor, a Chaos Warrior.";
	    	  }
	      }
	  },
	  
	  MELARSMAZE {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "H� muito tempo, um poderoso feiticeiro chamado Melar criou um Talism� o qual "
	    		  		+ "\npodia aumentar o conhecimento de magia do usu�rio. Ele guardava o Talism� "
	    		  		+ "\ncom ele em todos os momentos, temendo que ele poderia ser roubado e usado "
	    		  		+ "\npelos aliados de Zargon. Dizem que ele deixou o Talism� em seu Laborat�rio "
	    		  		+ "\nno cora��o do seu Labirinto. O Labirinto de Melar � defendido por muitas "
	    		  		+ "\narmadilhas e guardi�es m�gicos. Tamb�m h� rumores de que ele � assombrado "
	    		  		+ "\npor aqueles que buscaram o Talism� e morreram na tentativa...";
	    	  } else {
	    		  return "Long ago, a powerful sorcerer by the name of Melar created a Talisman which "
	    		  		+ "\ncould enhance the wearer's understanding of magic. He kept the Talisman "
	    		  		+ "\nwith him at all times, fearing it might be stolen and used by the allies "
	    		  		+ "\nof Zargon. It is said that he left the Talisman in his Laboratory at the "
	    		  		+ "\nheart of his Maze. Melar's Maze is guarded by many traps and magical guardians. "
	    		  		+ "\nIt is also rumored to be haunted by those who have sought the Talisman and "
	    		  		+ "\nperished in the attempt...";
	    	  }
	      }
	  },
	  
	  LEGACYOFTHEORCWARLORD {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "O horrendo filho de Ulag, Grak, jurou vingan�a contra aqueles que mataram seu pai."
	    		  		+ "\nApesar de ter levado v�rios meses, ele finalmente conseguiu localizar voc� "
	    		  		+ "e o capturou em uma emboscada. Agora voc� est� sendo mantido prisioneiro nas suas "
	    		  		+ "catacumbas enquanto ele quebra a cabe�a pensando numa terr�vel puni��o para voc�. "
	    		  		+ "\nEnquanto o guarda dorme, por�m, voc� conseguiu abrir o cadeado da sua cela com "
	    		  		+ "um velho osso de rato. Voc� deve encontrar seu equipamento e escapar.";
	    	  } else {
	    		  return "Ulag's foul offspring, Grak, has sworn revenge on those who killed his father."
	    		  		+ "\nAlthough it has taken him several months, he has finally tracked you down "
	    		  		+ "\nand captured you in an ambush. Now you are held prisoner in his dungeons "
	    		  		+ "\nwhile he racks his brains to devise a terrible punishment for you. While "
	    		  		+ "\nthe guard sleeps, however, you manage to pick the lock of your cell with "
	    		  		+ "\nan old rat bone. You must find your equipment and escape.";
	    	  }
	      }		  
	  },
	  
	  THESTONEHUNTER {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "O Mago pessoal do Imperador, Karlen, desapareceu. O Imperador teme que ou ele "
	    		  		+ "\nfoi assassinado ou sucumbiu �s tenta��es da magia do Chaos. Voc� deve descobrir "
	    		  		+ "\no que houve com Karlen e, caso ele esteja vivo, traz�-lo de volta � seguran�a."
	    		  		+ "\n Voc�s ser�o pagos 100 moedas de ouro cada ao retornarem.";
	    	  } else {
	    		  return "The Emperor's personal Wizard, Karlen has disappeared. The Emperor fears that "
	    		  		+ "\neither he has been murdered or has succumbed to the lures of Chaos magic. "
	    		  		+ "\nYou are to find out what has happened to Karlen and, if he is alive, bring "
	    		  		+ "\nhim to safety. You will be paid 100 gold coins each upon returning.";
	    	  }
	      }		
	  },
	  
	  FOUND_KARLEN {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "O Zumbi nesta sala � Karlne. Enquanto trabalhava em uma magia de Chaos ele foi consumido "
	    		  		+ "\npela magia que ele criou e foi transformado em um Zumbi lobotomizado.";
	    	  } else {
	    		  return "The Zombie in this room is Karlen. Whilst working on a Chaos spell he was consumed "
	    		  		+ "\nby the magic he created and was turned into a mindless Zombie.";
	    	  }
	      }		
	  },
	  
	  THEFIREMAGE {
		  public String toString() {
	    	  if(GUI.language == LanguageEnum.Portugues){
	    		  return "Os Orcs das Montanhas Negras t�m usado magias de Fogo em seus ataques. Balur, "
	    		  		+ "\no Mago do Fogo, � quem pensamos ser respons�vel por ajud�-los. Nenhuma magia "
	    		  		+ "\nde Fogo pode feri-lo, e os Magos do Imperador foram incapazes de combater "
	    		  		+ "\nseus feiti�os. Voc� foi ent�o escolhido para entrar em seu esconderijo, "
	    		  		+ "\nnas profundezas do Rochedo do Fogo Negro. O Imperador ir� recompens�-los com "
	    		  		+ "\n150 moedas de ouro cada, pela destrui��o de Balur.";
	    	  } else {
	    		  return "The Orcs of the Black Mountains have been using Fire magic in their raids. Balur, "
	    		  		+ "\nthe Fire Mage, is thought to be responsible for helping them. No Fire magic can "
	    		  		+ "\nharm him, and the Emperor's Wizards are unable to counter his spells. You have "
	    		  		+ "\ntherefore been chosen to enter his lair, deep beneath Black Fire Crag. The "
	    		  		+ "\nEmperor will reward you with 150 gold coins each for Balur's destruction.";
	    	  }
	      }		
	  }
}

package modelo;

import visao.AtorJogador;
import visao.Languages;

public enum Strings {
	 HEROQUEST {
	      public String toString() {
	          return "HeroQuestv2.8";
	      }
	  },
	  
	  DOOROUTOFRANGE {
	      public String toString() {
	          return "N�o est� perto da porta.";
	      }
	  },

	  NOTYOURTURN {
	      public String toString() {
	          return "N�o � jogador da vez.";
	      }
	  },
	  
	  CANTOPENDOOR {
	      public String toString() {
	          return "Monstros n�o s�o capazes de abrir portas!";
	      }
	  },
	  
	  SLEEPFREEZE {
	      public String toString() {
	          return "Voc� est� dormindo, n�o pode se mover!";
	      }
	  },
	  
	  PHYSICSLAWS {
	      public String toString() {
	          return "Respeite as leis da f�sica";
	      }
	  },
	  
	  NOMOVELEFT {
	      public String toString() {
	          return "Voc� n�o tem movimento suficiente nessa rodada.";
	      }
	  },
	  
	  SLEEPNOATT {
	      public String toString() {
	          return "Voc� est� dormindo, n�o pode atacar!";
	      }
	  },
	  
	  OUTOFRANGE {
	      public String toString() {
	          return "N�o � poss�vel atacar um alvo t�o distante.";
	      }
	  },
	  
	  NOMIND {
	      public String toString() {
	          return "Mind insuficiente.";
	      }
	  },
	  
	  DOESNTUSESPELLS {
	      public String toString() {
	          return "Seu personagem n�o usa magia.";
	      }
	  },
	  
	  MAGICFAIL {
	      public String toString() {
	          return "O encantamento parece perigoso, mas o conjurador se desconcentra e o feiti�o se desfaz...";
	      }
	  },
	  
	  CHARSELECTERROR {
	      public String toString() {
	          return "Erro na sele��o de personagem.";
	      }
	  },
	  
	  THEPLAYER {
	      public String toString() {
	          return "O jogador ";
	      }
	  },
	  
	  FOUNDGOLD {
	      public String toString() {
	          return " encontrou algumas moedas de ouro.";
	      }
	  },
	  
	  THECREATURE {
	      public String toString() {
	          return "A criatura ";
	      }
	  },
	  
	  WOKEUP {
	      public String toString() {
	          return " acordou!";
	      }
	  },
	  
	  MONSTERCANTUNDERSTAND {
	      public String toString() {
	          return "O monstro n�o � capaz de entender esse comando.";
	      }
	  },
	  
	  CHARUNAVAILABLE {
	      public String toString() {
	          return "Personagem n�o dispon�vel";
	      }
	  },
	  
	  ENDGAME {
	      public String toString() {
	          return "O jogo terminou, deseja fech�-lo?";
	      }
	  },
	  
	  ZARGONNOGOLD {
	      public String toString() {
	          return "Zargon n�o carrega gold";
	      }
	  },
	  
	  UKNOWN {
	      public String toString() {
	          return "???????";
	      }
	  },
	  
	  SERVER {
	      public String toString() {
	          return ", Connected to server: ";
	      }
	  },
	  
	  COMMAPLAYER {
	      public String toString() {
	          return ", Player: ";
	      }
	  },
	  
	  INSTRUCTIONS {
	      public String toString() {
	          return "Instru\u00E7\u00F5es";
	      }
	  },
	  
	  MENU {
	      public String toString() {
	          return "Menu";
	      }
	  },
	  
	  SELECTCHAR {
	      public String toString() {
	          return "Selecionar personagem";
	      }
	  },
	  
	  SETTINGS {
	      public String toString() {
	          return "Configura��es";
	      }
	  },
	  
	  TRIGGERMUSIC {
	      public String toString() {
	          return "Turn music on/off";
	      }
	  },
	  
	  SELECTSPELL {
	      public String toString() {
	          return "Digite o n�mero correspondente � magia que deseja usar: ";
	      }
	  },
	  
	  SELECTTARGET {
	      public String toString() {
	          return "Digite o n�mero correspondente ao alvo escolhido: ";
	      }
	  },
	  
	  INPUTNAME {
	      public String toString() {
	          return "Digite o nome do seu personagem: ";
	      }
	  },
	  
	  OBTAINSERVERID {
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
	          return "Insira o endere�o do servidor";
	      }
	  },
	  
	  SUCCESSFULCONNECT {
	      public String toString() {
	          return "Conexao com exito";
	      }
	  },
	  
	  ALREADYCONNECTED {
	      public String toString() {
	          return "Tentativa de conexao com conexao previamente estabelecida";
	      }
	  },
	  
	  FAILEDCONNECT {
	      public String toString() {
	          return "Tentativa de conexao falhou";
	      }
	  },
	  
	  SUCCESSFULDISCONNECT {
	      public String toString() {
	          return "Desconexao com exito";
	      }
	  },
	  
	  DISCBEFORECONNECT {
	      public String toString() {
	          return "Tentativa de desconexao sem conexao previamente estabelecida";
	      }
	  },
	  
	  FAILEDDISCONNECT {
	      public String toString() {
	          return "Tentativa de desconexao falhou";
	      }
	  },
	  
	  SUCCESSFULSTART {
	      public String toString() {
	          return "Solicita��o de inicio procedida com exito";
	      }
	  },
	  
	  STARTBEFORECONNECT {
	      public String toString() {
	          return "Tentativa de inicio sem conexao previamente estabelecida";
	      }
	  },
	  
	  UNINTERRUPTEDGAME {
	      public String toString() {
	          return "Partida corrente nao interrompida";
	      }
	  },
	  
	  HEROWIN {
	      public String toString() {
	          return "Parab�ns aos aventureiros!\n Voc�s foram vitoriosos!!!";
	      }
	  },
	  
	  ZARGONWIN {
	      public String toString() {
	          return "Oh n�o, o terr�vel Zargon venceu desta vez!";
	      }
	  },
	  
	  YOUHAVE {
	      public String toString() {
	          return "Voc� possui ";
	      }
	  },
	  
	  INVYCOINS {
	      public String toString() {
	          return " moedas de ouro no seu invent�rio.";
	      }
	  },
	  
	  CURRENTBP {
	      public String toString() {
	          return "Body atual: ";
	      }
	  },
	  
	  CURRENTMP {
	      public String toString() {
	          return "\nMind restante: ";
	      }
	  },
	  
	  REMAININGMOVES {
	      public String toString() {
	          return "\nMovimento restante: ";
	      }
	  },
	  
	  CURRENTSTATUS {
	      public String toString() {
	          return "\nStatus atual: ";
	      }
	  },
	  
	  LINE {
	      public String toString() {
	          return "\nLinha: " ;
	      }
	  },
	  
	  COLUMN {
	      public String toString() {
	          return " Coluna: ";
	      }
	  },
	  
	  TTW {
	      public String toString() {
	          return "\nTurns left to wake: ";
	      }
	  },
	  
	  NUMBEROFPLAYERS {
	      public String toString() {
	          return "Favor digitar a quantidade de jogadores que ir�o participar desta partida: ";
	      }
	  },
	  
	  OHNO {
	      public String toString() {
	          return "Oh n�o! ";
	      }
	  },
	  
	  ACTIVATEDTRAP {
	      public String toString() {
	          return " ativou uma armadilha e perdeu ";
	      }
	  },
	  
	  OFBP {
	      public String toString() {
	          return " de body points!";
	      }
	  },
	  
	  RECEIVED {
	      public String toString() {
	          return " recebeu ";
	      }
	  },
	  
	  OFDAMAGE {
	      public String toString() {
	          return " de dano.";
	      }
	  },
	  
	  ATTEMPTSSEPPUKU {
	      public String toString() {
	          return " tenta seppuku e recebe ";
	      }
	  },
	  
	  DIEDHONORABLY {
	      public String toString() {
	          return " morreu honrosamente em batalha.";
	      }
	  },
	  
	  THE {
	      public String toString() {
	          return "O ";
	      }
	  },
	  
	  MURMUREDSPELL {
	      public String toString() {
	          return " murmurou algumas palavras m�gicas (";
	      }
	  },
	  
	  ANDTHECREATURE {
	      public String toString() {
	          return "), e a criatura ";
	      }
	  },
	  
	  MODIFIEDIN {
	      public String toString() {
	          return " modificou em ";
	      }
	  },
	  
	  BPMODSTATUS {
	      public String toString() {
	          return " seus body points, e conferiu a ele o estado ";
	      }
	  },
	  
	  BPMODSNOTATUS {
	      public String toString() {
	          return " seus body points!";
	      }
	  },
	  
	  EXCLMARK {
	      public String toString() {
	          return "!";
	      }
	  },
	  
	  DIEDONTRAP {
	      public String toString() {
	          return " morreu ao pisar numa armadilha!";
	      }
	  },
	  
	  CREATURESTURN {
	      public String toString() {
	          return "A vez � da criatura ";
	      }
	  },
	  
	  ONLINE {
	      public String toString() {
	          return " a qual est� na linha ";
	      }
	  },
	  
	  COMMACOLUMN {
	      public String toString() {
	          return ", coluna ";
	      }
	  },
	  
	  OFGAMEBOARD {
	      public String toString() {
	          return " do tabuleiro.";
	      }
	  },
	  
	  SELECTDOOR {
	      public String toString() {
	          return "Escolha a porta a ser aberta: \n";
	      }
	  },
	  
	  DWARFDISARMEDTRAPS {
	      public String toString() {
	          return "O an�o desarmou as armadilhas pr�ximas a ele!";
	      }
	  },
	  
	  ROCKFALL {
	      public String toString() {
	          return "Oh n�o, voc� est� sendo soterrado! Escolha se deseja ir para frente ou para tr�s:\n";
	      }
	  },
	  
	  FORWARD {
	      public String toString() {
	          return "para frente\n";
	      }
	  },
	  
	  BACKWARD {
	      public String toString() {
	          return "para tr�s";
	      }
	  },
	  
	  PITJUMP {
	      public String toString() {
	          return "Voc� deseja pular o buraco?\n";
	      }
	  },
	  
	  YES {
	      public String toString() {
	          return "sim\n";
	      }
	  },
	  
	  NO {
	      public String toString() {
	          return "n�o";
	      }
	  },
	  
	  ERRORABORT {
	      public String toString() {
	          return "Ocorreu um erro e o jogo teve de ser abortado";
	      }
	  },
	  
	  SELECTYOURCHAR {
	      public String toString() {
	          return "Selecione seu personagem";
	      }
	  },
	  
	  TYPECHARNUMBER {
	      public String toString() {
	          return "Digite o n\u00FAmero ao lado do escolhido:";
	      }
	  },
	  
	  ZARGONDESCRIPTION {
	      public String toString() {
	          return "Zargon. You control the monsters in the dungeon. Your task is to kill the heroes.";
	      }
	  },
	  
	  ZARGON {
	      public String toString() {
	          return "Zargon";
	      }
	  },
	  
	  BARBARIAN {
	      public String toString() {
	          return "Barbarian";
	      }
	  },
	  
	  WIZARD {
	      public String toString() {
	          return "Wizard";
	      }
	  },
	  
	  ELF {
	      public String toString() {
	          return "Elf";
	      }
	  },
	  
	  DWARF {
	      public String toString() {
	          return "Elf";
	      }
	  },
	  
	  LANGSELECT {
	      public String toString() {
	          return "Select a language";
	      }
	  },
	  
	  SELECT {
	      public String toString() {
	          return "Select";
	      }
	  },
	  
	  INSTRUCTMANUAL {
	      public String toString() {
	    	  if(AtorJogador.language == Languages.Portugues){
	    		  return "Manual de Instru��es";
	    	  } else {
	    		  return "Instruction Manual";
	    	  }
	      }
	  },
	  
	  GUIDE {
	      public String toString() {
	          return "You are in a dungeon. It is cold, damp and dark. "
	          		+ "The only sounds are your own heartbeat, "
	          		+ "breathing and dripping water ecoing through the passageways. "
	          		+ "There are no lights, even in the distance, "
	          		+ "except for the torches you and your allies carry. "
	          		+ "Your objective is to reach the ladder and end your turn to escape "
	          		+ "the terrible fate that awaits all who get lost in Zargon's halls. "
	          		+ "Have I mentioned Zargon? He is a sorcerer who specializes in dark magic. "
	          		+ "He controls monsters with it "
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
	          		+ "Then everyone selects their characters and start playing!";
	      }
	  }
}

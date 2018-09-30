package modelo;

import visao.AtorJogador;
import visao.Languages;

public enum Strings {
	 HEROQUEST {
	      public String toString() {
	          return "HeroQuestv3.0(alfa)";
	      }
	  },
	  
	  DOOROUTOFRANGE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Não está perto da porta.";
	    	  	default: return "You are too far from the door.";
	    	  }
	      }
	  },

	  NOTYOURTURN {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Não é o jogador da vez.";
	    	  	default: return "It's not your turn.";
	    	  }
	      }
	  },
	  
	  YOURTURN {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "É sua vez.";
	    	  	default: return "It's your turn.";
	    	  }
	      }
	  },
	  
	  CANTOPENDOOR {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Monstros não são capazes de abrir portas!";
	    	  	default: return "Monsters cannot open doors!";
	    	  }
	      }
	  },
	  
	  SLEEPFREEZE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Você está dormindo, não pode se mover!";
	    	  	default: return "You are fast asleep, and cannot move!";
	    	  }
	      }
	  },
	  
	  PHYSICSLAWS {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Respeite as leis da física!";
	    	  	default: return "Show some respect for the laws of Physics!";
	    	  }
	      }
	  },
	  
	  NOMOVELEFT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Você não tem movimento suficiente nesta rodada.";
	    	  	default: return "You have no movement left!";
	    	  }
	      }
	  },
	  
	  SLEEPNOATT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Você está dormindo, não pode atacar!";
	    	  	default: return "You are fast asleep, and cannot attack!";
	    	  }
	      }
	  },
	  
	  OUTOFRANGE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Não é possível atacar um alvo tão distante.";
	    	  	default: return "You cannot reach your target.";
	    	  }
	      }
	  },
	  
	  NOMIND {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Mind insuficiente.";
	    	  	default: return "Insufficient Mind points.";
	    	  }
	      }
	  },
	  
	  DOESNTUSESPELLS {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Seu personagem não usa magia.";
	    	  	default: return "Your character cannot use spells.";
	    	  }
	      }
	  },
	  
	  MAGICFAIL {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "O encantamento parece perigoso, mas o conjurador se desconcentra e o feitiço se desfaz...";
		    	default: return "The spell seems dangerous at first, but the caster loses his focus for a split-second and the spell vanishes from whence it came...";
	    	  }
	      }
	  },
	  
	  CHARSELECTERROR {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Erro na seleção de personagem. Tente novamente.";
	    	  	default: return "Error during character selection. Please try again.";
	    	  }
	      }
	  },
	  
	  THEPLAYER {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "O jogador ";
	    	  	default: return "The player ";
	    	  }
	      }
	  },
	  
	  FOUNDGOLD {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " encontrou algumas moedas de ouro.";
	    	  	default: return " found some gold coins.";
	    	  }
	      }
	  },
	  
	  THECREATURE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "A criatura ";
	    	  	default: return "The creature ";
	    	  }
	      }
	  },
	  
	  WOKEUP {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " acordou!";
	    	  	default: return " woke up!";
	    	  }
	      }
	  },
	  
	  MONSTERCANTUNDERSTAND {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "O monstro não é capaz de entender esse comando.";
	    	  	default: return "The monster cannot understand this command.";
	    	  }
	      }
	  },
	  
	  CHARUNAVAILABLE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Personagem não disponível";
	    	  	default: return "The selected character is unavailable";
	    	  }
	      }
	  },
	  
	  ENDGAME {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "O jogo terminou, deseja fechá-lo?";
	    	  	default: return "The game has ended, do you wish to exit?";
	    	  }
	      }
	  },
	  
	  ZARGONNOGOLD {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Zargon não carrega gold.";
	    	  	default: return "Zargon does not carry gold.";
	    	  }
	      }
	  },
	  
	  UKNOWN {
	      public String toString() {
	          return "???????";
	      }
	  },
	  
	  SERVER {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return ", Conectado ao servidor: ";
	    	  	default: return ", Connected to server: ";
	    	  }
	      }
	  },
	  
	  COMMAPLAYER {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return ", Jogador: ";
	    	  	default: return ", Player: ";
	    	  }
	      }
	  },
	  
	  INSTRUCTIONS {
	      public String toString() {
	    	  switch(AtorJogador.language){
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
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Selecionar personagem";
	    	  	default: return "Character selection";
	    	  }
	      }
	  },
	  
	  SETTINGS {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Configurações";
	    	  	default: return "Settings";
	    	  }
	      }
	  },
	  
	  TRIGGERMUSIC {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Iniciar/pausar a música";
	    	  	default: return "Turn music on/off";
	    	  }
	      }
	  },
	  
	  LANGUAGEBUTTON {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Língua";
	    	  	default: return "Language";
	    	  }
	      }
	  },
	  
	  SELECTSPELL {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Digite o número correspondente à magia que deseja usar: ";
	    	  	default: return "Type in the number corresponding to the spell you wish to use: ";
	    	  }
	      }
	  },
	  
	  SELECTTARGET {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Digite o número correspondente ao alvo escolhido: ";
	    	  	default: return "Type in the number corresponding to the desired target: ";
	    	  }
	      }
	  },
	  
	  INPUTNAME {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Digite o nome do seu personagem: ";
	    	  	default: return "Type in the your character's name: ";
	    	  }
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
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Insira o endereço do servidor";
	    	  	default: return "Type in the server's address";
	    	  }
	      }
	  },
	  
	  SUCCESSFULCONNECT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Conexao com exito";
	    	  	default: return "Successful connection";
	    	  }
	      }
	  },
	  
	  ALREADYCONNECTED {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Tentativa de conexao com conexao previamente estabelecida";
	    	  	default: return "Conection attempt with previously established connection";
	    	  }
	      }
	  },
	  
	  FAILEDCONNECT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Tentativa de conexao falhou";
	    	  	default: return "Failed to establish connection";
	    	  }
	      }
	  },
	  
	  SUCCESSFULDISCONNECT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Desconexao com exito";
	    	  	default: return "Successfully disconnected";
	    	  }
	      }
	  },
	  
	  DISCBEFORECONNECT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Tentativa de desconexao sem conexao previamente estabelecida";
		    	default: return "Attempt to disconnect without previous connection";
	    	  }
	      }
	  },
	  
	  FAILEDDISCONNECT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Tentativa de desconexao falhou";
	    	  	default: return "Failed disconnection attempt";
	    	  }
	      }
	  },
	  
	  SUCCESSFULSTART {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Solicitação de inicio procedida com exito";
	    	  	default: return "Successful starting request";
	    	  }
	      }
	  },
	  
	  STARTBEFORECONNECT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Tentativa de inicio sem conexao previamente estabelecida";
	    	  	default: return "Attempt at starting without previous connection";
	    	  }
	      }
	  },
	  
	  UNINTERRUPTEDGAME {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Partida corrente nao interrompida";
	    	  	default: return "Current game not interrupted";
	    	  }
	      }
	  },
	  
	  HEROWIN {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Parabéns aos aventureiros!\n Vocês foram vitoriosos!!!";
		    	default: return "Congratulations to the heroes!\n You were victorious!!!";
	    	  }
	      }
	  },
	  
	  ZARGONWIN {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Oh não, o terrível Zargon venceu desta vez!";
	    	  	default: return "Oh no, the evil Zargon has won!";
	    	  }
	      }
	  },
	  
	  YOUHAVE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Você possui ";
	    	  	default: return "You have ";
	    	  }
	      }
	  },
	  
	  INVYCOINS {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " moedas de ouro no seu inventário.";
	    	  	default: return " gold coins in your inventory.";
	    	  }
	      }
	  },
	  
	  CURRENTBP {
	      public String toString() {
	    	  switch(AtorJogador.language){
		    	  case Portugues: return "Body atual: ";
		    	  default: return "Current Body: ";
	    	  }
	      }
	  },
	  
	  CURRENTMP {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "\nMind restante: ";
	    	  	default: return "\nCurrent Mind: ";
	    	  }
	      }
	  },
	  
	  REMAININGMOVES {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "\nMovimento restante: ";
	    	  	default: return "\nRemaining Movement: ";
	    	  }
	      }
	  },
	  
	  CURRENTSTATUS {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "\nStatus atual: ";
	    	  	default: return "\nCurrent Status: ";
	    	  }
	      }
	  },
	  
	  LINE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "\nLinha: " ;
	    	  	default: return "\nLine: ";
	    	  }
	      }
	  },
	  
	  COLUMN {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " Coluna: ";
	    	  	default: return " Column: ";
	    	  }
	      }
	  },
	  
	  TTW {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "\nTurnos até acordar: ";
	    	  	default: return "\nTurns left to wake: ";
	    	  }
	      }
	  },
	  
	  NUMBEROFPLAYERS {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Favor digitar a quantidade de jogadores que irão participar desta partida: ";
	    	  	default: return "Please type in the (total) number of players who will participate in this game: ";
	    	  }
	      }
	  },
	  
	  OHNO {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Oh não! ";
	    	  	default: return "Oh no! ";
	    	  }
	      }
	  },
	  
	  ACTIVATEDTRAP {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " ativou uma armadilha e perdeu ";
	    	  	default: return " has activated a trap and lost ";
	    	  }
	      }
	  },
	  
	  OFBP {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " de Body points!";
	    	  	default: return " Body points!";
	    	  }
	      }
	  },
	  
	  RECEIVED {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " recebeu ";
	    	  	default: return " received ";
	    	  }
	      }
	  },
	  
	  OFDAMAGE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " de dano.";
	    	  	default: return " of damage.";
	    	  }
	      }
	  },
	  
	  ATTEMPTSSEPPUKU {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " tenta seppuku e recebe ";
	    	  	default: return " attempts seppuku and inflicts ";
	    	  }
	      }
	  },
	  
	  DIEDHONORABLY {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " morreu honrosamente em batalha.";
	    	  	default: return " has died honorably in battle.";
	    	  }
	      }
	  },
	  
	  THE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "O ";
	    	  	default: return "The ";
	    	  }
	      }
	  },
	  
	  MURMUREDSPELL {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " murmurou algumas palavras mágicas (";
	    	  	default: return " murmured a few magical words (";
	    	  }
	      }
	  },
	  
	  ANDTHECREATURE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "), e a criatura ";
	    	  	default: return "), and the creature ";
	    	  }
	      }
	  },
	  
	  MODIFIEDIN {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " modificou em ";
	    	  	default: return " modified in ";
	    	  }
	      }
	  },
	  
	  BPMODSTATUS {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " seus Body points, e conferiu a ele o estado ";
	    	  	default : return " its Body points, and conferred to it the status ";
	    	  }
	      }
	  },
	  
	  BPMODSNOTATUS {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " seus Body points!";
	    	  	default: return " its Body points!";
	    	  }
	      }
	  },
	  
	  EXCLMARK {
	      public String toString() {
	          return "!";
	      }
	  },
	  
	  DIEDONTRAP {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " morreu ao ativar uma armadilha!";
	    	  	default: return " died as it triggered a trap!";
	    	  }
	      }
	  },
	  
	  CREATURESTURN {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "A vez é da criatura ";
	    	  	default: return "The turn is of creature ";
	    	  }
	      }
	  },
	  
	  ONLINE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " a qual está na linha ";
	    	  	default: return " which is on line ";
	    	  }
	      }
	  },
	  
	  COMMACOLUMN {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return ", coluna ";
	    	  	default: return ", column ";
	    	  }
	      }
	  },
	  
	  OFGAMEBOARD {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return " do tabuleiro.";
	    	  	default: return " of the game board.";
	    	  }
	      }
	  },
	  
	  SELECTDOOR {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Escolha a porta a ser aberta: \n";
	    	  	default: return "Choose the door to be opened: \n";
	    	  }
	      }
	  },
	  
	  DWARFDISARMEDTRAPS {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "O Anão desarmou as armadilhas próximas a ele!";
	    	  	default: return "The Dwarf has disarmed the traps close to him!";
	    	  }
	      }
	  },
	  
	  ROCKFALL {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Oh não, você está sendo soterrado! Escolha se deseja ir para frente ou para trás:\n";
	    	  	default: return "Oh no, you are being buried by rocks! Decide if you want to move forwards or backwards:\n";
	    	  }
	      }
	  },
	  
	  FORWARD {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "para frente\n";
	    	  	default: return "forwards\n";
	    	  }
	      }
	  },
	  
	  BACKWARD {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "para trás";
	    	  	default: return "backwards";
	    	  }
	      }
	  },
	  
	  PITJUMP {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Você deseja pular o buraco?\n";
	    	  	default: return "Do you wish to jump over the pit?\n";
	    	  }
	      }
	  },
	  
	  YES {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "sim\n";
	    	  	default: return "yes\n";
	    	  }
	      }
	  },
	  
	  NO {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "não";
	    	  	default: return "no";
	    	  }
	      }
	  },
	  
	  ERRORABORT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Ocorreu um erro e o jogo teve de ser abortado";
	    	  	default: return "An error has ocurred and the game had to be aborted";
	    	  }
	      }
	  },
	  
	  SELECTYOURCHAR {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Selecione seu personagem";
	    	  	default: return "Select your character";
	    	  }
	      }
	  },
	  
	  TYPECHARNUMBER {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Digite o n\u00FAmero ao lado do escolhido:";
	    	  	default: return "Type in the number beside the chosen character";
	    	  }
	      }
	  },
	  
	  ZARGONDESCRIPTION {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Zargon. Você controla os monstros na masmorra. Seu objetivo é matar os herois."; 
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
	          return "Dwarf";
	      }
	  },
	  
	  NORTH {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Norte";
	    	  	default : return "North";
	    	  }
	      }
	  },
	  
	  SOUTH {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Sul";
	    	  	default: return "South";
	    	  }
	      }
	  },
	  
	  EAST {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Leste";
	    	  	default: return "East";
	    	  }
	      }
	  },
	  
	  WEST {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Oeste";
	    	  	default: return "West";
	    	  }
	      }
	  },
	  
	  LANGSELECT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Selecione uma língua";
	    	  	default: return "Select a language";
	    	  }
	      }
	  },
	  
	  SELECT {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Selecione";
	    	  	default: return "Select";
	    	  }
	      }
	  },
	  
	  INSTRUCTMANUAL {
	      public String toString() {
	    	  if(AtorJogador.language == Languages.Portugues){
	    		  return "Manual de Instruções";
	    	  } else {
	    		  return "Instruction Manual";
	    	  }
	      }
	  },
	  
	  GUIDE {
	      public String toString() {
	    	  switch(AtorJogador.language){
	    	  	case Portugues: return "Você está em uma masmorra. Está frio, úmido e totalmente escuro. "
	    	  			+ "Os únicos sons são de seus próprios batimentos cardíacos, "
	    	  			+ "respiração e ecos de água gotejando pelos corredores. "
	    	  			+ "Não há luz, nem ao longe, "
	    	  			+ "exceto pelas tochas que você e seus aliados carregam. "
	    	  			+ "Seu objetivo é alcançar a escada e encerrar seu turno para escapar "
	    	  			+ "o terrível destino que espera a todos que se perdem nas masmorras de Zargon. "
	    	  			+ "Eu mencionei Zargon? Ele é um feiticeiro que se especializa em magia negra. "
	    	  			+ "Ele controla monstros com ela, "
	    	  			+ "e se regozija ao destruir todos que se atrevem a entrar em seu covil. "
	    	  			+ "Lute em seu caminho e sobreviva, ou morra tentando. "
	    	  			+ "Boa sorte, e que os Deuses tenham piedade de sua alma."
	    	  			+ "\r\n\rUse as setas em seu teclado para se mover."
	    	  			+ "\r\nClique nos botões com seu mouse para selecionar uma ação. "
	    	  			+ "Alternativamente, veja \"Teclado\" abaixo."
	    	  			+ "\r\nAbra/feche portas se posicionando ao lado delas "
	    	  			+ "e clicando nelas diretamente no mapa, "
	    	  			+ "ou escolhendo através do menu pelo Teclado."
	    	  			+ "\r\nTeclado: 1, 2 e 3 para conectar, "
	    	  			+ "desconectar e iniciar partida, respectivamente. "
	    	  			+ "A para atacar, S para usar magia, R para procurar por armadilhas, "
	    	  			+ "T para procurar por tesouro, O para abrir portas, "
	    	  			+ "P para selecionar personagem, I para abrir o inventário, "
	    	  			+ "E para encerrar seu turno e M para tocar/pausar a música."
	    	  			+ "\r\n*Para começar a jogar, conecte se já não estiver conectado. "
	    	  			+ "Então, UM jogador inicia a partida "
	    	  			+ "e digita o número de jogadores que irão participar. "
	    	  			+ "Então todos selecionam seus personagens e começam a jogar!"
	    	  			+ "\nPS: Esta versão contém um protótipo de saves de jogador. Para carregar um save, "
		          		+ "utilize o mesmo nome que você utilizou no seu último jogo "
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
	  
	  CONFIRMLOADGAME {
	      public String toString() {
	    	  if(AtorJogador.language == Languages.Portugues){
	    		  return "Um arquivo com o seu nome já existe, deseja carregá-lo?";
	    	  } else {
	    		  return "A file with your name already exists, do you wish to load it?";
	    	  }
	      }
	  },
	  
	  FILENOTFOUND {
	      public String toString() {
	    	  if(AtorJogador.language == Languages.Portugues){
	    		  return "Arquivo com não encontrado, criando um novo...";
	    	  } else {
	    		  return "File not found, creating new file...";
	    	  }
	      }
	  }
	  
}

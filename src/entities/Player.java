package entities;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Player implements Jogada {

	private static final long serialVersionUID = 1098287781774912497L;
	protected String name;

	public Player(String name) {
		this.name = name;
	}
	
	public Player() {}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
package entities;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Player implements Jogada {

	private static final long serialVersionUID = 1098287781774912497L;
	protected String nome;

	public Player(String name) {
		this.nome = name;
	}
	
	public Player() {}
	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
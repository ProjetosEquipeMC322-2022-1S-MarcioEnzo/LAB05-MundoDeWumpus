package pt.c40task.l05wumpus;

import java.util.Random;

public class Hero extends Componente {
	private boolean temFlecha, flechaEquipada, temOuro;

	public Hero(int row, int column) {
		this.row = row;
		this.column = column;
		temFlecha = true;
		flechaEquipada = false;
		temOuro = false;
	}
	
	public boolean flechaEquipada() {
		return flechaEquipada;
	}

	public void perdeFlecha() {
		temFlecha = false;
		flechaEquipada = false;
	}

	private void equiparFlecha() {
		if (temFlecha) {
			flechaEquipada = true;
			temFlecha = false;
		} else
			throw new GameException("O her�i n�o tem flechas para equipar");
	}

	private void pegaOuro() {
		Sala salaAtual = getSala();
		if (salaAtual.getComponentes()[0] instanceof Ouro) {
			salaAtual.setComponente(0, null);
			temOuro = true;
			System.out.println("UHUUU! T� rico !!! =D");
		} else
			throw new GameException("N�o tem ouro aqui =(");
	}

	public boolean enfrentaWumpus() {
		Sala salaAtual = getSala();
		boolean vitoria = false;
		if (flechaEquipada) {
			Random rand = new Random();
			if (rand.nextInt(2) == 1)
				vitoria = true;
		}
		if (vitoria)
			salaAtual.setComponente(0, null);
		return vitoria;
	}

	private void fazMovimento(char move) {
		Sala salaAtual = getSala();
		switch (move) {
		case 'w':
			if (salaAtual.getRow() != 0) {
				salaAtual.setComponente(1, null);
				row--;
			} else
				throw new GameException("Ainda n�o atravesso paredes, n� =P");
			break;

		case 'a':
			if (salaAtual.getColumn() != 0) {
				salaAtual.setComponente(1, null);
				column--;
			} else
				throw new GameException("Ainda n�o atravesso paredes, n� =P");
			break;

		case 's':
			if (salaAtual.getRow() != 3) {
				salaAtual.setComponente(1, null);
				row++;
			} else
				throw new GameException("Ainda n�o atravesso paredes, n� =P");
			break;
		case 'd':
			if (salaAtual.getColumn() != 3) {
				salaAtual.setComponente(1, null);
				column++;
			} else
				throw new GameException("Ainda n�o atravesso paredes, n� =P");
			break;
		default:
			throw new GameException("Comando de movimento inv�lido");
		}
		salaAtual = getSala();
		salaAtual.setVisitada(true);
		salaAtual.setComponente(1, this);
	}

	public void realizaAcao(char comando) {
		if (comando == 'w' || comando == 'a' || comando == 's' || comando == 'd')
			fazMovimento(comando);
		else if (comando == 'k')
			equiparFlecha();
		else if (comando == 'c')
			pegaOuro();
		else
			throw new GameException("A��o inv�lida! Digite Novamente.");
	}

	public boolean temOuro() {
		return temOuro;
	}

	public Sala getSala() {
		return caverna.getSala(row, column);
	}

	@Override
	public char representacao() {
		return 'P';
	}
}

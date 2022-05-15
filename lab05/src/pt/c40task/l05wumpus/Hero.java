package pt.c40task.l05wumpus;

import java.util.Random;

public class Hero extends Componente {
	private boolean temFlecha, flechaEquipada, temOuro;
	private Sala salaAtual;

	public Hero(int row, int column) {
		super(row, column);
		salaAtual = caverna.getSala(0, 0);
		temFlecha = true;
		flechaEquipada = false;
		temOuro = false;
	}

	private void equiparFlecha() {
		if (temFlecha) {
			flechaEquipada = true;
			temFlecha = false;
		} else
			throw new GameException("O herói não tem flechas para equipar");
	}
	
	private void pegaOuro() {
		if (salaAtual.getComponentes()[0] instanceof Ouro) {
			salaAtual.setComponente(0, null);
			temOuro = true;
			System.out.println("UHUUU! Tô rico !!! =D");
		}
		else
			throw new GameException("Não tem ouro aqui =(");
	}

	public boolean enfrentaWumpus() {
		boolean vitoria = false;
		if (flechaEquipada) {
			Random rand = new Random();
			if (rand.nextInt(2) == 1)
				vitoria = true;
		}
		return vitoria;
	}

	private void fazMovimento(char move) {
		switch (move) {
			case 'w':
				if (salaAtual.getRow() != 0) {
					salaAtual = caverna.getSala(row - 1, column);
					row--;
				 }
				else
					throw new GameException("Ainda não atravesso paredes, né =P");
			break;
			
			case 'a':
				if (salaAtual.getColumn() != 0) {
					salaAtual = caverna.getSala(row, column - 1);
					column--;
				}
				else
					throw new GameException("Ainda não atravesso paredes, né =P");
				break;
			
			case 's':
				if (salaAtual.getRow() != 3) {
					salaAtual = caverna.getSala(row + 1, column);
					row--;
				 }
				else
					throw new GameException("Ainda não atravesso paredes, né =P");
				break;
			case 'd':
				if (salaAtual.getColumn() != 3) {
					salaAtual = caverna.getSala(row, column + 1);
					column--;
				}
				else
					throw new GameException("Ainda não atravesso paredes, né =P");
				break;
			default:
				throw new GameException("Comando de movimento inválido");
		}
	}
	
	public void realizaAcao(char comando) {
		if (comando == 'w' || comando == 'a' || comando == 's' || comando == 'd') 
			fazMovimento(comando);
		else if (comando == 'k')
			equiparFlecha();
		else if (comando == 'c')
			pegaOuro();
		else
			throw new GameException("Ação inválida! Digite Novamente.");
	}
	
	public boolean temOuro() {
		return temOuro;
	}

	@Override
	public char representacao() {
		return 'P';
	}
}

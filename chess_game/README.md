# Projeto Crazy Chess, jogo de xadrez online
## Alunos
* Márcio Levi Sales Prado - 183680
* Enzo de Oliveira Farias - 247067

---
## Descrição do projeto
Nosso projeto consiste na criação de um jogo de xadrez o qual pode ser jogado tanto no modo _single player_ quanto no modo _multiplayer_ por meio de uma rede fechada, como os computadores do IC (Instituto de Computação) utilizando sockets e serialização.
### O modo _single player_
Por padrão o modo de jogo se não for selecionado um modo _multiplayer_ será o modo singleplayer o qual pode ser jogado em offline. Com esse modo selecionado ao pressionar o botão ***START*** aparecerá uma janela contendo duas opções perguntando ao usuário em qual ponto de vista ele gostaria de jogar **Branco** ou **Preto**, com isso selecionado abrirá uma nova janela de jogo com o lado voltado para vc estando no ponto de vista selecionado. Com isso você poderá treinar várias jogadas de xadrez possíveis e melhorar seu nível, sendo perfeito para quem esteja iniciando no xadrez e queira aprimorar suas habilidades.
### O modo _multiplayer_
Se o usuário clicar em no botão **Modo**, o botão abaixo de ***START***, e selecionar o modo  _multiplayer_ ele terá a possibilidade de iniciar um jogo com outro jogador em uma rede fechada. Depois de selecionado este modo de jogo, o usuário deve clicar em ***START***, então aparecerá uma janela perguntando se ele gostaria de **Criar** ou **Entrar** em uma sala. Caso ele clique em **Criar** o sistema ficará 50 segundos esperando alguém entrar no servidor recém-criado, caso ninguém entre ele lançará um *Timed-Out Exception*. Caso contrário, o programa pedirá para o usuário digitar o endereço IP do servidor no qual ele gostaria de entrar, após entrar, em cada computador aparecerá uma tela de jogo, as cores de cada jogador são escolhidas aleatoriamente e cada um só descobrirá sua cor após o jogo começar.
## Links importantes
* Arquivo jar do [Jogo de Xadrez](https://github.com/ProjetosEquipeMC322-2022-1S-MarcioEnzo/LAB05-MundoDeWumpus/blob/main/JogoXadrez.jar)
* [Primeira apresentação de slides](https://docs.google.com/presentation/d/16Hx_AqMoqavT5vFONxTMgnltgcCFWbol-6Pr7Gtcvvg/edit#slide=id.p1)
* [Apresentação final](https://docs.google.com/presentation/d/1zW8TBTf8BGTqB8bwBb-BsgGm5dPOKvZp78PV2Tn0GJg/edit#slide=id.p)

## Evolução do projeto
Ao longo do desenvolvimento do projeto várias ideias foram adicionadas e outras removidas, conforme o projeto se encaminhava e o tempo passava. Uma das maiores dificuldades enfrentadas ao longo do projeto foi referente à interface gráfica, visto que nenhum dos membros do grupo nunca havia feito uso de uma antes e tivemos que aprender do zero como funcionava e como implementariamos-na, visto que a interface gráfica em java primeiro é digitada no formato de código e então vemos o resultado final. Durante esse processo aprendemos sobre a interface <<_ActionListener_>> e o _design pattern Observer_ o qual implementamos. Outra dificuldade que tivemos foi com relação à conectividade do jogo e sobre como manejariamos o modo _multiplayer_, para isso aprendemos sobre o conceito de __serialização__ e brevemente sobre as classes *ObjectOutputStream* e *ObjectInputStream*. Todas as classe utilizadas no pacote _model_ e _controler_ implementam a interface <<_Serializable_>> o que permite que ambos os jogadores compartilhem a mesma partida de xadrez e sempre que algum movimento é efetuado, a partida é serializada, enviada para o outro jogador e então desserializada, atualizando a partida. Outra dificuldade enfrentada Foi em como o jogo detectaria que um movimento havia sido realizado e assim poderia atualizar a partida e também no menu inicial para processar que a partida havia começado, para tal utilizamos um método talvez não muito recomendado que é o *Thread.sleep()* para que a thread main ficasse inativa enquanto rodava a thread da interface gráfica.
## Destaques de código
Iremos pôr agora exemplos de pontos do código que foram essenciais para o funcionamento do projeto

#### A interface *IPiece*
```
package model;

import java.io.Serializable;

import javax.swing.ImageIcon;

public interface IPiece extends Serializable {
	public boolean[][] possibleMoves(); //retorna uma matriz de boleanos dizendo se dado movimento é possível ou não de ser feito 
	public Board getBoard();
	
	public default boolean possibleMove(int row, int column) {
		return possibleMoves()[row][column];
	}
	public default boolean possibleMove(Position pos) {
		return possibleMove(pos.getRow(), pos.getColumn());
	}
	
	public Position getPosition();
	public default int getRow() {
		return getPosition().getRow();
	}
	
	public default int getColumn() {
		return getPosition().getColumn();
	}
	
	public default boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		int width = mat.length;
		int heigth = mat[0].length;
		for (int i = 0; i < width; i++) 
			for (int j = 0; j < heigth; j++)
				if (mat[i][j])
					return true;
		return false;
	}
	
	public ImageIcon image();
	
}
```
A interface IPiece, implementada pela classe Piece, possui vários métodos que podem ser sobrescritos não apenas por peças de xadrez, mas também por qualquer outra peça de outros jogos de tabuleiro que talvez venham a ser criados em possíveis atualizações. Ela põe todos os métodos essenciais para peças de jogos no geral. Um exemplo de polimorfismo e reuso, visto que as classes herdeiras de _Piece_ como _ChessPiece_ e suas herderiras (_King, Queen, Bishop, Knight, Rook, Pawn_) implementaram essa interface permitindo o polimorfismo e o reuso.
#### A classe Network
```
(inserir classe network e seus destaques)
```
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
package online;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network implements INetwork {
	private ObjectInputStream is;
	private ObjectOutputStream os;
	private Socket socket;
	private ServerSocket serverSocket;

	public Network(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Network(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		this.attach();
	}
	
	public void close()  {
		try {
			if (serverSocket != null)
				serverSocket.close();
			if (socket != null)
				socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(Object type) throws IOException {
		os.writeObject(type);
		os.flush();
	}
	
	public Object read() throws IOException {
		try {
			Object obj;	
			obj = is.readObject();
			return obj;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}

	public void attach() throws IOException {
			serverSocket.setSoTimeout(50000);
			socket = serverSocket.accept();
			is = new ObjectInputStream(socket.getInputStream());
			os = new ObjectOutputStream(socket.getOutputStream());
	}
}

```

O propósito da classe network é manusear a comunicação entre dois usuários compartilhando um Object, a classe possui os construtores do tipo cliente e servidor e a interface INetwork permite implementação de leitura, envio e fechamento da comunicação entre os usuários.
 Ao ser chamado o construtor do tipo servidor a porta padrão de esperar conexão de um usuário é 6666, e por 50 segundos uma conexão de um socket é esperado, do lado do cliente é necessário fornecer o ip para conexão, assim um objeto do tipo Socket é instanciado e ambos cliente e servidor recebem esta instância, após isso também instanciam seus atributos do tipo ObjectInputStream e ObjectOutputStream, fornecidas pelo socket da conexão. Os objetos descritos anteriormente funcionam como os canais de recepção/envio de uma stream de bytes correspondendo a um object, sendo S o servidor e C cliente para um par ordenado as mensagens se correspondem da seguinte maneira : S(is,os)-->C(os,is),por exemplo uma mensagem do lado do servidor é escrita na outputstream e o cliente a lê na input stream, como um object, no caso do método read um flush é necessário para controlar a leitura de objetos enviados.
  Na aplicação do jogo, este em si requere uma INetwork quando chamado o modo multiplayer e dependendo de sua seleção um servidor ou um cliente são instanciados e assim os métodos read e send são utilizados para enviar a partida de xadrez que ambos os jogadores disputam, usando do polimorfismo para operar a comunicaçao.

  ## Como o modo multiplayer funciona, exemplo servidor
  ```
  jogo.server(6666);
					ChessColor color = (jogo.decidir() == 1) ? ChessColor.WHITE : ChessColor.BLACK;
					jogo.enviarPrioridade();
					Thread.sleep(200);
					ChessMatch chessMatch = new ChessMatch();
					jogo.enviarPartida(chessMatch);

					Player player = new Player();
					player.connect(chessMatch, color, true);
					jogo.setPlayer(player);
					jogo.getGameFrame().setVisible(false);
					jogo.setGameFrame(player.getGUI());
					((BoardGUI) jogo.getGameFrame()).connect(jogo);
					jogo.start();
					while (!chessMatch.isCheckMate() && !chessMatch.checkTie()) {
						if (chessMatch.getCheck())
							JOptionPane.showMessageDialog(jogo.getGameFrame(), "Você está em xeque, cuidado!", "Xeque",
									JOptionPane.WARNING_MESSAGE);
						if (chessMatch != null && color == chessMatch.getCurrentPlayer()) {
							while (color == chessMatch.getCurrentPlayer()) {
							}
						} else {
							chessMatch = jogo.receberPartida();
							jogo.atualizarPartida(chessMatch);
						}
  ```

  O trecho de código acima é um exemplo de como a classe _Jogar_ age no modo _multiplayer_ e faz parte do método _go_. No exemplo dado é para o caso do jogador escolher criar a sala, a cor do jogador é escolhida aleatoriamente e, basicamente, ele não pode efetuar nenhum movimento enquanto não for sua vez de jogar. O jogo continuará até ocorrer ou um xeque-mate ou um empate, o qual ocorre caso só haja dois reis no tabuleiro ou se ocorrer o afogamento, isto é, se todos os movimentos possíveis para o jogador colocarem o rei em xeque, o que significa que são proibidos.

  ## Destaques de pattern
  Para os destaques do design pattern gostaria de destacar dois: O _Observer_ e o _Facade_.
  ### Observer 
  O _Observer_ é implementado na interface gráfica nos seguintes trechos de código:
  ```
  ButtonHandler buttonHandler = new ButtonHandler();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j] = new JButton();
				squares[i][j].setBackground(((i + j) % 2 == 0) ? colorWhite : colorBlack);
				squares[i][j].addActionListener(buttonHandler);
				panel.add(squares[i][j]);
				
			}
		}
  ```
  e
  ```
  	private class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (multiplayer && pov != chessMatch.getCurrentPlayer())
				return;
			Position clickedPosition = getEventPosition(e);
			ChessPiece clickedPiece = chessMatch.getPieces()[clickedPosition.getRow()][clickedPosition.getColumn()];
			if (selectedPiece == null && clickedPiece != null && clickedPiece.getColor() == chessMatch.getCurrentPlayer()) {
				selectedPiece = clickedPiece;
				showPossibleMoves();
			} else if (selectedPiece != null && clickedPiece != null && clickedPiece.getColor() == chessMatch.getCurrentPlayer()) {
				flushPossibleMoves();
				selectedPiece = clickedPiece;
				showPossibleMoves();
			} else if (selectedPiece != null && selectedPiece.possibleMove(clickedPosition)) {
				flushPossibleMoves();
				try {
					chessMatch.peformChessMove(selectedPiece.getPosition(), clickedPosition);
					if (chessMatch.getPromoted() != null) {
						String[] possibilidades = { "Queen", "Rook", "Bishop", "Knight" };
						String o = (String) JOptionPane.showInputDialog(null, "Escolha sua promoção", "Peça promovida",
								JOptionPane.PLAIN_MESSAGE, null, possibilidades, "Queen");
						if (!o.equals("Knight"))
							chessMatch.replacePromotedPiece(o.substring(0, 1));
						else
							chessMatch.replacePromotedPiece("N");

					}
					fillBoard();
					selectedPiece = null;
					if (multiplayer)
						try {
							notifyGame();
						}
						catch (IOException exception) {
							exception.printStackTrace();
						}
				} catch (GameException exception) {
					JOptionPane.showMessageDialog(null, exception.getMessage(), "Chess Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
  ```
  Basicamente, cada botão da interface gráfica de jogo adicionar um objeto da classe interna _ButtonHandler_ o qual por sua vez implementa a interface <<_ActionListener_>> tornando se assim ouvinte dos possíveis eventos que venham a ocorrer na interface gráfica, isto é, o usuário clicar em uma das posições da interface gráfica. A classe interna "ouve" se ocorreu um evento em algum dos botões que a implementou, e então que realiza a ação que pode ser: nada, mudar a peça atual selecionada ou selecionar uma peça ou realizar de uma vez o movimento de xadrez.
### Facade
 Na classe _Jogar_ há o método `public static void go()`, a classe que de fato tem o método `public static void main(String[] args)` é a classe _ChessAplication_ pertencente ao pacote _aplication_. A única linha pertencente a esse método é `Jogar.go()`, dando ínicio ao jogo e escondendo do usuário os métodos utilizados e a forma como foram implementados, deixando assim a penas o mais simples, o start() sendo também um exemplo do encapsulamento no jogo.
## Conclusões e Trabalhos Futuros
  Apesar de aprender o básico do manuseio dos sockets e serialização após ser introduzido ao conceito de patterns,a classe network poderia ser utilizado patterns especificos para tratar comunicação entre usuários como por exemplo o pattern Database Session State, que objetos podem acessar arquivos de uma database, realizar as modificações necessárias e salvar novamente o arquivo, o design Strategy também seria útil para tornar mais genérica a ideia do jogo de tabuleiro e suas peças, permitindo mais jogos tabulares serem implementados de maneira online somente com a adição de um controler que conheça as regras. Outra conclusão é a inserção de novos modos de jogo, como um modo que a peças capturadas por um dos jogadores pode ser reensirida, mas dessa vez da cor do jogador que a capturou. Esses novos modos de jogo seriam herdeiros da classe _ChessMatch_ permitindo assim o uso de polimorfismo.
## Documentação dos componentes
### model
* Board
	* atributos
		* int rows (número de linhas)
		* int columns (número de colunas)
		* Piece[][] pieces (matriz composta de objetos Piece, ou seja, peças)
	* métodos principais
		* Piece piece(int row, int column/ Position pos) (retorna a peça na posição dada, seja como linha e coluna, seja como um objeto Position)
		* void placePiece(Piece piece, Position position) (coloca a peça na posição indicada)
		* Piece removePiece(Position position) (remove a peça que venha a estar nessa posição e então a retorna)
		* boolean thereIsAPiece(Position position) (diz se há uma peça na posição indicada)
* Position
	* atributos
		* int row
		* int column
* Piece (Implementa a interface IPiece)
	* atributos
		* Position position
		* Board board
#### model.pieces
* ChessColor <<__enum__>>
	BLACK, WHITE;
* ChessPiece (herdeira da classe Piece)
	* atributo adicionado
		* ChessColor color
* Peças de xadrez (King, Queen, Bishop, Rook, Knight e Pawn)
	* método principal sobrescrito da interface IPiece 
		* boolean[][] possibleMoves() (retorna os possíveis movimentos de cada peça)
* ChessPosition
	(Classe feita para representar a posição de xadrez no estilo letra e coluna, para uma possível atualização que registre a mudanças em tela)
## Controler e online
### Controler
* ChessMatch
	* atributos
	```
	private int turn;
	private ChessColor currentPlayer;
	private Board board;

	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
	private List<ChessPiece> capturedPieces = new ArrayList<>();
	```
	* método principal 
		`public void performChessMove(ChessPosition source, ChessPosition target)`
### Online
* Network (classe detalhada acima no texto)
* Jogar
	* atributos
	```
	private INetwork network;
	private int prioridade;
	private Player player;
	private boolean multiplayer;

	private State state = State.START_MENU;
	private JFrame gameFrame;
	```
	* métodos importantes
		* enviarPartida(ChessMatch chessmatch) (envia a partida de xadrez)
		* receberPartida() (ler a partida de xadrez)
		* go (inicia o jogo)
## view
* BoardGUI
	* atributos
		* ChessMatch chessMatch
		* ChessColor pov
		* Jogar jogo
	* métodos mais detalhados acima
* Menu (classe feita para definir os menu inicial e um possível final)

## Exceções 
* BoardException (refere-se a erros no tabuleiro em si)
* GameException (erros na execução do jogo)

# Link para o diagrama atualizado
[novo diagrama](https://lucid.app/lucidchart/5d326e0b-1b4e-474c-b27e-c7ad1b3b367b/edit?view_items=6QPw-g60uuN6&invitationId=inv_b94f319f-ebe8-4eee-b75e-8d1868699cff#)
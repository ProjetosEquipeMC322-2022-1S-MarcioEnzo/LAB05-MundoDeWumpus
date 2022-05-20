package pt.c40task.l05wumpus;

public class AppWumpus {

   public static void main(String[] args) {
      AppWumpus.executaJogo(
            (args.length > 0) ? args[0] : null,
            (args.length > 1) ? args[1] : null,
            (args.length > 2) ? args[2] : null);
   }
   
   public static void executaJogo(String arquivoCaverna, String arquivoSaida,
                                  String arquivoMovimentos) {
      Toolkit tk = Toolkit.start(arquivoCaverna, arquivoSaida, arquivoMovimentos);
      Componente.caverna = new Caverna();
      
      String cave[][] = tk.retrieveCave();
      char caverna[][] = new char[4][4];
      System.out.println("=== Caverna");
      for (int l = 0; l < cave.length; l++) {
         for (int c = 2; c < cave[l].length; c = c + 3) {
        	 caverna[Integer.parseInt(cave[l][c-2]) - 1][Integer.parseInt(cave[l][c-1]) - 1] = cave[l][c].charAt(0);
         }
      }
      Montador.construir(caverna);
      if (!Controle.validarCaverna())
    	  throw new GameException("Caverna Inv�lida");
      Controle controle = new Controle((Hero) Componente.caverna.getSala(0, 0).getComponentes()[1]);
      Montador.imprimirJogo(controle);
      tk.writeBoard(Componente.caverna.matrizDeCaracteres(), controle.getScore(), controle.getStatus());
      
      String movements = tk.retrieveMovements();
      for (int i = 0; i < movements.length(); i++) {
    	  controle.realizarComando(movements.charAt(i));
    	  Montador.imprimirJogo(controle);
    	  tk.writeBoard(Componente.caverna.matrizDeCaracteres(),controle.getScore(), controle.getStatus());
      }
      System.out.println("=== Movimentos");
      System.out.println(movements);
      
      System.out.println("=== Caverna Intermediaria");
      char partialCave[][] = {
         {'#', '#', 'b', '-'},
         {'#', 'b', '-', '-'},
         {'b', '-', '-', '-'},
         {'p', '-', '-', '-'}
      };
      int score = -120;
      char status = 'x'; // 'w' para venceu; 'n' para perdeu; 'x' intermediárias
      tk.writeBoard(partialCave, score, status);

      System.out.println("=== Última Caverna");
      char finalCave[][] = {
         {'#', '#', 'b', '-'},
         {'#', 'b', '#', 'f'},
         {'b', '-', '-', 'w'},
         {'#', '-', '-', '-'}
      };
      score = -1210;
      status = 'n'; // 'w' para venceu; 'n' para perdeu; 'x' intermediárias
      tk.writeBoard(finalCave, score, status);
      
      tk.stop();
   }

}

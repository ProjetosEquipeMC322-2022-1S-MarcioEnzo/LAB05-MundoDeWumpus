package pt.c40task.l05wumpus;

import java.io.IOException;
import java.util.Scanner;

public class AppWumpus {

   public static void main(String[] args) {
      AppWumpus.executaJogo(
            (args.length > 0) ? args[0] : null,
            (args.length > 1) ? args[1] : null,
            (args.length > 2) ? args[2] : null);
   }
   
   public static void executaJogo(String arquivoCaverna, String arquivoSaida,
                                  String arquivoMovimentos) {
	  try {
	      Toolkit tk = Toolkit.start(arquivoCaverna, arquivoSaida, arquivoMovimentos);
	      
	      Componente.caverna = new Caverna();
	      String cave[][] = tk.retrieveCave();
	      char caverna[][] = new char[4][4];
	      for (int l = 0; l < cave.length; l++) {
	         for (int c = 2; c < cave[l].length; c = c + 3) {
	        	 caverna[Integer.parseInt(cave[l][c-2]) - 1][Integer.parseInt(cave[l][c-1]) - 1] = cave[l][c].charAt(0);
	         }
	      }
	      Montador.construir(caverna);
	      if (!Controle.validarCaverna())
	    	  throw new GameException("Caverna Inválida");
	      Controle controle = new Controle((Hero) Componente.caverna.getSala(0, 0).getComponentes()[1]);
	      Montador.imprimirJogo(controle);
	      tk.writeBoard(Componente.caverna.matrizDeCaracteres(), controle.getScore(), controle.getStatus());
	      
	      String movements = tk.retrieveMovements();
	      int count = 0;
	      while (count < movements.length() && controle.getStatus() == 'P') {
	    	  try {
		    	  controle.realizarComando(movements.charAt(count));
		    	  Montador.imprimirJogo(controle);
		    	  tk.writeBoard(Componente.caverna.matrizDeCaracteres(),controle.getScore(), controle.getStatus());
		    	  count++;
	    	  }
	    	  catch (GameException gameError) {
	    		  System.out.println(gameError.getMessage());
	    	  }
	      }
	      tk.stop();
	  }
	  catch (IOException e) {
		  Toolkit tk = Toolkit.start(arquivoCaverna, arquivoSaida);
		  
		  Componente.caverna = new Caverna();
	      String cave[][] = tk.retrieveCave();
	      char caverna[][] = new char[4][4];
	      for (int l = 0; l < cave.length; l++) {
	         for (int c = 2; c < cave[l].length; c = c + 3) {
	        	 caverna[Integer.parseInt(cave[l][c-2]) - 1][Integer.parseInt(cave[l][c-1]) - 1] = cave[l][c].charAt(0);
	         }
	      }
	      Montador.construir(caverna);
	      if (!Controle.validarCaverna())
	    	  throw new GameException("Caverna Inválida");
	      Scanner keyboard = new Scanner(System.in);
	      Controle controle = new Controle((Hero) Componente.caverna.getSala(0, 0).getComponentes()[1]);
	      Montador.imprimirJogo(controle);
	      tk.writeBoard(Componente.caverna.matrizDeCaracteres(), controle.getScore(), controle.getStatus());
	      
	      while (controle.getStatus() == 'P') {
	    	  try {
	    		  controle.realizarComando(keyboard.nextLine().charAt(0));
	    		  Montador.imprimirJogo(controle);
	    	  }
	    	  catch (GameException gameError) {
	    		  System.out.println(gameError.getMessage());
	    		  Montador.imprimirJogo(controle);
		    	  tk.writeBoard(Componente.caverna.matrizDeCaracteres(),controle.getScore(), controle.getStatus());
	    	  }
	      }
	      
	      keyboard.close();
	      tk.stop();
	  }
   }

}

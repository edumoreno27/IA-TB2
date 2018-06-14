import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;

/**
 * Reportar las acciones de la vista al modelo
 * Guia de movimientos:
 * Player0 hace un movimiento, selecciona OK
 * Otro jugador puede llamarlo "mentiroso"
 * Player1 hace un movimiento
 * Player 0 ve el botón Miente/No miente y debe seleccionar uno
 * Si llama miente: si el acusado miente se le dará la pila de cartas, de lo contrario player0 recibirá la pila
 * Si llama no miente: le toca al siguiente jugador si duda
 * Otro jugador puede llamar a player1 "mentiroso"
 * Player2 hace un movimiento
 */
 
public class GameController {

	GameLogic gamelogic;
	MainGameFrame view;
	
	int currplayer = 0; //Turno del actual jugador (players 0 a 3)

	public void addModel(GameLogic model) {
		gamelogic = model;
	}

	public void addView(MainGameFrame view) {
		this.view = view;
	}

	JButton getStartButton(String title)
	{
		JButton b = new JButton(title);

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Comenzando juego: " + "La acción del controlador fue llamada");

				/*Cuando se hace clic en el botón start, se inicializa el juego, se muestra la mano del jugador*/
				gamelogic.startGame();
				
				//Cuando el juego inicia se muestra las cartas del jugador
				try {
					view.setItsYourTurn(true);
					view.update();
					view.setStartDisabled();
					
				} catch (IOException e1) {
					System.out.println("Error consiguiendo la imagen de las cartas");
					e1.printStackTrace();
				}
			}
		});
		return b;
	}

	JButton getOKButton()
	{
		JButton b = new JButton("OK");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Jugador hizo un movimiento");


				List<String> cardsplayed = view.getActualCardsPlayed();
				for(String s: cardsplayed)
				{
					char rank = s.charAt(0);
					
					char suit = s.charAt(1);
					Carta card = new Carta(rank, suit);
					gamelogic.addCardToPile(card);
					gamelogic.players[0].removeCardFromHand(card);
				}
				
				
				Move m = view.getPlayersLastMove();
				gamelogic.setLastMove(m);
				
				
				/*
				 * verificar si los oponentes quieren llamar mentiroso al jugador
				 */
				boolean cheatcalled = gamelogic.checkIfCaughtCheating(0, false); 
				if(!m.isValid() && cheatcalled)
				{
					view.updateCheatLabel("Fuiste atrapado mintiendo!! La pila de cartas completa fue anadida a tu mano");
				}
				else if( !m.isValid() && !cheatcalled){
					view.updateCheatLabel("Suerte! Nadie te acuso de mentir!");
				}
				else if(m.isValid() && cheatcalled){
					view.updateCheatLabel("Fuiste acusado falsamente de mentir! La pila de cartas fue anadida a la mano de tu oponente");
				}
				else{
					view.updateCheatLabel("Nadie intento acursarte de mentir");
				}
					
				gamelogic.getPossibleRanksToPlay(); //gets y set de los posible ranks a jugar
				incrementCurrPlayerAndExecuteTurn();
				
				view.setItsYourTurn(false);
				
				//update para refrescar la vista
				try {
					view.update();
				} catch (IOException e1) {
					System.out.println("Error consiguiendo la imagen de las cartas");
					e1.printStackTrace();
				}
			}
		});
		return b;
	}
	
	
	JButton getCallCheatButton()
	{
		JButton b = new JButton("Mentira!");
		
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Revisar si el llamado de mentira es valido y redistribuir las cartas de acuerdo a la mentira				
				gamelogic.checkIfCaughtCheating(currplayer, true);
				
				if(gamelogic.getLastMove().isValid())
				{
					view.updateCheatLabel("No, no estuvo mintiendo! La pila sera anadida a tu mano");
				}
				else{
					view.updateCheatLabel("Correcto! Estuvo mintiendo! La pila sera anadida a su mano");
				}
				
				incrementCurrPlayerAndExecuteTurn();
				
				//Actualizar las cartas del jugador en la vista
				try {
					view.update();
				} catch (IOException e1) {
					System.out.println("Error consiguiendo la imagen de las cartas");
					e1.printStackTrace();
				}
			}
		});
		return b;
	}
	
	
	JButton getNotCheatingButton()
	{
		JButton b = new JButton("No");
	
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				//Revisar si el llamado de mentira es valido y redistribuir las cartas de acuerdo a la mentira
				boolean cheatcalled = gamelogic.checkIfCaughtCheating(currplayer, false);
				
				if(cheatcalled && !gamelogic.getLastMove().isValid())
					view.updateCheatLabel("Player " + (currplayer+1) + " fue atrapado mintiendo! La pila sera anadida a su mano");
				
				incrementCurrPlayerAndExecuteTurn();
				
				//Actualizar las cartas del jugador en la vista
				try {
					view.update();
				} catch (IOException e1) {
					System.out.println("Error consiguiendo la imagen de las cartas");
					e1.printStackTrace();
				}
				
			}
		});
		
		return b;
	}
	
	
	private void incrementCurrPlayerAndExecuteTurn()
	{
		if(currplayer < 3)
		{
			/*
			 * aumentar currplayer y llamar playMove para ese jugador		 */
			currplayer++;			
			Move newmove = gamelogic.playMove(currplayer); 
			gamelogic.setLastMove(newmove);
			
			//output a la vista de cual fue el movimiento del jugador
			view.updateInfoLabel("Player: " + (currplayer +1) + "'s movio: " + newmove.toString());
			
			//hacer que aparezca el boton de mentira asi el jugador puede acusarlo
			view.displayCheatButtons(true);
		
		}	
		else //currplayer es 3 entonces el siguiente es 0 ( the user)
		{ 
			currplayer = 0;
			view.setItsYourTurn(true);
			view.updateInfoLabel("");
		}
		
		view.updateOtherInfor("" + gamelogic.getPlayerScores());
		
		int winner = gamelogic.someoneHasWon();
		if(winner > -1) view.updateOtherInfor("Juego terminado: " + gamelogic.players[winner].name + " se quedó sin cartas!" );
			
	}

}

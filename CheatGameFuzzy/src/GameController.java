import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;

/**
 * Reports actions on the view to the model 
 * Chain of events:
 * 
 * Player0 makes a move, hits OK
 * Other Players can call their bluff
 * Player1 makes a move
 * Player 0 sees Cheating/Not cheating button and must select one
 * If calls cheating: is given the pile, else player1 is given the pile
 * Other players can call player1s bluff
 * Player2 makes a move
 */
 
public class GameController {

	GameLogic gamelogic;
	MainGameFrame view;
	
	int currplayer = 0; //whose turn it is (players 0 to 3)

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
				System.out.println("Started game: " + "Controller' action performed was called");

				/*When the start button is clicked, we should initialize the game, so that the view can show players hand*/
				gamelogic.startGame();
				
				//when the game starts we need to display each players cards
				try {
					view.setItsYourTurn(true);
					view.update();
					view.setStartDisabled();
					
				} catch (IOException e1) {
					System.out.println("Error getting the images for the cards");
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
				System.out.println("User made a move");

				/*When the OK button is clicked:
				 *  update gamelogic.pile
				 *  we should get the players move,
				 *  save it to the game logic as "last move"
				 *  ask the other players if they want to call cheat.
				 *  call playMove() for player 2, display on the screen what he played.
				 */

				List<String> cardsplayed = view.getActualCardsPlayed();
				for(String s: cardsplayed)
				{
					char rank = s.charAt(0);
					
					char suit = s.charAt(1);
					Card card = new Card(rank, suit);
					gamelogic.addCardToPile(card);
					gamelogic.players[0].removeCardFromHand(card);
				}
				
				
				Move m = view.getPlayersLastMove();
				gamelogic.setLastMove(m);
				
				
				/*
				 * check if opponents want to call player's bluff
				 */
				boolean cheatcalled = gamelogic.checkIfCaughtCheating(0, false); 
				if(!m.isValid() && cheatcalled)
				{
					view.updateCheatLabel("You were caught cheating!! The whole pile has been added to your hand");
				}
				else if( !m.isValid() && !cheatcalled){
					view.updateCheatLabel("Lucky! No one accused you of cheating!");
				}
				else if(m.isValid() && cheatcalled){
					view.updateCheatLabel("You were falsely accused of cheating! The pile has been added to your opponent's hand");
				}
				else{
					view.updateCheatLabel("No one tried to accuse you of cheating.");
				}
					
				gamelogic.getPossibleRanksToPlay(); //gets and set the possible ranks to play
				incrementCurrPlayerAndExecuteTurn();
				
				view.setItsYourTurn(false);
				
				//call update to refresh the view
				try {
					view.update();
				} catch (IOException e1) {
					System.out.println("Error getting the images for the cards");
					e1.printStackTrace();
				}
			}
		});
		return b;
	}
	
	
	JButton getCallCheatButton()
	{
		JButton b = new JButton("Cheating!");
		
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//check whether cheat call is valid and redistribute cards accordingly
				gamelogic.checkIfCaughtCheating(currplayer, true);
				
				if(gamelogic.getLastMove().isValid())
				{
					view.updateCheatLabel("Nope, wasn't cheating! The pile has been added to your hand.");
				}
				else{
					view.updateCheatLabel("You're right! They were cheating! The pile has been added to their hand");
				}
				
				incrementCurrPlayerAndExecuteTurn();
				
				//update player's cards on the view
				try {
					view.update();
				} catch (IOException e1) {
					System.out.println("Error getting the images for the cards");
					e1.printStackTrace();
				}
			}
		});
		return b;
	}
	
	
	JButton getNotCheatingButton()
	{
		JButton b = new JButton("Nope");
	
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				//check whether cheat call is valid and redistribute cards accordingly
				boolean cheatcalled = gamelogic.checkIfCaughtCheating(currplayer, false);
				
				if(cheatcalled && !gamelogic.getLastMove().isValid())
					view.updateCheatLabel("Player " + (currplayer+1) + " was caught cheating! The pile has been added to their hand");
				
				incrementCurrPlayerAndExecuteTurn();
				
				//update player's cards on the view
				try {
					view.update();
				} catch (IOException e1) {
					System.out.println("Error getting the images for the cards");
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
			 * increment currplayer and call playMove for that player
			 */
			currplayer++;			
			Move newmove = gamelogic.playMove(currplayer); 
			gamelogic.setLastMove(newmove);
			
			//output to the view what the players move was
			view.updateInfoLabel("Player: " + (currplayer +1) + "'s move: " + newmove.toString());
			
			//make cheat button appear so that user could call the players bluff
			view.displayCheatButtons(true);
		
		}	
		else //currplayer is 3 so next player is 0 ( the user)
		{ 
			currplayer = 0;
			view.setItsYourTurn(true);
			view.updateInfoLabel("");
		}
		
		view.updateOtherInfor("" + gamelogic.getPlayerScores());
		
		int winner = gamelogic.someoneHasWon();
		if(winner > -1) view.updateOtherInfor("Game over: " + gamelogic.players[winner].name + " has finished all their cards!" );
			
	}

}

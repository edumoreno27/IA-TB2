import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ComboBoxModel;

public class GameLogic {

	private MainGameFrame mainview; 

	static final char[] possiblesuits = {'h', 'd', 's', 'c'};
	static final char[] possibleranks = {'2','3','4','5','6','7','8','9','T','J','Q','K','A'};

	Player[] players = new Player[4]; //assume always 4 players
	ArrayList<Carta> allcards = new ArrayList<Carta>();
	ArrayList<Carta> pile = new ArrayList<Carta>();

	List<String> currentranks = new ArrayList<String>();
	Move lastMove;

	/**constructor**/
	GameLogic()
	{
		//initialise the cards
		for(char rank: possibleranks){
			for(char suit: possiblesuits)
			{
				Carta card = new Carta(rank, suit);
				allcards.add(card);
			}
		}
	}

	/**getters **/
	public ArrayList<Carta> getAllcards() {
		return allcards;
	}

	public ArrayList<Carta> getPile() {
		return pile;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Move getLastMove() {//the play that was last claimed to be played
		return lastMove;
	}

	public String[] getPossibleRanksToPlay() {

		currentranks.clear();

		if(lastMove == null)
		{
			for(char r: possibleranks)
				currentranks.add(String.valueOf(r));
		}
		else
		{
			for(int i = 0; i<possibleranks.length;i++)
			{
				if(possibleranks[i] == lastMove.rank)
				{
					int nextRankid = i+1;
					int prevRankid = i-1;
					
					if(i == possibleranks.length -1){ 
						nextRankid = 0; 
					}
					else if(i == 0) {
						prevRankid = possibleranks.length -1;
					}
					
					currentranks.add(String.valueOf(possibleranks[prevRankid]));
					currentranks.add(String.valueOf(possibleranks[i]));
					currentranks.add(String.valueOf(possibleranks[nextRankid]));
					break;
				}
			}

		}
		return currentranks.toArray(new String[currentranks.size()]);
	}


	/**setters**/
	public void addView(MainGameFrame view) {
		mainview = view;
	}

	public void setLastMove(Move m) {
		lastMove = m;
	}

	public void addCardToPile(Carta c){
		pile.add(c);
	}

	public void startGame() {

		//init players, scores, players cards..
		players[0] = new Player("Player 1 (You)" , "human");
		players[1] = new Player("Player 2 (Risk-taker)", "risky");
		players[2] = new Player("Player 3 (Plays safe)", "safe");
		players[3] = new Player("Player 4: (Random)", "random" );

		Collections.shuffle(allcards);

		int i = 0;
		for(; i<allcards.size();)
		{
			players[0].addCardToHand(allcards.get(i));
			i++;
			players[1].addCardToHand(allcards.get(i));
			i++;
			players[2].addCardToHand(allcards.get(i));
			i++;
			players[3].addCardToHand(allcards.get(i));
			i++;
		}
	}

	/**pass the game state to the player and invoke getNextMove */
	public Move playMove(int playerid) {

		//let player i play a move
		Player player = players[playerid];

		//given the move that previous player just made and other information about the game, make a move
		int cardsinpile = pile.size();
		int[] playershands = new int[4];
		for(Player p: players)
		{
			playershands[p.getId()] = p.getHand().size();
		}

		/**
		 * Game state: last move, cards in pile, number of cards in each players hands, current ranks allowed
		 */
		Move m = player.getNextMove(lastMove, cardsinpile, playershands, currentranks); 
		for(Carta c: player.mylastmove)
		{
			pile.add(c);
		}
		
		return m;	
	}

	public boolean checkIfCaughtCheating(int playerid, boolean userCalledCheat) {

		//playerid is the id of the player who made the last move ( ie they take the pile if caught cheating)

		int calledcheat = -1; // if not -1, that means that some player called cheat
		boolean cheatingcalled  = false;

		if(userCalledCheat)
		{
			calledcheat = 0;
		}
		else
		{
			//for each AI opponent, see if they want to call cheat.
			for(int i = 1 ; i< players.length;i++)
			{
				if(i!= playerid)
				{
					if(players[i].wantToCallCheat(lastMove, players[playerid].getHand().size(), pile.size(), possibleranks))
					{
						calledcheat =i;
					}
				}		
			}
		}

		if(calledcheat > -1)
			cheatingcalled = true;
		
		if(cheatingcalled && !lastMove.isValid())
		{ 
			System.out.println("Player " + playerid + " was caught cheating by player " + calledcheat);
			pickUpCards(playerid);
		}
		else if (cheatingcalled && lastMove.isValid()){

			//player who called cheat has to take the whole pile

			System.out.println("Player " + playerid + " was falsely accused by player " + calledcheat);
			pickUpCards(calledcheat);
		}
		else{
			System.out.println("No one called cheat on player " + playerid);
		}
		return cheatingcalled;
	}

	public void pickUpCards(int player) {
		for(Carta card: pile)
		{
			players[player].addCardToHand(card);
		}
		pile.clear();
	}

	public String getPlayerScores() {
		
		String s = "Number of cards:  ";
		for(Player p: players){
			s+= " "+ p.name + ": " + p.getHand().size() + " ";
		}
		s+= " /In the pile: " + pile.size();
		return s;
	}

	public int someoneHasWon() {
		int winner = -1;
		for(Player p: players){
			if (p.getHand().size() <=0) winner = p.getId();
		}
		return winner;
	}
}

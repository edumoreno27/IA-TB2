import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ComboBoxModel;

public class GameLogic {

	private MainGameFrame mainview; 

	static final char[] possiblesuits = {'h', 'd', 's', 'c'};
	static final char[] possibleranks = {'2','3','4','5','6','7','8','9','T','J','Q','K','A'};

	Player[] players = new Player[4]; //asumimos que habran 4 jugadores
	ArrayList<Carta> allcards = new ArrayList<Carta>();
	ArrayList<Carta> pile = new ArrayList<Carta>();

	List<String> currentranks = new ArrayList<String>();
	Move lastMove;

	/**constructor**/
	GameLogic()
	{
		//inicializar las cartas
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

	public Move getLastMove() {//la jugada que fue reclamada por última vez para ser jugada
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

		//inicializar players, scores, players cards..
		players[0] = new Player("Player 1 (Tu)" , "humano");
		players[1] = new Player("Player 2 (Toma Riesgos)", "riesgoso");
		players[2] = new Player("Player 3 (Juego seguro)", "seguro");
		players[3] = new Player("Player 4: (Aleatorio)", "aleatorio" );

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

		//dejar que player i haga un movimiento
		Player player = players[playerid];

		//dado el movimiento que el jugador anterior acaba de hacer y otra información sobre el juego, haga un movimiento
		int cardsinpile = pile.size();
		int[] playershands = new int[4];
		for(Player p: players)
		{
			playershands[p.getId()] = p.getHand().size();
		}

		Move m = player.getNextMove(lastMove, cardsinpile, playershands, currentranks); 
		for(Carta c: player.mylastmove)
		{
			pile.add(c);
		}
		
		return m;	
	}

	public boolean checkIfCaughtCheating(int playerid, boolean userCalledCheat) {

		//playerid es el id del player que hizo el ultimo movimiento ( ie ellos toman la pila si son atrapados mintiendo)

		int calledcheat = -1; // si no es -1, esto significa que algun jugador llamo mentiroso a otro
		boolean cheatingcalled  = false;

		if(userCalledCheat)
		{
			calledcheat = 0;
		}
		else
		{
			//para cada oponente de AI, ver si ellos llaman "mentiroso"
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
			System.out.println("Player " + playerid + " fue atrapado mintiendo por player " + calledcheat);
			//reice las cartas q puso sobre la mesa por mentiroso
			rollbackTurn(playerid);
			//reicbe cartas acumuladas en pile
			pickUpCards(playerid);
		}
		else if (cheatingcalled && lastMove.isValid()){

			//juegador que llame mentiroso tendra que coger todas las cartas de la pila

			System.out.println("Player " + playerid + " fue falsamente acusado por player " + calledcheat);
			//jugador que acusop reicbe aumneot de cartas
			pickUpCards(calledcheat);
			
		}
		else{
			System.out.println("Nadie llamo mentiroso al player " + playerid);
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

	
	public void rollbackTurn(int player) {
		for(Carta card: players[player].retriveCardsFromLastMove())
		{
			players[player].addCardToHand(card);	
		}
	}
	

	public String getPlayerScores() {
		
		String s = "Numero de cartas:  ";
		for(Player p: players){
			s+= " "+ p.name + ": " + p.getHand().size() + " ";
		}
		s+= " /En la pila: " + pile.size();
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

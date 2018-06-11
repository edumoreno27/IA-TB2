import java.util.*;

public class Player {

	static int currplayerid = 0; //player ids start at 0

	String name;
	private int id;
	//HashSet<Card> hand = new HashSet<Card>(); //score is the number of cards in hand
	List<Card> hand = new ArrayList<Card>();
	List<Card> mylastmove = new ArrayList<Card>();
	String strategy; // possible values: "human", "random", "risky", "safe"
	
	Player(String name, String strategy) 
	{
		this.name = name;
		this.strategy = strategy;
		id = currplayerid;
		currplayerid++;
	}
	public List<Card> getHand() { return hand;}
	public String getStrategy() {return strategy; }
	public int getId() { return id;}
	
	public void addCardToHand(Card card) {
		hand.add(card);
	}

	public void removeCardFromHand(Card card) {
		hand.remove(card);
	}
	
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	private void addCardsToLastMove(Card c) {
		mylastmove.clear();
		mylastmove.add(c);
	}

	/*
	 * Method used by AI players to determine if they want to call cheat
	 * @return true if calling cheat on lastMove, false if letting it pass
	 */
	public boolean wantToCallCheat(Move lastMove, int cardsInOpponentsHand, int pilesize, char[] allranks){

		// input: pilesize, size of each player's hands, currentranks to choose from.
		// also given the players move (ignore the isValid parameter)
		/* if the number of cards of the rank known is greater than 4, call cheat*/

		int numInHand = 0;//count how many cards player has of the last move rank.
		for(Card c: hand )
		{
			if(c.rank == lastMove.rank)
			{
				numInHand ++;
			}
		}
		
		int numInMove = lastMove.numcards;
		int totalKnown = numInMove + numInHand;
		if(totalKnown > 4)  return true;

		/* if opponent has put down all of their cards, automatically call cheat because they will win*/
		if(cardsInOpponentsHand == 0) return true;

		//check Rules
		double is_cheating = Rules.checkRules(pilesize, cardsInOpponentsHand, this.hand.size());
		
		/*de-fuzzification*/
		/* for the safe player the membership in these sets should be greater than 80%
		 *  for the risk-taker it is 50% */
		double percentrule;
		if(strategy == "safe")
		{
			percentrule = 0.8;
		}
		else //(strategy == "risk-taker")
		{
			percentrule = 0.5;
		}
		
		if(is_cheating >= percentrule)
		{
			return true;
		}
		return false;
	}

	/***
	 * In order to decide on a move, the player needs the state of the game
	 * 
	 * @param lastMove
	 * @param cardsinpile
	 * @param playershands
	 * @param currentranks - the ranks that player is allowed to put down 
	 * @return nextmove
	 * 
	 * It returns a move object -- consisting of the number of cards played, rank claimed and whether it is a valid move
	 */
	Move getNextMove(Move lastMove, int cardsinpile, int[] playershands, List<String> currentranks)
	{
		//figure out the best move to make based on the current game state and then return it to the caller
		boolean isValidMove = false;
		char cardrank = 0;
		int numcards = 0;

		printPlayerHand();

		Map<Character,Integer> numPerRank = getNumPerRank(currentranks);
		System.out.println(" --Num cards per allowed rank: " + numPerRank); 

		Random rand = new Random();
		//choose a random card rank to play
		char randomrank = currentranks.get(rand.nextInt(currentranks.size()-1)).charAt(0);

		if(strategy != null) // == "random")
		{
			cardrank = randomrank;
			if (numPerRank.containsKey((Character)cardrank))
			{
				/*get all the cards in player's hand with that rank*/		
				numcards = numPerRank.get((Character)cardrank);
				Card[] cards = new Card[numcards];

				int i =  0;
				for(Card c: hand){
					if(c.getRank() == cardrank){
						cards[i] = c;
						i++;
					}
				}
				for(Card c: cards){
					this.removeCardFromHand(c);
					this.addCardsToLastMove(c);
				}

				isValidMove = true;
			}
			else/* the rank chosen is not in the player's hand*/
			{ 
				//choose from 1 to 4 random cards to play
				int randomNum;
				if(strategy == "random") 
					randomNum = rand.nextInt(4) + 1;
				else if(strategy == "safe")
					randomNum = rand.nextInt(2) + 1;
				else //strategy == risky
					randomNum = rand.nextInt(4) + 3;
				
				Card[] cards = new Card[randomNum];

				for(int i =0; i<randomNum; i++)
				{
					int randomid = rand.nextInt(hand.size()-1) ;
					cards[i] = hand.get(randomid);
				}
				for(Card c: cards){
					this.removeCardFromHand(c);
					this.addCardsToLastMove(c);
				}
				numcards = randomNum;
				isValidMove = false;
			}
		}
		Move move = new Move(numcards, cardrank, isValidMove);
		System.out.println( name + " makes move: " + move);

		return move;
	}

	private void printPlayerHand() {
		System.out.print(name+ ": " + hand.toString());
	}
	
	private Map<Character, Integer> getNumPerRank(char[] ranks) {
		Map<Character, Integer> numPerRank = new HashMap<Character,Integer>();	
		for(char rank: ranks)
		{
			//count how many cards they have of each rank.
			for(Card c: hand ){
				if(c.rank == rank){
					if(numPerRank.containsKey(rank))
					{
						int n = numPerRank.get(rank);
						numPerRank.put((Character)rank, ++n);
					}
					else{
						numPerRank.put((Character)rank, 1);
					}
				}
			}
		}
		return numPerRank;
	}

	public Map<Character, Integer> getNumPerRank(List<String> currentranks){
		char[] ranks = new char[currentranks.size()];
		int i=0;
		for(String r: currentranks)
		{
			ranks[i] = r.charAt(0);
			i++;
		}
		return getNumPerRank(ranks);
	}
}

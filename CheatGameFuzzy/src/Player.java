import java.util.*;

public class Player {

	static int currplayerid = 0; //id de player comienza en 0 0

	String name;
	private int id;
	
	List<Carta> hand = new ArrayList<Carta>();
	List<Carta> handToRetrive = new ArrayList<Carta>();
	List<Carta> mylastmove = new ArrayList<Carta>();
	String strategy; // posibles valores: "humano", "aleatorio", "riesgoso", "seguro"
	
	Player(String name, String strategy) 
	{
		this.name = name;
		this.strategy = strategy;
		id = currplayerid;
		currplayerid++;
	}
	public List<Carta> getHand() { return hand;}
	public String getStrategy() {return strategy; }
	public int getId() { return id;}
	
	public void addCardToHand(Carta card) {
		hand.add(card);
	}

	public void removeCardFromHand(Carta card) {
		hand.remove(card);
	}
	
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setHand(List<Carta> hand) {
		this.hand = hand;
	}

	private void addCardsToLastMove(Carta c) {
		mylastmove.clear();
		mylastmove.add(c);
	}

	/*
	 * Metodo usado por los jugadores AI para determinar si ellos quieren llamar mentiroso a otro jugador
	 * @return true si llama mentiroso en su lastMove, false si lo deja pasar
	 */
	public boolean wantToCallCheat(Move lastMove, int cardsInOpponentsHand, int pilesize, char[] allranks){

		// input: pilesize, tamaño de la mano de cada oponente, currentranks para escoger
		
		//Cuenta cuantas cartas el jugador tiene de su ultimo movimiento rank
		int numInHand = 0;
		for(Carta c: hand )
		{
			if(c.rank == lastMove.rank)
			{
				numInHand ++;
			}
		}
		
		int numInMove = lastMove.numcards;
		int totalKnown = numInMove + numInHand;
		if(totalKnown > 4)  return true;

		/* Si el oponente se quedo sin cartas, automaticamente llamarlo mentiroso porque podria ganar*/
		if(cardsInOpponentsHand == 0) return true;

		//verificar reglas
		double is_cheating = Rules.checkRules(pilesize, cardsInOpponentsHand, this.hand.size());
		System.out.println("la probabilidad de que un jugador mienta el oponente es de :: " + is_cheating );
		
		/*de-fuzzification*/
		/* para el jugador seguro, la funcion membresía en estos conjuntos debe ser mayor al 80%
		 *  para el seguro es de  50% */
		double percentrule;
		if(strategy == "seguro")
		{
			percentrule = 0.7;
		}
		else 
		{
			percentrule = 0.5;
		}
		
		if(is_cheating >= percentrule)
		{
			return true;
		}
		return false;
	}

	Move getNextMove(Move lastMove, int cardsinpile, int[] playershands, List<String> currentranks)
	{
		//Encontrar el mejor movimiento basado en el estado del juego y retornar estos al caller
		
		boolean isValidMove = false;
		
		char cardrank = 0;
		int numcards = 0;

		printPlayerHand();

		Map<Character,Integer> numPerRank = getNumPerRank(currentranks);
		System.out.println(" --Num cards per allowed rank: " + numPerRank); 

		Random rand = new Random();
		
		
		//eligiendo un rank random para escoger
		char randomrank = currentranks.get(rand.nextInt(currentranks.size()-1)).charAt(0);

		if(strategy != null) 
		{
			cardrank = randomrank;
			if (numPerRank.containsKey((Character)cardrank))
			{
				/*obetener todas las cartas de la mano del jugador con ese rank*/		
				numcards = numPerRank.get((Character)cardrank);
				Carta[] cards = new Carta[numcards];

				int i =  0;
				for(Carta c: hand){
					if(c.getRank() == cardrank){
						
						cards[i] = c;
						handToRetrive.add(cards[i]);
						
						i++;
					}
				}
				for(Carta c: cards){
					this.removeCardFromHand(c);
					this.addCardsToLastMove(c);
				}

				isValidMove = true;
			}
			else/* El rank escogio no esta en la mano del jugador*/
			{ 
				//elegir de 1 a 4 cartas aleatorias para jugar
				int randomNum;
				if(strategy == "aleatorio") 
					randomNum = rand.nextInt(4) + 1;
				else if(strategy == "seguro")
					randomNum = rand.nextInt(2) + 1;
				else 
					randomNum = rand.nextInt(4) + 3;
				
				Carta[] cards = new Carta[randomNum];

				for(int i =0; i<randomNum; i++)
				{
					int randomid = rand.nextInt(hand.size()-1) ;
					cards[i] = hand.get(randomid);
				}
				for(Carta c: cards){
					this.removeCardFromHand(c);
					this.addCardsToLastMove(c);
				}
				numcards = randomNum;
				isValidMove = false;
			}
		}
		Move move = new Move(numcards, cardrank, isValidMove);
		System.out.println( name + " hizo el movimiento: " + move);

		return move;
	}

	private void printPlayerHand() {
		System.out.print(name+ ": " + hand.toString());
	}
	
	private Map<Character, Integer> getNumPerRank(char[] ranks) {
		Map<Character, Integer> numPerRank = new HashMap<Character,Integer>();	
		for(char rank: ranks)
		{
			//contar cuantas cartas tiene cada rank
			for(Carta c: hand ){
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
	
	public List<Carta>retriveCardsFromLastMove(){
		return handToRetrive;
	}
}

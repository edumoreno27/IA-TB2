
public class Move {
	
	int numcards;
	char rank; 
	boolean isValid;
	
	public int getNumcards() {
		return numcards;
	}

	public void setNumcards(int numcards) {
		this.numcards = numcards;
	}

	public char getRank() {
		return rank;
	}

	public void setRank(char rank) {
		this.rank = rank;
	}

	Move(int numcards, char rank, boolean isValid)
	{
		this.numcards= numcards;
		this.rank = rank;
		this.isValid = isValid;
	}

	public boolean isValid() {
		return isValid;
	}
	
	public String toString()
	{
		return "" + numcards + " card(s) of rank: " + String.valueOf(rank); // + " -- isValid? " + isValid ;
	}
	
}

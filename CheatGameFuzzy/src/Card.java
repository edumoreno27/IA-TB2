import javax.swing.ImageIcon;

public class Card {

	char rank; //A,2,3,4,5,6,7,8,9,T,J,Q,K ( use T for 10)
	char suit; //h,d,c,s (hearts, diamonds, clubs, spades)
	
	ImageIcon cardFaceImage; 
	String imageFilename;
	
	public Card(char rank, char suit){
		this.rank = rank;
		this.suit = suit;
		
		imageFilename = "cards/card-images/" +rank + suit + ".gif";
	}
	
	public String getImageFilename(){
		return imageFilename;
	}

	public ImageIcon getCardFaceImage() {
		return cardFaceImage;
	}

	public void setCardFaceImage(ImageIcon cardFaceImage) {
		this.cardFaceImage = cardFaceImage;
	}

	public char getRank() {
		return rank;
	}

	public char getSuit() {
		return suit;
	}
	
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Card card = (Card) obj;
        return((suit == card.suit) && (rank == card.rank));
    }

    public String toString(){
    	return "" +rank+ suit ;
    }
}

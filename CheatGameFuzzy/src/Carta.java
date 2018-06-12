import javax.swing.ImageIcon;

public class Carta {

	char rank; //A,2,3,4,5,6,7,8,9,T,J,Q,K ( se usa T para 10)
	char suit; //h,d,c,s (hearts, diamonds, clubs, spades)
	
	ImageIcon imagenCarta; 
	String archivoImagen;
	
	public Carta(char rank, char suit){
		this.rank = rank;
		this.suit = suit;
		
		archivoImagen = "cards/card-images/" +rank + suit + ".gif";
	}
	
	public String getImageFilename(){
		return archivoImagen;
	}

	public ImageIcon getCardFaceImage() {
		return imagenCarta;
	}

	public void setCardFaceImage(ImageIcon cardFaceImage) {
		this.imagenCarta = cardFaceImage;
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

        Carta card = (Carta) obj;
        return((suit == card.suit) && (rank == card.rank));
    }

    public String toString(){
    	return "" +rank+ suit ;
    }
}

import java.util.ArrayList;

public class ObteniendoCentroide {
	int pilesize;
	int cardsInOpponentsHand;
	int cardsInMyHand;
	ArrayList<Punto> a1;
	ArrayList<Punto> a2;
	ArrayList<Punto> a3;
	
	public ObteniendoCentroide(int a,int b,int c) {
		this.pilesize=a;
		this.cardsInOpponentsHand=b;
		this.cardsInMyHand=c;
	}
	
	double getCentroide(int pilesize,	int cardsInOpponentsHand,	int cardsInMyHand) {
		double centroide=0.0;
		Graficas graphics=new Graficas();
		if(pilesize >=0 && pilesize <=12)
		{
			a1=graphics.getArreglo1();
		}
		else {
			a1=graphics.getArreglo2();
		}
		if( cardsInOpponentsHand >=0 && cardsInOpponentsHand <=8 )
		{ 
			a2=graphics.getArreglo3();
		}
		else {
			a2=graphics.getArreglo4();
		}
		a3=graphics.getArreglo5();
		
		
		
		return centroide;
		
	}
	
	public int getPilesize() {
		return pilesize;
	}
	public void setPilesize(int pilesize) {
		this.pilesize = pilesize;
	}
	public int getCardsInOpponentsHand() {
		return cardsInOpponentsHand;
	}
	public void setCardsInOpponentsHand(int cardsInOpponentsHand) {
		this.cardsInOpponentsHand = cardsInOpponentsHand;
	}
	public int getCardsInMyHand() {
		return cardsInMyHand;
	}
	public void setCardsInMyHand(int cardsInMyHand) {
		this.cardsInMyHand = cardsInMyHand;
	}
	
}

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Graficas {
	ArrayList <Punto> arreglo1;
	ArrayList <Punto> arreglo2;
	ArrayList <Punto> arreglo3;
	ArrayList <Punto> arreglo4;
	ArrayList <Punto> arreglo5;
	ArrayList <Punto> arreglo6;
	int pilesize;
	int oponentHand;
	int myHand;
	public ArrayList<Punto> getArreglo6() {
		return arreglo6;
	}

	public void setArreglo6(ArrayList<Punto> arreglo6) {
		this.arreglo6 = arreglo6;
	}

	int[] valoresRecta = new int[2];
	
	
	public Graficas(	int a,int b,	int c) {
		//vlaord e la recta reusltante de ocmbinar linea d eprobabilidad ocn grafica 
		//X del primer punto
		valoresRecta[0] = 0;
		//X del seugn punto
		valoresRecta[1] = 0;
		//Y de los puntos
		
		
		pilesize=a;
		oponentHand=b;
		myHand=c;
		
		//Relacionados al tamaño de la fila
		//Pila corta
		arreglo1= new ArrayList<Punto>();
		//Pila larga
		arreglo2= new ArrayList<Punto>();
		//Cartas en la mano oponente
		//Pocas cartas
		arreglo3= new ArrayList<Punto>();
		//Muchas cartas
		arreglo4= new ArrayList<Punto>();
		//Cartas en mi mano
		//Pocas cartas
		arreglo5= new ArrayList<Punto>();
		//Cheat
		arreglo6=new ArrayList<Punto>();
		
		for(int i=0;i<49;i++)
		{
			arreglo1.add(new Punto(0,12,i));
		}
		
		for(int i=0;i<49;i++)
		{
			arreglo2.add(new Punto(22,52,i));
		}
		for(int i=0;i<50;i++)
		{
			arreglo3.add(new Punto(0,8,i));
		}
		for(int i=0;i<50;i++)
		{
			arreglo4.add(new Punto(18,52,i));
		}
		for(int i=0;i<50;i++)
		{
			arreglo5.add(new Punto(0,8,i));
		}
		for(int i=0;i<101;i++)
		{
			arreglo6.add(new Punto(0,100,i));
		}
	}

	public ArrayList<Punto> getArreglo1() {
		return arreglo1;
	}

	public void setArreglo1(ArrayList<Punto> arreglo1) {
		this.arreglo1 = arreglo1;
	}

	public ArrayList<Punto> getArreglo2() {
		return arreglo2;
	}

	public void setArreglo2(ArrayList<Punto> arreglo2) {
		this.arreglo2 = arreglo2;
	}

	public ArrayList<Punto> getArreglo3() {
		return arreglo3;
	}

	public void setArreglo3(ArrayList<Punto> arreglo3) {
		this.arreglo3 = arreglo3;
	}

	public ArrayList<Punto> getArreglo4() {
		return arreglo4;
	}

	public void setArreglo4(ArrayList<Punto> arreglo4) {
		this.arreglo4 = arreglo4;
	}

	public ArrayList<Punto> getArreglo5() {
		return arreglo5;
	}

	public void setArreglo5(ArrayList<Punto> arreglo5) {
		this.arreglo5 = arreglo5;
	}
	
	//Funciones para obneter valores de las recta de interseccion entre grafica y probabilidad (input)
	
	public int[] getValuesRecta1(){
		int []valores = new int[]{0,0};
		double prob1=arreglo1.get(pilesize).getY();
		boolean ap1=true;
		boolean ap2=true;
		for(int i = 0 ;i<101; i++){
			
			if(arreglo6.get(i).getY() > prob1 && ap1==true && i<=50)
			{
				valoresRecta[0]=i;
				ap1=false;
			}
			if(arreglo6.get(i).getY() < prob1 && ap2==true && i>50)
			{
				valoresRecta[1]=i-1;
				ap2=false;
			}
		
		}
		
		valores[0] = valoresRecta[0];
		valores[1] = valoresRecta[1];
		valoresRecta[0] = 0;
		valoresRecta[1] = 0;
		
		//reotnr los indices d elos puntos y su funcion de pertenecia
		return valores;
	}

	
	public int[] getValuesRecta2(){
		int []valores = new int[]{0,0};
		double prob1=arreglo2.get(pilesize).getY();
		boolean ap1=true;
		boolean ap2=true;
		for(int i = 0 ;i<101; i++){
			
			if(arreglo6.get(i).getY() > prob1 && ap1==true && i<=50)
			{
				valoresRecta[0]=i;
				ap1=false;
			}
			if(arreglo6.get(i).getY() < prob1 && ap2==true && i>50)
			{
				valoresRecta[1]=i-1;
				ap2=false;
			}
		
		}
		
		valores[0] = valoresRecta[0];
		valores[1] = valoresRecta[1];
		valoresRecta[0] = 0;
		valoresRecta[1] = 0;
		
		//reotnr los indices d elos puntos y su funcion de pertenecia
		return valores;
	}
	


	
	public int[] getValuesRecta3(){
		int []valores = new int[]{0,0};
		double prob1=arreglo3.get(pilesize).getY();
		boolean ap1=true;
		boolean ap2=true;
		for(int i = 0 ;i<101; i++){
			
			if(arreglo6.get(i).getY() > prob1 && ap1==true && i<=50)
			{
				valoresRecta[0]=i;
				ap1=false;
			}
			if(arreglo6.get(i).getY() < prob1 && ap2==true && i>50)
			{
				valoresRecta[1]=i-1;
				ap2=false;
			}
		
		}
		
		valores[0] = valoresRecta[0];
		valores[1] = valoresRecta[1];
		valoresRecta[0] = 0;
		valoresRecta[1] = 0;
		
		//reotnr los indices d elos puntos y su funcion de pertenecia
		return valores;
	}
	
	public int[] getValuesRecta4(){
		int []valores = new int[]{0,0};
		double prob1=arreglo4.get(pilesize).getY();
		boolean ap1=true;
		boolean ap2=true;
		for(int i = 0 ;i<101; i++){
			
			if(arreglo6.get(i).getY() > prob1 && ap1==true && i<=50)
			{
				valoresRecta[0]=i;
				ap1=false;
			}
			if(arreglo6.get(i).getY() < prob1 && ap2==true && i>50)
			{
				valoresRecta[1]=i-1;
				ap2=false;
			}
		
		}
		
		valores[0] = valoresRecta[0];
		valores[1] = valoresRecta[1];
		valoresRecta[0] = 0;
		valoresRecta[1] = 0;
		
		//reotnr los indices d elos puntos y su funcion de pertenecia
		return valores;
	}

	public int[] getValuesRecta5(){
		int []valores = new int[]{0,0};
		double prob1=arreglo5.get(pilesize).getY();
		boolean ap1=true;
		boolean ap2=true;
		for(int i = 0 ;i<101; i++){
			
			if(arreglo6.get(i).getY() > prob1 && ap1==true && i<=50)
			{
				valoresRecta[0]=i;
				ap1=false;
			}
			if(arreglo6.get(i).getY() < prob1 && ap2==true && i>50)
			{
				valoresRecta[1]=i-1;
				ap2=false;
			}
		
		}
		
		valores[0] = valoresRecta[0];
		valores[1] = valoresRecta[1];
		valoresRecta[0] = 0;
		valoresRecta[1] = 0;
		
		//reotnr los indices d elos puntos y su funcion de pertenecia
		return valores;
	}

}

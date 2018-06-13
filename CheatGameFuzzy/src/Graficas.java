import java.util.ArrayList;

public class Graficas {
	ArrayList <Punto> arreglo1;
	ArrayList <Punto> arreglo2;
	ArrayList <Punto> arreglo3;
	ArrayList <Punto> arreglo4;
	ArrayList <Punto> arreglo5;
	
	public Graficas() {
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
			arreglo4.add(new Punto(0,8,i));
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

}

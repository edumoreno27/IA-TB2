
public class Punto {
	int x;
	double y;
	int a,b;
	public Punto(int a,int b,int x) {
		this.x=x;
		this.y=FuzzyFunctions.triangle(a,b, x);
	}
	int getX() {return x;}
	double getY() {return y;}
	int getA() {return a;}
	int getB() {return b;}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setA(int a) {
		this.a = a;
	}
	public void setB(int b) {
		this.b = b;
	}
	
}

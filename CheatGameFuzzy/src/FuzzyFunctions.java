import java.util.*;


public class FuzzyFunctions {

	//Rango ( para obtener el rango de las posibles entradas)
	public static List<Integer> range(int start, int stop, int step){
		List<Integer> result = new ArrayList<Integer>();
		result.add(start);
		int current = start;
		while(current < stop){
			current += step;
			result.add(current);
		}
		return result;
	}
	
	//up
	public static double up(int a, int b, int x){
		if(x< a)
			return 0.0;
		else if(x < b)
			return (x-a)/(b-a);
		else return 1.0;
	}

	//down
	public static double down(int a, int b, int x){
		return 1.0 -up(a, b, x);
	}
	
	//triangle
	public static double triangle(int a, int b, int x){
		double mean = (double)(a+b)/2;
		double first = (double)(x-a)/(mean - a);
		double second = (double)(b-x)/(b-mean);
		return Math.max(Math.min(first, second), 0.0);
	}
	
	//trapezoid
	public static double trapezoid(int a, int b, int c, int d, int x){
		double first = (double)(x-a)/(b-a);
		double second = (double)(d-x)/(d-c);
		return Math.max(Math.min(first,Math.min(1.0, second)), 0.0);
	}
		
	//hedges
	public static double hedge(double power, double membershipValue){
		if (power == 0)
			return 0.0;
		else
			return Math.pow(membershipValue, power);
	}
}

import java.util.ArrayList;
import java.util.Arrays;

public class Rules {
	
	
	public static double checkRules(int pilesize, int cardsInOpponentsHand, int cardsInMyHand)
	{
		
		Double probabilidadDeMentira = 0.0;
		Graficas graficas = new Graficas(pilesize,cardsInOpponentsHand,cardsInMyHand);
		//
		//Dado las entradas, checkRules usa el significado de maximo para desfuzificar
		//Punto(valor de X, funcion de menbresia(Y))
		
		//rule 1: If la pila es pequeña then Miente
		Punto puntoNaranja1 = new Punto(0,12,pilesize);
		//obtenbemos valores de la recta
		int valoresRecta1[] = graficas.getValuesRecta1();
		System.out.println("valores posicion dle punto 1 : " + puntoNaranja1.x + " : y"+ puntoNaranja1.y );
		System.out.println("valores para la recta 1 : X1: " + valoresRecta1[0] + "  : X2 : "+ valoresRecta1[1] );
		
		//rule 2: If la pila es larga then No miente
		Punto puntoNaranja2 = new Punto(22,52,pilesize);
		int valoresRecta2[] = graficas.getValuesRecta2();
		System.out.println("valores posicion dle punto 2 : " + puntoNaranja2.x + " : y"+ puntoNaranja2.y );
		System.out.println("valores para la recta 2 : x: " + valoresRecta2[0] + "  : y : "+ valoresRecta2[1] );

		//rule 3: If la mano del oponente es pequeña then Miente
		Punto puntoNaranja3 = new Punto(0,8,cardsInOpponentsHand);
		int valoresRecta3[] = graficas.getValuesRecta3();
		System.out.println("valores posicion dle punto 3 : " + puntoNaranja3.x + " : y"+ puntoNaranja3.y );
		System.out.println("valores para la recta 3 : x: " + valoresRecta3[0] + "  : y : "+ valoresRecta3[1] );
		
		//rule 4: If la mano del oponente es grande then No miente
		Punto puntoNaranja4 = new Punto(18,52,cardsInOpponentsHand);
		int valoresRecta4[] = graficas.getValuesRecta4();
		System.out.println("valores posicion dle punto 4 : " + puntoNaranja4.x + " : y"+ puntoNaranja4.y );
		System.out.println("valores para la recta 4 : x: " + valoresRecta4[0] + "  : y : "+ valoresRecta4[1] );
		
		//rule 5: If mi mano es pequeña (Ganare pronto) then No miente
		Punto puntoNaranja5 = new Punto(0,8,cardsInMyHand);
		int valoresRecta5[] = graficas.getValuesRecta5();
		System.out.println("valores posicion dle punto 5 : " + puntoNaranja5.x + " : y"+ puntoNaranja5.y );
		System.out.println("valores para la recta 5 : x: " + valoresRecta5[0] + "  : y : "+ valoresRecta5[1] );
		
		//CALCULO DE CENTROIDE
		double []valores = new double[100];
		for(int i = 0; i < 100 ; i++){
			valores[i] = 0.0;
		}

		
		
		//para recta 1
		System.out.println(" para la recta 1 : ");
		for(int i = valoresRecta1[0]; i <= valoresRecta1[1] ; i++){
			//cojemos los valores comprneidods en la recta
			valores[i]= puntoNaranja1.y;
			System.out.println(" para la recta 1 :(valor) ::  "+puntoNaranja1.y + " valor :: "+i);
		}

		//para recta 2
		System.out.println(" para la recta 2 : ");
		for(int i = valoresRecta2[0]; i <= valoresRecta2[1] ; i++){
			//cojemos los valores comprneidods en la recta
			if(valores[i] != 0){
			valores[i]= puntoNaranja2.y;
			}
			System.out.println(" para la recta 2 :(valor) ::  "+puntoNaranja2.y+ " valor :: "+i);
		}

		//para recta 3
		System.out.println(" para la recta 3 : ");
		for(int i = valoresRecta3[0]; i <= valoresRecta3[1] ; i++){
			//cojemos los valores comprneidods en la recta
			if(valores[i] != 0){
			valores[i]= puntoNaranja3.y;
			}
			System.out.println(" para la recta 3 :(valor) ::  "+puntoNaranja3.y+ " valor :: "+i);
		}
		
		//para recta 4
		System.out.println(" para la recta 4 : ");
		for(int i = valoresRecta4[0]; i <= valoresRecta4[1] ; i++){
			//cojemos los valores comprneidods en la recta
			if(valores[i] != 0){
			valores[i]= puntoNaranja4.y;
			}
			System.out.println(" para la recta 4 :(valor) ::  "+puntoNaranja4.y+ " valor :: "+i);
		}

		//para recta 5
		System.out.println(" para la recta 5 : ");
		for(int i = valoresRecta5[0]; i <= valoresRecta5[1] ; i++){
			//cojemos los valores comprendidos en la recta
			if(valores[i] != 0){
			valores[i]= puntoNaranja5.y;
			}
			System.out.println(" para la recta 5 :(valor) ::  "+puntoNaranja5.y+ " valor :: "+i);
		}
		
		//calculasm el cenotride con sumas y multiplicaciones
		double dividendo = 0.0;
		double divisor = 0.0;
		for(int i = 0; i < 100 ; i++){
			System.out.println(" para la suma :(valor)  "+ i +" ::  "+valores[i]);
			
			divisor = divisor + valores[i];
			dividendo = dividendo + (valores[i]*(i+1));
		}

		System.out.println("dividendo : " + dividendo );
		System.out.println("divisor : " + divisor );
		
		probabilidadDeMentira = dividendo/divisor;
		
		//retorna el valor de probabildiad de q el opoente mienta
		return probabilidadDeMentira/(100.0);
		

	}

}


//forma antigua 

/*
 * 
		//
		//Dado las entradas, checkRules usa el significado de maximo para desfuzificar
		
		
		//rule 1: If la pila es pequeña then Miente
		Punto puntoNaranja1 = new Punto();
		double rule1 = FuzzyFunctions.triangle(0, 12,pilesize);   
		
		
		//rule 2: If la pila es larga then No miente
		double rule2 = 1- FuzzyFunctions.triangle(22, 52, pilesize);

		
		//rule 3: If la mano del oponente es pequeña then Miente
		double rule3= FuzzyFunctions.triangle(0, 8,  cardsInOpponentsHand);
		
		
		//rule 4: If la mano del oponente es grande then No miente
		double rule4 = 1 - FuzzyFunctions.triangle(18, 52, cardsInOpponentsHand); 
		
		
		
		//rule 5: If mi mano es pequeña (Ganare pronto) then No miente
		double rule5 = FuzzyFunctions.triangle(0, 8,  cardsInMyHand);

		
		//este método usa la regla más aplicable como el valor de verdad
		return Math.max(Math.max(Math.max((Math.max(rule1, rule2)),rule3), rule4), rule5);

 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
import java.util.ArrayList;

public class Rules {
	
	
	public static double checkRules(int pilesize, int cardsInOpponentsHand, int cardsInMyHand)
	{
		//
		//Dado las entradas, checkRules usa el significado de maximo para desfuzificar
		//rule 1: If la pila es pequeña then Miente
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

	}
	

}


public class Rules {
	
	
	public static double checkRules(int pilesize, int cardsInOpponentsHand, int cardsInMyHand)
	{
		//given input, check rules, use mean of max to defuzzify.
		
		//rule 1: If shortPile then Cheating
		double rule1 = FuzzyFunctions.triangle(0, 12,pilesize);   
		//double mediumPile = FuzzyFunctions.triangle(10, 25, pilesize);
		
		//rule 2: If largePile then NOT cheating
		double rule2 = 1- FuzzyFunctions.triangle(22, 52, pilesize);

		
		//rule 3: If smallOpponentHand then Cheating
		double rule3= FuzzyFunctions.triangle(0, 8,  cardsInOpponentsHand);
		
		//rule 4: If largeOpponentHand then NOT Cheating
		double rule4 = 1 - FuzzyFunctions.triangle(18, 52, cardsInOpponentsHand); //52 is the total number of cards in deck

		//rule 5: If my hand is small ( I may win soon) then NOT Cheating
		double rule5 = FuzzyFunctions.triangle(0, 8,  cardsInMyHand);
		
		
		return Math.max(Math.max(Math.max((Math.max(rule1, rule2)),rule3), rule4), rule5);  //this method uses the most applicable rule as the truth value

	}
	

}

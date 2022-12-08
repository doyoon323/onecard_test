
public interface OneCardGameInterface {
	
	/*
	 * checks if human_player's turn
	 */
	public boolean isPlayerTurn();
	
	/*
	 * put player's card (suit, rank) onto thrown deck
	 * returns if successful
	 */
	public boolean putCard(String suit, int rank);
	
	/* 
	 * returns top card on thrown deck
	 */
	public Card topCard();
	
	/*
	 * returns human player's cards
	 */
	public Card[] playerCards();
	
	/*
	 * returns number of cards for all computers in this order: (com1, com2, com3)
	 */
	public int[] numberOfComCards();
}

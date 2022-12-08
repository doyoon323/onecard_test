import java.util.Arrays;

public class CardDeckPut {
	
	private Card[] thrown = new Card[54];
	private int card_count =0;
	private CardDeckStart start_deck;
	
	
	//start_deck을 인수로 받아 연결시킨다. 받은start_deck을 이용할 수 있다(추가/제거) 
	public CardDeckPut(CardDeckStart s) {
		start_deck = s;
	}
	
	//받은 카드를 덱에 추가한다
	public void receiveCard(Card c) {
		thrown[card_count] = c;
		card_count+=1;
		
	}
	
	//Card[] --> void로 수정 
	public void remove() {
		
		Card temp = thrown[card_count-1];
		start_deck.appendDeck(Arrays.copyOfRange(thrown, 0, card_count-2), card_count-1);
		Arrays.fill(thrown, null);
		thrown[0]= temp;
		
		}
	
	public Card cardOnTop() {return thrown[card_count-1];}
	
}

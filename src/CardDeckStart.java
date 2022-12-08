
public class CardDeckStart {

	
	private int card_count=0; // 남은 카드 수
    private Card[] deck = new Card[54];
    // Invariant: deck[0], .., deck[card_count-1] 에 카드가 있다.
    
    
    //오브젝트 생성시 카드덱을만든다 
    public CardDeckStart() {
    	createDeck();
    	}
    
    
    
    //모든 문양을 (1~10 && J,Q,K) 총 13장씩 만들어주고, 조커카드 2가지를 만들고 deck에 저장한다. 
    private void createDeck() {
        createSuit(Card.SPADES);  //card_count = 13
        createSuit(Card.HEARTS);  //card_count = 26
        createSuit(Card.CLUBS);   //card_count = 39
        createSuit(Card.DIAMONDS);//card_count = 52
        
        deck[card_count] = new Card(Card.Black_Joker, 0);
        card_count +=1;
        deck[card_count] = new Card(Card.Color_Joker, 0);
        card_count +=1;
    }
    
    // craeteSuit - 카드에 문양과 숫자를 부여해줌, 13장씩 만듬 
    private void createSuit(String which_suit) {
        for(int i = 1; i <= 13; i++) {
            deck[card_count] = new Card(which_suit, i);
            card_count += 1;
        }
    }

    
    
   /** newCard - 임의의 새 카드 한 장을 뽑아 줌
    * @return 카드 덱에서 임의로 한 장을 뽑아 리턴 -- deck을 랜덤으로 섞는 역할을 함
    *            */
    public Card newCard() {
        int index = (int)(Math.random() * card_count);
        Card card_to_deal = deck[index];
        
        for (int i = index+1; i < card_count; i++)
            deck[i-1] = deck[i];
        
        card_count = card_count - 1;
        return card_to_deal;
    }

  
    
   /* appendDeck - StartDeck이 0이 되어서 PutDeck으로부터 카드를 받아 넣는다. 
    *  Card[] --> void 수정
    */
    public void appendDeck(Card[] d, int d_length) {
    	for (int i=0; i< d_length; i++) { 
    		deck[card_count] = d[i];
    		card_count+=1;
    	}
    	
    }
    
    public int cardCount() {return card_count;}
    
}


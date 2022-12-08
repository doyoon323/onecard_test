public class CardPlayer {
	
	

    private Card[] hand = new Card[16];
    private int card_count;
    public int idx = 0;
    Dealer dealer; 
    
    public CardPlayer(int max_cards) {
        hand = new Card[max_cards];
        card_count = 0;
   
    }

    public void receiveCard(Card d) {
    	//System.out.println(card_count);
		hand[card_count] = d;
		card_count += 1;
	}
    
    public Card [] hand() {
    	return hand;
    }
    
    public int cardCount() {
    	return card_count;
    }
    
    
 // 놓여진 카드(put) 과 hand 중 규칙에 맞게 낼 수 있는 카드 array를 return한다. 
 //	스페이드 1 인 경우에 공격카드만 낼 수 있는데 possible_cards는 스페이드 + 1 모두 모아준다. 
    public Card[] possible_cards(Card put) {
        Card[] cards = new Card[card_count];
        int cc = 0;
        
        for(int i=0; i< card_count; i++) {
          if(hand[i].rank() == put.rank() || hand[i].suit() == put.suit() || hand[i].rank() == 0 || put.rank() == 0){   
        	 cards[cc] = hand[i];
             cc++;
          }
       }
        Card possible[] = new Card[cc];
        for (int i = 0; i < cc; i++) {
            possible[i] = cards[i];
        }
        return possible;
    }

    
    // put에서 낼 수 있는 공격카드들만 모음 
    public Card[] possible_attack_cards(Card put) {
        Card[] cards = new Card[card_count]; 
        int cc = 0;
        Card[] possible;
        
        if (put.rank() == 2){
            for(int i=0; i<card_count; i++) {
                if(hand[i].rank() == 0 || hand[i].rank() ==  1 
                    || hand[i].rank() == 2){
                    cards[cc] = hand[i];
                    cc++;
                }
            }
            
            possible = new Card[cc];
            for (int i = 0; i < cc; i++) {
				possible[i] = cards[i];
            }
            
        }
        else if (put.rank() == 1){
            for(int i=0; i<card_count; i++) {
                if(hand[i].rank() == 0 || hand[i].rank() == 1){
                    cards[cc] = hand[i];
                    cc++;
                }
            }
            possible = new Card[cc];
            for (int i = 0; i < cc; i++) {
                possible[i] = cards[i];
            }
        }
        else{ //put.rank() == 0
            for(int i=0; i<card_count; i++) {
                if(hand[i].rank() == 0){
                    cards[cc] = hand[i];
                    cc++;
                }
            }
            possible = new Card[cc];
            for (int i = 0; i < cc; i++) {
                possible[i] = cards[i];
            }
        }
        
        return possible;
    }

    
    public boolean possible(Card put) {
		if(possible_cards(put).length > 0)
			return true;
		else
			return false;
	}
    
	public boolean possible_attack(Card put) {
    	if(possible_attack_cards(put).length > 0)  
			return true;
		else
			return false;
	}

    // thrown에 낸 카드 c를 인수로 받아서 
    public void putCard(Card c) {
    	for (int i = 0; i < card_count-1; i++){
    		if (hand[i].suit() == c.suit() && hand[i].rank() == c.rank()) {
    			hand[i] = null;
    			for (int j=i; j<card_count-1; j++) { // j<16 j = 15 j=14
    				hand[j] = hand[j+1];
    				hand[j+1] =null;
    			}
    			break;
    		}
    	}
    	card_count--;
    }
    
}

       

public class ComputerPlayer extends CardPlayer{
		
	private int computer_id; //컴퓨터 플레이어의 수? 
	
	public ComputerPlayer(int max_cards,int id) {
		super(max_cards);
		computer_id = id;
	}
	
	public boolean test() {
		return true;
	}
	
	public int name() {return computer_id;}
	
	// 놓여진 카드 put에 맞는 possible_cards 를 얻고 그 중 하나를 정해서 인자로 돌려준다
	public Card takeCard(Card put, boolean attack) {
		
		Card[] arr;
		Card putCard = null;
		 // do i have an attack card (is it possible to defend?)
		
		if(attack) {arr = super.possible_attack_cards(put);}
		else arr = super.possible_cards(put);
		
		if (arr.length > 0) { // arr 존재 
			if(attack){// 공격 
				if(put.rank() == 1) {
					for (Card c: arr) {
						if(c.rank() == 0 || c.rank() == 1 || (c.suit()==put.suit() && c.rank()==2)){
							putCard = c;
							super.putCard(c);
							return putCard;
						}
					}

				}
				else if(put.rank() == 2) {
					for(Card c: arr) {
						if(c.rank() == 0 || c.rank()==2 || (c.suit()==put.suit() && c.rank()==1)){
							putCard = c; // why?
							super.putCard(c);
							return putCard;
						}
					}
				}
				else if(put.rank() == 0) {
					for(Card c: arr) {
						if(c.rank() == 0){
							putCard = c;
							super.putCard(c); 
							return putCard;
						}
					}
				}
				return putCard;
			}
			else { // null 
				idx = (int)(Math.random() * arr.length);
				putCard = arr[idx];
				putCard(arr[idx]);
				return putCard;
			}
		}
		return putCard;
	}
}


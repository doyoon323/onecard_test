
public class Dealer {
	
	private CardDeckPut thrown;
	private CardDeckStart deck;
	
	// Dealer() 오브젝트 생성시 --> 카드를 놓을 덱/ 카드를 뽑을 54장의 카드덱을 생성한다. 
	public Dealer() {
		deck = new CardDeckStart();
		thrown = new CardDeckPut(deck);
	}
	
/* 딜러가 플레이어에게 카드를 먹이는  함수 : dealTo*/
	//없어서 먹어야하는 경우 && 공격받아서 먹어야하는 경우  :: n장의 카드를 플레이어 p의 덱에 추가한다. 
	public void dealTo(CardPlayer p ,int n ){
		for (int i=0; i<n;i++)
			p.receiveCard(deck.newCard());
	}

//플레이어가 카드를 내면 이를 놓인 덱에 추가하는 함수 : putCard
	//	hand에서 카드를 받아서 put deck에 넣는다
		public void putCard(Card d){
		    thrown.receiveCard(d);
		}

	// 시작할 때 한번만 돌아가는 메소드이다. 보여줄 카드 한 장을 추가한다. 
	public void dealToPut(){
		thrown.receiveCard(deck.newCard());
	}

	//맨 위 1장을 제외한 카드를 start_deck에다가 추가한다. 
	public void thrownToStart(){ // put -> thrown 함수 이름 수
		thrown.remove();
	}

	
	// thrown[0]만 돌려준다. 놓인 카드덱을 보여주기 위한 함수이고, 플레이어는 이에 관련된 카드를 내야한다. 
	public Card topCard() {
		return thrown.cardOnTop();
	}
}

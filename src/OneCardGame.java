import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

public class OneCardGame implements OneCardGameInterface {

	private Dealer dealer;
	private HumanPlayer hand_player;
	private ComputerPlayer hand_com1;
	private ComputerPlayer hand_com2;
	private ComputerPlayer hand_com3;

	private int total_players = 4;
	private CardPlayer[] turn = new CardPlayer[total_players]; //{Humanplayer, complayer, complayer, cplmaer} 
	private int max_cards = 16;
	private int nowTurn = 0; // 플레이어 턴부터 시작 turn[nowTurn] nowTurn = ((nowTurn + direction) % total_players) 
	private int cases; // 0 := 게임중 // 1 := player 승리 // 2 := player패배
	private int stackedAttack = 0; // 누적된 attack cards. 공격카드 겹쳐진 경우 얼마나 먹어야하는지.
	private int total_turns = 0; // 몇번째 턴인지 확인용.
	private int direction = 1; // 턴의 순서
	private String name = "";
	private Card topper;// 맨 마지막으로 낸 카드

	// complay switch문으로 간결화 시키면 좋을듯

	// attack 카드가 뭔지에 따라서 카드를 먹는다
	private boolean attack = false; // 공격 카드가 활성화 됀지 확인

	public OneCardGame(Dealer d) {
		dealer = d;
		// name = JOptionPane.showInputDialog("What is your name?");
		// 모든 플레이어 활성화
		hand_player = new HumanPlayer(max_cards, name);
		hand_com1 = new ComputerPlayer(max_cards,1);
		hand_com2 = new ComputerPlayer(max_cards,2);
		hand_com3 = new ComputerPlayer(max_cards,3);

		turn[0] = hand_player;
		turn[1] = hand_com1;
		turn[2] = hand_com2;
		turn[3] = hand_com3;

		StartGame();
	}

	public void StartGame() {

		// 랜덤으로 첫 순서 뽑기
		// nowTurn = (int)(Math.random() * (total_players-1));

		// Set CardDeckPut by adding a card from CardDeckStart
		for (int i = 0; i < total_players; i++) {
			dealer.dealTo(turn[i], 7); // 각 플레이어에게 7장씩 준다
		}

		

		// put deck의 맨 위에 카드를 하나 둔다
		dealer.dealToPut();
		cases = 0; // 현재 게임 상태. 0 - 게임중
		topper = dealer.topCard();

		System.out.println("game ready to start"); // debug line
	}

	// Computer's Turn
	public void ComPlay(ComputerPlayer cp) {
		// set for each computer
		boolean die = false;
		// 카드를 낼 수 있는지 검토 & 냈다면 card를 받고 내지못했다면 null을 받는다.
		Card take = cp.takeCard(topper, attack);

		if (!attack) { // attack = false 인 상태라면, 즉 앞사람의 카드가 공격카드가 아니거나 앞사람이 stackAttacked를 이미 먹음
			if (take != null) {// 카드를 낼 수 있는 경우(카드를 먹지 않아도 ok)
				
				// total turn 증가
				total_turns ++;
				
				// ComputerPlayer가 낸 take를 놓인 덱에 추가하고 topper를 설정한다.
				dealer.putCard(take);
				topper = take;

				// 내가 놓은 카드가 attack 카드인 경우
				int takeRank = take.rank();
				switch (takeRank) {
				case 0:
					stackedAttack += 5; 
					attack = true;
					break;
				case 1:
					stackedAttack += 3;
					attack = true;
					break;
				case 2:
					stackedAttack += 2;
					attack = true;
					break;
				case 11:
					nowTurn += 1;
					break;
				case 12:
					direction *= -1;
					break;
				case 13:
					nowTurn -= 1;
					break;
				default:
					break;
				}

				// 50% 확률로 컴퓨터가 원카드를 외치고 이김
				Random ran = new Random();
				int random_number;
				if (cp.cardCount() == 1) {
					random_number = ran.nextInt(2);
					if (random_number == 1)
						JOptionPane.showMessageDialog(null, "OneCard!");
					else
						JOptionPane.showMessageDialog(null, "fail!"); // 실패하면 카드 뽑아야지!
				}
			} else {// take == null, 낼 수 있는 카드가 없다면 카드를 먹는다

				// attack == false 이므로 1장을 먹는다.
				if (cp.cardCount() < 16) {
					dealer.dealTo(cp, 1);
				} else {
					dealer.dealTo(cp, 1);
					die = true;
					total_players -= 1;
					// 카드는.. 공중분해가 되도록 하자... (개인적의견입니다 ><)
				}
			}
		} else {// attack == true , 즉 바로 앞사람이 공격카드를 낸 상태
			if (take != null) { // 여기 위험 
				// 내가 놓은 카드인 attack 카드
				if (take.rank() == 0 || take.rank() == 1 || take.rank() == 2) {
					attack = true;
					if (take.rank() == 0)
						stackedAttack += 5;
					else if (take.rank() == 1)
						stackedAttack += 3;
					else if (take.rank() == 2)
						stackedAttack += 2;
				}
			} else {// attack 상황에서 카드를 먹어야하는 경우
				if (cp.cardCount() + stackedAttack <= 16) {
					dealer.dealTo(cp, stackedAttack);
					stackedAttack = 0; // 먹었으므로 누적된 카드는 0으로 초기화
					attack = false; // attack이 종료되었으므로 0으로 초기화
					System.out.println("false " + attack );
				} else {// 먹은 순간 16장을 초과한 경우
					die = true;
					stackedAttack = 0;
					attack = false;
					total_players -= 1;
				}
			}
		}

		// die 한 컴퓨터 플레이어의 칸을 비우고 당겨준다
		if (die) {
			turn[nowTurn] = null; // { 1,2,3,null} --> {1,2,3,}
			for (int nt = nowTurn; nt < total_players; nt++) {
				turn[nt] = turn[nt + 1];
			}
		}

		if (cp.cardCount() == 0) {// 승리!!!
			hand_player.lose();
			cases = 2;
		}
	}

	/*
	 * returns true if is human player's turn
	 */
	public boolean isPlayerTurn() {
		return (turn[nowTurn] == hand_player);
	}

	/*
	 * 카드 버튼을 눌렀을 때 가능한 카드면 플레이하고 (true) 아니면 false를 return한다.
	 */
	public boolean putCard(String suit, int rank) {
		// 앞이 공격 효과를 잃은 조커인 경우 아무카드나 놔둘 수 있는데 이 기능은 어떤 함수에 작성하지?
		// 플레이어 차례이고 낼 수 있는 카드가 있다면 (null 방지문
		if (isPlayerTurn()) { // 플레이어의 턴인 경우 attack이면 공격카드만,!attack이라면 공격+일반카드
			if (!attack) { // 클릭을 받았을 때 앞의 카드가 attack이 아닌 경우(일반카드)
				if (hand_player.possible(topper)) {// 낼 수 있는 카드가 존재
					// 가능한 카드를 Card array p로 받는다
					Card[] p = hand_player.possible_cards(topper);
					// 낼 수 있는 카드 중에 만약 누른 카드와 일치하는 카드가 있다면
					System.out.println(p.length);

					for (int i = 0; i < p.length; i++) {
						if (p[i].rank() == rank && p[i].suit().equals(suit)) {
							System.out.println("rank: " + rank + " suit: " + suit + " was played!");
							switch (rank) {
							case 0:
								stackedAttack += 5;
								attack = true;
								break; // black||color both 5
							case 1:
								stackedAttack += 3;
								attack = true;
								break; // ace
							case 2:
								stackedAttack += 2;
								attack = true;
								break; // rank 2
							case 11:
								nowTurn += 1;
								break; // J skips turn
							case 12:
								direction *= -1;
								break; // Q changes direction
							// case 13: PlayerPlay(); break; // 재귀 K one more turn
							case 13:
								nowTurn -= direction;
								break; // 한칸 이전으로 돌려야 +해도 내 차례다.
							default:
								break;
							}

							// 여기 hand_player가 카드를 put하고 dealer가 받는다
							//Card c = new Card(suit, rank);
							hand_player.putCard(p[i]);
							System.out.println("P_HAND: "+Arrays.toString(hand_player.hand()));
							dealer.putCard(p[i]);
							total_turns++; // 턴 바뀔때마다 총 턴 증가
							topper = p[i]; // topper 매번 재설정 해야 되나요?

							// 여기서 (카드를 낸 상태) 만약 player의 카드 수가 0이면 플레이어 승리다
							// 1이면 원카드 (아직 구현하지 않음)
							if (hand_player.cardCount() < 1)
								cases = 1;

							// player가 카드를 성공적으로 놨다. 턴을 끝낸다.
							nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
							// 플레이어 차례일 때까지 컴퓨터가 플레이한다.
							//System.out.println("NOWTURN=" + turn[nowTurn]);

							// computerPlayer1,2,3이 모두 시행된다.
							computerProcess();
							
							System.out.println("suit: " + dealer.topCard().suit() + " rank: " + dealer.topCard().rank());
							return true; // 낼 수 있는 카드다
						}
					} // for문 끝
				}
			} else {// attack인 상태
				if (hand_player.possible_attack(topper)) { // possible_attack 이 있는 경우
					Card[] p = hand_player.possible_attack_cards(topper);
					// 낼 수 있는 카드 중에 만약 누른 카드와 일치하는 카드가 있다면
					for (int i = 0; i < p.length; i++) {
						if (p[i].rank() == rank && p[i].suit().equals(suit)) {
							System.out.println("rank: " + rank + " suit: " + suit + " was played!");
							switch (rank) {
							case 0: //joker
								stackedAttack += 5;
								attack = true;
								break; // black||color both 5
							case 1: //ace
								stackedAttack += 3;
								attack = true;
								break; // ace
							case 2: 
								stackedAttack += 2;
								attack = true;
								break; // rank 2
							default:
								break;
							}

							Card c = new Card(suit, rank);
							hand_player.putCard(c);
							total_turns++; // 턴 바뀔때마다 총 턴 증가
							topper = c; // topper 매번 재설정 해야 되나요?

							// 여기서 (카드를 낸 상태) 만약 player의 카드 수가 0이면 플레이어 승리다
							// 1이면 원카드 (아직 구현하지 않음)
							if (hand_player.cardCount() < 1)
								cases = 1;

							// player가 카드를 성공적으로 놨다. 턴을 끝낸다.
							nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
							// 플레이어 차례일 때까지 컴퓨터가 플레이한다.
							//System.out.println("NOWTURN=" + turn[nowTurn]);
							computerProcess();
							return true;
						}
					}
				} 
			}
		}
// 플레이어 차례가 아닌 경우 
		System.out.println("couldn't play suit: " + dealer.topCard().suit() + " rank: " + dealer.topCard().rank());
		return false;

	}

	// 컴퓨터들을 플레이시켜준 후 attack이 true && 플레이어가 카드를 낼 수 없다면 -> 플레이어에게 카드를 먹이고 attack이
	// false 이거나 플레이어가 카드를 낼 수 있을 때까지
	public void computerProcess() { // 더 나은 이름 모집중
		System.out.println("start");
		
		while (!isPlayerTurn() && cases == 0) { // 현 게임 상태도 확인해준다. 중간에 바뀌었을 수도 있습니다.
			System.out.println("player: " + ((ComputerPlayer)turn[nowTurn]).name());
			ComPlay((ComputerPlayer) turn[nowTurn]);
			System.out.println("ComputerPutCard :"+ topper.suit() + " " + topper.rank());
			nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
			total_turns++;
		}
		
		System.out.println("Player turn");
		// 이제 플레이어의 턴일때 공격이 아닐때까지 computerProcess를 돌려준다.
		if (attack && !hand_player.possible_attack(topper)) {
			// 방어 카드가 없어서 먹어야하는 경우
			System.out.println("방어 카드가 없어서 먹어야하는 경우 ");
			if (hand_player.cardCount() + stackedAttack < 16) {
				dealer.dealTo(hand_player, stackedAttack);
				stackedAttack = 0;
				attack = false;
				nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
				// 나는 카드를 내지 못하고 먹었으므로 다시 컴퓨터의 턴
				computerProcess();
			}
			// 먹은 순간 16장을 초과한 경우
			else
				cases = 2; // 플레이어의 패배

		}
		if (!attack && !hand_player.possible(topper)) {
			System.out.println("!attack && !hand_player.possible(topper)");
			if (hand_player.cardCount() + 1 <= 16) {
				System.out.println("hand_player.cardCount() < 16");
				dealer.dealTo(hand_player, 1);
				nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
				computerProcess();
			}
			else cases = 2;
		}

		// attack == false 이면computerProcess가 종료하고 플레이어는 입력을 받으면 된다.
	}

	// Player decides to take a card ( despite possible )
	public void endPlayerTurn() {
		if (isPlayerTurn()) { // 내가 카드를 내지 않겠다고 했을 때 카드를 한장 먹고 내 턴이 돌아올때까지 코드를 돌린다
			if (hand_player.cardCount() < 16) {
				dealer.dealTo(hand_player, 1); // 카드 받음
				nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
				computerProcess(); // 내 턴이 돌아올때까지 코드를 돌림
			}
			else {
				// nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
				cases = 2;
				System.out.println("///// PLAYER LOSES /////");
			}
		}
	}

	// 여기서 topper 카드가 아니라 dealer.topCard()를 돌려주는 이유가 있나요? 중간에 제대로 세팅이 안돼 있을 수 있을지도.
	public Card topCard() {
		Card top = dealer.topCard();
		return top;
	}

	public Card[] playerCards() {
		return hand_player.hand();
	}

	public int playerCardCount() {
		return hand_player.cardCount();
	}

	/*
	 * returns int[] of each computer's card numbers (0, 1, 2) = com1, com2, com3
	 */
	public int[] numberOfComCards() {
		int handArr[] = new int[3];
		handArr[0] = hand_com1.cardCount();
		handArr[1] = hand_com2.cardCount();
		handArr[2] = hand_com3.cardCount();
		return handArr;
	}

	/*
	 * test
	 */
	public static void main(String[] args) {
		OneCardGame ocg = new OneCardGame(new Dealer());
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("game state: " + ocg.state());
			System.out.println("PUT CARD SUIT: " + ocg.topCard().suit() + " RANK: " + ocg.topCard().rank());
			System.out.println("MY CARDS("+ ocg.playerCardCount()+"): ");

			Card[] arr = ocg.playerCards();
			for (int i = 0; i < ocg.playerCardCount(); i++) {
				System.out.println(arr[i].suit() + " " + arr[i].rank());
			}

			System.out.println("SUIT: ");
			String suit = sc.nextLine();
			while (suit.isBlank())
				suit = sc.nextLine();

			System.out.println("RANK: ");
			String rank = sc.nextLine();
			while (rank.isBlank())
				rank = sc.nextLine();

			String end = "";
			if (ocg.putCard(suit, Integer.parseInt(rank)))
				System.out.println("SUCESS");
			else {
				System.out.println("end turn? 0 for no 1, for yes: ");
				end = sc.nextLine();
				while (end.isBlank() || end.isEmpty() || end.equals(""))
					end = sc.nextLine();
				if (Integer.parseInt(end) == 1) {
					ocg.endPlayerTurn();
				}
			}

		}
	}

	// returns what turn it is (누적)
	public int turnCount() {
		return total_turns;
	}

	public int chips() {
		return hand_player.chips();
	}

	public void setUsername(String str) {
		name = str;
		System.out.println("name set as " + str); // debug line
	}

	public String username() {
		return name;
	}

	public int state() {
		return cases;
	}

}


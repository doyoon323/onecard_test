
public class Card {
	
	public static final String SPADES = "spades";
    public static final String HEARTS = "hearts";
    public static final String DIAMONDS = "diamonds";
    public static final String CLUBS = "clubs";

    
    public static final int ACE = 1;
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;
    public static final String Black_Joker = "blackjoker";
    public static final String Color_Joker = "colorjoker";

    
    private String suit;
    private int rank;
    
    public Card(String s, int r) {
        suit = s; 
        rank = r;
    }
    
    public String suit() {
        return suit;
    }

    public int rank() {
        return rank;
    }
    

    //특정카드와 모양이같은경우 
    public boolean shape_equals(Card c) {
        return suit.equals(c.suit());
    }
    
    //특정카드와 숫자가 같은 경우 
    public boolean num_equals(Card c) {
    	return rank == c.rank();
    }
    
    //+
    
}




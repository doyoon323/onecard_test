import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CardImage {

	private String suit; //hearts, spades, clubs, diamonds, blackjoker, redjoker
    private String rank;
    private int rank_int;
    private Image img=null;
  
    public Image img() {
    	return img;
    }

    public CardImage(String s, int r) {
    	suit = s;
    	rank_int = r;
    	rank = Integer.toString(r);
    	setImage(suit, r);
    }
	
	private void setImage(String suit, int rank_int) {
		if (rank_int ==1) rank = "ace";
    	else if (rank_int==11) rank = "jack";
    	else if (rank_int== 12) rank = "queen";
    	else if (rank_int==13) rank = "king";
    	else rank = Integer.toString(rank_int);
    	try {
    		if (suit.equals("blackjoker")) {
    			img = ImageIO.read(new FileInputStream("img/cards/black_joker.png"));
    		}
    		else if (suit.equals("redjoker")) {
    			img = ImageIO.read(new FileInputStream("img/cards/red_joker.png"));
    		}
    		else {
    			img = ImageIO.read(new FileInputStream("img/cards/"+rank + "_of_" + suit+".png"));
    		}
    		int n = 6; // image scaler ratio int
			int width = img.getWidth(null)/n;
			int height = img.getHeight(null)/n;
			img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			} catch (Exception ex) {
				System.out.println(ex);
		}
	}
}
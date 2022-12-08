import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class CardButton extends JButton implements ActionListener{
    private String suit; //hearts, spades, clubs, diamonds, blackjoker, redjoker
    private String rank;
    private int rank_int;
    private int width;
    private int height;
    private OneCardWriter oneCardWriter;
    
    public String suit() {
    	return suit;
    }
    public int rank() {
    	return rank_int;
    }

    public CardButton(String s, int r, OneCardWriter ocw) {
    	oneCardWriter = ocw;
    	suit = s;
    	rank_int = r;
    	setImage(suit, rank_int);
    	
    	// remove button border
    	setBorder(BorderFactory.createEmptyBorder());
    	setContentAreaFilled(false);
    	addActionListener(this);
    	setSize(width, height);
    	setToolTipText("SUIT: " + suit.toUpperCase() + " RANK: " + rank_int);
    }

	public void actionPerformed(ActionEvent e) {
		System.out.println("c");
		oneCardWriter.putCard(suit, rank_int);
	}
    
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLayout(new GridLayout(2,8));
//		f.add(new CardButton("diamonds", 1));
//		f.add(new CardButton("diamonds", 2));
//		f.add(new CardButton("diamonds", 5));
//		f.add(new CardButton("diamonds", 7));
//		f.add(new CardButton("diamonds", 12));
//		f.add(new CardButton("hearts", 1));
//		f.add(new CardButton("hearts", 4));
//		f.add(new CardButton("hearts", 5));
//		f.add(new CardButton("hearts", 6));
//		f.add(new CardButton("hearts", 7));
//		f.add(new CardButton("clubs", 10));
//		f.add(new CardButton("spades", 1));
//		f.add(new CardButton("spades", 8));
//		f.add(new CardButton("spades", 10));
//		f.add(new CardButton("spades", 12));
//		f.add(new CardButton("blackjoker", 0));
		f.pack();
		//f.setSize(800,600);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	private void setImage(String suit, int rank_int) {
		if (rank_int ==1) rank = "ace";
    	else if (rank_int==11) rank = "jack";
    	else if (rank_int== 12) rank = "queen";
    	else if (rank_int==13) rank = "king";
    	else rank = Integer.toString(rank_int);
		Image img = null;
    	// 
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
			width = img.getWidth(getFocusCycleRootAncestor())/n;
			height = img.getHeight(getFocusCycleRootAncestor())/n;
			img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			setIcon(new ImageIcon(img));
			} catch (Exception ex) {
				System.out.println(ex);
		}
	}
	
}
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CardDeckButton extends JButton implements ActionListener {

	private OneCardWriter cardWriter;
	public CardDeckButton(OneCardWriter ocw) {
		cardWriter = ocw;
		Image card_back = null;
		// load image file
		try {
			card_back = ImageIO.read(new FileInputStream("img/back.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//resize image
		int n = 6; // image scaler ratio int
		int width = card_back.getWidth(getFocusCycleRootAncestor())/n;
		int height = card_back.getHeight(getFocusCycleRootAncestor())/n;
		card_back = card_back.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		setIcon(new ImageIcon(card_back));
		setBorder(BorderFactory.createEmptyBorder());
    	setContentAreaFilled(false);
    	addActionListener(this);
    	setSize(width, height);
	}
	
	public void actionPerformed(ActionEvent e) {
		cardWriter.endTurn();
	}
	
}

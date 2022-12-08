import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


public class LoginPanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private List<ActionListener> buttonActionListeners= new ArrayList<>();
	
	private JTextField username;
	private OneCardWriter oneCardWriter;
	private JLabel err_label;
	private BufferedImage joker_card;
	private static int window_width = 1500;
	private static int window_height = 800;
	private float alpha = 0;
    private float delta = 0.01f;
    private static int x_velocity = 0;
    private static String[] suits = {"hearts", "spades", "clubs", "diamonds"};
    private int rand_suit; // random number from 1 to 9
    private int rand_rank; // random number from 0 to 3
    private static int x_padding = 20;
    private Image card_back = null;
    private int card_width;
    private int card_height;
    
    
    // temporary variables for debugging, should be deleted
    private static JFrame f; // temp
    private String name;
	
	public LoginPanel() {
		try { card_back = ImageIO.read(new FileInputStream("img/back.png"));
		} catch (Exception e) { e.printStackTrace(); }
		card_width = card_back.getWidth(null)/3;
		card_height = card_back.getHeight(null)/3;
		System.out.println(card_width);
		System.out.println(card_height);
		
		rand_rank = (int)(Math.random()*8) + 1;
		rand_suit = (int)(Math.random()*3);
		
		//oneCardWriter = ocw;
		try { joker_card = ImageIO.read(new FileInputStream("img/cards/black_joker.png"));
		} catch (Exception e) { e.printStackTrace(); }
		
		// Timer for animation
		alpha = 1.0f;
		
//		Timer timer = new Timer(1000, new ActionListener(){ 
//			 public void actionPerformed(ActionEvent e) {
//                 alpha += delta;
//                 if (alpha > 1.0) {
//                     alpha = 1.0f;
//                     delta *= -1.0f;
//                 } else if (alpha < 0.0) {
//                     alpha = 0.0f;
//                     delta *= -1.0f;
//                 }
//                 
//             }
//		});
//		timer.start();
        
		
		Timer timer2 = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				rand_rank = (int)(Math.random()*8) + 1;
//				rand_suit = (int)(Math.random()*3);
				x_velocity += 5;
				if (x_velocity > x_padding + card_width) x_velocity = 0;
				repaint();
			}
		});
		timer2.start();
		
		// import font
		File font_file = new File("img/SanMarinoBeach-2OOLW.ttf");
		Font font = null;
		// Get Font
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Font font2 = null;
		font_file = new File("img/Bebas-Regular.ttf");
		try { font2 = Font.createFont(Font.PLAIN, font_file); }
		catch (Exception e) { e.printStackTrace(); }
		
		// Initialize
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Title Text
		JLabel title = new JLabel("One Card");
		title.setFont(font2.deriveFont(Font.PLAIN, 72f));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		add(title, c);
		
		// Username text
		JLabel text = new JLabel();
		text.setText("USERNAME: ");
		c.insets = new Insets(15,0,0,0); // top padding
		c.gridwidth = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.5;
		add(text, c);
		
		// Username input
		username = new JTextField(12);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(username, c);
		username.addActionListener(this);
		c.weightx = 0.0;
		
		c.anchor = GridBagConstraints.CENTER;
		
		// Error message
		err_label = new JLabel();
		err_label.setText("Please enter your username.");
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 2;
		add(err_label, c);
		
		JButton start = new JButton("Start");
		start.setFont(font2.deriveFont(Font.PLAIN, 24f));
		start.addActionListener(this);
		
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 4;
		c.ipady = 40;
		c.ipadx = 100;
		c.insets = new Insets(15,0,0,0); // top padding
		add(start, c);
		setPreferredSize(new Dimension(300,300));
		
	}
	
	// Graphics
	public void paintComponent(Graphics graphics) {
//		int width = card_img.getWidth();
//		int height = card_img.getHeight();
//		// recolor image to grayscale 
//		// >> shifts a bit pattern to the right 
//	    for (int col = 0; col < width; col++) {
//	        for (int row = 0; row < height; row++) {
//	        	int rgb = card_img.getRGB(col, row);
//	        	int a = (rgb >> 24) & 0xff; // alpha value 
//	        	int red = (rgb >> 16) & 0xFF;
//	        	int green = (rgb >> 8) & 0xFF; 
//	        	int blue = rgb & 0xFF;
//	        	int sum = (red + green + blue)/3;
//	        	int p = (a<<24) | (sum<<16) | (sum<<8) | sum;
//	            card_img.setRGB(col, row, p);
//	        }
//	    }
//	    width/=4; height/=4;
//		graphics.drawImage(card_img, 0, 0, width, height, null);
//		
//		
//		CardImage tmp = new CardImage(suits[rand_suit], rand_rank);
//		Image card_image = tmp.img();
//		int cardW = card_image.getWidth(null)*2;
//		int cardH = card_image.getHeight(null)*2;
		
		float opacity = 0.5f;
		Graphics2D g = (Graphics2D) graphics.create();
		g.setComposite(AlphaComposite.SrcOver.derive(0.5f));
		int base = (-1) * (card_width+x_padding);
		// g.drawImage(card_image, base, base, cardW, cardH, null);
		for (int i =0; i<9; i++) {
			for (int j=0; j<3; j++) {
				int x_coord = base+(card_width+20)*i;
				if (j %2==0) {
					x_coord -= base;
					x_coord-= x_velocity;
				} else {
					x_coord += x_velocity;
				}
				// card_image = (new CardImage(suits[rand_suit], rand_rank).img());
				Image card_image = card_back;
				g.drawImage(card_image, x_coord, 5+(card_height+x_padding)*j, card_width, card_height, null);
			}
			
		}
		
		g.dispose();
		
		graphics.setColor(Color.white);
		graphics.fillRoundRect(getWidth()/2-card_width*3/2, getHeight()/2-card_height*3/2, card_width*3, card_height*3, 50, 50);
		graphics.setColor(Color.black);
		graphics.drawRoundRect(getWidth()/2-card_width*3/2, getHeight()/2-card_height*3/2, card_width*3, card_height*3, 50, 50);
	}
	
	// Main
	public static void main(String[] args) {
		f = new JFrame("Login");
		f.add(new LoginPanel());
		f.setSize(window_width,window_height);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}


	public void actionPerformed(ActionEvent e) {
		// if (e.getActionCommand() == "Start") System.out.println("start");
		String text = username.getText();
		if (text!="null" && ! text.trim().isEmpty()) {
			text = text.strip();
			text = text.replaceAll("[\\s]+", "");
			if (!text.matches("[\\d\\w]+") )  // [] matches any pattern in the brackets digits words more than once 
				err_label.setText("Username must only contain letters and numbers.");
			else {
				System.out.println(text);
				err_label.setText("Welcome "+text+"!\nPress start to begin.");
				name = text;
			}
		}
		else 
			err_label.setText("Username cannot be empty.");
		
		username.selectAll();
		username.setText("");
		
		for(ActionListener listener: buttonActionListeners){
			System.out.println("pressed");
		}
		if(e.getActionCommand().toString().equals("Start"))startGame(name);
		
	}
	
	public void addMyButtonActionListener(ActionListener a){
        if(!buttonActionListeners.contains(a))
            buttonActionListeners.add(a);
    }
	
	// test method
	private void startGame(String username) {
		OneCardGame cardGame = new OneCardGame(new Dealer());
		cardGame.setUsername(name);
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		OneCardWriter gameWriter = new OneCardWriter(cardGame);
		f.setTitle("OneCard");
		f.setContentPane(gameWriter);
		
		
	}
}

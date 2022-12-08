import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class OneCardGUIController {
	private static int window_width = 1500;
	private static int window_height = 800;
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setTitle("OneCard");
		
//		OneCardWriter writer = new OneCardWriter(new OneCardGame());
//		f.setContentPane(writer);
		
		f.setContentPane(new LoginPanel());
		
		f.setSize(window_width, window_height);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}

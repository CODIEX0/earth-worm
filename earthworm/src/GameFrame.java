import javax.swing.JFrame;

public class GameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GameFrame(){
		
		this.add(new GamePanel());
		this.setTitle("Earth Worm");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}

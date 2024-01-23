import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1806389001071659170L;
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 23;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 50;
	final int x[] = new int [GAME_UNITS];
	final int y[] = new int [GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	int mangosEaten;
	int mangoX;
	int mangoY;
	char direction = 'R';
	boolean running = false; 
	Timer timer;
	Random random;
	Random r = new Random();
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(new Color(102, 51, 1));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame(); 
		
	}
	public void startGame() {
		newApple();
		newMango();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
	}
     public void paintComponent(Graphics g) {
    	 super.paintComponent(g);
    	 draw(g);
     }
     public void draw(Graphics g) {
    	 
    	 if(running) {
    		 //Display grids
    	     /*
    		 for(int i = 0;i < SCREEN_HEIGHT/UNIT_SIZE;i++) {
    		 	 g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
    		 	 g.drawLine(0, i*UNIT_SIZE, SCREEN_HEIGHT, i*UNIT_SIZE);
    	     }*/
	    	 g.setColor(Color.yellow);
	    	 g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
	    	 
	    	 g.setColor(Color.green);
	    	 g.fillOval(mangoX, mangoY, UNIT_SIZE, UNIT_SIZE);
	    	 
	    	 for(int i = 0; i< bodyParts;i++) {
	    		 if(i == 1 || i == 2) {
	    			 g.setColor(Color.red);
	    			 g.fillRect(x[i],y[i],UNIT_SIZE+2,UNIT_SIZE+2);	    			 
	    		 }
	    		 else {
	    			 //pink worm
	    			 g.setColor(Color.pink);
	    			 g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE); 
	    			 //Rainbow worm
	    			 // g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));    			 
	    		 }
	     }
    	 g.setColor(Color.red);
    	 g.setFont(new Font("Ink Free",Font.BOLD,40));
    	 FontMetrics metrics = getFontMetrics(g.getFont());
    	 int score = (applesEaten*2 + mangosEaten*2);
    	 g.drawString("Score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score))/2, g.getFont().getSize());
    	 }
    	 else {
    		 gameOver(g);
    	 }
     }
     public void newApple() {
    	 appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    	 appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
     }
     
     public void newMango() {
    	 mangoX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    	 mangoY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
     }
     public void move() {
    	 for(int i = bodyParts;i>0;i--) {
    		 x[i] = x[i-1];
    		 y[i] = y[i-1];
    	 }
    	 switch(direction) {
    	 case 'U':
    	 y[0] = y[0] - UNIT_SIZE;
    	 break;
    	 
    	 case 'D':
    	 y[0] = y[0] + UNIT_SIZE;
    	 break;

         case 'L':
     	 x[0] = x[0] - UNIT_SIZE;
    	 break;
	 
         case 'R':
	     x[0] = x[0] + UNIT_SIZE;
	     break;
    	 }
     }
     public void checkLeaf() {
    	 if((x[0] == appleX) && (y[0] == appleY)) {
    		 bodyParts++;
    		 applesEaten++;
    		 newApple();    		 
    	 }
     }
     
     public void checkMango() {
    	 if((x[0] == mangoX) && (y[0] == mangoY)) {
    		 bodyParts++;
    		 mangosEaten++;
    		 newMango();    		 
    	 }
     }
     
     public void checkCollisions() {
    	 // checks if head collides with body
    	 for(int i = bodyParts;i>0;i--) {
    		 if((x[0] == x[i]) && (y[0] == y[i])) {
    			 //bodyParts--;
    			 running = false;
    		 }
    	 }
    	 //check if head touches left border 
    	 if(x[0] < 0) {
    		 //stop running
    		 running = false;
    	 }
    	 //check if head touches right border 
    	 if(x[0] > SCREEN_WIDTH) {
    		//stop running
    		 running = false;
    	 }
    	//check if head touches top border 
    	 if(y[0] < 0) {
    		//stop running
    		 running = false;
    	 }
    	//check if head touches bottom border 
    	 if(y[0] > SCREEN_HEIGHT) {
    		 //stop running    		 
    		 running = false;
    	 }
    	 if(!running) {
    		 timer.stop();
    	 }
     }
     
     public void gameOver(Graphics g) {
    	 //Display score
    	 g.setColor(Color.red);
    	 g.setFont(new Font("Ink Free",Font.BOLD,50));
    	 FontMetrics metrics1 = getFontMetrics(g.getFont());
    	 int score = (applesEaten*2 + mangosEaten*2);
    	 g.drawString("Score: " + score, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + score))/2, g.getFont().getSize());
    	 //Game over text
    	 g.setColor(Color.red);
    	 g.setFont(new Font("Ink Free",Font.BOLD,75));
    	 FontMetrics metrics2 = getFontMetrics(g.getFont());
    	 g.drawString("Wasted", (SCREEN_WIDTH - metrics2.stringWidth("Wasted"))/2, SCREEN_HEIGHT/2);
     }
     
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkLeaf();
			checkMango();
			checkCollisions();			
		}
		repaint();		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}			
		}
	}
}

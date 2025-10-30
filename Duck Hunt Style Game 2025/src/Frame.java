import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	//frame size
	private int screenWidth = 910, screenHeight = 540;
	private String title = "Duck Hunt";
	
	private int count = 3; 
	
	private int totalScore = 0; 
	//private int time = 30; 
	int waveNum = 1;
	
	/**
	 * Declare and instantiate (create) your objects here
	 */
	private Duck duckObject = new Duck();
	private Duck2 duck2Object = new Duck2();
	
	private Background myBackground = new Background();
	
	
	
	private Character characterObject = new Character();
	private Character2 character2Object = new Character2();
	
	private BackgroundLayer backgroundLayerObject = new BackgroundLayer();
	
	private Ammo ammoObject = new Ammo();
	private Ammo2 ammo2Object = new Ammo2();
	private Ammo1 ammo1Object = new Ammo1();
	private Ammo0 ammo0Object = new Ammo0();
	
	private MyCursor cursor = new MyCursor();
	
	Music mouseClickSound = new Music("sparkleSoundOfficial.wav", false); 
	Music newWave = new Music("bookSoundNewWave.wav", false);
	
	public void paint(Graphics pen) {
		
		//this line of code is to force redraw the entire frame
		super.paintComponent(pen);
		
		//background should be drawn before the objects
		//or based on how you want to LAYER
		myBackground.paint(pen);
		
		
		//call paint for the object
		//for objects, you call methods on them using the dot operator
		//methods use always involve parenthesis
		duckObject.paint(pen);
		
		duck2Object.paint(pen);
		
		backgroundLayerObject.paint(pen);
		
		
		
		characterObject.paint(pen);
		
		character2Object.paint(pen);
		
		
		cursor.paint(pen);
		
		
		
		if(count == 3) {
			ammoObject.paint(pen);
		}
		if(count == 2) {
			ammo2Object.paint(pen);
		}
		if(count == 1) {
			ammo1Object.paint(pen);
		}
		if(count == 0) {
			ammo0Object.paint(pen);
		}
		if(count ==-1) {
			ammo0Object.paint(pen);
			count = 3; 
		}
		
		
		Font f = new Font("Segoe UI", Font.PLAIN, 50); 
		pen.setFont(f);
		pen.setColor(Color.white); 
		pen.drawString("Score: " + totalScore, 300, 50);
		//pen.drawString("" + time, 500, 50); 
		
		Font font2 = new Font("Segoe UI", Font.PLAIN, 50); 
		pen.setFont(font2);
		pen.drawString("Wave " + waveNum, 500, 50);
		
		if(totalScore == 5) {
			totalScore = 0;
			count = 3;
			waveNum++; 
			this.newWave.play(); 
		}
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent mouse) {
	    // Runs when the mouse is clicked (pressed and released quickly).
	    // Example: You could use this to open a menu or select an object.
	}

	@Override
	public void mouseEntered(MouseEvent mouse) {
	    // Runs when the mouse enters the area of a component (like a button).
	    // Example: You could highlight the button when the mouse hovers over it.
	}

	@Override
	public void mouseExited(MouseEvent mouse) {
	    // Runs when the mouse leaves the area of a component.
	    // Example: You could remove the highlight when the mouse moves away.
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
	    // Runs when a mouse button is pressed down.
	    // Example: You could start dragging an object here.
		System.out.println(mouse.getX()+""+mouse.getY()); 
		
		duckObject.checkCollision(mouse.getX(), mouse.getY());
		duck2Object.checkCollision(mouse.getX(), mouse.getY());
		
		count -= 1; 
		
		
		if(this.duckObject.checkCollision(mouse.getX(), mouse.getY()) && count != -1) {
			totalScore++; 
		}
		
		if(this.duck2Object.checkCollision(mouse.getX(), mouse.getY()) && count != -1) {
			totalScore++; 
		}
		this.mouseClickSound.play(); 
		
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
	    // Runs when a mouse button is released.
	    // Example: You could stop dragging the object or drop it in place.
	}



	/*
	 * This method runs automatically when a key is pressed down
	 */
	public void keyPressed(KeyEvent key) {
		
		System.out.println("from keyPressed method:"+key.getKeyCode());
		
	}

	/*
	 * This method runs when a keyboard key is released from a pressed state
	 * aka when you stopped pressing it
	 */
	public void keyReleased(KeyEvent key) {
		
	}

	/**
	 * Runs when a keyboard key is pressed then released
	 */
	public void keyTyped(KeyEvent key) {
		
		
	}
	
	
	/**
	 * The Timer animation calls this method below which calls for a repaint of the JFrame.
	 * Allows for our animation since any changes to states/variables will be reflected
	 * on the screen if those variables are being used for any drawing on the screen.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}
	
	/*
	 * Main method to create a Frame (the GUI that you see)
	 */
	public static void main(String[] arg) {
		new Frame();
	}
	
	
	
	public Frame() {
		JFrame f = new JFrame(title);
		f.setSize(new Dimension(screenWidth, screenHeight));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		  
		//cursor icon code
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("cursor.png");
		Cursor a = toolkit.createCustomCursor(image , new Point(this.getX(), this.getY()), ""); 
		this.setCursor (a);
		
	}

}
